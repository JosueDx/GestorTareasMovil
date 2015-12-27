package com.example.gestortareasapp;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.modelo.informacion.Tarea;
import com.modelos.DbUsuarios;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class NuevaTarea extends Activity {
	
	    private EditText edittextDescripcion,edittextComentario;
	    private Spinner empleadoSpinner, nivelSpinner;
	    private Button btnFechaInicio,btnFechaFin,btnGuardar;    
	    private int añoIni,añoFin;    
	    private int mesIni,mesFin;    
	    private int diaIni,diaFin;    
	    String fechaini, fechafin,descripcion,comentario;
	    String cmes1="",cmes2="",cdia1="",cdia2="";
	    String opcion;
     static final int DATE_DIALOG_ID = 0;
	    static final int DATE_DIALOG_ID2 = 1;
	    
	    
	    static String NAMESPACE = "http://servicio.servicio.com";
		static String URL = "http://192.168.1.10:8080/Servicio_Tarea/services/funciones_servicio?wsdl";
		private String SOAP_ACTION="http://192.168.1.10:8080/Servicio_Tarea/services/funciones_servicio/registro_tarea";
		private String METODO="registro_tarea";
		
		private String SOAP_ACTION2="http://192.168.1.10:8080/Servicio_Tarea/services/funciones_servicio/id_empleado";
		private String METODO2="id_empleado";
		
		private String SOAP_ACTION3="http://192.168.1.10:8080/Servicio_Tarea/services/funciones_servicio/id_nivel_tarea";
		private String METODO3="id_nivel_tarea";
	    

	 // la devolución de llamada recibida cuando el usuario "fija " la fecha en el diálogo 
	    private DatePickerDialog.OnDateSetListener mDateSetListener =            
	    	new DatePickerDialog.OnDateSetListener() {                
	    	public void onDateSet(DatePicker view, int year,                                       
	    			int monthOfYear, int dayOfMonth) {                    
	    		añoIni = year;                    
	    		mesIni = monthOfYear +1;                    
	    		diaIni = dayOfMonth;
	    		if(mesIni<10){
	    			cmes1="0";
	    		}else{cmes1="";}
	    			
	    		if(diaIni<10){
	    			cdia1="0";
	    		}else{cdia1="";}
	    		
	    		fechaini= añoIni+"-"+cmes1+ mesIni +"-"+cdia1+diaIni;	    		
	    		updateTextViewFechaIni();                
	    		}            
	    	};
	    	
	    	
	    private DatePickerDialog.OnDateSetListener mDateSetListener2 =            
	    	new DatePickerDialog.OnDateSetListener() {                
	       	public void onDateSet(DatePicker view, int year,                                       
	    	    	int monthOfYear, int dayOfMonth) {                    
	    	  	añoFin = year;                    
	    	  	mesFin = monthOfYear + 1;                    
	    	    diaFin = dayOfMonth;
	    	    if(mesFin<10){
	    			cmes2="0";
	    		}else{cmes2="";}
	    		if(diaFin<10){
	    			cdia2="0";
	    		}else{cdia2="";}
	    		
	    	    fechafin= añoFin+"-"+cmes2+mesFin +"-"+cdia2+diaFin;
	    	    updateTextViewFechafin();                
	    	    }            
	    	};
	/////////////////////
	    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nueva_tarea);
		inicializar();
		fechaIni();
		fechaFin();
		
		 Intent intent = this.getIntent();
		opcion= intent.getStringExtra("opc");
		
