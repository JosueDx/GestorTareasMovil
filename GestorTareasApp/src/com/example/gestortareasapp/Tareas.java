package com.example.gestortareasapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Tareas extends Activity {

	Button buttonCrearTarea;
	int id_personas,id_departamento;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tareas);
		
		id_personas=this.getIntent().getIntExtra("id_persona", 0);
		id_departamento=this.getIntent().getIntExtra("id_departamento", 0);
		
	}

	public void onCrearTarea(View boton){
		Intent intent= new Intent(this,NuevaTarea.class);
		intent.putExtra("opc", "1");
		intent.putExtra("id_persona", id_personas);
		intent.putExtra("id_departamento", id_departamento);
		startActivity(intent);
		
	}
	public void onBuscarTarea(View boton){
		Intent intent= new Intent(this,BuscarTarea.class);
		intent.putExtra("op", "2");
		intent.putExtra("id_persona", id_personas);
		intent.putExtra("id_departamento", id_departamento);
		startActivity(intent);
			}
	public void onEditarTarea(View boton){
		Intent intent= new Intent(this,BuscarTarea.class);
		intent.putExtra("op", "3");
		intent.putExtra("id_persona", id_personas);
		intent.putExtra("id_departamento", id_departamento);
		startActivity(intent);
		
		
	}
	public void onEliminarTarea(View boton){
		Intent intent= new Intent(this,BuscarTarea.class);
		intent.putExtra("op", "4");
		intent.putExtra("id_persona", id_personas);
		intent.putExtra("id_departamento", id_departamento);
		startActivity(intent);
	}
	public void onSalir(View boton){
		finish();
		
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tareas, menu);
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
