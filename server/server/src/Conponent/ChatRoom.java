package Conponent;

import java.util.Arrays;

public class ChatRoom {
	final int MAX_CHATTING_ROOM = 100;
	final int MAX_CHATTING_LOG = 10;
	final int MAX_USER = 4;
	String user[] = new String[MAX_USER];
	int roomId = -1;
	int lastChatNumber = 0;
	String[] chatInfo = new String[MAX_CHATTING_LOG];
	String[][] chatLog = new String[MAX_CHATTING_ROOM][MAX_CHATTING_LOG];
	
	public ChatRoom (int roomId) {
		this.roomId = roomId;
	}
	
	public ChatRoom () {
		
	}
	
	public boolean addUser(String id) {
		for (int i = 0; i < MAX_USER; i++) {
			if (user[i] == null) {
				user[i] = id;
				return true;
			}			
		}
		return false;
	}
	
	public boolean addChat(Option op, String chat) {
		chatInfo[0] = op.getId();
		chatInfo[1] = op.getName();
		chatInfo[2] = chat;
		chatLog[lastChatNumber] = chatInfo.clone();
		lastChatNumber++;
		return true;
	}
	
	//getter setter
	public int getRoomId() {
		return roomId;
	}
	
	public String[][] getChatLog () {
		return chatLog;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}
	
	public String[] getUser() {
		return user;
	}
}
