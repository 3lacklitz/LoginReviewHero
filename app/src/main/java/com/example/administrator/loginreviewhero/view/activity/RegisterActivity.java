package com.example.administrator.loginreviewhero.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.loginreviewhero.R;
import com.example.administrator.loginreviewhero.Util;
import com.example.administrator.loginreviewhero.model.GenericStatus;
import com.example.administrator.loginreviewhero.service.LoginService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.administrator.loginreviewhero.Constants.BASE_URL;

public class RegisterActivity extends AppCompatActivity {
    @BindView(R.id.username)
    EditText inputUsername;
    @BindView(R.id.password)
    EditText inputPassword;
    @BindView(R.id.confirm_password)
    EditText inputConfirmPassword;
    @BindView(R.id.name)
    EditText inputName;
    @BindView(R.id.mail)
    EditText inputEmail;
    @BindView(R.id.tel)
    EditText inputTel;
    @BindView(R.id.img)
    EditText inputImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @OnClick(R.id.button_create)
    void onCreateButtonClick() {
        String insertUsername = inputUsername.getText().toString();
        String insertPassword = inputPassword.getText().toString();
        String insertConfirmPassword = inputConfirmPassword.getText().toString();
        String insertName = inputName.getText().toString();
        String insertEmail = inputEmail.getText().toString();
        String insertTel = inputTel.getText().toString();
        String insertImage = inputImage.getText().toString();

        if (Util.validateSignUp(insertUsername, insertPassword, insertName, insertEmail,
                insertTel, insertImage, getBaseContext())) {
        } else {
            if (insertPassword.equals(insertConfirmPassword)) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                LoginService loginService = retrofit.create(LoginService.class);
                Call<GenericStatus> call = loginService.getSignUpData(insertUsername, insertPassword, insertName,
                        insertEmail, insertTel, insertImage);

                call.enqueue(new Callback<GenericStatus>() {
                    @Override
                    public void onResponse(Call<GenericStatus> call, Response<GenericStatus> response) {
                        if (response.body().getStatusCode() == 1000) {
                            Toast.makeText(getBaseContext(), "Successful...", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getBaseContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(getBaseContext(), response.body().getStatusDescription(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<GenericStatus> call, Throwable t) {
                        Toast.makeText(getBaseContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            } else {
                Toast.makeText(getBaseContext(), "The password does not match", Toast.LENGTH_LONG).show();
            }
        }
    }

    @OnClick(R.id.link_login)
    void onCancelButtonClick() {
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }
}
