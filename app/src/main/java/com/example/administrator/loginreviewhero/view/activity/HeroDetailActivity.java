package com.example.administrator.loginreviewhero.view.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.loginreviewhero.R;
import com.example.administrator.loginreviewhero.Util;
import com.example.administrator.loginreviewhero.model.Hero;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.administrator.loginreviewhero.Constants.HERO_LIST;

public class HeroDetailActivity extends AppCompatActivity {
    @BindView(R.id.background)
    LinearLayout background;
    @BindView(R.id.txt_name_hero)
    TextView nameDetail;
    @BindView(R.id.txt_intro)
    TextView introDetail;
    @BindView(R.id.txt_year)
    TextView yearDetail;
    @BindView(R.id.txt_text)
    TextView textDetail;
    @BindView(R.id.image_hero)
    ImageView imageHero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero_detail);
        ButterKnife.bind(this);

        //Get Parcelable
        Hero hero = getIntent().getParcelableExtra(HERO_LIST);

        nameDetail.setText(hero.getTitle());
        yearDetail.setText(hero.getYear());
        introDetail.setText(hero.getIntro());
        textDetail.setText(hero.getText());
        Util.setDownloadImageView(this, hero.getImage(), imageHero);
        background.setBackgroundColor(Color.parseColor(hero.getColor()));
    }
}
