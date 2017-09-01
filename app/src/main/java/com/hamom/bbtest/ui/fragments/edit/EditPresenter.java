package com.hamom.bbtest.ui.fragments.edit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.util.Patterns;
import com.hamom.bbtest.data.network.NetworkDataProvider;
import com.hamom.bbtest.data.network.request.CreateUserReq;
import com.hamom.bbtest.data.network.responce.User;
import com.hamom.bbtest.di.scopes.EditScope;
import com.hamom.bbtest.ui.base.BasePresenter;
import com.hamom.bbtest.utils.AppConfig;
import com.hamom.bbtest.utils.ConstantManager;
import com.hamom.bbtest.utils.UriFromContentConverter;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import javax.inject.Inject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * Created by hamom on 30.08.17.
 */
@EditScope
public class EditPresenter extends BasePresenter<EditFragment> {
  private static String TAG = ConstantManager.TAG_PREFIX + "EditPresenter: ";
  private File mPhotoFile;
  private Uri mPhotoUri;

  @Inject
  EditPresenter(NetworkDataProvider networkDataProvider) {
    super(networkDataProvider);
  }

  void onSaveClick(String firstName, String lastName, String email, User user) {
    mView.hideAllErrors();
    if (verifyFields(firstName, lastName, email)) {
      if (user != null) {
        CreateUserReq req = new CreateUserReq(firstName, lastName, email);
        req.setAvatarUrl(user.getAvatarUrl());
        mNetworkDataProvider.editUser(String.valueOf(user.getId()), req, mPhotoUri,
            getEditCallback());
      } else {
        CreateUserReq req = new CreateUserReq(firstName, lastName, email);
        mNetworkDataProvider.createUser(req, mPhotoUri, getCreateCallback());
      }
    }
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

  //region===================== Take photo ==========================
  void onAvatarClick() {
    mView.showChoosePhotoDialog();
  }

  void chooseGallery() {
    String[] permissions = { android.Manifest.permission.READ_EXTERNAL_STORAGE };
    if (checkPermissionsAndRequestIfNotGranted(permissions,
        ConstantManager.REQUEST_GALLERY_PERMISSION)) {
      loadPhotoFromGallery();
    }
  }

  private void loadPhotoFromGallery() {
    Intent intent = new Intent();
    if (Build.VERSION.SDK_INT < 19) {
      intent.setType("image/*");
      intent.setAction(Intent.ACTION_GET_CONTENT);
    } else {
      intent.setType("image/*");
      intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
      intent.addCategory(Intent.CATEGORY_OPENABLE);
    }
    mView.startActivityForResult(intent,
        ConstantManager.REQUEST_PHOTO_FROM_GALLERY);
  }

  void chooseCamera() {
    String[] permissions = new String[] { CAMERA, WRITE_EXTERNAL_STORAGE };
    if (checkPermissionsAndRequestIfNotGranted(permissions,
        ConstantManager.REQUEST_CAMERA_PERMISSION)) {
      loadPhotoFromCamera();
    }
  }

  private void loadPhotoFromCamera() {
    mPhotoFile = createImageFile();
    if (mPhotoFile == null){
      mView.showCantTakePhoto();
      return;
    }

    mView.sendCameraIntent(mPhotoFile);
  }

  private File createImageFile() {
    String dateStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    String imageFileName = "JPEG_" + dateStamp;
    File storageDir = (mView.getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES));
    if (storageDir == null){
      return null;
    }
    Log.d(TAG, "createImageFile: " + storageDir.getAbsolutePath());
    File image;
    try {
      image = File.createTempFile(imageFileName, ".jpg", storageDir);
    } catch (IOException e) {
      return null;
    }

    return image;
  }

  private boolean checkPermissionsAndRequestIfNotGranted(String[] permissions,
      int requestCode) {
    boolean allGranted = true;
    for (String permission : permissions) {

      int selfPermission =
          ContextCompat.checkSelfPermission(mView.getActivity(), permission);
      if (selfPermission != PackageManager.PERMISSION_GRANTED) {
        allGranted = false;
        break;
      }
    }
    if (!allGranted) {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        mView.requestPermissions(permissions, requestCode);
      }
      return false;
    }

    return allGranted;
  }

  void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
    if (AppConfig.DEBUG) Log.d(TAG, "onRequestPermissionsResult: ");

    boolean allGranted = true;
    for (int grantResult : grantResults) {
      if (grantResult != PackageManager.PERMISSION_GRANTED) allGranted = false;
    }

    if (allGranted) {
      switch (requestCode){
        case ConstantManager.REQUEST_GALLERY_PERMISSION:
          loadPhotoFromGallery();
          break;
        case ConstantManager.REQUEST_CAMERA_PERMISSION:
          loadPhotoFromCamera();
          break;
      }
    } else {
      mView.showPermissionNecessary();
    }
  }

  @SuppressLint("NewApi")
  void onActivityResult(int requestCode, int resultCode, Intent data) {
    switch (requestCode){
      case ConstantManager.REQUEST_CAMERA_PICTURE:
        if (mPhotoFile != null) {
          mPhotoUri = Uri.fromFile(mPhotoFile);
        }
        break;
      case ConstantManager.REQUEST_PHOTO_FROM_GALLERY:
        if (data != null){
          String uri = "file://" + UriFromContentConverter.getPath(mView.getActivity(), data.getData());
          mPhotoUri = Uri.parse(uri);
        }
    }
    mView.setPhoto(mPhotoUri);
  }
  //endregion
}
