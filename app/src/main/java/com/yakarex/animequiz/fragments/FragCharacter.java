package com.yakarex.animequiz.fragments;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.yakarex.animequiz.activities.MainFragActivity;
import com.yakarex.animequiz.R;
import com.yakarex.animequiz.R.id;
import com.yakarex.animequiz.utils.FinalStringsUtils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class FragCharacter extends Fragment {
	
	Toast wrongToast;
	int hintsNumber;
	Vibrator v;

	Cursor cursor;
	int position;
	int charid;

	int level;
	String genre;
	String anime;
	String fullname;
	Uri uri;
	
	int score;
	
	String scoreString;
	TextView charscoreView;

	ImageView charimage;
	ImageView prevBtn;
	ImageView nextBtn;
	EditText textInput;
	ProgressBar pbar;
	TextView charDialog;
	Button okButton;
	Button hintButton;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//we get the position from the bundle and the cursor from the mainactivity, then we set the data
		position= getArguments().getInt("position");
		cursor= ((MainFragActivity)getActivity()).getLvlCursor();
		cursor.moveToPosition(position);

		charid = cursor.getInt(1);
		level = cursor.getInt(2);
		genre = cursor.getString(3);
		anime = cursor.getString(4);
		fullname = cursor.getString(5);
		uri = Uri.parse(cursor.getString(6));
		
		
		View rootView = inflater.inflate(R.layout.frag_character, container,
				false);

		charimage = (ImageView) rootView.findViewById(R.id.characterframefrag);
		textInput = (EditText) rootView.findViewById(R.id.editText1);
		pbar = (ProgressBar) rootView.findViewById(R.id.progressBar1);
		charDialog = (TextView) rootView.findViewById(R.id.chardialog);
		
		okButton = (Button) rootView.findViewById(R.id.okbutton);
		okButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
			
				checkInput(v);
			}
		});
		
		hintButton = (Button) rootView.findViewById(R.id.showhint);
		hintButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				showHint(v);	
			}
		});
		
		
		charscoreView = (TextView) rootView.findViewById(id.scoreviewchar);

		prevBtn = (ImageView) rootView.findViewById(R.id.prevButton);
		prevBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				btnBehaviour("prev");
			}
		});

		nextBtn = (ImageView) rootView.findViewById(R.id.nextButton);
		nextBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				btnBehaviour("next");
			}
		});

		buttonsVisibility();

		charimage.setImageURI(uri);
		
		setButtonsStatus();
		
		showScore();
		
		return rootView;
	}

	@Override
	public void onResume() {
		super.onResume();

		charDialog.setSelected(true);
	}

	public void buttonsVisibility() {

		if (cursor.isFirst()) {
			prevBtn.setVisibility(View.INVISIBLE);
		} else if (cursor.isLast()) {
			nextBtn.setVisibility(View.INVISIBLE);
		} else {
			prevBtn.setVisibility(View.VISIBLE);
			nextBtn.setVisibility(View.VISIBLE);
		}

	}

	public void btnBehaviour(String behaviour) {

		if (behaviour == "next") {
			cursor.moveToNext();
		} else if (behaviour == "prev") {
			cursor.moveToPrevious();
		}

		Log.e("Cursor position:", String.valueOf(cursor.getPosition()));
		buttonsVisibility();

		charid = cursor.getInt(1);
		level = cursor.getInt(2);
		genre = cursor.getString(3);
		anime = cursor.getString(4);
		fullname = cursor.getString(5);
		uri = Uri.parse(cursor.getString(6));

		charimage.setImageURI(uri);
		
		setButtonsStatus();

	}

	public void checkInput(View view) {

		String text = textInput.getText().toString().trim().toLowerCase();
		// we clean the input
		textInput.setText("");
		// we create 2 arrays, one from the input and the other from the full
		// name

		int matches = comparator(text, fullname);
		int animematches = comparator(text, anime);

		// we have an issue here, the swith that shows later compare the matches
		// with the lenght method of the array taken from the db,
		// we need to split it 2 times now before comparation
		String[][] beforecompare = stringSplitter(fullname);
		String[] nameArray = beforecompare[0];

		String[][] beforecompareanime = stringSplitter(anime);
		String[] animeArray = beforecompareanime[0];

		pbar.setProgress(score);
		Context context = ((MainFragActivity)getActivity()).getApplicationContext();
		Toast gotNameToast = Toast.makeText(context, R.string.gotname,
				Toast.LENGTH_SHORT);
		gotNameToast.setGravity(Gravity.TOP, 0, 0);
		Toast gotFnameToast = Toast.makeText(context, R.string.gotfullname,
				Toast.LENGTH_SHORT);
		gotFnameToast.setGravity(Gravity.TOP, 0, 0);
		Toast gotAnimeToast = Toast.makeText(context, R.string.gotanime,
				Toast.LENGTH_SHORT);
		gotAnimeToast.setGravity(Gravity.TOP, 0, 0);
		Toast charCompletedToast = Toast.makeText(context,
				R.string.charcompleted, Toast.LENGTH_SHORT);
		charCompletedToast.setGravity(Gravity.TOP, 0, 0);
		wrongToast = Toast
				.makeText(context, R.string.wrong, Toast.LENGTH_SHORT);
		wrongToast.setGravity(Gravity.TOP, 0, 0);

		switch (score) {
		case 0:

			charDialog.setText(R.string.qall);

			if (animematches >= animeArray.length) {
				score = score + 30;
				gotAnimeToast.show();
				((MainFragActivity)getActivity()).setCharInputedAnime(charid, text);
				((MainFragActivity)getActivity()).hapticsManager(FinalStringsUtils.GOOD);
				((MainFragActivity)getActivity()).setCharScore(charid, 30, level);
				charDialog.setText(R.string.qname);
				showScore();
				pbar.setProgress(score);
				unlockingCheck(30);
			}

			else if (matches >= nameArray.length) {
				score = score + 70;
				gotFnameToast.show();
				((MainFragActivity)getActivity()).hapticsManager(FinalStringsUtils.GOOD);
				charDialog.setText(R.string.qanime);
				((MainFragActivity)getActivity()).setCharScore(charid, 70, level);
				showScore();
				pbar.setProgress(score);
				unlockingCheck(70);
			}

			else if (matches > 0) {
				score = score + 35;
				gotNameToast.show();
				((MainFragActivity)getActivity()).hapticsManager(FinalStringsUtils.GOOD);
				charDialog.setText(R.string.qanime);
				((MainFragActivity)getActivity()).setCharScore(charid, 35, level);
				showScore();
				pbar.setProgress(score);
				unlockingCheck(35);
			}

			// else if (extra.compareTo(text) == 0) {
			// score = score + 25;
			// textInput.setText("Got extra data ;) Score +25");
			// scoreHelper.setCharScore(charid, 25, level);
			// pbar.setProgress(score);
			// }

			else {
				wrongAnswer();
			}

			break;
		case 35:
			// Only got the name partially
			charDialog.setText(R.string.qanime);
			// therefore if we get the full name here we must add 35 to the
			// value not 70
			if (animematches >= animeArray.length) {
				score = score + 30;
				gotAnimeToast.show();
				((MainFragActivity)getActivity()).setCharInputedAnime(charid, text);
				((MainFragActivity)getActivity()).hapticsManager(FinalStringsUtils.GOOD);
				charDialog.setText(R.string.qfname);
				((MainFragActivity)getActivity()).setCharScore(charid, 30, level);
				showScore();
				pbar.setProgress(score);
				unlockingCheck(30);
			} else if (matches >= nameArray.length) {
				score = score + 35;
				gotFnameToast.show();
				((MainFragActivity)getActivity()).hapticsManager(FinalStringsUtils.GOOD);
				charDialog.setText(R.string.qanime);
				((MainFragActivity)getActivity()).setCharScore(charid, 35, level);
				showScore();
				pbar.setProgress(score);
				unlockingCheck(35);
			}
			else {
				wrongAnswer();
			}

			break;
		case 30:
			// Only got the anime
			charDialog.setText(R.string.qname);
			if (matches >= nameArray.length) {
				score = score + 70;
				charCompletedToast.show();
				((MainFragActivity)getActivity()).hapticsManager(FinalStringsUtils.GOOD);
				charDialog.setText(R.string.charcompleted);
				String animeforcomplete;
				String fnameforcomplete;
				if (anime.contains("@")) {
					String[] animearray = anime.split("@");
					animeforcomplete = animearray[0];
				} else {
					animeforcomplete = anime;
				}
				if (fullname.contains("@")) {
					String[] fnamearray = fullname.split("@");
					fnameforcomplete = fnamearray[0];
				} else {
					fnameforcomplete = fullname;
				}
				String separator = getString(R.string.from);
				String stringforcompletedialog = fnameforcomplete + " "
						+ separator + " " + animeforcomplete;

				charDialog.setText(stringforcompletedialog.toUpperCase());

				pbar.setProgress(100);
				okButton.setEnabled(false);
				hintButton.setEnabled(false);
				textInput.setEnabled(false);
				textInput.setClickable(false);
				okButton.setEnabled(false);
				textInput.setEnabled(false);
				textInput.setClickable(false);
				((MainFragActivity)getActivity()).setCharScore(charid, 70, level);
				showScore();
				pbar.setProgress(score);
				unlockingCheck(70);
			}

			else if (matches > 0) {
				score = score + 35;
				gotNameToast.show();
				((MainFragActivity)getActivity()).hapticsManager(FinalStringsUtils.GOOD);
				charDialog.setText(R.string.qfname);
				((MainFragActivity)getActivity()).setCharScore(charid, 35, level);
				showScore();
				pbar.setProgress(score);
				unlockingCheck(35);
			}

			else {
				wrongAnswer();
			}
			break;
		case 70:
			// Only got the full name
			charDialog.setText("anime?");
			if (animematches >= animeArray.length) {
				score = score + 30;
				charCompletedToast.show();
				((MainFragActivity)getActivity()).setCharInputedAnime(charid, text);
				((MainFragActivity)getActivity()).hapticsManager(FinalStringsUtils.GOOD);
				((MainFragActivity)getActivity()).setCharScore(charid, 30, level);
				showScore();
				pbar.setProgress(score);
				charDialog.setText(R.string.charcompleted);
				String animeforcomplete;
				String fnameforcomplete;
				
				if(((MainFragActivity)getActivity()).getCharInputedAnime(charid)!= null){
					animeforcomplete= ((MainFragActivity)getActivity()).getCharInputedAnime(charid);
				}
				
				else{
					if (anime.contains("@")) {
						String[] animearray = anime.split("@");
						animeforcomplete = animearray[0];
					} else {
						animeforcomplete = anime;
					}
				}
				if (fullname.contains("@")) {
					String[] fnamearray = fullname.split("@");
					fnameforcomplete = fnamearray[0];
				} else {
					fnameforcomplete = fullname;
				}
				String separator = getString(R.string.from);
				String stringforcompletedialog = fnameforcomplete + " "
						+ separator + " " + animeforcomplete;

				charDialog.setText(stringforcompletedialog.toUpperCase());

				pbar.setProgress(100);
				unlockingCheck(30);

				okButton.setEnabled(false);
				hintButton.setEnabled(false);
				textInput.setEnabled(false);
				textInput.setClickable(false);
				okButton.setEnabled(false);
				textInput.setEnabled(false);
				textInput.setClickable(false);
			}
			else {
				wrongAnswer();
			}
			break;
		case 65:
			// Got the anime and name
			charDialog.setText(R.string.qfname);
			if (matches >= nameArray.length) {
				score = score + 35;
				charCompletedToast.show();
				((MainFragActivity)getActivity()).hapticsManager(FinalStringsUtils.GOOD);
				((MainFragActivity)getActivity()).setCharScore(charid, 35, level);
				pbar.setProgress(score);
				showScore();
				charDialog.setText(R.string.charcompleted);
				String animeforcomplete;
				String fnameforcomplete;
				
				if(((MainFragActivity)getActivity()).getCharInputedAnime(charid)!= null){
					animeforcomplete= ((MainFragActivity)getActivity()).getCharInputedAnime(charid);
				}
				
				else{
					if (anime.contains("@")) {
						String[] animearray = anime.split("@");
						animeforcomplete = animearray[0];
					} else {
						animeforcomplete = anime;
					}
				}
				if (fullname.contains("@")) {
					String[] fnamearray = fullname.split("@");
					fnameforcomplete = fnamearray[0];
				} else {
					fnameforcomplete = fullname;
				}
				String separator = getString(R.string.from);
				String stringforcompletedialog = fnameforcomplete + " "
						+ separator + " " + animeforcomplete;

				charDialog.setText(stringforcompletedialog.toUpperCase());

				pbar.setProgress(100);
				unlockingCheck(35);

				okButton.setEnabled(false);
				hintButton.setEnabled(false);
				textInput.setEnabled(false);
				textInput.setClickable(false);
				okButton.setEnabled(false);
				textInput.setEnabled(false);
				textInput.setClickable(false);
			} else {
				wrongAnswer();
			}
			break;
		case 100:
			charDialog.setText(R.string.charcompleted);
			charCompletedToast.show();
			((MainFragActivity)getActivity()).hapticsManager(FinalStringsUtils.GOOD);
			pbar.setProgress(score);
			showScore();
			charDialog.setEnabled(false);
			String animeforcomplete;
			String fnameforcomplete;
			
			if(((MainFragActivity)getActivity()).getCharInputedAnime(charid)!= null){
				animeforcomplete= ((MainFragActivity)getActivity()).getCharInputedAnime(charid);
			}
			
			else{
				if (anime.contains("@")) {
					String[] animearray = anime.split("@");
					animeforcomplete = animearray[0];
				} else {
					animeforcomplete = anime;
				}
			}
			
			if (fullname.contains("@")) {
				String[] fnamearray = fullname.split("@");
				fnameforcomplete = fnamearray[0];
			} else {
				fnameforcomplete = fullname;
			}
			String separator = getString(R.string.from);
			String stringforcompletedialog = fnameforcomplete + " " + separator
					+ " " + animeforcomplete;

			charDialog.setText(stringforcompletedialog.toUpperCase());

			pbar.setProgress(100);
			okButton.setEnabled(false);
			hintButton.setEnabled(false);
			textInput.setEnabled(false);
			textInput.setClickable(false);

			break;
		}
		
		showScore();
	}

	public void showScore() {
		// get and show the total and level score
		if (level == 100) {
			String totalscoreText = (String) getText(R.string.totalscore);
			String lvlscoreText = (String) getText(R.string.lvlscore);
			String lvlname = (String) getText(R.string.games);
			scoreString = lvlscoreText + " " + lvlname + ": "
					+ String.valueOf(((MainFragActivity)getActivity()).getLevelScore(level)) + " "
					+ totalscoreText + ": "
					+ String.valueOf(((MainFragActivity)getActivity()).getTotalScore());

		}

		if (level == 101) {
			String totalscoreText = (String) getText(R.string.totalscore);
			String lvlscoreText = (String) getText(R.string.lvlscore);
			String lvlname = "Pets";
			scoreString = lvlscoreText + " " + lvlname + ": "
					+ String.valueOf(((MainFragActivity)getActivity()).getLevelScore(level)) + " "
					+ totalscoreText + ": "
					+ String.valueOf(((MainFragActivity)getActivity()).getTotalScore());

		}

		else {
			String totalscoreText = (String) getText(R.string.totalscore);
			String lvlscoreText = (String) getText(R.string.lvlscore);
			scoreString = lvlscoreText + " " + String.valueOf(level) + ": "
					+ String.valueOf(((MainFragActivity)getActivity()).getLevelScore(level)) + " "
					+ totalscoreText + ": "
					+ String.valueOf(((MainFragActivity)getActivity()).getTotalScore());
		}

		charscoreView.setText(scoreString);
		hintsNumber = Integer.parseInt(((MainFragActivity)getActivity()).getTotalScore()) / 100;
	}

	public void unlockingCheck(int scoreToAdd) {

		int totalScoreInt = Integer.parseInt(((MainFragActivity)getActivity()).getTotalScore());
		Context context = ((MainFragActivity)getActivity()).getApplicationContext();
		Toast lvlunlocked = Toast.makeText(context, "", Toast.LENGTH_SHORT);

		for(int x= 3;x<FinalStringsUtils.lvlunlockinglogicarray.length ;x++){
			if (totalScoreInt < FinalStringsUtils.lvlunlockinglogicarray[x]
					&& totalScoreInt + scoreToAdd >= FinalStringsUtils.lvlunlockinglogicarray[x]) {
				String unlockText;
				//Gamer Level
				if(x == 8){
					unlockText= getResources().getString(R.string.unlockedtoast) +" "+getResources().getString(R.string.levelgames);
				}
				//Pets level
				else if(x == 18){
					unlockText= getResources().getString(R.string.unlockedtoast) +" "+ getResources().getString(R.string.levelpets);
				}
				//Gamer Level 2
				else if(x == 21){
					unlockText= getResources().getString(R.string.unlockedtoast) +" "+ getResources().getString(R.string.levelgames2);
				}
				//Movies Level
				else if(x == 24){
					unlockText= getResources().getString(R.string.unlockedtoast) +" "+ getResources().getString(R.string.levelmovies);
				}
				else{
					if(x>24){
						unlockText= getResources().getString(R.string.unlockedtoast) +" "+ String.valueOf(x-4);
					}
					else if(x>21){
						unlockText= getResources().getString(R.string.unlockedtoast) +" "+ String.valueOf(x-3);
					}
					else if(x>18){
						unlockText= getResources().getString(R.string.unlockedtoast) +" "+ String.valueOf(x-2);
					}
					else if(x>8){
						unlockText= getResources().getString(R.string.unlockedtoast) +" "+ String.valueOf(x-1);
					}
					else{
						unlockText= getResources().getString(R.string.unlockedtoast) +" "+ String.valueOf(x);
					}
				}

				lvlunlocked.setText(unlockText);
				lvlunlocked.setGravity(Gravity.TOP, 0, 0);
				((MainFragActivity)getActivity()).hapticsManager(FinalStringsUtils.UNLOCKED);
				lvlunlocked.show();
			}
		}

		if(totalScoreInt >= 9000){
			((MainFragActivity)getActivity()).manageAchievements(FinalStringsUtils.UNLOCK, 0, 0);
		}

		
	}
	
	public void wrongAnswer(){
		
		wrongToast.show();
		//The haptics manager also checks the achievements
		((MainFragActivity)getActivity()).hapticsManager(FinalStringsUtils.WRONG);

		((MainFragActivity)getActivity()).manageAchievements(FinalStringsUtils.INCREMENT, FinalStringsUtils.FAILACHIEV, 1);
	}

	private String[][] stringSplitter(String tosplit) {
		String[] splitted1 = tosplit.split("@");
		List<String[]> bdarraylist = new ArrayList<String[]>();
		int lenght = splitted1.length;
		for (int x = 0; x < lenght; x++) {
			bdarraylist.add(splitted1[x].split(" "));
		}
		String[][] array2D = new String[bdarraylist.size()][];
		bdarraylist.toArray(array2D);

		return array2D;
	}

	private int comparator(String ctext, String dbfield) {
		String text = ctext;
		String[] inputArray = text.split(" ");

		dbfield= dbfield.toLowerCase().trim();

		if (dbfield.contains("@")) {
			String[][] bidircomparativearray = stringSplitter(dbfield);
			int outmatches = 0;

			for (int outx = 0; outx < bidircomparativearray.length; outx++) {
				int inmatches = 0;
				// Log.d("comparator", "inmatches"+String.valueOf(inmatches));

				for (int x = 0; x < inputArray.length; x++) {
					String[] inarray = bidircomparativearray[outx];
					for (int y = 0; y < inarray.length; y++) {
						if (inputArray[x].compareTo(inarray[y]) == 0) {
							// Log.d("comparator", "comparing: "+inputArray[x]);
							// Log.d("comparator", "with: "+inarray[y]);
							inmatches = inmatches + 1;
						}
					}
				}

				if (inmatches > outmatches) {
					outmatches = inmatches;
				}

			}
			return outmatches;
		}

		else {
			String[] nameArray = dbfield.split(" ");

			int matches = 0;
			for (int x = 0; x < inputArray.length; x++) {
				for (String word : nameArray) {
					if (inputArray[x].compareTo(word) == 0) {
						matches = matches + 1;
					}
				}
			}
			// Log.d("comparator", "matches "+String.valueOf(matches));
			return matches;

		}
	}

	public void showHint(View view) {

		// Se guardara el contador de hints como un score mas, los campos seran
		// compeltados asi: charid= 987654321, score=
		// scoreHelper.setCharScore(charid, 35, level);

		String hint;
		if (anime.contains("@")) {
			String[] hintarray = anime.split("@");
			Random rnd = new Random();
			hint = hintarray[rnd.nextInt(hintarray.length)];
		} else {
			hint = anime;
		}
		hint = hint.replace("a", "*").replace("e", "*").replace("i", "*")
				.replace("o", "*").replace("u", "*").toUpperCase();

		((MainFragActivity) getActivity()).manageAchievements(FinalStringsUtils.INCREMENT, FinalStringsUtils.HINTACHIEV, 1);

		charDialog.setText("Anime hint: " + hint);

	}

	public void setButtonsStatus() {
		score = ((MainFragActivity) getActivity()).getCharScore(charid, level);

		Log.e("Getting Score: ", String.valueOf(score));

		if (score < 100) {
			okButton.setEnabled(true);
			hintButton.setEnabled(true);
			textInput.setEnabled(true);
			textInput.setClickable(true);
		} else {
			okButton.setEnabled(false);
			hintButton.setEnabled(false);
			textInput.setEnabled(false);
			textInput.setClickable(false);
		}

		pbar.setProgress(score);

		
		// Here the char dialog is settled
		switch (score) {
		case 0:
			charDialog.setText(R.string.qall);
			break;
		case 35:
			charDialog.setText(R.string.qanime);
			break;
		case 30:
			charDialog.setText(R.string.qname);
			break;
		case 70:
			charDialog.setText(R.string.qanime);
			break;
		case 65:
			charDialog.setText(R.string.qfname);
			break;
		case 100:
			charDialog.setText(R.string.charcompleted);
			break;
		}

	}
	
}
