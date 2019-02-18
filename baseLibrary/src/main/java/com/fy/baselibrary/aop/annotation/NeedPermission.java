package com.fy.baselibrary.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 定义一个注解 NeedPermission，用来注解方法，
 * 以便在编译期被编译器检测到需要做切面的方法
 * 注意：建议用在 activity，fragment，service
 * Created by fangs on 2018/8/27 15:32.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface NeedPermission {

    /**
     * 请求权限列表
     * @return
     */
    String[] value() default {};

    /**
     * 存在被拒绝的权限时，是否继续执行
     * @return 默认 false，不继续执行
     */
    boolean isRun() default false;

}
