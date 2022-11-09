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
	public String host;
	public int numberOfUser;
	public String destination;
	public float departureTime;
	public Double[] coordinate = new Double[2];


	public ChatRoom (int roomId) {
		this.roomId = roomId;
	}
	
	public ChatRoom () {
		
	}


	public ChatRoom(String host, String destination, float departureTime, Double[] coordinate) {
		this.host = host;
		this.destination = destination;
		this.departureTime = departureTime;
		this.coordinate = coordinate;
	}

	public ChatRoom (String host) {
		this.host = host;
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

	public boolean findUser(Option op) {
		for (int i = 0; i < lastChatNumber; i++) {
			if (user[i].equals(op.getName())) {
				return true;
			}
		}
		return false;
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

	public String getHost() {
		return host;
	}
}
