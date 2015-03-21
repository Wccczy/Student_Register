package com.bear.web.model;

import com.bear.web.model.base.BaseModel;

/**
 * Created by Bear on 2015/3/21.
 */
public class ReClass extends BaseModel<ReClass> {
    public static final String TABLE_NAME = "Re_Class";
    public static final ReClass dao = new ReClass();

    //班级ID
    public static final String classID = "classID";
    //班级名字（专业名）
    public static final String className = "class_name";
    //班级人数
    public static final String classPeople = "class_people";
    //班级已注册人数
    public static final String classRePeople = "class_Repeople";
}
