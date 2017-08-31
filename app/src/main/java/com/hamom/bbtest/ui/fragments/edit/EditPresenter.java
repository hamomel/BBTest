package com.hamom.bbtest.ui.fragments.edit;

import android.util.Patterns;
import com.hamom.bbtest.data.network.NetworkDataProvider;
import com.hamom.bbtest.data.network.request.CreateUserReq;
import com.hamom.bbtest.data.network.responce.User;
import com.hamom.bbtest.di.scopes.EditScope;
import com.hamom.bbtest.ui.base.BasePresenter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.inject.Inject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hamom on 30.08.17.
 */
@EditScope
public class EditPresenter extends BasePresenter<EditFragment> {

  private String mAvatarFileUrl;

  @Inject
  public EditPresenter(NetworkDataProvider networkDataProvider) {
    super(networkDataProvider);
  }

  public void onAvatarClick() {

  }

  public void onSaveClick(String firstName, String lastName, String email, User user) {
    mView.hideAllErrors();
    if (verifyFields(firstName, lastName, email)){
      if (user != null){
        CreateUserReq req = new CreateUserReq(firstName, lastName, email);
        req.setAvatarUrl(user.getAvatarUrl());
        mNetworkDataProvider.editUser(String.valueOf(user.getId()), req, mAvatarFileUrl, getEditCallback());
      } else {
        CreateUserReq req = new CreateUserReq(firstName, lastName, email);
        mNetworkDataProvider.createUser(req, mAvatarFileUrl, getCreateCallback());
      }
    }
  }

  private Callback<Void> getCreateCallback() {
    return new Callback<Void>() {
      @Override
      public void onResponse(Call<Void> call, Response<Void> response) {
        // TODO: 31.08.17 handle result
      }

      @Override
      public void onFailure(Call<Void> call, Throwable t) {
        // TODO: 31.08.17 handle error
      }
    };
  }

  private Callback<Void> getEditCallback() {
    return new Callback<Void>() {
      @Override
      public void onResponse(Call<Void> call, Response<Void> response) {
          // TODO: 31.08.17 handle result
      }

      @Override
      public void onFailure(Call<Void> call, Throwable t) {
        // TODO: 31.08.17 handle error
      }
    };
  }

  //verify and set errors on all fields
  private boolean verifyFields(String firstName, String lastName, String email) {
    boolean isValid;
    isValid = verifyFirstName(firstName);
    isValid = verifyLastName(lastName) && isValid;
    isValid = verifyEmail(email) && isValid;
    return isValid;
  }

  private boolean verifyFirstName(String firstName) {
    boolean isEmpty = firstName.isEmpty();
    if (isEmpty) mView.showFirstNameEmptyError();
    return !isEmpty;
  }

  private boolean verifyLastName(String lastName) {
    boolean isEmpty = lastName.isEmpty();
    if (isEmpty) mView.showLastNameEmptyError();
    return !isEmpty;
  }

  private boolean verifyEmail(String email) {
    boolean isEmpty = email.isEmpty();
    if (isEmpty) {
      mView.showEmailEmptyError();
      return false;
    }

    Matcher matcher = Patterns.EMAIL_ADDRESS.matcher(email);
    if (!matcher.matches()) {
      mView.showEmailIncorrectError();
      return false;
    }
    return true;
  }

}
