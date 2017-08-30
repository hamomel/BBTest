package com.hamom.bbtest.di.scopes;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.inject.Scope;

/**
 * Created by hamom on 30.08.17.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface UserListScope {
}
