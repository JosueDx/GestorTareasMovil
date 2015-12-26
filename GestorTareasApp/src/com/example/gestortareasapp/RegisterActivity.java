package com.example.gestortareasapp;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity {

	Spinner spinnerDepartamento;
	EditText edittextNombre, edittextApellido, edittextCedula, edittextDreccion, 
	edittextEmail, edittextUsuario, edittextContraseña, edittextRepetirContrasenia;
	TextView textviewUsuario,textviewContrasenia,textviewCedula;
	
	static String NAMESPACE = "http://servicio.servicio.com";
	static String URL = "http://192.168.1.10:8080/Servicio_Tarea/services/funciones_servicio?wsdl";
	private String SOAP_ACTION="http://192.168.1.10:8080/Servicio_Tarea/services/funciones_servicio/registro";
	private String METODO="registro";
	
	private String SOAP_ACTION2="http://192.168.1.10:8080/Servicio_Tarea/services/funciones_servicio/id_departamento";
	private String METODO2="id_departamento";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		inicializar();
		
		
	}

	public void inicializar(){
		spinnerDepartamento= (Spinner) findViewById(R.id.RspinnerDepartamentos);
		spinnerDepartamento.setPrompt("Seleccione departamento");
				
		//Iniciaizar componentes 
		edittextNombre = (EditText) findViewById(R.id.Rnombre);
		edittextApellido = (EditText) findViewById(R.id.Rapellido);
		edittextCedula = (EditText) findViewById(R.id.Rcedula);
		edittextDreccion = (EditText) findViewById(R.id.Rdireccion);
		edittextEmail = (EditText) findViewById(R.id.Remail);
		edittextUsuario = (EditText) findViewById(R.id.Rusuario);
		edittextContraseña  = (EditText) findViewById(R.id.Rcontrasenia);
		edittextRepetirContrasenia= (EditText) findViewById(R.id.RConfirmarContrasenia);		
		textviewCedula = (TextView) findViewById(R.id.textViewValidarCedula);
		textviewContrasenia=(TextView) findViewById(R.id.textViewValidadContrasenia);
		textviewUsuario=(TextView) findViewById(R.id.textViewValidarUsuario);
		
		
		

		edittextRepetirContrasenia.addTextChangedListener(new TextWatcher() {

			   public void afterTextChanged(Editable s) {
			   }

			   public void beforeTextChanged(CharSequence s, int start, 
			     int count, int after) {
			   }

			   public void onTextChanged(CharSequence s, int start, 
			     int before, int count) {
				   
				   if(edittextContraseña.getText().toString().equals(edittextRepetirContrasenia.getText().toString())){
					   textviewContrasenia.setText(":D");
					
					
					}else{
						textviewContrasenia.setText("X");
						
					}
				   
			   }
			  });
		
		
		edittextCedula.addTextChangedListener(new TextWatcher() {

			   public void afterTextChanged(Editable s) {
			   }

			   public void beforeTextChanged(CharSequence s, int start, 
			     int count, int after) {
			   }

			   public void onTextChanged(CharSequence s, int start, 
			     int before, int count) {
				   //Envio campo cedula a la funcion
				   
				   String validacion = "";
				   
				   validacion = validacioncedula(edittextCedula.getText().toString());
				   
				   if(validacion.equals("false")){
					   textviewCedula.setText(":D");
					
					}else{
						textviewCedula.setText("X");
						
					}
				   
			   }
			  });
		
		edittextUsuario.addTextChangedListener(new TextWatcher() {

			   public void afterTextChanged(Editable s) {
			   }

			   public void beforeTextChanged(CharSequence s, int start, 
			     int count, int after) {
			   }

			   public void onTextChanged(CharSequence s, int start, 
			     int before, int count) {
				   //Envio campo usuario a la funcion
				   String validacion = "";
				   
				   validacion = validacionUsuario(edittextUsuario.getText().toString());
				   
				   if(validacion.equals("false")){
					   textviewUsuario.setText(":D");
					
					}else{
						textviewUsuario.setText("X");
						
					}		   
			   }
			  });
		
		
		
		
		
		String json = "['Financiero','Contabilidad','RRHH','Administracion','Sistemas']";
		
		//ArrayList<String> list = new ArrayList<String>();     
		List<String> list = new ArrayList<String>();
		try {
			JSONArray jsonArray = new JSONArray(json);
					
			for (int i=0; i<jsonArray.length(); i++) {
				//Toast.makeText(this, jsonArray.getString(i), Toast.LENGTH_LONG).show();
			    list.add( jsonArray.getString(i) );
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		// Application of the Array to the Spinner
		ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list);
		spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
		spinnerDepartamento.setAdapter(spinnerArrayAdapter);
		
		
	}
	
	public String validacioncedula(String cedula){
		String validacion="";
		
		String jsonCedula = "['2400085151','2400090201','0927511618','0987654321']";
		
		//ArrayList<String> list = new ArrayList<String>();     
		List<String> listCedula = new ArrayList<String>();
		try {
			JSONArray jsonArray = new JSONArray(jsonCedula);
					
			for (int i=0; i<jsonArray.length(); i++) {
				//Toast.makeText(this, jsonArray.getString(i), Toast.LENGTH_LONG).show();
				//listCedula.add( jsonArray.getString(i) );
				if(jsonArray.getString(i).equals(cedula)){
					validacion ="true";
					i = jsonArray.length();
				}
				else{
					if (edittextCedula.length()<10){
					 validacion ="true";
					}else{
					validacion ="false";
					}
				}
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return validacion;
	}
	
	//validar usuario
	public String validacionUsuario(String usuario){
		String validacion="";
		
		String jsonUsuario = "['JosueDx','Alvar1','Carlos1','Josue1']";
		
		//ArrayList<String> list = new ArrayList<String>();     
		List<String> listUsuario = new ArrayList<String>();
		try {
			JSONArray jsonArray = new JSONArray(jsonUsuario);
					
			for (int i=0; i<jsonArray.length(); i++) {
				//Toast.makeText(this, jsonArray.getString(i), Toast.LENGTH_LONG).show();
				//listCedula.add( jsonArray.getString(i) );
				if(jsonArray.getString(i).equals(usuario)){
					validacion ="true";
					i = jsonArray.length();
				}
				else{
					
					validacion ="false";
					
				}
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return validacion;
	}
	
	

public void OnRegistar(View v){
		
		inicializar();
		String Nombre = edittextNombre.getText().toString();
		String Apellido= edittextApellido.getText().toString();
		String Cedula= edittextCedula.getText().toString();
		String Direccion = edittextDreccion.getText().toString();
		String Email= edittextEmail.getText().toString();
		String Departamento= "";
		String Usuario = edittextUsuario.getText().toString();
		String Contraseña= edittextContraseña.getText().toString();
		String confirmarcontrasenia = edittextRepetirContrasenia.getText().toString();
		
		
		//Toast.makeText(this, "aqui vamos Carlos", Toast.LENGTH_LONG).show();
		if(Nombre.equals("") && Apellido.equals("") && Cedula.equals("")
				&& Direccion.equals("") && Email.equals("") && Usuario.equals("") 
				&& Contraseña.equals("")){
			
			Toast.makeText(this, "Faltan ingresar campos", Toast.LENGTH_LONG).show();
			
		}
		else {
			
			JSONObject objPersona=new JSONObject();
			try {
				objPersona.put("Nombre",Nombre);
				objPersona.put("Departamento", id_Departamento(spinnerDepartamento.getSelectedItem().toString()));
				objPersona.put("Apellido",Apellido);
				objPersona.put("Cedula",Cedula);
				objPersona.put("Direccion",Direccion);
				objPersona.put("Email",Email);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			JSONObject objusuario=new JSONObject();
			try {
				objusuario.put("Tipo_Usuario",3);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			JSONObject objdatosusuario=new JSONObject();
			try {
				objdatosusuario.put("Usuario",Usuario);
				objdatosusuario.put("Contraseña",Contraseña);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			SoapObject request = new SoapObject(NAMESPACE, METODO);
			request.addProperty("request1" , objPersona);
		  	request.addProperty("request2" , objusuario);
		  	request.addProperty("request3" , objdatosusuario);
		  	
		  	  SoapSerializationEnvelope Envoltorio = new SoapSerializationEnvelope (SoapEnvelope.VER11);
		      Envoltorio.setOutputSoapObject (request);
		  	  
		  	  HttpTransportSE TransporteHttp = new HttpTransportSE(URL);
		  	
		  	try {
		  		TransporteHttp.call(SOAP_ACTION, Envoltorio);
		  	   
		  		SoapObject result = (SoapObject) Envoltorio.bodyIn;
		  		
		  		if(result != null){
		  			
		  			if(result.getProperty(0).toString().equals("0")){
						Toast.makeText(this, "Usuario Agregado Correctamente ", Toast.LENGTH_LONG).show();
						
					}
					if(result.getProperty(0).toString().equals("1")){
						Toast.makeText(this, "Error al registrar", Toast.LENGTH_LONG).show();
						
					}
					if(result.getProperty(0).toString().equals("2")){
						Toast.makeText(this, "Error al registrar", Toast.LENGTH_LONG).show();
						
					}
		  		
		  		
		  		}else{
		  			Toast.makeText(getApplicationContext(), "No Response!", Toast.LENGTH_SHORT).show();
		  		}
		  		
		  		
		  		}catch (Exception e) {
		  			e.printStackTrace();
		  			Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_SHORT).show();
		  	  		
		  		}

			
			
			Toast.makeText(this, "Estamos en Mantenimientoo", Toast.LENGTH_LONG).show();
		}
	}
	
	public String id_Departamento(String departamento){
		SoapObject request = new SoapObject(NAMESPACE, METODO2);
		request.addProperty("request1" , departamento);
	  	
	  	  SoapSerializationEnvelope Envoltorio = new SoapSerializationEnvelope (SoapEnvelope.VER11);
	      Envoltorio.setOutputSoapObject (request);
	  	  
	  	  HttpTransportSE TransporteHttp = new HttpTransportSE(URL);
	  	
	  	try {
	  		TransporteHttp.call(SOAP_ACTION2, Envoltorio);
	  	   
	  		SoapObject result = (SoapObject) Envoltorio.bodyIn;
	  		
	  		if(result != null){
	  			
	  			return result.getProperty(0).toString();
	  			
	  		}else{
	  			Toast.makeText(getApplicationContext(), "No Response!", Toast.LENGTH_SHORT).show();
	  			return "";
	  		}
	  		}catch (Exception e) {
	  			e.printStackTrace();
	  			Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_SHORT).show();
	  	  		return "";
	  		}
		
	  }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
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
