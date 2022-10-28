package option;

public class Option {
	String name;
	int phone;
	String id;
	String password;
	String gender;
	
	public Option () {
		
	}
	
	public Option (String name, int phone, String id, String password, String gender) {
		this.name = name;
		this.phone = phone;
		this.gender = gender;
		this.id = id;
		this.password = password;
	}
	
	public void optionCopy (Option option) {
		this.name = option.getName();
		this.phone = option.getPhone();
		this.gender = option.getGender();
		this.id = option.getId();
		this.password = option.getPassword();
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
