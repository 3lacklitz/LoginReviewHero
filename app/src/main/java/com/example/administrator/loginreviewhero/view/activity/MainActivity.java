package com.example.administrator.loginreviewhero.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.loginreviewhero.R;
import com.example.administrator.loginreviewhero.Util;
import com.example.administrator.loginreviewhero.model.Login;
import com.example.administrator.loginreviewhero.service.LoginService;
import com.example.administrator.loginreviewhero.view.adapter.CustomPagerAdapter;

import java.util.Timer;
import java.util.TimerTask;

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

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.txt_username)
    EditText userName;
    @BindView(R.id.txt_password)
    EditText password;
    @BindView(R.id.view_pager_review)
    ViewPager viewPager;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setAdaptor();
    }

    private void setAdaptor() {
        viewPager.setAdapter(new CustomPagerAdapter(this));
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(), 3000, 6000);
    }

    public class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (viewPager.getCurrentItem() == 0) {
                        viewPager.setCurrentItem(1);
                    } else if (viewPager.getCurrentItem() == 1) {
                        viewPager.setCurrentItem(2);
                    } else if (viewPager.getCurrentItem() == 2) {
                        viewPager.setCurrentItem(3);
                    } else {
                        viewPager.setCurrentItem(0);
                    }
                }
            });
        }
    }


    @OnClick(R.id.btn_login)
    void onLoginButtonClick() {
        String usernameString = userName.getText().toString();
        String passwordString = password.getText().toString();

        if (Util.validateValueAccount(usernameString, passwordString, this)) {

        } else {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            LoginService loginService = retrofit.create(LoginService.class);
            Call<Login> call = loginService.getLoginData(usernameString, passwordString);
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage(" loading....");
            showDialog();
            call.enqueue(new Callback<Login>() {
                @Override
                public void onResponse(Call<Login> call, Response<Login> response) {
                    Login login = response.body();
                    if (login.getStatusCode() == 1000) {
                        Intent intent = new Intent(getBaseContext(), HeroListActivity.class);
                        intent.putExtra(USER_DETAIL, login.getUserDetails().get(0));
                        startActivity(intent);
                        finish();
                        hideDialog();
                    } else {
                        Toast.makeText(getBaseContext(), login.getStatusDescription(), Toast.LENGTH_SHORT).show();
                        hideDialog();
                    }
                }

                @Override
                public void onFailure(Call<Login> call, Throwable t) {
                    Toast.makeText(getBaseContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @OnClick(R.id.btn_sign_up)
    void onSignUpButtonClick() {
        Intent intent = new Intent(getBaseContext(), RegisterActivity.class);
        startActivity(intent);
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
