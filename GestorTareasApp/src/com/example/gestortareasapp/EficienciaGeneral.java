package com.example.gestortareasapp;

import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import com.modelos.DbUsuarios;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class EficienciaGeneral extends Activity {
	
	TextView textviewEficiencia,textviewTotales,textviewRealizado,textviewAtrasadaa;
	
    int id_personas;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_eficiencia_general);
		
		id_personas=this.getIntent().getIntExtra("id_persona", 0);
		
		inicializar();
		DbUsuarios db= new DbUsuarios();
		float[] variable = {0, 0, 0, 0};
		Log.e("id persona", id_personas+"");
		variable=db.listarEficiencia(id_personas);
		textviewTotales.setText(variable[0]+"");
		textviewRealizado.setText(variable[1]+"");
		textviewAtrasadaa.setText(variable[2]+"");
		textviewEficiencia.setText(variable[3]+" %");
		
		
	}

	void inicializar(){
	textviewEficiencia=(TextView) findViewById(R.id.textViewEficiente);
	textviewTotales=(TextView) findViewById(R.id.textViewTotal);	
	textviewRealizado=(TextView) findViewById(R.id.textViewRealizada);
	textviewAtrasadaa=(TextView) findViewById(R.id.textViewAtrasada);
	
	}
	
	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.eficiencia_general, menu);
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
