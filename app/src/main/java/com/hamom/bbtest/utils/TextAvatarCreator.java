package com.hamom.bbtest.utils;

import android.graphics.drawable.Drawable;
import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.hamom.bbtest.data.network.responce.User;

/**
 * Created by hamom on 30.08.17.
 */

public class TextAvatarCreator {

  public static Drawable createAvatar(User user) {
    ColorGenerator generator = ColorGenerator.MATERIAL;
    String initials = user.getFirstName().substring(0, 1).toUpperCase() +
        user.getLastName().substring(0, 1).toUpperCase();
    return TextDrawable.builder().buildRound(initials, generator.getColor(initials));
  }
}
