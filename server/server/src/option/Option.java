package option;

public class Option {
	String name;
	int phone;
	String id;
	String password;
	String gender;
	
	public Option () {
		
	}
	
	public Option (int phone, String name, String id, String password, String gender) {
		this.name = name;
		this.phone = phone;
		this.gender = gender;
		this.id = id;
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPhone() {
		return phone;
	}

	public void setPhone(int phone) {
		this.phone = phone;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
	
}
