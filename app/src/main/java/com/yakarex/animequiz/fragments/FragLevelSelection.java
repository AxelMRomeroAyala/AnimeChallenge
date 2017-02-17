package com.yakarex.animequiz.fragments;

import com.yakarex.animequiz.activities.MainFragActivity;
import com.yakarex.animequiz.R;
import com.yakarex.animequiz.R.id;
import com.yakarex.animequiz.adapters.LevelsAdapter;
import com.yakarex.animequiz.utils.FinalStringsUtils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragLevelSelection extends Fragment implements LevelsAdapter.LevelInteractor{

    TextView totalScoreView;
    static int[] lvlunlockinglogicarray;
    int totalScoreInt;
    View rootView;
    RecyclerView levelsRecycler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.frag_level_selection_new, container, false);

        totalScoreView = (TextView) rootView.findViewById(R.id.totalScoreView);
        levelsRecycler = (RecyclerView) rootView.findViewById(id.level_selection_recycler);

        lvlunlockinglogicarray = FinalStringsUtils.lvlunlockinglogicarray;

        return rootView;
    }

    public FragLevelSelection() {


    }

    @Override
    public void onResume() {

        String totalScore = ((MainFragActivity) getActivity()).getTotalScore();
        totalScoreView.setText(getString(R.string.gamescore) + " " + totalScore);

        totalScoreInt = Integer.parseInt(totalScore);

        LevelsAdapter levelsAdapter= new LevelsAdapter(((MainFragActivity)getActivity()).getLevels(), getContext(), this);
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(getContext());
        levelsRecycler.setLayoutManager(layoutManager);
        levelsRecycler.setAdapter(levelsAdapter);

        super.onResume();
    }

    @Override
    public void onLevelClicked(int lvlId) {

        ((MainFragActivity) getActivity()).openLevelbyId(lvlId);

    }
}
