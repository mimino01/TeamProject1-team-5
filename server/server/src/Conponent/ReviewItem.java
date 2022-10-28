package Conponent;

import java.util.Objects;

public class ReviewItem {
	String description;
	float rating;
	
	public ReviewItem(float rating, String description) {
		// TODO Auto-generated constructor stub
		this.rating = rating;
		this.description = description;
	}	
	
	public ReviewItem() {
		// TODO Auto-generated constructor stub
	}	
	

	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public float getRating() {
		return rating;
	}



	public void setRating(float rating) {
		this.rating = rating;
	}



	@Override
	public String toString() {
		return "ReviewItem [description=" + description + ", rating=" + rating + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReviewItem other = (ReviewItem) obj;
		return Objects.equals(description, other.description) && rating == other.rating;
	}
	
	public String[] toStringArray() {
		String[] data = new String[10];
		
		data[0] = Float.toString(this.rating);
		data[1] = this.description;
		
		return data;
	}
	
	
}
