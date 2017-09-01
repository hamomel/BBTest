package com.hamom.bbtest.ui.fragments.edit;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.hamom.bbtest.App;
import com.hamom.bbtest.R;
import com.hamom.bbtest.data.network.responce.User;
import com.hamom.bbtest.ui.base.BaseFragment;
import com.hamom.bbtest.utils.ConstantManager;
import com.hamom.bbtest.utils.TextDrawableCreator;
import com.squareup.picasso.Picasso;
import de.hdodenhof.circleimageview.CircleImageView;
import java.io.File;
import java.util.List;

/**
 * Created by hamom on 30.08.17.
 */

public class EditFragment extends BaseFragment<EditPresenter> {
  private static String TAG = ConstantManager.TAG_PREFIX + "EditFragment: ";

  @BindView(R.id.avatar_edit_iv)
  CircleImageView avatarEditIv;
  @BindView(R.id.first_name_edit_layout)
  TextInputLayout firstNameEditLayout;
  @BindView(R.id.first_name_et)
  TextInputEditText firstNameEt;
  @BindView(R.id.last_name_edit_layout)
  TextInputLayout lastNameEditLayout;
  @BindView(R.id.last_name_et)
  TextInputEditText lastNameEt;
  @BindView(R.id.email_edit_layout)
  TextInputLayout emailEditLayout;
  @BindView(R.id.email_et)
  TextInputEditText emailEt;
  @BindView(R.id.save_btn)
  Button saveBtn;

  private Uri mFileUri;
  private User mUser;
  private ProgressDialog mProgressDialog;

  @Override
  protected void initDagger() {
    App.getAppComponent().getEditComponent().inject(this);
  }

  @Override
  protected void setFabVisibility() {
    if (!getMainActivity().isMultiPane()) getMainActivity().setFabVisible(false);
  }

  public void setUser(User user) {
    mUser = user;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_edit, container, false);
    ButterKnife.bind(this, view);
    initView();
    return view;
  }

  private void initView() {
    saveBtn.setOnClickListener(getSaveClickListener());
    avatarEditIv.setOnClickListener(getAvatarClickListener());
    if (mUser == null) {
      avatarEditIv.setBackground(TextDrawableCreator.create("+"));
      return;
    }

    avatarEditIv.setBackground(TextDrawableCreator.createAvatar(mUser));
    String avatar = TextUtils.isEmpty(mUser.getAvatarUrl()) ? null : mUser.getAvatarUrl();
    Picasso.with(getActivity()).load(avatar).fit().into(avatarEditIv);
    firstNameEt.setText(mUser.getFirstName());
    lastNameEt.setText(mUser.getLastName());
    emailEt.setText(mUser.getEmail());
  }

  private View.OnClickListener getAvatarClickListener() {
    return new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mPresenter.onAvatarClick();
      }
    };
  }

  private View.OnClickListener getSaveClickListener() {
    return new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String firstName = firstNameEt.getText().toString();
        String lastName = lastNameEt.getText().toString();
        String email = emailEt.getText().toString();
        mPresenter.onSaveClick(firstName, lastName, email, mUser);
      }
    };
  }

  public void setPhoto(Uri photoUri) {
    Picasso.with(getActivity()).load(photoUri).into(avatarEditIv);
  }

  public void showFirstNameEmptyError() {
    showFirstNameError(getString(R.string.required_field));
  }

  public void showLastNameEmptyError() {
    showLastNameError(getString(R.string.required_field));
  }

  public void showEmailEmptyError() {
    showEmailError(getString(R.string.required_field));
  }

  public void showEmailIncorrectError() {
    showEmailError(getString(R.string.incorrect_format));
  }

  private void showFirstNameError(String error) {
    firstNameEditLayout.setErrorEnabled(true);
    firstNameEditLayout.setError(error);
  }

  private void showLastNameError(String error) {
    lastNameEditLayout.setErrorEnabled(true);
    lastNameEditLayout.setError(error);
  }

  private void showEmailError(String string) {
    emailEditLayout.setErrorEnabled(true);
    emailEditLayout.setError(string);
  }

  public void hideAllErrors() {
    firstNameEditLayout.setErrorEnabled(false);
    lastNameEditLayout.setErrorEnabled(false);
    emailEditLayout.setErrorEnabled(false);
  }

  public void showChoosePhotoDialog() {
    String source[] = {
        getString(R.string.load_from_gallery), getString(R.string.take_photo),
        getString(R.string.cancel)
    };
    android.support.v7.app.AlertDialog.Builder alertDialog =
        new android.support.v7.app.AlertDialog.Builder(getActivity());
    alertDialog.setTitle(R.string.choose_photo);
    alertDialog.setItems(source, new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        switch (which) {
          case 0:
            mPresenter.chooseGallery();
            break;
          case 1:
            mPresenter.chooseCamera();
            break;
          case 2:
            dialog.cancel();
            break;
        }
      }
    });
    alertDialog.show();
  }

  public void showPermissionNecessary() {
    if (getView() == null) return;

    Snackbar.make(getView(), getString(R.string.need_grant_permission), Snackbar.LENGTH_LONG)
        .setAction(getString(R.string.grant), new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            openAppSettings();
          }
        })
        .show();
  }

  private void openAppSettings() {
    Intent intent = new Intent();
    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
    Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
    intent.setData(uri);
    startActivity(intent);
  }

  public void showProgressDialog() {
    mProgressDialog = new ProgressDialog(getActivity());
    mProgressDialog.setMessage(getString(R.string.uploading));
    mProgressDialog.show();
  }

  public void hideProgressDialog(){
    mProgressDialog.dismiss();
  }

  public void showUploadSuccess() {
    showToast(getString(R.string.data_renewed));
  }

  public void showCantTakePhoto() {
    showToast(getString(R.string.cant_take_photo));
  }

  private void showToast(String message) {
    Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
  }

  public void sendCameraIntent(File photoFile) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      mFileUri =
          FileProvider.getUriForFile(getActivity(), ConstantManager.FILE_PROVIDER_AUTHORITY,
              photoFile);
    } else {
      mFileUri = Uri.fromFile(photoFile);
    }

    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mFileUri);

    // grant permission to allow camera app write our file
    grantUriPermission(mFileUri, takePictureIntent);
    startActivityForResult(takePictureIntent, ConstantManager.REQUEST_CAMERA_PICTURE);
  }


  private void grantUriPermission(Uri uriForFile, Intent intent) {
    List<ResolveInfo> resolvedIntentActivities = getActivity().getPackageManager()
        .queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
    for (ResolveInfo resolvedIntentInfo : resolvedIntentActivities) {
      String packageName = resolvedIntentInfo.activityInfo.packageName;

      getActivity().grantUriPermission(packageName, uriForFile,
          Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
    }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    mPresenter.onRequestPermissionsResult(requestCode, permissions, grantResults);
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == ConstantManager.REQUEST_CAMERA_PICTURE){
      getActivity().revokeUriPermission(mFileUri,
          Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
      mFileUri = null;
    }
    mPresenter.onActivityResult(requestCode, resultCode, data);
  }
}
