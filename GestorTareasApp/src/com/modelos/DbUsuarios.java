package com.modelos;



import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.modelo.informacion.Datosusuarios;
import com.modelo.informacion.Tarea;
import com.modelo.informacion.Usuarios;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DbUsuarios {

	public static final String DB_NAME = "DB_Tareas";
	public static final String TABLA_NAME_datosusuarios = "datosusuarios";
	public static final String TABLA_NAME_usuarios = "usuarios";
	public static final String TABLA_NAME_tipousuarios = "tipousuarios";
	
	private String Sql = "[{'id_tarea':1, 'descripcion':'Descripcion1', 'comentario':'Comentario1', 'nivel_tarea':'bajo'},"
			+ "{'id_tarea':2, 'descripcion':'Descripcion2', 'comentario':'Comentario1', 'nivel_tarea':'medio'},"
			+ "{'id_tarea':3, 'descripcion':'Descripcion3', 'comentario':'Comentario1', 'nivel_tarea':'alto'},"
			+ "{'id_tarea':4, 'descripcion':'Descripcion4', 'comentario':'Comentario1', 'nivel_tarea':'medio'},"
			+ "{'id_tarea':5, 'descripcion':'Descripcion5', 'comentario':'Comentario1', 'nivel_tarea':'alto'}]";
	
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
	
public ArrayList<Tarea> listartexto(){
	ArrayList<Tarea> listaTarea = null;
	//GastosSqlLite gastosbd = new GastosSqlLite(contexto, DB_NAME, null, 1);
	//SQLiteDatabase db = gastosbd.getWritableDatabase();
	
	listaTarea = new ArrayList<Tarea>();
	//String [] parametrosBusqueda = new String[]{"%"+criterio+"%"};
	
	
	try {
		JSONArray array = new JSONArray(Sql);
		for (int i = 0; i < array.length(); i++) {
		    JSONObject row = array.getJSONObject(i);
		    Tarea item = new Tarea();
		    item.setId_tarea(row.getInt("id_tarea"));
		    item.setDescripcion(row.getString("descripcion"));
		    item.setComentario(row.getString("comentario"));
		    item.setNivel_tarea(row.getString("nivel_tarea"));
		    
		    listaTarea.add(item);
		}
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
//	String Sql = "SELECT * FROM gasto";r
	/*Cursor cursor = db.rawQuery(Sql, parametrosBusqueda);
	if(cursor.moveToFirst()){
		do{
			Tarea item = new Tarea();
			
			
				item.setId_gastos(cursor.getInt(0));
				item.setTitulo(cursor.getString(1));
				item.setDescripcion(cursor.getString(2));
				item.setTipo(cursor.getString(3));
				item.setDia(cursor.getInt(4));
				item.setMes(cursor.getInt(5));
				item.setAnio(cursor.getInt(6));
				item.setValor(cursor.getDouble(7));
				item.setFecha(cursor.getString(8));
				item.setIdTipo(cursor.getInt(9));
				
				
			listaGastos.add(item);
		}while(cursor.moveToNext());
	}
	cursor.close();
	db.close();*/
	return listaTarea;
}	



	
	
	
}
