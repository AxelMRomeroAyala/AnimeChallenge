package com.yakarex.animequiz.activities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.SQLException;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.example.games.basegameutils.BaseGameUtils;
import com.yakarex.animequiz.models.MessageEvent;
import com.yakarex.animequiz.utils.DBUtil;
import com.yakarex.animequiz.utils.DataBaseHelper;
import com.yakarex.animequiz.fragments.FragLevel;
import com.yakarex.animequiz.fragments.FragOptions;
import com.yakarex.animequiz.fragments.FragMainMenu;
import com.yakarex.animequiz.R;
import com.yakarex.animequiz.R.id;
import com.yakarex.animequiz.utils.ScoreDbHelper;
import com.yakarex.animequiz.utils.FinalStringsUtils;
import com.yakarex.animequiz.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainFragActivity extends FragmentActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        RewardedVideoAdListener{

    private AdView adView;
    private InterstitialAd interstitial;
    private RewardedVideoAd mRewardedVideoAd;
    private String currentFragment;
    private String newFragment;
    private List<String> backStackList;
    private Context context;
    private Cursor lvlCursor;

    FragmentManager fragmentManager;
    private boolean audioOff;
    private boolean vibrationOff;
    static boolean ads = true;

    SoundPool soundPool;
    int lvlunlockedID;
    int goodbellID;
    int wrongID;

    double playTime;
    long startTime;

    Vibrator v;

    private GoogleApiClient mGoogleApiClient;
    private static int RC_SIGN_IN = 9001;
    private static int REQUEST_ACHIEVEMENTS = 4008;
    private static int REQUEST_LEADERBOARD = 4009;

    private boolean mResolvingConnectionFailure = false;
    private boolean mAutoStartSignInflow;
    private boolean mSignInClicked = false;

    boolean mExplicitSignOut = false;
    boolean mInSignInFlow = false;

    boolean isSignedIn = false;

    final Handler mHandler = new Handler();

    private AdView mAdView;
    private DBUtil dbUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_frag);

        dbUtil= new DBUtil(this);

        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.SMART_BANNER);
        adView.setAdUnitId(getResources().getString(R.string.adunitid));

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("90FF8FB418074AAB3EAAE3A12C474257")
                .build();
        mAdView.loadAd(adRequest);

        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(this);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        SharedPreferences settings = getSharedPreferences("com.yacarex.animequiz", 0);

        mAutoStartSignInflow = settings.getBoolean("AutoSingIn", false);

        backStackList = new ArrayList<String>();

        context = getApplicationContext();
        //changeFragment(FragMainMenu.instantiate(context, FragMainMenu.class.getName()), true, false);
        changeFragment(FragMainMenu.instantiate(context, FragMainMenu.class.getName()));

        // Create the Google Api Client with access to the Play Games services
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Games.API).addScope(Games.SCOPE_GAMES)
                // add other APIs and scopes here as needed
                .build();

        startTime = SystemClock.elapsedRealtime();

