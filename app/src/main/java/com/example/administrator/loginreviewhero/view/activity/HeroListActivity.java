package com.example.administrator.loginreviewhero.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.administrator.loginreviewhero.R;
import com.example.administrator.loginreviewhero.model.UserDetail;
import com.example.administrator.loginreviewhero.view.fragment.HeroListFragment;

import static com.example.administrator.loginreviewhero.Constants.USER_DETAIL;

public class HeroListActivity extends AppCompatActivity {
    HeroListFragment heroListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero_list);

        UserDetail userDetail = getIntent().getParcelableExtra(USER_DETAIL);

        heroListFragment = HeroListFragment.newInstance(userDetail);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frg_hero_list, heroListFragment)
                .commit();
    }
}
