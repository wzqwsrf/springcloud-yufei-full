package com.yufei.status.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author wangzhenqing
 * @date 2025/11/27 16:15
 * @description
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StatusDto {

    private Long id;
    private Long userId;
    private String title;    // 原来的标题
    private String content;  // 原来的正文
    private Long likeCount;
    private Long createTime;

}
