package com.hamom.bbtest.ui.fragments.user_list;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.hamom.bbtest.App;
import com.hamom.bbtest.R;
import com.hamom.bbtest.data.network.responce.User;
import com.hamom.bbtest.ui.activities.MainActivity;
import com.hamom.bbtest.ui.base.BaseFragment;
import com.hamom.bbtest.ui.fragments.edit.EditFragment;
import com.hamom.bbtest.utils.AppConfig;
import com.hamom.bbtest.utils.ConstantManager;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by hamom on 30.08.17.
 */

public class UserListFragment extends BaseFragment<UserListPresenter> {
  private static String TAG = ConstantManager.TAG_PREFIX + "UserListFrag: ";
  private static final String RECYCLER_STATE = "RECYCLER_STATE";


  @BindView(R.id.user_recycler)
  RecyclerView userRecycler;

  private UserListAdapter mAdapter;

  @Override
  protected void initDagger() {
    App.getAppComponent().getUserListComponent().inject(this);
  }

  @Override
  protected void setFabVisibility() {
    getMainActivity().setFabVisible(true);
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      Bundle savedInstanceState) {
    if (AppConfig.DEBUG) Log.d(TAG, "onCreateView: ");

    setRetainInstance(true);
    View view = inflater.inflate(R.layout.ftagment_user_list, container, false);
    ButterKnife.bind(this, view);
    initView(view);
    return view;
  }

  private void initView(View view) {

    if (mAdapter == null) mAdapter = new UserListAdapter(getCallback());
    userRecycler.setAdapter(mAdapter);

    if (userRecycler.getLayoutManager() == null) {
      userRecycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }
  }

  private UserListAdapter.UserCallback getCallback() {
    return new UserListAdapter.UserCallback() {
      @Override
      public void onClick(User user) {
        mPresenter.onItemClick(user);
      }
    };
  }

  @Override
  public void onViewStateRestored(Bundle savedInstanceState) {
    super.onViewStateRestored(savedInstanceState);

    if (savedInstanceState != null){
      if (AppConfig.DEBUG) Log.d(TAG, "onViewStateRestored: " + savedInstanceState);

      Parcelable state = savedInstanceState.getParcelable(RECYCLER_STATE);
      userRecycler.getLayoutManager().onRestoreInstanceState(state);
    }
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    if (AppConfig.DEBUG) Log.d(TAG, "onSaveInstanceState: ");

    if (userRecycler != null){
      Parcelable recyclerState = userRecycler.getLayoutManager().onSaveInstanceState();
      outState.putParcelable(RECYCLER_STATE, recyclerState);
    }
    super.onSaveInstanceState(outState);
  }

  public void setUsers(List<User> users) {
    if (AppConfig.DEBUG) Log.d(TAG, "setUsers: ");

    mAdapter.setUsers(users);
  }

  public void showMessage(String text) {
    Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
  }

  public void setEditFragment(User user) {
    EditFragment fragment = new EditFragment();
    fragment.setUser(user);
    getMainActivity().setDetailFragment(fragment);
  }
}
