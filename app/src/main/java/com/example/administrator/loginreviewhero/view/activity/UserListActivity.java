package com.example.administrator.loginreviewhero.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.administrator.loginreviewhero.R;
import com.example.administrator.loginreviewhero.model.UserDetail;
import com.example.administrator.loginreviewhero.view.fragment.UserListFragment;

public class UserListActivity extends AppCompatActivity {
    UserListFragment userListFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        UserDetail userDetail = getIntent().getParcelableExtra("USER_DETAIL");

        userListFragment = userListFragment.newInstance(userDetail);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frg_user_list,userListFragment)
                .commit();
    }
}
