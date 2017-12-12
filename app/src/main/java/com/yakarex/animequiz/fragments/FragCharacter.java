package com.yakarex.animequiz.fragments;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Random;

import com.crashlytics.android.Crashlytics;
import com.yakarex.animequiz.activities.MainFragActivity;
import com.yakarex.animequiz.R;
import com.yakarex.animequiz.models.AChaCharacterModel;
import com.yakarex.animequiz.models.MessageEvent;
import com.yakarex.animequiz.utils.DBUtil;
import com.yakarex.animequiz.utils.FinalStringsUtils;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.lang3.StringUtils;
import org.greenrobot.eventbus.EventBus;

public class FragCharacter extends Fragment {

    private final static int EMPTY = 0;
    private final static int ANIME = 30;
    private final static int PARTIALNAME = 35;
    private final static int ANIMEANDNAME = 65;
    private final static int FULLNAME = 70;
    private final static int COMPLETED = 100;

    Toast wrongToast;
    AChaCharacterModel characterModel;
    int score;

    ImageView charimage;
    EditText textInput;
    ProgressBar pbar;
    TextView charDialog;
    LinearLayout buttonsContainer;
    Button okButton;
    Button hintButton;
    RelativeLayout animeNameComponent, animeCharacterComponent;
    TextView animeName, characterName;
    CardView imageFrame;

    Toast gotNameToast, gotFnameToast, gotAnimeToast, charCompletedToast;

    Animation shake;

    public static String CHARACTER = "character";
    private DBUtil dbUtil;

    public static FragCharacter newInstance(AChaCharacterModel characterModel) {
        FragCharacter fragment = new FragCharacter();

        Bundle args = new Bundle();
        args.putParcelable(CHARACTER, characterModel);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbUtil = new DBUtil(getContext());
        shake = AnimationUtils.loadAnimation(getContext(), R.anim.shake);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        try {
            characterModel = getArguments().getParcelable(CHARACTER);
            score = dbUtil.getCharScore(characterModel.getCharid(), characterModel.getLevel());
        } catch (NullPointerException npe) {
            Crashlytics.logException(npe);
            npe.printStackTrace();
        }

        View rootView = inflater.inflate(R.layout.frag_character, container,
                false);

        setUpView(rootView);

        return rootView;
    }

