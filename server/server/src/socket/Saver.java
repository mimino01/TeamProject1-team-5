package socket;

import java.util.Arrays;

import option.Option;

public class Saver {
	static Option[] list = new Option[30];
	static int lastUserNumber = 0;
	
	public Saver() {
		
	}
	
	public static boolean signup(Object obj) {
		try {
			String[] user = (String[]) obj;
			if (lastUserNumber == 0) {
				list[0] = new Option(user[1],Long.parseLong(user[2]),user[3],user[4],user[5]);
				lastUserNumber++;
				return true;
			} else {
				for (int i = 0; i < lastUserNumber; i++) {
					if (list[i] == null) {
						list[i] = new Option(user[1],Long.parseLong(user[2]),user[3],user[4],user[5]);
						return true;
					}
				}
			}			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean signin(Object obj) {
		String[] user = (String[]) obj;
		try {
			for (int i = 0; i < list.length; i++) {
				if (list[i] == null) {
					System.out.println("data is null");
					continue;
				}
				System.out.println("id: " + list[i].getId() + " password: " + list[i].getPassword());
				System.out.println("id: " + user[1] + " password: " + user[2]);
				System.out.println(list[i].getId().equals(user[1]));
				System.out.println(list[i].getPassword().equals(user[2]));
				if (list[i].getId().equals(user[1]) && list[i].getPassword().equals(user[2])) {
					System.out.println("유저 로그인 성공" + user[1]);
					return true;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}
