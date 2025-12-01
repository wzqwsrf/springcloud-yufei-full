package com.yufei.status.service;

import com.yufei.status.domain.StatusDto;
import com.yufei.status.entity.StatusEntity;
import com.yufei.status.request.StatusCreateRequest;

/**
 * @author wangzhenqing
 * @date 2025/11/27 16:17
 * @description
 */
public interface StatusService {
    Long createStatus(StatusCreateRequest request);
    StatusDto getStatus(Long id);
    void likeStatus(Long id, Long userId);
}
