package Conponent;

import java.util.Arrays;
import Conponent.*;

public class LinerTable {
	ChatRoom[] room;
	HashTableIntString hash = new HashTableIntString(40);
	
	public LinerTable(int size) {
		room = new ChatRoom[size];
		for (int i = 0; i < size; i++) {
			room[i] = new ChatRoom();
		}
	}
	
	public boolean createRoom(String id) {
		for (int i = 0; i < room.length; i++) {
			if (room[i].getRoomId() == -1) {
				room[i] = new ChatRoom(i);
				room[i].addUser(id);
				hash.put(id, i);
				return true;
			}
		}		
		return false;
	}
	
	public boolean addData(int roomId, Option op, String data) {
		hash.put(op.getId(), roomId);
		return room[roomId].addChat(op, data);
	}
	
	public int findRoomNumberByUserId(String id) {
		return hash.get(id);
	}
	
	public String[][] getChatData (int roomId) {
		if (roomId == -1) {
			String[][] error = new String[1][1];
			error[0][0] = Boolean.toString(false);
			return error;
		}
		return room[roomId].getChatLog();		
	}
}
