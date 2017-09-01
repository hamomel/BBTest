package com.hamom.bbtest.utils;

import com.hamom.bbtest.BuildConfig;

/**
 * Created by hamom on 30.08.17.
 */

public class ConstantManager {

  public static final String TAG_PREFIX = "BBTest";

  public static final String FILE_PROVIDER_AUTHORITY = BuildConfig.APPLICATION_ID + ".fileprovider";

  public static final int REQUEST_GALLERY_PERMISSION = 1000;
  public static final int REQUEST_CAMERA_PERMISSION = 1001;
  public static final int REQUEST_PHOTO_FROM_GALLERY = 2000;
  public static final int REQUEST_CAMERA_PICTURE = 2001;
}
