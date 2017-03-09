package com.yakarex.animequiz.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.yakarex.animequiz.fragments.FragCharacter;
import com.yakarex.animequiz.fragments.FragCharacterSwiper;
import com.yakarex.animequiz.models.AChaCharacterModel;

/**
 * Created by axel.romero on 13/12/2016.
 */

public class CharacterPagesAdapter extends FragmentStatePagerAdapter {

    private Context context;
    private Cursor charactersCursor;

    public CharacterPagesAdapter(FragmentManager fm, Context context, Cursor cursor) {
        super(fm);
        this.context= context;
        this.charactersCursor= cursor;
    }

    @Override
    public Fragment getItem(int position) {

        charactersCursor.moveToPosition(position);
        return FragCharacter.newInstance(new AChaCharacterModel(charactersCursor));
    }

    @Override
    public int getCount() {
        return charactersCursor.getCount();
    }
}
