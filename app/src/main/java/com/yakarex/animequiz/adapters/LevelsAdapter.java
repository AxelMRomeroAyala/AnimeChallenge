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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yakarex.animequiz.BuildConfig;
import com.yakarex.animequiz.R;
import com.yakarex.animequiz.models.LevelStatModel;
import com.yakarex.animequiz.models.MessageEvent;
import com.yakarex.animequiz.utils.FinalStringsUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by axel.romero on 06/01/2017.
 */

public class LevelsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<LevelStatModel> levels;
    private Context context;
    private LevelInteractor levelInteractor;

    private final static int RANDOMIZED_LEVEL = 7474;
    private final static int NORMAL_LEVEL = 8080;

    public LevelsAdapter(List<LevelStatModel> levels, Context context, LevelInteractor interactor) {
        this.levels = levels;
        this.context = context;
        this.levelInteractor = interactor;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == NORMAL_LEVEL) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.levelbutton, parent, false);

            return new ViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.random_level_button, parent, false);

            return new ViewHolderRandom(v);
        }
    }

    @Override
    public int getItemViewType(int position) {

        if (position == 0) {
            return RANDOMIZED_LEVEL;
        } else {
            return NORMAL_LEVEL;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        switch (holder.getItemViewType()) {
            case NORMAL_LEVEL:
                ViewHolder viewHolder = (ViewHolder) holder;
                int lvlScore = levels.get(position).getLevelScore();
                int lvlMaxScore = levels.get(position).getLvlMaxScore();

                viewHolder.levelName.setText(levels.get(position).getLevelName());

                viewHolder.levePBar.setMax(lvlMaxScore);

                ObjectAnimator animation = ObjectAnimator.ofInt(viewHolder.levePBar, "progress", levels.get(position).getLevelScore());
                animation.setDuration(500); // 0.5 second
                animation.setInterpolator(new DecelerateInterpolator());
                animation.start();

                if (lvlScore == lvlMaxScore) {
                    viewHolder.levePBar.getProgressDrawable().setColorFilter(context.getResources().getColor(R.color.golden), PorterDuff.Mode.SRC_IN);
                } else if (lvlScore >= (lvlMaxScore / 2)) {
                    viewHolder.levePBar.getProgressDrawable().setColorFilter(context.getResources().getColor(R.color.silver), PorterDuff.Mode.SRC_IN);
                } else if (lvlScore <= (lvlMaxScore / 2)) {
                    viewHolder.levePBar.getProgressDrawable().setColorFilter(context.getResources().getColor(R.color.bronze), PorterDuff.Mode.SRC_IN);
                }

                viewHolder.levelButton.setClickable(true);
                setlvlStar(position, viewHolder.levelStar);
                viewHolder.levelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        levelInteractor.onLevelClicked(FinalStringsUtils.lvlidarray[position]);
                    }
                });

                if (!levels.get(position).isUnlocked()) {

                    if(!BuildConfig.DEBUG){
                        viewHolder.levelButton.setClickable(false);
                        viewHolder.levelStar.setImageResource(R.drawable.locked);
                    }
                }
                break;

            case RANDOMIZED_LEVEL:
                ViewHolderRandom viewHolderRandom = (ViewHolderRandom) holder;
                viewHolderRandom.levelName.setText(levels.get(position).getLevelName());
                viewHolderRandom.levelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        levelInteractor.onLevelClicked(FinalStringsUtils.lvlidarray[position]);
                    }
                });

                break;
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
        Drawable empty = context.getResources().getDrawable(R.drawable.starempty);

        int score = levels.get(position).getLevelScore();
        int maxscore = levels.get(position).getLvlMaxScore();

        if (score > 0 && score < maxscore / 2) {

            view.setImageDrawable(cupper);

        } else if (score >= maxscore / 2 && score < maxscore) {

            view.setImageDrawable(silver);
        } else if (score >= maxscore) {

            view.setImageDrawable(gold);

        } else {
            view.setImageDrawable(empty);
        }
    }

    public interface LevelInteractor {
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

            levelButton = v.findViewById(R.id.customlvlselbuttom);
            levelName = v.findViewById(R.id.lvlsellvlname);
            levelStar = v.findViewById(R.id.lvlsellvlstar);
            levePBar = v.findViewById(R.id.lvlsellvlpbar);

        }
    }

    public class ViewHolderRandom extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        LinearLayout levelButton;
        TextView levelName;
        ImageView rerandomizeButton;

        public ViewHolderRandom(View v) {
            super(v);

            levelButton = v.findViewById(R.id.customlvlselbuttom);
            levelName = v.findViewById(R.id.lvlsellvlname);
            rerandomizeButton = v.findViewById(R.id.rerandomize_level);

        }
    }
}
