package socket;

import java.util.Arrays;

import option.Option;

public class Saver {
	static Option[] list = new Option[30];
	
	public Saver() {
		
	}
	
	public static boolean signup(Object obj) {
		try {
			String[] user = (String[]) obj;
			for (int i = 0; i < list.length; i++) {
				if (list[i].equals(new Option())) {
					list[i] = new Option(user[1],Integer.parseInt(user[2]),user[3],user[4],user[5]);
					return true;
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
				if (list[i].getId() == user[3] && list[i].getPassword() == user[4]) {
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
