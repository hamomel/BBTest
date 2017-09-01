package com.hamom.bbtest.utils;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.GroupGrantee;
import com.amazonaws.services.s3.model.Permission;
import com.hamom.bbtest.data.network.error.S3UploadError;
import java.io.File;
import java.util.UUID;

/**
 * Created by hamom on 01.09.17.
 */

public class S3UploadHelper {
  private Context mContext;

  public S3UploadHelper(Context context) {
    mContext = context;
  }

  public void uploadToStorage(Uri fileUrl, UploadCallback callback) {
    // Initialize the Amazon Cognito credentials provider
    CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
        mContext, AppConfig.AWS_IDENTITY_POOL_ID,
        AppConfig.AWS_COGNITO_REGION
    );

    final AmazonS3 s3 = new AmazonS3Client(credentialsProvider);
    TransferUtility transferUtility = new TransferUtility(s3, mContext);
    File file = new File(fileUrl.getPath());
    final String key = UUID.randomUUID().toString() + ".jpg";
    TransferObserver observer = transferUtility.upload(AppConfig.AWS_BUCKET_NAME, key, file);
    observer.setTransferListener(getListener(s3, key, callback));
  }

  @NonNull
  private TransferListener getListener(final AmazonS3 s3, final String key, final UploadCallback callback) {
    return new TransferListener() {
      @Override
      public void onStateChanged(int id, TransferState state) {
        switch (state){
          case COMPLETED:
            setPermissions(s3, key);
            callback.onSuccess(AppConfig.AWS_BUCKET_PREFIX + key);
            break;
          case FAILED:
            callback.onFailure(new S3UploadError());
        }
      }

      @Override
      public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {

      }

      @Override
      public void onError(int id, Exception ex) {
        callback.onFailure(new S3UploadError(ex));
      }
    };
  }

  private void setPermissions(final AmazonS3 s3, final String key) {
    new Thread(new Runnable() {
      @Override
      public void run() {
        AccessControlList acl = s3.getObjectAcl(AppConfig.AWS_BUCKET_NAME, key);
        acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);
        s3.setObjectAcl(AppConfig.AWS_BUCKET_NAME, key, acl);
      }
    }).start();
  }

  public interface UploadCallback {
    void onSuccess(String s3Link);
    void onFailure(Throwable e);
  }
}
