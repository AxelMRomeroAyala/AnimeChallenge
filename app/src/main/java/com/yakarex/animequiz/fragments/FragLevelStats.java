package com.yakarex.animequiz.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yakarex.animequiz.R;
import com.yakarex.animequiz.adapters.LevelsStatsAdapter;
import com.yakarex.animequiz.utils.DataBaseHelper;
import com.yakarex.animequiz.utils.FinalStringsUtils;
import com.yakarex.animequiz.utils.ScoreDbHelper;

/**
 * Created by axel.romero on 06/01/2017.
 */

public class FragLevelStats extends Fragment {

    RecyclerView levelStatsRecycler;
    DataBaseHelper dbHelper;
    ScoreDbHelper scoreHelper;
    int levelQuantity= FinalStringsUtils.lvlunlockinglogicarray.length -1;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        dbHelper= new DataBaseHelper(getActivity());
        dbHelper.openDataBase();
        scoreHelper= new ScoreDbHelper(getActivity());
        scoreHelper.openDataBase();

        return inflater.inflate(R.layout.levels_stats_tab, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        levelStatsRecycler= (RecyclerView) view.findViewById(R.id.levels_stats_recycler);
        LevelsStatsAdapter adapter= new LevelsStatsAdapter();
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(getContext());
        levelStatsRecycler.setLayoutManager(layoutManager);
        levelStatsRecycler.setAdapter(adapter);
    }
}
