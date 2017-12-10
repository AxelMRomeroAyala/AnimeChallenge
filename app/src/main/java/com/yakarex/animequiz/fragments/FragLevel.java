package com.yakarex.animequiz.fragments;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.yakarex.animequiz.adapters.CharactersRecyclerAdapter;
import com.yakarex.animequiz.R;
import com.yakarex.animequiz.R.id;
import com.yakarex.animequiz.models.LevelStatModel;
import com.yakarex.animequiz.utils.DBUtil;
import com.yakarex.animequiz.utils.SimpleCacheController;
import com.yakarex.animequiz.utils.Utils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import io.paperdb.Paper;

public class FragLevel extends Fragment implements RewardedVideoAdListener {

    static int indexgridposition = 0;
    int lvl;
    private RecyclerView mRecyclerView;
    private CharactersRecyclerAdapter charRecyclerAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FrameLayout reRandomizeLayout;
    private Button reRandomizeButton;
    private ProgressBar reRandomizePBar;
    GridView gv;
    TextView scoreView;
    private DBUtil dbUtil;
    private LevelStatModel levelStatModel;
    SimpleCacheController simpleCacheController;
    private RewardedVideoAd mRewardedVideoAd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(getActivity());
        mRewardedVideoAd.setRewardedVideoAdListener(this);
        simpleCacheController = new SimpleCacheController(getContext());
        dbUtil = new DBUtil(getContext());

        lvl = this.getArguments().getInt("lvl");

        levelStatModel = dbUtil.getLevels().get(lvl);

        return inflater.inflate(R.layout.frag_level, container, false);
    }

    private boolean hasRandomStored() {

        return Paper.book().read("randomLevel") != null;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = view.findViewById(R.id.characters_recycler_view);
        reRandomizeButton = view.findViewById(id.re_randomize_button);
        reRandomizePBar= view.findViewById(id.re_randomize_button_progress_bar);
        reRandomizeLayout= view.findViewById(id.re_randomize);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        //mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new GridLayoutManager(getContext(), 3);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        charRecyclerAdapter = new CharactersRecyclerAdapter(levelStatModel, getActivity(), this);
        mRecyclerView.setAdapter(charRecyclerAdapter);

        if(lvl==0){
            reRandomizeLayout.setVisibility(View.VISIBLE);
            loadRewardedVideoAd();
            if (hasRandomStored()) {
                levelStatModel = Paper.book().read("randomLevel");
            } else {
                Paper.book().write("randomLevel", levelStatModel);
            }
        }

        reRandomizeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utils.hasDailyRandom()) {
                    reRandomize();
                } else {
                    if (mRewardedVideoAd.isLoaded()) {
                        mRewardedVideoAd.show();
                    }
                }
            }
        });

    }

    private void reRandomize() {
        levelStatModel = dbUtil.getLevels().get(lvl);
        charRecyclerAdapter.setLevelStatModel(levelStatModel);
        charRecyclerAdapter.notifyDataSetChanged();

        Paper.book().write("randomLevel", levelStatModel);
    }


    @Override
    public void onResume() {
        super.onResume();
        charRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        dbUtil.finish();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            //do when hidden
        } else {
            //do when show
            mRecyclerView.setAdapter(charRecyclerAdapter);
            updateScoreView();
        }
    }

    public void updateScoreView() {
        String scoreString;

        if (lvl == 100) {
            String totalscoreText = (String) getText(R.string.totalscore);
            String lvlscoreText = (String) getText(R.string.lvlscore);
            String lvlname = (String) getText(R.string.games);
            scoreString = lvlscoreText + " " + lvlname + ": " + String.valueOf(dbUtil.getLevelScore(lvl)) + " " + totalscoreText + ": " + String.valueOf(dbUtil.getTotalScore());
        } else {
            String totalscoreText = (String) getText(R.string.totalscore);
            String lvlscoreText = (String) getText(R.string.lvlscore);
            scoreString = lvlscoreText + " " + String.valueOf(lvl) + ": " + String.valueOf(dbUtil.getLevelScore(lvl)) + " " + totalscoreText + ": " + String.valueOf(dbUtil.getTotalScore());
        }
        //Find and set the score data on the level
        scoreView = getActivity().findViewById(id.scoreView);
        scoreView.setText(scoreString);
    }

    private void loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917",
                new AdRequest.Builder().build());

        reRandomizePBar.setVisibility(View.VISIBLE);
        reRandomizeButton.setVisibility(View.GONE);
    }

    @Override
    public void onRewardedVideoAdLoaded() {
        reRandomizePBar.setVisibility(View.GONE);
        reRandomizeButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {
        loadRewardedVideoAd();
    }

    @Override
    public void onRewarded(RewardItem rewardItem) {
        reRandomize();
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {

    }
}
