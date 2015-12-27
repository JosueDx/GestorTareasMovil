package com.example.gestortareasapp;

import java.util.ArrayList;

import com.modelo.informacion.Tarea;
import com.modelos.DbUsuarios;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class BuscarTarea extends Activity {

	String criterio="";
	EditText edittextCriterio;
	ListView listviewTareas;
	Button buttonBuscar;
	String opcion;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_buscar_tarea);
		
		edittextCriterio= (EditText) findViewById(R.id.editTextCriterioBusqueda);
		listviewTareas = (ListView) findViewById(R.id.listViewTareas);
		 Intent intent = this.getIntent();
         
	     
		opcion= intent.getStringExtra("op");
			
		cargarlista();
		
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
	
	//clase interna
	class MyItemClickListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View componente, 
				int posicion,
				long arg3) {
			// TODO Auto-generated method stub
			
		//definir el comportamiento que tendrá la lista cuando se 
		//seleccione un item
			
	Tarea itemtarea =(Tarea) listviewTareas.getItemAtPosition(posicion);
	
	
	Intent intent=null;	
	int opcionInt= Integer.parseInt(opcion);
	
	switch (opcionInt) {
			case 1: //nuevo
				
		break;
			case 2: //BUSCAR
	    	 intent = new Intent(componente.getContext(),NuevaTarea.class);
	         intent.putExtra("idTarea", itemtarea.getId_tarea()+"");			
	         intent.putExtra("opc", "2");
	         startActivity(intent);
		break;
			case 3: //EDITAR
				intent = new Intent(componente.getContext(),NuevaTarea.class);
	            intent.putExtra("idTarea", itemtarea.getId_tarea()+"");
				intent.putExtra("opc", "3");
				startActivity(intent);
		break;
		
			case 4: //Eliminar
				intent = new Intent(componente.getContext(),NuevaTarea.class);
	            intent.putExtra("idTarea", itemtarea.getId_tarea()+"");
				intent.putExtra("opc", "4");
				startActivity(intent);
		break;
	default:
		break;
	}
	
		}
		
	}
	
	
	

public void onBuscarTarea(View v){
	if(edittextCriterio.getText().toString().equals("")){
		Toast.makeText(this, "Hola Guapo :3", Toast.LENGTH_SHORT).show();
	}else{
		cargarlista(edittextCriterio.getText().toString());
	}
}

public void cargarlista(){
DbUsuarios bd_tarea = new DbUsuarios();
	
	ArrayList<Tarea> listatareas = bd_tarea.listartexto();
	
    CustomListViewTareas Adaptador = new CustomListViewTareas(
	this,R.layout.tarea_list,listatareas);
	
	
            listviewTareas.setAdapter(Adaptador);
            listviewTareas.setOnItemClickListener(new MyItemClickListener());
            listviewTareas.refreshDrawableState();
}
public void cargarlista(String criterio){
DbUsuarios bd_tarea = new DbUsuarios();
	
	ArrayList<Tarea> listatareas = bd_tarea.listartexto(criterio);
	
    CustomListViewTareas Adaptador = new CustomListViewTareas(
	this,R.layout.tarea_list,listatareas);
	
	
            listviewTareas.setAdapter(Adaptador);
            listviewTareas.setOnItemClickListener(new MyItemClickListener());
            listviewTareas.refreshDrawableState();
}

}
