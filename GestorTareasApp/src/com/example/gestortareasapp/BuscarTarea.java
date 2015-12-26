package com.example.gestortareasapp;

import java.util.ArrayList;

import com.modelo.informacion.Tarea;
import com.modelos.DbUsuarios;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class BuscarTarea extends Activity {

	String criterio="";
	EditText edittextCriterio;
	ListView listviewTareas;
	Button buttonBuscar;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_buscar_tarea);
		
		edittextCriterio= (EditText) findViewById(R.id.editTextCriterioBusqueda);
		listviewTareas = (ListView) findViewById(R.id.listViewTareas);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.buscar_tarea, menu);
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
	


public void onBuscarTarea(View v){
	DbUsuarios bd_tarea = new DbUsuarios();
	
	ArrayList<Tarea> listatareas = bd_tarea.listartexto();
	
    CustomListViewTareas Adaptador = new CustomListViewTareas(
	this,R.layout.tarea_list,listatareas);
	
	
            listviewTareas.setAdapter(Adaptador);
         //   listviewTareas.setOnItemClickListener(new MyItemClickListener());
            listviewTareas.refreshDrawableState();
	
            Toast.makeText(this, "ando x aki ", Toast.LENGTH_LONG).show();
}
}
