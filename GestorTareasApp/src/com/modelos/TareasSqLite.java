package com.modelos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class TareasSqLite extends SQLiteOpenHelper {

	//Sentencia SQL para crear la tabla de Bancos
	
		String sql1 = "CREATE TABLE datosusuarios (" +
				" id_u INTEGER PRIMARY KEY AUTOINCREMENT," +
				" id_usuario INTEGER NOT NULL," +
				" usuario TEXT," +
				" clave TEXT," +
				"FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usu))";
		
		String sql2 = "CREATE TABLE usuarios (" +
				" id_usu INTEGER PRIMARY KEY AUTOINCREMENT," +
				" id_tipousuario INTEGER ," +
				"FOREIGN KEY (id_tipousuario) REFERENCES tipousuarios(id_tipusu))";
	
		String sql3 = "CREATE TABLE tipousuarios (" +
				" id_tipusu INTEGER PRIMARY KEY AUTOINCREMENT," +
				" descripcion TEXT)";
	
	
	public TareasSqLite(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	
@Override
 	
 	//se ejecuta al abrir la base de datos
 	public void onOpen(SQLiteDatabase db) {
 	    super.onOpen(db);
 	   // if (!db.isReadOnly()) {
 	        // Enable foreign key constraints
 	        db.execSQL("PRAGMA foreign_keys=ON;");
 	   // }
 	}
	@Override
	public void onCreate(SQLiteDatabase sqldb) {
		// TODO Auto-generated method stub
		// Enable foreign key constraints
					
				sqldb.execSQL(sql1);
				sqldb.execSQL(sql2);		
				sqldb.execSQL(sql3);
				
				sqldb.execSQL("CREATE TRIGGER fk_tipousuario_usuario " +
					    " BEFORE INSERT ON usuarios FOR EACH ROW BEGIN"+
					    " SELECT CASE WHEN ((SELECT id_usu FROM usuarios "+ 
					    "WHERE id_usu=new.id_tipousuario) IS NULL)"+
					    " THEN RAISE (ABORT,'Foreign Key Violation fk_tipousuario_usuario') END;"+
					    "  END;");
				
				sqldb.execSQL("CREATE TRIGGER fk_usuario_datosusuarios " +
					    " BEFORE INSERT ON datosusuarios FOR EACH ROW BEGIN"+
					    " SELECT CASE WHEN ((SELECT id_tipusu FROM tipousuarios "+ 
					    "WHERE id_tipusu=new.id_usuario) IS NULL)"+
					    " THEN RAISE (ABORT,'Foreign Key Violation fk_usuario_datosusuarios') END;"+
					    "  END;");
				
				sqldb.execSQL("INSERT INTO tipousuarios VALUES(NULL,'Jefe')");
				sqldb.execSQL("INSERT INTO tipousuarios VALUES(NULL,'Empleado')");
				
				sqldb.execSQL("INSERT INTO usuarios VALUES(NULL,1)");
				sqldb.execSQL("INSERT INTO usuarios VALUES(NULL,2)");
				
				sqldb.execSQL("INSERT INTO datosusuarios VALUES(NULL,1,'josue','1234')");
				sqldb.execSQL("INSERT INTO datosusuarios VALUES(NULL,2,'karen','1111')");
				
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

}
