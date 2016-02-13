package com.modelos;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import com.modelo.informacion.Alarma;
import com.modelo.informacion.Tarea;
import com.modelo.informacion.TareaRealizadaE;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ParseException;
import android.util.Log;

public class DbUsuarios {

	public static final String DB_NAME = "DB_Tareas";
	public static final String TABLA_NAME_alarma = "alarma";
	
	
	static String NAMESPACE = "http://servicio.servicio.com";
	static String URL = "http://192.168.71.53:8080/Servicio_Tarea/services/funciones_servicio?wsdl";
	private String SOAP_ACTION="http://192.168.71.53:8080/Servicio_Tarea/services/funciones_servicio/lista_tareas";
	private String METODO="lista_tareas";
	
	private String SOAP_ACTION2="http://192.168.71.53:8080/Servicio_Tarea/services/funciones_servicio/devolucion_tarea";
	private String METODO2="devolucion_tarea";
	
	private String SOAP_ACTION3="http://192.168.71.53:8080/Servicio_Tarea/services/funciones_servicio/lista_tareas_jefe_parametro";
	private String METODO3="lista_tareas_jefe_parametro";
	
	private String SOAP_ACTION4="http://192.168.71.53:8080/Servicio_Tarea/services/funciones_servicio/lista_tareasEmpleado";
	private String METODO4="lista_tareasEmpleado";
	
	private String SOAP_ACTION5="http://192.168.71.53:8080/Servicio_Tarea/services/funciones_servicio/lista_tareas_empleado_parametro";
	private String METODO5="lista_tareas_empleado_parametro";
	
	private String SOAP_ACTION6="http://192.168.71.53:8080/Servicio_Tarea/services/funciones_servicio/lista_tarearealizadaxempleado";
	private String METODO6="lista_tarearealizadaxempleado";
	
	private String SOAP_ACTION7="http://192.168.71.53:8080/Servicio_Tarea/services/funciones_servicio/lista_tareasrealizadas_empleado_parametro";
	private String METODO7="lista_tareasrealizadas_empleado_parametro";
	
	private String SOAP_ACTION8="http://192.168.71.53:8080/Servicio_Tarea/services/funciones_servicio/tarearealizadaxid";
	private String METODO8="tarearealizadaxid";
	
	private String Sql = "["
			+ "{'id_tarea':1, 'descripcion':'Descripcion1', 'comentario':'Comentario1', 'nivel_tarea':'bajo', 'estado':'A'},"
			+ "{'id_tarea':2, 'descripcion':'Descripcion2', 'comentario':'Comentario2', 'nivel_tarea':'medio', 'estado':'A'},"
			+ "{'id_tarea':3, 'descripcion':'Descripcion3', 'comentario':'Comentario3', 'nivel_tarea':'alto', 'estado':'A'},"
			+ "{'id_tarea':4, 'descripcion':'Descripcion4', 'comentario':'Comentario4', 'nivel_tarea':'medio', 'estado':'A'},"
			+ "{'id_tarea':5, 'descripcion':'Descripcion5', 'comentario':'Comentario5', 'nivel_tarea':'alto', 'estado':'A'}]";
	
	public Alarma listaRecordatorio(Context contexto, String id){
		TareasSqLite lista = new TareasSqLite(contexto,DB_NAME,null,1);
		SQLiteDatabase db = lista.getReadableDatabase();
		
	   	String sql ="SELECT * FROM " + TABLA_NAME_alarma +" where id= ?";
		 String[] parametroBusqueda = new String[]{id};
		 Cursor cursor =db.rawQuery(sql, parametroBusqueda);
		 
		 Alarma alarma =null;
	 if(cursor.moveToFirst()){
			 alarma= new Alarma();
			 //mapeo de datos
			 alarma.setId(cursor.getInt(0));
			 alarma.setHora(cursor.getString(1));
			 Log.e("datosalmacenadps", ""+cursor.getString(1));
		 }
		return alarma;		
	}
	
	public void editarRecordatorio(Context contexto, String id,String hora){
		
		TareasSqLite tareassqlite = new TareasSqLite(contexto, DB_NAME,null,1);
		SQLiteDatabase db= tareassqlite.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		
		values.put("hora", hora);
		
		String whereClause= "id=? ";
		String whereArgs[] = new String[]{id};
		
		if(db!=null){
			db.update(TABLA_NAME_alarma, values,whereClause , whereArgs);
			db.close();
		}else{
			Log.e("DBTAREAS", "UPPPSSS ERROR :( " );
		}
	}
	
