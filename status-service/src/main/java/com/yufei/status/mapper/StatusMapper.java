package com.yufei.status.mapper;

/**
 * @author wangzhenqing
 * @date 2025/11/28 09:07
 * @description
 */
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yufei.status.entity.StatusEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StatusMapper extends BaseMapper<StatusEntity> {
}