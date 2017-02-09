package com.yakarex.animequiz.adapters;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yakarex.animequiz.R;
import com.yakarex.animequiz.models.LevelStatModel;

import java.util.List;

/**
 * Created by axel.romero on 06/01/2017.
 */

public class LevelsStatsAdapter extends RecyclerView.Adapter<LevelsStatsAdapter.ViewHolder> {

    List<LevelStatModel> levels;
    private Context context;

    public LevelsStatsAdapter(List<LevelStatModel> levels, Context context){
        this.levels= levels;
        this.context= context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_stats, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        int lvlScore= levels.get(position).getLevelScore();
        int lvlMaxScore= levels.get(position).getLvlMaxScore();


        holder.levelName.setText(levels.get(position).getLevelName());
        holder.levelScore.setText(String.valueOf(lvlScore));
        holder.starString.setText(String.valueOf(
                levels.get(position).getStarStats()[0])+
                " / "+String.valueOf(levels.get(position).getStarStats()[1])+
                " / "+String.valueOf(levels.get(position).getStarStats()[2])+"  ");

        holder.pBar.setMax(lvlMaxScore);

        if(lvlScore == lvlMaxScore){
            holder.pBar.getProgressDrawable().setColorFilter(context.getResources().getColor(R.color.golden), PorterDuff.Mode.SRC_IN);
        }
        else if(lvlScore >= (lvlMaxScore/2)){
            holder.pBar.getProgressDrawable().setColorFilter(context.getResources().getColor(R.color.silver), PorterDuff.Mode.SRC_IN);
        }
        else if(lvlScore <= (lvlMaxScore/2)){
            holder.pBar.getProgressDrawable().setColorFilter(context.getResources().getColor(R.color.bronze), PorterDuff.Mode.SRC_IN);
        }

        ObjectAnimator animation = ObjectAnimator.ofInt(holder.pBar, "progress", levels.get(position).getLevelScore());
        animation.setDuration(500); // 0.5 second
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();
    }

    @Override
    public int getItemCount() {
        return levels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
    // each data item is just a string in this case

        TextView levelName, levelScore, starString;
        ImageView levelStar;
        ProgressBar pBar;

    public ViewHolder(View v) {
        super(v);

        levelName= (TextView) v.findViewById(R.id.stats_level_name);
        levelScore= (TextView) v.findViewById(R.id.stats_level_score);
        starString= (TextView) v.findViewById(R.id.stats_level_star_string);
        levelStar= (ImageView) v.findViewById(R.id.stats_level_star);
        pBar= (ProgressBar) v.findViewById(R.id.stats_level_progress);

    }
}
}
