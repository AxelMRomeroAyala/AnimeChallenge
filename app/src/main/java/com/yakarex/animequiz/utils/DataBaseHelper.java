package com.yakarex.animequiz.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

@SuppressLint("SdCardPath")
public class DataBaseHelper extends SQLiteOpenHelper{
	
	private static String DB_PATH = "/data/data/com.yakarex.animequiz/databases/";
	private static String DB_NAME = "CharactersDBv4.ydb";
	private SQLiteDatabase myDataBase;
	private final Context myContext;
	
	 /**
	  * Constructor
	  * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
	  * @param context
	  */
	
	public DataBaseHelper(Context context){
		super(context, DB_NAME, null, 1);
		this.myContext= context;
	}
	
	 /**
	  * Creates a empty database on the system and rewrites it with your own database.
	  * */
	public void createDatabase() throws IOException{
		
		boolean dbExist = checkDataBase();
		if(dbExist){
			//do nothing, database already exist (Actually Depreciated by Axel por que necesito qeu siempre sobreescriba por las dudas)
			this.getReadableDatabase();
			this.close();
			try{
				this.close();
				copyDataBase();
			}
			catch (IOException e){
				throw new Error("error copying database");
			}
		}
		else{ //By calling this method and empty database will be created into the default system path
			//of your application so we are gonna be able to overwrite that database with our database
			this.getReadableDatabase();
			this.close();
			try{
				this.close();
				copyDataBase();
			}
			catch (IOException e){
				throw new Error("error copying database");
			}
		}
	}
	
	 /**
	  * Check if the database already exist to avoid re-copying the file each time you open the application.
	  * @return true if it exists, false if it doesn't
	  */
	private boolean checkDataBase(){
		SQLiteDatabase checkDB=null;
		
		try{
			String path =DB_PATH+DB_NAME;
			checkDB= SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
		}
		catch (SQLiteException e){
		//database doesn't exist yet
		}
		
		if (checkDB != null){
			checkDB.close();
		}
		return checkDB != null;
	}
	
	 /**
	  * Copies your database from your local assets-folder to the just created empty database in the
	  * system folder, from where it can be accessed and handled.
	  * This is done by transfering bytestream.
	  * */
	 private void copyDataBase() throws IOException{
		 
		//Open your local db as the input stream
		InputStream myInput = myContext.getAssets().open(DB_NAME);
		 
		// Path to the just created empty db
		String outFileName = DB_PATH + DB_NAME;
		 
		//Open the empty db as the output stream
		OutputStream myOutput = new FileOutputStream(outFileName);
		 
		//transfer bytes from the inputfile to the outputfile
		byte[] buffer = new byte[(86016)];
		int length;
		while ((length = myInput.read(buffer))>0){
		myOutput.write(buffer, 0, length);
		}
		 
		//Close the streams
		myOutput.flush();
		myOutput.close();
		myInput.close();
		 
		}
	
	 public void openDataBase() throws SQLException{
		 //Open the database
		 String mypath = DB_PATH + DB_NAME;
		 myDataBase = SQLiteDatabase.openDatabase(mypath, null, SQLiteDatabase.OPEN_READWRITE);
		 
	 }
	 
	 @Override
	 public synchronized void close(){
		 if(myDataBase != null){
			 myDataBase.close();
			 super.close();
		 }
	 }
	 
		@Override
		public void onCreate(SQLiteDatabase db) {
		 
		}
		 
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		 
		}
		
	
		
	public Cursor getLvl(int lvl){
		String query= "SELECT * FROM characters WHERE level = " + lvl;
		
		myDataBase.beginTransaction();
		Cursor lvlCursor= myDataBase.rawQuery(query, null);
		myDataBase.setTransactionSuccessful();
		myDataBase.endTransaction();
		
		return lvlCursor;
		
	}
	
	public Cursor getChallengeLvl(String lvl){
		String query= "SELECT * FROM challenge WHERE anime = '" + lvl+ "'";
		
		myDataBase.beginTransaction();
		Cursor challengeLvlCursor = myDataBase.rawQuery(query, null);
		myDataBase.setTransactionSuccessful();
		myDataBase.endTransaction();
		
		return challengeLvlCursor;
		
	}
	
	public int getLvlmaxScore(int lvl){
		String query= "SELECT * FROM characters WHERE level = " + lvl;
		myDataBase.beginTransaction();
		Cursor lvlcursor= myDataBase.rawQuery(query, null);
		myDataBase.setTransactionSuccessful();
		myDataBase.endTransaction();
		
		int maxscore= lvlcursor.getCount()*100;
		lvlcursor.close();

		return maxscore;
	}
	
	

}
