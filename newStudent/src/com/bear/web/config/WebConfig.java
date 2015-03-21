package com.bear.web.config;

import com.bear.web.model.AdminUser;
import com.bear.web.model.ReClass;
import com.bear.web.model.ReRMB;
import com.bear.web.model.ReStudent;
import com.jfinal.config.*;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.tx.TxByActionMethods;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.render.ViewType;

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
        loadPropertyFile("property_config.txt");
        DOMAIN = getProperty("domain");
        me.setDevMode(getPropertyToBoolean("devMode", true));
        me.setViewType(ViewType.JSP);
        me.setError404View("/error/404.jsp");
        me.setError500View("/error/500.jsp");
    }

    /**
     * 配置路由
     */
    @Override
    public void configRoute(Routes me) {
        //后台API

    }

    /**
     * 配置插件
     */
    @Override
    public void configPlugin(Plugins me) {
        c3p0Plugin = new C3p0Plugin(getProperty("jdbcUrl"), getProperty("user"), getProperty("password").trim());
        me.add(c3p0Plugin);

        ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0Plugin);
        arp.addMapping("re_student", ReStudent.class);
        arp.addMapping("re_class", ReClass.class);
        arp.addMapping("re_rmb", ReRMB.class);
        arp.addMapping("re_admin", AdminUser.class);
        me.add(arp);
    }

    /**
     * 配置全局拦截器
     */
    @Override
    public void configInterceptor(Interceptors me) {
        me.add(new TxByActionMethods("save", "update"));
    }

    /**
     * 配置处理器
     */
    @Override
    public void configHandler(Handlers me) {

    }
}
