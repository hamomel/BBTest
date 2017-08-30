package com.hamom.bbtest.di.modules;

import android.content.Context;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/**
 * Created by hamom on 30.08.17.
 */

@Module
public class AppModule {
  private Context mContext;

  public AppModule(Context context) {
    mContext = context;
  }

  @Provides
  @Singleton
  Context provideContext() {
    return mContext;
  }
}
