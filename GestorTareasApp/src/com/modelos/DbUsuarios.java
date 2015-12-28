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
	
	private String Sql = "["
			+ "{'id_tarea':1, 'descripcion':'Descripcion1', 'comentario':'Comentario1', 'nivel_tarea':'bajo', 'estado':'A'},"
			+ "{'id_tarea':2, 'descripcion':'Descripcion2', 'comentario':'Comentario2', 'nivel_tarea':'medio', 'estado':'A'},"
			+ "{'id_tarea':3, 'descripcion':'Descripcion3', 'comentario':'Comentario3', 'nivel_tarea':'alto', 'estado':'A'},"
			+ "{'id_tarea':4, 'descripcion':'Descripcion4', 'comentario':'Comentario4', 'nivel_tarea':'medio', 'estado':'A'},"
			+ "{'id_tarea':5, 'descripcion':'Descripcion5', 'comentario':'Comentario5', 'nivel_tarea':'alto', 'estado':'A'}]";
	
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
	listaTarea = new ArrayList<Tarea>();
	try {
		JSONArray array = new JSONArray(Sql);
		for (int i = 0; i < array.length(); i++) {
		    JSONObject row = array.getJSONObject(i);
		    Tarea item = new Tarea();
		    item.setId_tarea(row.getInt("id_tarea"));
		    item.setDescripcion(row.getString("descripcion"));
		    item.setComentario(row.getString("comentario"));
		    item.setNivel_tarea(row.getString("nivel_tarea"));
		    item.setFecha_inicio("2/05/2015");
		    item.setFecha_fin("16/05/2015");
		    listaTarea.add(item);
		}
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return listaTarea;
}

public ArrayList<Tarea> listartexto2(){
	ArrayList<Tarea> listaTarea = null;
	listaTarea = new ArrayList<Tarea>();
	try {
		JSONArray array = new JSONArray(Sql);
		for (int i = 0; i < array.length(); i++) {
		    JSONObject row = array.getJSONObject(i);
		    Tarea item = new Tarea();
		    item.setDescripcion(row.getString("descripcion"));
		    item.setFecha_inicio("2/05/2015");
		    item.setFecha_fin("16/05/2015");
		    listaTarea.add(item);
		}
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return listaTarea;
}

public ArrayList<Tarea> listartexto(String criterio){
	ArrayList<Tarea> listaTarea = null;
	listaTarea = new ArrayList<Tarea>();
	try {
		JSONArray array = new JSONArray(Sql);
		for (int i = 0; i < array.length(); i++) {
		    JSONObject row = array.getJSONObject(i);
		    Tarea item = new Tarea();
		    Log.e("criterio", row.getString("descripcion"));
		    Log.e("criterio", criterio);
		    if (row.getString("descripcion").equals(criterio)){
		    	Log.e("criterio2", criterio);
			    item.setId_tarea(row.getInt("id_tarea"));
			    item.setDescripcion(row.getString("descripcion"));
			    item.setComentario(row.getString("comentario"));
			    item.setNivel_tarea(row.getString("nivel_tarea"));
			    item.setEstado(row.getString("estado"));
			    listaTarea.add(item);
		   }
		    
		    
		}
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return listaTarea;
}	


public Tarea tareabuscar(String id_tarea){
	Tarea item = new Tarea();
	try {
		JSONArray array = new JSONArray(Sql);
		Log.e("ID tarea", id_tarea);
		for (int i = 0; i < array.length(); i++) {
		    JSONObject row = array.getJSONObject(i);
		    		    
		    if (row.getInt("id_tarea") == Integer.parseInt(id_tarea)){
		    item.setId_tarea(row.getInt("id_tarea"));
		    item.setDescripcion(row.getString("descripcion"));
		    item.setComentario(row.getString("comentario"));
		    item.setNivel_tarea(row.getString("nivel_tarea"));
		    item.setEstado(row.getString("estado"));
		    }
		}
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return item;
}	



	
	
	
}
