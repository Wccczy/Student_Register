package com.bear.web.model;

import com.bear.web.model.base.BaseModel;

/**
 * Created by VIJAY-YAN on 2014/8/21.
 */
public class AdminUser extends BaseModel<AdminUser> {
    public static final String TABLE_NAME = "re_admin";
    public static final AdminUser dao = new AdminUser();

    public static final String ID = "id";
    public static final String USRENAME = "username";
    public static final String PASSWORD = "password";
    public static final String CREATE_TIME = "create_time";
    public static final String UPDATE = "create_time";
}
