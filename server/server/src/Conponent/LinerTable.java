package Conponent;

public class LinerTable {
	ChatRoom[] room;
	
	public LinerTable(int size) {
		room = new ChatRoom[size];
	}
	
	public boolean createRoom(String id) {
		for (int i = 0; i < room.length; i++) {
			if (this.findRoomNumberByRoomId(i) == -1) {
				room[i].CreateChatRoom(i);
				room[i].addUser(id);
				return true;
			}
		}		
		return false;
	}
	
	public boolean addData(int roomId, Option op, String data) {
		return room[roomId].addChat(op, data);
	}
	
	public int findRoomNumberByUserId(String id) {
		for (int i = 0; i < room.length; i++) {
			if (room[i].isThere(id)) {
				return i;
			}
		}
		return -1;
	}
	
	public int findRoomNumberByRoomId(int id) {
		for (int i = 0; i < room.length; i++) {
			if (room[i].getRoomId() == id) {
				return i;
			}
		}
		return -1;
	}
	
	public String[][] getChatData (int roomId) {
		return room[roomId].getChatLog();		
	}
}