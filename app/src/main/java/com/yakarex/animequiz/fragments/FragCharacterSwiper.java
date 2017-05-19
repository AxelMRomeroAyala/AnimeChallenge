package com.yakarex.animequiz.fragments;

import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.transition.ChangeBounds;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yakarex.animequiz.R;
import com.yakarex.animequiz.activities.MainFragActivity;
import com.yakarex.animequiz.adapters.CharacterPagesAdapter;
import com.yakarex.animequiz.models.AChaCharacterModel;
import com.yakarex.animequiz.models.MessageEvent;
import com.yakarex.animequiz.utils.FinalStringsUtils;
import com.yakarex.animequiz.utils.ZoomOutPageTransformer;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by axel.romero on 13/12/2016.
 */

public class FragCharacterSwiper extends Fragment {

    CharacterPagesAdapter charAdapter;

    Cursor cursor;
    int position;
    int charid;

    int level;

    int score;

    String scoreString;
    TextView charscoreView;

    ViewPager characterPager;
    AChaCharacterModel characterModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            this.setSharedElementEnterTransition((new ChangeBounds()));
        }

        position= getArguments().getInt("position");
        cursor= ((MainFragActivity)getActivity()).getLvlCursor();
        cursor.moveToPosition(position);

        charAdapter= new CharacterPagesAdapter(getFragmentManager(), getContext(), cursor);

        View rootView = inflater.inflate(R.layout.frag_swiper, container,
                false);

        charscoreView = (TextView) rootView.findViewById(R.id.scoreviewchar);
        characterPager= (ViewPager) rootView.findViewById(R.id.characters_view_pager);
        characterPager.setPageTransformer(true, new ZoomOutPageTransformer());
        characterPager.setAdapter(charAdapter);
        characterPager.setCurrentItem(position);

        characterModel= new AChaCharacterModel(cursor);

        characterPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                cursor.moveToPosition(position);
                characterModel= new AChaCharacterModel(cursor);

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        showScore();

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
     if(event.message.equals(FinalStringsUtils.UPDATESCORE)){
         showScore();
     }
    }

    public void showScore() {
        // get and show the total and level score
        if (characterModel.getLevel() == 100) {
            String totalscoreText = (String) getText(R.string.totalscore);
            String lvlscoreText = (String) getText(R.string.lvlscore);
            String lvlname = (String) getText(R.string.games);
            scoreString = lvlscoreText + " " + lvlname + ": "
                    + String.valueOf(((MainFragActivity) getActivity()).getLevelScore(characterModel.getLevel())) + " "
                    + totalscoreText + ": "
                    + String.valueOf(((MainFragActivity) getActivity()).getTotalScore());

        }

        if (characterModel.getLevel() == 101) {
            String totalscoreText = (String) getText(R.string.totalscore);
            String lvlscoreText = (String) getText(R.string.lvlscore);
            String lvlname = "Pets";
            scoreString = lvlscoreText + " " + lvlname + ": "
                    + String.valueOf(((MainFragActivity) getActivity()).getLevelScore(characterModel.getLevel())) + " "
                    + totalscoreText + ": "
                    + String.valueOf(((MainFragActivity) getActivity()).getTotalScore());

        } else {
            String totalscoreText = (String) getText(R.string.totalscore);
            String lvlscoreText = (String) getText(R.string.lvlscore);
            scoreString = lvlscoreText + " " + String.valueOf(characterModel.getLevel()) + ": "
                    + String.valueOf(((MainFragActivity) getActivity()).getLevelScore(characterModel.getLevel())) + " "
                    + totalscoreText + ": "
                    + String.valueOf(((MainFragActivity) getActivity()).getTotalScore());
        }

        charscoreView.setText(scoreString);
        //hintsNumber = Integer.parseInt(((MainFragActivity) getActivity()).getTotalScore()) / 100;
    }
}
