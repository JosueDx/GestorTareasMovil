package com.example.gestortareasapp;

import android.support.v7.app.ActionBarActivity;

import java.util.ArrayList;

import com.example.gestortareasapp.BuscarTarea.MyItemClickListener;
import com.modelo.informacion.Tarea;
import com.modelos.DbUsuarios;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class BuscarEficiencia extends Activity {
	ListView listviewEficiente;
	int id_personas;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_buscar_eficiencia);
		id_personas=this.getIntent().getIntExtra("id_persona", 0);
		
		listviewEficiente=(ListView) findViewById(R.id.listViewEficiencia);
		
		cargarlista(id_personas);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.buscar_eficiencia, menu);
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

	public void cargarlista(int id_personas){
		DbUsuarios bd_tarea = new DbUsuarios();
			
			ArrayList<Tarea> listatareas = bd_tarea.listartextoeficiencia(id_personas);
			
		    CustomListViewEficiencia Adaptador = new CustomListViewEficiencia(
			this,R.layout.eficiencia_list,listatareas);
			
			
		    listviewEficiente.setAdapter(Adaptador);
		 //   listviewEficiente.setOnItemClickListener(new MyItemClickListener());
		    listviewEficiente.refreshDrawableState();
		}




}
