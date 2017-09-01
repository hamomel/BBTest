package com.hamom.bbtest.ui.fragments.user_list;

import android.util.Log;
import com.hamom.bbtest.data.network.ApiService;
import com.hamom.bbtest.data.network.NetworkDataProvider;
import com.hamom.bbtest.data.network.responce.User;
import com.hamom.bbtest.di.scopes.UserListScope;
import com.hamom.bbtest.ui.base.BasePresenter;
import com.hamom.bbtest.utils.AppConfig;
import com.hamom.bbtest.utils.ConstantManager;
import java.util.List;
import javax.inject.Inject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hamom on 30.08.17.
 */
@UserListScope
public class UserListPresenter extends BasePresenter<UserListFragment>{
  private static String TAG = ConstantManager.TAG_PREFIX + "UserListPres: ";

  @Inject
  UserListPresenter(NetworkDataProvider networkDataProvider) {
    super(networkDataProvider);
  }

  @Override
  public void takeView(UserListFragment view) {
    super.takeView(view);
    fetchAllUsers();
  }

  public void fetchAllUsers() {
    if (AppConfig.DEBUG) Log.d(TAG, "fetchAllUsers: ");

    mNetworkDataProvider.getAllUsers(new Callback<List<User>>() {
      @Override
      public void onResponse(Call<List<User>> call, Response<List<User>> response) {
        if (response.isSuccessful() && response.code() == 200){
          updateView(response.body());
        }
      }
      // TODO: 31.08.17 handle error

      @Override
      public void onFailure(Call<List<User>> call, Throwable t) {
        // TODO: 31.08.17 handle error
      }
    });
  }

  private void updateView(List<User> users) {
    if (mView != null)
      mView.setUsers(users);
  }

  void onItemClick(User user) {
    mView.setEditFragment(user);
  }
}
