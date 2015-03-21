package com.bear.web.service;


import com.bear.web.dao.AdminUserDAO;
import com.bear.web.model.AdminUser;
import com.bear.web.service.base.BaseService;
import com.jfinal.plugin.activerecord.Page;

/**
 * Created by VIJAY-YAN on 2014/8/21.
 */
public class AdminUserService extends BaseService {
	private AdminUserDAO adminUserDAO = new AdminUserDAO();

	public AdminUser login(String username, String password) {
		return adminUserDAO.findByUsernameAndPassword(username, password);
	}

	public Page<AdminUser> getList(int page, int pageSize) {
		return adminUserDAO.findAll(page, pageSize);
	}

	public boolean delete(int id) {
		AdminUser user = new AdminUser();
		user.set("id", id);
		return adminUserDAO.delete(user);
	}

	public AdminUser findById(int id) {
		return adminUserDAO.findById(String.valueOf(id));
	}

	public boolean saveOrUpdate(String password, String username, String role, int id) {
		if (id == -1) {
			AdminUser user = new AdminUser();
			user.set("username", username);
			user.set("password", password);
			user.set("role", role);
			return adminUserDAO.save(user);
		} else {
			AdminUser user = adminUserDAO.findById(String.valueOf(id));
			user.set("username", username);
			user.set("password", password);
			user.set("role", role);
			return adminUserDAO.update(user);
		}
	}

}
