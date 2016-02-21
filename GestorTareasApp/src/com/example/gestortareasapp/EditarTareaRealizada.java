package com.example.gestortareasapp;

import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.modelo.informacion.Alarma;
import com.modelo.informacion.Tarea;
import com.modelo.informacion.TareaRealizadaE;
import com.modelos.DbUsuarios;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditarTareaRealizada extends Activity {
	TextView textviewFecha,textviewDescripTar,textviewComentTar;
	EditText editTextDes;
	
	int idtarea,Tarea_id;
	TareaRealizadaE tobj = new TareaRealizadaE();
	Tarea tobjtarea = new Tarea();
	String url;
	
	static String NAMESPACE = "http://servicio.servicio.com";
	static String URL = "http://192.168.71.53:8080/Servicio_Tarea/services/funciones_servicio?wsdl";
	private String SOAP_ACTION="http://192.168.71.53:8080/Servicio_Tarea/services/funciones_servicio/actualizartarearealizada";
	//192.192.192.1  clave redaplicaciones
	private String METODO="actualizartarearealizada";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editar_tarea_realizada);
		//edittextDescripcion = (EditText) findViewById(R.id.editTextEditarDescripcion);
		textviewFecha=(TextView) findViewById(R.id.textViewFechaActual);
		textviewDescripTar= (TextView) findViewById(R.id.textViewDescripcTareaEditar);
		textviewComentTar= (TextView) findViewById(R.id.textViewComentarioTareaEditarr);
		editTextDes=(EditText) findViewById(R.id.editTxtDescripcionTareaR);
		//ID TAREA 
		idtarea=this.getIntent().getIntExtra("idTarea", 0);
		BuscarDatos();
		
	}
	public void onArchivo(View v){
		String link = url;
		Log.e("link Archivo", link);
		Intent intent = null;
		intent = new Intent(intent.ACTION_VIEW,Uri.parse(link));
		startActivity(intent);
	
	}
	
	
public void BuscarDatos(){
		
	DbUsuarios dbuser = new DbUsuarios();
	tobj= dbuser.tareabuscarEditar(idtarea);
	editTextDes.setText(tobj.getDescripcion());
	Tarea_id=tobj.getId();
	Log.e("Tarea_id tu ....", idtarea+"");
	Log.e("Tarea_id tu macho", tobj.getId()+"");
	tobjtarea=dbuser.tareabuscarempleado(idtarea);
	Integer id_tar;
	id_tar=tobjtarea.getId_tarea();
	Log.e("mi tarea ", id_tar+"");
	textviewDescripTar.setText(tobjtarea.getDescripcion());
	textviewComentTar.setText(tobjtarea.getComentario());
	textviewFecha.setText(tobjtarea.getFecha_fin()); 
	url= tobjtarea.getArchivo_adjunto();
}

	
	public void onGuardar(View v){
		if(editTextDes.getText().toString().equals("")){
			Toast.makeText(this, "Faltan por ingresar campos", Toast.LENGTH_LONG).show();
		
		}else{
			
			JSONObject objTarea=new JSONObject();
			try {
				objTarea.put("id_Tarea_realizada",Tarea_id);// no queremos este valor
				objTarea.put("id_tarea",idtarea);
				objTarea.put("descipcion",editTextDes.getText().toString());
				objTarea.put("fecha_fin",getDatePhone());// poner fecha actual
				objTarea.put("archivo_env","LLLLLLL");
				objTarea.put("estado","A");
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Log.e("idTarRealiz", Tarea_id+"");
			Log.e("idTarea", idtarea+"");
			Log.e("json", objTarea.toString());			
			
			SoapObject request = new SoapObject(NAMESPACE, METODO);
			request.addProperty("request1" , objTarea.toString());
		  	
		  	  SoapSerializationEnvelope Envoltorio = new SoapSerializationEnvelope (SoapEnvelope.VER11);
		      Envoltorio.setOutputSoapObject (request);
		  	  
		  	  HttpTransportSE TransporteHttp = new HttpTransportSE(URL);
		  	
		  	try {
		  		TransporteHttp.call(SOAP_ACTION, Envoltorio);
		  		SoapObject result = (SoapObject) Envoltorio.bodyIn;
		  		
		  		if(result != null){
		  			
		  			if(result.getProperty(0).toString().equals("si")){
						Toast.makeText(this, "Tarea Realizada Editada Correctamente ", Toast.LENGTH_LONG).show();						
					}
					if(result.getProperty(0).toString().equals("no")){
						Toast.makeText(this, "Error al crear Tarea Realizada", Toast.LENGTH_LONG).show();						
					}		  		
		  		
		  		}else{
		  			Toast.makeText(getApplicationContext(), "No Response!", Toast.LENGTH_SHORT).show();
		  		}
		  		}catch (Exception e) {
		  			e.printStackTrace();
		  			Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_SHORT).show();	  	  		
		  		}			
			finish();
			
		}
        		
	}
	
	private String getDatePhone()
	{
	    Calendar cal = new GregorianCalendar();
	    Date date = cal.getTime();
	    SimpleDateFormat df = new SimpleDateFormat("yyy-MM-dd");
	    String formatteDate = df.format(date);
	    return formatteDate;
	}
	
	public void onCancelar(View v){
		finish();
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.editar_tarea_realizada, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
