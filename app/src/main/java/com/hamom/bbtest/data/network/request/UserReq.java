package com.hamom.bbtest.data.network.request;

import com.google.gson.annotations.SerializedName;

public class UserReq{
  @SerializedName("avatar_url")
  private String avatarUrl;

  @SerializedName("last_name")
  private String lastName;

  @SerializedName("first_name")
  private String firstName;

  private String email;

	public UserReq(String firstName, String lastName, String email) {
		this.lastName = lastName;
		this.firstName = firstName;
		this.email = email;
	}

  public void setAvatarUrl(String avatarUrl) {
    this.avatarUrl = avatarUrl;
  }
}
