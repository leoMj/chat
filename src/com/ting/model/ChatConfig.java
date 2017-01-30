package com.ting.model;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.plugin.redis.RedisPlugin;
import com.jfinal.template.Engine;
import com.ting.controller.ChatController;
import com.ting.controller.IndexController;
import com.ting.controller.UserController;
import com.ting.interceptor.LoginInterceptor;
import com.ting.interceptor.WebSocketHandler;

public class ChatConfig extends JFinalConfig {

	@Override
	public void configConstant(Constants me) {
		PropKit.use("chat.properties");
		// me.setDevMode(true);
		// me.setViewType(ViewType.JSP);
	}

	@Override
	public void configRoute(Routes me) {
		me.add("/", IndexController.class, "/WEB-INF/view");
		me.add("/chat", ChatController.class, "/WEB-INF/view");
		me.add("/user", UserController.class, "/WEB-INF/view");
	}

	@Override
	public void configPlugin(Plugins me) {
		// TODO Auto-generated method stub
		// loadPropertyFile("WEB-INF/config/jdbc.properties");
		C3p0Plugin c3p0Plugin = new C3p0Plugin(PropKit.get("datasource.url"), PropKit.get("datasource.username"),
				PropKit.get("datasource.password"));
		me.add(c3p0Plugin);
		ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0Plugin);
		arp.setShowSql(true);
		me.add(arp);
		arp.addMapping("chat_user", "Id", ChatUser.class);
		arp.addMapping("chat_message", "Id", ChatMessage.class);
		me.add(new EhCachePlugin());
		RedisPlugin msgRedis = new RedisPlugin("message", "localhost");
		me.add(msgRedis);
	}

	@Override
	public void configInterceptor(Interceptors me) {
		// TODO Auto-generated method stub
		me.add(new LoginInterceptor());// 增加以后就是全局拦截器
	}

	@Override
	public void configHandler(Handlers me) {
		// TODO Auto-generated method stub
		// me.add(new ConstantPackingHandler());
		me.add(new WebSocketHandler("^/chatSocket"));
	}

	/**
	 * 在web容器启动的时候把项目的根目录放到全局的ApplicationContext中，方便页面使用${path}获取
	 */
	@Override
	public void afterJFinalStart() {
		JFinal.me().getServletContext().setAttribute("path", JFinal.me().getServletContext().getContextPath());
	}

	@Override
	public void beforeJFinalStop() {
		// TODO Auto-generated method stub
	}

	@Override
	public void configEngine(Engine arg0) {
		// TODO Auto-generated method stub
		
	}
}