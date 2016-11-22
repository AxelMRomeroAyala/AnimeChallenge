package com.yakarex.animequiz;

import com.yakarex.animequiz.R.id;
import com.yakarex.animequiz.utils.DataBaseHelper;
import com.yakarex.animequiz.utils.ScoreDbHelper;

import android.database.Cursor;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class StatsTab2Fragment extends Fragment{
	
	DataBaseHelper dbHelper;
	ScoreDbHelper scoreHelper;
	
	ProgressBar lvl1pbar;
	ImageView lvl1staranim;
	TextView lvl1starsstring;
	TextView lvl1scorestring;
	AnimationDrawable sanim1;
	
	ProgressBar lvl2pbar;
	ImageView lvl2staranim;
	TextView lvl2starsstring;
	TextView lvl2scorestring;
	AnimationDrawable sanim2;
	
	ProgressBar lvl3pbar;
	ImageView lvl3staranim;
	TextView lvl3starsstring;
	TextView lvl3scorestring;
	AnimationDrawable sanim3;
	
	ProgressBar lvl4pbar;
	ImageView lvl4staranim;
	TextView lvl4starsstring;
	TextView lvl4scorestring;
	AnimationDrawable sanim4;
	
	ProgressBar lvl5pbar;
	ImageView lvl5staranim;
	TextView lvl5starsstring;
	TextView lvl5scorestring;
	AnimationDrawable sanim5;
	
	ProgressBar lvl6pbar;
	ImageView lvl6staranim;
	TextView lvl6starsstring;
	TextView lvl6scorestring;
	AnimationDrawable sanim6;
	
	ProgressBar lvl7pbar;
	ImageView lvl7staranim;
	TextView lvl7starsstring;
	TextView lvl7scorestring;
	AnimationDrawable sanim7;
	
	ProgressBar lvlgamespbar;
	ImageView lvlgamesstaranim;
	TextView lvlgamesstarsstring;
	TextView lvlgamesscorestring;
	AnimationDrawable sanimgames;
	
	ProgressBar lvl8pbar;
	ImageView lvl8staranim;
	TextView lvl8starsstring;
	TextView lvl8scorestring;
	AnimationDrawable sanim8;
	
	ProgressBar lvl9pbar;
	ImageView lvl9staranim;
	TextView lvl9starsstring;
	TextView lvl9scorestring;
	AnimationDrawable sanim9;
	
	ProgressBar lvl10pbar;
	ImageView lvl10staranim;
	TextView lvl10starsstring;
	TextView lvl10scorestring;
	AnimationDrawable sanim10;
	
	ProgressBar lvl11pbar;
	ImageView lvl11staranim;
	TextView lvl11starsstring;
	TextView lvl11scorestring;
	AnimationDrawable sanim11;
	
	ProgressBar lvl12pbar;
	ImageView lvl12staranim;
	TextView lvl12starsstring;
	TextView lvl12scorestring;
	AnimationDrawable sanim12;
	
	ProgressBar lvl13pbar;
	ImageView lvl13staranim;
	TextView lvl13starsstring;
	TextView lvl13scorestring;
	AnimationDrawable sanim13;
	
	ProgressBar lvl14pbar;
	ImageView lvl14staranim;
	TextView lvl14starsstring;
	TextView lvl14scorestring;
	AnimationDrawable sanim14;
	
	ProgressBar lvl15pbar;
	ImageView lvl15staranim;
	TextView lvl15starsstring;
	TextView lvl15scorestring;
	AnimationDrawable sanim15;
	
	ProgressBar lvl16pbar;
	ImageView lvl16staranim;
	TextView lvl16starsstring;
	TextView lvl16scorestring;
	AnimationDrawable sanim16;
	
	ProgressBar lvlpetspbar;
	ImageView lvlpetsstaranim;
	TextView lvlpetsstarsstring;
	TextView lvlpetsscorestring;
	AnimationDrawable sanimpets;
	
	ProgressBar lvl17pbar;
	ImageView lvl17staranim;
	TextView lvl17starsstring;
	TextView lvl17scorestring;
	AnimationDrawable sanim17;
	
	ProgressBar lvl18pbar;
	ImageView lvl18staranim;
	TextView lvl18starsstring;
	TextView lvl18scorestring;
	AnimationDrawable sanim18;
	
	ProgressBar lvlgames2pbar;
	ImageView lvlgames2staranim;
	TextView lvlgames2starsstring;
	TextView lvlgames2scorestring;
	AnimationDrawable sanimgames2;
	
	ProgressBar lvl19pbar;
	ImageView lvl19staranim;
	TextView lvl19starsstring;
	TextView lvl19scorestring;
	AnimationDrawable sanim19;
	
	ProgressBar lvl20pbar;
	ImageView lvl20staranim;
	TextView lvl20starsstring;
	TextView lvl20scorestring;
	AnimationDrawable sanim20;
	
	ProgressBar lvlmoviespbar;
	ImageView lvlmoviesstaranim;
	TextView lvlmoviesstarsstring;
	TextView lvlmoviesscorestring;
	AnimationDrawable sanimmovies;

	
	ProgressBar lvl21pbar;
	ImageView lvl21staranim;
	TextView lvl21starsstring;
	TextView lvl21scorestring;
	AnimationDrawable sanim21;
	
	ProgressBar lvl22pbar;
	ImageView lvl22staranim;
	TextView lvl22starsstring;
	TextView lvl22scorestring;
	AnimationDrawable sanim22;
	
	ProgressBar lvl23pbar;
	ImageView lvl23staranim;
	TextView lvl23starsstring;
	TextView lvl23scorestring;
	AnimationDrawable sanim23;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.statstab2, container, false);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		dbHelper= new DataBaseHelper(getActivity());
        dbHelper.openDataBase();
        scoreHelper= new ScoreDbHelper(getActivity());
        scoreHelper.openDataBase();
        
        
        Cursor lvl1cursor= dbHelper.getLvl(1);
        double lvl1maxscore= lvl1cursor.getCount()*100;
        double lvl1score= scoreHelper.getLevelScore(1);
        double lvl1division= lvl1score / lvl1maxscore;
        double lvl1pbarvalue= lvl1division*100;
        lvl1cursor.close();
        
        int [] starsArray= scoreHelper.starsStats(1);
        String starsString = String.valueOf(starsArray[0])+" / "+String.valueOf(starsArray[1])+" / "+String.valueOf(starsArray[2])+"  ";
        
        lvl1pbar = (ProgressBar) getView().findViewById(id.statslvl1pbar);
        lvl1pbar.setProgress((int) lvl1pbarvalue);
        lvl1staranim= (ImageView) getView().findViewById(R.id.starsanim1);
        lvl1staranim.setBackgroundResource(R.drawable.starsanimation);
        //lvl1staranim.setImageResource(R.drawable.starsanimation);
        sanim1= (AnimationDrawable) lvl1staranim.getBackground();
        //sanim1.start();
        lvl1starsstring = (TextView) getView().findViewById(id.statslvl1starsstring);
        lvl1starsstring.setText(starsString);
        lvl1scorestring= (TextView) getView().findViewById(id.statslvl1score);
        lvl1scorestring.setText(String.valueOf((int)lvl1score));
        
        Cursor lvl2cursor= dbHelper.getLvl(2);
        double lvl2maxscore= lvl2cursor.getCount()*100;
        double lvl2score= scoreHelper.getLevelScore(2);
        double lvl2division= lvl2score / lvl2maxscore;
        double lvl2pbarvalue= lvl2division*100;
        lvl2cursor.close();
        
        int [] starsArray2= scoreHelper.starsStats(2);
        String starsString2 = String.valueOf(starsArray2[0])+" / "+String.valueOf(starsArray2[1])+" / "+String.valueOf(starsArray2[2])+"  ";
        
        lvl2pbar = (ProgressBar) getView().findViewById(id.statslvl2pbar);
        lvl2pbar.setProgress((int) lvl2pbarvalue);
        lvl2staranim= (ImageView) getView().findViewById(id.starsanim2);
        lvl2staranim.setBackgroundResource(R.drawable.starsanimation);
        //lvl1staranim.setImageResource(R.drawable.starsanimation);
        sanim2= (AnimationDrawable) lvl2staranim.getBackground();
        //sanim2.start();
        lvl2starsstring = (TextView) getView().findViewById(id.statslvl2starsstring);
        lvl2starsstring.setText(starsString2);
        lvl2scorestring= (TextView) getView().findViewById(id.statslvl2score);
        lvl2scorestring.setText(String.valueOf((int)lvl2score));
        
        Cursor lvl3cursor= dbHelper.getLvl(3);
        double lvl3maxscore= lvl3cursor.getCount()*100;
        double lvl3score= scoreHelper.getLevelScore(3);
        double lvl3division= lvl3score / lvl3maxscore;
        double lvl3pbarvalue= lvl3division*100;
        lvl3cursor.close();
        
        int [] starsArray3= scoreHelper.starsStats(3);
        String starsString3 = String.valueOf(starsArray3[0])+" / "+String.valueOf(starsArray3[1])+" / "+String.valueOf(starsArray3[2])+"  ";
        
        lvl3pbar = (ProgressBar) getView().findViewById(id.statslvl3pbar);
        lvl3pbar.setProgress((int) lvl3pbarvalue);
        lvl3staranim= (ImageView) getView().findViewById(id.starsanim3);
        lvl3staranim.setBackgroundResource(R.drawable.starsanimation);
        //lvl1staranim.setImageResource(R.drawable.starsanimation);
        sanim3= (AnimationDrawable) lvl3staranim.getBackground();
        //sanim3.start();
        lvl3starsstring = (TextView) getView().findViewById(id.statslvl3starsstring);
        lvl3starsstring.setText(starsString3);
        lvl3scorestring= (TextView) getView().findViewById(id.statslvl3score);
        lvl3scorestring.setText(String.valueOf((int)lvl3score));
        
        Cursor lvl4cursor= dbHelper.getLvl(4);
        double lvl4maxscore= lvl4cursor.getCount()*100;
        double lvl4score= scoreHelper.getLevelScore(4);
        double lvl4division= lvl4score / lvl4maxscore;
        double lvl4pbarvalue= lvl4division*100;
        lvl4cursor.close();
        
        int [] starsArray4= scoreHelper.starsStats(4);
        String starsString4 = String.valueOf(starsArray4[0])+" / "+String.valueOf(starsArray4[1])+" / "+String.valueOf(starsArray4[2])+"  ";
        
        lvl4pbar = (ProgressBar) getView().findViewById(id.statslvl4pbar);
        lvl4pbar.setProgress((int) lvl4pbarvalue);
        lvl4staranim= (ImageView) getView().findViewById(id.starsanim4);
        lvl4staranim.setBackgroundResource(R.drawable.starsanimation);
        //lvl1staranim.setImageResource(R.drawable.starsanimation);
        sanim4= (AnimationDrawable) lvl4staranim.getBackground();
        //sanim4.start();
        lvl4starsstring = (TextView) getView().findViewById(id.statslvl4starsstring);
        lvl4starsstring.setText(starsString4);
        lvl4scorestring= (TextView) getView().findViewById(id.statslvl4score);
        lvl4scorestring.setText(String.valueOf((int)lvl4score));
        
        Cursor lvl5cursor= dbHelper.getLvl(5);
        double lvl5maxscore= lvl5cursor.getCount()*100;
        double lvl5score= scoreHelper.getLevelScore(5);
        double lvl5division= lvl5score / lvl5maxscore;
        double lvl5pbarvalue= lvl5division*100;
        lvl5cursor.close();
        
        int [] starsArray5= scoreHelper.starsStats(5);
        String starsString5 = String.valueOf(starsArray5[0])+" / "+String.valueOf(starsArray5[1])+" / "+String.valueOf(starsArray5[2])+"  ";
        
        lvl5pbar = (ProgressBar) getView().findViewById(id.statslvl5pbar);
        lvl5pbar.setProgress((int) lvl5pbarvalue);
        lvl5staranim= (ImageView) getView().findViewById(id.starsanim5);
        lvl5staranim.setBackgroundResource(R.drawable.starsanimation);
        //lvl1staranim.setImageResource(R.drawable.starsanimation);
        sanim5= (AnimationDrawable) lvl5staranim.getBackground();
        //sanim5.start();
        lvl5starsstring = (TextView) getView().findViewById(id.statslvl5starsstring);
        lvl5starsstring.setText(starsString5);
        lvl5scorestring= (TextView) getView().findViewById(id.statslvl5score);
        lvl5scorestring.setText(String.valueOf((int)lvl5score));
        
        Cursor lvl6cursor= dbHelper.getLvl(6);
        double lvl6maxscore= lvl6cursor.getCount()*100;
        double lvl6score= scoreHelper.getLevelScore(6);
        double lvl6division= lvl6score / lvl6maxscore;
        double lvl6pbarvalue= lvl6division*100;
        lvl6cursor.close();
        
        int [] starsArray6= scoreHelper.starsStats(6);
        String starsString6 = String.valueOf(starsArray6[0])+" / "+String.valueOf(starsArray6[1])+" / "+String.valueOf(starsArray6[2])+"  ";
        
        lvl6pbar = (ProgressBar) getView().findViewById(id.statslvl6pbar);
        lvl6pbar.setProgress((int) lvl6pbarvalue);
        lvl6staranim= (ImageView) getView().findViewById(id.starsanim6);
        lvl6staranim.setBackgroundResource(R.drawable.starsanimation);
        //lvl1staranim.setImageResource(R.drawable.starsanimation);
        sanim6= (AnimationDrawable) lvl6staranim.getBackground();
        //sanim6.start();
        lvl6starsstring = (TextView) getView().findViewById(id.statslvl6starsstring);
        lvl6starsstring.setText(starsString6);
        lvl6scorestring= (TextView) getView().findViewById(id.statslvl6score);
        lvl6scorestring.setText(String.valueOf((int)lvl6score));
        
        Cursor lvl7cursor= dbHelper.getLvl(7);
        double lvl7maxscore= lvl7cursor.getCount()*100;
        double lvl7score= scoreHelper.getLevelScore(7);
        double lvl7division= lvl7score / lvl7maxscore;
        double lvl7pbarvalue= lvl7division*100;
        lvl7cursor.close();
        
        int [] starsArray7= scoreHelper.starsStats(7);
        String starsString7 = String.valueOf(starsArray7[0])+" / "+String.valueOf(starsArray7[1])+" / "+String.valueOf(starsArray7[2])+"  ";
        
        lvl7pbar = (ProgressBar) getView().findViewById(id.statslvl7pbar);
        lvl7pbar.setProgress((int) lvl7pbarvalue);
        lvl7staranim= (ImageView) getView().findViewById(id.starsanim7);
        lvl7staranim.setBackgroundResource(R.drawable.starsanimation);
        //lvl1staranim.setImageResource(R.drawable.starsanimation);
        sanim7= (AnimationDrawable) lvl7staranim.getBackground();
        //sanim7.start();
        lvl7starsstring = (TextView) getView().findViewById(id.statslvl7starsstring);
        lvl7starsstring.setText(starsString7);
        lvl7scorestring= (TextView) getView().findViewById(id.statslvl7score);
        lvl7scorestring.setText(String.valueOf((int)lvl7score));
        
        Cursor lvlgamescursor= dbHelper.getLvl(100);
        double lvlgamesmaxscore= lvlgamescursor.getCount()*100;
        double lvlgamesscore= scoreHelper.getLevelScore(100);
        double lvlgamesdivision= lvlgamesscore / lvlgamesmaxscore;
        double lvlgamespbarvalue= lvlgamesdivision*100;
        lvlgamescursor.close();
        
        int [] starsArraygames= scoreHelper.starsStats(100);
        String starsStringgames = String.valueOf(starsArraygames[0])+" / "+String.valueOf(starsArraygames[1])+" / "+String.valueOf(starsArraygames[2])+"  ";
        
        lvlgamespbar = (ProgressBar) getView().findViewById(id.statslvlgamespbar);
        lvlgamespbar.setProgress((int) lvlgamespbarvalue);
        lvlgamesstaranim= (ImageView) getView().findViewById(id.starsanimgames);
        lvlgamesstaranim.setBackgroundResource(R.drawable.starsanimation);
        //lvl1staranim.setImageResource(R.drawable.starsanimation);
        sanimgames= (AnimationDrawable) lvlgamesstaranim.getBackground();
        //sanimgames.start();
        lvlgamesstarsstring = (TextView) getView().findViewById(id.statslvlgamesstarsstring);
        lvlgamesstarsstring.setText(starsStringgames);
        lvlgamesscorestring= (TextView) getView().findViewById(id.statslvlgamesscore);
        lvlgamesscorestring.setText(String.valueOf((int)lvlgamesscore));
        
        Cursor lvl8cursor= dbHelper.getLvl(8);
        double lvl8maxscore= lvl8cursor.getCount()*100;
        double lvl8score= scoreHelper.getLevelScore(8);
        double lvl8division= lvl8score / lvl8maxscore;
        double lvl8pbarvalue= lvl8division*100;
        lvl8cursor.close();
        
        int [] starsArray8= scoreHelper.starsStats(8);
        String starsString8 = String.valueOf(starsArray8[0])+" / "+String.valueOf(starsArray8[1])+" / "+String.valueOf(starsArray8[2])+"  ";
        
        lvl8pbar = (ProgressBar) getView().findViewById(id.statslvl8pbar);
        lvl8pbar.setProgress((int) lvl8pbarvalue);
        lvl8staranim= (ImageView) getView().findViewById(id.starsanim8);
        lvl8staranim.setBackgroundResource(R.drawable.starsanimation);
        //lvl1staranim.setImageResource(R.drawable.starsanimation);
        sanim8= (AnimationDrawable) lvl8staranim.getBackground();
        //sanim8.start();
        lvl8starsstring = (TextView) getView().findViewById(id.statslvl8starsstring);
        lvl8starsstring.setText(starsString8);
        lvl8scorestring= (TextView) getView().findViewById(id.statslvl8score);
        lvl8scorestring.setText(String.valueOf((int)lvl8score));
        
        Cursor lvl9cursor= dbHelper.getLvl(9);
        double lvl9maxscore= lvl9cursor.getCount()*100;
        double lvl9score= scoreHelper.getLevelScore(9);
        double lvl9division= lvl9score / lvl9maxscore;
        double lvl9pbarvalue= lvl9division*100;
        lvl9cursor.close();
        
        int [] starsArray9= scoreHelper.starsStats(9);
        String starsString9 = String.valueOf(starsArray9[0])+" / "+String.valueOf(starsArray9[1])+" / "+String.valueOf(starsArray9[2])+"  ";
        
        lvl9pbar = (ProgressBar) getView().findViewById(id.statslvl9pbar);
        lvl9pbar.setProgress((int) lvl9pbarvalue);
        lvl9staranim= (ImageView) getView().findViewById(id.starsanim9);
        lvl9staranim.setBackgroundResource(R.drawable.starsanimation);
        //lvl1staranim.setImageResource(R.drawable.starsanimation);
        sanim9= (AnimationDrawable) lvl9staranim.getBackground();
        //sanim9.start();
        lvl9starsstring = (TextView) getView().findViewById(id.statslvl9starsstring);
        lvl9starsstring.setText(starsString9);
        lvl9scorestring= (TextView) getView().findViewById(id.statslvl9score);
        lvl9scorestring.setText(String.valueOf((int)lvl9score));
        
        Cursor lvl10cursor= dbHelper.getLvl(10);
        double lvl10maxscore= lvl7cursor.getCount()*100;
        double lvl10score= scoreHelper.getLevelScore(10);
        double lvl10division= lvl10score / lvl10maxscore;
        double lvl10pbarvalue= lvl10division*100;
        lvl10cursor.close();
        
        int [] starsArray10= scoreHelper.starsStats(10);
        String starsString10 = String.valueOf(starsArray10[0])+" / "+String.valueOf(starsArray10[1])+" / "+String.valueOf(starsArray10[2])+"  ";
        
        lvl10pbar = (ProgressBar) getView().findViewById(id.statslvl10pbar);
        lvl10pbar.setProgress((int) lvl10pbarvalue);
        lvl10staranim= (ImageView) getView().findViewById(id.starsanim10);
        lvl10staranim.setBackgroundResource(R.drawable.starsanimation);
        //lvl1staranim.setImageResource(R.drawable.starsanimation);
        sanim10= (AnimationDrawable) lvl10staranim.getBackground();
        //sanim10.start();
        lvl10starsstring = (TextView) getView().findViewById(id.statslvl10starsstring);
        lvl10starsstring.setText(starsString10);
        lvl10scorestring= (TextView) getView().findViewById(id.statslvl10score);
        lvl10scorestring.setText(String.valueOf((int)lvl10score));
        
        Cursor lvl11cursor= dbHelper.getLvl(11);
        double lvl11maxscore= lvl11cursor.getCount()*100;
        double lvl11score= scoreHelper.getLevelScore(11);
        double lvl11division= lvl11score / lvl11maxscore;
        double lvl11pbarvalue= lvl11division*100;
        lvl11cursor.close();
        
        int [] starsArray11= scoreHelper.starsStats(11);
        String starsString11 = String.valueOf(starsArray11[0])+" / "+String.valueOf(starsArray11[1])+" / "+String.valueOf(starsArray11[2])+"  ";
        
        lvl11pbar = (ProgressBar) getView().findViewById(id.statslvl11pbar);
        lvl11pbar.setProgress((int) lvl11pbarvalue);
        lvl11staranim= (ImageView) getView().findViewById(id.starsanim11);
        lvl11staranim.setBackgroundResource(R.drawable.starsanimation);
        //lvl1staranim.setImageResource(R.drawable.starsanimation);
        sanim11= (AnimationDrawable) lvl11staranim.getBackground();
        //sanim11.start();
        lvl11starsstring = (TextView) getView().findViewById(id.statslvl11starsstring);
        lvl11starsstring.setText(starsString11);
        lvl11scorestring= (TextView) getView().findViewById(id.statslvl11score);
        lvl11scorestring.setText(String.valueOf((int)lvl11score));
        
        Cursor lvl12cursor= dbHelper.getLvl(12);
        double lvl12maxscore= lvl7cursor.getCount()*100;
        double lvl12score= scoreHelper.getLevelScore(12);
        double lvl12division= lvl12score / lvl12maxscore;
        double lvl12pbarvalue= lvl12division*100;
        lvl12cursor.close();
        
        int [] starsArray12= scoreHelper.starsStats(12);
        String starsString12 = String.valueOf(starsArray12[0])+" / "+String.valueOf(starsArray12[1])+" / "+String.valueOf(starsArray12[2])+"  ";
        
        lvl12pbar = (ProgressBar) getView().findViewById(id.statslvl12pbar);
        lvl12pbar.setProgress((int) lvl12pbarvalue);
        lvl12staranim= (ImageView) getView().findViewById(id.starsanim12);
        lvl12staranim.setBackgroundResource(R.drawable.starsanimation);
        //lvl1staranim.setImageResource(R.drawable.starsanimation);
        sanim12= (AnimationDrawable) lvl12staranim.getBackground();
        //sanim12.start();
        lvl12starsstring = (TextView) getView().findViewById(id.statslvl12starsstring);
        lvl12starsstring.setText(starsString12);
        lvl12scorestring= (TextView) getView().findViewById(id.statslvl12score);
        lvl12scorestring.setText(String.valueOf((int)lvl12score));
        
        Cursor lvl13cursor= dbHelper.getLvl(13);
        double lvl13maxscore= lvl13cursor.getCount()*100;
        double lvl13score= scoreHelper.getLevelScore(13);
        double lvl13division= lvl13score / lvl13maxscore;
        double lvl13pbarvalue= lvl13division*100;
        lvl13cursor.close();
        
        int [] starsArray13= scoreHelper.starsStats(13);
        String starsString13 = String.valueOf(starsArray13[0])+" / "+String.valueOf(starsArray13[1])+" / "+String.valueOf(starsArray13[2])+"  ";
        
        lvl13pbar = (ProgressBar) getView().findViewById(id.statslvl13pbar);
        lvl13pbar.setProgress((int) lvl13pbarvalue);
        lvl13staranim= (ImageView) getView().findViewById(id.starsanim13);
        lvl13staranim.setBackgroundResource(R.drawable.starsanimation);
        //lvl1staranim.setImageResource(R.drawable.starsanimation);
        sanim13= (AnimationDrawable) lvl13staranim.getBackground();
        //sanim13.start();
        lvl13starsstring = (TextView) getView().findViewById(id.statslvl13starsstring);
        lvl13starsstring.setText(starsString13);
        lvl13scorestring= (TextView) getView().findViewById(id.statslvl13score);
        lvl13scorestring.setText(String.valueOf((int)lvl13score));
        
        Cursor lvl14cursor= dbHelper.getLvl(14);
        double lvl14maxscore= lvl14cursor.getCount()*100;
        double lvl14score= scoreHelper.getLevelScore(14);
        double lvl14division= lvl14score / lvl14maxscore;
        double lvl14pbarvalue= lvl14division*100;
        lvl14cursor.close();
        
        int [] starsArray14= scoreHelper.starsStats(14);
        String starsString14 = String.valueOf(starsArray14[0])+" / "+String.valueOf(starsArray14[1])+" / "+String.valueOf(starsArray14[2])+"  ";
        
        lvl14pbar = (ProgressBar) getView().findViewById(id.statslvl14pbar);
        lvl14pbar.setProgress((int) lvl14pbarvalue);
        lvl14staranim= (ImageView) getView().findViewById(id.starsanim14);
        lvl14staranim.setBackgroundResource(R.drawable.starsanimation);
        //lvl1staranim.setImageResource(R.drawable.starsanimation);
        sanim14= (AnimationDrawable) lvl14staranim.getBackground();
        //sanim14.start();
        lvl14starsstring = (TextView) getView().findViewById(id.statslvl14starsstring);
        lvl14starsstring.setText(starsString14);
        lvl14scorestring= (TextView) getView().findViewById(id.statslvl14score);
        lvl14scorestring.setText(String.valueOf((int)lvl14score));
        
        Cursor lvl15cursor= dbHelper.getLvl(15);
        double lvl15maxscore= lvl15cursor.getCount()*100;
        double lvl15score= scoreHelper.getLevelScore(15);
        double lvl15division= lvl15score / lvl15maxscore;
        double lvl15pbarvalue= lvl15division*100;
        lvl15cursor.close();
        
        int [] starsArray15= scoreHelper.starsStats(15);
        String starsString15 = String.valueOf(starsArray15[0])+" / "+String.valueOf(starsArray15[1])+" / "+String.valueOf(starsArray15[2])+"  ";
        
        lvl15pbar = (ProgressBar) getView().findViewById(id.statslvl15pbar);
        lvl15pbar.setProgress((int) lvl15pbarvalue);
        lvl15staranim= (ImageView) getView().findViewById(id.starsanim15);
        lvl15staranim.setBackgroundResource(R.drawable.starsanimation);
        //lvl1staranim.setImageResource(R.drawable.starsanimation);
        sanim15= (AnimationDrawable) lvl15staranim.getBackground();
        //sanim15.start();
        lvl15starsstring = (TextView) getView().findViewById(id.statslvl15starsstring);
        lvl15starsstring.setText(starsString15);
        lvl15scorestring= (TextView) getView().findViewById(id.statslvl15score);
        lvl15scorestring.setText(String.valueOf((int)lvl15score));
        
        Cursor lvl16cursor= dbHelper.getLvl(16);
        double lvl16maxscore= lvl16cursor.getCount()*100;
        double lvl16score= scoreHelper.getLevelScore(16);
        double lvl16division= lvl16score / lvl16maxscore;
        double lvl16pbarvalue= lvl16division*100;
        lvl16cursor.close();
        
        int [] starsArray16= scoreHelper.starsStats(16);
        String starsString16 = String.valueOf(starsArray16[0])+" / "+String.valueOf(starsArray16[1])+" / "+String.valueOf(starsArray16[2])+"  ";
        
        lvl16pbar = (ProgressBar) getView().findViewById(id.statslvl16pbar);
        lvl16pbar.setProgress((int) lvl16pbarvalue);
        lvl16staranim= (ImageView) getView().findViewById(id.starsanim16);
        lvl16staranim.setBackgroundResource(R.drawable.starsanimation);
        //lvl1staranim.setImageResource(R.drawable.starsanimation);
        sanim16= (AnimationDrawable) lvl16staranim.getBackground();
        //sanim16.start();
        lvl16starsstring = (TextView) getView().findViewById(id.statslvl16starsstring);
        lvl16starsstring.setText(starsString16);
        lvl16scorestring= (TextView) getView().findViewById(id.statslvl16score);
        lvl16scorestring.setText(String.valueOf((int)lvl16score));

        Cursor lvlpetscursor= dbHelper.getLvl(101);
        double lvlpetsmaxscore= lvlpetscursor.getCount()*100;
        double lvlpetsscore= scoreHelper.getLevelScore(101);
        double lvlpetsdivision= lvlpetsscore / lvlpetsmaxscore;
        double lvlpetspbarvalue= lvlpetsdivision*100;
        lvlpetscursor.close();
        
        int [] starsArraypets= scoreHelper.starsStats(101);
        String starsStringpets = String.valueOf(starsArraypets[0])+" / "+String.valueOf(starsArraypets[1])+" / "+String.valueOf(starsArraypets[2])+"  ";
        
        lvlpetspbar = (ProgressBar) getView().findViewById(id.statslvlpetspbar);
        lvlpetspbar.setProgress((int) lvlpetspbarvalue);
        lvlpetsstaranim= (ImageView) getView().findViewById(id.starsanimpets);
        lvlpetsstaranim.setBackgroundResource(R.drawable.starsanimation);
        //lvl1staranim.setImageResource(R.drawable.starsanimation);
        sanimpets= (AnimationDrawable) lvlpetsstaranim.getBackground();
        //sanim16.start();
        lvlpetsstarsstring = (TextView) getView().findViewById(id.statslvlpetsstarsstring);
        lvlpetsstarsstring.setText(starsStringpets);
        lvlpetsscorestring= (TextView) getView().findViewById(id.statslvlpetsscore);
        lvlpetsscorestring.setText(String.valueOf((int)lvlpetsscore));
        
        Cursor lvl17cursor= dbHelper.getLvl(17);
        double lvl17maxscore= lvl17cursor.getCount()*100;
        double lvl17score= scoreHelper.getLevelScore(17);
        double lvl17division= lvl17score / lvl17maxscore;
        double lvl17pbarvalue= lvl17division*100;
        lvl17cursor.close();
        
        int [] starsArray17= scoreHelper.starsStats(17);
        String starsString17 = String.valueOf(starsArray17[0])+" / "+String.valueOf(starsArray17[1])+" / "+String.valueOf(starsArray17[2])+"  ";
        
        lvl17pbar = (ProgressBar) getView().findViewById(id.statslvl17pbar);
        lvl17pbar.setProgress((int) lvl17pbarvalue);
        lvl17staranim= (ImageView) getView().findViewById(id.starsanim17);
        lvl17staranim.setBackgroundResource(R.drawable.starsanimation);
        //lvl1staranim.setImageResource(R.drawable.starsanimation);
        sanim17= (AnimationDrawable) lvl17staranim.getBackground();
        //sanim16.start();
        lvl17starsstring = (TextView) getView().findViewById(id.statslvl17starsstring);
        lvl17starsstring.setText(starsString17);
        lvl17scorestring= (TextView) getView().findViewById(id.statslvl17score);
        lvl17scorestring.setText(String.valueOf((int)lvl17score));
        
        Cursor lvl18cursor= dbHelper.getLvl(18);
        double lvl18maxscore= lvl18cursor.getCount()*100;
        double lvl18score= scoreHelper.getLevelScore(18);
        double lvl18division= lvl18score / lvl18maxscore;
        double lvl18pbarvalue= lvl18division*100;
        lvl18cursor.close();
        
        int [] starsArray18= scoreHelper.starsStats(18);
        String starsString18 = String.valueOf(starsArray18[0])+" / "+String.valueOf(starsArray18[1])+" / "+String.valueOf(starsArray18[2])+"  ";
        
        lvl18pbar = (ProgressBar) getView().findViewById(id.statslvl18pbar);
        lvl18pbar.setProgress((int) lvl18pbarvalue);
        lvl18staranim= (ImageView) getView().findViewById(id.starsanim18);
        lvl18staranim.setBackgroundResource(R.drawable.starsanimation);
        //lvl1staranim.setImageResource(R.drawable.starsanimation);
        sanim18= (AnimationDrawable) lvl18staranim.getBackground();
        //sanim16.start();
        lvl18starsstring = (TextView) getView().findViewById(id.statslvl18starsstring);
        lvl18starsstring.setText(starsString18);
        lvl18scorestring= (TextView) getView().findViewById(id.statslvl18score);
        lvl18scorestring.setText(String.valueOf((int)lvl18score));
        
        Cursor lvlgames2cursor= dbHelper.getLvl(102);
        double lvlgames2maxscore= lvlgames2cursor.getCount()*100;
        double lvlgames2score= scoreHelper.getLevelScore(102);
        double lvlgames2division= lvlgames2score / lvlgames2maxscore;
        double lvlgames2pbarvalue= lvlgames2division*100;
        lvlgames2cursor.close();
        
        int [] starsArraygames2= scoreHelper.starsStats(102);
        String starsStringgames2 = String.valueOf(starsArraygames2[0])+" / "+String.valueOf(starsArraygames2[1])+" / "+String.valueOf(starsArraygames2[2])+"  ";
        
        lvlgames2pbar = (ProgressBar) getView().findViewById(id.statslvlgames2pbar);
        lvlgames2pbar.setProgress((int) lvlgames2pbarvalue);
        lvlgames2staranim= (ImageView) getView().findViewById(id.starsanimgames2);
        lvlgames2staranim.setBackgroundResource(R.drawable.starsanimation);
        //lvl1staranim.setImageResource(R.drawable.starsanimation);
        sanimgames2= (AnimationDrawable) lvlgames2staranim.getBackground();
        //sanim16.start();
        lvlgames2starsstring = (TextView) getView().findViewById(id.statslvlgames2starsstring);
        lvlgames2starsstring.setText(starsStringgames2);
        lvlgames2scorestring= (TextView) getView().findViewById(id.statslvlgames2score);
        lvlgames2scorestring.setText(String.valueOf((int)lvlgames2score));
        
        Cursor lvl19cursor= dbHelper.getLvl(19);
        double lvl19maxscore= lvl19cursor.getCount()*100;
        double lvl19score= scoreHelper.getLevelScore(19);
        double lvl19division= lvl19score / lvl19maxscore;
        double lvl19pbarvalue= lvl19division*100;
        lvl19cursor.close();
        
        int [] starsArray19= scoreHelper.starsStats(19);
        String starsString19 = String.valueOf(starsArray19[0])+" / "+String.valueOf(starsArray19[1])+" / "+String.valueOf(starsArray19[2])+"  ";
        
        lvl19pbar = (ProgressBar) getView().findViewById(id.statslvl19pbar);
        lvl19pbar.setProgress((int) lvl19pbarvalue);
        lvl19staranim= (ImageView) getView().findViewById(id.starsanim19);
        lvl19staranim.setBackgroundResource(R.drawable.starsanimation);
        //lvl1staranim.setImageResource(R.drawable.starsanimation);
        sanim19= (AnimationDrawable) lvl19staranim.getBackground();
        //sanim16.start();
        lvl19starsstring = (TextView) getView().findViewById(id.statslvl19starsstring);
        lvl19starsstring.setText(starsString19);
        lvl19scorestring= (TextView) getView().findViewById(id.statslvl19score);
        lvl19scorestring.setText(String.valueOf((int)lvl19score));
        
        Cursor lvl20cursor= dbHelper.getLvl(20);
        double lvl20maxscore= lvl20cursor.getCount()*100;
        double lvl20score= scoreHelper.getLevelScore(20);
        double lvl20division= lvl20score / lvl20maxscore;
        double lvl20pbarvalue= lvl20division*100;
        lvl20cursor.close();
        
        int [] starsArray20= scoreHelper.starsStats(20);
        String starsString20 = String.valueOf(starsArray20[0])+" / "+String.valueOf(starsArray20[1])+" / "+String.valueOf(starsArray20[2])+"  ";
        
        lvl20pbar = (ProgressBar) getView().findViewById(id.statslvl20pbar);
        lvl20pbar.setProgress((int) lvl20pbarvalue);
        lvl20staranim= (ImageView) getView().findViewById(id.starsanim20);
        lvl20staranim.setBackgroundResource(R.drawable.starsanimation);
        //lvl1staranim.setImageResource(R.drawable.starsanimation);
        sanim20= (AnimationDrawable) lvl20staranim.getBackground();
        //sanim16.start();
        lvl20starsstring = (TextView) getView().findViewById(id.statslvl20starsstring);
        lvl20starsstring.setText(starsString20);
        lvl20scorestring= (TextView) getView().findViewById(id.statslvl20score);
        lvl20scorestring.setText(String.valueOf((int)lvl20score));
        
        Cursor lvlmoviescursor= dbHelper.getLvl(103);
        double lvlmoviesmaxscore= lvlmoviescursor.getCount()*100;
        double lvlmoviesscore= scoreHelper.getLevelScore(103);
        double lvlmoviesdivision= lvlmoviesscore / lvlmoviesmaxscore;
        double lvlmoviespbarvalue= lvlmoviesdivision*100;
        lvlmoviescursor.close();
        
        int [] starsArraymovies= scoreHelper.starsStats(103);
        String starsStringmovies = String.valueOf(starsArraymovies[0])+" / "+String.valueOf(starsArraymovies[1])+" / "+String.valueOf(starsArraymovies[2])+"  ";
        
        lvlmoviespbar = (ProgressBar) getView().findViewById(id.statslvlmoviespbar);
        lvlmoviespbar.setProgress((int) lvlmoviespbarvalue);
        lvlmoviesstaranim= (ImageView) getView().findViewById(id.starsanimmovies);
        lvlmoviesstaranim.setBackgroundResource(R.drawable.starsanimation);
        //lvl1staranim.setImageResource(R.drawable.starsanimation);
        sanimmovies= (AnimationDrawable) lvlmoviesstaranim.getBackground();
        //sanim16.start();
        lvlmoviesstarsstring = (TextView) getView().findViewById(id.statslvlmoviesstarsstring);
        lvlmoviesstarsstring.setText(starsStringmovies);
        lvlmoviesscorestring= (TextView) getView().findViewById(id.statslvlmoviesscore);
        lvlmoviesscorestring.setText(String.valueOf((int)lvlmoviesscore));
        
        Cursor lvl21cursor= dbHelper.getLvl(21);
        double lvl21maxscore= lvl21cursor.getCount()*100;
        double lvl21score= scoreHelper.getLevelScore(21);
        double lvl21division= lvl21score / lvl21maxscore;
        double lvl21pbarvalue= lvl21division*100;
        lvl21cursor.close();
        
        int [] starsArray21= scoreHelper.starsStats(21);
        String starsString21 = String.valueOf(starsArray21[0])+" / "+String.valueOf(starsArray21[1])+" / "+String.valueOf(starsArray21[2])+"  ";
        
        lvl21pbar = (ProgressBar) getView().findViewById(id.statslvl21pbar);
        lvl21pbar.setProgress((int) lvl21pbarvalue);
        lvl21staranim= (ImageView) getView().findViewById(id.starsanim21);
        lvl21staranim.setBackgroundResource(R.drawable.starsanimation);
        //lvl1staranim.setImageResource(R.drawable.starsanimation);
        sanim21= (AnimationDrawable) lvl21staranim.getBackground();
        //sanim16.start();
        lvl21starsstring = (TextView) getView().findViewById(id.statslvl21starsstring);
        lvl21starsstring.setText(starsString21);
        lvl21scorestring= (TextView) getView().findViewById(id.statslvl21score);
        lvl21scorestring.setText(String.valueOf((int)lvl21score));
        
        Cursor lvl22cursor= dbHelper.getLvl(22);
        double lvl22maxscore= lvl22cursor.getCount()*100;
        double lvl22score= scoreHelper.getLevelScore(22);
        double lvl22division= lvl22score / lvl22maxscore;
        double lvl22pbarvalue= lvl22division*100;
        lvl22cursor.close();
        
        int [] starsArray22= scoreHelper.starsStats(22);
        String starsString22 = String.valueOf(starsArray22[0])+" / "+String.valueOf(starsArray22[1])+" / "+String.valueOf(starsArray22[2])+"  ";
        
        lvl22pbar = (ProgressBar) getView().findViewById(id.statslvl22pbar);
        lvl22pbar.setProgress((int) lvl22pbarvalue);
        lvl22staranim= (ImageView) getView().findViewById(id.starsanim22);
        lvl22staranim.setBackgroundResource(R.drawable.starsanimation);
        //lvl1staranim.setImageResource(R.drawable.starsanimation);
        sanim22= (AnimationDrawable) lvl22staranim.getBackground();
        //sanim16.start();
        lvl22starsstring = (TextView) getView().findViewById(id.statslvl22starsstring);
        lvl22starsstring.setText(starsString22);
        lvl22scorestring= (TextView) getView().findViewById(id.statslvl22score);
        lvl22scorestring.setText(String.valueOf((int)lvl22score));
        
        Cursor lvl23cursor= dbHelper.getLvl(23);
        double lvl23maxscore= lvl23cursor.getCount()*100;
        double lvl23score= scoreHelper.getLevelScore(23);
        double lvl23division= lvl23score / lvl23maxscore;
        double lvl23pbarvalue= lvl23division*100;
        lvl23cursor.close();
        
        int [] starsArray23= scoreHelper.starsStats(23);
        String starsString23 = String.valueOf(starsArray23[0])+" / "+String.valueOf(starsArray23[1])+" / "+String.valueOf(starsArray23[2])+"  ";
        
        lvl23pbar = (ProgressBar) getView().findViewById(id.statslvl23pbar);
        lvl23pbar.setProgress((int) lvl23pbarvalue);
        lvl23staranim= (ImageView) getView().findViewById(id.starsanim23);
        lvl23staranim.setBackgroundResource(R.drawable.starsanimation);
        //lvl1staranim.setImageResource(R.drawable.starsanimation);
        sanim23= (AnimationDrawable) lvl23staranim.getBackground();
        //sanim16.start();
        lvl23starsstring = (TextView) getView().findViewById(id.statslvl23starsstring);
        lvl23starsstring.setText(starsString23);
        lvl23scorestring= (TextView) getView().findViewById(id.statslvl23score);
        lvl23scorestring.setText(String.valueOf((int)lvl23score));
        
        dbHelper.close();
        scoreHelper.close();
        
        if(isVisible()){
        	sanim1.start();
			sanim2.start();
			sanim3.start();
			sanim4.start();
			sanim5.start();
			sanim6.start();
			sanim7.start();
			sanim8.start();
			sanim9.start();
			sanim10.start();
			sanim11.start();
			sanim12.start();
			sanim13.start();
			sanim14.start();
			sanim15.start();
			sanim16.start();
			sanim17.start();
			sanim18.start();
			sanim19.start();
			sanim20.start();
			sanim21.start();
			sanim22.start();
			sanim23.start();
			sanimpets.start();
			sanimgames.start();
			sanimgames2.start();
			sanimmovies.start();
        }
		
	}
	
}