    private void setUpView(View rootView) {

        prepareToasts();

        imageFrame = rootView.findViewById(R.id.characterframefragcardview);
        charimage = rootView.findViewById(R.id.characterframefrag);
        textInput = rootView.findViewById(R.id.editText1);
        pbar = rootView.findViewById(R.id.progressBar1);
        charDialog = rootView.findViewById(R.id.chardialog);

        buttonsContainer = rootView.findViewById(R.id.buttons);

        okButton = rootView.findViewById(R.id.okbutton);
        okButton.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {

                if (textInput.getText().length() > 0 && textInput.getText().toString().trim().length() > 0) {
                    checkInputAdvanced();
                }

            }
        });

        hintButton = (Button) rootView.findViewById(R.id.showhint);
        hintButton.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                showHint(v);
            }
        });

        int resID = getResources().getIdentifier(characterModel.getUri(), "drawable", getContext().getPackageName());
        charimage.setImageResource(resID);

        //charimage.setImageURI(characterModel.getUri());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            charimage.setTransitionName("charimage");
        }

        animeNameComponent = (RelativeLayout) rootView.findViewById(R.id.character_anime_component);
        animeCharacterComponent = (RelativeLayout) rootView.findViewById(R.id.character_component);
        animeName = (TextView) rootView.findViewById(R.id.character_anime_name_text_view);
        characterName = (TextView) rootView.findViewById(R.id.character_name_text_view);

        setButtonsStatus();
    }

    private void prepareToasts() {

        Context context = getActivity().getApplicationContext();
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

    public void checkInputAdvanced() {

        switch (score) {
            case EMPTY:
                //Check everything

                //one at a time
                if (!checkAnime()) {
                    if (!checkName(false)) {
                        wrongAnswer();
                    }
                }

                break;
            case PARTIALNAME:
                // Only got the name partially
                // therefore if we get the full name here we must add 35 to the
                // value not 70

                //one at a time
                if (!checkAnime()) {
                    if (!checkName(true)) {
                        wrongAnswer();
                    }
                }

                break;
            case ANIME:
                // Only got the anime

                if (!checkName(false)) {
                    wrongAnswer();
                }

                break;
            case FULLNAME:
                // Only got the full name

                if (!checkAnime()) {
                    wrongAnswer();
                }

                break;
            case ANIMEANDNAME:
                // Got the anime and name

                if (!checkName(true)) {
                    wrongAnswer();
                }

                break;
        }

        updateCharacterView(true);
    }

    public boolean checkAnime() {

        String text = textInput.getText().toString().trim().toLowerCase();
        text = Normalizer.normalize(text, Normalizer.Form.NFD).replaceAll
                ("[^a-zA-Z0-9@ ]", "");


        String[] splittedInput = text.split(" ");
        String charModelAnimeString = characterModel.getAnime();
        charModelAnimeString = Normalizer.normalize(charModelAnimeString, Normalizer.Form.NFD).replaceAll
                ("[^a-zA-Z0-9@ ]", "");

        String[] splittedAnime = charModelAnimeString.split("@");

        ArrayList<String> splittedWCoincidences = new ArrayList<>();

        String regex = "\\b(";

        for (String aSplittedInput : splittedInput) {

            regex = regex + aSplittedInput + "|";
            for (String aSplittedAnime : splittedAnime) {
                if (aSplittedAnime.contains(aSplittedInput)) {
                    if (!splittedWCoincidences.contains(aSplittedAnime)) {
                        splittedWCoincidences.add(aSplittedAnime);
                    }
                }
            }
        }

        regex = regex + ")\\b";
        regex = regex.replace("|)", ")");

        ArrayList<String> checkResult = new ArrayList<>();
        for (String aSplittedCoincidence : splittedWCoincidences) {
            checkResult.add(aSplittedCoincidence.replaceAll(regex, "X"));

        }

        for (String result : checkResult) {

            int matches = StringUtils.countMatches(result, "X");
            result = result.replace(" ", "");

            if (result.length() == matches) {

                gotAnime(text);

                return true;
            }
        }

        return false;
    }

    public boolean checkName(boolean expectFullAnswer) {

        String text = textInput.getText().toString().trim().toLowerCase();
        text = Normalizer.normalize(text, Normalizer.Form.NFD).replaceAll
                ("[^a-zA-Z0-9@ ]", "");

        String[] splittedInput = text.split(" ");
        String fullNameString = characterModel.getFullname();
        fullNameString = Normalizer.normalize(fullNameString, Normalizer.Form.NFD).replaceAll
                ("[^a-zA-Z0-9@ ]", "");
        String[] splittedCharName = fullNameString.split("@");

        ArrayList<String> splittedWCoincidences = new ArrayList<>();

        String regex = "\\b(";

        for (String aSplittedInput : splittedInput) {

            regex = regex + aSplittedInput + "|";
            for (String aSplittedCharName : splittedCharName) {
                if (aSplittedCharName.contains(aSplittedInput)) {
                    if (!splittedWCoincidences.contains(aSplittedCharName)) {
                        splittedWCoincidences.add(aSplittedCharName);
                    }
                }
            }
        }

        regex = regex + ")\\b";
        regex = regex.replace("|)", ")");

        ArrayList<String> checkResult = new ArrayList<>();
        for (String aSplittedCoincidence : splittedWCoincidences) {
            checkResult.add(aSplittedCoincidence.replaceAll(regex, "X"));
        }
//search for full answer
        for (String result : checkResult) {

            int matches = StringUtils.countMatches(result, "X");
            result = result.replace(" ", "");

            if (result.length() == matches) {

                gotNameFully(text, expectFullAnswer);

                return true;
            }
        }
//THEN search for a partial one
        for (String result : checkResult) {

            int matches = StringUtils.countMatches(result, "X");
            if (matches > 0 && !expectFullAnswer) {

                String[] inputArray = text.split(" ");

                String inputToSave = "";
                for (String input : inputArray) {
                    if (characterModel.getFullname().contains(input)) {
                        inputToSave = inputToSave + input + " ";
                    }
                }

                inputToSave = inputToSave.trim();

                gotNamePartially(inputToSave);
                textInput.setText(inputToSave);
                return true;
            }
        }

        return false;

    }

    public void gotAnime(String text) {

        //score = score + ANIME;
        gotAnimeToast.show();
        dbUtil.setCharInputedAnime(characterModel.getCharid(), text);
        ((MainFragActivity) getActivity()).hapticsManager(FinalStringsUtils.GOOD);
        dbUtil.setCharScore(characterModel.getCharid(), ANIME, characterModel.getLevel());
        unlockingCheck(ANIME);
        textInput.setText("");
    }

    public void gotNamePartially(String text) {

        //score = score + PARTIALNAME;
        gotNameToast.show();
        dbUtil.setCharInputedName(characterModel.getCharid(), text);
        ((MainFragActivity) getActivity()).hapticsManager(FinalStringsUtils.GOOD);
        dbUtil.setCharScore(characterModel.getCharid(), PARTIALNAME, characterModel.getLevel());
        unlockingCheck(PARTIALNAME);

    }

    public void gotNameFully(String text, boolean fullAnwserWasExpected) {

        int scoreToBeAdded;

        if (fullAnwserWasExpected) {
            scoreToBeAdded = PARTIALNAME;
            //score = score + PARTIALNAME;
        } else {
            scoreToBeAdded = FULLNAME;
            //score = score + FULLNAME;
        }

        charCompletedToast.show();
        dbUtil.setCharInputedName(characterModel.getCharid(), text);
        ((MainFragActivity) getActivity()).hapticsManager(FinalStringsUtils.GOOD);
        dbUtil.setCharScore(characterModel.getCharid(), scoreToBeAdded, characterModel.getLevel());
        unlockingCheck(scoreToBeAdded);

        textInput.setText("");

    }

    public void showScore() {

        EventBus.getDefault().post(new MessageEvent(FinalStringsUtils.UPDATESCORE));
    }

    public void unlockingCheck(int scoreToAdd) {

        int totalScoreInt = Integer.parseInt(dbUtil.getTotalScore());
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

        imageFrame.startAnimation(shake);

        wrongToast.show();
        //The haptics manager also checks the achievements
        ((MainFragActivity) getActivity()).hapticsManager(FinalStringsUtils.WRONG);
        ((MainFragActivity) getActivity()).manageAchievements(FinalStringsUtils.INCREMENT, FinalStringsUtils.FAILACHIEV, 1);

    }

    public void showHint(View view) {


        dbUtil.setCharScore(FinalStringsUtils.HINTCOUNT, 1, 0);

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

        updateCharacterView(true);

    }

    private void updateCharacterView(boolean animated) {

        score = dbUtil.getCharScore(characterModel.getCharid(), characterModel.getLevel());

        int charScore = 100;
        pbar.setMax(charScore);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            try{
                if (score == charScore) {
                    pbar.setProgressTintList(ColorStateList.valueOf(getContext().getResources().getColor(R.color.golden)));
                } else if (score <= 0) {
                    pbar.setProgressTintList(ColorStateList.valueOf(getContext().getResources().getColor(R.color.gray)));
                } else if (score >= (charScore / 2)) {
                    pbar.setProgressTintList(ColorStateList.valueOf(getContext().getResources().getColor(R.color.silver)));
                } else if (score <= (charScore / 2)) {
                    pbar.setProgressTintList(ColorStateList.valueOf(getContext().getResources().getColor(R.color.bronze)));
                }
            }
            catch (NullPointerException npe){
                npe.printStackTrace();
                Crashlytics.logException(npe);
            }

        }

        if (animated) {

            ObjectAnimator animation = ObjectAnimator.ofInt(pbar, "progress", score);
            animation.setDuration(500); // 0.5 second
            animation.setInterpolator(new DecelerateInterpolator());
            animation.start();
        } else {
            pbar.setProgress(score);
        }

        if (score >= 100) {
            okButton.setEnabled(false);
            hintButton.setEnabled(false);
            buttonsContainer.setVisibility(View.GONE);
            animeNameComponent.setVisibility(View.VISIBLE);
            animeCharacterComponent.setVisibility(View.VISIBLE);
            animeName.setText(dbUtil.getCharInputedAnime(characterModel.getCharid()));
            characterName.setText(dbUtil.getCharInputedName(characterModel.getCharid(), false));
            characterName.setSelected(true);
            animeName.setSelected(true);
            textInput.setVisibility(View.GONE);
            textInput.setEnabled(false);
            textInput.setClickable(false);
        } else {
            okButton.setEnabled(true);
            hintButton.setEnabled(true);
            buttonsContainer.setVisibility(View.VISIBLE);
            animeNameComponent.setVisibility(View.GONE);
            animeCharacterComponent.setVisibility(View.GONE);
            textInput.setVisibility(View.VISIBLE);
            textInput.setEnabled(true);
            textInput.setClickable(true);
            characterName.setSelected(true);
            animeName.setSelected(true);
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

                //the partially inputed name is showed
                textInput.setText(dbUtil.getCharInputedName(characterModel.getCharid(), true));
                break;
            case 100:
                charDialog.setText(R.string.charcompleted);
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(charimage.getWindowToken(), 0);
                }
                break;
        }

        showScore();
    }

}
