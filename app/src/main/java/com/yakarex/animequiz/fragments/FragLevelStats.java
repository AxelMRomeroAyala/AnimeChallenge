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
import com.yakarex.animequiz.activities.MainFragActivity;
import com.yakarex.animequiz.adapters.LevelsStatsAdapter;
import com.yakarex.animequiz.models.LevelStatModel;
import com.yakarex.animequiz.utils.DBUtil;
import com.yakarex.animequiz.utils.DataBaseHelper;
import com.yakarex.animequiz.utils.FinalStringsUtils;
import com.yakarex.animequiz.utils.ScoreDbHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by axel.romero on 06/01/2017.
 */

public class FragLevelStats extends Fragment {

    RecyclerView levelStatsRecycler;
    MainFragActivity mainFragActivityInstance;
    DBUtil dbUtil;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbUtil= new DBUtil(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.levels_stats_tab, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mainFragActivityInstance= ((MainFragActivity)getActivity());

        levelStatsRecycler= (RecyclerView) view.findViewById(R.id.levels_stats_recycler);
        List<LevelStatModel> levels= dbUtil.getLevels();
        levels.remove(0);
        LevelsStatsAdapter adapter= new LevelsStatsAdapter(levels, getContext());
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(getContext());
        levelStatsRecycler.setLayoutManager(layoutManager);
        levelStatsRecycler.setAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dbUtil.finish();
    }
}
