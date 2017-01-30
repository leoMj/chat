package com.ting.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

public class LoginInterceptor implements Interceptor {
	/**
	 * À¹½ØµÇÂ½
	 */
	@Override
	public void intercept(Invocation inv) {
		// TODO Auto-generated method stub
		Object username = inv.getController().getSession().getAttribute("u");
		if (username == null) {
			inv.getController().forwardAction("/login");
			return;
		}
		inv.invoke();
	}

}