package com.yakarex.animequiz.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.yakarex.animequiz.R;
import com.yakarex.animequiz.fragments.FragLevel;
import com.yakarex.animequiz.models.LevelStatModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by axel.romero on 10/10/2017.
 */

public class DBUtil {
    private DataBaseHelper dataBaseHelper;
    private ScoreDbHelper scoreHelper;
    private Context context;

    public DBUtil(Context context) {
        this.context= context;
        dataBaseHelper = new DataBaseHelper(context);
        scoreHelper = new ScoreDbHelper(context);

        scoreHelper.createDb();
        try {
            dataBaseHelper.createDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        scoreHelper.openDataBase();
        dataBaseHelper.openDataBase();

    }

    public List<LevelStatModel> getLevels() {

        List<LevelStatModel> levelList = new ArrayList<>();
        for (int x = 0; x <= FinalStringsUtils.lvlidarray.length - 1; x++) {
            LevelStatModel level = new LevelStatModel();

            String lvlName = getLvlNamebyId(FinalStringsUtils.lvlidarray[x]);

            level.setLevelId(FinalStringsUtils.lvlidarray[x]);
            level.setLevelName(lvlName);
            level.setLevelScore(getLevelScore(FinalStringsUtils.lvlidarray[x]));
            level.setStarStats(getStarStats(FinalStringsUtils.lvlidarray[x]));
            level.setLvlMaxScore(getLevelMaxScore(FinalStringsUtils.lvlidarray[x]));

            if (Integer.parseInt(getTotalScore()) >= FinalStringsUtils.lvlunlockinglogicarray[x]) {
                level.setUnlocked(true);
            }

            levelList.add(level);
        }

        return levelList;
    }

    public String getTotalScore() {

        return String.valueOf(scoreHelper.getTotalScore());
    }

    public int getLevelScore(int level) {


        return scoreHelper.getLevelScore(level);
    }

    public int getLevelMaxScore(int level) {

        return dataBaseHelper.getLvlmaxScore(level);
    }

    public int[] getStarStats(int level) {
        return scoreHelper.starsStats(level);
    }

    public int getCharScore(int id, int level) {

        return scoreHelper.getCharScore(id, level);
    }

    public void setCharScore(int charId, int score, int level) {

        scoreHelper.setCharScore(charId, score, level);

    }

    public String getCharInputedAnime(int id) {

        if (scoreHelper.getInputedAnime(id) != null) {
            return scoreHelper.getInputedAnime(id);
        } else {

            return dataBaseHelper.getCharacter(id).getAnime().split("@")[0];
        }

    }

    public String getCharInputedName(int id, boolean avoidHinting) {

        if (scoreHelper.getInputedName(id) != null) {
            return scoreHelper.getInputedName(id);
        } else {
            if (avoidHinting) {
                return "";
            } else {
                return dataBaseHelper.getCharacter(id).getFullname().split("@")[0];
            }
        }

    }

    public void setCharInputedAnime(int id, String anime) {

        scoreHelper.setInputedAnime(id, anime);

    }

    public void setCharInputedName(int id, String name) {

        scoreHelper.setInputedName(id, name);

    }

    public void resetScore() {

        scoreHelper.deleteScoretable();

    }

    public String getLvlNamebyId(int lvlId) {

        String name = context.getResources().getString(R.string.level_name);

        switch (lvlId) {
            case 100:
                name = context.getResources().getString(R.string.levelgames);
                break;
            case 101:
                name = context.getResources().getString(R.string.levelpets);
                break;
            case 102:
                name = context.getResources().getString(R.string.levelgames2);
                break;
            case 103:
                name = context.getResources().getString(R.string.levelmovies);
                break;
            default:
                name = name.replace("@@", String.valueOf(lvlId));
                break;
        }


        return name;
    }

    public DataBaseHelper getDataBaseHelper() {
        return dataBaseHelper;
    }

    public ScoreDbHelper getScoreHelper() {
        return scoreHelper;
    }

    public void finish(){
        dataBaseHelper.close();
        scoreHelper.close();
    }
}
