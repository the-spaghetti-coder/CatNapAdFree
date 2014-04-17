package com.cedideas.catnap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class DBHelper extends SQLiteOpenHelper{

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "db";
	private static final String TABLE_USERS = "users";
	private static final String KEY_ID = "_id";
	private static final String COLUMN_ALARMSTART = "alarmstart";
	private static final String COLUMN_ALARMEND = "alarmend";
	private static final String COLUMN_ALARMNAME = "alarmname";
	private static final String COLUMN_LASTNAME = "lastname";
	
	
	private static final String COLUMN_TASKNAME = "taskname";
	private static final String COLUMN_DURATION = "duration";
	private static final String COLUMN_NAME = "name";
	private static final String COLUMN_YEAR = "year";
	private static final String COLUMN_MONTH = "month";
	private static final String COLUMN_DAY = "day";
	private static final String TABLE_ALARMEVENTS_CREATE = "create table if not exists alarmevents('_id' integer primary key autoincrement, alarmstart text, alarmend text, alarmname text);";
	private static final String TABLE_ALARMEVENTS = "alarmevents";
	
	
	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

	@Override
	public void onCreate(SQLiteDatabase db) {
		try {
			db.execSQL(TABLE_ALARMEVENTS_CREATE);

		} catch (SQLiteException e){
			Log.e("createFAIL", e.toString(), e);
		}
		
	}

	public void insertEntry(String alarmstart, String alarmend, String alarmname){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(COLUMN_ALARMSTART, alarmstart);
		values.put(COLUMN_ALARMEND, alarmend);
		values.put(COLUMN_ALARMNAME, alarmname);
		db.insert(TABLE_ALARMEVENTS, COLUMN_ALARMSTART, values);
		
		try { 
			Cursor c = db.rawQuery("SELECT * FROM alarmevents;", null);
			if (c!=null) {
				c.moveToLast();
				int cursorCount = c.getCount();
				String strCursorCount = String.valueOf(cursorCount);
				System.out.println("Cursor count: " + strCursorCount);
				String strAlarmStart = c.getString(1).toString();
				String strAlarmEnd = c.getString(2).toString();
				String strAlarmName = c.getString(3).toString();
				System.out.println("Last entry\n=====\n " + "alarm start : " + strAlarmStart + " alarm end: " + strAlarmEnd + " alarm name: " + strAlarmName);

			} else {
				Context context = null;
				Toast.makeText(context, "fail", Toast.LENGTH_LONG).show();
			}

		} catch (SQLiteException e ) {
			Log.e("addUSERfailed", e.toString(), e);
		}
		db.close();
		
	}
	
	public void deleteEntry(){
		SQLiteDatabase db = this.getWritableDatabase();
//		db.delete(TABLE_USERS, COLUMN_FIRSTNAME + "= ?", new String[]{username});
//		System.out.println("User deleted: " + username);
//		db.close();
	}
	
	public void updateEntry(){
		
	}
	
	public String getLastEntry(){
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = db.rawQuery("select * from alarmevents;", null);
		String lastEntry = null;
		try {
			if (c!=null){
				c.moveToLast();
				String strAlarmStart = c.getString(1).toString();
				String strAlarmEnd = c.getString(2).toString();
				String strAlarmName = c.getString(3).toString();
				lastEntry = "Alarm starts at " + strAlarmStart + " and rings at " + strAlarmEnd + "\n" + strAlarmName;
			}
		} catch (SQLiteException e ){
			Log.e("getlastentryFAILED", e.toString(), e);
		}
		return lastEntry;

	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
