package com.yufei.search.es.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.analysis.TokenFilter;
import co.elastic.clients.elasticsearch._types.analysis.TokenFilterDefinition;
import co.elastic.clients.elasticsearch._types.analysis.Tokenizer;
import co.elastic.clients.elasticsearch.indices.AnalyzeRequest;
import co.elastic.clients.elasticsearch.indices.AnalyzeResponse;
import co.elastic.clients.elasticsearch.indices.analyze.AnalyzeToken;
import com.yufei.search.es.IEsIkTokenService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangzhenqing
 * @date 2024-01-24 16:32:50
 * @description
 */
@Slf4j
@Service
public class EsIkTokenServiceImpl implements IEsIkTokenService {

    private static final String IK_SMART_ANALYZER = "ik_smart";
    private static final String IK_MAX_WORD_ANALYZER = "ik_max_word";
    private static final String STANDARD_ANALYZER = "standard";

    private static final String JIEBA_INDEX = "jieba_analyze";
    private static final String JIEBA_SEARCH = "search_analyzer";
    private static final String JIEBA_CUSTOM_SEARCH = "custom_analyzer";

    @Resource
    private ElasticsearchClient searchProdClient;

    @Override
    public List<String> getIkSmartTokenList(String query) {
        return getIkTokenList(query, IK_SMART_ANALYZER);
    }

    @Override
    public List<String> getIkMaxWordTokenList(String query) {
        return getIkTokenList(query, IK_MAX_WORD_ANALYZER);
    }

    /**
     * @param query
     * @return
     */
    @Override
    public List<String> getIkSmartTokenListProd(String query) {
        return getIkSmartTokenListProd(query, IK_SMART_ANALYZER);
    }

    private List<String> getIkTokenList(String query, String analyzer) {
        // 创建 IK Analyzer 的 Settings
        List<String> list = new ArrayList<>();
        if (StringUtils.isBlank(query)){
            return list;
        }
        try {
            AnalyzeRequest request = new AnalyzeRequest.Builder().analyzer(analyzer).text(query).build();
            AnalyzeResponse response = searchProdClient.indices().analyze(request);
            for (AnalyzeToken token : response.tokens()) {
                list.add(token.token());
            }
        } catch (Exception e) {
            log.error("getIkSmartList exception", e);
        }
        return list;

    }

    private List<String> getIkSmartTokenListProd(String query, String analyzer) {
        // 创建 IK Analyzer 的 Settings
        List<String> list = new ArrayList<>();
        if (StringUtils.isBlank(query)){
            return list;
        }
        try {
            AnalyzeRequest request = new AnalyzeRequest.Builder().analyzer(analyzer).text(query).build();
            AnalyzeResponse response = searchProdClient.indices().analyze(request);
            for (AnalyzeToken token : response.tokens()) {
                list.add(token.token());
            }
        } catch (Exception e) {
            log.error("getIkSmartList exception", e);
        }
        return list;

    }

    public List<String> getTokensWithJieba(String query) {
        List<String> list = new ArrayList<>();
        if (StringUtils.isBlank(query)){
            return list;
        }
        try {
            AnalyzeRequest request =
                    new AnalyzeRequest.Builder().index(JIEBA_INDEX).analyzer(JIEBA_SEARCH).text(query).build();
            AnalyzeResponse response = searchProdClient.indices().analyze(request);
            for (AnalyzeToken token : response.tokens()) {
                if (!token.token().trim().isEmpty()) {
                    list.add(token.token());
                }
            }
        } catch (Exception e) {
            log.error("get jieba analyze exception", e);
        }
        return list;
    }

    public List<String> getTokensWithJiebaWithoutStop(String query) {
        List<String> list = new ArrayList<>();
        if (StringUtils.isBlank(query)){
            return list;
        }
        try {
            Tokenizer tokenizer = new Tokenizer.Builder().name("jieba_search").build();
            TokenFilterDefinition definition = new TokenFilterDefinition.Builder().stop(s -> s.stopwordsPath(
                    "stopwords/stopwords.txt")).build();
            TokenFilter filter = new TokenFilter.Builder().definition(definition).build();
            AnalyzeRequest request =
                    new AnalyzeRequest.Builder().tokenizer(tokenizer).filter(filter).text(query).build();
            AnalyzeResponse response = searchProdClient.indices().analyze(request);
            for (AnalyzeToken token : response.tokens()) {
                list.add(token.token());
            }
        } catch (Exception e) {
            log.error("get jieba analyze exception", e);
        }
        return list;
    }

    //批量分词
    public List<String> getTokensWithJiebaList(List<String> queryList) {
        List<String> list = new ArrayList<>();
        try {
            AnalyzeRequest request =
                    new AnalyzeRequest.Builder().index(JIEBA_INDEX).analyzer(JIEBA_SEARCH).text(queryList).build();
            AnalyzeResponse response = searchProdClient.indices().analyze(request);
            for (AnalyzeToken token : response.tokens()) {
                if (!token.token().trim().isEmpty()) {
                    list.add(token.token());
                }
            }
        } catch (Exception e) {
            log.error("get jieba analyze exception", e);
        }
        return list;
    }


    public List<String> getTokensWithJieba(String query, String index) {
        List<String> list = new ArrayList<>();
        if (StringUtils.isBlank(query)){
            return list;
        }
        try {
            AnalyzeRequest request =
                    new AnalyzeRequest.Builder().index(index).analyzer(JIEBA_CUSTOM_SEARCH).text(query).build();
            AnalyzeResponse response = searchProdClient.indices().analyze(request);
            for (AnalyzeToken token : response.tokens()) {
                if (!token.token().trim().isEmpty()) {
                    list.add(token.token());
                }
            }
        } catch (Exception e) {
            log.error("get jieba analyze exception", e);
            //如果报错就使用默认的index分词器
            list = getTokensWithJieba(query);
        }
        return list;
    }

    @Override
    public List<String> getStandardTokenList(String query) {
        return getIkTokenList(query, STANDARD_ANALYZER);
    }
}
