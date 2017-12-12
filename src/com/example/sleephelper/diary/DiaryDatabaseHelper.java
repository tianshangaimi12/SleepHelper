package com.example.sleephelper.diary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DiaryDatabaseHelper extends SQLiteOpenHelper{
	
	private final String CREATE_TABLE = "create table diary("
			+"id integer primary key autoincrement," +
			"date text," +
			"title text," +
			"content text)";

	public DiaryDatabaseHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

}
