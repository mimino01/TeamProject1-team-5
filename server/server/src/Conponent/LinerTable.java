package Conponent;

import java.util.Arrays;
import Conponent.*;

public class LinerTable {
	BoardLinkedList.LinkedList room;
	
	public LinerTable() {
	}
	
	public boolean createRoom(String host,String latitude, String longitude, String departureTime, String destination) {
		ChatRoom temp = new ChatRoom(host, destination, Float.parseFloat(departureTime), new Double[]{Double.parseDouble(latitude), Double.parseDouble(longitude)});
		room.insertNode(temp);
		return true;
	}
	
	public boolean addData(Option op, String data) {
		return room.addChat(op, data);
	}

	public String[][] getChatData (Option user) {
		if (room.searchNode(user) == null) {
			String[][] temp = new String[1][1];
			temp[0][0] = "user not included";
			return temp;
		} else {
			return room.searchNode(user).getChatLog();
		}
	}
}
