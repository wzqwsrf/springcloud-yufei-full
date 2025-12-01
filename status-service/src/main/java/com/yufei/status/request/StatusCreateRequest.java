package com.yufei.status.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author wangzhenqing
 * @date 2025/11/27 16:21
 * @description
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StatusCreateRequest {
    private Long userId;
    private String title;
    private String content;
}
