package com.cedideas.catnap;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class DBHelper extends SQLiteOpenHelper{

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "db";
	
	private static final String KEY_ID = "_id";
	private static final String COLUMN_ALARMSTART = "alarmstart";
	private static final String COLUMN_ALARMEND = "alarmend";
	private static final String COLUMN_ALARMNAME = "alarmname";
	private static final String COLUMN_CURRENTDATE = "currentdate";
	private static final String COLUMN_ALARMACTIVEORNOT = "alarmactiveornot";
	
	private static final String TABLE_ALARMEVENTS_CREATE = "create table if not exists alarmevents('_id' integer primary key autoincrement, alarmstart text, alarmend text, alarmname text, currentdate text, alarmactiveornot integer);";
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

	public void insertEntry(String alarmstart, String alarmend, String alarmname, String currentdate, int alarmactiveornot){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(COLUMN_ALARMSTART, alarmstart);
		values.put(COLUMN_ALARMEND, alarmend);
		values.put(COLUMN_ALARMNAME, alarmname);
		values.put(COLUMN_CURRENTDATE, currentdate);
		values.put(COLUMN_ALARMACTIVEORNOT, alarmactiveornot);
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
				String strCurrentDate = c.getString(4).toString();
				int alarmActive = c.getInt(5);
				String strAlarmActive = String.valueOf(alarmActive);
				System.out.println("Last entry\n=====\n " + 
					"alarm start : " + strAlarmStart + " alarm end: " + strAlarmEnd + " alarm name: " + strAlarmName + 
					" current date: " + strCurrentDate + " alarmActive: " + strAlarmActive);

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
	
	public int getLastEntryId(){
		int lastEntryId = 0;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = db.rawQuery("select * from alarmevents;", null);
		int cursorCount = c.getCount();
		try {
			if (cursorCount!=0){
				c.moveToLast();
				int lastRowId  = c.getInt(0);
				String strLastRowId = String.valueOf(lastRowId);
				System.out.println("Last row id: " + strLastRowId);
			}
		} catch (SQLiteException e ){
			Log.e("getlastentryFAILED", e.toString(), e);
		}
		db.close();
		return lastEntryId;
	}
	
	public List<String> getFullList(){
		List<String> fullList = new ArrayList<String>();
		SQLiteDatabase db = this.getWritableDatabase();
		
		return fullList;
		
	}
	
	public List<String> getActiveAlarmList() {
		List<String> activeList = new ArrayList<String>();
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = db.rawQuery("select * from alarmevents where alarmactiveornot = 1;", null);
		int cursorCount = c.getCount();
		
//		activeList.add("hello");
		c.moveToFirst();
//		int rowId = c.getInt(0);
//		String strRowId = String.valueOf(rowId);
//		String strAlarmStart = c.getString(1).toString();
//		String strAlarmEnd = c.getString(2).toString();
//		String strAlarmName = c.getString(3).toString();
//		String strCurrentDate = c.getString(4).toString();
//		int alarmActive = c.getInt(5);
		try {
			if (cursorCount!=0){
				for(int i=0;i<cursorCount;i++){
					int rowId = c.getInt(0);
					String strRowId = String.valueOf(rowId);
					String strAlarmStart = c.getString(1).toString();
					String strAlarmEnd = c.getString(2).toString();
					String strAlarmName = c.getString(3).toString();
					String strCurrentDate = c.getString(4).toString();
					int alarmActive = c.getInt(5);
					String strAlarmActive = String.valueOf(alarmActive);
					String completeEntry = strRowId + " - Alarm started at " + strAlarmStart + ", ends at " + strAlarmEnd + ". \n" + strAlarmName + " " + strCurrentDate;
					c.moveToNext();
					activeList.add(completeEntry);
					System.out.println(completeEntry);
				}
			} else {System.out.println("shit is fucked up");}
		} catch (SQLiteException e ){
			Log.e("getlastentryFAILED", e.toString(), e);
		}
		c.close();
		db.close();
		return activeList;
	}
	
	public void findAlarmMillisRequestCode() {
		
	}
	
	public void updateSpecificAlarmStatus(int rowId, int alarmActiveStatus) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(COLUMN_ALARMACTIVEORNOT, alarmActiveStatus);
		db.update(TABLE_ALARMEVENTS, values, KEY_ID + " = " + rowId, null);
		Cursor c = db.rawQuery("select * from " + TABLE_ALARMEVENTS + " where " + KEY_ID + " = " + rowId + ";", null);
		c.moveToFirst();
		int rowIdTwo = c.getInt(0);
		String strRowId = String.valueOf(rowIdTwo);
		String strAlarmStart = c.getString(1).toString();
		String strAlarmEnd = c.getString(2).toString();
		String strAlarmName = c.getString(3).toString();
		String strCurrentDate = c.getString(4).toString();
		int alarmActive = c.getInt(5);
		String strAlarmActive = String.valueOf(alarmActive);
		String completeEntry = strRowId + " - Alarm started at " + strAlarmStart + ", ends at " + strAlarmEnd + ". \n" + strAlarmName + " " + strCurrentDate + " alarm status: " + strAlarmActive;
		System.out.println(completeEntry);
		c.close();
		db.close();
	}
	
	public void updateLastAlarmStatus(int rowId, int alarmActiveStatus){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		Cursor c = db.rawQuery("select * from " + TABLE_ALARMEVENTS + ";", null);
		c.moveToLast();
		db.update(TABLE_ALARMEVENTS, values, KEY_ID + " = " + rowId, null);
		c.close();
		db.close();
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
