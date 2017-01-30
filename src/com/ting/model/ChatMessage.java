package com.ting.model;

import java.util.Date;
import java.util.List;
import com.jfinal.plugin.activerecord.Model;

public class ChatMessage extends Model<ChatMessage> {
	public final static ChatMessage dao = new ChatMessage();

	public List<ChatMessage> getChatContentByUserName(String name) {
		List<ChatMessage> list = dao.find(
				"select chat_content from chat_content cc,chat_user cu where (cc.owner_id=cu.id or cc.target_id=cu.id) and cu.name=?",
				name);
		return list;
	}

	public static void main(String[] args) {
		System.out.println(new Date().getTime());
	}
}
