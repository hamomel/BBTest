package com.hamom.bbtest.ui.fragments.edit;

import com.hamom.bbtest.data.network.NetworkDataProvider;
import com.hamom.bbtest.di.scopes.EditScope;
import com.hamom.bbtest.ui.base.BasePresenter;
import javax.inject.Inject;

/**
 * Created by hamom on 30.08.17.
 */
@EditScope
public class EditPresenter extends BasePresenter<EditFragment> {

  @Inject
  public EditPresenter(NetworkDataProvider networkDataProvider) {
    super(networkDataProvider);
  }
}
