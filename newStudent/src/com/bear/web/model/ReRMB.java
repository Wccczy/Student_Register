package com.bear.web.model;

import com.bear.web.model.base.BaseModel;

/**
 * Created by Bear on 2015/3/21.
 */
public class ReRMB extends BaseModel<ReRMB> {
    public static final String TABLE_NAME = "Re_RMB";
    public static final ReRMB dao = new ReRMB();

    //人民币ID
    public static final String RMBID = "rembID";
    //人民币面值
    public static final String RMBVALUE = "RMBvalue";
    //人民币标识码s
    public static final String RMBCODE = "rmbcode";
}
