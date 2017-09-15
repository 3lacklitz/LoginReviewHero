package com.example.administrator.loginreviewhero.view.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.loginreviewhero.R;
import com.example.administrator.loginreviewhero.Util;
import com.example.administrator.loginreviewhero.model.UserDetail;
import com.example.administrator.loginreviewhero.model.UserList;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserCardViewHolder>{
    private Context context;
    private List<UserDetail> userDetails;

    public UserListAdapter(Context context, List<UserDetail> userDetails){
        this.context = context;
        this.userDetails = userDetails;
    }

    @Override
    public UserCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_card_view_user, parent, false);

        return new UserCardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(UserCardViewHolder holder, int position) {
        Util.setLoadImageProfile(context, userDetails.get(position).getMemberImg(),holder.logoImageUser);
        holder.showName.setText(userDetails.get(position).getMemberName());
        holder.showTel.setText(userDetails.get(position).getMemberTel());
    }

    @Override
    public int getItemCount() {
        return userDetails.size();
    }

    public class UserCardViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.card_view_user)
        CardView cardViewUser;
        @BindView(R.id.logo_image_user)
        ImageView logoImageUser;
        @BindView(R.id.show_text_name)
        TextView showName;
        @BindView(R.id.show_text_tel)
        TextView showTel;

        public UserCardViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