	public void guardarRecordatorio(Context contexto,int id, String hora){
		
		// COnexion a la BD
		TareasSqLite tarjetaDB =new TareasSqLite(contexto,DB_NAME,null,1);
		// Referencia a la BD
		SQLiteDatabase  db = tarjetaDB.getWritableDatabase();
		
		if(db!=null){
			db.execSQL("INSERT INTO alarma( id,hora )" + "VALUES("+id+",'"+hora+"')");
		}
		db.close(); //cerrar referencia a la base
		
	}
	
/*public Datosusuarios Listalogin(Context contexto, String usuario, String contrasenia ){
		
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
	*/

	public float[] listarEficiencia(int id_personas){
	float[] variable = {0, 0, 0, 0,0};
	
	Log.e("id persona", id_personas+"");
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
			  			
			  			//declaracion de variables para el calculo de la eficiencia
			  			int n=0, r=0, t=0,a=0;
			  			float efi=0;
			  			JSONObject jsondatos = new JSONObject(result.getProperty(0).toString());
			  			JSONArray arr = jsondatos.getJSONArray("tareas");
			  			for (int i = 0; i < arr.length(); i++)
			  			{
			  				if(arr.getJSONObject(i).getString("estado").equals("R")){
						    	n=n+1;
						    	r=r+1;
						    }
						    if(arr.getJSONObject(i).getString("estado").equals("T")){
						    	n=n+1;
						    	t=t+1;
						    }
						    					        
						    if(arr.getJSONObject(i).getString("estado").equals("A")) {
						    	Date fechaActual = new Date();
						        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
						        String fechaSistema=formateador.format(fechaActual);
						    	String resultadoMayor= compararFechasConDate(CapturarFechaInicio(arr.getJSONObject(i).getString("fecha_fin")),fechaSistema);
						    	if(resultadoMayor.equals("si")){
						    	n=n+1;
						    	a=a+1;
						    	}else{
						    	}
						    }
						  
			  			}
			  			
			  			efi=((r*100)/n);
			  			variable[0]=n;
			  			variable[1]=r;
			  			variable[2]=t;
			  			variable[3]=a;
			  			variable[4]=efi;
			  			
			  			
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
	return variable;
}
	
