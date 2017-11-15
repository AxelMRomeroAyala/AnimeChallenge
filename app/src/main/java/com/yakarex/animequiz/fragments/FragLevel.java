package com.yakarex.animequiz.fragments;

import com.yakarex.animequiz.adapters.CharactersRecyclerAdapter;
import com.yakarex.animequiz.R;
import com.yakarex.animequiz.R.id;
import com.yakarex.animequiz.models.LevelStatModel;
import com.yakarex.animequiz.utils.DBUtil;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import io.paperdb.Paper;

public class FragLevel extends Fragment {

    static int indexgridposition= 0;
    int lvl;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter charRecyclerAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    GridView gv;
    TextView scoreView;
    private DBUtil dbUtil;
    private LevelStatModel levelStatModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        dbUtil = new DBUtil(getContext());

        lvl = this.getArguments().getInt("lvl");

        levelStatModel= dbUtil.getLevels().get(lvl);

        if(lvl == 0){
            if (Paper.book().read("randomLevel")!= null) {
                levelStatModel = Paper.book().read("randomLevel");
            }
            else {
                Paper.book().write("randomLevel", levelStatModel);
            }
        }

        return inflater.inflate(R.layout.frag_level, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.characters_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        //mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new GridLayoutManager(getContext(), 3);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        charRecyclerAdapter = new CharactersRecyclerAdapter(levelStatModel, getActivity(), this);
        mRecyclerView.setAdapter(charRecyclerAdapter);

    }

    @Override
    public void onResume() {
        super.onResume();

        charRecyclerAdapter.notifyDataSetChanged();
        updateScoreView();

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
        scoreView = (TextView) getActivity().findViewById(id.scoreView);
        scoreView.setText(scoreString);
    }
}
