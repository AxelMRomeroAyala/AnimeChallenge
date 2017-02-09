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
import com.yakarex.animequiz.models.LevelStatModel;
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
    DataBaseHelper dbHelper;
    ScoreDbHelper scoreHelper;

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
        LevelsStatsAdapter adapter= new LevelsStatsAdapter(feedLevels(), getContext());
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(getContext());
        levelStatsRecycler.setLayoutManager(layoutManager);
        levelStatsRecycler.setAdapter(adapter);
    }

    private List<LevelStatModel> feedLevels(){

        List<LevelStatModel> levelList= new ArrayList<>();
        for (int x= 0; x<=FinalStringsUtils.lvlidarray.length-1; x++){
            LevelStatModel level= new LevelStatModel();
            level.setLevelName(getLvlNamebyId(FinalStringsUtils.lvlidarray[x]));
            level.setLevelScore(scoreHelper.getLevelScore(FinalStringsUtils.lvlidarray[x]));
            level.setStarStats(scoreHelper.starsStats(FinalStringsUtils.lvlidarray[x]));
            level.setLvlMaxScore(dbHelper.getLvlmaxScore(FinalStringsUtils.lvlidarray[x]));
            levelList.add(level);
        }

        return levelList;
    }

    private String getLvlNamebyId(int lvlId){

        String name = getResources().getString(R.string.level_name);

        switch (lvlId){
            case 100:
                name= getResources().getString(R.string.levelgames);
                break;
            case 101:
                name= getResources().getString(R.string.levelpets);
                break;
            case 102:
                name= getResources().getString(R.string.levelgames2);
                break;
            case 103:
                name= getResources().getString(R.string.levelmovies);
                break;
            default:
                name= name.replace("@@", String.valueOf(lvlId));
                break;
        }


        return name;
    }
}
