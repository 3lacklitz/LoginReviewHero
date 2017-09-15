package com.example.administrator.loginreviewhero.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.administrator.loginreviewhero.R;
import com.example.administrator.loginreviewhero.model.UserDetail;
import com.example.administrator.loginreviewhero.view.fragment.ProfileFragment;

public class ProfileActivity extends AppCompatActivity {
    ProfileFragment profileFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        UserDetail userDetail = getIntent().getParcelableExtra("USER_DETAIL");

        profileFragment = ProfileFragment.newInstance(userDetail);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frg_profile, profileFragment)
                .commit();
    }
}
