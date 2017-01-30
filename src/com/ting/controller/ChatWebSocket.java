package com.ting.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.alibaba.fastjson.JSON;
import com.ting.model.ChatMessageBean;
import com.ting.service.ChatMessageService;;

@ServerEndpoint(value = "/chatSocket/{uid}")
public class ChatWebSocket {
	private static int onlineCount = 0;
	private long userId;
	public final static CopyOnWriteArraySet<ChatWebSocket> chatSocketSet = new CopyOnWriteArraySet<ChatWebSocket>();
	// 与某个客户端的连接会话，需要通过它来给客户端发送数据
	private Session session;

	@OnOpen
	public void onOpen(@PathParam("uid") long uid, Session session) {
		this.session = session;
		this.setUserId(uid);
		chatSocketSet.add(this);
		addOnlineCount();
		System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());

		// Utils.setCache("message", this.getUserId() + "-" + receiver,
		// msgBean);
	}

	@OnMessage
	public void onMessage(String message, Session session) {
		System.out.println("来自客户端的消息:" + message);
		String receiver = message.substring(1, message.indexOf(","));
		String msg = message.substring(message.indexOf(",") + 1, message.length() - 1);
		ChatMessageBean msgBean = new ChatMessageBean();
		msgBean.setContent(msg).setSender(this.getUserId()).setReceiver(Long.valueOf(receiver))
				.setCreateTime(new Date().getTime());
		// 当前在线的用户中是否有消息发送的对象用户
		List<ChatWebSocket> listCahtSocket = chatSocketSet.parallelStream()
				.filter((x) -> x.getUserId() == Long.valueOf(receiver)).collect(Collectors.toList());
		// 如果当前消息接收对象不在线，则把消息状态设置成未读，否则设置成已读
		if (listCahtSocket.size() == 0)
			msgBean.setState(0);
		else
			msgBean.setState(1);
		ChatMessageService.saveChatMessage("message", msgBean.getSender() + "", msgBean);
		for (ChatWebSocket c : listCahtSocket) {
			try {
				c.sendMessage(JSON.toJSONString(msgBean));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@OnError
	public void onError(Session session, Throwable error) {
		System.out.println("发生错误");
		error.printStackTrace();
	}

	public void sendMessage(String message) throws IOException {
		this.session.getBasicRemote().sendText(message);
		// this.session.getAsyncRemote().sendText(message);
	}

	@OnClose
	public void onClose() {
		chatSocketSet.remove(this); // 从set中删除
		subOnlineCount(); // 在线数减1
		System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
	}

	public static synchronized void addOnlineCount() {
		ChatWebSocket.onlineCount++;
	}

	public static synchronized int getOnlineCount() {
		return onlineCount;
	}

	public static synchronized void subOnlineCount() {
		ChatWebSocket.onlineCount--;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
}
