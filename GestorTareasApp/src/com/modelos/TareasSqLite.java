package com.modelos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class TareasSqLite extends SQLiteOpenHelper {

	String sql1 = "CREATE TABLE alarma (" +
				" id INTEGER ," +
				" hora TEXT NOT NULL)";

	public TareasSqLite(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}
	
@Override
 	
 	//se ejecuta al abrir la base de datos
 	public void onOpen(SQLiteDatabase db) {
 	    super.onOpen(db);
 	        db.execSQL("PRAGMA foreign_keys=ON;");
 	}
	@Override
	public void onCreate(SQLiteDatabase sqldb) {
		// TODO Auto-generated method stub
		// Enable foreign key constraints			
				sqldb.execSQL(sql1);			
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

}
