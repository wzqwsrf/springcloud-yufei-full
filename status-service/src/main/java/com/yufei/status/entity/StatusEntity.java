package com.yufei.status.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author wangzhenqing
 * @date 2025/12/01 18:22
 * @description
 */

@TableName("status")
@Data
public class StatusEntity {
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long userId;
    private String title;    // 原来的标题
    private String content;  // 原来的正文
    private Long likeCount;
    private Long createTime;
}