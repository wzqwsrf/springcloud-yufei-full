package com.yufei.search.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

/**
 * @author wangzhenqing
 * @date 2024-07-05 17:17:42
 * @description
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class StatusResultDto {
    private String title;
    private String content;
    @JsonProperty("create_time")
    private String createTime;
    @JsonProperty("status_url")
    private String statusUrl;
    @JsonProperty("user_name")
    private String userName;
    @JsonProperty("user_id")
    private Long userId;
    @JsonProperty("status_id")
    private Long statusId;

}
