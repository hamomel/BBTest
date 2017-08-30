package com.hamom.bbtest.di.components;

import com.hamom.bbtest.di.scopes.UserListScope;
import com.hamom.bbtest.ui.fragments.user_list.UserListFragment;
import dagger.Subcomponent;

/**
 * Created by hamom on 30.08.17.
 */
@UserListScope
@Subcomponent
public interface UserListComponent {

  void inject(UserListFragment fragment);
}
