package socket;

import java.util.Arrays;
import Conponent.Option;
import Conponent.HashTable;

public class Saver {
	static HashTable hash = new HashTable(40);
	static Option list = new Option();
	static int lastUserNumber = 0;
	
	public Saver() {
		
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
}
	