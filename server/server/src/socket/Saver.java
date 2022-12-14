package socket;

import java.util.Arrays;
import Conponent.Option;
import Conponent.ReviewItem;
import Conponent.HashTable;
import Conponent.LinerTable;

public class Saver {
	static HashTable hash = new HashTable(40);
	static LinerTable room = new LinerTable(40);
	static Option list = new Option();
	static int lastUserNumber = 0;
	
	public Saver() {
		hash.put("adminid", new Option("관리자",01012341234,"adminid","adminpw","man"));
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
	
	public static String[] reqUserdata(Object obj) {
		String[] user = (String[]) obj;
		return hash.get(user[1]).toStringArray();
	}
	
	public static String[][] chating(Object obj) {
		String[][] result = new String[10][10];
		String[] data = (String[]) obj;
		String key = data[1];
		switch (key) {
		case "create":
			System.out.println("processing chat in create");
			if (room.createRoom(data[2])) {
				result[0][0] = Boolean.toString(true);
				return result;
			}
			break;
			
		case "addChat":
			System.out.println("processing chat in add");
			if (room.addData(room.findRoomNumberByUserId(data[2]), hash.get(data[2]), data[3])) {
				result[0][0] = Boolean.toString(true);
				return result;
			}
			break;
			
		case "loadChat":
			System.out.println("processing chat in load");
			return room.getChatData(room.findRoomNumberByUserId(data[2]));
			
		default:
			break;
		}
		result[0][0] = Boolean.toString(false);
		return result;
	}
	
	public static String[][] review(Object obj) {
		String[] res = (String[]) obj;
		String key = res[1];
		Option user = new Option();
		ReviewItem review = new ReviewItem();
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
	