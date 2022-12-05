package socket;

import Conponent.*;

import java.util.Arrays;

public class Saver {
	static HashTable hash = new HashTable(40);
	static BoardLinkedList.LinkedList room = new BoardLinkedList.LinkedList();
	static Option list = new Option();
	
	public Saver() {
		hash.put("adminid", new Option("관리자",01012341234,"adminid","adminpw","man"));
		hash.put("subadminid", new Option("보조관리자",01012344321,"subadminid","subadminpw","man"));
		hash.put("parkid", new Option("박휘건", 01010044321, "parkid", "parkhgpw","man"));
		hash.put("hongid", new Option("홍길동", 01010043421, "hongid", "hongpw","man"));
		hash.put("ganadaid", new Option("가나다", 01012044321, "ganadaid", "ganadapw","woman"));
		room.insertNode(new ChatRoom("박휘건", "기흥역", 930, new Double[]{37.22344259294581, 127.18734526333768}, 900, 5));
		room.insertNode(new ChatRoom("홍길동", "영통역", 830, new Double[]{37.224755790256964, 127.18881331477333}, 920, 4));
		room.insertNode(new ChatRoom("가나다", "명지대역", 1000, new Double[]{37.22219444666843, 127.19029421815819}, 930, 4));
	}

	public static boolean signup(Object obj) {
		String[] user = (String[]) obj;
		list = new Option(user[1],Long.parseLong(user[2]),user[3],user[4],user[5]);
		try {			
			hash.put(list.getId(),list);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean signin(Object obj) {
		String[] user = (String[]) obj;
		try {
			if (user[2].equals(hash.get(user[1]).getPassword())) {
				return true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public static boolean signOut(Object obj) {
		String[] user = (String[]) obj;
		try {
			hash.delete(user[1]);
			if (hash.get(user[1]).getId().equals("data not include")) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean update(Object obj) {
		String[] user = (String[]) obj;
		list = new Option(user[1],Long.parseLong(user[2]),user[3],user[4],user[5]);
		try {
			if (hash.get(list.getId()) == null) {
				return false;
			}
			hash.put(user[3], list);
//			System.out.println("Saver.update - updated user info and res data : " + hash.get(user[3]).toString() + list.toString() + hash.get(user[3]).equals(list));
			if (hash.get(user[3]).equals(list)) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static String[] reqUserdata(Object obj) {
		String[] user = (String[]) obj;
		return hash.get(user[1]).toStringArray();
	}
	
	public static String[][] chatting(Object obj) {
		String[][] result = new String[10][10];
		String[] data = (String[]) obj;
		String key = data[1];
		ChatRoom temp;
		switch (key) {
		case "create":
			System.out.println("processing chat in create");
			temp = new ChatRoom(hash.get(data[2]).getName(),data[6],Float.parseFloat(data[5]),new Double[]{Double.parseDouble(data[3]),Double.parseDouble(data[4])}, Integer.parseInt(data[7]), hash.get(data[2]).getRank());
			if (room.insertNode(temp)) {
				result[0][0] = Boolean.toString(true);
				return result;
			}
			break;

		case "addUser":
			if (hash.get(data[2]).equals(hash.get("dfasfsadvasdvevsdfver"))) {
				temp = room.searchNode(hash.getByName(data[2]));
				boolean response = temp.addUser(data[3], hash.get(data[3]).getName());
				room.insertNode(temp);
				result[0][0] = Boolean.toString(response);
				return result;
			} else {
				temp = room.searchNode(hash.get(data[2]));
				boolean response = temp.addUser(data[3], hash.get(data[3]).getName());
				room.insertNode(temp);
				result[0][0] = Boolean.toString(response);
				return result;
			}
			
		case "addChat":
			System.out.println("processing chat in add");
			if (room.addChat(hash.get(data[2]), data[3])) {
				result[0][0] = Boolean.toString(true);
				return result;
			}
			break;
			
		case "loadChat":
			System.out.println("processing chat in load");
			if (room.searchNode(hash.get(data[2])) == null) {
				String[][] temp1 = new String[1][1];
				temp1[0][0] = "user not included";
				return temp1;
			} else {
				return room.searchNode(hash.get(data[2])).getChatLog();
			}

		case "loadAllChat":
			System.out.println("processing chat in load all chat");
			return room.toDeepArray();
			
		default:
			break;
		}
		result[0][0] = Boolean.toString(false);
		return result;
	}

	public static String[][] chatRoomSort(Object object) {
		String[] res = (String[]) object;
		String[][] req = new String[100][8];
		String key = res[1];
		int roomLength = 0;
		Sort sortD = new Sort();
		String[][] copyRoom = room.toDeepArray().clone();
//		copyRoom[0] = new String[]{"안녕","하","세","3","요","ㅇㅈ"};
//		copyRoom[1] = new String[]{"안sdaf","하","세","2","31","fasd"};

		switch (key) {
			case "default":
				req = copyRoom.clone();
				break;

			case "AscendingTime":
				System.out.println("Save.chatRoomSort.AscendingTime - room data : " + Arrays.deepToString(copyRoom));
				if (copyRoom[0] == null) {

				} else if (copyRoom[1] == null) {

				} else {
					for (int i = 0; copyRoom[i] != null; i++) {
						roomLength++;
					}
					System.out.println("Saver.chatRoomSort.AscendingTime - roomLength data : " + roomLength);
					sortD.ascendingTime(copyRoom, 0, roomLength - 1);
				}
				req = copyRoom.clone();
				break;

			case "DescendingTime":
				System.out.println("Save.chatRoomSort.DescendingTime - room data : " + Arrays.deepToString(copyRoom));
				if (copyRoom[0] == null) {

				} else if (copyRoom[1] == null) {

				} else {
					for (int i = 0; copyRoom[i] != null; i++) {
						roomLength++;
					}
					System.out.println("Saver.chatRoomSort.DescendingTime - roomLength data : " + roomLength);
					sortD.descendingTime(copyRoom, 0, roomLength - 1);
				}
				req = copyRoom.clone();
				break;

			case "Destination":
				System.out.println("Save.chatRoomSort.Destination - room data : " + Arrays.deepToString(copyRoom));
				if (copyRoom[0] == null) {

				} else if (copyRoom[1] == null) {

				} else {
					for (int i = 0; copyRoom[i] != null; i++) {
						roomLength++;
					}
					System.out.println("Saver.chatRoomSort.Destination - roomLength data : " + roomLength);
					sortD.destination(copyRoom, 0, roomLength - 1);
				}
				req = copyRoom.clone();
				break;

			case "StartingTime":
				break;

			default:
				Sort.test();
				break;
		}

		return req;
	}
	
	public static String[][] review(Object obj) {
		String[] res = (String[]) obj;
		String key = res[1];
		Option user;
		ReviewItem review;
		switch (key) {
		case "addReview":
			user = hash.get(key);
			review = new ReviewItem(res[3], res[4]);
			user.addReview(review);
			hash.put(key, user);
			String[][] temp = new String[1][1];
			temp[0][0] = "true";
			return temp;
		case "loadReview":
			return hash.get(key).getReviewToString();				
			
		default:
			break;
		}
		String[][] temp = new String[1][1];
		temp[0][0] = "false";
		return temp;
	}
}
	