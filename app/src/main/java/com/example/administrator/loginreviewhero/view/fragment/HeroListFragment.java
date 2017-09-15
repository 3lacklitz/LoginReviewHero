package com.example.administrator.loginreviewhero.view.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.loginreviewhero.R;
import com.example.administrator.loginreviewhero.Util;
import com.example.administrator.loginreviewhero.model.Hero;
import com.example.administrator.loginreviewhero.model.HeroList;
import com.example.administrator.loginreviewhero.model.Login;
import com.example.administrator.loginreviewhero.model.UserDetail;
import com.example.administrator.loginreviewhero.service.HeroListCallService;
import com.example.administrator.loginreviewhero.service.LoginService;
import com.example.administrator.loginreviewhero.view.activity.ProfileActivity;
import com.example.administrator.loginreviewhero.view.activity.UserListActivity;
import com.example.administrator.loginreviewhero.view.adapter.HeroListAdapter;
import com.example.administrator.loginreviewhero.view.adapter.HeroViewPagerAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.administrator.loginreviewhero.Constants.BASE_HERO_URL;
import static com.example.administrator.loginreviewhero.Constants.BASE_URL;
import static com.example.administrator.loginreviewhero.Constants.USER_DETAIL;

public class HeroListFragment extends Fragment {
    @BindView(R.id.recycler_view)
    RecyclerView heroList;
    @BindView(R.id.view_pager_img)
    ViewPager viewPager;
    @BindView(R.id.my_toolbar)
    Toolbar myToolbar;
    @BindView(R.id.image_profile_show)
    ImageView imageViewProfileShow;
    @BindView(R.id.image_user_list)
    ImageView ImageButtonUserList;

    private UserDetail userDetail;
    private ProgressDialog progressDialog;

    public static HeroListFragment newInstance(UserDetail userDetail) {
        HeroListFragment fragment = new HeroListFragment();
        Bundle data = new Bundle();
        data.putParcelable(USER_DETAIL, userDetail);
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hero_list, container, false);
        ButterKnife.bind(this, view);
        myToolbar.setClickable(true);
        ImageButtonUserList.setVisibility(View.GONE);
        userDetail = getArguments().getParcelable(USER_DETAIL);
        if (userDetail != null) {
            Util.setLoadImageProfile(getContext(), userDetail.getMemberImg(), imageViewProfileShow);
            if (userDetail.getAdminStatus().equals("1")) {
                ImageButtonUserList.setVisibility(View.VISIBLE);
            }
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_HERO_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        HeroListCallService callService = retrofit.create(HeroListCallService.class);
        Call<HeroList> call = callService.getHeroList();

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(" loading....");
        showDialog();

        call.enqueue(new Callback<HeroList>() {
            @Override
            public void onResponse(Call<HeroList> call, Response<HeroList> response) {
                List<Hero> heroes = response.body().getElements();

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                HeroListAdapter heroListAdapter = new HeroListAdapter(getContext(), heroes);
                heroList.setLayoutManager(layoutManager);
                heroList.setAdapter(heroListAdapter);
                viewPager.setAdapter(new HeroViewPagerAdapter(getContext(), heroes));
            }

            @Override
            public void onFailure(Call<HeroList> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    @OnClick(R.id.image_profile_show)
    void onImageProfileClick() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        LoginService loginService = retrofit.create(LoginService.class);
        Call<Login> call = loginService.getLoginData(userDetail.getMemberUsername(), userDetail.getMemberPassword());

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(" loading....");
        showDialog();

        call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                Login login =response.body();
                if(login.getStatusCode() == 1000){
                    Intent intent = new Intent(getContext(), ProfileActivity.class);
                    intent.putExtra(USER_DETAIL, login.getUserDetails().get(0));
                    startActivity(intent);
                    getActivity().finish();
                    hideDialog();
                }else {
                    Toast.makeText(getContext(), login.getStatusDescription(), Toast.LENGTH_LONG).show();
                    hideDialog();
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @OnClick(R.id.image_user_list)
    void onImageUserListClick(){
        Intent intent = new Intent(getContext(), UserListActivity.class);
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
