package com.yakarex.animequiz.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.crashlytics.android.Crashlytics;

public class ScoreDbHelper extends SQLiteOpenHelper {

    private String sqlCreate = "CREATE TABLE Scoretable (id INTEGER, score INTEGER, level INTEGER)";
    private String sqlCreateAnsTable = "CREATE TABLE AnswerTable (id INTEGER, nameAns TEXT, animeAns TEXT)";

    private static String DB_NAME = "Scores";

    private static final int HINTCOUNT = 999999999;
    private static final int LOGCOUNT = 999999998;
    private static final int WRONGCOUNT = 999999997;

    private SQLiteDatabase myScoreDataBase;

    @SuppressWarnings("unused")
    private final Context myContext;

    public ScoreDbHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.myContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.rawQuery(sqlCreate, null);
        db.rawQuery(sqlCreateAnsTable, null);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }

    public void createDb() {
        boolean exist = checkDataBase();

        if (exist) {

        } else {
            this.getWritableDatabase();
            this.close();
        }
    }

    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            String path = myContext.getDatabasePath(DB_NAME).getPath();
            checkDB = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
        } catch (SQLiteException e) {
            //database doesn't exist yet
        }

        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null;
    }

    public void openDataBase() throws SQLException {
        //Open the database
        String mypath = myContext.getDatabasePath(DB_NAME).getPath();
        myScoreDataBase = SQLiteDatabase.openDatabase(mypath, null, SQLiteDatabase.OPEN_READWRITE);
        //myScoreDataBase.rawQuery("CREATE TABLE IF NOT EXISTS Scoretable (id INTEGER, score INTEGER)", null);
    }

    @Override
    public synchronized void close() {
        if (myScoreDataBase != null) {
            myScoreDataBase.close();
            super.close();
        }
    }

    public int getCharScore(int charid, int level) {

        myScoreDataBase.beginTransaction();
        try {
            myScoreDataBase.execSQL("CREATE TABLE IF NOT EXISTS Scoretable (id INTEGER, score INTEGER, level INTEGER)");
            myScoreDataBase.setTransactionSuccessful();
        } finally {
            myScoreDataBase.endTransaction();
        }

        String testSql = "select * from Scoretable where id= " + charid;

        Cursor testcursor = myScoreDataBase.rawQuery(testSql, null);
        //int testintvalue= testcursor.getCount();
        int quantity = testcursor.getCount();
        testcursor.close();
        if (quantity < 1) {

            String sql = "INSERT INTO Scoretable (id, score, level) VALUES (" + charid + ", 0," + level + ")";
            myScoreDataBase.beginTransaction();
            try {
                myScoreDataBase.execSQL(sql);
                myScoreDataBase.setTransactionSuccessful();
                return 0;
            } finally {
                myScoreDataBase.endTransaction();
            }
        } else {
            myScoreDataBase.beginTransaction();
            try {
                String sql = "SELECT score FROM Scoretable where id = " + String.valueOf(charid);

                //Log.d("Executed SQL", sql);
                //Log.d("Char ID", String.valueOf(charid));

                Cursor cursor = myScoreDataBase.rawQuery(sql, null);
                cursor.moveToFirst();
                int score = cursor.getInt(0);
                cursor.close();

                return score;
            } finally {
                myScoreDataBase.endTransaction();
            }
        }
    }

    public void setCharUserNameAnswer(int charID, String userAnswr) {

        String testSql = "select * from Scoretable where id= " + charID;
        String sql = "INSERT INTO Scoretable (id, nameAns) VALUES (" + charID + ", " + userAnswr + ")";
        String updateSql = "UPDATE Scoretable SET nameAns = " + userAnswr + " WHERE id = " + charID;

        Cursor testCursor = myScoreDataBase.rawQuery(testSql, null);
        int quantity = testCursor.getCount();
        testCursor.close();

        if (quantity < 1) {

            myScoreDataBase.beginTransaction();
            try {
                myScoreDataBase.execSQL(sql);
                myScoreDataBase.setTransactionSuccessful();
            } finally {
                myScoreDataBase.endTransaction();
            }
        } else {
            myScoreDataBase.beginTransaction();
            try {
                myScoreDataBase.execSQL(updateSql);
                myScoreDataBase.setTransactionSuccessful();
            } finally {
                myScoreDataBase.endTransaction();
            }
        }

    }

    public void setCharUserAnimeAnswer(int charID, String userAnswr) {

        String testSql = "select * from Scoretable where id= " + charID;
        String sql = "INSERT INTO Scoretable (id, animeAns) VALUES (" + charID + ", " + userAnswr + ")";
        String updateSql = "UPDATE Scoretable SET animeAns = " + userAnswr + " WHERE id = " + charID;

        Cursor testCursor = myScoreDataBase.rawQuery(testSql, null);
        int quantity = testCursor.getCount();
        testCursor.close();

        if (quantity < 1) {

            myScoreDataBase.beginTransaction();
            try {
                myScoreDataBase.execSQL(sql);
                myScoreDataBase.setTransactionSuccessful();
            } finally {
                myScoreDataBase.endTransaction();
            }
        } else {
            myScoreDataBase.beginTransaction();
            try {
                myScoreDataBase.execSQL(updateSql);
                myScoreDataBase.setTransactionSuccessful();
            } finally {
                myScoreDataBase.endTransaction();
            }
        }

    }

    public void setCharScore(int charId, int score, int level) {

        String updateSql = "UPDATE Scoretable SET score = score+" + score + " WHERE id = " + charId;

        String testSql = "select * from Scoretable where id= " + charId;

        Cursor testcursor = myScoreDataBase.rawQuery(testSql, null);
        int quantity = testcursor.getCount();
        testcursor.close();
        if (quantity < 1) {

            String sql = "INSERT INTO Scoretable (id, score, level) VALUES (" + charId + ", " + score + ", " + level + ")";
            myScoreDataBase.beginTransaction();
            try {
                myScoreDataBase.execSQL(sql);
                myScoreDataBase.setTransactionSuccessful();
            } finally {
                myScoreDataBase.endTransaction();
            }
        } else {
            myScoreDataBase.beginTransaction();
            try {
                myScoreDataBase.execSQL(updateSql);
                myScoreDataBase.setTransactionSuccessful();
            } finally {
                myScoreDataBase.endTransaction();
            }
        }
    }

    public int getLevelScore(int lvl) {
        myScoreDataBase.beginTransaction();
        try {
            myScoreDataBase.execSQL("CREATE TABLE IF NOT EXISTS Scoretable (id INTEGER, score INTEGER, level INTEGER)");
            myScoreDataBase.setTransactionSuccessful();

        } finally {
            myScoreDataBase.endTransaction();
        }

        String scoregettersql = "SELECT * FROM Scoretable WHERE level = " + String.valueOf(lvl);
        Cursor lvlcursor = myScoreDataBase.rawQuery(scoregettersql, null);

        int lvlscore = 0;


        for (int x = 0; lvlcursor.moveToPosition(x) == true; x++) {
            lvlcursor.moveToPosition(x);

            lvlscore = lvlscore + lvlcursor.getInt(1);
        }
        lvlcursor.close();
        return lvlscore;
    }

    public int getTotalScore() {
        myScoreDataBase.beginTransaction();
        try {
            myScoreDataBase.execSQL("CREATE TABLE IF NOT EXISTS Scoretable (id INTEGER, score INTEGER, level INTEGER)");
            myScoreDataBase.setTransactionSuccessful();

        } finally {
            myScoreDataBase.endTransaction();
        }
        Cursor totalcursor = myScoreDataBase.rawQuery("SELECT * FROM Scoretable", null);

        int hints = getCharScore(HINTCOUNT, 0);
        int logs = getCharScore(LOGCOUNT, 0);
        int wrongs = getCharScore(WRONGCOUNT, 0);

        int totalscore = 0;
        for (int x = 0; totalcursor.moveToPosition(x) == true; x++) {
            totalcursor.moveToPosition(x);

            totalscore = totalscore + totalcursor.getInt(1);
        }
        totalcursor.close();
        totalscore = totalscore - hints - logs - wrongs;
        return totalscore;
    }

    public boolean isTableExists(String tableName, boolean openDb) {
        if (openDb) {
            if (myScoreDataBase == null || !myScoreDataBase.isOpen()) {
                myScoreDataBase = getReadableDatabase();
            }

            if (!myScoreDataBase.isReadOnly()) {
                myScoreDataBase.close();
                myScoreDataBase = getReadableDatabase();
            }
        }

        Cursor cursor = myScoreDataBase.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" + tableName + "'", null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }

    public void deleteScoretable() {
        if (isTableExists("Scoretable", true)) {
            myScoreDataBase.execSQL("DROP TABLE Scoretable");
        }
    }

    public void deleteAnswertable() {
        myScoreDataBase.execSQL("DROP TABLE AnswerTable");
    }

    public void resethintcount() {
        myScoreDataBase.execSQL("DELETE FROM Scoretable WHERE id = 999999999");
    }

    public int[] starsStats() {
        myScoreDataBase.beginTransaction();
        try {
            myScoreDataBase.execSQL("CREATE TABLE IF NOT EXISTS Scoretable (id INTEGER, score INTEGER, level INTEGER)");
            myScoreDataBase.setTransactionSuccessful();

        } finally {
            myScoreDataBase.endTransaction();
        }


        Cursor gold = myScoreDataBase.rawQuery("select * from Scoretable where score = 100", null);
        Cursor silver = myScoreDataBase.rawQuery("select * from scoretable where score between 40 and 99", null);
        Cursor bronze = myScoreDataBase.rawQuery("select * from scoretable where score between 30 and 35", null);

        int goldcount = 0;
        int silvercount = 0;
        int coopercount = 0;

        goldcount = gold.getCount();
        silvercount = silver.getCount();
        coopercount = bronze.getCount();

        int[] starsArray = new int[3];
        starsArray[0] = goldcount;
        starsArray[1] = silvercount;
        starsArray[2] = coopercount;

        gold.close();
        silver.close();
        bronze.close();
        return starsArray;
    }

    public int[] starsStats(int lvl) {
        int lvlnum = lvl;
        myScoreDataBase.beginTransaction();
        try {
            myScoreDataBase.execSQL("CREATE TABLE IF NOT EXISTS Scoretable (id INTEGER, score INTEGER, level INTEGER)");
            myScoreDataBase.setTransactionSuccessful();

        } finally {
            myScoreDataBase.endTransaction();
        }

        String goldsql = "select * from Scoretable where score = 100 and level =" + String.valueOf(lvlnum);
        String silversql = "select * from scoretable where score between 40 and 99 and level =" + String.valueOf(lvlnum);
        String bronzesql = "select * from scoretable where score between 30 and 35 and level =" + String.valueOf(lvlnum);


        Cursor gold = myScoreDataBase.rawQuery(goldsql, null);
        Cursor silver = myScoreDataBase.rawQuery(silversql, null);
        Cursor bronze = myScoreDataBase.rawQuery(bronzesql, null);

        int goldcount = 0;
        int silvercount = 0;
        int coopercount = 0;

        goldcount = gold.getCount();
        silvercount = silver.getCount();
        coopercount = bronze.getCount();

        int[] starsArray = new int[3];
        starsArray[0] = goldcount;
        starsArray[1] = silvercount;
        starsArray[2] = coopercount;

        gold.close();
        silver.close();
        bronze.close();
        return starsArray;
    }

    public void setInputedAnime(int id, String anime) {

        String sql = "INSERT OR REPLACE INTO Answerstable (id, animeanswer) VALUES (" + id + ", '" + anime + "')";

        myScoreDataBase.beginTransaction();
        try {
            myScoreDataBase.execSQL("CREATE TABLE IF NOT EXISTS Answerstable (id INTEGER, animeanswer TEXT, nameanswer TEXT)");

            myScoreDataBase.execSQL(sql);

            myScoreDataBase.setTransactionSuccessful();

        } finally {
            myScoreDataBase.endTransaction();
        }

    }

    public String getInputedAnime(int id) {

        String sql = "SELECT animeanswer FROM Answerstable WHERE id = " + String.valueOf(id);
        Cursor cursor;

        myScoreDataBase.beginTransaction();
        try {
            myScoreDataBase.execSQL("CREATE TABLE IF NOT EXISTS Answerstable (id INTEGER, animeanswer TEXT, nameanswer TEXT)");

            cursor = myScoreDataBase.rawQuery(sql, null);

            myScoreDataBase.setTransactionSuccessful();

        } finally {
            myScoreDataBase.endTransaction();
        }


        cursor.moveToFirst();

        String inputedAnime;
        try {
            inputedAnime = cursor.getString(0);

        } catch (CursorIndexOutOfBoundsException cioobe) {
            inputedAnime = "";
            cioobe.printStackTrace();
            Crashlytics.logException(cioobe);
        } finally {
            cursor.close();
        }

        return inputedAnime;
    }

    public void setInputedName(int id, String name) {

        String sql = "INSERT INTO Answerstable (id, nameanswer) VALUES (" + id + ", '" + name + "')";
        String testSql = "SELECT nameanswer FROM Answerstable WHERE id = " + String.valueOf(id);
        String replaceSQL = "UPDATE Answerstable SET nameanswer= '" + name + "' WHERE id = " + id;

        myScoreDataBase.beginTransaction();
        try {
            myScoreDataBase.execSQL("CREATE TABLE IF NOT EXISTS Answerstable (id INTEGER, animeanswer TEXT, nameanswer TEXT)");

            if (myScoreDataBase.rawQuery(testSql, null).getCount() > 0) {
                myScoreDataBase.execSQL(replaceSQL);
            } else {
                myScoreDataBase.execSQL(sql);
            }

            myScoreDataBase.setTransactionSuccessful();

        } finally {
            myScoreDataBase.endTransaction();
        }
    }

    public String getInputedName(int id) {

        String sql = "SELECT nameanswer FROM Answerstable WHERE id = " + String.valueOf(id);

        Cursor cursor;

        myScoreDataBase.beginTransaction();
        try {
            myScoreDataBase.execSQL("CREATE TABLE IF NOT EXISTS Answerstable (id INTEGER, animeanswer TEXT, nameanswer TEXT)");

            cursor = myScoreDataBase.rawQuery(sql, null);

            myScoreDataBase.setTransactionSuccessful();

        } finally {
            myScoreDataBase.endTransaction();
        }


        cursor.moveToFirst();

        String inputedName;
        try {
            inputedName = cursor.getString(0);
        } catch (CursorIndexOutOfBoundsException cioobe) {
            inputedName = "";
            cioobe.printStackTrace();
            Crashlytics.logException(cioobe);
        } finally {
            cursor.close();
        }

        return inputedName;
    }


    public boolean testAnswTableHasValues(int id) {

        String testSql = "select * from Answerstable where id= " + id;

        Cursor testcursor = myScoreDataBase.rawQuery(testSql, null);
        //int testintvalue= testcursor.getCount();
        int quantity = testcursor.getCount();
        testcursor.close();
        if (quantity < 1) {

            return false;
        } else {

            return true;
        }

    }
}