package com.yakarex.animequiz.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.yakarex.animequiz.R;
import com.yakarex.animequiz.StatsTab1Fragment;
import com.yakarex.animequiz.StatsTab2Fragment;

/**
 * Created by aromero on 25/01/16.
 */
public class StatsPagerAdapter extends FragmentStatePagerAdapter {

    Context context;

    public StatsPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context= context;
    }

    @Override
    public Fragment getItem(int position) {

        if(position== 0){

            StatsTab1Fragment tab1= new StatsTab1Fragment();

            return tab1;
        }
        else{

            StatsTab2Fragment tab2 = new StatsTab2Fragment();

            return tab2;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        if(position == 0){
            return context.getResources().getString(R.string.tab1);
        }
        else{
            return context.getResources().getString(R.string.tab2);
        }
    }
}
