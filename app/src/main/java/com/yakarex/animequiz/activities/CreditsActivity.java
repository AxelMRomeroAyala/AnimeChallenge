package com.yakarex.animequiz.activities;

import java.io.IOException;
import java.util.Random;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import com.yakarex.animequiz.R;

public class CreditsActivity extends Activity {

	RelativeLayout creditstext;
	WebView creditsweb;
	AssetManager assetManager;
	MediaPlayer player;
	boolean audioOff;
	
	Random rdm= new Random();
	int num;
	String [] SAQ= {"midis/chronotrigger.mid", "midis/evangelion.mid", "midis/megaman.mid", "midis/pokemon.mid", "midis/sailormoon.mid", "midis/saintseiya.mid", "midis/slamdunk.mid"};
	AssetFileDescriptor descriptor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_credits);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		Animation translatebu= AnimationUtils.loadAnimation(this, R.anim.scrolldown);
		translatebu.setRepeatCount(Animation.INFINITE);
		translatebu.setRepeatMode(Animation.RESTART);
		
//		creditsweb= (WebView) findViewById(R.id.creditsweb);
//		
//		creditsweb.loadUrl("file:///android_asset/credits.html");
//		creditsweb.setBackgroundColor(0x00000000);
//		
//		creditsweb.startAnimation(translatebu);
		
		SharedPreferences settings = getSharedPreferences("com.yacarex.animequiz", 0);
		audioOff = settings.getBoolean("audioOff", false);
		
		if(audioOff){
			
		}
		else{
			mPlayer();	
		}
		creditstext= (RelativeLayout) findViewById(R.id.creditstext);
		creditstext.setGravity(Gravity.CENTER_HORIZONTAL);
		creditstext.startAnimation(translatebu);
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if(audioOff){
			
		}
		else{
			player.pause();
			player.release();
		}
	}
	
	public void mPlayer(){
		
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		player= new MediaPlayer();
		try {
			assetManager= getAssets();
			
      		num= rdm.nextInt(SAQ.length);
      	
      		descriptor = assetManager.openFd(SAQ[num]);
      	
      	
			player.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
			player.prepare();
			
			//player.setLooping(true);
			player.start();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void musicRandom(View view){
		if(audioOff){
			
		}
		else{
			player.pause();
			player.release();
			
			mPlayer();	
		}
		
	}

}
