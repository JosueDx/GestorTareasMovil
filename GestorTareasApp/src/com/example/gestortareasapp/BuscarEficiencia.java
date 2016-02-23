package com.example.gestortareasapp;

import java.util.ArrayList;

import com.modelo.informacion.Tarea;
import com.modelos.DbUsuarios;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

public class BuscarEficiencia extends Activity {
	ListView listviewEficiente;
	EditText editextdescripcion;
	int id_personas;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_buscar_eficiencia);
		id_personas=this.getIntent().getIntExtra("id_persona", 0);
		editextdescripcion=(EditText) findViewById(R.id.editTxtDescripcionEficienciaJefe);
		//editTxtDescripcionEficienciaJefe
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
	
	public void cargarlista(int id_personas, String criterio){
		DbUsuarios bd_tarea = new DbUsuarios();
			
			ArrayList<Tarea> listatareas = bd_tarea.listartextoeficiencia(id_personas,criterio);
			
		    CustomListViewEficiencia Adaptador = new CustomListViewEficiencia(
			this,R.layout.eficiencia_list,listatareas);
			
			
		    listviewEficiente.setAdapter(Adaptador);
			 //   listviewEficiente.setOnItemClickListener(new MyItemClickListener());
			    listviewEficiente.refreshDrawableState();
			}
		

	public void onBuscar(View v){
		if(editextdescripcion.getText().toString().equals("")){
			cargarlista(id_personas);
		}else{
			cargarlista(id_personas, editextdescripcion.getText().toString());
		}
	}



}
