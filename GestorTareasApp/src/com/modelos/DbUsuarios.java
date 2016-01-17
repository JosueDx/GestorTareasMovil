package com.modelos;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import com.modelo.informacion.Datosusuarios;
import com.modelo.informacion.Tarea;
import com.modelo.informacion.Usuarios;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

public class DbUsuarios {

	public static final String DB_NAME = "DB_Tareas";
	public static final String TABLA_NAME_datosusuarios = "datosusuarios";
	public static final String TABLA_NAME_usuarios = "usuarios";
	public static final String TABLA_NAME_tipousuarios = "tipousuarios";
	
	static String NAMESPACE = "http://servicio.servicio.com";
	static String URL = "http://192.168.1.8:8080/Servicio_Tarea/services/funciones_servicio?wsdl";
	private String SOAP_ACTION="http://192.168.1.8:8080/Servicio_Tarea/services/funciones_servicio/lista_tareas";
	private String METODO="lista_tareas";
	
	private String SOAP_ACTION2="http://192.168.1.8:8080/Servicio_Tarea/services/funciones_servicio/devolucion_tarea";
	private String METODO2="devolucion_tarea";
	
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
	
public ArrayList<Tarea> listartexto(int id_personas){
	ArrayList<Tarea> listaTarea = null;
	listaTarea = new ArrayList<Tarea>();
	try {
		JSONObject pruebalistatarea = new JSONObject();
		pruebalistatarea.put("id_persona", id_personas);
		
		SoapObject request = new SoapObject(NAMESPACE, METODO);
		request.addProperty("request1" , pruebalistatarea.toString());
	  	
	    SoapSerializationEnvelope Envoltorio = new SoapSerializationEnvelope (SoapEnvelope.VER11);
	    Envoltorio.setOutputSoapObject (request);
	  	HttpTransportSE TransporteHttp = new HttpTransportSE(URL);
	  	try {
	  		TransporteHttp.call(SOAP_ACTION, Envoltorio);
	  	   
	  		SoapObject result = (SoapObject) Envoltorio.bodyIn;
	  		
			  		if(result != null){
			  			Log.e("TIPO ACCION: 2-- ","opcion");
			  		//	Toast.makeText(getApplicationContext(), result.getProperty(0).toString(), Toast.LENGTH_SHORT).show();
			  			
			  			JSONObject jsondatos = new JSONObject(result.getProperty(0).toString());
			  			JSONArray arr = jsondatos.getJSONArray("tareas");
			  			for (int i = 0; i < arr.length(); i++)
			  			{
				  			Tarea item = new Tarea();
						    item.setId_tarea(arr.getJSONObject(i).getInt("id_tarea"));
						    item.setId_empleado(arr.getJSONObject(i).getInt("id_persotarea"));
						    item.setDescripcion(arr.getJSONObject(i).getString("descripcion"));
						    item.setComentario(arr.getJSONObject(i).getString("comentario"));
						    item.setNivel_tarea(arr.getJSONObject(i).getString("id_tipotarea"));
						    item.setFecha_inicio(arr.getJSONObject(i).getString("fecha_inicio"));
						    item.setFecha_fin(arr.getJSONObject(i).getString("fecha_fin"));
						    listaTarea.add(item);
			  			}
			  			
			  			//return listaTarea;
			  		}else{
			  			//return listaTarea;
			  		}
		  		}catch (JSONException e) {
		  			// TODO Auto-generated catch block
		  			e.printStackTrace();
		  		}
	  		}catch (Exception e) {
	  			e.printStackTrace();
	  		}
	return listaTarea;
}

public ArrayList<Tarea> listartextoempleado(int id_personas){
	ArrayList<Tarea> listaTarea = null;
	listaTarea = new ArrayList<Tarea>();
	try {
		JSONObject pruebalistatarea = new JSONObject();
		pruebalistatarea.put("id_persona", id_personas);
		Log.e("cargarlista tareas", id_personas+"");
		SoapObject request = new SoapObject(NAMESPACE, METODO);
		request.addProperty("request1" , pruebalistatarea.toString());
	  	
	    SoapSerializationEnvelope Envoltorio = new SoapSerializationEnvelope (SoapEnvelope.VER11);
	    Envoltorio.setOutputSoapObject (request);
	  	HttpTransportSE TransporteHttp = new HttpTransportSE(URL);
	  	try {
	  		TransporteHttp.call(SOAP_ACTION, Envoltorio);
	  	   
	  		SoapObject result = (SoapObject) Envoltorio.bodyIn;
	  		
			  		if(result != null){
			  			Log.e("TIPO ACCION: 2-- ","opcion");
			  		//	Toast.makeText(getApplicationContext(), result.getProperty(0).toString(), Toast.LENGTH_SHORT).show();
			  			
			  			JSONObject jsondatos = new JSONObject(result.getProperty(0).toString());
			  			JSONArray arr = jsondatos.getJSONArray("tareas");
			  			for (int i = 0; i < arr.length(); i++)
			  			{
				  			Tarea item = new Tarea();
						    item.setId_tarea(arr.getJSONObject(i).getInt("id_tarea"));
						    item.setId_empleado(arr.getJSONObject(i).getInt("id_persotarea"));
						    item.setDescripcion(arr.getJSONObject(i).getString("descripcion"));
						    item.setComentario(arr.getJSONObject(i).getString("comentario"));
						    item.setNivel_tarea(arr.getJSONObject(i).getString("id_tipotarea"));
						    item.setFecha_inicio(arr.getJSONObject(i).getString("fecha_inicio"));
						    item.setFecha_fin(arr.getJSONObject(i).getString("fecha_fin"));
						    listaTarea.add(item);
			  			}
			  			
			  			//return listaTarea;
			  		}else{
			  			//return listaTarea;
			  		}
		  		}catch (JSONException e) {
		  			// TODO Auto-generated catch block
		  			e.printStackTrace();
		  		}
	  		}catch (Exception e) {
	  			e.printStackTrace();
	  		}
	return listaTarea;
}
		


// cargar la lista con id y haciendo un onchage del  editext al buscar
public ArrayList<Tarea> listartexto(int id_personas, String criterio){
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


public Tarea tareabuscarFutura(String id_tarea){
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
		    item.setFecha_inicio("20/05/2015");
		    item.setFecha_fin("21/05/2015");
		    }
		}
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return item;
}	

