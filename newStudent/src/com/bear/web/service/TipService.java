package com.bear.web.service;


import com.bear.web.controller.base.BaseController;

import java.util.HashMap;
import java.util.Map;

public class TipService {
    private static Map<String, String> tip;

    public static void initMap() {
        tip = new HashMap<String, String>();
        tip.put("发表内容含有敏感信息", "Published content containing sensitive information!");
        tip.put("非法请求，参数错误", "Illegal request parameter error!");
        tip.put("Method使用错误，请查看API", "Method Using the wrong, see API!");
        tip.put("未找到访问地址", "Access address not found!");
        tip.put("访问成功", "Visit success!");
        tip.put("不存在该用户", "The user does not exist!");
        tip.put("参数错误", "Parameter error!");
        tip.put("发送成功", "Sent successfully!");
        tip.put("该用户已经屏蔽了你", "This user has blocked you!");
        tip.put("登陆失败，内部原因", "Login failed internal reasons!");
        tip.put("该邮箱已被注册", "The mailbox has been registered!");
        tip.put("删除失败！ID不存在", "Delete Failed!The photoid doesn't exist!");
        tip.put("删除失败！UUID,WORD_ID不存在", "Delete Failed!The uuid and word_id doesn't exist!");
        tip.put("保存失败！内部原因", "Save Failed!Server internal error!");
        tip.put("更新好友关系失败", "Failed to update relationship!");
        tip.put("今天你已经点赞过", "You already praise today!");
        tip.put("密码长度必须大于6位", "Password length must be greater than 6!");
        tip.put("密码不正确", "invalid password!");
        tip.put("用户已被封锁，请与我们联系", "user have been blocked , please contact us!");
    }

    public static String getKey(String key, BaseController base) {
        String lang = base.getPara("lang");
        if ("en".equals(lang)) {
            return tip.get(key);
        } else {
            return key;
        }
    }
}
