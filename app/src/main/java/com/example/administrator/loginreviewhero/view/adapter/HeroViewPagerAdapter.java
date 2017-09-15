package com.example.administrator.loginreviewhero.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.loginreviewhero.R;
import com.example.administrator.loginreviewhero.Util;
import com.example.administrator.loginreviewhero.model.Hero;
import com.example.administrator.loginreviewhero.view.activity.HeroDetailActivity;

import java.util.List;

import static com.example.administrator.loginreviewhero.Constants.HERO_LIST;

public class HeroViewPagerAdapter extends PagerAdapter{
    private Context context;
    private List<Hero> heroes;
    private LayoutInflater layoutInflater;

    public HeroViewPagerAdapter(Context context, List<Hero> heroes) {
        this.context = context;
        this.heroes = heroes;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = layoutInflater.inflate(R.layout.image_hero_viewpager, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.item_viewpager);
        TextView heroName = (TextView) itemView.findViewById(R.id.name_hero_viewpager);
        heroName.setText(heroes.get(position).getTitle());

        Util.setDownloadImageView(context, heroes.get(position).getImage(), imageView);

        container.addView(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, HeroDetailActivity.class);

                intent.putExtra(HERO_LIST, heroes.get(position));

                context.startActivity(intent);
            }
        });
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }
}
