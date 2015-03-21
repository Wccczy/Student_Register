package com.bear.web.config;

import com.jfinal.config.*;
import com.jfinal.plugin.c3p0.C3p0Plugin;

/**
 * Created by Bear on 2015/3/15.
 */

/**
 * API引导式配置
 */
public class WebConfig extends JFinalConfig {
    public static String DOMAIN;
    public static String MAIL_SMTP;
    public static String MAIL_POP;
    public static String MAIL_ACCOUNT;
    public static String MAIL_PASSWORD;
    private C3p0Plugin c3p0Plugin;

    /**
     * 配置常量
     */
    @Override
    public void configConstant(Constants me) {

    }

    /**
     * 配置路由
     */
    @Override
    public void configRoute(Routes me) {

    }

    /**
     * 配置插件
     */
    @Override
    public void configPlugin(Plugins me) {

    }

    /**
     * 配置全局拦截器
     */
    @Override
    public void configInterceptor(Interceptors me) {

    }

    /**
     * 配置处理器
     */
    @Override
    public void configHandler(Handlers me) {

    }
}
