package com.example.gestortareasapp;


import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.modelo.informacion.Datosusuarios;
import com.modelos.DbUsuarios;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class LoginActivity extends Activity {

	EditText edittextUser,edittextPassword;
	String nom;			
	Intent intent;	
	String NombreUsuario;
	String ClaveUsuario;
	Context contexto;
	
	static String NAMESPACE = "http://servicio.servicio.com";
	static String URL = "http://192.168.1.10:8080/Servicio_Tarea/services/funciones_servicio?wsdl";
	private String SOAP_ACTION="http://192.168.1.10:8080/Servicio_Tarea/services/funciones_servicio/login";
	private String METODO="login";
	
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 	
		setContentView(R.layout.activity_login);	
		
		if (android.os.Build.VERSION.SDK_INT > 9) {
		    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		    StrictMode.setThreadPolicy(policy);
		}
	}
	

	public void onSpinner(View v){
		Intent intent= new Intent(this,SpinnerActivity.class);
		startActivity(intent);
		
	}
	
	void inicializar(){
		edittextUser = (EditText) findViewById(R.id.EdittextUser);
		edittextPassword = (EditText) findViewById(R.id.EditttextPassword);
		}
	
	public void onLogin(View v){
		
		inicializar();
		String NombreUsuario = edittextUser.getText().toString();
		String ClaveUsuario= edittextPassword.getText().toString();
		//Toast.makeText(this, "aqui vamos", Toast.LENGTH_LONG).show();
		if(NombreUsuario.equals("") && ClaveUsuario.equals("")){
			Toast.makeText(this, "Faltan por ingresar campos", Toast.LENGTH_LONG).show();
			
		}
		else{
			
			
			
			SoapObject request = new SoapObject(NAMESPACE, METODO);
			request.addProperty("request1" , NombreUsuario);
		  	request.addProperty("request2" , ClaveUsuario);
		  	
		  	  SoapSerializationEnvelope Envoltorio = new SoapSerializationEnvelope (SoapEnvelope.VER11);
		      Envoltorio.setOutputSoapObject (request);
		  	  
		  	  HttpTransportSE TransporteHttp = new HttpTransportSE(URL);
		  	
		  	try {
		  		TransporteHttp.call(SOAP_ACTION, Envoltorio);
		  	   
		  		SoapObject result = (SoapObject) Envoltorio.bodyIn;
		  		
		  		if(result != null){
		  			Log.e("TIPO ACCION: 2-- ","opcion");
		  			Toast.makeText(getApplicationContext(), result.getProperty(0).toString(), Toast.LENGTH_SHORT).show();
		  			
		  			if(result.getProperty(0).toString().equals("jefe")){
						Toast.makeText(this, "Bienvenido Jefe ", Toast.LENGTH_LONG).show();
						intent= new Intent(this,MainActivity.class);
			    		System.out.println("Usuario");
			    		intent.putExtra("op", "1");
			    		startActivity(intent);
					}
					if(result.getProperty(0).toString().equals("empleado")){
						Toast.makeText(this, "Bienvenido Empleado", Toast.LENGTH_LONG).show();
						intent= new Intent(this,MainActivity.class);
						intent.putExtra("op", "2");
				    	System.out.println("Usuario");
			    		startActivity(intent);
					}
		  		
		  		
		  		}else{
		  			Toast.makeText(getApplicationContext(), "No Response!", Toast.LENGTH_SHORT).show();
		  		}
		  		
		  		
		  		}catch (Exception e) {
		  			e.printStackTrace();
		  			Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_SHORT).show();
		  	  		
		  		}
			

		}
		edittextUser.setText("");
		edittextPassword.setText("");
	}
	
	public void finalizar(){
		SoapObject request = new SoapObject(NAMESPACE, METODO);
		request.addProperty("request1", NombreUsuario);
		request.addProperty("request2", ClaveUsuario);
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
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
	
	public void onRegister(View boton){
	
		Intent intent= new Intent(this,RegisterActivity.class);
		startActivity(intent);
	}
	
	
}
