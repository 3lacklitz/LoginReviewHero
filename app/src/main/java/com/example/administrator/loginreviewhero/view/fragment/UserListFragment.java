package com.example.administrator.loginreviewhero.view.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.administrator.loginreviewhero.R;
import com.example.administrator.loginreviewhero.model.UserDetail;
import com.example.administrator.loginreviewhero.model.UserList;
import com.example.administrator.loginreviewhero.service.UserListCallService;
import com.example.administrator.loginreviewhero.view.activity.HeroListActivity;
import com.example.administrator.loginreviewhero.view.adapter.HeroListAdapter;
import com.example.administrator.loginreviewhero.view.adapter.UserListAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.administrator.loginreviewhero.Constants.BASE_URL;
import static com.example.administrator.loginreviewhero.Constants.USER_DETAIL;

public class UserListFragment extends Fragment {
    @BindView(R.id.recycler_view_user)
    RecyclerView recyclerViewUser;
    @BindView(R.id.toolbar_user_list)
    Toolbar toolbarUserList;
    private ProgressDialog progressDialog;
    private UserDetail userDetail;

    public static UserListFragment newInstance(UserDetail userDetail) {
        UserListFragment fragment = new UserListFragment();
        Bundle data = new Bundle();
        data.putParcelable(USER_DETAIL, userDetail);
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_list, container, false);
        ButterKnife.bind(this, view);
        userDetail = getArguments().getParcelable("USER_DETAIL");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UserListCallService showListCallService = retrofit.create(UserListCallService.class);
        Call<UserList> call = showListCallService.getUserDetail();

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(" loading....");
        showDialog();

        call.enqueue(new Callback<UserList>() {
            @Override
            public void onResponse(Call<UserList> call, Response<UserList> response) {
                List<UserDetail> userDetails = response.body().getElements();
                //End Retrofit
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                UserListAdapter userListAdapter = new UserListAdapter(getContext(), userDetails);
                recyclerViewUser.setLayoutManager(layoutManager);
                recyclerViewUser.setAdapter(userListAdapter);
                hideDialog();
            }

            @Override
            public void onFailure(Call<UserList> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }

    @OnClick(R.id.image_back_user)
    void onImageBackClick(){
        Intent intent = new Intent(getContext(), HeroListActivity.class);
        intent.putExtra(USER_DETAIL, userDetail);
        startActivity(intent);
        getActivity().finish();
    }

    public void showDialog() {

        if (progressDialog != null && !progressDialog.isShowing())
            progressDialog.show();
    }

    public void hideDialog() {

        if (progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();
    }
}
