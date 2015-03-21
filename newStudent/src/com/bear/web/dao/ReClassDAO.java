package com.bear.web.dao;

import com.bear.web.dao.base.BaseDAO;
import com.bear.web.model.ReClass;
import com.jfinal.plugin.activerecord.Page;

import java.util.List;

/**
 * Created by Bear on 2015/3/21.
 */
public class ReClassDAO extends BaseDAO<ReClass> {
    @Override
    public ReClass findById(String id) {
        return null;
    }

    @Override
    public List<ReClass> findAll() {
        return null;
    }

    @Override
    public Page<ReClass> findAll(int currentPage, int pageSize) {
        return null;
    }
}
