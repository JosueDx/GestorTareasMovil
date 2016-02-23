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
	static String URL = "http://server-jobtask.jl.serv.net.mx/Servicio_Tarea/services/funciones_servicio?wsdl";
	private String SOAP_ACTION="http://server-jobtask.jl.serv.net.mx/Servicio_Tarea/services/funciones_servicio/registrartarearealizada";
	private String METODO="registrartarearealizada";
	
	TextView textdescripcion, textfecha;
	EditText editdescripcion;
	Tarea tobj = new Tarea();
	
	String descripcion, idTarea;
	int id_personas,id_departamento;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tarea_realizada);
		inicializar();
		Intent intent = this.getIntent();
		idTarea = intent.getStringExtra("idTarea");
		descripcion = intent.getStringExtra("descripcion");
		cargar_tarea();
		
		id_personas=this.getIntent().getIntExtra("id_persona", 0);
		id_departamento=this.getIntent().getIntExtra("id_departamento", 0);
		
	}
	
	public void cargar_tarea(){

		textdescripcion.setText(descripcion);
	}
	
	void inicializar(){
		
		textdescripcion =  (TextView) findViewById(R.id.textViewTRDescripcion);
		textfecha =  (TextView) findViewById(R.id.textViewTRFecha);
		editdescripcion = (EditText) findViewById(R.id.editTextTRDescripcion);
		textfecha.setText(""+getDatePhone());
		
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
	    SimpleDateFormat df = new SimpleDateFormat("yyy-MM-dd");
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
				objRealizada.put("id_Tarea_realizada",0);
				objRealizada.put("id_tarea", idTarea);
				objRealizada.put("descipcion", editdescripcion.getText().toString());
				objRealizada.put("fecha_fin",textfecha.getText().toString()+" 23:50:00");
				objRealizada.put("estado","A");
				objRealizada.put("archivo_env","");
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			SoapObject request = new SoapObject(NAMESPACE, METODO);
			request.addProperty("request1" , objRealizada.toString());
		  	Log.e("objRealizado tarea", objRealizada.toString());
		  	  SoapSerializationEnvelope Envoltorio = new SoapSerializationEnvelope (SoapEnvelope.VER11);
		      Envoltorio.setOutputSoapObject (request);
		  	  
		  	  HttpTransportSE TransporteHttp = new HttpTransportSE(URL);
		  	
		  	try {
		  		TransporteHttp.call(SOAP_ACTION, Envoltorio);
		  	   
		  		SoapObject result = (SoapObject) Envoltorio.bodyIn;
		  		
		  		if(result != null){
		  			
		  			if(result.getProperty(0).toString().equals("1")){
						Toast.makeText(this, "Registro exitoso ", Toast.LENGTH_LONG).show();
			/*		
						//Intent intent= new Intent(TareaRealizada.this,BuscarTareaEmpleadoActivity.class);
					//	intent.putExtra("id_persona", id_personas);
						//intent.putExtra("id_departamento", id_departamento);
					//	startActivity(intent);
						//this.finish();
				*/		
				startActivity(new Intent(getBaseContext(), MainActivity.class)
		        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
			    finish();
						
						/*	//finish();
						Intent intent= new Intent(this,BuscarTareaEmpleadoActivity.class);
						intent.putExtra("id_persona", id_personas);
						intent.putExtra("id_departamento", id_departamento);
						Log.e("enviar", id_personas+" "+id_departamento);
						startActivity(intent);
						*/
						
					}
					if(result.getProperty(0).toString().equals("0")){
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
