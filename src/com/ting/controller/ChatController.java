package com.ting.controller;

import java.util.List;

import com.jfinal.core.Controller;
import com.ting.model.ChatMessage;
import com.ting.model.ChatMessageBean;
import com.ting.service.ChatMessageService;

public class ChatController extends Controller{
	public void getMessageFromData() {
		String sender = getPara(0);
		List<ChatMessage> list = ChatMessage.dao.find("select *from chat_message where sender=? order by createTime",
				sender);
		renderJson(list);
	}
	/**
	 * 获取与制定用户的交互消息
	 */
	public void caches() {
		String sender = getPara(0), receiver = getPara(1);
		List<ChatMessageBean> list = ChatMessageService.interactiveChatMesage("message", sender, receiver);
		if (null != list)
			list.sort((x, y) -> {
				return x.getCreateTime() > y.getCreateTime() ? 1 : -1;
			});
		renderJson(list);
	}
}
