package com.hamom.bbtest.data.network;

import com.hamom.bbtest.data.network.request.CreateUserReq;
import com.hamom.bbtest.data.network.responce.User;
import java.util.List;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by hamom on 30.08.17.
 */

public interface ApiService {

  @GET("users.json")
  Call<List<User>> getAllUsers();

  @POST("users.json")
  Call<Void> createUser(@Body CreateUserReq req);

  @PATCH("users/{userId}.json")
  Call<Void> editUser(@Path("userId") String userId, @Body CreateUserReq req);
}
