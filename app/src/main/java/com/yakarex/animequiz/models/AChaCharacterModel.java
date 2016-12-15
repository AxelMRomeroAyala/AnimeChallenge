package com.yakarex.animequiz.models;

import android.database.Cursor;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by axel.romero on 13/12/2016.
 */

public class AChaCharacterModel implements Parcelable {

    private int charid, level;
    private String genre, anime, fullname;
    private Uri uri;

    public AChaCharacterModel(Cursor cursor){

        charid = cursor.getInt(1);
        level = cursor.getInt(2);
        genre = cursor.getString(3);
        anime = cursor.getString(4);
        fullname = cursor.getString(5);
        uri = Uri.parse(cursor.getString(6));

    }

    protected AChaCharacterModel(Parcel in) {
        charid = in.readInt();
        level = in.readInt();
        genre = in.readString();
        anime = in.readString();
        fullname = in.readString();
        uri = in.readParcelable(Uri.class.getClassLoader());
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
        return anime;
    }

    public String getFullname() {
        return fullname;
    }

    public Uri getUri() {
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
        parcel.writeParcelable(uri, i);
    }
}
