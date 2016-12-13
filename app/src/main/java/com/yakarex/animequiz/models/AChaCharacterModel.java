package com.yakarex.animequiz.models;

import android.database.Cursor;
import android.net.Uri;

import java.io.Serializable;

/**
 * Created by axel.romero on 13/12/2016.
 */

public class AChaCharacterModel implements Serializable {

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
}
