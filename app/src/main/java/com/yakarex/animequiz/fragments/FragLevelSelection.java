package com.yakarex.animequiz.fragments;

import com.yakarex.animequiz.activities.MainFragActivity;
import com.yakarex.animequiz.R;
import com.yakarex.animequiz.R.id;
import com.yakarex.animequiz.utils.FinalStringsUtils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class FragLevelSelection extends Fragment {

    TextView totalScoreView;
    boolean fulllvlunlock = false;
    static int[] lvlunlockinglogicarray;
    int totalScoreInt;
    View rootView;
    Drawable emptystar;
    Drawable lock;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.frag_level_selection, container, false);

        totalScoreView = (TextView) rootView.findViewById(R.id.totalScoreView);

        lvlunlockinglogicarray = FinalStringsUtils.lvlunlockinglogicarray;

        return rootView;
    }

    public FragLevelSelection() {


    }

    @Override
    public void onResume() {

        String totalScore = ((MainFragActivity) getActivity()).getTotalScore();
        totalScoreView.setText(getString(R.string.gamescore) + " " + totalScore);

        totalScoreInt = Integer.parseInt(totalScore);

        lvlUnlockCheck();

        super.onResume();
    }

    public void lvlUnlockCheck() {

        Button lvl3Button;
        Button lvl4Button;
        Button lvl5Button;
        Button lvl6Button;
        Button lvl7Button;
        Button lvl8Button;
        Button lvl9Button;
        Button lvl10Button;
        Button lvl11Button;
        Button lvl12Button;
        Button lvl13Button;
        Button lvl14Button;
        Button lvl15Button;
        Button lvl16Button;
        Button lvl17Button;
        Button lvl18Button;
        Button lvl19Button;
        Button lvl20Button;
        Button lvl21Button;
        Button lvl22Button;
        Button lvl23Button;
        Button lvlmoviesButton;
        Button lvlgamesButton;
        Button lvlgames2Button;
        Button lvlpetsButton;

        if (fulllvlunlock == false) {
            emptystar = this.getResources().getDrawable(R.drawable.starempty);
            lock = this.getResources().getDrawable(R.drawable.locked);

            if (totalScoreInt < lvlunlockinglogicarray[3]) {
                lvl3Button = (Button) rootView.findViewById(id.lvl3);
                lvl3Button.setText(String.valueOf(lvlunlockinglogicarray[3]) + getString(R.string.tounlock));
                lvl3Button.setTextColor(getResources().getColor(R.color.white));
                lvl3Button.setCompoundDrawablesWithIntrinsicBounds(null, null, lock, null);
                lvl3Button.setEnabled(false);
            }
            if (totalScoreInt < lvlunlockinglogicarray[4]) {
                lvl4Button = (Button) rootView.findViewById(id.lvl4);
                lvl4Button.setText(String.valueOf(lvlunlockinglogicarray[4]) + getString(R.string.tounlock));
                lvl4Button.setTextColor(getResources().getColor(R.color.white));
                lvl4Button.setCompoundDrawablesWithIntrinsicBounds(null, null, lock, null);
                lvl4Button.setEnabled(false);
            }
            if (totalScoreInt < lvlunlockinglogicarray[5]) {
                lvl5Button = (Button) rootView.findViewById(id.lvl5);
                lvl5Button.setText(String.valueOf(lvlunlockinglogicarray[5]) + getString(R.string.tounlock));
                lvl5Button.setTextColor(getResources().getColor(R.color.white));
                lvl5Button.setCompoundDrawablesWithIntrinsicBounds(null, null, lock, null);
                lvl5Button.setEnabled(false);
            }
            if (totalScoreInt < lvlunlockinglogicarray[6]) {
                lvl6Button = (Button) rootView.findViewById(id.lvl6);
                lvl6Button.setText(String.valueOf(lvlunlockinglogicarray[6]) + getString(R.string.tounlock));
                lvl6Button.setTextColor(getResources().getColor(R.color.white));
                lvl6Button.setCompoundDrawablesWithIntrinsicBounds(null, null, lock, null);
                lvl6Button.setEnabled(false);
            }
            if (totalScoreInt < lvlunlockinglogicarray[7]) {
                lvl7Button = (Button) rootView.findViewById(id.lvl7);
                lvl7Button.setText(String.valueOf(lvlunlockinglogicarray[7]) + getString(R.string.tounlock));
                lvl7Button.setTextColor(getResources().getColor(R.color.white));
                lvl7Button.setCompoundDrawablesWithIntrinsicBounds(null, null, lock, null);
                lvl7Button.setEnabled(false);
            }
            if (totalScoreInt < lvlunlockinglogicarray[8]) {
                lvlgamesButton = (Button) rootView.findViewById(id.lvlgames);
                lvlgamesButton.setText(String.valueOf(lvlunlockinglogicarray[8]) + getString(R.string.tounlock));
                lvlgamesButton.setTextColor(getResources().getColor(R.color.white));
                lvlgamesButton.setCompoundDrawablesWithIntrinsicBounds(null, null, lock, null);
                lvlgamesButton.setEnabled(false);
            }
            if (totalScoreInt < lvlunlockinglogicarray[9]) {
                lvl8Button = (Button) rootView.findViewById(id.lvl8);
                lvl8Button.setText(String.valueOf(lvlunlockinglogicarray[9]) + getString(R.string.tounlock));
                lvl8Button.setTextColor(getResources().getColor(R.color.white));
                lvl8Button.setCompoundDrawablesWithIntrinsicBounds(null, null, lock, null);
                lvl8Button.setEnabled(false);
            }
            if (totalScoreInt < lvlunlockinglogicarray[10]) {
                lvl9Button = (Button) rootView.findViewById(id.lvl9);
                lvl9Button.setText(String.valueOf(lvlunlockinglogicarray[10]) + getString(R.string.tounlock));
                lvl9Button.setTextColor(getResources().getColor(R.color.white));
                lvl9Button.setCompoundDrawablesWithIntrinsicBounds(null, null, lock, null);
                lvl9Button.setEnabled(false);
            }
            if (totalScoreInt < lvlunlockinglogicarray[11]) {
                lvl10Button = (Button) rootView.findViewById(id.lvl10);
                lvl10Button.setText(String.valueOf(lvlunlockinglogicarray[11]) + getString(R.string.tounlock));
                lvl10Button.setTextColor(getResources().getColor(R.color.white));
                lvl10Button.setCompoundDrawablesWithIntrinsicBounds(null, null, lock, null);
                lvl10Button.setEnabled(false);
            }
            if (totalScoreInt < lvlunlockinglogicarray[12]) {
                lvl11Button = (Button) rootView.findViewById(id.lvl11);
                lvl11Button.setText(String.valueOf(lvlunlockinglogicarray[12]) + getString(R.string.tounlock));
                lvl11Button.setTextColor(getResources().getColor(R.color.white));
                lvl11Button.setCompoundDrawablesWithIntrinsicBounds(null, null, lock, null);
                lvl11Button.setEnabled(false);
            }
            if (totalScoreInt < lvlunlockinglogicarray[13]) {
                lvl12Button = (Button) rootView.findViewById(id.lvl12);
                lvl12Button.setText(String.valueOf(lvlunlockinglogicarray[13]) + getString(R.string.tounlock));
                lvl12Button.setTextColor(getResources().getColor(R.color.white));
                lvl12Button.setCompoundDrawablesWithIntrinsicBounds(null, null, lock, null);
                lvl12Button.setEnabled(false);
            }
            if (totalScoreInt < lvlunlockinglogicarray[14]) {
                lvl13Button = (Button) rootView.findViewById(id.lvl13);
                lvl13Button.setText(String.valueOf(lvlunlockinglogicarray[14]) + getString(R.string.tounlock));
                lvl13Button.setTextColor(getResources().getColor(R.color.white));
                lvl13Button.setCompoundDrawablesWithIntrinsicBounds(null, null, lock, null);
                lvl13Button.setEnabled(false);
            }
            if (totalScoreInt < lvlunlockinglogicarray[15]) {
                lvl14Button = (Button) rootView.findViewById(id.lvl14);
                lvl14Button.setText(String.valueOf(lvlunlockinglogicarray[15]) + getString(R.string.tounlock));
                lvl14Button.setTextColor(getResources().getColor(R.color.white));
                lvl14Button.setCompoundDrawablesWithIntrinsicBounds(null, null, lock, null);
                lvl14Button.setEnabled(false);
            }
            if (totalScoreInt < lvlunlockinglogicarray[16]) {
                lvl15Button = (Button) rootView.findViewById(id.lvl15);
                lvl15Button.setText(String.valueOf(lvlunlockinglogicarray[16]) + getString(R.string.tounlock));
                lvl15Button.setTextColor(getResources().getColor(R.color.white));
                lvl15Button.setCompoundDrawablesWithIntrinsicBounds(null, null, lock, null);
                lvl15Button.setEnabled(false);
            }
            if (totalScoreInt < lvlunlockinglogicarray[17]) {
                lvl16Button = (Button) rootView.findViewById(id.lvl16);
                lvl16Button.setText(String.valueOf(lvlunlockinglogicarray[17]) + getString(R.string.tounlock));
                lvl16Button.setTextColor(getResources().getColor(R.color.white));
                lvl16Button.setCompoundDrawablesWithIntrinsicBounds(null, null, lock, null);
                lvl16Button.setEnabled(false);
            }
            if (totalScoreInt < lvlunlockinglogicarray[18]) {
                lvlpetsButton = (Button) rootView.findViewById(id.lvlpets);
                lvlpetsButton.setText(String.valueOf(lvlunlockinglogicarray[18]) + getString(R.string.tounlock));
                lvlpetsButton.setTextColor(getResources().getColor(R.color.white));
                lvlpetsButton.setCompoundDrawablesWithIntrinsicBounds(null, null, lock, null);
                lvlpetsButton.setEnabled(false);
            }
            if (totalScoreInt < lvlunlockinglogicarray[19]) {
                lvl17Button = (Button) rootView.findViewById(id.lvl17);
                lvl17Button.setText(String.valueOf(lvlunlockinglogicarray[19]) + getString(R.string.tounlock));
                lvl17Button.setTextColor(getResources().getColor(R.color.white));
                lvl17Button.setCompoundDrawablesWithIntrinsicBounds(null, null, lock, null);
                lvl17Button.setEnabled(false);
            }

            if (totalScoreInt < lvlunlockinglogicarray[20]) {
                lvl18Button = (Button) rootView.findViewById(id.lvl18);
                lvl18Button.setText(String.valueOf(lvlunlockinglogicarray[20]) + getString(R.string.tounlock));
                lvl18Button.setTextColor(getResources().getColor(R.color.white));
                lvl18Button.setCompoundDrawablesWithIntrinsicBounds(null, null, lock, null);
                lvl18Button.setEnabled(false);
            }
            if (totalScoreInt < lvlunlockinglogicarray[21]) {
                lvlgames2Button = (Button) rootView.findViewById(id.lvlgames2);
                lvlgames2Button.setText(String.valueOf(lvlunlockinglogicarray[21]) + getString(R.string.tounlock));
                lvlgames2Button.setTextColor(getResources().getColor(R.color.white));
                lvlgames2Button.setCompoundDrawablesWithIntrinsicBounds(null, null, lock, null);
                lvlgames2Button.setEnabled(false);
            }
            if (totalScoreInt < lvlunlockinglogicarray[22]) {
                lvl19Button = (Button) rootView.findViewById(id.lvl19);
                lvl19Button.setText(String.valueOf(lvlunlockinglogicarray[22]) + getString(R.string.tounlock));
                lvl19Button.setTextColor(getResources().getColor(R.color.white));
                lvl19Button.setCompoundDrawablesWithIntrinsicBounds(null, null, lock, null);
                lvl19Button.setEnabled(false);
            }
            if (totalScoreInt < lvlunlockinglogicarray[23]) {
                lvl20Button = (Button) rootView.findViewById(id.lvl20);
                lvl20Button.setText(String.valueOf(lvlunlockinglogicarray[23]) + getString(R.string.tounlock));
                lvl20Button.setTextColor(getResources().getColor(R.color.white));
                lvl20Button.setCompoundDrawablesWithIntrinsicBounds(null, null, lock, null);
                lvl20Button.setEnabled(false);
            }
            if (totalScoreInt < lvlunlockinglogicarray[24]) {
                lvlmoviesButton = (Button) rootView.findViewById(id.lvlmovies);
                lvlmoviesButton.setText(String.valueOf(lvlunlockinglogicarray[24]) + getString(R.string.tounlock));
                lvlmoviesButton.setTextColor(getResources().getColor(R.color.white));
                lvlmoviesButton.setCompoundDrawablesWithIntrinsicBounds(null, null, lock, null);
                lvlmoviesButton.setEnabled(false);
            }
            if (totalScoreInt < lvlunlockinglogicarray[25]) {
                lvl21Button = (Button) rootView.findViewById(id.lvl21);
                lvl21Button.setText(String.valueOf(lvlunlockinglogicarray[25]) + getString(R.string.tounlock));
                lvl21Button.setTextColor(getResources().getColor(R.color.white));
                lvl21Button.setCompoundDrawablesWithIntrinsicBounds(null, null, lock, null);
                lvl21Button.setEnabled(false);
            }
            if (totalScoreInt < lvlunlockinglogicarray[26]) {
                lvl22Button = (Button) rootView.findViewById(id.lvl22);
                lvl22Button.setText(String.valueOf(lvlunlockinglogicarray[26]) + getString(R.string.tounlock));
                lvl22Button.setTextColor(getResources().getColor(R.color.white));
                lvl22Button.setCompoundDrawablesWithIntrinsicBounds(null, null, lock, null);
                lvl22Button.setEnabled(false);
            }
            if (totalScoreInt < lvlunlockinglogicarray[27]) {
                lvl23Button = (Button) rootView.findViewById(id.lvl23);
                lvl23Button.setText(String.valueOf(lvlunlockinglogicarray[27]) + getString(R.string.tounlock));
                lvl23Button.setTextColor(getResources().getColor(R.color.white));
                lvl23Button.setCompoundDrawablesWithIntrinsicBounds(null, null, lock, null);
                lvl23Button.setEnabled(false);
            }
            if (totalScoreInt >= lvlunlockinglogicarray[3]) {
                lvl3Button = (Button) rootView.findViewById(id.lvl3);
                lvl3Button.setTextSize(30);
                lvl3Button.setText(getLvlNamebyId(3));
                lvl3Button.setCompoundDrawablesWithIntrinsicBounds(null, null, emptystar, null);
                lvl3Button.setEnabled(true);
            }
            if (totalScoreInt >= lvlunlockinglogicarray[4]) {
                lvl4Button = (Button) rootView.findViewById(id.lvl4);
                lvl4Button.setTextSize(30);
                lvl4Button.setText(getLvlNamebyId(4));
                lvl4Button.setCompoundDrawablesWithIntrinsicBounds(null, null, emptystar, null);
                lvl4Button.setEnabled(true);
            }
            if (totalScoreInt >= lvlunlockinglogicarray[5]) {
                lvl5Button = (Button) rootView.findViewById(id.lvl5);
                lvl5Button.setTextSize(30);
                lvl5Button.setText(getLvlNamebyId(5));
                lvl5Button.setCompoundDrawablesWithIntrinsicBounds(null, null, emptystar, null);
                lvl5Button.setEnabled(true);
            }
            if (totalScoreInt >= lvlunlockinglogicarray[6]) {
                lvl6Button = (Button) rootView.findViewById(id.lvl6);
                lvl6Button.setTextSize(30);
                lvl6Button.setText(getLvlNamebyId(6));
                lvl6Button.setCompoundDrawablesWithIntrinsicBounds(null, null, emptystar, null);
                lvl6Button.setEnabled(true);
            }
            if (totalScoreInt >= lvlunlockinglogicarray[7]) {
                lvl7Button = (Button) rootView.findViewById(id.lvl7);
                lvl7Button.setTextSize(30);
                lvl7Button.setText(getLvlNamebyId(7));
                lvl7Button.setCompoundDrawablesWithIntrinsicBounds(null, null, emptystar, null);
                lvl7Button.setEnabled(true);
            }
            if (totalScoreInt >= lvlunlockinglogicarray[8]) {
                lvlgamesButton = (Button) rootView.findViewById(id.lvlgames);
                lvlgamesButton.setTextSize(30);
                lvlgamesButton.setText(R.string.levelgames);
                lvlgamesButton.setCompoundDrawablesWithIntrinsicBounds(null, null, emptystar, null);
                lvlgamesButton.setEnabled(true);
            }
            if (totalScoreInt >= lvlunlockinglogicarray[9]) {
                lvl8Button = (Button) rootView.findViewById(id.lvl8);
                lvl8Button.setTextSize(30);
                lvl8Button.setText(getLvlNamebyId(8));
                lvl8Button.setCompoundDrawablesWithIntrinsicBounds(null, null, emptystar, null);
                lvl8Button.setEnabled(true);
            }
            if (totalScoreInt >= lvlunlockinglogicarray[10]) {
                lvl9Button = (Button) rootView.findViewById(id.lvl9);
                lvl9Button.setTextSize(30);
                lvl9Button.setText(getLvlNamebyId(9));
                lvl9Button.setCompoundDrawablesWithIntrinsicBounds(null, null, emptystar, null);
                lvl9Button.setEnabled(true);
            }
            if (totalScoreInt >= lvlunlockinglogicarray[11]) {
                lvl10Button = (Button) rootView.findViewById(id.lvl10);
                lvl10Button.setTextSize(30);
                lvl10Button.setText(getLvlNamebyId(10));
                lvl10Button.setCompoundDrawablesWithIntrinsicBounds(null, null, emptystar, null);
                lvl10Button.setEnabled(true);
            }
            if (totalScoreInt >= lvlunlockinglogicarray[12]) {
                lvl11Button = (Button) rootView.findViewById(id.lvl11);
                lvl11Button.setTextSize(30);
                lvl11Button.setText(getLvlNamebyId(11));
                lvl11Button.setCompoundDrawablesWithIntrinsicBounds(null, null, emptystar, null);
                lvl11Button.setEnabled(true);
            }
            if (totalScoreInt >= lvlunlockinglogicarray[13]) {
                lvl12Button = (Button) rootView.findViewById(id.lvl12);
                lvl12Button.setTextSize(30);
                lvl12Button.setText(getLvlNamebyId(12));
                lvl12Button.setCompoundDrawablesWithIntrinsicBounds(null, null, emptystar, null);
                lvl12Button.setEnabled(true);
            }
            if (totalScoreInt >= lvlunlockinglogicarray[14]) {
                lvl13Button = (Button) rootView.findViewById(id.lvl13);
                lvl13Button.setTextSize(30);
                lvl13Button.setText(getLvlNamebyId(13));
                lvl13Button.setCompoundDrawablesWithIntrinsicBounds(null, null, emptystar, null);
                lvl13Button.setEnabled(true);
            }
            if (totalScoreInt >= lvlunlockinglogicarray[15]) {
                lvl14Button = (Button) rootView.findViewById(id.lvl14);
                lvl14Button.setTextSize(30);
                lvl14Button.setText(getLvlNamebyId(14));
                lvl14Button.setCompoundDrawablesWithIntrinsicBounds(null, null, emptystar, null);
                lvl14Button.setEnabled(true);
            }
            if (totalScoreInt >= lvlunlockinglogicarray[16]) {
                lvl15Button = (Button) rootView.findViewById(id.lvl15);
                lvl15Button.setTextSize(30);
                lvl15Button.setText(getLvlNamebyId(15));
                lvl15Button.setCompoundDrawablesWithIntrinsicBounds(null, null, emptystar, null);
                lvl15Button.setEnabled(true);
            }
            if (totalScoreInt >= lvlunlockinglogicarray[17]) {
                lvl16Button = (Button) rootView.findViewById(id.lvl16);
                lvl16Button.setTextSize(30);
                lvl16Button.setText(getLvlNamebyId(16));
                lvl16Button.setCompoundDrawablesWithIntrinsicBounds(null, null, emptystar, null);
                lvl16Button.setEnabled(true);
            }
            if (totalScoreInt >= lvlunlockinglogicarray[18]) {
                lvlpetsButton = (Button) rootView.findViewById(id.lvlpets);
                lvlpetsButton.setTextSize(30);
                lvlpetsButton.setText(R.string.levelpets);
                lvlpetsButton.setCompoundDrawablesWithIntrinsicBounds(null, null, emptystar, null);
                lvlpetsButton.setEnabled(true);
            }
            if (totalScoreInt >= lvlunlockinglogicarray[19]) {
                lvl17Button = (Button) rootView.findViewById(id.lvl17);
                lvl17Button.setTextSize(30);
                lvl17Button.setText(getLvlNamebyId(17));
                lvl17Button.setCompoundDrawablesWithIntrinsicBounds(null, null, emptystar, null);
                lvl17Button.setEnabled(true);
            }
            if (totalScoreInt >= lvlunlockinglogicarray[20]) {
                lvl18Button = (Button) rootView.findViewById(id.lvl18);
                lvl18Button.setTextSize(30);
                lvl18Button.setText(getLvlNamebyId(18));
                lvl18Button.setCompoundDrawablesWithIntrinsicBounds(null, null, emptystar, null);
                lvl18Button.setEnabled(true);
            }
            if (totalScoreInt >= lvlunlockinglogicarray[21]) {
                lvlgames2Button = (Button) rootView.findViewById(id.lvlgames2);
                lvlgames2Button.setTextSize(30);
                lvlgames2Button.setText(R.string.levelgames2);
                lvlgames2Button.setCompoundDrawablesWithIntrinsicBounds(null, null, emptystar, null);
                lvlgames2Button.setEnabled(true);
            }
            if (totalScoreInt >= lvlunlockinglogicarray[22]) {
                lvl19Button = (Button) rootView.findViewById(id.lvl19);
                lvl19Button.setTextSize(30);
                lvl19Button.setText(getLvlNamebyId(19));
                lvl19Button.setCompoundDrawablesWithIntrinsicBounds(null, null, emptystar, null);
                lvl19Button.setEnabled(true);
            }
            if (totalScoreInt >= lvlunlockinglogicarray[23]) {
                lvl20Button = (Button) rootView.findViewById(id.lvl20);
                lvl20Button.setTextSize(30);
                lvl20Button.setText(getLvlNamebyId(20));
                lvl20Button.setCompoundDrawablesWithIntrinsicBounds(null, null, emptystar, null);
                lvl20Button.setEnabled(true);
            }
            if (totalScoreInt >= lvlunlockinglogicarray[24]) {
                lvlmoviesButton = (Button) rootView.findViewById(id.lvlmovies);
                lvlmoviesButton.setTextSize(30);
                lvlmoviesButton.setText(R.string.levelmovies);
                lvlmoviesButton.setCompoundDrawablesWithIntrinsicBounds(null, null, emptystar, null);
                lvlmoviesButton.setEnabled(true);
            }
            if (totalScoreInt >= lvlunlockinglogicarray[25]) {
                lvl21Button = (Button) rootView.findViewById(id.lvl21);
                lvl21Button.setTextSize(30);
                lvl21Button.setText(getLvlNamebyId(21));
                lvl21Button.setCompoundDrawablesWithIntrinsicBounds(null, null, emptystar, null);
                lvl21Button.setEnabled(true);
            }
            if (totalScoreInt >= lvlunlockinglogicarray[26]) {
                lvl22Button = (Button) rootView.findViewById(id.lvl22);
                lvl22Button.setTextSize(30);
                lvl22Button.setText(getLvlNamebyId(22));
                lvl22Button.setCompoundDrawablesWithIntrinsicBounds(null, null, emptystar, null);
                lvl22Button.setEnabled(true);
            }
            if (totalScoreInt >= lvlunlockinglogicarray[27]) {
                lvl23Button = (Button) rootView.findViewById(id.lvl23);
                lvl23Button.setTextSize(30);
                lvl23Button.setText(getLvlNamebyId(23));
                lvl23Button.setCompoundDrawablesWithIntrinsicBounds(null, null, emptystar, null);
                lvl23Button.setEnabled(true);
            }

        }
        setlvlStar(1, id.lvl1);
        setlvlStar(2, id.lvl2);
        setlvlStar(3, id.lvl3);
        setlvlStar(4, id.lvl4);
        setlvlStar(5, id.lvl5);
        setlvlStar(6, id.lvl6);
        setlvlStar(7, id.lvl7);
        setlvlStar(8, id.lvl8);
        setlvlStar(9, id.lvl9);
        setlvlStar(10, id.lvl10);
        setlvlStar(11, id.lvl11);
        setlvlStar(12, id.lvl12);
        setlvlStar(13, id.lvl13);
        setlvlStar(14, id.lvl14);
        setlvlStar(15, id.lvl15);
        setlvlStar(16, id.lvl16);
        setlvlStar(17, id.lvl17);
        setlvlStar(18, id.lvl18);
        setlvlStar(19, id.lvl19);
        setlvlStar(20, id.lvl20);
        setlvlStar(21, id.lvl21);
        setlvlStar(22, id.lvl22);
        setlvlStar(23, id.lvl23);
        setlvlStar(100, id.lvlgames);
        setlvlStar(101, id.lvlpets);
        setlvlStar(102, id.lvlgames2);
        setlvlStar(103, id.lvlmovies);

    }

    private String getLvlNamebyId(int lvlId) {

        String name = getResources().getString(R.string.level_name);

        switch (lvlId) {
            case 100:
                name = getResources().getString(R.string.levelgames);
                break;
            case 101:
                name = getResources().getString(R.string.levelpets);
                break;
            case 102:
                name = getResources().getString(R.string.levelgames2);
                break;
            case 103:
                name = getResources().getString(R.string.levelmovies);
                break;
            default:
                name = name.replace("@@", String.valueOf(lvlId));
                break;
        }


        return name;
    }

    public void setlvlStar(int lvl, int id) {
        Button lvlbutton = (Button) rootView.findViewById(id);
        Drawable cupper = this.getResources().getDrawable(R.drawable.cupperstar);
        Drawable silver = this.getResources().getDrawable(R.drawable.silver);
        Drawable gold = this.getResources().getDrawable(R.drawable.goldstar);

        int score = ((MainFragActivity) getActivity()).getLevelScore(lvl);
        int maxscore = ((MainFragActivity) getActivity()).getLevelMaxScore(lvl);

        if (score > 0) {

            lvlbutton.setCompoundDrawablesWithIntrinsicBounds(null, null, cupper, null);
        }
        if (score > (maxscore / 2)) {

            lvlbutton.setCompoundDrawablesWithIntrinsicBounds(null, null, silver, null);
        }
        if (score >= maxscore) {

            lvlbutton.setCompoundDrawablesWithIntrinsicBounds(null, null, gold, null);
        }
        lvlbutton.refreshDrawableState();
    }

}
