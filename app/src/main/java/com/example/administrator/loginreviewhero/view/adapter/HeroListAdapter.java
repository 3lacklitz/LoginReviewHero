package com.example.administrator.loginreviewhero.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.loginreviewhero.R;
import com.example.administrator.loginreviewhero.Util;
import com.example.administrator.loginreviewhero.model.Hero;
import com.example.administrator.loginreviewhero.view.activity.HeroDetailActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.administrator.loginreviewhero.Constants.HERO_LIST;

public class HeroListAdapter extends RecyclerView.Adapter<HeroListAdapter.HeroCardViewHolder> {
    private Context context;
    private List<Hero> heroes;

    public HeroListAdapter(Context context, List<Hero> heroes) {
        this.context = context;
        this.heroes = heroes;
    }

    @Override
    public HeroCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_card_view, parent, false);

        return new HeroCardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HeroCardViewHolder holder, final int position) {
        Util.setDownloadImageView(
                context,
                heroes.get(position).getImage(),
                holder.logoImageView);
        holder.heroName.setText(heroes.get(position).getTitle());
        holder.cardView.setBackgroundColor(Color.parseColor(heroes.get(position).getColor()));

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, HeroDetailActivity.class);

                intent.putExtra(HERO_LIST, heroes.get(position));

                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return heroes.size();
    }

    public class HeroCardViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.card_view)
        CardView cardView;

        @BindView(R.id.logo_image_hero)
        ImageView logoImageView;

        @BindView(R.id.hero_name)
        TextView heroName;

        public HeroCardViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
