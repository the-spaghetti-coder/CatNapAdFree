package com.cedideas.catnap;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper{

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "db";
	private static final String TABLE_USERS = "users";
	private static final String KEY_ID = "_id";
	private static final String COLUMN_FIRSTNAME = "firstname";
	private static final String COLUMN_LASTNAME = "lastname";
	private static final String TABLE_EVENTLOG = "eventlog2";
	
	private static final String COLUMN_TASKNAME = "taskname";
	private static final String COLUMN_DURATION = "duration";
	private static final String COLUMN_NAME = "name";
	private static final String COLUMN_YEAR = "year";
	private static final String COLUMN_MONTH = "month";
	private static final String COLUMN_DAY = "day";
	private static final String TABLE_ALARMEVENTS_CREATE = "create table if not exists users('_id' integer primary key autoincrement, timestamp integer, firstname text);";
	private static final String TABLE_EVENTLOG_CREATE = "create table if not exists eventlog2('_id' integer primary key autoincrement, taskname text, duration real, name text, year text, month text," +
			"day text);"; //duration is int because i want to add up the values over time
	private static final String TABLE_USERS_PERSONALEVENTS_CREATE = "create table if not exists usersevents('_id' integer primary key autoincrement, taskname text);"; 
				//constructor	
	
	
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

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
