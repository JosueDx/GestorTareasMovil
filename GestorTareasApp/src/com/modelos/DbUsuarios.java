package com.modelos;



import com.modelo.informacion.Datosusuarios;
import com.modelo.informacion.Usuarios;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DbUsuarios {

	public static final String DB_NAME = "DB_Tareas";
	public static final String TABLA_NAME_datosusuarios = "datosusuarios";
	public static final String TABLA_NAME_usuarios = "usuarios";
	public static final String TABLA_NAME_tipousuarios = "tipousuarios";
	
public Datosusuarios Listalogin(Context contexto, String usuario, String contrasenia ){
		
	Datosusuarios listario =null;
	Usuarios usu = null;
		// COnexion a la BD
		TareasSqLite tarjetaDB =new TareasSqLite(contexto,DB_NAME,null,1);
		// Referencia a la BD
		SQLiteDatabase  db = tarjetaDB.getReadableDatabase();
		
		// Consulta sobre la bd
		//Cursor cursor = db.query(TABLA_NAME, new String[]{"tar_titular","tar_nombre","tar_numero"}, 
		//null,null,null,null,"tar_nombre");
		// si es que existe al menos un resultado
		String[] parametrosDeBusqueda = new String[]{usuario,contrasenia};
		
		String sql = "SELECT user.id_u, user.id_usuario,user.usuario,user.clave, u.id_tipousuario " +
					 "FROM "+TABLA_NAME_datosusuarios+" as user, "+TABLA_NAME_usuarios+" as u "  +
				     "WHERE user.usuario = ? and user.clave = ? and u.id_usu = user.id_usuario ";
				      Cursor cursor = db.rawQuery(sql, parametrosDeBusqueda);	
			
				      if(cursor.moveToFirst()){
			// Recorrer los resultados
			do{
				listario = new Datosusuarios();
				usu = new Usuarios();
				listario.setId_datosusuarios(cursor.getInt(0));
				listario.setId_usuario(cursor.getInt(1));
				listario.setUsuario(cursor.getString(2));
				listario.setClave(cursor.getString(3));
			    listario.setEstado("A");
			    
			    usu.setId_tipousuario(cursor.getInt(4));
			    listario.setUsuarios(usu);
			   
			    
			}while(cursor.moveToNext());
		}
		db.close();
		return listario;
		
	}
	
	
	
	
	
}
