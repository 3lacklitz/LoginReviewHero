package com.example.administrator.loginreviewhero.view.fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.loginreviewhero.R;
import com.example.administrator.loginreviewhero.Util;
import com.example.administrator.loginreviewhero.model.GenericStatus;
import com.example.administrator.loginreviewhero.model.Login;
import com.example.administrator.loginreviewhero.model.UserDetail;
import com.example.administrator.loginreviewhero.service.LoginService;
import com.example.administrator.loginreviewhero.view.activity.HeroListActivity;
import com.example.administrator.loginreviewhero.view.activity.MainActivity;

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

public class ProfileFragment extends Fragment {
    @BindView(R.id.toolbar_profile)
    Toolbar toolbarProfile;
    @BindView(R.id.image_profile)
    ImageView imageProfile;
    @BindView(R.id.show_name)
    TextView textShowName;
    @BindView(R.id.show_mail)
    TextView textShowMail;
    @BindView(R.id.show_tel)
    TextView textShowTel;
    @BindView(R.id.edit_name)
    EditText editName;
    @BindView(R.id.edit_mail)
    EditText editMail;
    @BindView(R.id.edit_tel)
    EditText editTel;
    @BindView(R.id.btn_logout)
    Button buttonLogout;
    @BindView(R.id.btn_ok)
    Button buttonOk;
    @BindView(R.id.btn_edit)
    Button buttonEdit;
    @BindView(R.id.btn_cancel)
    Button buttonCancel;
    private UserDetail userDetail;
    private ProgressDialog progressDialog;

    public static ProfileFragment newInstance(UserDetail userDetail) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle data = new Bundle();
        data.putParcelable(USER_DETAIL, userDetail);
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);
        toolbarProfile.setClickable(true);

        buttonOk.setVisibility(View.GONE);
        buttonCancel.setVisibility(View.GONE);
        editName.setVisibility(View.GONE);
        editMail.setVisibility(View.GONE);
        editTel.setVisibility(View.GONE);

        userDetail = getArguments().getParcelable(USER_DETAIL);
        if (userDetail != null) {
            textShowName.setText(userDetail.getMemberName());
            textShowMail.setText(userDetail.getMemberEmail());
            textShowTel.setText(userDetail.getMemberTel());
            Util.setDownloadImageView(getContext(), userDetail.getMemberImg(), imageProfile);
        }
        return view;
    }

    @OnClick(R.id.btn_logout)
    void onLogoutButtonClick() {
        final AlertDialog.Builder ab = new AlertDialog.Builder(getContext());
        ab.setMessage("Confirm to Log Out ? ");
        ab.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        ab.setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.cancel();
            }
        });
        ab.show();
    }

    @OnClick(R.id.btn_ok)
    void onOkButtonClick() {
        final String editTextName = editName.getText().toString();
        final String editTextMail = editMail.getText().toString();
        final String editTextTel = editTel.getText().toString();
        if (userDetail != null && !editTextName.equals("") && editName.getText().length() > 0) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            LoginService loginService = retrofit.create(LoginService.class);
            Call<GenericStatus> call = loginService.getEditData(userDetail.getMemberId(),
                    editTextName, editTextMail, editTextTel);
            final ProgressDialog progressDialog;
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage(" loading....");
            showDialog();

            call.enqueue(new Callback<GenericStatus>() {
                @Override
                public void onResponse(Call<GenericStatus> call, Response<GenericStatus> response) {
                    if (response.body().getStatusCode() == 1000) {
                        textShowName.setText(editTextName);
                        textShowMail.setText(editTextMail);
                        textShowTel.setText(editTextTel);
                        Toast.makeText(getContext(), "Successful...", Toast.LENGTH_LONG).show();
                        buttonOk.setVisibility(View.GONE);
                        buttonCancel.setVisibility(View.GONE);
                        editName.setVisibility(View.GONE);
                        editMail.setVisibility(View.GONE);
                        editTel.setVisibility(View.GONE);
                        buttonEdit.setVisibility(View.VISIBLE);
                        buttonLogout.setVisibility(View.VISIBLE);
                        textShowName.setVisibility(View.VISIBLE);
                        textShowMail.setVisibility(View.VISIBLE);
                        textShowTel.setVisibility(View.VISIBLE);
                        hideDialog();
                    } else {
                        Toast.makeText(getContext(), response.body().getStatusDescription(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<GenericStatus> call, Throwable t) {
                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    @OnClick(R.id.btn_edit)
    void onEditButtonClick() {
        editName.setVisibility(View.VISIBLE);
        editMail.setVisibility(View.VISIBLE);
        editTel.setVisibility(View.VISIBLE);
        buttonOk.setVisibility(View.VISIBLE);
        buttonCancel.setVisibility(View.VISIBLE);
        buttonEdit.setVisibility(View.GONE);
        buttonLogout.setVisibility(View.GONE);
        textShowName.setVisibility(View.GONE);
        textShowMail.setVisibility(View.GONE);
        textShowTel.setVisibility(View.GONE);
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
                Login login = response.body();
                if (login.getStatusCode() == 1000) {
                    List<UserDetail> userDetails = login.getUserDetails();
                    if (userDetail != null) {
                        editName.setText(userDetail.getMemberName());
                        editMail.setText(userDetail.getMemberEmail());
                        editTel.setText(userDetail.getMemberTel());
                        hideDialog();

                    }
                } else {
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

    @OnClick(R.id.btn_cancel)
    void onCancelButtonClick() {
        buttonOk.setVisibility(View.GONE);
        buttonCancel.setVisibility(View.GONE);
        editName.setVisibility(View.GONE);
        editMail.setVisibility(View.GONE);
        editTel.setVisibility(View.GONE);
        textShowName.setVisibility(View.VISIBLE);
        textShowMail.setVisibility(View.VISIBLE);
        textShowTel.setVisibility(View.VISIBLE);
        buttonEdit.setVisibility(View.VISIBLE);
        buttonLogout.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.image_back_profile)
    void onBackImageClick() {
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
