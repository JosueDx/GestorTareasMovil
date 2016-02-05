package com.example.gestortareasapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MenuEficienciaEmpleado extends Activity {

	int id_personas;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_eficiencia_empleado);
		
		id_personas=this.getIntent().getIntExtra("id_persona", 0);
		
	}

	public void onGeneral(View v){

		Intent intent= new Intent(this,EficienciaGEmpleado.class);
	intent.putExtra("id_persona", id_personas);
		startActivity(intent);
	}
	
	public void onTareas(View v){

		Intent intent= new Intent(this,EficienciaTEmpleado.class);
		intent.putExtra("id_persona", id_personas);
		startActivity(intent);
	}
	public void onCerrar(View v){

		finish();
	}
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_eficiencia_empleado, menu);
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
