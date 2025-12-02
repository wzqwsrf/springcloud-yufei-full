package com.yufei.search.component;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.yufei.search.dto.ResponseDto;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class ElasticComponent {

    @Resource
    private ElasticsearchClient searchClient;

    /**
     * es搜索
     *
     * @param searchRequest
     * @return
     */
//    @TimeAOP(name = "es search")
    public <T> ResponseDto<List<T>> search(SearchRequest searchRequest, Class<T> tClass) {
        ResponseDto<List<T>> responseDto = ResponseDto.successEmptyDto();
        long totalNum = 0;
        List<T> list = new ArrayList<>();
        try {
            SearchResponse<T> response = searchClient.search(searchRequest, tClass);
            if (null == response || null == response.hits() || response.hits().hits().size() < 1) {
                log.info("service search recall get empty from es");
            } else {
                totalNum = response.hits().total().value();
                log.info("es recall response size:{}", totalNum);
                List<Hit<T>> hitList = response.hits().hits();
                for (Hit<T> hit : hitList) {
                    list.add(hit.source());
                }
            }
        } catch (Exception e) {
            log.error("es search exception", e);
        }
        responseDto.setTotal(totalNum);
        responseDto.setData(list);
        return responseDto;
    }

}
