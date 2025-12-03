package com.yufei.search.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;

/**
 * @author wangzhenqing
 * @date 2024-01-20 12:00:27
 * @description 小红书搜索
 */
@Data
@Getter
public class StatusQueryDto {

    @NotBlank(message = "查询参数不能为空！")
    private String query;
    @Max(value = 10)
    private int page;            //起始页
    @Max(value = 1000)
    @JsonProperty("page_size")
    private int size;            //每页个数
    @JsonProperty("trace_id")
    private String traceId;
    @JsonProperty("user_id")
    private String userId;

}
