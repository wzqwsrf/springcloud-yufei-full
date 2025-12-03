package com.yufei.search.dto;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.util.List;

/**
 * @ClassName SearchResult
 * @Description data
 * @Author wangliang
 * @Date 2023/9/14 16:55
 * @Version 1.0
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(alphabetic = true)
public class StatusSearchResult<T> {
    private String ver;
    @JsonProperty("total_num")
    private long totalNum;
    @JsonProperty("datas")
    private List<T> dataList;
    @JsonProperty("esDsl")
    private JSONObject esDSL;
    @JsonProperty("sql")
    private String sql;
}
