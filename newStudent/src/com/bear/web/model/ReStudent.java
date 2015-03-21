package com.bear.web.model;

import com.bear.web.model.base.BaseModel;

/**
 * Created by Bear on 2015/3/21.
 */
public class ReStudent extends BaseModel<ReStudent> {
    public static final String TABLE_NAME = "Re_Student";
    public static final ReStudent dao = new ReStudent();

    //id
    public static final String ID = "id";
    //UUID
    public static final String UUID = "uuid";
    //学生名字
    public static final String NAME = "Sname";
    //学生籍贯
    public static final String NA_PLACE = "na_place";
    //学生家庭住址
    public static final String ADDRESS = "address";
    //学生手机
    public static final String TEL = "tel";
    //学生父亲名字
    public static final String Father = "p_name";
    //学生母亲名字
    public static final String Mother = "m_name";
    //学生监护人电话
    public static final String FTEL = "f_tel";
    //学生第一次到校注册时间
    public static final String REGTIME = "reg_time";
    //是否有注册
    public static final String ISREG = "isReg";
    //学生身高
    public static final String HEIGHT = "height";
    //学生班级ID
    public static final String classID = "classID";
    //学生专业
    public static final String PRO = "pro";
    //学生交钱RMB标识
    public static final String RMBID = "rmbID";
}
