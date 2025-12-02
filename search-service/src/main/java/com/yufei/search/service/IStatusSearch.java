package com.yufei.search.service;

import com.yufei.search.dto.StatusQueryDto;

/**
 * @author wangzhenqing
 * @date 2025/12/02 11:53
 * @description
 */
public interface IStatusSearch {
    Object search(StatusQueryDto queryDto);
}
