package com.bear.web.dao;


import com.bear.web.dao.base.BaseDAO;
import com.bear.web.model.AdminUser;
import com.jfinal.plugin.activerecord.Page;

import java.util.List;

/**
 * Created by VIJAY-YAN on 2014/8/21.
 */
public class AdminUserDAO extends BaseDAO<AdminUser> {

    public AdminUser findByUsernameAndPassword(String username, String password) {
        return AdminUser.dao.findFirst("select * from md_admin_user where username=? and password=?", username, password);
    }

    /**
     * 根据ID查询一个
     *
     * @param id
     * @return
     */
    public AdminUser findById(String id) {
        return AdminUser.dao.findFirst("select * from md_admin_user where id=? ", id);
    }

    /**
     * 查询全部
     *
     * @return
     */
    public List<AdminUser> findAll() {
        return null;
    }

    /**
     * 查询全部(分页查询)
     *
     * @return
     */
    public Page<AdminUser> findAll(int currentPage, int pageSize) {
        return AdminUser.dao.paginate(currentPage, pageSize, "select *", "from md_admin_user order by id asc");
    }
}
