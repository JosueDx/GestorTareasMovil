package com.example.gestortareasapp;

import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MenuEmpleado extends Activity {
int id_personas,id_departamento;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_empleado);
		id_personas=this.getIntent().getIntExtra("id_persona", 0);
		id_departamento=this.getIntent().getIntExtra("id_departamento", 0);
	}
	
	
	public void onTareaPendiente(View v){
		Intent intent= new Intent(this,BuscarTareaEmpleadoActivity.class);
		intent.putExtra("id_persona", id_personas);
		intent.putExtra("id_departamento", id_departamento);
		Log.e("id_personaTPendiente",id_personas+"");
		Log.e("id_departamentoTPendiente",id_departamento+"");
		startActivity(intent);
	}


	
	public void onEficienciaEmpleado(View v){
		
		Intent intent= new Intent(this,MenuEficienciaEmpleado.class);
		intent.putExtra("id_persona", id_personas);
		intent.putExtra("id_departamento", id_departamento);
		Log.e("id_personaTPendiente",id_personas+"");
		Log.e("id_departamentoTPendiente",id_departamento+"");
		startActivity(intent);

	}
	
	public void onTareaRealizada(View v){
		Intent intent= new Intent(this,BuscarTareaEmpleadoRealizado.class);
		intent.putExtra("id_persona", id_personas);
		intent.putExtra("id_departamento", id_departamento);
		Log.e("id_personaTPendiente",id_personas+"");
		Log.e("id_departamentoTPendiente",id_departamento+"");
		startActivity(intent);
	}
	
	public void onCerrar(View v){
		
		finish();
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_empleado, menu);
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
