package com.hamom.bbtest.data.network.error;

/**
 * Created by hamom on 01.09.17.
 */

public class S3UploadError extends Throwable {
  public S3UploadError() {
    super("Unknown error during upload");
  }

  public S3UploadError(Throwable cause) {
    super(cause);
  }
}
