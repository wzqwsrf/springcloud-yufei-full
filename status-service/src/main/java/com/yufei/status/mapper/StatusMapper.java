package com.yufei.status.mapper;

/**
 * @author wangzhenqing
 * @date 2025/11/28 09:07
 * @description
 */
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yufei.status.entity.StatusEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface StatusMapper extends BaseMapper<StatusEntity> {
    
    /**
     * 增加点赞数
     * @param id 状态ID
     * @return 影响行数
     */
    @Update("UPDATE status SET like_count = like_count + 1 WHERE id = #{id}")
    int increaseLike(@Param("id") Long id);
}