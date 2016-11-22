package com.yakarex.animequiz;

import com.yakarex.animequiz.R.id;
import com.yakarex.animequiz.utils.ScoreDbHelper;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;



public class CustomCursorAdapter extends SimpleCursorAdapter {
	
	@SuppressWarnings("unused")
	private Context context;
	private int layout;
	
	@SuppressWarnings("deprecation")
	public CustomCursorAdapter(Context context, int layout, Cursor c,
			String[] from, int[] to) {
		super(context, layout, c, from, to);
		this.context = context;
		this.layout = layout;
		
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent){
		
		Cursor c= getCursor();
		
		final LayoutInflater inflater= LayoutInflater.from(context);
		View v = inflater.inflate(layout, parent, false);
		
		int keyCharId= c.getInt(1);
		
		int lvl =c.getInt(2);
		
		String imgPath= c.getString(6);
		Uri imguri = Uri.parse(imgPath);
		
		ImageView charFrame= (ImageView) v.findViewById(id.charimg);
		ImageView star= (ImageView) v.findViewById(id.starimg);
		
		ScoreDbHelper scoreHelper = new ScoreDbHelper(context);
		scoreHelper.openDataBase();
		int score = scoreHelper.getCharScore(keyCharId, lvl);
		scoreHelper.close();
		
		charFrame.setImageURI(imguri);
	
		if (score >0 && score< 40){
			star.setImageResource(R.drawable.cupperstar);
		}
		else if (score >40 && score< 100){
			star.setImageResource(R.drawable.silver);
		}
		else if (score >= 100){
			star.setImageResource(R.drawable.goldstar);
		}
		else {
			star.setImageResource(R.drawable.starempty);
		}
		
		
		return v;
	}
	
	@Override
	public void bindView(View v, Context context, Cursor c){
		
		int keyCharId= c.getInt(1);
		
		int lvl =c.getInt(2);
		
		String imgPath= c.getString(6);
		Uri imguri = Uri.parse(imgPath);
		
		ImageView charFrame= (ImageView) v.findViewById(id.charimg);
		ImageView star= (ImageView) v.findViewById(id.starimg);
		
		ScoreDbHelper scoreHelper = new ScoreDbHelper(context);
		scoreHelper.openDataBase();
		int score = scoreHelper.getCharScore(keyCharId, lvl);
		scoreHelper.close();
		
		charFrame.setImageURI(imguri);
	
		if (score >0 && score< 40){
			star.setImageResource(R.drawable.cupperstar);
		}
		else if (score >40 && score< 100){
			star.setImageResource(R.drawable.silver);
		}
		else if (score >= 100){
			star.setImageResource(R.drawable.goldstar);
		}
		else {
			star.setImageResource(R.drawable.starempty);
		}
		
		
		
	}
	

	

}