	public float[] listarEficienciaEmpleado(int id_personas){
	
		float[] variable = {0, 0, 0, 0,0};
		
		Log.e("id persona", id_personas+"");
		ArrayList<Tarea> listaTarea = null;

		listaTarea = new ArrayList<Tarea>();
		try {
			JSONObject pruebalistatarea = new JSONObject();
			pruebalistatarea.put("id_persona", id_personas);
			
			SoapObject request = new SoapObject(NAMESPACE, METODO4);
			request.addProperty("request1" , pruebalistatarea.toString());
		  	
			
			
		    SoapSerializationEnvelope Envoltorio = new SoapSerializationEnvelope (SoapEnvelope.VER11);
		    Envoltorio.setOutputSoapObject (request);
		  	HttpTransportSE TransporteHttp = new HttpTransportSE(URL);
		  	try {
		  		TransporteHttp.call(SOAP_ACTION4, Envoltorio);
		  	   
		  		SoapObject result = (SoapObject) Envoltorio.bodyIn;
		  		
				  		if(result != null){
				  			Log.e("TIPO ACCION: 2-- ","opcion");
				  		//	Toast.makeText(getApplicationContext(), result.getProperty(0).toString(), Toast.LENGTH_SHORT).show();
				  			
				  			//declaracion de variables para el calculo de la eficiencia
				  			int n=0, r=0, t=0,a=0;
				  			float efi=0;
				  			JSONObject jsondatos = new JSONObject(result.getProperty(0).toString());
				  			JSONArray arr = jsondatos.getJSONArray("tareas");
				  			for (int i = 0; i < arr.length(); i++)
				  			{
				  				Log.e("veces",arr.getJSONObject(i).getString("estado")+" : " +i );
							    
				  				if(arr.getJSONObject(i).getString("estado").equals("R")){
							    	n=n+1;
							    	r=r+1;
							    }
							    if(arr.getJSONObject(i).getString("estado").equals("T")){
							    	n=n+1;
							    	t=t+1;
							    }
							    					        
							    if(arr.getJSONObject(i).getString("estado").equals("A")) {
							    	Date fechaActual = new Date();
							        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
							        String fechaSistema=formateador.format(fechaActual);
							    	String resultadoMayor= compararFechasConDate(CapturarFechaInicio(arr.getJSONObject(i).getString("fecha_fin")),fechaSistema);
							    	if(resultadoMayor.equals("si")){
							    	n=n+1;
							    	a=a+1;
							    	}else{
							    	}
							    }
							  
				  			}
				  			
				  			efi=((r*100)/n);
				  			variable[0]=n;
				  			variable[1]=r;
				  			variable[2]=t;
				  			variable[3]=a;
				  			variable[4]=efi;
				  			
				  			
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
		return variable;
	}
	
	
	public String CapturarFechaInicio(String cadena){
		String fecha1="";
		String sCadena = cadena;
		for (int x=0;x<=9;x++) {
			fecha1 = fecha1 + sCadena.charAt(x);
			}
		return fecha1;
	}
	
	private String compararFechasConDate(String fecha1, String fechaActual) {  
		  String resultado="";
		   /**Obtenemos las fechas enviadas en el formato a comparar*/
		   SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd"); 
		   
		try {
		   Date fechaDate1 = formateador.parse(fecha1);
		   Date fechaDate2 = formateador.parse(fechaActual);
		   Log.e("hollaa", "akilooeeeeegggggg");	    
		    if ( fechaDate1.before(fechaDate2) ){
		    resultado= "si";
		    }else{
		    resultado="no";
		    }
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  return resultado;
		 
		}


//LISTARR TAREAS JEFFE SIN PARAMETRO
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
						    
						    if(arr.getJSONObject(i).getInt("id_tipotarea")==1){
						    	item.setNivel_tarea("Bajo");
							}else if(arr.getJSONObject(i).getInt("id_tipotarea")==2){
								item.setNivel_tarea("Medio");	
							}else{
								item.setNivel_tarea("Alto");
							}
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

//eficiencia
public ArrayList<Tarea> listartextoeficiencia(int id_personas){
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
			  				
			  				if((arr.getJSONObject(i).getString("estado").equals("A")) || (arr.getJSONObject(i).getString("estado").equals("R")) || (arr.getJSONObject(i).getString("estado").equals("T"))){
			  		
			  					if(arr.getJSONObject(i).getString("estado").equals("R")){
			  						Tarea item = new Tarea();
								    item.setId_tarea(arr.getJSONObject(i).getInt("id_tarea"));
								    item.setId_empleado(arr.getJSONObject(i).getInt("id_persotarea"));
								    item.setDescripcion(arr.getJSONObject(i).getString("descripcion"));
								    item.setComentario(arr.getJSONObject(i).getString("comentario"));
								    item.setNivel_tarea(arr.getJSONObject(i).getString("id_tipotarea"));
								    item.setFecha_inicio(arr.getJSONObject(i).getString("fecha_inicio"));
								    item.setFecha_fin(arr.getJSONObject(i).getString("fecha_fin"));
								    item.setEstado("Eficiente");
								    listaTarea.add(item);
							    }
							    if(arr.getJSONObject(i).getString("estado").equals("T")){
							    	Tarea item = new Tarea();
								    item.setId_tarea(arr.getJSONObject(i).getInt("id_tarea"));
								    item.setId_empleado(arr.getJSONObject(i).getInt("id_persotarea"));
								    item.setDescripcion(arr.getJSONObject(i).getString("descripcion"));
								    item.setComentario(arr.getJSONObject(i).getString("comentario"));
								    item.setNivel_tarea(arr.getJSONObject(i).getString("id_tipotarea"));
								    item.setFecha_inicio(arr.getJSONObject(i).getString("fecha_inicio"));
								    item.setFecha_fin(arr.getJSONObject(i).getString("fecha_fin"));
								    item.setEstado("M M Eficiente");
								    listaTarea.add(item);
							    }
							    
							    if(arr.getJSONObject(i).getString("estado").equals("A")) {
							    	Date fechaActual = new Date();
							        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
							        String fechaSistema=formateador.format(fechaActual);
							    	String resultadoMayor= compararFechasConDate(CapturarFechaInicio(arr.getJSONObject(i).getString("fecha_fin")),fechaSistema);
							    	if(resultadoMayor.equals("si")){
							    		Tarea item = new Tarea();
									    item.setId_tarea(arr.getJSONObject(i).getInt("id_tarea"));
									    item.setId_empleado(arr.getJSONObject(i).getInt("id_persotarea"));
									    item.setDescripcion(arr.getJSONObject(i).getString("descripcion"));
									    item.setComentario(arr.getJSONObject(i).getString("comentario"));
									    item.setNivel_tarea(arr.getJSONObject(i).getString("id_tipotarea"));
									    item.setFecha_inicio(arr.getJSONObject(i).getString("fecha_inicio"));
									    item.setFecha_fin(arr.getJSONObject(i).getString("fecha_fin"));
									    item.setEstado("No Eficiente");
									    listaTarea.add(item);
							    	}else{
							    	Log.e("hollaa", "akiiii");
							    	}
							    }
			  				
							}
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


//LISTAR TAREAS EFICIENCIA POR TAREAS
public ArrayList<Tarea> listarEficienciaEmpleadoTareas(int id_personas){
	ArrayList<Tarea> listaTarea = null;
	listaTarea = new ArrayList<Tarea>();
	try {
		JSONObject pruebalistatarea = new JSONObject();
		pruebalistatarea.put("id_persona", id_personas);
		SoapObject request = new SoapObject(NAMESPACE, METODO4);
		request.addProperty("request1" , pruebalistatarea.toString());
	  	
	    SoapSerializationEnvelope Envoltorio = new SoapSerializationEnvelope (SoapEnvelope.VER11);
	    Envoltorio.setOutputSoapObject (request);
	  	HttpTransportSE TransporteHttp = new HttpTransportSE(URL);
	  	try {
	  		TransporteHttp.call(SOAP_ACTION4, Envoltorio);  
	  		SoapObject result = (SoapObject) Envoltorio.bodyIn;
	  		
			  		if(result != null){
			  			Log.e("TIPO ACCION: 2-- ","opcion");
			  		//	Toast.makeText(getApplicationContext(), result.getProperty(0).toString(), Toast.LENGTH_SHORT).show();
			  			
			  			JSONObject jsondatos = new JSONObject(result.getProperty(0).toString());
			  			JSONArray arr = jsondatos.getJSONArray("tareas");
			  			for (int i = 0; i < arr.length(); i++)
			  			{
			  				if((arr.getJSONObject(i).getString("estado").equals("A")) || (arr.getJSONObject(i).getString("estado").equals("R")) || (arr.getJSONObject(i).getString("estado").equals("T"))){
						  		
			  					if(arr.getJSONObject(i).getString("estado").equals("R")){
			  						Tarea item = new Tarea();
								    item.setId_tarea(arr.getJSONObject(i).getInt("id_tarea"));
								    item.setId_empleado(arr.getJSONObject(i).getInt("id_persotarea"));
								    item.setDescripcion(arr.getJSONObject(i).getString("descripcion"));
								    item.setComentario(arr.getJSONObject(i).getString("comentario"));
								    item.setNivel_tarea(arr.getJSONObject(i).getString("id_tipotarea"));
								    item.setFecha_inicio(arr.getJSONObject(i).getString("fecha_inicio"));
								    item.setFecha_fin(arr.getJSONObject(i).getString("fecha_fin"));
								    item.setEstado("M.E");
								    listaTarea.add(item);
							    }
							    if(arr.getJSONObject(i).getString("estado").equals("T")){
							    	Tarea item = new Tarea();
								    item.setId_tarea(arr.getJSONObject(i).getInt("id_tarea"));
								    item.setId_empleado(arr.getJSONObject(i).getInt("id_persotarea"));
								    item.setDescripcion(arr.getJSONObject(i).getString("descripcion"));
								    item.setComentario(arr.getJSONObject(i).getString("comentario"));
								    item.setNivel_tarea(arr.getJSONObject(i).getString("id_tipotarea"));
								    item.setFecha_inicio(arr.getJSONObject(i).getString("fecha_inicio"));
								    item.setFecha_fin(arr.getJSONObject(i).getString("fecha_fin"));
								    item.setEstado("E");
								    listaTarea.add(item);
							    }
							    
							    if(arr.getJSONObject(i).getString("estado").equals("A")) {
							    	Date fechaActual = new Date();
							        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
							        String fechaSistema=formateador.format(fechaActual);
							    	String resultadoMayor= compararFechasConDate(CapturarFechaInicio(arr.getJSONObject(i).getString("fecha_fin")),fechaSistema);
							    	if(resultadoMayor.equals("si")){
							    		Tarea item = new Tarea();
									    item.setId_tarea(arr.getJSONObject(i).getInt("id_tarea"));
									    item.setId_empleado(arr.getJSONObject(i).getInt("id_persotarea"));
									    item.setDescripcion(arr.getJSONObject(i).getString("descripcion"));
									    item.setComentario(arr.getJSONObject(i).getString("comentario"));
									    item.setNivel_tarea(arr.getJSONObject(i).getString("id_tipotarea"));
									    item.setFecha_inicio(arr.getJSONObject(i).getString("fecha_inicio"));
									    item.setFecha_fin(arr.getJSONObject(i).getString("fecha_fin"));
									    item.setEstado("N.E");
									    listaTarea.add(item);
							    	}else{
							    	Log.e("hollaa", "akiiii");
							    	}
							    }
			  				
							}		
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

//LISTAR TAREAS EFICIENCIA POR TAREAS PARAMETROS
public ArrayList<Tarea> listarEficienciaEmpleadoTareasParametro(int id_personas,String criterio){
	ArrayList<Tarea> listaTarea = null;
	listaTarea = new ArrayList<Tarea>();
	try {
		JSONObject pruebalistatarea = new JSONObject();
		pruebalistatarea.put("id_persona", id_personas);
		pruebalistatarea.put("descripcion", criterio);
		
		SoapObject request = new SoapObject(NAMESPACE, METODO5);
		request.addProperty("request1" , pruebalistatarea.toString());
	  	
	    SoapSerializationEnvelope Envoltorio = new SoapSerializationEnvelope (SoapEnvelope.VER11);
	    Envoltorio.setOutputSoapObject (request);
	  	HttpTransportSE TransporteHttp = new HttpTransportSE(URL);
	  	try {
	  		TransporteHttp.call(SOAP_ACTION5, Envoltorio);  
	  		SoapObject result = (SoapObject) Envoltorio.bodyIn;
	  		
			  		if(result != null){
			  			Log.e("TIPO ACCION: 2-- ","opcion");
			  		//	Toast.makeText(getApplicationContext(), result.getProperty(0).toString(), Toast.LENGTH_SHORT).show();
			  			
			  			JSONObject jsondatos = new JSONObject(result.getProperty(0).toString());
			  			JSONArray arr = jsondatos.getJSONArray("tareas");
			  			for (int i = 0; i < arr.length(); i++)
			  			{
			  				if((arr.getJSONObject(i).getString("estado").equals("A")) || (arr.getJSONObject(i).getString("estado").equals("R")) || (arr.getJSONObject(i).getString("estado").equals("T"))){
						  		
			  					if(arr.getJSONObject(i).getString("estado").equals("R")){
			  						Tarea item = new Tarea();
								    item.setId_tarea(arr.getJSONObject(i).getInt("id_tarea"));
								    item.setId_empleado(arr.getJSONObject(i).getInt("id_persotarea"));
								    item.setDescripcion(arr.getJSONObject(i).getString("descripcion"));
								    item.setComentario(arr.getJSONObject(i).getString("comentario"));
								    item.setNivel_tarea(arr.getJSONObject(i).getString("id_tipotarea"));
								    item.setFecha_inicio(arr.getJSONObject(i).getString("fecha_inicio"));
								    item.setFecha_fin(arr.getJSONObject(i).getString("fecha_fin"));
								    item.setEstado("M.E");
								    listaTarea.add(item);
							    }
							    if(arr.getJSONObject(i).getString("estado").equals("T")){
							    	Tarea item = new Tarea();
								    item.setId_tarea(arr.getJSONObject(i).getInt("id_tarea"));
								    item.setId_empleado(arr.getJSONObject(i).getInt("id_persotarea"));
								    item.setDescripcion(arr.getJSONObject(i).getString("descripcion"));
								    item.setComentario(arr.getJSONObject(i).getString("comentario"));
								    item.setNivel_tarea(arr.getJSONObject(i).getString("id_tipotarea"));
								    item.setFecha_inicio(arr.getJSONObject(i).getString("fecha_inicio"));
								    item.setFecha_fin(arr.getJSONObject(i).getString("fecha_fin"));
								    item.setEstado("E");
								    listaTarea.add(item);
							    }
							    
							    if(arr.getJSONObject(i).getString("estado").equals("A")) {
							    	Date fechaActual = new Date();
							        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
							        String fechaSistema=formateador.format(fechaActual);
							    	String resultadoMayor= compararFechasConDate(CapturarFechaInicio(arr.getJSONObject(i).getString("fecha_fin")),fechaSistema);
							    	if(resultadoMayor.equals("si")){
							    		Tarea item = new Tarea();
									    item.setId_tarea(arr.getJSONObject(i).getInt("id_tarea"));
									    item.setId_empleado(arr.getJSONObject(i).getInt("id_persotarea"));
									    item.setDescripcion(arr.getJSONObject(i).getString("descripcion"));
									    item.setComentario(arr.getJSONObject(i).getString("comentario"));
									    item.setNivel_tarea(arr.getJSONObject(i).getString("id_tipotarea"));
									    item.setFecha_inicio(arr.getJSONObject(i).getString("fecha_inicio"));
									    item.setFecha_fin(arr.getJSONObject(i).getString("fecha_fin"));
									    item.setEstado("N.E");
									    listaTarea.add(item);
							    	}else{
							    	Log.e("hollaa", "akiiii");
							    	}
							    }
			  				
							}		
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




//LISTARR TAREAS EMPLEADO SIN PARAMETRO
public ArrayList<Tarea> listartextoempleado(int id_personas){
	ArrayList<Tarea> listaTarea = null;
	listaTarea = new ArrayList<Tarea>();
	try {
		JSONObject pruebalistatarea = new JSONObject();
		pruebalistatarea.put("id_persona", id_personas);
		Log.e("cargarlista tareas", id_personas+"");
		SoapObject request = new SoapObject(NAMESPACE, METODO4);
		request.addProperty("request1" , pruebalistatarea.toString());
	  	
	    SoapSerializationEnvelope Envoltorio = new SoapSerializationEnvelope (SoapEnvelope.VER11);
	    Envoltorio.setOutputSoapObject (request);
	  	HttpTransportSE TransporteHttp = new HttpTransportSE(URL);
	  	try {
	  		TransporteHttp.call(SOAP_ACTION4, Envoltorio);
	  	   
	  		SoapObject result = (SoapObject) Envoltorio.bodyIn;
	  		
			  		if(result != null){
			  			Log.e("TIPO ACCION: 2-- ","opcion");
			  		//	Toast.makeText(getApplicationContext(), result.getProperty(0).toString(), Toast.LENGTH_SHORT).show();
			  			
			  			JSONObject jsondatos = new JSONObject(result.getProperty(0).toString());
			  			JSONArray arr = jsondatos.getJSONArray("tareas");
			  			for (int i = 0; i < arr.length(); i++)
			  			{
			  				if(arr.getJSONObject(i).getString("estado").equals("A")){
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


//LISTARR TAREAS EMPLEADO REALIZADO SIN PARAMETRO
public ArrayList<Tarea> listartextoempleadoRealizado(int id_personas){
	ArrayList<Tarea> listaTarea = null;
	listaTarea = new ArrayList<Tarea>();
	try {
		JSONObject pruebalistatarea = new JSONObject();
		pruebalistatarea.put("id_persona", id_personas);
		Log.e("cargarlista tareas", id_personas+"");
		SoapObject request = new SoapObject(NAMESPACE, METODO4);
		request.addProperty("request1" , pruebalistatarea.toString());
	  	
	    SoapSerializationEnvelope Envoltorio = new SoapSerializationEnvelope (SoapEnvelope.VER11);
	    Envoltorio.setOutputSoapObject (request);
	  	HttpTransportSE TransporteHttp = new HttpTransportSE(URL);
	  	try {
	  		TransporteHttp.call(SOAP_ACTION4, Envoltorio);
	  	   
	  		SoapObject result = (SoapObject) Envoltorio.bodyIn;
	  		
			  		if(result != null){
			  			Log.e("TIPO ACCION: 2-- ","opcion");
			  		//	Toast.makeText(getApplicationContext(), result.getProperty(0).toString(), Toast.LENGTH_SHORT).show();
			  			
			  			JSONObject jsondatos = new JSONObject(result.getProperty(0).toString());
			  			JSONArray arr = jsondatos.getJSONArray("tareas");
			  			for (int i = 0; i < arr.length(); i++)
			  			{
			     if(arr.getJSONObject(i).getString("estado").equals("R") || arr.getJSONObject(i).getString("estado").equals("T")){
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
			  			}
			  		}else{
			  			
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

//LISTARR TAREAS EMPLEADO REALIZADO CON PARAMETRO
public ArrayList<Tarea> listartextoempleadoRealizado(int id_personas, String criterio){
	ArrayList<Tarea> listaTarea = null;
	listaTarea = new ArrayList<Tarea>();
	try {
		JSONObject pruebalistatarea = new JSONObject();
		pruebalistatarea.put("id_persona", id_personas);
		pruebalistatarea.put("descripcion", criterio);
		
		SoapObject request = new SoapObject(NAMESPACE, METODO5);
		request.addProperty("request1" , pruebalistatarea.toString());
	  	
	    SoapSerializationEnvelope Envoltorio = new SoapSerializationEnvelope (SoapEnvelope.VER11);
	    Envoltorio.setOutputSoapObject (request);
	  	HttpTransportSE TransporteHttp = new HttpTransportSE(URL);
	  	try {
	  		TransporteHttp.call(SOAP_ACTION5, Envoltorio);
	  	   
	  		SoapObject result = (SoapObject) Envoltorio.bodyIn;
	  		
			  		if(result != null){
		  			Log.e("TIPO ACCION: 2-- ","opcion");
			  			JSONObject jsondatos = new JSONObject(result.getProperty(0).toString());
			  			JSONArray arr = jsondatos.getJSONArray("tareas");
			  			for (int i = 0; i < arr.length(); i++)
			  			{
			  				if(arr.getJSONObject(i).getString("estado").equals("R") || arr.getJSONObject(i).getString("estado").equals("T")){
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


//LISTARR TAREAS EMPLEADO CON PARAMETRO
public ArrayList<Tarea> listartextoempleado(int id_personas, String criterio){
	ArrayList<Tarea> listaTarea = null;
	listaTarea = new ArrayList<Tarea>();
	try {
		JSONObject pruebalistatarea = new JSONObject();
		pruebalistatarea.put("id_persona", id_personas);
		pruebalistatarea.put("descripcion", criterio);
		
		SoapObject request = new SoapObject(NAMESPACE, METODO5);
		request.addProperty("request1" , pruebalistatarea.toString());
	  	
	    SoapSerializationEnvelope Envoltorio = new SoapSerializationEnvelope (SoapEnvelope.VER11);
	    Envoltorio.setOutputSoapObject (request);
	  	HttpTransportSE TransporteHttp = new HttpTransportSE(URL);
	  	try {
	  		TransporteHttp.call(SOAP_ACTION5, Envoltorio);
	  	   
	  		SoapObject result = (SoapObject) Envoltorio.bodyIn;
	  		
			  		if(result != null){
		  			Log.e("TIPO ACCION: 2-- ","opcion");
			  			JSONObject jsondatos = new JSONObject(result.getProperty(0).toString());
			  			JSONArray arr = jsondatos.getJSONArray("tareas");
			  			for (int i = 0; i < arr.length(); i++)
			  			{
			  				if(arr.getJSONObject(i).getString("estado").equals("A")){
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
		
//listar tareas realizadas sin parametro empleados
public ArrayList<TareaRealizadaE> listartextoempleadorealizado(int id_personas){
	ArrayList<TareaRealizadaE> listaTarea = null;
	listaTarea = new ArrayList<TareaRealizadaE>();
	try {
		
		SoapObject request = new SoapObject(NAMESPACE, METODO6);
		request.addProperty("request1" , id_personas+"");
	  	
	    SoapSerializationEnvelope Envoltorio = new SoapSerializationEnvelope (SoapEnvelope.VER11);
	    Envoltorio.setOutputSoapObject (request);
	  	HttpTransportSE TransporteHttp = new HttpTransportSE(URL);
	  	try {
	  		TransporteHttp.call(SOAP_ACTION6, Envoltorio);
	  	   
	  		SoapObject result = (SoapObject) Envoltorio.bodyIn;
	  		
			  		if(result != null){
			  			Log.e("TIPO ACCION: 2-- ","opcion");
			  		//	Toast.makeText(getApplicationContext(), result.getProperty(0).toString(), Toast.LENGTH_SHORT).show();
			  			
			  			JSONObject jsondatos = new JSONObject(result.getProperty(0).toString());
			  			JSONArray arr = jsondatos.getJSONArray("tarearealizada");
			  			for (int i = 0; i < arr.length(); i++)
			  			{
				  			TareaRealizadaE item = new TareaRealizadaE();
						    item.setId(arr.getJSONObject(i).getInt("id_Tarea_realizada"));
						    item.setIdTarea(arr.getJSONObject(i).getInt("id_tarea"));
						    item.setDescripcion(arr.getJSONObject(i).getString("descipcion"));
						    item.setFechafin(arr.getJSONObject(i).getString("fecha_fin"));
						    item.setEstado(arr.getJSONObject(i).getString("estado"));
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

//listar tareas realizadas con parametro empleados 
public ArrayList<TareaRealizadaE> listartextoempleadorealizado(int id_personas, String criterio){
	ArrayList<TareaRealizadaE> listaTarea = null;
	listaTarea = new ArrayList<TareaRealizadaE>();
	try {
		
		JSONObject pruebalistatarea = new JSONObject();
		pruebalistatarea.put("id_persona", id_personas);
		pruebalistatarea.put("descripcion", criterio);
		
		SoapObject request = new SoapObject(NAMESPACE, METODO7);
		request.addProperty("request1" , pruebalistatarea.toString());
	  	
	    SoapSerializationEnvelope Envoltorio = new SoapSerializationEnvelope (SoapEnvelope.VER11);
	    Envoltorio.setOutputSoapObject (request);
	  	HttpTransportSE TransporteHttp = new HttpTransportSE(URL);
	  	try {
	  		TransporteHttp.call(SOAP_ACTION7, Envoltorio);
	  	   
	  		SoapObject result = (SoapObject) Envoltorio.bodyIn;
	  		
			  		if(result != null){
			  			Log.e("TIPO ACCION: 2-- ","opcion");
			  		//	Toast.makeText(getApplicationContext(), result.getProperty(0).toString(), Toast.LENGTH_SHORT).show();
			  			
			  			JSONObject jsondatos = new JSONObject(result.getProperty(0).toString());
			  			JSONArray arr = jsondatos.getJSONArray("tarearealizada");
			  			for (int i = 0; i < arr.length(); i++)
			  			{
				  			TareaRealizadaE item = new TareaRealizadaE();
						    item.setId(arr.getJSONObject(i).getInt("id_Tarea_realizada"));
						    item.setIdTarea(arr.getJSONObject(i).getInt("id_tarea"));
						    item.setDescripcion(arr.getJSONObject(i).getString("descipcion"));
						    item.setFechafin(arr.getJSONObject(i).getString("fecha_fin"));
						    item.setEstado(arr.getJSONObject(i).getString("estado"));
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




//LISTARR TAREAS JEFFE CON PARAMETRO
// cargar la lista con id y haciendo un onchage del  editext al buscar
public ArrayList<Tarea> listartexto(int id_personas, String criterio){
	ArrayList<Tarea> listaTarea = null;
	listaTarea = new ArrayList<Tarea>();
	try {
		JSONObject pruebalistatarea = new JSONObject();
		pruebalistatarea.put("id_persona", id_personas);
		pruebalistatarea.put("descripcion", criterio);
		
		SoapObject request = new SoapObject(NAMESPACE, METODO3);
		request.addProperty("request1" , pruebalistatarea.toString());
	  	
	    SoapSerializationEnvelope Envoltorio = new SoapSerializationEnvelope (SoapEnvelope.VER11);
	    Envoltorio.setOutputSoapObject (request);
	  	HttpTransportSE TransporteHttp = new HttpTransportSE(URL);
	  	try {
	  		TransporteHttp.call(SOAP_ACTION3, Envoltorio);
	  	   
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
						    if(arr.getJSONObject(i).getInt("id_tipotarea")==1){
						    	item.setNivel_tarea("Bajo");
							}else if(arr.getJSONObject(i).getInt("id_tipotarea")==2){
								item.setNivel_tarea("Medio");	
							}else{
								item.setNivel_tarea("Alto");
							}
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
								    item.setArchivo_adjunto(jsondatos.getString("archivo"));
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

public TareaRealizadaE tareabuscarEditar(int id_tarea){
	
	TareaRealizadaE item = new TareaRealizadaE();
	try {
		
		
		SoapObject request = new SoapObject(NAMESPACE, METODO8);
		request.addProperty("request1" ,id_tarea+"");
	  	
	    SoapSerializationEnvelope Envoltorio = new SoapSerializationEnvelope (SoapEnvelope.VER11);
	    Envoltorio.setOutputSoapObject (request);
	  	HttpTransportSE TransporteHttp = new HttpTransportSE(URL);
	  	try {
	  		TransporteHttp.call(SOAP_ACTION8, Envoltorio);
	  	    SoapObject result = (SoapObject) Envoltorio.bodyIn;
	  		
			  		if(result != null){
			  			Log.e("TIPO ACCION: 2-- ","opcion");
			  		//	Toast.makeText(getApplicationContext(), result.getProperty(0).toString(), Toast.LENGTH_SHORT).show();
			  			
			  			JSONObject jsondatos = new JSONObject(result.getProperty(0).toString());
			  
			  					 item.setId(jsondatos.getInt("id_Tarea_realizada"));
			  					 item.setIdTarea(jsondatos.getInt("id_tarea"));
			  					 item.setDescripcion(jsondatos.getString("descipcion"));
			  					 item.setFechafin(jsondatos.getString("fecha_fin"));
			  					item.setFechafin(jsondatos.getString("estado"));			
			  				   
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


	
}
	
