package com.hamom.bbtest.ui.base;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.hamom.bbtest.ui.activities.MainActivity;
import javax.inject.Inject;

/**
 * Created by hamom on 30.08.17.
 */

public abstract class BaseFragment<P extends BasePresenter> extends Fragment {

  @Inject
  protected P mPresenter;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initDagger();
  }

  protected abstract void initDagger();

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    //noinspection unchecked
    mPresenter.takeView(this);
    setFabVisibility();
  }

  protected abstract void setFabVisibility();

  @Override
  public void onDestroyView() {
    mPresenter.dropView();
    super.onDestroyView();
  }

  protected MainActivity getMainActivity(){
    return ((MainActivity) getActivity());
  }
}
