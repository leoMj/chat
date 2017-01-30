package com.ting.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.aop.Clear;
import com.jfinal.core.ActionKey;
import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.ting.model.ChatUser;
import com.ting.service.ChatUserService;

public class UserController extends Controller {
	@Clear
	public void register() {
		Map<String, Object> result = new HashMap<String, Object>();
		String email = getPara("email");
		String password = getPara("password"), repetition = getPara("repetition");
		String nickName = getPara("nickName");
		if (StrKit.isBlank(email) || !email.matches("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+")) {
			result.put("result", false);
			result.put("msg", "邮箱有误");
			renderJson(result);
			return;
		}
		if (!StrKit.notBlank(password, repetition) || !password.equals(repetition)) {
			result.put("result", false);
			result.put("msg", "密码有误(为空或者不一致)");
			renderJson(result);
			return;
		}
		if (StrKit.isBlank(nickName)) {
			result.put("result", false);
			result.put("msg", "昵称不能为空");
			renderJson(result);
			return;
		}
		if (ChatUser.dao.isExistEmail(email)) {
			result.put("result", false);
			result.put("msg", "邮箱已经存在");
			renderJson(result);
			return;
		}
		ChatUser cu = new ChatUser();
		cu.set("name", email).set("nickName", nickName).set("password", password).set("createTime",
				new Date().getTime());
		try {
			if (!cu.save()) {
				result.put("result", false);
				result.put("msg", "注册失败");
				renderJson(result);
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		result.put("result", true);
		getSession().setAttribute("u", ChatUser.dao.findUserByEmail(email));
		renderJson(result);
	}
	
	@Clear
	public void login() {
		String username = getPara("username");
		String password = getPara("password");
		Map<String, Object> result = new HashMap<String, Object>();
		// 验证用户名密码
		if (StrKit.isBlank(username) || StrKit.isBlank(password)) {
			result.put("result", false);
			result.put("msg", "账号和密码不能为空");
			renderJson(result);
			return;
		}

		ChatUser chatUser = ChatUser.dao.existUser(username, password);
		if (chatUser == null) {
			result.put("result", false);
			result.put("msg", "账号或者密码错误");
			renderJson(result);
			return;
		}
		if (ChatUserService.identity(chatUser.getLong("Id"))) {
			result.put("result", false);
			result.put("msg", "用户已经登录");
			renderJson(result);
			return;
		}
		result.put("result", true);
		getSession().setAttribute("u", chatUser);
		renderJson(result);
	}
	/**
	 * 获取所有用户，用于聊天主页面所有用户列表 异步访问
	 */
	public void users() {
		ChatUser cu = (ChatUser) getSession().getAttribute("u");
		List<ChatUser> uList = ChatUser.dao.queryAllUsers(cu.getLong("Id"));
		renderJson(uList);
	}

	/**
	 * 获取当前登陆用户的好友，异步访问
	 */
	public void friends() {
		Object u = getSession().getAttribute("u");
		List<ChatUser> fList = ChatUser.dao.queryFriends(((ChatUser) u).getStr("friends"));
		renderJson(fList);
	}
	/**
	 * 获取跟当前用户有消息交互的所有用户列表
	 */
	public void recentCaches() {
		ChatUser cu = (ChatUser) getSession().getAttribute("u");
		List<ChatUser> list = ChatUserService.recentInteractiveUser(cu.getLong("Id").longValue());
		renderJson(list);
	}
	/**
	 * 退出当前系统
	 */
	public void logout() {
		getSession().removeAttribute("u");
		getSession().invalidate();
		renderJsp("login.jsp");
	}
}