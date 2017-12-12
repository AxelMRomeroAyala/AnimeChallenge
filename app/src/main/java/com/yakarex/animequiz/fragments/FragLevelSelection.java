package com.yakarex.animequiz.fragments;

import com.yakarex.animequiz.activities.MainFragActivity;
import com.yakarex.animequiz.R;
import com.yakarex.animequiz.R.id;
import com.yakarex.animequiz.adapters.LevelsAdapter;
import com.yakarex.animequiz.utils.DBUtil;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragLevelSelection extends Fragment implements LevelsAdapter.LevelInteractor{

    TextView totalScoreView;
    int totalScoreInt;
    View rootView;
    RecyclerView levelsRecycler;
    DBUtil dbUtil;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbUtil= new DBUtil(FragLevelSelection.this.getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.frag_level_selection_new, container, false);

        totalScoreView = rootView.findViewById(R.id.totalScoreView);
        levelsRecycler = rootView.findViewById(id.level_selection_recycler);

        return rootView;
    }

    public FragLevelSelection() {


    }

    @Override
    public void onResume() {

        String totalScore = dbUtil.getTotalScore();
        totalScoreView.setText(getString(R.string.gamescore) + " " + totalScore);

        totalScoreInt = Integer.parseInt(totalScore);

        LevelsAdapter levelsAdapter= new LevelsAdapter(dbUtil.getLevels(), getContext(), this);
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(getContext());
        levelsRecycler.setLayoutManager(layoutManager);
        levelsRecycler.setAdapter(levelsAdapter);

        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        dbUtil.finish();
    }

    @Override
    public void onLevelClicked(int lvlId) {

        ((MainFragActivity) getActivity()).openLevelbyId(lvlId);

    }
}
