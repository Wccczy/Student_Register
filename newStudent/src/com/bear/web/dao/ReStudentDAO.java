package com.bear.web.dao;

import com.bear.web.dao.base.BaseDAO;
import com.bear.web.model.ReStudent;
import com.jfinal.plugin.activerecord.Page;

import java.util.List;

/**
 * Created by Bear on 2015/3/21.
 */
public class ReStudentDAO extends BaseDAO<ReStudent> {
    @Override
    public ReStudent findById(String id) {
        return null;
    }

    @Override
    public List<ReStudent> findAll() {
        return null;
    }

    @Override
    public Page<ReStudent> findAll(int currentPage, int pageSize) {
        return null;
    }
}
