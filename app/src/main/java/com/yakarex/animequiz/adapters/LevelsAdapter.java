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

public class LevelsAdapter extends RecyclerView.Adapter<LevelsAdapter.ViewHolder> {

    List<LevelStatModel> levels;
    private Context context;

    public LevelsAdapter(List<LevelStatModel> levels, Context context){
        this.levels= levels;
        this.context= context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.levelbutton, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        int lvlScore= levels.get(position).getLevelScore();
        int lvlMaxScore= levels.get(position).getLvlMaxScore();


        holder.levelName.setText(levels.get(position).getLevelName());

        holder.levePBar.setMax(lvlMaxScore);

        if(lvlScore == lvlMaxScore){
            holder.levePBar.getProgressDrawable().setColorFilter(context.getResources().getColor(R.color.golden), PorterDuff.Mode.SRC_IN);
        }
        else if(lvlScore >= (lvlMaxScore/2)){
            holder.levePBar.getProgressDrawable().setColorFilter(context.getResources().getColor(R.color.silver), PorterDuff.Mode.SRC_IN);
        }
        else if(lvlScore <= (lvlMaxScore/2)){
            holder.levePBar.getProgressDrawable().setColorFilter(context.getResources().getColor(R.color.bronze), PorterDuff.Mode.SRC_IN);
        }

        ObjectAnimator animation = ObjectAnimator.ofInt(holder.levePBar, "progress", levels.get(position).getLevelScore());
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

        TextView levelName;
        ImageView levelStar;
        ProgressBar levePBar;

    public ViewHolder(View v) {
        super(v);

        levelName= (TextView) v.findViewById(R.id.lvlsellvlname);
        levelStar= (ImageView) v.findViewById(R.id.lvlsellvlstar);
        levePBar= (ProgressBar) v.findViewById(R.id.lvlsellvlpbar);

    }
}
}