public Tarea tareabuscar(int id_personas, int id_tarea){
	
		//ArrayList<Tarea> listaTarea = null;
		//listaTarea = new ArrayList<Tarea>();
		Tarea item = new Tarea();
		try {
			JSONObject pruebalistatarea = new JSONObject();
			pruebalistatarea.put("id_persona", id_personas);
			
			SoapObject request = new SoapObject(NAMESPACE, METODO);
			request.addProperty("request1" , pruebalistatarea.toString());
		  	
		    SoapSerializationEnvelope Envoltorio = new SoapSerializationEnvelope (SoapEnvelope.VER11);
		    Envoltorio.setOutputSoapObject (request);
		  	HttpTransportSE TransporteHttp = new HttpTransportSE(URL);
		  	try {
		  		TransporteHttp.call(SOAP_ACTION, Envoltorio);
		  	   
		  		SoapObject result = (SoapObject) Envoltorio.bodyIn;
		  		
				  		if(result != null){
				  			Log.e("TIPO ACCION: 2-- ","opcion");
				  		//	Toast.makeText(getApplicationContext(), result.getProperty(0).toString(), Toast.LENGTH_SHORT).show();
				  			
				  			JSONObject jsondatos = new JSONObject(result.getProperty(0).toString());
				  			JSONArray arr = jsondatos.getJSONArray("tareas");
				  			for (int i = 0; i < arr.length(); i++)
				  			{			  				
				  				 if (arr.getJSONObject(i).getInt("id_tarea") == id_tarea){
				  					item.setId_tarea(arr.getJSONObject(i).getInt("id_tarea"));
				  					item.setId_empleado(arr.getJSONObject(i).getInt("id_persotarea"));
								    item.setDescripcion(arr.getJSONObject(i).getString("descripcion"));
								    item.setComentario(arr.getJSONObject(i).getString("comentario"));
								    item.setId_nivel_tarea(arr.getJSONObject(i).getInt("id_tipotarea"));
								    item.setFecha_inicio(arr.getJSONObject(i).getString("fecha_inicio"));
								    item.setFecha_fin(arr.getJSONObject(i).getString("fecha_fin"));
				  				   }
				  				
							    //listaTarea.add(item);
				  			}
				  			
				  			//return listaTarea;
				  		}else{
				  			//return listaTarea;
				  		}
			  		}catch (JSONException e) {
			  			// TODO Auto-generated catch block
			  			e.printStackTrace();
			  		}
		  		}catch (Exception e) {
		  			e.printStackTrace();
		  		}	
		
		//return listaTarea;	
	return item;
}	

public Tarea tareabuscarempleado(int id_tarea){
	
	//ArrayList<Tarea> listaTarea = null;
	//listaTarea = new ArrayList<Tarea>();
	Tarea item = new Tarea();
	try {
			
		SoapObject request = new SoapObject(NAMESPACE, METODO2);
		request.addProperty("request1" , id_tarea+"");
	  	
	    SoapSerializationEnvelope Envoltorio = new SoapSerializationEnvelope (SoapEnvelope.VER11);
	    Envoltorio.setOutputSoapObject (request);
	  	HttpTransportSE TransporteHttp = new HttpTransportSE(URL);
	  	try {
	  		TransporteHttp.call(SOAP_ACTION2, Envoltorio);
	  	   
	  		SoapObject result = (SoapObject) Envoltorio.bodyIn;
	  		
			  		if(result != null){
			  			Log.e("TIPO ACCION: 2-- ","opcion");
			  		//	Toast.makeText(getApplicationContext(), result.getProperty(0).toString(), Toast.LENGTH_SHORT).show();
			  			
			  			JSONObject jsondatos = new JSONObject(result.getProperty(0).toString());
			  
			  					item.setId_tarea(jsondatos.getInt("id_tarea"));
			  					item.setId_empleado(jsondatos.getInt("id_persotarea"));
							    item.setDescripcion(jsondatos.getString("descripcion"));
							    item.setComentario(jsondatos.getString("comentario"));
							    item.setId_nivel_tarea(jsondatos.getInt("id_tipotarea"));
							    item.setFecha_inicio(jsondatos.getString("fecha_inicio"));
							    item.setFecha_fin(jsondatos.getString("fecha_fin"));
			  				   
			  		}
			  		
	  		}catch (Exception e) {
	  			e.printStackTrace();
	  		}
		}catch (Exception e) {
			e.printStackTrace();
		}
	return item;

}	

	
}
	
