package com.example.gestortareasapp;


import org.json.JSONObject;
import org.json.JSONTokener;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.modelo.informacion.Persona;
import com.modelo.informacion.Usuarios;
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
	Persona objPersona;
	Usuarios objUsuario;
	
	
	
	static String NAMESPACE  ="http://servicio.servicio.com";
	static String URL = "http://server-jobtask.jl.serv.net.mx/Servicio_Tarea/services/funciones_servicio?wsdl";
	private String SOAP_ACTION="http://server-jobtask.jl.serv.net.mx/Servicio_Tarea/services/funciones_servicio/login";
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
		  	  
		  	  HttpTransportSE TransporteHttp = new HttpTransportSE(URL,12000);
		  	
		  	try {
		  		TransporteHttp.call(SOAP_ACTION, Envoltorio);
		  	   
		  		SoapObject result = (SoapObject) Envoltorio.bodyIn;
		  		
		  		if(result != null){
		  			Log.e("TIPO ACCION: 2-- ","opcion");
		  		//	Toast.makeText(getApplicationContext(), result.getProperty(0).toString(), Toast.LENGTH_SHORT).show();
		  			
		  			JSONObject jsondatos = new JSONObject(result.getProperty(0).toString());
					//personas
					JSONTokener jsontokenerpersonas = new JSONTokener(jsondatos.get("persona").toString());
					JSONObject jsonpersonas = (JSONObject)jsontokenerpersonas.nextValue();
					objPersona =  new Persona();
					objPersona.setDireccion( jsonpersonas.getString("direccion"));
					objPersona.setNombre(jsonpersonas.getString("nombres"));
					objPersona.setApellido(jsonpersonas.getString("apellidos"));
					objPersona.setEmail(jsonpersonas.getString("email"));
					objPersona.setDepartamento(jsonpersonas.getInt("id_departamento"));
					objPersona.setCedula(jsonpersonas.getString("cedula"));
					objPersona.setIdPersona(jsonpersonas.getInt("id_persona"));
					
					//datosusuarios
					JSONTokener jsontokenerdatosusuario = new JSONTokener(jsondatos.get("usuario").toString());
					JSONObject jsondatosusuario = (JSONObject)jsontokenerdatosusuario.nextValue();
					objUsuario = new Usuarios();
					objUsuario.setId_tipousuario(jsondatosusuario.getInt("id_tipousuario"));
				
		  				  			
		  			if(objUsuario.getId_tipousuario()==2){
						Toast.makeText(this, "Bienvenido Jefe ", Toast.LENGTH_LONG).show();
						intent= new Intent(this,MainActivity.class);
			    		System.out.println("Usuario");
			    		intent.putExtra("op", "1");
			    		intent.putExtra("id_persona", objPersona.getIdPersona());
			    		intent.putExtra("id_departamento", objPersona.getDepartamento());
			    		intent.putExtra("nombre_persona", objPersona.getNombre() +" "+ objPersona.getApellido());
			    		startActivity(intent);
					}
					if(objUsuario.getId_tipousuario()==3){
						Toast.makeText(this, "Bienvenido Empleado", Toast.LENGTH_LONG).show();
						intent= new Intent(this,MainActivity.class);
						intent.putExtra("op", "2");
			    		intent.putExtra("id_persona", objPersona.getIdPersona());
			    		intent.putExtra("id_departamento", objPersona.getDepartamento());
			    		intent.putExtra("nombre_persona", objPersona.getNombre() +" "+ objPersona.getApellido());
			    		startActivity(intent);
					}
		  		
		  		}else{	
		  			Toast.makeText(getApplicationContext(), "No Response!", Toast.LENGTH_SHORT).show();
		  		}
		  		
		  		}catch (Exception e) {
		  			e.printStackTrace();
		  			//Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_SHORT).show();
		  			Toast.makeText(this, "Datos no coinciden con la BD", Toast.LENGTH_LONG).show();
		  	  		
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