//		Intent intent=null;	
		int opcionInt= Integer.parseInt(opcion);
		
		
		
		if(opcionInt==2){
			String idTarea = intent.getStringExtra("idTarea");
			
			Tarea tobj = new Tarea();
			DbUsuarios bd =new DbUsuarios();
			tobj = bd.tareabuscar(idTarea);
			
			edittextComentario.setText(tobj.getComentario());
			edittextDescripcion.setText(tobj.getDescripcion());
			btnFechaInicio.setText("");
			btnFechaFin.setText("");
			empleadoSpinner.setSelection(-1);
			if(tobj.getNivel_tarea().equals("bajo")){
				nivelSpinner.setSelection(2);
			}else if(tobj.getNivel_tarea().equals("medio")){
				nivelSpinner.setSelection(1);
			}else{
				nivelSpinner.setSelection(0);
			}
						
			edittextComentario.setEnabled(false);
			edittextDescripcion.setEnabled(false);
			btnFechaInicio.setEnabled(false);
			btnFechaFin.setEnabled(false);
			empleadoSpinner.setEnabled(false);
			nivelSpinner.setEnabled(false);
			
			btnGuardar.setVisibility(View.INVISIBLE);
			
		}else{
			if(opcionInt==3){
				String idTarea = intent.getStringExtra("idTarea");
				
				edittextComentario.setText("manual tecnico");
				edittextDescripcion.setText("");
				btnFechaInicio.setText("");
				btnFechaFin.setText("");
				empleadoSpinner.setSelection(-1);
				nivelSpinner.setSelection(-1);
				btnGuardar.setText("GUARDAR");
			}
			else{
				if(opcionInt==4){
					String idTarea = intent.getStringExtra("idTarea");
					
					edittextComentario.setText("manual operativo");
					edittextDescripcion.setText("");
					btnFechaInicio.setText("");
					btnFechaFin.setText("");
					empleadoSpinner.setSelection(-1);
					nivelSpinner.setSelection(-1);
					
					edittextComentario.setEnabled(false);
					edittextDescripcion.setEnabled(false);
					btnFechaInicio.setEnabled(false);
					btnFechaFin.setEnabled(false);
					empleadoSpinner.setEnabled(false);
					nivelSpinner.setEnabled(false);
					btnGuardar.setText("ELIMINAR");
				}
				else{
					btnGuardar.setText("Guardar");
					
				}
				
			}
		}
		
		
	}

	public void inicializar(){
		btnFechaInicio=(Button) findViewById(R.id.buttonCrearTarea);
		btnFechaFin=(Button) findViewById(R.id.buttonFechaFin);
		edittextComentario=(EditText) findViewById(R.id.editTextComentario);
		edittextDescripcion=(EditText) findViewById(R.id.editTextDescripcion);
		empleadoSpinner=(Spinner) findViewById(R.id.spinnerPersona);
		nivelSpinner=(Spinner) findViewById(R.id.spinnerTipoTarea);
		btnGuardar=(Button) findViewById(R.id.buttonBuscarTarea);
		
		String json = "['Karen Bonilla','Josue Aquino','Carlos Torres','Leam Lee']";
		
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
		empleadoSpinner.setAdapter(spinnerArrayAdapter);
		
		
		String json2 = "['Alto','Medio','Bajo']";
		
		//ArrayList<String> list = new ArrayList<String>();     
		List<String> list2 = new ArrayList<String>();
		try {
			JSONArray jsonArray = new JSONArray(json2);
					
			for (int i=0; i<jsonArray.length(); i++) {
				//Toast.makeText(this, jsonArray.getString(i), Toast.LENGTH_LONG).show();
			    list2.add( jsonArray.getString(i) );
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		// Application of the Array to the Spinner
		ArrayAdapter<String> spinnerArrayAdapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list2);
		spinnerArrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
		nivelSpinner.setAdapter(spinnerArrayAdapter2);

		
	}
	
	public void onGuardar(View boton){
		 comentario = edittextComentario.getText().toString();
		 descripcion= edittextDescripcion.getText().toString();
		
		if(descripcion.equals("") || comentario.equals("")){
			Toast.makeText(this, "Faltan por ingresar campos", Toast.LENGTH_LONG).show();
			
		}else{
			
			
			JSONObject objPersona=new JSONObject();
			try {
				objPersona.put("Descripcion",descripcion);
				objPersona.put("Comentario",comentario);
				objPersona.put("Fecha_inicio",fechaini);
				objPersona.put("Fecha_fin",fechafin);
				objPersona.put("Empleado",id_Empleado(empleadoSpinner.getSelectedItem().toString()));
				objPersona.put("Nivel_tarea",id_NivelTarea(nivelSpinner.getSelectedItem().toString()));
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			SoapObject request = new SoapObject(NAMESPACE, METODO);
			request.addProperty("request1" , objPersona);
		  	
		  	  SoapSerializationEnvelope Envoltorio = new SoapSerializationEnvelope (SoapEnvelope.VER11);
		      Envoltorio.setOutputSoapObject (request);
		  	  
		  	  HttpTransportSE TransporteHttp = new HttpTransportSE(URL);
		  	
		  	try {
		  		TransporteHttp.call(SOAP_ACTION, Envoltorio);
		  	   
		  		SoapObject result = (SoapObject) Envoltorio.bodyIn;
		  		
		  		if(result != null){
		  			
		  			if(result.getProperty(0).toString().equals("0")){
						Toast.makeText(this, "Tarea Agregado Correctamente ", Toast.LENGTH_LONG).show();
						
					}
					if(result.getProperty(0).toString().equals("1")){
						Toast.makeText(this, "Error al crear tarea", Toast.LENGTH_LONG).show();
						
					}
					if(result.getProperty(0).toString().equals("2")){
						Toast.makeText(this, "Error al crear tarea", Toast.LENGTH_LONG).show();
						
					}
		  		
		  		
		  		}else{
		  			Toast.makeText(getApplicationContext(), "No Response!", Toast.LENGTH_SHORT).show();
		  		}
		  		
		  		
		  		}catch (Exception e) {
		  			e.printStackTrace();
		  			Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_SHORT).show();
		  	  		
		  		}

			
			
			
			
			
			Toast.makeText(this, "Tarea asignada correctmente", Toast.LENGTH_LONG).show();
			finish();
		}		
		}
	
	
	public String id_Empleado(String empleado){
		SoapObject request = new SoapObject(NAMESPACE, METODO2);
		request.addProperty("request1" , empleado);
	  	
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
	
	
	public String id_NivelTarea(String nivel_tarea){
		SoapObject request = new SoapObject(NAMESPACE, METODO3);
		request.addProperty("request1" , nivel_tarea);
	  	
	  	  SoapSerializationEnvelope Envoltorio = new SoapSerializationEnvelope (SoapEnvelope.VER11);
	      Envoltorio.setOutputSoapObject (request);
	  	  
	  	  HttpTransportSE TransporteHttp = new HttpTransportSE(URL);
	  	
	  	try {
	  		TransporteHttp.call(SOAP_ACTION3, Envoltorio);
	  	   
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
	
	public void onCancelar(View boton){
	finish();	
	}
	
	
	// updates the date in the TextView    
    private void updateTextViewFechaIni() {        
    	String mes= null;
    	if(mesIni== 1){
    		mes="Enero";
    	}else if (mesIni==2) {
    		mes="Febrero";
		}else if (mesIni==3) {
    		mes="Marzo";
		}else if (mesIni==4) {
    		mes="Abril";
		}else if (mesIni==5) {
    		mes="Mayo";
		}else if (mesIni==6) {
    		mes="Junio";
		}else if (mesIni==7) {
    		mes="Julio";
		}else if (mesIni==8) {
    		mes="Agosto";
		}else if (mesIni==9) {
    		mes="Septiembre";
		}else if (mesIni==10) {
    		mes="Octubre";
		}else if (mesIni==11) {
    		mes="Noviembre";
		}else if (mesIni==12) {
    		mes="Dciembre";
		}
    	
    	
    	btnFechaInicio.setText(            
    			new StringBuilder()                    
    			// Month is 0 based so add 1   
    			.append(diaIni ).append(" de ")
    			.append(mes ).append(" del ")
    			.append(añoIni).append(" ")		                    
    			);    
    }
    
    
    private void updateTextViewFechafin() {
    	
    	String mes=null;
    	if(mesFin== 1){
    		mes="Enero";
    	}else if (mesFin==2) {
    		mes="Febrero";
		}else if (mesFin==3) {
    		mes="Marzo";
		}else if (mesFin==4) {
    		mes="Abril";
		}else if (mesFin==5) {
    		mes="Mayo";
		}else if (mesFin==6) {
    		mes="Junio";
		}else if (mesFin==7) {
    		mes="Julio";
		}else if (mesFin==8) {
    		mes="Agosto";
		}else if (mesFin==9) {
    		mes="Septiembre";
		}else if (mesFin==10) {
    		mes="Octubre";
		}else if (mesFin==11) {
    		mes="Noviembre";
		}else if (mesFin==12) {
    		mes="Dciembre";
		
		}
    	
    	btnFechaFin.setText(            
    			new StringBuilder()                    
    			// Month is 0 based so add 1   
    			.append(diaFin ).append(" de ")
    			.append(mes ).append(" del ")                    
    			.append(añoFin).append("")
    			);    
    }
    
    public void onAgregarArchivo(View v){
    	
    	try {
			File tarjeta = Environment.getExternalStorageDirectory();
			File file = new File(tarjeta.getAbsoluteFile(),"");
			
    		
			
		} catch (Exception e) {
			// TODO: handle exception
		}
    }
    
    public void fechaIni(){
    	// add a click listener to the button        
    	btnFechaInicio.setOnClickListener(new View.OnClickListener() 
    		{            
    		public void onClick(View v) {                
    			showDialog(DATE_DIALOG_ID);            
    			}        
    		});        
    	// get the current date        
    	final Calendar ci = Calendar.getInstance();        
    	añoIni = ci.get(Calendar.YEAR);        
    	mesIni = ci.get(Calendar.MONTH) + 1 ;        
    	diaIni = ci.get(Calendar.DAY_OF_MONTH);       
    }
	
	public void fechaFin(){
    	// add a click listener to the button        
    	
		btnFechaFin.setOnClickListener(new View.OnClickListener() 
    		{            
    		public void onClick(View v) {                
    			showDialog(DATE_DIALOG_ID2);            
    			}        
    		});        
    	// get the current date        
    	final Calendar cf = Calendar.getInstance();        
    	añoFin = cf.get(Calendar.YEAR);        
    	mesFin = cf.get(Calendar.MONTH) + 1;        
    	diaFin = cf.get(Calendar.DAY_OF_MONTH);        
	}

	
    @Override
    protected Dialog onCreateDialog(int id) {    
    	switch (id) {   
    		case DATE_DIALOG_ID:        
    			return new DatePickerDialog(this,                    
    					mDateSetListener,                    
    					añoIni, (mesIni - 1), diaIni);   
    			
    		case DATE_DIALOG_ID2:        
    			return new DatePickerDialog(this,                    
    					mDateSetListener2,                    
    					añoFin,( mesFin - 1), diaFin);   
    			} 
    	return null;
    	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.nueva_tarea, menu);
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
