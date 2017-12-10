package com.yakarex.animequiz.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by elaxel on 10/12/2017.
 */

public class SimpleCacheController {

    private final Context context;
    String id = "ACHA";

    public SimpleCacheController(Context context) {
        this.context = context;
    }

    public String read(String label) {
        SharedPreferences pref = context.getSharedPreferences(id, Context.MODE_PRIVATE);
        String json = pref.getString(label, null);
        if (json != null && !json.isEmpty()) {
            return json;
        } else {
            return "";
        }
    }

    public void save(String label, String data) {
        SharedPreferences pref = context.getSharedPreferences(id, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(label, data);
        editor.apply();
    }

    public void clear(String label) {
        SharedPreferences pref = context.getSharedPreferences(id, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove(label);
        editor.apply();
    }

    public boolean readBool(String label) {
        SharedPreferences pref = context.getSharedPreferences(id, Context.MODE_PRIVATE);
        return pref.getBoolean(label, false);
    }

    public void saveBool(String label, boolean data) {
        SharedPreferences pref = context.getSharedPreferences(id, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(label, data);
        editor.apply();
    }
}
