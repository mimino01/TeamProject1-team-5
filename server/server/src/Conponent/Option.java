package Conponent;

public class Option {
	String name;
	long phone;
	String id;
	String password;
	String gender;
	ReviewItem[] review = new ReviewItem[10];
	
	public Option () {
		
	}
	
	public Option (String name, long phone, String id, String password, String gender) {
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
	
	public boolean addReview (ReviewItem review) {
		for (int i = 0; i < this.review.length; i++) {
			this.review[i] = review;
			return true;
		}
		return false;
	}
	
	public void setReview (ReviewItem[] review) {
		this.review = review;
	}
	
	public ReviewItem[] getReview () {
		return review;
	}
	
	public String[][] getReviewToString () {
		String[][] data = new String[review.length][5];
		for (int i = 0; i < review.length; i++) {
			data[i] = review[i].toStringArray();
		}
		return data;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getPhone() {
		return phone;
	}

	public void setPhone(long phone) {
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

	@Override
	public String toString() {
		return "Option [name=" + name + ", phone=" + phone + ", id=" + id + ", password=" + password + ", gender="
				+ gender + "]";
	}
	
	public String[] toStringArray() {
		String[] data = new String[10];
		
		data[0] = this.name;
		data[1] = Long.toString(this.phone);
		data[2] = this.gender;
		data[3] = this.id;
		data[4] = this.password;
		
		return data;
	}
	
}
