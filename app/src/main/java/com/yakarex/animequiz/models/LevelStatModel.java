package com.yakarex.animequiz.models;

/**
 * Created by axel.romero on 06/01/2017.
 */

public class LevelStatModel {
    private String levelName;
    private int[] starStats;
    private int levelScore;
    private int lvlMaxScore;

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public int[] getStarStats() {
        return starStats;
    }

    public void setStarStats(int[] starStats) {
        this.starStats = starStats;
    }

    public int getLevelScore() {
        return levelScore;
    }

    public void setLevelScore(int levelScore) {
        this.levelScore = levelScore;
    }

    public int getLvlMaxScore() {
        return lvlMaxScore;
    }

    public void setLvlMaxScore(int lvlMaxScore) {
        this.lvlMaxScore = lvlMaxScore;
    }
}
