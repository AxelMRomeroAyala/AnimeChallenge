package com.yakarex.animequiz.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by axel.romero on 06/01/2017.
 */

public class LevelStatModel implements Parcelable {
    private int levelId;
    private String levelName;
    private int[] starStats;
    private int levelScore;
    private int lvlMaxScore;
    private boolean isUnlocked= false;
    private List<AChaCharacterModel> characterModels;

    public LevelStatModel() {
    }

    public LevelStatModel(Parcel in) {
        levelId = in.readInt();
        levelName = in.readString();
        starStats = in.createIntArray();
        levelScore = in.readInt();
        lvlMaxScore = in.readInt();
        isUnlocked = in.readByte() != 0;
        characterModels = in.createTypedArrayList(AChaCharacterModel.CREATOR);
    }

    public static final Creator<LevelStatModel> CREATOR = new Creator<LevelStatModel>() {
        @Override
        public LevelStatModel createFromParcel(Parcel in) {
            return new LevelStatModel(in);
        }

        @Override
        public LevelStatModel[] newArray(int size) {
            return new LevelStatModel[size];
        }
    };

    public List<AChaCharacterModel> getCharacterModels() {
        return characterModels;
    }

    public void setCharacterModels(List<AChaCharacterModel> characterModels) {
        this.characterModels = characterModels;
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(levelId);
        dest.writeString(levelName);
        dest.writeIntArray(starStats);
        dest.writeInt(levelScore);
        dest.writeInt(lvlMaxScore);
        dest.writeByte((byte) (isUnlocked ? 1 : 0));
        dest.writeTypedList(characterModels);
    }
}
