package Conponent;

import java.util.Arrays;

public class ChatRoom {
	final int MAX_CHATTING_ROOM = 100;
	final int MAX_CHATTING_LOG = 10;
	final int MAX_USER = 4;
	String user[] = new String[MAX_USER];
	private int roomId = -1;
	private int lastChatNumber = 0;
	private String[] chatInfo = new String[MAX_CHATTING_LOG];
	private String[][] chatLog = new String[MAX_CHATTING_ROOM][MAX_CHATTING_LOG];
	private String host;
	private int numberOfUser;
	private String hostGender;
	private String destination;
	private int departureTime;
	private Double[] coordinate = new Double[2];
	private float hostRank;
	private int lastPos = 0;

	private int createTime;
	
	public ChatRoom () {
		
	}


	public ChatRoom(String host, String destination, int departureTime, Double[] coordinate, int createTime, float hostRank) {

		this.host = host;
		this.user[0] = host;
		this.hostRank = hostRank;
		numberOfUser++;
		this.destination = destination;
		this.departureTime = departureTime;
		this.coordinate = coordinate;
		this.createTime = createTime;
		this.hostGender = hostGender;
	}

	public ChatRoom (String host) {
		this.host = host;
	}
	
	public boolean addUser(String id, String name) {
		for (int i = 0; i < MAX_USER; i++) {
			if (user[i] == null) {
				user[i] = name;
				numberOfUser++;
				return true;
			}			
		}
		return false;
	}
	
	public boolean addChat(Option op, String chat) {
		chatInfo[0] = op.getId();
		chatInfo[1] = op.getName();
		chatInfo[2] = chat;
		chatInfo[3] = String.valueOf(lastPos);
		chatLog[lastChatNumber] = chatInfo.clone();
		lastChatNumber++;
		lastPos++;
		return true;
	}

	public boolean findUser(Option op) {
//		System.out.println("findUser parameter : " + op.toString());
		for (int i = 0; i < numberOfUser; i++) {
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

	public float getHostRank() {
		return hostRank;
	}

	public void setHostRank(float hostRank) {
		this.hostRank = hostRank;
	}

	@Override
	public String toString() {
		return "ChatRoom{" +
				"user=" + Arrays.toString(user) +
				", roomId=" + roomId +
				", lastChatNumber=" + lastChatNumber +
				", chatInfo=" + Arrays.toString(chatInfo) +
				", chatLog=" + Arrays.deepToString(chatLog) +
				", host='" + host + '\'' +
				", numberOfUser=" + numberOfUser +
				", destination='" + destination + '\'' +
				", departureTime=" + departureTime +
				", coordinate=" + Arrays.toString(coordinate) +
				", createTime=" + createTime +
				'}';
	}

	public String[] toArray() {
		String[] result = new String[]{Arrays.toString(user), Integer.toString(roomId), Integer.toString(lastChatNumber), Arrays.toString(chatInfo), Arrays.deepToString(chatLog), host, Integer.toString(numberOfUser), destination, Float.toString(departureTime), Arrays.toString(coordinate), Integer.toString(createTime)};

		return result;
	}

	public String[] toArrayByDetailInfo() {
		String[] result = new String[]{host, destination, String.valueOf(hostRank), String.valueOf(createTime), String.valueOf(departureTime), String.valueOf(coordinate[0]), String.valueOf(coordinate[1])};

		return result;
	}

	public String[] infoToArray() {
		String[] result = new String[]{host, destination, String.valueOf(hostRank), String.valueOf(createTime), String.valueOf(departureTime)};

		return result;
	}
}
