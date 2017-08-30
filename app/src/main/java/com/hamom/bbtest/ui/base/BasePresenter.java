package com.hamom.bbtest.ui.base;

import com.hamom.bbtest.data.network.NetworkDataProvider;

/**
 * Created by hamom on 30.08.17.
 */

public class BasePresenter<V extends BaseFragment> {
  protected V mView;
  protected NetworkDataProvider mNetworkDataProvider;

  public BasePresenter(NetworkDataProvider networkDataProvider) {
    mNetworkDataProvider = networkDataProvider;
  }

  public void takeView(V view){
    this.mView = view;
  }

  public void dropView(){
    mView = null;
  }
}
