package com.yakarex.animequiz.adapters;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.yakarex.animequiz.activities.MainFragActivity;
import com.yakarex.animequiz.R;
import com.yakarex.animequiz.fragments.FragCharacterSwiper;
import com.yakarex.animequiz.models.AChaCharacterModel;
import com.yakarex.animequiz.models.LevelStatModel;
import com.yakarex.animequiz.utils.ScoreDbHelper;

import java.util.List;


/**
 * Created by aromero on 22/10/15.
 */

public class CharactersRecyclerAdapter extends RecyclerView.Adapter<CharactersRecyclerAdapter.ViewHolder>  {

    private LevelStatModel levelStatModel;
    private Context context;
    private int lastPosition = -1;
    ScoreDbHelper scoreHelper;
    private Fragment frag;

    public CharactersRecyclerAdapter(LevelStatModel levelStatModel, Context context, Fragment frag){
        this.levelStatModel= levelStatModel;
        this.context= context;
        this.frag= frag;

        scoreHelper = new ScoreDbHelper(context);
        scoreHelper.openDataBase();
    }

    public LevelStatModel getLevelStatModel() {
        return levelStatModel;
    }

    public void setLevelStatModel(LevelStatModel levelStatModel) {
        this.levelStatModel = levelStatModel;
    }

    @Override
    public CharactersRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.gridchar, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final AChaCharacterModel model = levelStatModel.getCharacterModels().get(position);

        String imgPath=model.getUri();

        //imgPath= imgPath.replace("android.resource://com.yakarex.animequiz/drawable/", "");

        int resourceId = context.getResources().getIdentifier(imgPath, "drawable", context.getPackageName());
        String imageCharStringUri= "drawable://" + resourceId;

        int keyCharId= model.getCharid();
        int lvl =model.getLevel();

        int score = scoreHelper.getCharScore(keyCharId, lvl);
        //scoreHelper.close();

        if (score >0 && score< 40){
            holder.starImage.setImageResource(R.drawable.cupperstar);
            //ImageLoader.getInstance().displayImage(imageStarStringUri+R.drawable.cupperstar, holder.starImage);
        }
        else if (score >40 && score< 100){
            holder.starImage.setImageResource(R.drawable.silver);
            //ImageLoader.getInstance().displayImage(imageStarStringUri + R.drawable.silver, holder.starImage);
        }
        else if (score >= 100){
            holder.starImage.setImageResource(R.drawable.goldstar);
            //ImageLoader.getInstance().displayImage(imageStarStringUri + R.drawable.goldstar, holder.starImage);
        }
        else {
            holder.starImage.setImageResource(R.drawable.starempty);
            //ImageLoader.getInstance().displayImage(imageStarStringUri + R.drawable.starempty, holder.starImage);
        }

        //ImageLoader.getInstance().displayImage(model.getUri(), holder.charImage);

        int resID = context.getResources().getIdentifier(model.getUri(), "drawable",  context.getPackageName());
        holder.charImage.setImageResource(resID);
        //holder.charImage.setImageURI(model.getUri());

        setAnimation(holder.charImage
                ,holder.starImage, position);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            holder.charImage.setTransitionName("");
        }

        holder.charImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Fragment fragmentTwo= FragCharacterSwiper.newInstance(levelStatModel, position);


                ((MainFragActivity)context).changeFragment(fragmentTwo);
            }
        });
    }

    private void setAnimation(View viewToAnimate1, View viewToAnimate2, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate1.startAnimation(animation);
            viewToAnimate2.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return levelStatModel.getCharacterModels().size();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView charImage;
        public ImageView starImage;

        public ViewHolder(View v) {
            super(v);
            charImage = (ImageView) v.findViewById(R.id.charimg);
            starImage = (ImageView) v.findViewById(R.id.starimg);
        }
    }
}
