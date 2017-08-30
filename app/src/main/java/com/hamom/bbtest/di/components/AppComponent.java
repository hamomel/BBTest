package com.hamom.bbtest.di.components;

import android.content.Context;
import com.hamom.bbtest.di.modules.AppModule;
import com.hamom.bbtest.di.modules.NetworkModule;
import dagger.Component;
import javax.inject.Singleton;

/**
 * Created by hamom on 30.08.17.
 */
@Singleton
@Component(modules = { AppModule.class, NetworkModule.class })
public interface AppComponent {

  Context getAppContext();

  UserListComponent getUserListComponent();
  EditComponent getEditComponent();
}
