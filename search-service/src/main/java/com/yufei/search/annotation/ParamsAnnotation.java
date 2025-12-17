package com.yufei.search.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author wangzhenqing
 * @date 2024/01/04 16:40
 * @description
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ParamsAnnotation {

    boolean notNull() default false;

    /**
     * 属性最小值
     *
     * @return
     */
    int min() default 0;

    /**
     * 属性最大值
     *
     * @return
     */
    int max() default 0;

    /**
     * 属性值最大长度，针对String
     *
     * @return
     */
    int length() default 0;

    /**
     * 排序字段，默认值为time
     *
     * @return
     */
    String defaultValue() default "";

    String name() default "";

}
