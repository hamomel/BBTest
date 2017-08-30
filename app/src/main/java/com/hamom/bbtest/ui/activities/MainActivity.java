package com.hamom.bbtest.ui.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import com.hamom.bbtest.R;
import com.hamom.bbtest.ui.fragments.edit.EditFragment;
import com.hamom.bbtest.ui.fragments.user_list.UserListFragment;

public class MainActivity extends AppCompatActivity {

  private FrameLayout mDetailFrame;
  private FloatingActionButton mFAB;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    mDetailFrame = ((FrameLayout) findViewById(R.id.detail_frame));

    initToolbar();
    initFAB();
    if (savedInstanceState == null)initView();
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
        Fragment fragment = new EditFragment();
        setDetailFragment(fragment);
      }
    });
  }

  private void initView() {
    setMainFragment(new UserListFragment(), false);
  }

  private void setMainFragment(Fragment fragment, boolean addToBackStack) {
    FragmentManager fragmentManager = getFragmentManager();
    FragmentTransaction transaction = fragmentManager.beginTransaction();
    transaction.replace(R.id.main_frame, fragment);
    if (addToBackStack) transaction.addToBackStack(null);
    transaction.commit();
  }

  public void setDetailFragment(Fragment fragment){
    if (mDetailFrame == null) {
      setMainFragment(fragment, true);
    } else {
      FragmentManager fragmentManager = getFragmentManager();
      FragmentTransaction transaction = fragmentManager.beginTransaction();
      transaction.replace(R.id.detail_frame, fragment);
      transaction.commit();
    }
  }

  public void setFabVisible(boolean visible) {
    if (visible){
      mFAB.setVisibility(View.VISIBLE);
    } else {
      mFAB.setVisibility(View.GONE);
    }
  }
}
