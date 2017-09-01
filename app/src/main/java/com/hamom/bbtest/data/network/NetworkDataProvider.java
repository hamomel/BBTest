package com.hamom.bbtest.data.network;

import android.content.Context;
import android.net.Uri;
import com.hamom.bbtest.data.network.request.CreateUserReq;
import com.hamom.bbtest.data.network.responce.User;
import com.hamom.bbtest.utils.ConstantManager;
import com.hamom.bbtest.utils.S3UploadHelper;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import retrofit2.Callback;

/**
 * Created by hamom on 30.08.17.
 */
@Singleton
public class NetworkDataProvider {
  private static String TAG = ConstantManager.TAG_PREFIX + "NetworkDataP: ";
  private ApiService mApiService;
  private Context mAppContext;

  @Inject
  public NetworkDataProvider(ApiService apiService, Context context) {
    mApiService = apiService;
    mAppContext = context;
  }

  public void getAllUsers(Callback<List<User>> callback){
    mApiService.getAllUsers().enqueue(callback);
  }

  public void createUser(CreateUserReq req, Uri avatarFileUrl, Callback<Void> callback){
    if (avatarFileUrl != null){
      new S3UploadHelper(mAppContext).uploadToStorage(avatarFileUrl, getCreateCallback(req, callback));
    } else {
      mApiService.createUser(req).enqueue(callback);
    }
  }

  private S3UploadHelper.UploadCallback getCreateCallback(final CreateUserReq req,
      final Callback<Void> callback) {
    return new S3UploadHelper.UploadCallback() {
      @Override
      public void onSuccess(String s3Link) {
        req.setAvatarUrl(s3Link);
        mApiService.createUser(req).enqueue(callback);
      }

      @Override
      public void onFailure(Throwable e) {
        handleUploadError(e);
      }
    };
  }

  public void editUser(String userId, CreateUserReq req, Uri avatarFileUrl, Callback<Void> callback){
    if (avatarFileUrl != null){
      new S3UploadHelper(mAppContext).uploadToStorage(avatarFileUrl, getEditCallback(userId, req, callback));
    } else {
      mApiService.editUser(userId, req).enqueue(callback);
    }
  }

  private S3UploadHelper.UploadCallback getEditCallback(final String userId, final CreateUserReq req,
      final Callback<Void> callback) {
    return new S3UploadHelper.UploadCallback() {
      @Override
      public void onSuccess(String s3Link) {
        req.setAvatarUrl(s3Link);
        mApiService.editUser(userId, req).enqueue(callback);
      }

      @Override
      public void onFailure(Throwable e) {
        handleUploadError(e);
      }
    };
  }

  private void handleUploadError(Throwable e) {
    // TODO: 01.09.17 implement me
  }
}
