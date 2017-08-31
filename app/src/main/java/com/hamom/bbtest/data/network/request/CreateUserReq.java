package com.hamom.bbtest.data.network.request;

public class CreateUserReq{
	private UserReq user;

  public CreateUserReq(String firstName, String lastName, String email) {
    user = new UserReq(firstName, lastName, email);
  }

  public void setAvatarUrl(String url){
    user.setAvatarUrl(url);
  }
}
