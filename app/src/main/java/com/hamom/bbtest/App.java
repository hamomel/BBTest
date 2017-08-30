package com.hamom.bbtest;

import android.app.Application;
import com.hamom.bbtest.di.components.AppComponent;
import com.hamom.bbtest.di.components.DaggerAppComponent;
import com.hamom.bbtest.di.modules.AppModule;
import com.hamom.bbtest.di.modules.NetworkModule;

/**
 * Created by hamom on 30.08.17.
 */

public class App extends Application {

  private static AppComponent sAppComponent;

  public static AppComponent getAppComponent() {
    return sAppComponent;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    createAppComponent();
  }

  private void createAppComponent() {
    sAppComponent = DaggerAppComponent.builder()
        .appModule(new AppModule(getApplicationContext()))
        .networkModule(new NetworkModule())
        .build();
  }
}
