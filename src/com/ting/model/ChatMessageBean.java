package com.ting.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ChatMessageBean implements Serializable{
	private static final long serialVersionUID = 1L;
	private int state;
	private long createTime;
	private String formatTime;
	private Long receiver;
	private Long sender;
	private String content;

	// 重写比较方法，实现去重操作
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return this.sender.longValue() == ((ChatMessageBean) obj).sender.longValue()
				&& (this.receiver.longValue() == ((ChatMessageBean) obj).receiver.longValue());
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return sender.hashCode() + receiver.hashCode();
	}

	public int getState() {
		return state;
	}

	public ChatMessageBean setState(int state) {
		this.state = state;
		return this;
	}

	public long getCreateTime() {
		return createTime;
	}

	public ChatMessageBean setCreateTime(long createTime) {
		this.createTime = createTime;
		return this;
	}

	public long getReceiver() {
		return receiver;
	}

	public String getReceiverForLam() {
		return String.valueOf(getReceiver());
	}

	public ChatMessageBean setReceiver(long receiver) {
		this.receiver = receiver;
		return this;
	}

	public long getSender() {
		return sender;
	}

	public ChatMessageBean setSender(long sender) {
		this.sender = sender;
		return this;
	}

	public String getContent() {
		return content;
	}

	public ChatMessageBean setContent(String content) {
		this.content = content;
		return this;
	}

	public String getFormatTime() {
		return formatTime;
	}

	public ChatMessageBean setFormatTime(String formatTime) {
		this.formatTime = formatTime;
		return this;
	}

	public static ChatMessage copyToData(ChatMessageBean m) {
		ChatMessage c = new ChatMessage();
		c.set("sender", m.getSender());
		c.set("receiver", m.getReceiver());
		c.set("createTime", m.getCreateTime());
		c.set("state", m.getState());
		c.set("content", m.getContent());
		return c;
	}

	public static void main(String[] args) {
		ChatMessageBean m = new ChatMessageBean();
		m.setReceiver(1);
		m.setSender(8);
		m.setCreateTime(10);
		ChatMessageBean m2 = new ChatMessageBean();
		m2.setReceiver(2);
		m2.setSender(8);
		m2.setCreateTime(2);
		ChatMessageBean m3 = new ChatMessageBean();
		m3.setReceiver(1);
		m3.setSender(8);
		m3.setCreateTime(3);
		ChatMessageBean m4 = new ChatMessageBean();
		m4.setReceiver(3);
		m4.setSender(7);
		m4.setCreateTime(4);
		ChatMessageBean m5 = new ChatMessageBean();
		m5.setReceiver(1);
		m5.setSender(7);
		m5.setCreateTime(5);

		System.out.println(m.equals(m2));
		System.out.println(m.equals(m3));
		List<ChatMessageBean> list = new ArrayList<ChatMessageBean>();
		list.add(m);
		list.add(m2);
		list.add(m3);
		list.add(m4);
		list.add(m5);

		System.out.println(list.stream().map(x -> x.getCreateTime() + "").reduce(((x, y) -> {
			return (x + "," + y);
		})).get());

	}
}