//        adView = (AdView) this.findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        adView.loadAd(adRequest);

        audioOff = settings.getBoolean("audioOff", false);
        vibrationOff = settings.getBoolean("vibrationOff", false);

        Context context = getBaseContext();

        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        AssetManager assetManager = context.getAssets();
        soundPool = new SoundPool(20, AudioManager.STREAM_MUSIC, 0);
        AssetFileDescriptor descriptor;
        try {
            descriptor = assetManager.openFd("lvlunlocked.mp3");
            lvlunlockedID = soundPool.load(descriptor, 1);
            descriptor = assetManager.openFd("goodbell.mp3");
            goodbellID = soundPool.load(descriptor, 1);
            descriptor = assetManager.openFd("wrong.mp3");
            wrongID = soundPool.load(descriptor, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

//        // Create the interstitial.
//        interstitial = new InterstitialAd(this);
//        interstitial.setAdUnitId(getString(R.string.adunitinterad));
//
//        // Create ad request.
//        AdRequest adRequest2 = new AdRequest.Builder().build();
//
//        // Begin loading your interstitial.
//        interstitial.loadAd(adRequest2);
//
//        if (ads) {
//            adThread();
//            ads = false;
//        }

        loadRewardedVideoAd();
    }

    private void loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd("ca-app-pub-5318551320912238/7160028352",
                new AdRequest.Builder().build());
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mRewardedVideoAd.resume(this);
        //adView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        mRewardedVideoAd.pause(this);
        //adView.pause();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();

        dbUtil.finish();
        mRewardedVideoAd.destroy(this);

        long endTime = SystemClock.elapsedRealtime();

        playTime = endTime - startTime;

        Log.d("Play Time Was", String.valueOf(playTime) + " Miliseconds");
    }

    @Override
    public void onBackPressed() {

        if (backStackList.size() == 1 && backStackList.get(backStackList.size() - 1).compareTo(currentFragment) == 0) {
            Utils.getAlertDialog(MainFragActivity.this
                    , R.string.exit, R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }, R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    }).show();

        } else {
            popFragment();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.frag_main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.options:
                openOptions();
                return true;

            case R.id.vibrate:
                vibrationManager();
                return true;

            case R.id.volume:
                audioManager();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void openOptions() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FragOptions fragment = new FragOptions();
        fragmentTransaction.replace(R.id.mainfragment, fragment).addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void audioManager() {

        SharedPreferences settings = getSharedPreferences("com.yacarex.animequiz", 0);
        SharedPreferences.Editor editor = settings.edit();
        audioOff = settings.getBoolean("audioOff", false);

        if (audioOff) {
            editor.putBoolean("audioOff", false);
            editor.apply();
            Toast volon = Toast.makeText(getBaseContext(), R.string.volon, Toast.LENGTH_SHORT);
            volon.show();
        } else {
            editor.putBoolean("audioOff", true);
            editor.apply();
            Toast voloff = Toast.makeText(getBaseContext(), R.string.voloff, Toast.LENGTH_SHORT);
            voloff.show();
        }
    }

    public void vibrationManager() {

        SharedPreferences settings = getSharedPreferences("com.yacarex.animequiz", 0);
        SharedPreferences.Editor editor = settings.edit();
        vibrationOff = settings.getBoolean("vibrationOff", false);

        if (vibrationOff) {
            editor.putBoolean("vibrationOff", false);
            editor.apply();
            Toast vibon = Toast.makeText(getBaseContext(), R.string.vibon, Toast.LENGTH_SHORT);
            long[] pattern = {0, 500};
            v.vibrate(pattern, -1);
            vibon.show();
        } else {
            editor.putBoolean("vibrationOff", true);
            editor.apply();
            Toast viboff = Toast.makeText(getBaseContext(), R.string.viboff, Toast.LENGTH_SHORT);
            viboff.show();
        }
    }

    public void hapticsManager(int reaction) {

        SharedPreferences settings = getSharedPreferences("com.yacarex.animequiz", 0);
        audioOff = settings.getBoolean("audioOff", false);
        vibrationOff = settings.getBoolean("vibrationOff", false);

        int dot = 200;      // Length of a Morse Code "dot" in milliseconds
        //int dash = 500;     // Length of a Morse Code "dash" in milliseconds
        int short_gap = 100;    // Length of Gap Between dots/dashes
        //int medium_gap = 500;   // Length of Gap Between Letters
        //int long_gap = 1000;    // Length of Gap Between Words
        long[] pattern = {0, dot, short_gap, dot};

        if (reaction == FinalStringsUtils.GOOD) {
            //checkAchievements(GOOD);
            if (!audioOff && !vibrationOff) {
                //do both
                soundPool.play(goodbellID, 1.0f, 1.0f, 0, 0, 1);
                v.vibrate(300);
            } else if (audioOff && !vibrationOff) {
                //vibrate
                v.vibrate(300);
            } else if (!audioOff && vibrationOff) {
                //play audio
                soundPool.play(goodbellID, 1.0f, 1.0f, 0, 0, 1);
            } else {
                //do nothing
            }
        }
        if (reaction == FinalStringsUtils.WRONG) {
            //checkAchievements(WRONGCOUNT);
            if (!audioOff && !vibrationOff) {
                //do both
                soundPool.play(wrongID, 1.0f, 1.0f, 0, 0, 1);
                v.vibrate(pattern, -1);
            } else if (audioOff && !vibrationOff) {
                //vibrate
                v.vibrate(pattern, -1);
            } else if (!audioOff && vibrationOff) {
                //play audio
                soundPool.play(wrongID, 1.0f, 1.0f, 0, 0, 1);
            } else {
                //do nothing
            }
        }
        if (reaction == FinalStringsUtils.UNLOCKED) {
            if (!audioOff && !vibrationOff) {
                //do both
                soundPool.play(lvlunlockedID, 1.0f, 1.0f, 0, 0, 1);
                v.vibrate(600);
            } else if (audioOff && !vibrationOff) {
                //vibrate
                v.vibrate(600);
            } else if (!audioOff && vibrationOff) {
                //play audio
                soundPool.play(lvlunlockedID, 1.0f, 1.0f, 0, 0, 1);
            } else {
                //do nothing
            }
        }
    }

    public void disclaimer() {
        String message = getString(R.string.disclaimermessage);
        String close = getString(R.string.close);
        String title = getString(R.string.disclaimer);

        new AlertDialog.Builder(this)
                .setMessage(message)
                .setCancelable(false)
                .setNeutralButton(close, null).setTitle(title)
                .show();

    }

    protected void adThread() {
        Thread ads = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(20000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mHandler.post(mostrarAd);
            }
        };
        ads.start();
    }

    public void displayInterstitial() {
        if (interstitial.isLoaded()) {
            interstitial.show();
        }
    }

    final Runnable mostrarAd = new Runnable() {
        public void run() {
            displayInterstitial();
        }
    };

    private void popFragment() {
        int lastPos = backStackList.size() - 1;
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();

        if (currentFragment.compareTo(backStackList.get(lastPos)) == 0) {
            t.detach(getSupportFragmentManager().findFragmentByTag(backStackList.get(lastPos)));
            backStackList.remove(lastPos);
            lastPos -= 1;
        } else {
            t.detach(getSupportFragmentManager().findFragmentByTag(currentFragment));
        }

        t.attach(getSupportFragmentManager().findFragmentByTag(backStackList.get(lastPos)));

        currentFragment = backStackList.get(lastPos);
        t.commitAllowingStateLoss();
    }

    public void changeFragment(Fragment myNewFragment, Boolean addToBackStack, boolean clearStack) {
        Log.e("Fragment Name", myNewFragment.getClass().getName());
        newFragment = myNewFragment.getClass().getName();

        if (currentFragment == null || currentFragment.compareTo(newFragment) != 0) {

            FragmentTransaction t = getSupportFragmentManager().beginTransaction();
            t.setCustomAnimations(R.anim.fade_in_animation, R.anim.fade_out_animation);

            if (clearStack) {
                backStackList.clear();
            }

            if (addToBackStack) {
                t.addToBackStack(myNewFragment.getClass().getName());
                backStackList.add(newFragment);
                if (currentFragment != null) {
                    t.detach(getSupportFragmentManager().findFragmentByTag(currentFragment));
                }
                currentFragment = newFragment;

            } else {

                if (backStackList.size() > 1) {
                    t.remove(getSupportFragmentManager().findFragmentByTag(backStackList.get(backStackList.size() - 1)));
                    if (currentFragment != null && currentFragment.compareTo(backStackList.get(backStackList.size() - 1)) != 0) {
                        t.remove(getSupportFragmentManager().findFragmentByTag(currentFragment));
                    }
                }
                currentFragment = newFragment;
            }
            t.add(id.mainfragment, myNewFragment, newFragment);
            t.commitAllowingStateLoss();
        }
    }


    public void changeFragment(Fragment myNewFragment) {

        Log.e("Fragment Name", myNewFragment.getClass().getName());
        newFragment = myNewFragment.getClass().getName();

        if (currentFragment == null || currentFragment.compareTo(newFragment) != 0) {

            FragmentTransaction t = getSupportFragmentManager().beginTransaction();
            t.setCustomAnimations(R.anim.fade_in_animation, R.anim.fade_out_animation, R.anim.fade_in_animation, R.anim.fade_out_animation);

            t.addToBackStack(myNewFragment.getClass().getName());
            backStackList.add(newFragment);
            if (currentFragment != null) {
                t.detach(getSupportFragmentManager().findFragmentByTag(currentFragment));
            }
            currentFragment = newFragment;

            t.add(id.mainfragment, myNewFragment, newFragment);
            t.commit();
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        if(event.message.equals(FinalStringsUtils.RANDOMCLICKED)){
            if (mRewardedVideoAd.isLoaded()) {
                mRewardedVideoAd.show();
            }
        }
    }

    public void openLevelbyId(int lvlId) {

        lvlCursor = dbUtil.getDataBaseHelper().getLvl(lvlId);

        Bundle bundle = new Bundle();
        bundle.putInt("lvl", lvlId);
        //changeFragment(FragLevel.instantiate(context, FragLevel.class.getName(), bundle), true, false);
        changeFragment(FragLevel.instantiate(context, FragLevel.class.getName(), bundle));

    }

    public void onConnected(Bundle bundle) {

        Log.e("PLAY SERVICES", "CONNECT SUCCESS");
        // The player is signed in. Hide the sign-in button and allow the
        // player to proceed.

        FragMainMenu fragMainMenu = (FragMainMenu) getSupportFragmentManager().findFragmentByTag(FragMainMenu.class.getName());
        fragMainMenu.setShowSignIn(false);

        isSignedIn = true;

        Games.setViewForPopups(mGoogleApiClient, findViewById(R.id.gps_popup));

        //TODO manage plays count offline too
        manageAchievements(FinalStringsUtils.INCREMENT, FinalStringsUtils.PLAYACHIEV, 1);
    }

    public void onConnectionSuspended(int i) {
        // Attempt to reconnect
        mGoogleApiClient.connect();

    }

    public void onConnectionFailed(ConnectionResult connectionResult) {

        FragMainMenu fragMainMenu = (FragMainMenu) getSupportFragmentManager().findFragmentByTag(FragMainMenu.class.getName());
        fragMainMenu.setShowSignIn(true);

        isSignedIn = false;

        Log.e("PLAY SERVICES", "CONNECT FAILED");
        if (mResolvingConnectionFailure) {
            // already resolving
            return;
        }

        // if the sign-in button was clicked or if auto sign-in is enabled,
        // launch the sign-in flow
        if (mSignInClicked || mAutoStartSignInflow) {
            mAutoStartSignInflow = false;
            mSignInClicked = false;
            mResolvingConnectionFailure = true;

            // Attempt to resolve the connection failure using BaseGameUtils.
            // The R.string.signin_other_error value should reference a generic
            // error string in your strings.xml file, such as "There was
            // an issue with sign-in, please try again later."
            if (!BaseGameUtils.resolveConnectionFailure(this,
                    mGoogleApiClient, connectionResult,
                    RC_SIGN_IN, getString(R.string.signin_other_error))) {
                mResolvingConnectionFailure = false;
            }
        }

        // Put code here to display the sign-in button
    }

    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        if (requestCode == RC_SIGN_IN) {
            mSignInClicked = false;
            mResolvingConnectionFailure = false;
            if (resultCode == RESULT_OK) {
                mGoogleApiClient.connect();
            } else {
                // Bring up an error dialog to alert the user that sign-in
                // failed. The R.string.signin_failure should reference an error
                // string in your strings.xml file that tells the user they
                // could not be signed in, such as "Unable to sign in."
                BaseGameUtils.showActivityResultError(this,
                        requestCode, resultCode, R.string.signin_failure);
            }
        }
    }

    // Call when the sign-out button is clicked
    public void signOutclicked() {
        Log.e("SIGN OUT", "CLICKED!");
        mSignInClicked = false;
        //Games.signOut(mGoogleApiClient);

        mExplicitSignOut = true;
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            Games.signOut(mGoogleApiClient);
            mGoogleApiClient.disconnect();
            FragMainMenu fragMainMenu = (FragMainMenu) getSupportFragmentManager().findFragmentByTag(FragMainMenu.class.getName());
            fragMainMenu.setShowSignIn(true);

            SharedPreferences settings = getSharedPreferences("com.yacarex.animequiz", 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("AutoSignIn", false);
        }
    }

    // Call when the sign-in button is clicked
    public void signInClicked() {
        Log.e("SIGN IN", "CLICKED!");
        mSignInClicked = true;
        mGoogleApiClient.connect();
        //FragMainMenu fragMainMenu= (FragMainMenu) getSupportFragmentManager().findFragmentByTag(FragMainMenu.class.getName());
        //fragMainMenu.setShowSignIn(false);

        SharedPreferences settings = getSharedPreferences("com.yacarex.animequiz", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("AutoSignIn", true);
    }

    public void achievementClicked(View view) {
        startActivityForResult(Games.Achievements.getAchievementsIntent(mGoogleApiClient),
                REQUEST_ACHIEVEMENTS);
    }

    public void leaderboardsClicked(View view) {
        startActivityForResult(Games.Leaderboards.getLeaderboardIntent(mGoogleApiClient,
                getString(R.string.leaderboardPLVL)), REQUEST_LEADERBOARD);
    }

    public void manageAchievements(String type, int subtype, int value) {

        if (isSignedIn) {
            Log.e("Achievement", "Now Managing");
            if (type.equals(FinalStringsUtils.INCREMENT)) {
                switch (subtype) {
                    case FinalStringsUtils.FAILACHIEV:
                        Log.e("ACHIVEMENT", "INCREMENT FAIL");
                        Games.Achievements.increment(mGoogleApiClient, getString(R.string.fail100), value);
                        Games.Achievements.increment(mGoogleApiClient, getString(R.string.fail250), value);
                        Games.Achievements.increment(mGoogleApiClient, getString(R.string.fail500), value);
                        break;
                    case FinalStringsUtils.HINTACHIEV:
                        Log.e("ACHIEVEMENT", "INCREMENT HINTS");
                        Games.Achievements.increment(mGoogleApiClient, getString(R.string.hint100), value);
                        Games.Achievements.increment(mGoogleApiClient, getString(R.string.hint500), value);
                        break;
                    case FinalStringsUtils.PLAYACHIEV:
                        Log.e("ACHIEVEMENT", "INCREMENT PLAYS");
                        Games.Achievements.increment(mGoogleApiClient, getString(R.string.play10), value);
                        Games.Achievements.increment(mGoogleApiClient, getString(R.string.play100), value);
                        Games.Achievements.increment(mGoogleApiClient, getString(R.string.play500), value);
                        break;
                }
            } else if (type.equals(FinalStringsUtils.UNLOCK)) {
                int totalScore = Integer.parseInt(dbUtil.getTotalScore());
                Log.e("TOTAL SCORE", String.valueOf(totalScore));
                Games.Leaderboards.submitScore(mGoogleApiClient, getString(R.string.leaderboardPLVL), totalScore);
                if (totalScore >= 9000) {
                    Log.e("ACHIEVEMENT", "MORE THAN 9k");
                    Games.Achievements.unlock(mGoogleApiClient, getString(R.string.score9k));
                } else if (totalScore >= 30000) {
                    Log.e("ACHIEVEMENT", "MORE THAN 30k");
                    Games.Achievements.unlock(mGoogleApiClient, getString(R.string.score30k));
                } else if (totalScore >= 60000) {
                    Log.e("ACHIEVEMENT", "MORE THAN 60k");
                    Games.Achievements.unlock(mGoogleApiClient, getString(R.string.score60k));
                }
                Log.e("ACHIEVEMENT", "GAMER LVL SCORE:" + String.valueOf(dbUtil.getLevelScore(FinalStringsUtils.GAMER1LEVEL)) + "MAX SCORE" + String.valueOf(dbUtil.getLevelMaxScore(FinalStringsUtils.GAMER1LEVEL)));
                if (dbUtil.getLevelScore(FinalStringsUtils.GAMER1LEVEL) >= dbUtil.getLevelMaxScore(FinalStringsUtils.GAMER1LEVEL)) {
                    Log.e("ACHIEVEMENT", "GAMER LVL UNLOCKED");
                    Games.Achievements.unlock(mGoogleApiClient, getString(R.string.player1achievement));
                }
                Log.e("ACHIEVEMENT", "GAMER LVL 2 SCORE:" + String.valueOf(dbUtil.getLevelScore(FinalStringsUtils.GAMER2LEVEL)) + "MAX SCORE" + String.valueOf(dbUtil.getLevelMaxScore(FinalStringsUtils.GAMER2LEVEL)));
                if (dbUtil.getLevelScore(FinalStringsUtils.GAMER2LEVEL) >= dbUtil.getLevelMaxScore(FinalStringsUtils.GAMER2LEVEL)) {
                    Games.Achievements.unlock(mGoogleApiClient, getString(R.string.gamerachievement));
                }
                Log.e("ACHIEVEMENT", "PETS LVL SCORE:" + String.valueOf(dbUtil.getLevelScore(FinalStringsUtils.PETSLEVEL)) + "MAX SCORE" + String.valueOf(dbUtil.getLevelMaxScore(FinalStringsUtils.PETSLEVEL)));
                if (dbUtil.getLevelScore(FinalStringsUtils.PETSLEVEL) >= dbUtil.getLevelMaxScore(FinalStringsUtils.PETSLEVEL)) {
                    Games.Achievements.unlock(mGoogleApiClient, getString(R.string.petsachievement));
                }
                Log.e("ACHIEVEMENT", "MOVIES LVL SCORE:" + String.valueOf(dbUtil.getLevelScore(FinalStringsUtils.MOVIESLEVEL)) + "MAX SCORE" + String.valueOf(dbUtil.getLevelMaxScore(FinalStringsUtils.MOVIESLEVEL)));
                if (dbUtil.getLevelScore(FinalStringsUtils.MOVIESLEVEL) >= dbUtil.getLevelMaxScore(FinalStringsUtils.MOVIESLEVEL)) {
                    Games.Achievements.unlock(mGoogleApiClient, getString(R.string.moviesachievement));
                }
            }

        }
    }

    public boolean isSignedIn() {
        return isSignedIn;
    }

    public void setIsSignedIn(boolean isSigned) {
        isSignedIn = isSigned;
    }

    @Override
    public void onRewardedVideoAdLoaded() {
        Toast.makeText(this, "onRewardedVideoAdLoaded", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onRewardedVideoAdOpened() {
        Toast.makeText(this, "onRewardedVideoAdOpened", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onRewardedVideoStarted() {
        Toast.makeText(this, "onRewardedVideoStarted", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onRewardedVideoAdClosed() {
        Toast.makeText(this, "onRewardedVideoAdClosed", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onRewarded(RewardItem rewardItem) {
        Toast.makeText(this, "onRewarded!" , Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onRewardedVideoAdLeftApplication() {
        Toast.makeText(this, "onRewardedVideoAdLeftApplication",
                Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {
        Toast.makeText(this, "onRewardedVideoAdFailedToLoad", Toast.LENGTH_SHORT).show();

    }
}