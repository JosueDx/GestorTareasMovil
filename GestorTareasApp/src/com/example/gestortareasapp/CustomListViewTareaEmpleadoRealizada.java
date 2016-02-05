package com.example.gestortareasapp;

import java.util.List;

import com.modelo.informacion.TareaRealizadaE;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomListViewTareaEmpleadoRealizada extends ArrayAdapter<TareaRealizadaE>{

	Context context;
	
	 public CustomListViewTareaEmpleadoRealizada(Context context, int resourceId,
	            List<TareaRealizadaE> items) {
	        super(context, resourceId, items);
	        this.context = context;
	    }
	 
	 private class ViewHolder {
		    //declaracion de componentes graficos para dibujar casa item
		    	
		        TextView textViewDescripcionAtrasado;
		        TextView textViewFechaInicioAtrasado;
		        
		    }
	 
	 public View getView(int position, View convertView, ViewGroup parent) {
	        ViewHolder holder = null;
	        TareaRealizadaE item = getItem(position);
	 // aqui inflamos el layout normal de un adapter es decir 
	        //lo estmaos personalizando
	        LayoutInflater mInflater = (LayoutInflater) context
	                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
	        if (convertView == null) {
	        	// indicar con que layout vamos a inflar el listview
	            convertView = mInflater.inflate(R.layout.tarea_list_empleado_realizado, null);
	           
	            holder = new ViewHolder();
	            holder.textViewDescripcionAtrasado = (TextView) 
	            		convertView.findViewById(R.id.textViewDescripTareaAtrasada);
	            holder.textViewFechaInicioAtrasado = (TextView) 
	            		convertView.findViewById(R.id.textViewFechaIniEmplAtrasada);
	            
	            
	        //    holder.textViewNombreSitio=(TextView)
	          //  		convertView.findViewById(R.id.textViewnombre);
	            convertView.setTag(holder);
	            
	        
	        } else
	        
	        	holder = (ViewHolder) convertView.getTag();
	      
	        // colocar datos a los componentes graficos de cada item
	        holder.textViewDescripcionAtrasado.setText(item.getDescripcion());
	        holder.textViewFechaInicioAtrasado.setText(item.getFechafin());
	        
	        return convertView;
	    
	    }

}
