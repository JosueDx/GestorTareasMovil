package com.example.gestortareasapp;

import java.io.File;
import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class NuevaTarea extends Activity {
	
	    private EditText edittextDescripcion,edittextComentario;
	    private Button btnFechaInicio,btnFechaFin;    
	    private int añoIni,añoFin;    
	    private int mesIni,mesFin;    
	    private int diaIni,diaFin;    
	    String fechaini, fechafin,descripcion,comentario;
	    String cmes1="",cmes2="",cdia1="",cdia2="";
     static final int DATE_DIALOG_ID = 0;
	    static final int DATE_DIALOG_ID2 = 1;

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
	}

	public void inicializar(){
		btnFechaInicio=(Button) findViewById(R.id.buttonCrearTarea);
		btnFechaFin=(Button) findViewById(R.id.buttonFechaFin);
		edittextComentario=(EditText) findViewById(R.id.editTextComentario);
		edittextDescripcion=(EditText) findViewById(R.id.editTextDescripcion);
	}
	
	public void onGuardar(View boton){
		 descripcion = edittextComentario.getText().toString();
		 comentario= edittextDescripcion.getText().toString();
		
		if(descripcion.equals("") || comentario.equals("")){
			Toast.makeText(this, "Faltan por ingresar campos", Toast.LENGTH_LONG).show();
			
		}else{
			Toast.makeText(this, "Tarea asignada correctmente", Toast.LENGTH_LONG).show();
			finish();
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
