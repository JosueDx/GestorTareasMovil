package com.example.gestortareasapp;

import org.ksoap2.serialization.SoapObject;

import com.modelo.informacion.Datosusuarios;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	Button buttonGestor, buttonRealizar, buttonEficiencia, buttonSesion;
	TextView textUsuario;
	String opcion2;
	int opcion1;
	String op1;
	String nombrepersonas;
	Intent intent;
	int id_personas,id_departamento;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);	
		inicializar();
		//opcion2= this.getIntent().getStringExtra("opcion");
		//Datosusuarios datosusuarios ;
		op1=this.getIntent().getStringExtra("op");
		id_personas=this.getIntent().getIntExtra("id_persona", 0);
		id_departamento=this.getIntent().getIntExtra("id_departamento", 0);
		nombrepersonas=this.getIntent().getStringExtra("nombre_persona");
		Log.e("cool: ",op1);
		Log.e("persona",nombrepersonas );
		textUsuario.setText(nombrepersonas);
		opcion1= Integer.parseInt(op1);

		if(opcion1==1){
			buttonRealizar.setVisibility(View.INVISIBLE);
		}
		if(opcion1==2){
			buttonGestor.setVisibility(View.INVISIBLE);
			buttonEficiencia.setVisibility(View.INVISIBLE);
			Log.e("servicios: ",op1);
			Intent msgIntent = new Intent(MainActivity.this, MiIntentService.class);
			msgIntent.putExtra("iteraciones", 100);
			msgIntent.putExtra("id_empleado", id_personas);
			startService(msgIntent);
		}
	}
	

	public void inicializar(){
		buttonGestor=(Button) findViewById(R.id.buttonGestorTareas);
		buttonRealizar =(Button) findViewById(R.id.buttonRealizarTareas);
		buttonEficiencia=(Button) findViewById(R.id.buttonEficienciaTareas);
		buttonSesion=(Button) findViewById(R.id.buttonCerrarSesion);
		textUsuario=(TextView) findViewById(R.id.textViewUsuario);
	}
	
	public void onRealizarTareas(View v){
		Intent intent= new Intent(this,BuscarTareaEmpleadoActivity.class);
		intent.putExtra("id_persona", id_personas);
		intent.putExtra("id_departamento", id_departamento);
		startActivity(intent);
	}
	
	
	// este es para preguntar si deseo salir 
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		
		if(keyCode == event.KEYCODE_BACK){
			
			
			AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);  
	        dialogo1.setTitle("Importante");  
	        dialogo1.setMessage("¿ Desea cerrar sesión ?");            
	        dialogo1.setCancelable(false);  
	        dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {  
	            public void onClick(DialogInterface dialogo1, int id) {  
	                cancelar();  
	            }  
	        });  
	        dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {  
	            public void onClick(DialogInterface dialogo1, int id) {  
	            	aceptar();
	            }  
	        });            
	        dialogo1.show();        
	 		
			
			
		}
		
		return super.onKeyDown(keyCode, event);
	}
	
	public void aceptar() {
        
    }
    
    public void cancelar() {
        finish();
    }


	public void onGestorTareas(View v){
		Intent intent= new Intent(this,Tareas.class);
		intent.putExtra("id_persona", id_personas);
		intent.putExtra("id_departamento", id_departamento);
		startActivity(intent);
	}
	public void onSesion(View v){
		AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);  
        dialogo1.setTitle("Importante");  
        dialogo1.setMessage("¿ Desea cerrar sesión ?");            
        dialogo1.setCancelable(false);  
        dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {  
            public void onClick(DialogInterface dialogo1, int id) {  
                cancelar();  
            }  
        });  
        dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {  
            public void onClick(DialogInterface dialogo1, int id) {  
            	aceptar();
            }  
        });            
        dialogo1.show();    
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void onEficiencia(View v){

		Intent intent= new Intent(this,EficienciaActivity.class);
		intent.putExtra("id_persona", id_personas);
		
		startActivity(intent);
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
