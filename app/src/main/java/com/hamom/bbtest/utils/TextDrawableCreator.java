package com.hamom.bbtest.utils;

import android.graphics.drawable.Drawable;
import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.hamom.bbtest.data.network.responce.User;

/**
 * Created by hamom on 30.08.17.
 */

public class TextDrawableCreator {

  public static Drawable createAvatar(User user) {
    String initials = user.getFirstName().substring(0, 1).toUpperCase() +
        user.getLastName().substring(0, 1).toUpperCase();
    return create(initials);
  }

  public static Drawable create(String text){
    ColorGenerator generator = ColorGenerator.MATERIAL;
    return TextDrawable.builder().buildRound(text, generator.getColor(text));
  }
}
