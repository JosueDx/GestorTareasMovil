package com.example.gestortareasapp;

import android.support.v7.app.ActionBarActivity;

import java.util.ArrayList;

import com.example.gestortareasapp.BuscarTarea.MyItemClickListener;
import com.modelo.informacion.Tarea;
import com.modelos.DbUsuarios;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class BuscarTareaEmpleadoActivity extends Activity {

	String criterio="";
	EditText edittextCriterio,edittextDescripc;
	ListView listviewTareasEmpleado;
	Button buttonBuscar;
	String opcion;
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_buscar_tarea_empleado);
	
	listviewTareasEmpleado = (ListView) findViewById(R.id.listViewBuscarTareaEmpleado);
	edittextCriterio = (EditText) findViewById(R.id.editTextBuscarTareaEmpleado);
	cargarlista();
	}
	
	public void cargarlista(){
		DbUsuarios bd_tarea = new DbUsuarios();
			
			ArrayList<Tarea> listatareas = bd_tarea.listartexto();
			
		    CustomListViewTareaEmpleado Adaptador = new CustomListViewTareaEmpleado(
			this,R.layout.tarea_list_empleado,listatareas);
			
			
		    listviewTareasEmpleado.setAdapter(Adaptador);
		    listviewTareasEmpleado.setOnItemClickListener(new MyItemClickListener());
		    listviewTareasEmpleado.refreshDrawableState();
		}
	

	//clase interna
		class MyItemClickListener implements OnItemClickListener{

			@Override
			public void onItemClick(AdapterView<?> arg0, View componente, 
					int posicion,
					long arg3) {
				// TODO Auto-generated method stub
				
			//definir el comportamiento que tendr� la lista cuando se 
			//seleccione un item
				
		Tarea itemtarea =(Tarea) listviewTareasEmpleado.getItemAtPosition(posicion);
		
		Intent intent=null;	
		
		intent = new Intent(componente.getContext(),DetalleActivity.class);
		intent.putExtra("idTarea", itemtarea.getId_tarea()+"");
         startActivity(intent);
		
				
			}
			
		}

	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.buscar_tarea_empleado, menu);
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
