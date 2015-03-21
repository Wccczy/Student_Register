package com.bear.web.dao.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

import java.util.List;

/**
 * Created by Bear on 2015/3/21.
 */
public abstract class BaseDAO<T extends Model<T>> {

    /**
     * 增
     *
     * @param T
     * @return
     */
    public boolean save(T T) {
        return T.save();
    }

    /**
     * 删
     *
     * @param T
     * @return
     */
    public boolean delete(T T) {
        return T.delete();
    }

    /**
     * 改
     *
     * @param T
     * @return
     */
    public boolean update(T T) {
        return T.update();
    }

    /**
     * 根据ID查询一个
     *
     * @param id
     * @return
     */
    public abstract T findById(String id);

    /**
     * 查询全部
     *
     * @return
     */
    public abstract List<T> findAll();

    /**
     * 查询全部(分页查询)
     *
     * @return
     */
    public abstract Page<T> findAll(int currentPage, int pageSize);
}

