package com.example.gestortareasapp;


import com.modelo.informacion.Tarea;
import com.modelos.DbUsuarios;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class DetalleActivity extends Activity {

	TextView textdescripcion, textcomentario, textfechaini, textfechafin, textnivel;
	Tarea tobj = new Tarea();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detalle);
		inicializar();
		Intent intent = this.getIntent();
		String idTarea = intent.getStringExtra("idTarea");
		cargar_tarea(idTarea);
	}
	
	public void inicializar(){
		textdescripcion=(TextView) findViewById(R.id.textViewDDescripcion);
		textcomentario=(TextView) findViewById(R.id.textViewDComentario);
		textfechaini=(TextView) findViewById(R.id.textViewDFechaInicio);
		textfechafin=(TextView) findViewById(R.id.textViewDFechaFin);
		textnivel=(TextView) findViewById(R.id.textViewDNivelTarea);
	}
	
	public void cargar_tarea(String idTarea){
		
		DbUsuarios bd =new DbUsuarios();
		tobj = bd.tareabuscar(idTarea);
		
		textdescripcion.setText(tobj.getDescripcion());
		textcomentario.setText(tobj.getComentario());
		textfechaini.setText(tobj.getFecha_inicio());
		textfechafin.setText(tobj.getFecha_fin());
		textnivel.setText(tobj.getNivel_tarea());
				
		textcomentario.setEnabled(false);
		textdescripcion.setEnabled(false);
		textfechaini.setEnabled(false);
		textfechafin.setEnabled(false);
		textnivel.setEnabled(false);
	}
	
	public void onTareaRealizada(View boton){ 
		Intent intent= new Intent(this,TareaRealizada.class);
		intent.putExtra("idTarea", tobj.getId_tarea()+"");
		startActivity(intent);	
	}
	
	public void onCancelar(View boton){ 
	 	finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detalle, menu);
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
