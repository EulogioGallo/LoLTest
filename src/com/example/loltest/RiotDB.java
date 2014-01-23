package com.example.loltest;

import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class RiotDB extends SQLiteOpenHelper {
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "RiotDB.db";
	private Context context;
	
	public RiotDB(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		//SQL statement to create all the tables
		String CREATE_TABLE_MAP_NAMES = "CREATE TABLE map_names ( " +
				"mapId INTEGER PRIMARY KEY, " +
				"name TEXT, " +
				"notes TEXT)";
		String CREATE_TABLE_CHAMPIONS = "CREATE TABLE champions ( " +
				"id INTEGER PRIMARY KEY, " +
				"name TEXT, " +
				"defenseRank INTEGER, " +
				"attackRank INTEGER, " +
				"difficultyRank INTEGER, " +
				"magicRank INTEGER)";
		
		db.execSQL(CREATE_TABLE_MAP_NAMES);
		db.execSQL(CREATE_TABLE_CHAMPIONS);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//Drop older tables if they exist
		db.execSQL("DROP TABLE IF EXISTS map_names");
		
		//create fresh tables
		this.onCreate(db);
	}
	
	//CRUD operations
	public void addRow(RIOT_CLASS row) {
		//row is a JSON formatted string
		try {
			JSONObject jRow = new JSONObject(row.toString());
			String tableName = row.tableName();
		
			Log.d("addRow", jRow.toString());
			
			//get sqlite db reference
			SQLiteDatabase db = this.getWritableDatabase();
			
			//create ContentValues
			ContentValues values = new ContentValues();
			
			Iterator<?> keys = jRow.getJSONObject(tableName).keys();
			while(keys.hasNext()) {
				String key = (String)keys.next();
				values.put(key, jRow.getJSONObject(tableName).getString(key));
				Log.d("addRow_"+key, key);
				Log.d("addRow_"+key, jRow.getJSONObject(tableName).getString(key));
			}
			
			//insert
			db.insert(tableName, null, values);
			
			//close
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	public String getRow(String tableName, String[] tableColumns) {
		try {
			//get sqlite db reference
			SQLiteDatabase db = this.getReadableDatabase();
			
			//build query
			Cursor cursor = 
					db.query(tableName,
							tableColumns,
							null,
							null,
							null,
							null,
							null,
							null);
			
			if (cursor != null)
				cursor.moveToFirst();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "row added";
	}
	
	/* Functions for loading League of Legends constants */
	
	//Loads current champions
	public void loadChamps() {
		//Create async request
		String url = context.getString(R.string.get_url) + context.getString(R.string.get_url_champion)
				+ "?api_key=" + context.getString(R.string.api_key);
		
		//call asynctask
		AsyncTaskSummoner ats = new AsyncTaskSummoner();
		String result = "";
		try {
			result = ats.execute(url).get();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//go through returned champs and add them to db
		try {
			JSONObject jResult = new JSONObject(result);
			
			JSONArray champs = jResult.getJSONArray("champions");
			for(int i = 0; i < champs.length(); i++) {
				String champString = champs.getJSONObject(i).toString();
				Log.d("JSON Iterate", champString);
				addRow(new Champion(champs.getJSONObject(i)));
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}