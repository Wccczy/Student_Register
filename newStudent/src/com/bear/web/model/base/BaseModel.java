package com.bear.web.model.base;

import com.jfinal.plugin.activerecord.Model;

import java.io.Serializable;

/**
 * Created by Bear on 2015/3/21.
 */
public abstract class BaseModel<M extends Model> extends Model<M> implements Serializable {

}
