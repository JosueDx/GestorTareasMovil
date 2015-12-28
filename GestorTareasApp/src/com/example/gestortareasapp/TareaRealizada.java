package com.example.gestortareasapp;

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

import com.modelo.informacion.Tarea;
import com.modelos.DbUsuarios;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TareaRealizada extends Activity {
	
	static String NAMESPACE = "http://servicio.servicio.com";
	static String URL = "http://192.168.71.53:8080/Servicio_Tarea/services/funciones_servicio?wsdl";
	private String SOAP_ACTION="http://192.168.71.53:8080/Servicio_Tarea/services/funciones_servicio/tarearealizado";
	private String METODO="tarearealizado";
	
	TextView textdescripcion, textfecha;
	EditText editdescripcion;
	Tarea tobj = new Tarea();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tarea_realizada);
		inicializar();
		Intent intent = this.getIntent();
		String idTarea = intent.getStringExtra("idTarea");
		cargar_tarea(idTarea);
	}
	
public void cargar_tarea(String idTarea){
		
		DbUsuarios bd =new DbUsuarios();
		tobj = bd.tareabuscar(idTarea);
		
		textdescripcion.setText("   "+tobj.getDescripcion());
	}
	
	void inicializar(){
		
		textdescripcion =  (TextView) findViewById(R.id.textViewTRDescripcion);
		textfecha =  (TextView) findViewById(R.id.textViewTRFecha);
		editdescripcion = (EditText) findViewById(R.id.editTextTRDescripcion);
		textfecha.setText("   "+getDatePhone());
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tarea_realizada, menu);
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
	
	private String getDatePhone()
	{
	    Calendar cal = new GregorianCalendar();
	    Date date = cal.getTime();
	    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
	    String formatteDate = df.format(date);
	    return formatteDate;
	}
	
	public void onGuardar(View boton){ 
		
		if(editdescripcion.equals("")){
			Toast.makeText(this, "Faltan por ingresar campos", Toast.LENGTH_LONG).show();
		}
		else{
			
			JSONObject objRealizada=new JSONObject();
			try {
				objRealizada.put("id_tarea", tobj.getId_tarea());
				objRealizada.put("descripcion", editdescripcion.getText().toString());
				objRealizada.put("fecha_fin",textfecha.getText().toString());
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			SoapObject request = new SoapObject(NAMESPACE, METODO);
			request.addProperty("request1" , objRealizada);
		  	
		  	  SoapSerializationEnvelope Envoltorio = new SoapSerializationEnvelope (SoapEnvelope.VER11);
		      Envoltorio.setOutputSoapObject (request);
		  	  
		  	  HttpTransportSE TransporteHttp = new HttpTransportSE(URL);
		  	
		  	try {
		  		TransporteHttp.call(SOAP_ACTION, Envoltorio);
		  	   
		  		SoapObject result = (SoapObject) Envoltorio.bodyIn;
		  		
		  		if(result != null){
		  			
		  			if(result.getProperty(0).toString().equals("true")){
						Toast.makeText(this, "Registro exitoso ", Toast.LENGTH_LONG).show();
						finish();
					}
					if(result.getProperty(0).toString().equals("false")){
						Toast.makeText(this, "Error al registrar", Toast.LENGTH_LONG).show();
					}
		  		
		  		
		  		}else{
		  			Toast.makeText(getApplicationContext(), "No Response!", Toast.LENGTH_SHORT).show();
		  		}
		  		
		  		
		  		}catch (Exception e) {
		  			e.printStackTrace();
		  			Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_SHORT).show();
		  	  		
		  		}
			

		}

		
	}
	
	public void onCancelar(View boton){ 
	 	finish();
	}
}
