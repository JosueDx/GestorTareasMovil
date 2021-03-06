package com.example.gestortareasapp;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;


import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity {

	Spinner spinnerDepartamento;
	EditText edittextNombre, edittextApellido, edittextCedula, edittextDreccion, 
	edittextEmail, edittextUsuario, edittextContraseña, edittextRepetirContrasenia;
	TextView textviewUsuario,textviewContrasenia,textviewCedula;
	ImageView imageVisto,imageError,imagev2,imagee2,imagev3,imagee3;
	/*
	static String NAMESPACE = "http://servicio.servicio.com";
	static String URL = "http://aplicacionweb.jelastic.servint.net/nuevo/funciones_servicio?wsdl";
	private String SOAP_ACTION="http://aplicacionweb.jelastic.servint.net/nuevo/funciones_servicio/registrar_usuario";
	private String METODO="registrar_usuario";
	
	private String SOAP_ACTION2="http://aplicacionweb.jelastic.servint.net/nuevo/funciones_servicio/id_departamento";
	private String METODO2="id_departamento";
	
	private String SOAP_ACTION3="http://aplicacionweb.jelastic.servint.net/nuevo/funciones_servicio/validar_cedula";
	private String METODO3="validar_cedula";
	
	private String SOAP_ACTION4="http://aplicacionweb.jelastic.servint.net/nuevo/funciones_servicio/validar_usuario";
	private String METODO4="validar_usuario";
	
	private String SOAP_ACTION5="http://aplicacionweb.jelastic.servint.net/nuevo/funciones_servicio/lista_departamento";
	private String METODO5="lista_departamento";
	*/
	
	static String NAMESPACE = "http://servicio.servicio.com";
	static String URL = "http://server-jobtask.jl.serv.net.mx/Servicio_Tarea/services/funciones_servicio?wsdl";
	private String SOAP_ACTION="http://server-jobtask.jl.serv.net.mx/Servicio_Tarea/services/funciones_servicio/registrar_usuario";
	private String METODO="registrar_usuario";
	
	private String SOAP_ACTION2="http://server-jobtask.jl.serv.net.mx/Servicio_Tarea/services/funciones_servicio/id_departamento";
	private String METODO2="id_departamento";
	
	private String SOAP_ACTION3="http://server-jobtask.jl.serv.net.mx/Servicio_Tarea/services/funciones_servicio/validar_cedula";
	private String METODO3="validar_cedula";
	
	private String SOAP_ACTION4="http://server-jobtask.jl.serv.net.mx/Servicio_Tarea/services/funciones_servicio/validar_usuario";
	private String METODO4="validar_usuario";
	
	private String SOAP_ACTION5="http://server-jobtask.jl.serv.net.mx/Servicio_Tarea/sservices/funciones_servicio/lista_departamento";
	private String METODO5="lista_departamento";
	
	/*
	static String NAMESPACE = "http://servicio.servicio.com";
	static String URL = "http://192.168.71.53:8080/Servicio_Tarea/services/funciones_servicio?wsdl";
	private String SOAP_ACTION="http://192.168.71.53:8080/Servicio_Tarea/services/funciones_servicio/registrar_usuario";
	private String METODO="registrar_usuario";
	
	private String SOAP_ACTION2="http://192.168.71.53:8080/Servicio_Tarea/services/funciones_servicio/id_departamento";
	private String METODO2="id_departamento";
	
	private String SOAP_ACTION3="http://192.168.71.53:8080/Servicio_Tarea/services/funciones_servicio/validar_cedula";
	private String METODO3="validar_cedula";
	
	private String SOAP_ACTION4="http://192.168.71.53:8080/Servicio_Tarea/services/funciones_servicio/validar_usuario";
	private String METODO4="validar_usuario";
	
	private String SOAP_ACTION5="http://192.168.71.53:8080/Servicio_Tarea/services/funciones_servicio/lista_departamento";
	private String METODO5="lista_departamento";
	*/
	int val1,val2,val3;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		inicializar();
		imageVisto.setVisibility(View.INVISIBLE);
		imagev2.setVisibility(View.INVISIBLE);
		imagev3.setVisibility(View.INVISIBLE);
		
		
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
		imageVisto=(ImageView) findViewById(R.id.imageViewVisto);
		imageError=(ImageView) findViewById(R.id.imageViewE1);
		imagev2=(ImageView) findViewById(R.id.ImageViewV2);
		imagee2=(ImageView) findViewById(R.id.ImageViewE2);
		imagev3=(ImageView) findViewById(R.id.ImageViewV3);
		imagee3=(ImageView) findViewById(R.id.ImageViewE3);
		
		edittextRepetirContrasenia.addTextChangedListener(new TextWatcher() {

			   public void afterTextChanged(Editable s) {
			   }

			   public void beforeTextChanged(CharSequence s, int start, 
			     int count, int after) {
			   }

			   public void onTextChanged(CharSequence s, int start, 
			     int before, int count) {
				   
				   if(edittextContraseña.getText().toString().equals(edittextRepetirContrasenia.getText().toString())){
					  // textviewContrasenia.setText(":D");
					   imagee3.setVisibility(View.INVISIBLE);
						imagev3.setVisibility(View.VISIBLE);
						
					
					}else{
						//textviewContrasenia.setText("X");
						 imagee3.setVisibility(View.VISIBLE);
							imagev3.setVisibility(View.INVISIBLE);
							
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
					  // textviewCedula.setText(":D");
					  imageError.setVisibility(View.INVISIBLE);
					  imageVisto.setVisibility(View.VISIBLE);
					}else{
						//textviewCedula.setText("X");
						 imageVisto.setVisibility(View.INVISIBLE);
						  imageError.setVisibility(View.VISIBLE);

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
					   //textviewUsuario.setText(":D");
					    imagee2.setVisibility(View.INVISIBLE);
						imagev2.setVisibility(View.VISIBLE);
								 
					}else{
						//textviewUsuario.setText("X");
						imagev2.setVisibility(View.INVISIBLE);
						imagee2.setVisibility(View.VISIBLE);

					}		   
			   }
			  });
		
		
		
		SoapObject request = new SoapObject(NAMESPACE, METODO5);
	  	
	    SoapSerializationEnvelope Envoltorio = new SoapSerializationEnvelope (SoapEnvelope.VER11);
	    Envoltorio.setOutputSoapObject (request);
	  	  
	  	HttpTransportSE TransporteHttp = new HttpTransportSE(URL);
	  	List<String> list = new ArrayList<String>();
	  	try {
	  		TransporteHttp.call(SOAP_ACTION5, Envoltorio);
	  	   
	  		SoapObject result = (SoapObject) Envoltorio.bodyIn;
	  		
	  		if(result != null){
	  			Log.e("TIPO ACCION: 2-- ","opcion");
	  		//	Toast.makeText(getApplicationContext(), result.getProperty(0).toString(), Toast.LENGTH_SHORT).show();
	  			
	  			JSONObject jsondatos = new JSONObject(result.getProperty(0).toString());
	  			JSONArray arr = jsondatos.getJSONArray("departamentos");
	  			for (int i = 0; i < arr.length(); i++)
	  			{
	  			  String departamentoNombre = arr.getJSONObject(i).getString("nombre");
	  			  list.add(departamentoNombre);
	  			}
	  			
	  			
	  		}else{
	  			Toast.makeText(getApplicationContext(), "No Response!", Toast.LENGTH_SHORT).show();
	  		}
	  		
	  		
	  		}catch (Exception e) {
	  			e.printStackTrace();
	  			Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_SHORT).show();
	  	  		
	  		}

		// Application of the Array to the Spinner
		ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list);
		spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
		spinnerDepartamento.setAdapter(spinnerArrayAdapter);
		
		
	}
	
	public void OnCancelar(View v){
		finish();
	}
	
	public String validacioncedula(String cedula){
		String validacion="";
		
		
		SoapObject request = new SoapObject(NAMESPACE, METODO3);
		request.addProperty("request1" , cedula);
	  	
	  	  SoapSerializationEnvelope Envoltorio = new SoapSerializationEnvelope (SoapEnvelope.VER11);
	      Envoltorio.setOutputSoapObject (request);
	  	  
	  	  HttpTransportSE TransporteHttp = new HttpTransportSE(URL);
	  	
	  	try {
	  		TransporteHttp.call(SOAP_ACTION3, Envoltorio);
	  	   
	  		SoapObject result = (SoapObject) Envoltorio.bodyIn;
	  		
	  		if(result != null){
	  			Log.e("TIPO ACCION: 2-- ","opcion");
	  		//	Toast.makeText(getApplicationContext(), result.getProperty(0).toString(), Toast.LENGTH_SHORT).show();
	  			
	  			if(result.getProperty(0).toString().equals("Si")){
					validacion ="true";
				}
				else{
					if (edittextCedula.length()<10){
					 validacion ="true";
					}else{
						if(validadorDeCedula(edittextCedula.getText().toString()) == false){
						    validacion ="true";
						}else{
							validacion ="false";
						}
					}
				}
	  			
	  		}else{
	  			Toast.makeText(getApplicationContext(), "No Response!", Toast.LENGTH_SHORT).show();
	  		}
	  		
	  		
	  		}catch (Exception e) {
	  			e.printStackTrace();
	  			Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_SHORT).show();
	  	  		
	  		}


		return validacion;
	}
	
	//validar usuario
	public String validacionUsuario(String usuario){
		String validacion="";
		//Toast.makeText(this, ""+id_Departamento(spinnerDepartamento.getSelectedItem().toString()), Toast.LENGTH_LONG).show();
		
		SoapObject request = new SoapObject(NAMESPACE, METODO4);
		request.addProperty("request1" , usuario);
	  	
	  	  SoapSerializationEnvelope Envoltorio = new SoapSerializationEnvelope (SoapEnvelope.VER11);
	      Envoltorio.setOutputSoapObject (request);
	  	  
	  	  HttpTransportSE TransporteHttp = new HttpTransportSE(URL);
	  	
	  	try {
	  		TransporteHttp.call(SOAP_ACTION4, Envoltorio);
	  	   
	  		SoapObject result = (SoapObject) Envoltorio.bodyIn;
	  		
	  		if(result != null){
	  			Log.e("TIPO ACCION: 2-- ","opcion");
	  		//	Toast.makeText(getApplicationContext(), result.getProperty(0).toString(), Toast.LENGTH_SHORT).show();
	  			
	  			if(result.getProperty(0).toString().equals("Si")){
					validacion ="true";
				}
				else{
					if (edittextUsuario.length()<4){
						 validacion ="true";
						 val1=0;
						}else{
						validacion ="false";
						val1=1;
						}
				}
	  			
	  		
	  		}else{
	  			Toast.makeText(getApplicationContext(), "No Response!", Toast.LENGTH_SHORT).show();
	  		}
	  		
	  		
	  		}catch (Exception e) {
	  			e.printStackTrace();
	  			Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_SHORT).show();
	  	  		
	  		}
		
		
		return validacion;
	}
	
	

	public void OnRegistar(View v){
		//inicializar();
		String Nombre = edittextNombre.getText().toString();
		String Apellido= edittextApellido.getText().toString();
		String Cedula= edittextCedula.getText().toString();
		String Direccion = edittextDreccion.getText().toString();
		String Email= edittextEmail.getText().toString();
		String Usuario = edittextUsuario.getText().toString();
		String Contraseña= edittextContraseña.getText().toString();
		String confirmarcontrasenia = edittextRepetirContrasenia.getText().toString();
		
		
		//Toast.makeText(this, "aqui vamos Carlos", Toast.LENGTH_LONG).show();
		if(Apellido.equals("") || Cedula.equals("") && Direccion.equals("") 
				|| Email.equals("") || Usuario.equals("") 
				&& Contraseña.equals("") || confirmarcontrasenia.equals("") || Nombre.equals("")){
			
			Toast.makeText(this, "Faltan por ingresar campos", Toast.LENGTH_LONG).show();
			
		}
		else {
			
			JSONObject objPersona=new JSONObject();
			try {
				objPersona.put("nombres",Nombre);
				objPersona.put("id_departamento", id_Departamento(spinnerDepartamento.getSelectedItem().toString()));
				objPersona.put("apellidos",Apellido);
				objPersona.put("cedula",Cedula);
				objPersona.put("direccion",Direccion);
				objPersona.put("email",Email);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			JSONObject objdatosusuario=new JSONObject();
			try {
				objdatosusuario.put("usuario",Usuario);
				objdatosusuario.put("contraseña",Contraseña);
				objdatosusuario.put("id_tipousuario",3);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			JSONObject jsonobject = new JSONObject();
			try {
				jsonobject.put("personas", objPersona);
				jsonobject.put("datosusuarios", objdatosusuario);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			SoapObject request = new SoapObject(NAMESPACE, METODO);
			request.addProperty("request1" , jsonobject.toString());
		  	
		  	  SoapSerializationEnvelope Envoltorio = new SoapSerializationEnvelope (SoapEnvelope.VER11);
		      Envoltorio.setOutputSoapObject (request);
		  	  
		  	  HttpTransportSE TransporteHttp = new HttpTransportSE(URL);
		  	
		  	try {
		  		TransporteHttp.call(SOAP_ACTION, Envoltorio);
		  	   
		  		SoapObject result = (SoapObject) Envoltorio.bodyIn;
		  		
		  		if(result != null){
		  			
		  			if(result.getProperty(0).toString().equals("1")){
						Toast.makeText(this, "Usuario Agregado Correctamente ", Toast.LENGTH_LONG).show();
						
					}
					if(result.getProperty(0).toString().equals("2")){
						Toast.makeText(this, "Error al registrar", Toast.LENGTH_LONG).show();
						
					}
					if(result.getProperty(0).toString().equals("0")){
						Toast.makeText(this, "Error al registrar", Toast.LENGTH_LONG).show();
						
					}
		  		
		  		
		  		}else{
		  			Toast.makeText(this, "No conexion a Internet", Toast.LENGTH_SHORT).show();
		  		}
		  		
		  		
		  		}catch (Exception e) {
		  			e.printStackTrace();
		  			Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_SHORT).show();
		  	  		
		  		}

			finish();
		}
		}
		
		
	
	public String id_Departamento(String departamento){
		
		JSONObject pruebadepartamento = new JSONObject();
		try {
			pruebadepartamento.put("descripcion", departamento);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		SoapObject request = new SoapObject(NAMESPACE, METODO2);
		request.addProperty("request1" , pruebadepartamento.toString());
	  	
	  	  SoapSerializationEnvelope Envoltorio = new SoapSerializationEnvelope (SoapEnvelope.VER11);
	      Envoltorio.setOutputSoapObject (request);
	  	  
	  	  HttpTransportSE TransporteHttp = new HttpTransportSE(URL);
	  	
	  	try {
	  		TransporteHttp.call(SOAP_ACTION2, Envoltorio);
	  	   
	  		SoapObject result = (SoapObject) Envoltorio.bodyIn;
	  		
	  		if(result != null){
	  			Log.e("yo ando x aki", "sdfsd");
	  			JSONObject jsondatos = new JSONObject(result.getProperty(0).toString());
	  				  			
	  			return ""+jsondatos.getString("id_tipodepartamento");
	  			//return result.getProperty(0).toString();
	  			
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
	
	public boolean validadorDeCedula(String cedula) {
		boolean cedulaCorrecta = false;
		 
		try {
		 
		if (cedula.length() == 10) // ConstantesApp.LongitudCedula
		{
		int tercerDigito = Integer.parseInt(cedula.substring(2, 3));
		if (tercerDigito < 6) {
		// Coeficientes de validación cédula
		// El decimo digito se lo considera dígito verificador
		 int[] coefValCedula = { 2, 1, 2, 1, 2, 1, 2, 1, 2 };
		 int verificador = Integer.parseInt(cedula.substring(9,10));
		 int suma = 0;
		 int digito = 0;
		for (int i = 0; i < (cedula.length() - 1); i++) {
		 digito = Integer.parseInt(cedula.substring(i, i + 1))* coefValCedula[i];
		 suma += ((digito % 10) + (digito / 10));
		}
		 
		if ((suma % 10 == 0) && (suma % 10 == verificador)) {
		 cedulaCorrecta = true;
		}
		else if ((10 - (suma % 10)) == verificador) {
		 cedulaCorrecta = true;
		} else {
		 cedulaCorrecta = false;
		}
		} else {
		cedulaCorrecta = false;
		}
		} else {
		cedulaCorrecta = false;
		}
		} catch (NumberFormatException nfe) {
		cedulaCorrecta = false;
		} catch (Exception err) {
		System.out.println("Una excepcion ocurrio en el proceso de validadcion");
		cedulaCorrecta = false;
		}
		 
		if (!cedulaCorrecta) {
		System.out.println("La Cédula ingresada es Incorrecta");
		}
		return cedulaCorrecta;
		}
	
}
