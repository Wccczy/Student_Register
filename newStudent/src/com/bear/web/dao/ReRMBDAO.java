package com.bear.web.dao;

import com.bear.web.dao.base.BaseDAO;
import com.bear.web.model.ReRMB;
import com.jfinal.plugin.activerecord.Page;

import java.util.List;

/**
 * Created by Bear on 2015/3/21.
 */
public class ReRMBDAO extends BaseDAO<ReRMB> {
    @Override
    public ReRMB findById(String id) {
        return null;
    }

    @Override
    public List<ReRMB> findAll() {
        return null;
    }

    @Override
    public Page<ReRMB> findAll(int currentPage, int pageSize) {
        return null;
    }
}
