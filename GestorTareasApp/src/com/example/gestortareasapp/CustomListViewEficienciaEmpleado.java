package com.example.gestortareasapp;

import java.util.List;

import com.modelo.informacion.Tarea;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomListViewEficienciaEmpleado  extends ArrayAdapter<Tarea>{
	
	Context context;
	
	 public CustomListViewEficienciaEmpleado(Context context, int resourceId,
	            List<Tarea> items) {
	        super(context, resourceId, items);
	        this.context = context;
	    }
	 
	 private class ViewHolder {
		    //declaracion de componentes graficos para dibujar casa item
		    	
		        TextView textViewDescripcion;
		        TextView textVieEficiencia;
		       
		        
		    }
			   
	 public View getView(int position, View convertView, ViewGroup parent) {
	        ViewHolder holder = null;
	        Tarea item = getItem(position);
	 // aqui inflamos el layout normal de un adapter es decir 
	        //lo estmaos personalizando
	        LayoutInflater mInflater = (LayoutInflater) context
	                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
	        if (convertView == null) {
	        	// indicar con que layout vamos a inflar el listview
	            convertView = mInflater.inflate(R.layout.eficiencia_listempleado, null);
	           
	            holder = new ViewHolder();
	            holder.textViewDescripcion = (TextView) 
	            		convertView.findViewById(R.id.textViewDescripcionEficiencia);
	            holder.textVieEficiencia = (TextView) 
	            		convertView.findViewById(R.id.textViewEficienciaPorc);
	                 convertView.setTag(holder);
    
	        } else
	        
	        	holder = (ViewHolder) convertView.getTag();
	      
	        // colocar datos a los componentes graficos de cada item
	        holder.textViewDescripcion.setText(item.getDescripcion());
	        holder.textVieEficiencia.setText(item.getEstado());
	      
	        return convertView;
	    
	    }

}
