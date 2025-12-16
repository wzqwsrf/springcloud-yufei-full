package com.yufei.search.service.impl;

import co.elastic.clients.elasticsearch._types.FieldSort;
import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.TermQuery;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.search.SourceConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yufei.search.component.ElasticComponent;
import com.yufei.search.dto.ResponseDto;
import com.yufei.search.dto.StatusIndexDocument;
import com.yufei.search.dto.StatusQueryDto;
import com.yufei.search.dto.StatusSearchResult;
import com.yufei.search.es.IEsIkTokenService;
import com.yufei.search.service.IStatusSearch;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangzhenqing
 * @date 2025/12/02 11:55
 * @description
 */
@Service
@Slf4j
public class StatusSearchImpl implements IStatusSearch {

    @Resource
    private ObjectMapper objectMapper;

    private static final String INDEX_NAME = "status_index";
    private static final String CREATE_TIME = "create_time";

    @Resource
    private IEsIkTokenService esIkTokenService;
    @Resource
    private ElasticComponent elasticComponent;

    @Override
    public Object search(StatusQueryDto queryDto) {
        Query query = getBoolQuery(queryDto);
        List<SortOptions> sortOptionsList = new ArrayList<>();
        sortOptionsList.add(SortOptions.of(f -> f.field(FieldSort.of(fn -> fn.field(CREATE_TIME).order(SortOrder.Desc)))));
        List<String> sourceList = List.of("id", "title", "content", "create_time", "user_id");
        int start = (queryDto.getPage() - 1) * queryDto.getSize();
        SearchRequest searchRequest = SearchRequest.of(b -> b.query(query)
                .index(INDEX_NAME)
                .from(start)
                .size(queryDto.getPage()).sort(sortOptionsList).
                source(SourceConfig.of(s -> s.filter(f -> f.includes(sourceList)))));
        log.info("searchRequest:{}", searchRequest);
        StopWatch stopWatch = new StopWatch("status search");
        stopWatch.start("Query Build");
        ResponseDto<List<StatusIndexDocument>> responseDto = elasticComponent.search(searchRequest,
                StatusIndexDocument.class);
        stopWatch.stop();
        log.info("Es Search cost:{}", stopWatch.getLastTaskTimeMillis());

        StatusSearchResult<StatusIndexDocument> result = new StatusSearchResult<>();
        List<StatusIndexDocument> resultDtoList = new ArrayList<>();
        if (responseDto.getData() != null) {
            resultDtoList.addAll(responseDto.getData());
        }
        result.setDataList(resultDtoList);
        result.setTotalNum(responseDto.getTotal());
        log.info("total cost:{}", stopWatch.prettyPrint());
        log.info("Process completed successfully.");
        return result;
    }

    /**
     * 基础query
     *
     * @param requestDto
     * @return
     */
    public Query getBoolQuery(StatusQueryDto requestDto) {
        String query = requestDto.getQuery();

        List<String> wordList = esIkTokenService.getIkSmartTokenList(query);
        List<Query> titleMustList = new ArrayList<>();
        List<Query> contentMustList = new ArrayList<>();

        for (String partQ : wordList) {
            titleMustList.add(TermQuery.of(t -> t.field("title").value(partQ))._toQuery());
            contentMustList.add(TermQuery.of(t -> t.field("content").value(partQ))._toQuery());
        }

        List<Query> filterList = new ArrayList<>();
        List<Query> shouldList = new ArrayList<>();
        shouldList.add(BoolQuery.of(b -> b.must(titleMustList))._toQuery());
        shouldList.add(BoolQuery.of(b -> b.must(contentMustList))._toQuery());
        filterList.add(BoolQuery.of(b -> b.should(shouldList).minimumShouldMatch("1"))._toQuery());
        return BoolQuery.of(f -> f.filter(filterList))._toQuery();
    }

    /**
     * 监听 Kafka 消息：接收 'status-topic' 的消息，并将数据写入 Elasticsearch。
     * * @param statusJsonMessage Kafka 接收到的 JSON 字符串消息
     */
    @KafkaListener(topics = "status-created-topic", groupId = "status-index-group")
    public void consumeAndIndexStatus(String statusJsonMessage) {
        StatusIndexDocument statusDocument = null;
        try {
            // 1. 反序列化：将 JSON 字符串转换为 Java 对象
            statusDocument = objectMapper.readValue(statusJsonMessage, StatusIndexDocument.class);

            if (statusDocument.getId() == null) {
                log.warn("Received status message is incomplete. Skipping: {}", statusJsonMessage);
                return;
            }

            // 2. 索引数据：将 Java 对象索引到 Elasticsearch
            IndexResponse response = elasticComponent.insert(INDEX_NAME,statusDocument,
                    String.valueOf(statusDocument.getId()));

            log.info("Indexed status ID: {} to ES. Result: {}",
                    statusDocument.getId(), response.result());

        } catch (Exception e) {
            // 3. 错误处理：记录错误，并继续处理下一条消息 (避免 Consumer 挂起)
            log.error("Failed to process Kafka message or index to ES. Message: {}", statusJsonMessage, e);
        }
    }

}
