package com.yakarex.animequiz.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by axel.romero on 13/12/2016.
 */

public class AChaCharacterModel implements Parcelable {

    private int charid, level;
    private String genre, anime, fullname, uri;
    public AChaCharacterModel(int id, int lvl, String genre, String anime, String fullName, String uri){

        this.charid = id;
        this.level = lvl;
        this.genre = genre;
        this.anime = anime;
        this.fullname = fullName;
        this.uri = uri;

    }

    protected AChaCharacterModel(Parcel in) {
        charid = in.readInt();
        level = in.readInt();
        genre = in.readString();
        anime = in.readString();
        fullname = in.readString();
        uri = in.readString();
    }

    public static final Creator<AChaCharacterModel> CREATOR = new Creator<AChaCharacterModel>() {
        @Override
        public AChaCharacterModel createFromParcel(Parcel in) {
            return new AChaCharacterModel(in);
        }

        @Override
        public AChaCharacterModel[] newArray(int size) {
            return new AChaCharacterModel[size];
        }
    };

    public int getCharid() {
        return charid;
    }

    public int getLevel() {
        return level;
    }

    public String getGenre() {
        return genre;
    }

    public String getAnime() {
        return anime.trim().toLowerCase();
    }

    public String getFullname() {
        return fullname.trim().toLowerCase();
    }

    public String getUri() {
        return uri;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(charid);
        parcel.writeInt(level);
        parcel.writeString(genre);
        parcel.writeString(anime);
        parcel.writeString(fullname);
        parcel.writeString(uri);
    }
}
