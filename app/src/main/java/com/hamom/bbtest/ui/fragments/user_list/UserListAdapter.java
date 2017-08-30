package com.hamom.bbtest.ui.fragments.user_list;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.hamom.bbtest.R;
import com.hamom.bbtest.data.network.responce.User;
import com.hamom.bbtest.utils.ConstantManager;
import com.squareup.picasso.Picasso;
import de.hdodenhof.circleimageview.CircleImageView;
import java.util.ArrayList;
import java.util.List;

import static com.hamom.bbtest.utils.TextAvatarCreator.createAvatar;

/**
 * Created by hamom on 30.08.17.
 */

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserViewHolder> {
  private static String TAG = ConstantManager.TAG_PREFIX + "UserListAdapter: ";
  private List<User> mUsers = new ArrayList<>();
  private Context mContext;
  private UserCallback mCallback;

  UserListAdapter(UserCallback callback) {
    mCallback = callback;
  }

  void setUsers(List<User> users) {
    mUsers = users;
    notifyDataSetChanged();
  }

  @Override
  public void onAttachedToRecyclerView(RecyclerView recyclerView) {
    super.onAttachedToRecyclerView(recyclerView);
    mContext = recyclerView.getContext();
  }

  @Override
  public void setHasStableIds(boolean hasStableIds) {
    super.setHasStableIds(hasStableIds);
  }

  @Override
  public long getItemId(int position) {
    return mUsers.get(position).getId();
  }

  @Override
  public UserListAdapter.UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
    return new UserViewHolder(view);
  }

  @Override
  public void onBindViewHolder(UserListAdapter.UserViewHolder holder, int position) {
    final User user = mUsers.get(position);
    String avatar = TextUtils.isEmpty(user.getAvatarUrl()) ? null : user.getAvatarUrl();
    Picasso.with(mContext)
        .load(avatar)
        .into(holder.avatarIv);
    //it is necessary because CircleImageView doesn't allow to load TextDrawable
    holder.avatarIv.setBackground(createAvatar(user));
    holder.firstNameTv.setText(user.getFirstName());
    holder.lastNameTv.setText(user.getLastName());
    holder.emailTv.setText(user.getEmail());
    holder.itemLayout.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mCallback.onClick(user);
      }
    });
  }

  @Override
  public int getItemCount() {
    return mUsers.size();
  }

  static class UserViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.item_layout)
    ConstraintLayout itemLayout;
    @BindView(R.id.avatar_iv)
    CircleImageView avatarIv;
    @BindView(R.id.first_name_tv)
    TextView firstNameTv;
    @BindView(R.id.last_name_tv)
    TextView lastNameTv;
    @BindView(R.id.email_tv)
    TextView emailTv;

    UserViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }

  interface UserCallback {
    void onClick(User user);
  }
}
