package com.yakarex.animequiz;

import com.yakarex.animequiz.R.id;
import com.yakarex.animequiz.utils.ScoreDbHelper;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class StatsTab1Fragment extends Fragment {
	
	ScoreDbHelper dbHelper;
	int [] starsArray;
	int totalScore;
	TextView gold;
	TextView silver;
	TextView bronze;
	TextView hints;
	private static final int HINTCOUNT= 999999999;
	int hintscount;
	
	TextView tab1totalscore;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
	        Bundle savedInstanceState) {
	        // Inflate the layout for this fragment
		
		dbHelper= new ScoreDbHelper(getActivity());
		dbHelper.createDb();
		dbHelper.openDataBase();
        starsArray= dbHelper.starsStats();
        totalScore= dbHelper.getTotalScore();
        hintscount= dbHelper.getCharScore(HINTCOUNT, 0);
        dbHelper.close();
        
        
	        return inflater.inflate(R.layout.statstab1, container, false);
	    }

	@Override
	public void onResume() {
		super.onResume();
		
        int goldquantity= starsArray[0];
        int silverquantity= starsArray[1];
        int bronzequantity= starsArray[2];
        
        gold= (TextView) getView().findViewById(id.goldcount);
        gold.setText(String.valueOf(goldquantity));
        silver= (TextView) getView().findViewById(id.silvercount);
        silver.setText(String.valueOf(silverquantity));
        bronze= (TextView) getView().findViewById(id.bronzecount);
        bronze.setText(String.valueOf(bronzequantity));
        hints= (TextView) getView().findViewById(id.hintscount);
        hints.setText(String.valueOf(hintscount));
        
        String gamescoreText= (String) getText(R.string.gamescore);
        String tab1scorestring= gamescoreText+" "+String.valueOf(totalScore) ;
        tab1totalscore= (TextView) getView().findViewById(id.tab1totalscore);
        tab1totalscore.setText(tab1scorestring);
	}
	

}
