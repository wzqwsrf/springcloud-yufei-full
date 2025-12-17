package com.yufei.search.aspect;

import com.alibaba.fastjson.JSON;
import com.yufei.search.utils.ParamsUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @ClassName StandardAop
 * @Description TODO
 * @Author wangliang
 * @Date 2023/9/13 19:20
 * @Version 1.0
 */
@Aspect
@Component
@Order(0)
@Slf4j
public class ParamsStandardAspect {

    @Pointcut("execution(* com.yufei.search.service.impl..*(..))")
    public void standardPointCut() {
    }

    @Before("standardPointCut()")
    public <T> void doAspect(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if (args.length > 0) {
            T dto = (T) args[0];
            ParamsUtils.validateDto(dto);
            log.info(JSON.toJSONString(dto));

//        Object arg = args[0];
//        SearchQueryDto request = (SearchQueryDto) arg;
//        String platMod = request.getPlatformMod();
//        if (!"P".equals(platMod) && !"P0".equals(platMod) &&  !"Abstract".equals(platMod)){
//            request.setPlatformMod("P");
//        }
        }

    }

}
