package com.example.gestortareasapp;

import java.util.ArrayList;

import com.example.gestortareasapp.BuscarTareaEmpleadoActivity.MyItemClickListener;
import com.modelo.informacion.Tarea;
import com.modelo.informacion.TareaRealizadaE;
import com.modelos.DbUsuarios;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class BuscarTareaEmpleadoRealizado extends Activity {
	
	ListView listviewTareaEmpleadoRealizado;
	EditText edittextCriterio;
	int id_personas;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_buscar_tarea_empleado_realizada);
		inicializar();
		id_personas=this.getIntent().getIntExtra("id_persona", 0);
		Log.e("ando x aki xk jouse no sabe",""+ id_personas);
		cargarlista();
		
	}	
	
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		cargarlista();
	}



	public void inicializar(){
		listviewTareaEmpleadoRealizado=(ListView) findViewById(R.id.listViewTareaRealizadas);
		edittextCriterio=(EditText) findViewById(R.id.editTextDescripcTareaAtrasadaa);
	}
	
	public void cargarlista(){
		
		  	DbUsuarios bd_tarea = new DbUsuarios();
			ArrayList<TareaRealizadaE> listatareas = bd_tarea.listartextoempleadorealizado(id_personas);
			
		    CustomListViewTareaEmpleadoRealizada Adaptador = new CustomListViewTareaEmpleadoRealizada(
			this,R.layout.tarea_list_empleado_realizado,listatareas);
			
			
		    listviewTareaEmpleadoRealizado.setAdapter(Adaptador);
		    listviewTareaEmpleadoRealizado.setOnItemClickListener(new MyItemClickListener());
		    listviewTareaEmpleadoRealizado.refreshDrawableState();
		
		
		}
	
	public void cargarlista(int id_personas, String criterio){
		DbUsuarios bd_tarea = new DbUsuarios();
			
		
			ArrayList<TareaRealizadaE> listatareas = bd_tarea.listartextoempleadorealizado(id_personas, criterio);
			
			CustomListViewTareaEmpleadoRealizada Adaptador = new CustomListViewTareaEmpleadoRealizada(
			this,R.layout.tarea_list_empleado_realizado,listatareas);
			
			
			listviewTareaEmpleadoRealizado.setAdapter(Adaptador);
			listviewTareaEmpleadoRealizado.setOnItemClickListener(new MyItemClickListener());
			listviewTareaEmpleadoRealizado.refreshDrawableState();
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
					
			TareaRealizadaE itemtarea =(TareaRealizadaE) listviewTareaEmpleadoRealizado.getItemAtPosition(posicion);
			
			
			Intent intent=null;	
			intent = new Intent(componente.getContext(),EditarTareaRealizada.class);
			intent.putExtra("idTarea",itemtarea.getId());
		    intent.putExtra("id_personas", id_personas);
	         startActivity(intent);
			
					
				}
				
			}
	
	
	public void OnBuscar(View v){
		if(edittextCriterio.getText().toString().equals("")){
			cargarlista();
		}else{
			cargarlista(id_personas, edittextCriterio.getText().toString());
		}
	}
	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tarea_pendiente, menu);
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
