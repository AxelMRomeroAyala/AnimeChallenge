package com.yakarex.animequiz.adapters;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yakarex.animequiz.R;
import com.yakarex.animequiz.models.LevelStatModel;
import com.yakarex.animequiz.utils.FinalStringsUtils;

import java.util.List;

/**
 * Created by axel.romero on 06/01/2017.
 */

public class LevelsAdapter extends RecyclerView.Adapter<LevelsAdapter.ViewHolder> {

    private List<LevelStatModel> levels;
    private Context context;
    private LevelInteractor levelInteractor;

    public LevelsAdapter(List<LevelStatModel> levels, Context context, LevelInteractor interactor){
        this.levels= levels;
        this.context= context;
        this.levelInteractor= interactor;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.levelbutton, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        int lvlScore= levels.get(position).getLevelScore();
        int lvlMaxScore= levels.get(position).getLvlMaxScore();


        holder.levelName.setText(levels.get(position).getLevelName());

        holder.levePBar.setMax(lvlMaxScore);

        ObjectAnimator animation = ObjectAnimator.ofInt(holder.levePBar, "progress", levels.get(position).getLevelScore());
        animation.setDuration(500); // 0.5 second
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();

        if(lvlScore == lvlMaxScore){
            holder.levePBar.getProgressDrawable().setColorFilter(context.getResources().getColor(R.color.golden), PorterDuff.Mode.SRC_IN);
        }
        else if(lvlScore >= (lvlMaxScore/2)){
            holder.levePBar.getProgressDrawable().setColorFilter(context.getResources().getColor(R.color.silver), PorterDuff.Mode.SRC_IN);
        }
        else if(lvlScore <= (lvlMaxScore/2)){
            holder.levePBar.getProgressDrawable().setColorFilter(context.getResources().getColor(R.color.bronze), PorterDuff.Mode.SRC_IN);
        }

        if(levels.get(position).isUnlocked()){

            holder.levelButton.setClickable(true);
            setlvlStar(position, holder.levelStar);
            holder.levelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    levelInteractor.onLevelClicked(FinalStringsUtils.lvlidarray[position]);
                }
            });
        }

        else {
            holder.levelButton.setClickable(false);
            holder.levelStar.setImageResource(R.drawable.locked);
        }

    }

    @Override
    public int getItemCount() {
        return levels.size();
    }

    public void setlvlStar(int position, ImageView view) {
        Drawable cupper = context.getResources().getDrawable(R.drawable.cupperstar);
        Drawable silver = context.getResources().getDrawable(R.drawable.silver);
        Drawable gold = context.getResources().getDrawable(R.drawable.goldstar);
        Drawable empty= context.getResources().getDrawable(R.drawable.starempty);

        int score = levels.get(position).getLevelScore();
        int maxscore = levels.get(position).getLvlMaxScore();

        if (score > 0) {

            view.setImageDrawable(cupper);

        }
        else if (score > (maxscore / 2)) {

            view.setImageDrawable(silver);
        }
        else if (score >= maxscore) {

            view.setImageDrawable(gold);

        }
        else{
            view.setImageDrawable(empty);
        }
    }

    public interface LevelInteractor{
        void onLevelClicked(int lvlId);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
    // each data item is just a string in this case

        RelativeLayout levelButton;
        TextView levelName;
        ImageView levelStar;
        ProgressBar levePBar;

    public ViewHolder(View v) {
        super(v);

        levelButton= (RelativeLayout)  v.findViewById(R.id.customlvlselbuttom);
        levelName= (TextView) v.findViewById(R.id.lvlsellvlname);
        levelStar= (ImageView) v.findViewById(R.id.lvlsellvlstar);
        levePBar= (ProgressBar) v.findViewById(R.id.lvlsellvlpbar);

    }
}
}
