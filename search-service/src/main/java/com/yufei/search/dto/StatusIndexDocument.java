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
    /**
     * 点赞数
     */
    @JsonProperty("retweet_count")
    private Long retweetCount;

    /**
     * 点赞数
     */
    @JsonProperty("fav_count")
    private Long favCount;

    /**
     * 点赞数
     */
    @JsonProperty("like_count")
    private Long likeCount;

    /**
     * 点赞数
     */
    @JsonProperty("reply_count")
    private Long replyCount;
    @JsonProperty("created_at")
    private Long createdAt;
    @JsonProperty("updated_at")
    private Long updatedAt;

}
