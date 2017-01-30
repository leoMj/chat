package com.ting.controller;

import java.util.List;

import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.ting.model.ChatUser;
import com.ting.service.ChatUserService;

public class IndexController extends Controller {
	@Clear
	public void register() {
		if (null != getSession().getAttribute("u")) {
			index();
		}
		renderJsp("register.jsp");
	}

	@Clear
	public void login() {
		if (null != getSession().getAttribute("u")) {
			index();
		}
		renderJsp("login.jsp");
	}

	public void index() {
		ChatUser cu = (ChatUser) getSession().getAttribute("u");
		List<ChatUser> list = ChatUserService.recentInteractiveUser(cu.getLong("Id").longValue());
		getRequest().setAttribute("recentInteractive", list);
		renderJsp("chat.jsp");
	}
}
