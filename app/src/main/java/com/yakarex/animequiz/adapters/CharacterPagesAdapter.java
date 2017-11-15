package com.yakarex.animequiz.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.yakarex.animequiz.fragments.FragCharacter;
import com.yakarex.animequiz.fragments.FragCharacterSwiper;
import com.yakarex.animequiz.models.AChaCharacterModel;
import com.yakarex.animequiz.models.LevelStatModel;

import java.util.List;

/**
 * Created by axel.romero on 13/12/2016.
 */

public class CharacterPagesAdapter extends FragmentStatePagerAdapter {

    private Context context;
    private List<AChaCharacterModel> characterList;

    public CharacterPagesAdapter(FragmentManager fm, Context context, List<AChaCharacterModel> charactersList) {
        super(fm);
        this.context= context;
        this.characterList= characterList;
    }

    @Override
    public Fragment getItem(int position) {

        return FragCharacter.newInstance(characterList.get(position));
    }

    @Override
    public int getCount() {
        return characterList.size();
    }
}
