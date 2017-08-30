package com.hamom.bbtest.data.network.responce;

import com.google.gson.annotations.SerializedName;

public class User{

	@SerializedName("avatar_url")
	private String avatarUrl;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("last_name")
	private String lastName;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id")
	private int id;

	@SerializedName("first_name")
	private String firstName;

	@SerializedName("email")
	private String email;

	@SerializedName("url")
	private String url;

	public String getAvatarUrl(){
		return avatarUrl;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public String getLastName(){
		return lastName;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public int getId(){
		return id;
	}

	public String getFirstName(){
		return firstName;
	}

	public String getEmail(){
		return email;
	}

	public String getUrl(){
		return url;
	}

	@Override
 	public String toString(){
		return 
			"User{" + 
			"avatar_url = '" + avatarUrl + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",last_name = '" + lastName + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",id = '" + id + '\'' + 
			",first_name = '" + firstName + '\'' + 
			",email = '" + email + '\'' + 
			",url = '" + url + '\'' + 
			"}";
		}
}