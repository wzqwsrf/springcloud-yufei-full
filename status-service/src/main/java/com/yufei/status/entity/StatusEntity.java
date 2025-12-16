package com.yufei.status.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 状态实体类
 * 对应数据库表：status
 * @author wangzhenqing
 * @date 2025/12/01 18:22
 * @description
 */

@TableName("status")
@Data
public class StatusEntity {
    /**
     * 主键ID，自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    @TableField("user_id")
    @JsonProperty("user_id")
    private Long userId;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 点赞数
     */
    @TableField("retweet_count")
    @JsonProperty("retweet_count")
    private Long retweetCount;

    /**
     * 点赞数
     */
    @TableField("fav_count")
    @JsonProperty("fav_count")
    private Long favCount;

    /**
     * 点赞数
     */
    @TableField("like_count")
    @JsonProperty("like_count")
    private Long likeCount;

    /**
     * 点赞数
     */
    @TableField("reply_count")
    @JsonProperty("reply_count")
    private Long replyCount;

    /**
     * 创建时间（时间戳）
     */
    @TableField("created_at")
    @JsonProperty("created_at")
    private Long createdAt;

    /**
     * 更新时间（时间戳）
     */
    @TableField(value = "updated_at")
    @JsonProperty("updated_at")
    private Long updatedAt;

    /**
     * 逻辑删除标记（0：未删除，1：已删除）
     */
    @TableLogic
    @TableField("is_deleted")
    private Integer isDeleted;

    /**
     * 版本号（乐观锁）
     */
    @Version
    @TableField("version")
    private Integer version;
}