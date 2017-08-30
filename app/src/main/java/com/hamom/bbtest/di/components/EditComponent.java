package com.hamom.bbtest.di.components;

import com.hamom.bbtest.di.scopes.EditScope;
import com.hamom.bbtest.ui.fragments.edit.EditFragment;
import dagger.Component;
import dagger.Subcomponent;

/**
 * Created by hamom on 30.08.17.
 */
@Subcomponent
@EditScope
public interface EditComponent {
  void inject(EditFragment fragment);
}
