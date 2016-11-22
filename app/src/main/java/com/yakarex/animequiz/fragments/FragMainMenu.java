package com.yakarex.animequiz.fragments;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.common.SignInButton;
import com.yakarex.animequiz.FragStats;
import com.yakarex.animequiz.activities.MainFragActivity;
import com.yakarex.animequiz.R;
import com.yakarex.animequiz.utils.FinalStringsUtils;

/**
 * Created by aromero on 22/10/15.
 */
public class FragMainMenu extends Fragment {

    private Context context;

    private Button startBtn;
    private Button challengeBtn;
    private Button statsBtn;
    private Button optionsBtn;
    private Button audioButton;
    private Button vibrationButton;
    private Button disclaimerBtn;
    private Button fbBtn;
    private Button rateBtn;
    private Button musicBtn;
    private Button achievementsBtn;
    private Button leaderboardsBtn;
    private SignInButton signInBtn;

    private boolean isSignedIn= false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_main_menu, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        startBtn= (Button) view.findViewById(R.id.startButton);
        challengeBtn= (Button) view.findViewById(R.id.challengeButton);
        statsBtn= (Button) view.findViewById(R.id.statsButton);
        optionsBtn= (Button) view.findViewById(R.id.optionsButton);
        audioButton= (Button) view.findViewById(R.id.audioButton);
        vibrationButton= (Button) view.findViewById(R.id.vibrationButton);
        disclaimerBtn= (Button) view.findViewById(R.id.disclaimerButton);
        fbBtn= (Button) view.findViewById(R.id.likeUsButton);
        rateBtn= (Button) view.findViewById(R.id.rateUsButton);
        musicBtn= (Button) view.findViewById(R.id.musicButton);
        leaderboardsBtn= (Button) view.findViewById(R.id.leaderboard);
        achievementsBtn= (Button) view.findViewById(R.id.achievements);
        signInBtn= (SignInButton) view.findViewById(R.id.sign_in_button_frag);

        startBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                ((MainFragActivity)getActivity()).changeFragment(FragLevelSelection.instantiate(context, FragLevelSelection.class.getName()), true, false);
            }
        });
        challengeBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO challenge frag
            }
        });
        statsBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((MainFragActivity)getActivity()).changeFragment(FragOptions.instantiate(context, FragStats.class.getName()), true, false);
            }
        });
        optionsBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((MainFragActivity)getActivity()).changeFragment(FragOptions.instantiate(context, FragOptions.class.getName()), true, false);
            }
        });
        audioButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((MainFragActivity)getActivity()).audioManager();
                audioAndVibButtonsStatus();
            }
        });
        vibrationButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((MainFragActivity)getActivity()).vibrationManager();
                audioAndVibButtonsStatus();
            }
        });
        musicBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO manage music
            }
        });
        disclaimerBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((MainFragActivity)getActivity()).disclaimer();
            }
        });
        leaderboardsBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((MainFragActivity)getActivity()).leaderboardsClicked(v);
            }
        });
        achievementsBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((MainFragActivity)getActivity()).achievementClicked(v);
            }
        });
        signInBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((MainFragActivity)getActivity()).signInClicked();
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context= getActivity();

    }

    @Override
    public void onResume() {
        super.onResume();

        fadeInFromFarLeft(startBtn, 100);
        fadeInFromFarRight(challengeBtn, 200);
        fadeInFromFarLeft(statsBtn, 300);
        fadeInFromFarRight(optionsBtn, 400);
        enterFromLeft(vibrationButton, 100);
        enterFromLeft(audioButton, 100);
        enterFromLeft(disclaimerBtn, 100);
        enterFromRight(rateBtn, 100);
        enterFromRight(fbBtn, 100);
        enterFromRight(musicBtn, 100);

        isSignedIn= ((MainFragActivity)getActivity()).isSignedIn();

        if(isSignedIn){
            achievementsBtn.setVisibility(View.VISIBLE);
            leaderboardsBtn.setVisibility(View.VISIBLE);
            enterFromLeft(achievementsBtn, 100);
            enterFromRight(leaderboardsBtn, 100);
            signInBtn.setVisibility(View.INVISIBLE);
            setShowSignIn(false);
        }
        else {
            achievementsBtn.setVisibility(View.INVISIBLE);
            leaderboardsBtn.setVisibility(View.INVISIBLE);
            signInBtn.setVisibility(View.VISIBLE);
            setShowSignIn(true);
        }

    }

    public void audioAndVibButtonsStatus(){

        SharedPreferences settings = getActivity().getSharedPreferences("com.yacarex.animequiz", 0);
        boolean audioOff = settings.getBoolean("audioOff", false);
        boolean vibrationOff= settings.getBoolean("vibrationOff", false);

        if(audioOff){
            audioButton.setBackgroundResource(R.drawable.soundoffl);
        }

        else{
            audioButton.setBackgroundResource(R.drawable.soundonl);
        }
        if(vibrationOff){
            vibrationButton.setBackgroundResource(R.drawable.vibrateoffwshl);
        }
        else{
            vibrationButton.setBackgroundResource(R.drawable.vibrateonwshl);
        }

    }

    public void enterFromLeft(View viewToAnimate, long delay){
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.slideright);
        animation.setDuration(500);
        animation.setStartOffset(delay);
        viewToAnimate.startAnimation(animation);
    }

    public void enterFromRight(View viewToAnimate, long delay){
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.sliderleft);
        animation.setDuration(500);
        animation.setStartOffset(delay);
        viewToAnimate.startAnimation(animation);
    }

    public void fadeInFromBottom(View viewToAnimate, long delay){
        Animation animation= AnimationUtils.loadAnimation(getContext(), R.anim.fade_in_from_bottom);
        animation.setDuration(1000);
        animation.setStartOffset(delay);
        viewToAnimate.startAnimation(animation);
    }

    public void fadeInFromFarLeft(View viewToAnimate, long delay){
        Animation animation= AnimationUtils.loadAnimation(getContext(), R.anim.fade_in_from_far_left);
        animation.setDuration(700);
        animation.setStartOffset(delay);
        viewToAnimate.startAnimation(animation);
    }

    public void fadeInFromFarRight(View viewToAnimate, long delay){
        Animation animation= AnimationUtils.loadAnimation(getContext(), R.anim.fade_in_from_far_right);
        animation.setDuration(700);
        animation.setStartOffset(delay);
        viewToAnimate.startAnimation(animation);
    }

    public void setShowSignIn(boolean showSignIn){

        if(showSignIn){
            signInBtn.setVisibility(View.VISIBLE);
            achievementsBtn.setVisibility(View.INVISIBLE);
            leaderboardsBtn.setVisibility(View.INVISIBLE);

            isSignedIn= false;
        }
        else {
            signInBtn.setVisibility(View.INVISIBLE);
            achievementsBtn.setVisibility(View.VISIBLE);
            leaderboardsBtn.setVisibility(View.VISIBLE);
            enterFromLeft(achievementsBtn, 100);
            enterFromRight(leaderboardsBtn, 100);

            isSignedIn= true;
        }

    }
}
