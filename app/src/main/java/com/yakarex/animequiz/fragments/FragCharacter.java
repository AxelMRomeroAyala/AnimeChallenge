package com.yakarex.animequiz.fragments;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.yakarex.animequiz.activities.MainFragActivity;
import com.yakarex.animequiz.R;
import com.yakarex.animequiz.models.AChaCharacterModel;
import com.yakarex.animequiz.utils.FinalStringsUtils;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class FragCharacter extends Fragment{

    Toast wrongToast;
    int hintsNumber;
    Cursor cursor;
    int position;
    AChaCharacterModel characterModel;
    int score;

    ImageView charimage;
    ImageView prevBtn;
    ImageView nextBtn;
    EditText textInput;
    ProgressBar pbar;
    TextView charDialog;
    LinearLayout buttonsContainer;
    Button okButton;
    Button hintButton;

    Toast gotNameToast, gotFnameToast, gotAnimeToast, charCompletedToast;

    public static String CHARACTER= "character";

    public static FragCharacter newInstance(AChaCharacterModel characterModel){
        FragCharacter fragment = new FragCharacter();

        Bundle args = new Bundle();
        args.putParcelable(CHARACTER, characterModel);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //we get the position from the bundle and the cursor from the mainactivity, then we set the data
        position = getArguments().getInt("position");

        cursor = ((MainFragActivity) getActivity()).getLvlCursor();
        cursor.moveToPosition(position);

        characterModel = (AChaCharacterModel) getArguments().getParcelable(CHARACTER);

        Log.e("Getting Score: ", String.valueOf(score));
        score = ((MainFragActivity) getActivity()).getCharScore(characterModel.getCharid(), characterModel.getLevel());

        View rootView = inflater.inflate(R.layout.frag_character, container,
                false);

        setUpView(rootView);

        return rootView;
    }

    private void setUpView(View rootView){

        prepareToasts();

        charimage = (ImageView) rootView.findViewById(R.id.characterframefrag);
        textInput = (EditText) rootView.findViewById(R.id.editText1);
        pbar = (ProgressBar) rootView.findViewById(R.id.progressBar1);
        charDialog = (TextView) rootView.findViewById(R.id.chardialog);

        buttonsContainer=(LinearLayout) rootView.findViewById(R.id.buttons);

        okButton = (Button) rootView.findViewById(R.id.okbutton);
        okButton.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {

                checkInput();
            }
        });

        hintButton = (Button) rootView.findViewById(R.id.showhint);
        hintButton.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                showHint(v);
            }
        });

        prevBtn = (ImageView) rootView.findViewById(R.id.prevButton);
        prevBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                //btnBehaviour("prev");
            }
        });

        nextBtn = (ImageView) rootView.findViewById(R.id.nextButton);
        nextBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                //btnBehaviour("next");
            }
        });

        charimage.setImageURI(characterModel.getUri());

        setButtonsStatus();

        showScore();
    }

    private void prepareToasts(){

        Context context = ((MainFragActivity) getActivity()).getApplicationContext();
        gotNameToast = Toast.makeText(context, R.string.gotname,
                Toast.LENGTH_SHORT);
        gotNameToast.setGravity(Gravity.TOP, 0, 0);
        gotFnameToast = Toast.makeText(context, R.string.gotfullname,
                Toast.LENGTH_SHORT);
        gotFnameToast.setGravity(Gravity.TOP, 0, 0);
        gotAnimeToast = Toast.makeText(context, R.string.gotanime,
                Toast.LENGTH_SHORT);
        gotAnimeToast.setGravity(Gravity.TOP, 0, 0);
        charCompletedToast = Toast.makeText(context,
                R.string.charcompleted, Toast.LENGTH_SHORT);
        charCompletedToast.setGravity(Gravity.TOP, 0, 0);
        wrongToast = Toast.makeText(context, R.string.wrong, Toast.LENGTH_SHORT);
        wrongToast.setGravity(Gravity.TOP, 0, 0);
    }

    @Override
    public void onResume() {
        super.onResume();

        charDialog.setSelected(true);
    }

    public void checkInput() {

        String text = textInput.getText().toString().trim().toLowerCase();
        // we clean the input
        textInput.setText("");
        // we create 2 arrays, one from the input and the other from the full
        // name

        int matches = comparator(text, characterModel.getFullname());
        int animematches = comparator(text, characterModel.getAnime());

        // we have an issue here, the swith that shows later compare the matches
        // with the lenght method of the array taken from the db,
        // we need to split it 2 times now before comparation
        String[][] beforecompare = stringSplitter(characterModel.getFullname());
        String[] nameArray = beforecompare[0];

        String[][] beforecompareanime = stringSplitter(characterModel.getAnime());
        String[] animeArray = beforecompareanime[0];

        updateCharacterView(true);


        switch (score) {
            case 0:

                if (animematches >= animeArray.length) {
                    score = score + 30;
                    gotAnimeToast.show();
                    ((MainFragActivity) getActivity()).setCharInputedAnime(characterModel.getCharid(), text);
                    ((MainFragActivity) getActivity()).hapticsManager(FinalStringsUtils.GOOD);
                    ((MainFragActivity) getActivity()).setCharScore(characterModel.getCharid(), 30, characterModel.getLevel());
                    updateCharacterView(true);
                    unlockingCheck(30);
                } else if (matches >= nameArray.length) {
                    score = score + 70;
                    gotFnameToast.show();
                    ((MainFragActivity) getActivity()).hapticsManager(FinalStringsUtils.GOOD);
                    ((MainFragActivity) getActivity()).setCharScore(characterModel.getCharid(), 70, characterModel.getLevel());
                    updateCharacterView(true);
                    unlockingCheck(70);
                } else if (matches > 0) {
                    score = score + 35;
                    gotNameToast.show();
                    ((MainFragActivity) getActivity()).hapticsManager(FinalStringsUtils.GOOD);
                    ((MainFragActivity) getActivity()).setCharScore(characterModel.getCharid(), 35, characterModel.getLevel());
                    updateCharacterView(true);
                    unlockingCheck(35);
                }

                else {
                    wrongAnswer();
                }

                break;
            case 35:
                // Only got the name partially
                // therefore if we get the full name here we must add 35 to the
                // value not 70
                if (animematches >= animeArray.length) {
                    score = score + 30;
                    gotAnimeToast.show();
                    ((MainFragActivity) getActivity()).setCharInputedAnime(characterModel.getCharid(), text);
                    ((MainFragActivity) getActivity()).hapticsManager(FinalStringsUtils.GOOD);
                    ((MainFragActivity) getActivity()).setCharScore(characterModel.getCharid(), 30, characterModel.getLevel());
                    updateCharacterView(true);
                    unlockingCheck(30);
                } else if (matches >= nameArray.length) {
                    score = score + 35;
                    gotFnameToast.show();
                    ((MainFragActivity) getActivity()).hapticsManager(FinalStringsUtils.GOOD);
                    ((MainFragActivity) getActivity()).setCharScore(characterModel.getCharid(), 35, characterModel.getLevel());
                    updateCharacterView(true);
                    unlockingCheck(35);
                } else {
                    wrongAnswer();
                }

                break;
            case 30:
                // Only got the anime
                if (matches >= nameArray.length) {
                    score = score + 70;
                    charCompletedToast.show();
                    ((MainFragActivity) getActivity()).hapticsManager(FinalStringsUtils.GOOD);
                    String animeforcomplete;
                    String fnameforcomplete;
                    if (characterModel.getAnime().contains("@")) {
                        String[] animearray = characterModel.getAnime().split("@");
                        animeforcomplete = animearray[0];
                    } else {
                        animeforcomplete = characterModel.getAnime();
                    }
                    if (characterModel.getFullname().contains("@")) {
                        String[] fnamearray = characterModel.getFullname().split("@");
                        fnameforcomplete = fnamearray[0];
                    } else {
                        fnameforcomplete = characterModel.getFullname();
                    }
                    String separator = getString(R.string.from);
                    String stringforcompletedialog = fnameforcomplete + " "
                            + separator + " " + animeforcomplete;

                    ((MainFragActivity) getActivity()).setCharScore(characterModel.getCharid(), 70, characterModel.getLevel());
                    updateCharacterView(true);
                    unlockingCheck(70);
                } else if (matches > 0) {
                    score = score + 35;
                    gotNameToast.show();
                    ((MainFragActivity) getActivity()).hapticsManager(FinalStringsUtils.GOOD);
                    ((MainFragActivity) getActivity()).setCharScore(characterModel.getCharid(), 35, characterModel.getLevel());
                    updateCharacterView(true);
                    unlockingCheck(35);
                } else {
                    wrongAnswer();
                }
                break;
            case 70:
                // Only got the full name
                if (animematches >= animeArray.length) {
                    score = score + 30;
                    charCompletedToast.show();
                    ((MainFragActivity) getActivity()).setCharInputedAnime(characterModel.getCharid(), text);
                    ((MainFragActivity) getActivity()).hapticsManager(FinalStringsUtils.GOOD);
                    ((MainFragActivity) getActivity()).setCharScore(characterModel.getCharid(), 30, characterModel.getLevel());
                    updateCharacterView(true);
                    String animeforcomplete;
                    String fnameforcomplete;

                    if (((MainFragActivity) getActivity()).getCharInputedAnime(characterModel.getCharid()) != null) {
                        animeforcomplete = ((MainFragActivity) getActivity()).getCharInputedAnime(characterModel.getCharid());
                    } else {
                        if (characterModel.getAnime().contains("@")) {
                            String[] animearray = characterModel.getAnime().split("@");
                            animeforcomplete = animearray[0];
                        } else {
                            animeforcomplete = characterModel.getAnime();
                        }
                    }
                    if (characterModel.getFullname().contains("@")) {
                        String[] fnamearray = characterModel.getFullname().split("@");
                        fnameforcomplete = fnamearray[0];
                    } else {
                        fnameforcomplete = characterModel.getFullname();
                    }
                    String separator = getString(R.string.from);
                    String stringforcompletedialog = fnameforcomplete + " "
                            + separator + " " + animeforcomplete;

                    updateCharacterView(true);
                    unlockingCheck(30);

                } else {
                    wrongAnswer();
                }
                break;
            case 65:
                // Got the anime and name
                if (matches >= nameArray.length) {
                    score = score + 35;
                    charCompletedToast.show();
                    ((MainFragActivity) getActivity()).hapticsManager(FinalStringsUtils.GOOD);
                    ((MainFragActivity) getActivity()).setCharScore(characterModel.getCharid(), 35, characterModel.getLevel());
                    updateCharacterView(true);
                    String animeforcomplete;
                    String fnameforcomplete;

                    if (((MainFragActivity) getActivity()).getCharInputedAnime(characterModel.getCharid()) != null) {
                        animeforcomplete = ((MainFragActivity) getActivity()).getCharInputedAnime(characterModel.getCharid());
                    } else {
                        if (characterModel.getAnime().contains("@")) {
                            String[] animearray = characterModel.getAnime().split("@");
                            animeforcomplete = animearray[0];
                        } else {
                            animeforcomplete = characterModel.getAnime();
                        }
                    }
                    if (characterModel.getFullname().contains("@")) {
                        String[] fnamearray = characterModel.getFullname().split("@");
                        fnameforcomplete = fnamearray[0];
                    } else {
                        fnameforcomplete = characterModel.getFullname();
                    }
                    String separator = getString(R.string.from);
                    String stringforcompletedialog = fnameforcomplete + " "
                            + separator + " " + animeforcomplete;

                    updateCharacterView(true);
                    unlockingCheck(35);

                } else {
                    wrongAnswer();
                }
                break;
            case 100:
                charCompletedToast.show();
                ((MainFragActivity) getActivity()).hapticsManager(FinalStringsUtils.GOOD);
                String animeforcomplete;
                String fnameforcomplete;

                if (((MainFragActivity) getActivity()).getCharInputedAnime(characterModel.getCharid()) != null) {
                    animeforcomplete = ((MainFragActivity) getActivity()).getCharInputedAnime(characterModel.getCharid());
                } else {
                    if (characterModel.getAnime().contains("@")) {
                        String[] animearray = characterModel.getAnime().split("@");
                        animeforcomplete = animearray[0];
                    } else {
                        animeforcomplete = characterModel.getAnime();
                    }
                }

                if (characterModel.getFullname().contains("@")) {
                    String[] fnamearray = characterModel.getFullname().split("@");
                    fnameforcomplete = fnamearray[0];
                } else {
                    fnameforcomplete = characterModel.getFullname();
                }
                String separator = getString(R.string.from);
                String stringforcompletedialog = fnameforcomplete + " " + separator
                        + " " + animeforcomplete;


                updateCharacterView(true);

                break;
        }

    }

    public void showScore() {

        //TODO update the score in the parent
    }

    public void unlockingCheck(int scoreToAdd) {

        int totalScoreInt = Integer.parseInt(((MainFragActivity) getActivity()).getTotalScore());
        Context context = (getActivity()).getApplicationContext();
        Toast lvlunlocked = Toast.makeText(context, "", Toast.LENGTH_SHORT);

        for (int x = 3; x < FinalStringsUtils.lvlunlockinglogicarray.length; x++) {
            if (totalScoreInt < FinalStringsUtils.lvlunlockinglogicarray[x]
                    && totalScoreInt + scoreToAdd >= FinalStringsUtils.lvlunlockinglogicarray[x]) {
                String unlockText;
                //Gamer Level
                if (x == 8) {
                    unlockText = getResources().getString(R.string.unlockedtoast) + " " + getResources().getString(R.string.levelgames);
                }
                //Pets level
                else if (x == 18) {
                    unlockText = getResources().getString(R.string.unlockedtoast) + " " + getResources().getString(R.string.levelpets);
                }
                //Gamer Level 2
                else if (x == 21) {
                    unlockText = getResources().getString(R.string.unlockedtoast) + " " + getResources().getString(R.string.levelgames2);
                }
                //Movies Level
                else if (x == 24) {
                    unlockText = getResources().getString(R.string.unlockedtoast) + " " + getResources().getString(R.string.levelmovies);
                } else {
                    if (x > 24) {
                        unlockText = getResources().getString(R.string.unlockedtoast) + " " + String.valueOf(x - 4);
                    } else if (x > 21) {
                        unlockText = getResources().getString(R.string.unlockedtoast) + " " + String.valueOf(x - 3);
                    } else if (x > 18) {
                        unlockText = getResources().getString(R.string.unlockedtoast) + " " + String.valueOf(x - 2);
                    } else if (x > 8) {
                        unlockText = getResources().getString(R.string.unlockedtoast) + " " + String.valueOf(x - 1);
                    } else {
                        unlockText = getResources().getString(R.string.unlockedtoast) + " " + String.valueOf(x);
                    }
                }

                lvlunlocked.setText(unlockText);
                lvlunlocked.setGravity(Gravity.TOP, 0, 0);
                ((MainFragActivity) getActivity()).hapticsManager(FinalStringsUtils.UNLOCKED);
                lvlunlocked.show();
            }
        }

        if (totalScoreInt >= 9000) {
            ((MainFragActivity) getActivity()).manageAchievements(FinalStringsUtils.UNLOCK, 0, 0);
        }


    }

    public void wrongAnswer() {

        wrongToast.show();
        //The haptics manager also checks the achievements
        ((MainFragActivity) getActivity()).hapticsManager(FinalStringsUtils.WRONG);

        ((MainFragActivity) getActivity()).manageAchievements(FinalStringsUtils.INCREMENT, FinalStringsUtils.FAILACHIEV, 1);
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

        dbfield = dbfield.toLowerCase().trim();

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
        } else {
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
        if (characterModel.getAnime().contains("@")) {
            String[] hintarray = characterModel.getAnime().split("@");
            Random rnd = new Random();
            hint = hintarray[rnd.nextInt(hintarray.length)];
        } else {
            hint = characterModel.getAnime();
        }
        hint = hint.replace("a", "*").replace("e", "*").replace("i", "*")
                .replace("o", "*").replace("u", "*").toUpperCase();

        ((MainFragActivity) getActivity()).manageAchievements(FinalStringsUtils.INCREMENT, FinalStringsUtils.HINTACHIEV, 1);

        charDialog.setText("Anime hint: " + hint);

    }

    public void setButtonsStatus() {

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

        updateCharacterView(false);

    }

    private void updateCharacterView(boolean animated){

        if(animated){
            ObjectAnimator animation = ObjectAnimator.ofInt(pbar, "progress", score);
            animation.setDuration(500); // 0.5 second
            animation.setInterpolator(new DecelerateInterpolator());
            animation.start();
        }
        else {
            pbar.setProgress(score);
        }

        if(score>= 100){
            okButton.setEnabled(false);
            hintButton.setEnabled(false);
            buttonsContainer.setVisibility(View.INVISIBLE);
            textInput.setVisibility(View.INVISIBLE);
            textInput.setEnabled(false);
            textInput.setClickable(false);
        }
        else{
            okButton.setEnabled(true);
            hintButton.setEnabled(true);
            buttonsContainer.setVisibility(View.VISIBLE);
            textInput.setVisibility(View.VISIBLE);
            textInput.setEnabled(true);
            textInput.setClickable(true);
        }

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

        showScore();
    }

}
