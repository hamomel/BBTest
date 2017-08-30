package com.hamom.bbtest.data.network;

import com.hamom.bbtest.data.network.responce.User;
import java.util.List;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;

/**
 * Created by hamom on 30.08.17.
 */

public interface ApiService {

  @GET("users.json")
  Call<List<User>> getAllUsers();
}
