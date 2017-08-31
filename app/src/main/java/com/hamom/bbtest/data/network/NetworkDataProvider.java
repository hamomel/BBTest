package com.hamom.bbtest.data.network;

import com.hamom.bbtest.data.network.request.CreateUserReq;
import com.hamom.bbtest.data.network.responce.User;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import retrofit2.Callback;

/**
 * Created by hamom on 30.08.17.
 */
@Singleton
public class NetworkDataProvider {
  private ApiService mApiService;

  @Inject
  public NetworkDataProvider(ApiService apiService) {
    mApiService = apiService;
  }

  public void getAllUsers(Callback<List<User>> callback){
    mApiService.getAllUsers().enqueue(callback);
  }

  public void createUser(CreateUserReq req, String avatarFileUrl, Callback<Void> callback){
    // TODO: 31.08.17 implement avatar upload
    mApiService.createUser(req).enqueue(callback);
  }

  public void editUser(String userId, CreateUserReq req, String avatarFileUrl, Callback<Void> callback){
    // TODO: 31.08.17 implement avatar upload
    mApiService.editUser(userId, req).enqueue(callback);
  }
}
