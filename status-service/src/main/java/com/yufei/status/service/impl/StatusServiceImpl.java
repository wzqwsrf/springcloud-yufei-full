package com.yufei.status.service.impl;

import cn.hutool.core.lang.Snowflake;
import com.yufei.status.entity.StatusEntity;
import com.yufei.status.mapper.StatusMapper;
import com.yufei.status.domain.StatusDto;
import com.yufei.status.mq.StatusEventProducer;
import com.yufei.status.request.StatusCreateRequest;
import com.yufei.status.service.StatusService;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author wangzhenqing
 * @date 2025/11/27 16:17
 * @description
 */

@Service
public class StatusServiceImpl implements StatusService {

    @Resource
    private Snowflake snowflake;

    @Resource
    private StatusMapper statusMapper;
    @Resource
    private RedisTemplate<String, String> redisTemplate;
    @Resource
    private StatusEventProducer statusEventProducer;

    private static final String LIKE_KEY = "status:like:";

    @Override
    public Long createStatus(StatusCreateRequest request) {
        StatusEntity status = new StatusEntity();
        status.setUserId(request.getUserId());
        status.setTitle(request.getTitle());
        status.setContent(request.getContent());
        status.setId(snowflake.nextId());
        status.setLikeCount(0L);
        status.setCreateTime(System.currentTimeMillis());

        statusMapper.insert(status);

        StatusDto statusDto = new StatusDto();
        BeanUtils.copyProperties(status, statusDto);

        statusEventProducer.sendStatusCreatedEvent(statusDto);
        return status.getId();
    }

    @Override
    public StatusDto getStatus(Long id) {
        StatusEntity status = statusMapper.selectById(id);
        StatusDto statusDto = new StatusDto();
        BeanUtils.copyProperties(statusDto, status);
        return statusDto;
    }

    @Override
    public void likeStatus(Long id, Long userId) {
        String key = LIKE_KEY + id;
        Boolean added = redisTemplate.opsForSet().add(key, userId.toString()) > 0;
        if (Boolean.TRUE.equals(added)) {
//            statusMapper.increaseLike(id); // XML 中写 update like_count = like_count + 1
        }
    }
}

