package com.example.gestortareasapp;


import com.modelo.informacion.Datosusuarios;
import com.modelos.DbUsuarios;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class LoginActivity extends Activity {

	EditText edittextUser,edittextPassword;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_login);
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
			
			Datosusuarios datosusuarios= new Datosusuarios();
			DbUsuarios dbusuarios= new DbUsuarios();
			
			datosusuarios= dbusuarios.Listalogin(this, NombreUsuario, ClaveUsuario);
			
			
			if(datosusuarios!=null){
				//Toast.makeText(this, "Datos correctos", Toast.LENGTH_LONG).show();
				if(datosusuarios.getUsuarios().getId_tipousuario() == 1){
					Toast.makeText(this, "Bienvenido Jefe: "+datosusuarios.getUsuario(), Toast.LENGTH_LONG).show();
					Intent intent= new Intent(this,MainActivity.class);
		    		System.out.println("Usuario");
		    		startActivity(intent);
				}
				if(datosusuarios.getUsuarios().getId_tipousuario() == 2){
					Toast.makeText(this, "Bienvenido Empleado: "+datosusuarios.getUsuario(), Toast.LENGTH_LONG).show();
					Intent intent= new Intent(this,MainActivity.class);
		    		System.out.println("Usuario");
		    		startActivity(intent);
				}
			
			}else{
				Toast.makeText(this, "Datos incorrectos", Toast.LENGTH_LONG).show();
			     edittextUser.setText("");
				 edittextPassword.setText("");			
			}					
		}	
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
	
	
	}
	
	
}
