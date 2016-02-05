package com.example.gestortareasapp;

import android.util.Log;

import com.modelos.DbUsuarios;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class EficienciaGEmpleado extends Activity {


TextView textviewEficiencia,textviewTotales,textviewRealizado,textviewAtrasadaa,textviewNoCumplida;
	
int id_personas;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_eficiencia_gempleado);
		id_personas=this.getIntent().getIntExtra("id_persona", 0);
		inicializar();
		DbUsuarios db= new DbUsuarios();
		float[] variable = {0, 0, 0, 0,0};
		Log.e("id persona", id_personas+"");
		variable=db.listarEficienciaEmpleado(id_personas);
		textviewTotales.setText(variable[0]+"");
		textviewRealizado.setText(variable[1]+"");
		textviewAtrasadaa.setText(variable[2]+"");
		textviewNoCumplida.setText(variable[3]+"");
		textviewEficiencia.setText(variable[4]+" %");
		
		
		
	}
	
		void inicializar(){
		textviewEficiencia=(TextView) findViewById(R.id.textViewEficiente);
		textviewTotales=(TextView) findViewById(R.id.textViewTotal);	
		textviewRealizado=(TextView) findViewById(R.id.textViewRealizada);
		textviewAtrasadaa=(TextView) findViewById(R.id.textViewAtrasada);
		textviewNoCumplida= (TextView) findViewById(R.id.textViewTareasNoCumplidas);
		}
	
	
	public void onCerrar(View v){
		finish();
	}
	
	
	
	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.eficiencia_gempleado, menu);
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
