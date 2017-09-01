package com.hamom.bbtest.utils;

import com.amazonaws.regions.Regions;

/**
 * Created by hamom on 30.08.17.
 */

public class AppConfig {
  public static final boolean DEBUG = true;

  //Network
  public static final String BASE_URL = "https://bb-test-server.herokuapp.com";
  public static final long MAX_CONNECT_TIMEOUT = 5000;
  public static final long MAX_READ_TIMEOUT = 5000;
  public static final long MAX_WRITE_TIMEOUT = 5000;

  //AWS
  public static final String AWS_IDENTITY_POOL_ID =
      "us-east-1:bbe9b23c-f78a-4e64-887b-24bacc703eca";
  public static final Regions AWS_COGNITO_REGION = Regions.US_EAST_1;
  public static final String AWS_BUCKET_NAME = "bbtest-images";
  public static final String AWS_BUCKET_PREFIX = "https://s3.eu-central-1.amazonaws.com/bbtest-images/";

}
