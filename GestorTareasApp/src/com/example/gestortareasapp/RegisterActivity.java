package com.example.gestortareasapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

public class RegisterActivity extends Activity {

	Spinner spinnerDepartamento;
	EditText edittextNombre, edittextApellido, edittextCedula, edittextDreccion, 
	edittextEmail, edittextUsuario, edittextContraseña;
	
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
	}

public void OnRegistar(View v){
		
		inicializar();
		String Nombre = edittextNombre.getText().toString();
		String Apellido= edittextApellido.getText().toString();
		String Cedula= edittextCedula.getText().toString();
		String Direccion = edittextDreccion.getText().toString();
		String Email= edittextEmail.getText().toString();
		String Usuario = edittextUsuario.getText().toString();
		String Contraseña= edittextContraseña.getText().toString();
		
		//Toast.makeText(this, "aqui vamos Carlos", Toast.LENGTH_LONG).show();
		if(Nombre.equals("") && Apellido.equals("") && Cedula.equals("")
				&& Direccion.equals("") && Email.equals("") && Usuario.equals("") 
				&& Contraseña.equals("") && spinnerDepartamento.getSelectedItem().toString().equals("Seleccione departamento")){
			
			Toast.makeText(this, "Faltan ingresar campos", Toast.LENGTH_LONG).show();
			
		}
		else {
			Toast.makeText(this, "Estamos en Mantenimientoo", Toast.LENGTH_LONG).show();
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
