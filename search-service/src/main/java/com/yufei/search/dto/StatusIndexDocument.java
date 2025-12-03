package com.yufei.search.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.Getter;

/**
 * @author wangzhenqing
 * @date 2023/12/11 16:12
 * @description
 */
@Data
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class StatusIndexDocument {

    private Long id;
    @JsonProperty("user_id")
    private Long userId;
    private String title;
    private String content;
    @JsonProperty("share_count")
    private long shareCount;
    @JsonProperty("comment_count")
    private long commentCount;
    @JsonProperty("liked_count")
    private long likedCount;
    @JsonProperty("collected_count")
    private long collectedCount;
    @JsonProperty("create_time")
    private String createTime;

}
