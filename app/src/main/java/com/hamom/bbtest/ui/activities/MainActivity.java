package com.hamom.bbtest.ui.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import com.hamom.bbtest.R;
import com.hamom.bbtest.data.network.responce.User;
import com.hamom.bbtest.ui.fragments.edit.EditFragment;
import com.hamom.bbtest.ui.fragments.user_list.UserListFragment;

public class MainActivity extends AppCompatActivity {

  public static final String EDIT_FRAGMENT = "editFragment";
  public static final String USER_LIST_FRAGMENT = "userListFragment";
  private FrameLayout mDetailFrame;
  private FloatingActionButton mFAB;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    mDetailFrame = ((FrameLayout) findViewById(R.id.detail_frame));

    initToolbar();
    initFAB();
    if (savedInstanceState == null) setUserListFragment();
  }

  private void initToolbar() {
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
  }

  private void initFAB() {
    mFAB = (FloatingActionButton) findViewById(R.id.fab);
    mFAB.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        setEditFragment(null);
      }
    });
  }

  public void setUserListFragment() {
    UserListFragment fragment =
        ((UserListFragment) getFragmentManager().findFragmentByTag(USER_LIST_FRAGMENT));
    if (fragment == null) {
      fragment = new UserListFragment();
    } else {
      fragment.refreshData();
    }
    setMainFragment(fragment, false, USER_LIST_FRAGMENT);
  }

  private void setMainFragment(Fragment fragment, boolean addToBackStack, String tag) {
    FragmentManager fragmentManager = getFragmentManager();
    FragmentTransaction transaction = fragmentManager.beginTransaction();
    transaction.replace(R.id.main_frame, fragment, tag);
    if (addToBackStack) transaction.addToBackStack(null);
    transaction.commit();
  }

  public void setEditFragment(User user) {
    EditFragment editFragment =
        ((EditFragment) getFragmentManager().findFragmentByTag(EDIT_FRAGMENT));
    if (editFragment == null){
      editFragment = new EditFragment();
    }
    editFragment.setUser(user);

    if (!isMultiPane()) {
      setMainFragment(editFragment, true, EDIT_FRAGMENT);
    } else {
      FragmentManager fragmentManager = getFragmentManager();
      FragmentTransaction transaction = fragmentManager.beginTransaction();
      transaction.replace(R.id.detail_frame, editFragment, EDIT_FRAGMENT);
      transaction.commit();
    }
  }

  public void refreshUserList() {
    UserListFragment fragment =
        ((UserListFragment) getFragmentManager().findFragmentByTag(USER_LIST_FRAGMENT));
    if (fragment == null) {
      setUserListFragment();
    } else {
      fragment.refreshData();
    }
  }

  public void setFabVisible(boolean visible) {
    if (visible){
      mFAB.setVisibility(View.VISIBLE);
    } else {
      mFAB.setVisibility(View.GONE);
    }
  }

  public boolean isMultiPane() {
    return mDetailFrame != null;
  }
}
