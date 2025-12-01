package com.yufei.status.config;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wangzhenqing
 * @date 2025/11/28 08:37
 * @description
 */
@Configuration
public class SnowflakeConfig {

    @Bean
    public Snowflake snowflake() {
        // datacenterId = 1, workerId = 1
        return IdUtil.getSnowflake(1, 1);
    }
}
