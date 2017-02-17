package com.yakarex.animequiz.models;

/**
 * Created by axel.romero on 06/01/2017.
 */

public class LevelStatModel {
    private int levelId;
    private String levelName;
    private int[] starStats;
    private int levelScore;
    private int lvlMaxScore;
    private boolean isUnlocked= false;

    public int getLevelId() {
        return levelId;
    }

    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }

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

    public boolean isUnlocked() {
        return isUnlocked;
    }

    public void setUnlocked(boolean unlocked) {
        isUnlocked = unlocked;
    }
}
