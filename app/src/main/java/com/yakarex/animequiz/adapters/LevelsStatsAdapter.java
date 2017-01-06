package com.yakarex.animequiz.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yakarex.animequiz.R;

/**
 * Created by axel.romero on 06/01/2017.
 */

public class LevelsStatsAdapter extends RecyclerView.Adapter<LevelsStatsAdapter.ViewHolder> {


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_stats, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
    // each data item is just a string in this case

        TextView levelName, levelScore, starString;
        ImageView levelStar;

    public ViewHolder(View v) {
        super(v);

        levelName= (TextView) v.findViewById(R.id.stats_level_name);
        levelScore= (TextView) v.findViewById(R.id.stats_level_score);
        starString= (TextView) v.findViewById(R.id.stats_level_star_string);
        levelStar= (ImageView) v.findViewById(R.id.stats_level_star);

    }
}
}
