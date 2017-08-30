package com.hamom.bbtest.ui.base;

import android.app.Fragment;

/**
 * Created by hamom on 30.08.17.
 */

public class BasePresenter<T extends Fragment> {
  protected T mView;

  public void takeView(T view){
    this.mView = view;
  }

  public void dropView(){
    mView = null;
  }
}
