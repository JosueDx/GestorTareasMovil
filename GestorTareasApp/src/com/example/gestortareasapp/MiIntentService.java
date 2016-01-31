package com.example.gestortareasapp;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.modelo.informacion.Tarea;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

public class MiIntentService extends IntentService {
	
	
	static String NAMESPACE = "http://servicio.servicio.com";
	static String URL = "http://192.168.71.53:8080/Servicio_Tarea/services/funciones_servicio?wsdl";
	private String SOAP_ACTION="http://192.168.71.53:8080/Servicio_Tarea/services/funciones_servicio/notificacion_tarea";
	private String METODO="notificacion_tarea";
	
	private String SOAP_ACTION2="http://192.168.71.53:8080/Servicio_Tarea/services/funciones_servicio/estado_notificacion";
	private String METODO2="estado_notificacion";
	
	int notificationID = 1;
	int j =0;
	int id_empleado;
	String nombrepersonas, op1;
	Intent intent;
	int id_departamento;
	
	public MiIntentService() {
        super("MiIntentService");
        
		
    }
	
	@Override
	protected void onHandleIntent(Intent intent) 
	{
		int iter = intent.getIntExtra("iteraciones", 0);
		id_empleado = intent.getIntExtra("id_empleado", 0);
		id_departamento=intent.getIntExtra("id_departamento", 0);
		nombrepersonas=intent.getStringExtra("nombre_persona");
		op1=intent.getStringExtra("op");
		String validacion = "0";
		for(int i=1; i<=iter; i++) {
			tareaLarga();
			Log.e("secundos", i+""+"");
			// desde aki comentar para prueba
			SoapObject request = new SoapObject(NAMESPACE, METODO);
			request.addProperty("request1" , ""+id_empleado);
		  	
		  	  SoapSerializationEnvelope Envoltorio = new SoapSerializationEnvelope (SoapEnvelope.VER11);
		      Envoltorio.setOutputSoapObject (request);
		  	  
		  	  HttpTransportSE TransporteHttp = new HttpTransportSE(URL);
		  	
		  	try {
		  		TransporteHttp.call(SOAP_ACTION, Envoltorio);
		  	   
		  		SoapObject result = (SoapObject) Envoltorio.bodyIn;
		  		
		  		if(result != null){
		  			
		  			if(result.getProperty(0).toString().equals("1")){
		  				validacion = "1"; // si tiene tarea pendiente 
					}
					if(result.getProperty(0).toString().equals("0")){
						validacion = "0"; // si no tiene tarea pendiente
					}
		  		
		  		}else{
		  			//Toast.makeText(getApplicationContext(), "No Response!", Toast.LENGTH_SHORT).show();
		  		}
		  		
		  		
		  		}catch (Exception e) {
		  			e.printStackTrace();
		  			//Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_SHORT).show();
		  	  		
		  		}	
		  	// hasta aki comentar en prueba
		  	
		  /*	if (i == 25){
		  		validacion = "1";
		  	}*/
		  				
			if(validacion.equals("1")){
				triggerNotification();
				i=100;
			}else{
				if(i==99){
					i=1;
				}
				
			}
		}
		
	}
	
	private void triggerNotification(){
		Tarea item;
		item = new Tarea();
        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("id_empleado", id_empleado);
		i.putExtra("id_departamento", id_departamento);
		i.putExtra("nombrepersonas", nombrepersonas);
		i.putExtra("op", op1);
		//startActivity(i);
		
		
        ArrayList<Tarea> listaTarea = null;
    	listaTarea = new ArrayList<Tarea>();
        //comentar desde aki en prueba
        SoapObject request = new SoapObject(NAMESPACE, METODO2);
		request.addProperty("request1" , ""+id_empleado);
	  	
	  	  SoapSerializationEnvelope Envoltorio = new SoapSerializationEnvelope (SoapEnvelope.VER11);
	      Envoltorio.setOutputSoapObject (request);
	  	  
	  	  HttpTransportSE TransporteHttp = new HttpTransportSE(URL);
	  	
	  	try {
	  		TransporteHttp.call(SOAP_ACTION2, Envoltorio);
	  	   
	  		SoapObject result = (SoapObject) Envoltorio.bodyIn;
	  		
	  		if(result != null){
	  			
	  			
	  			JSONObject jsondatos = new JSONObject(result.getProperty(0).toString());
	  			JSONArray arr = jsondatos.getJSONArray("tareas");
	  			for (int j = 0; j < arr.length(); j++)
	  			{
		  			
				    item.setId_tarea(arr.getJSONObject(j).getInt("id_tarea"));
				    item.setId_empleado(arr.getJSONObject(j).getInt("id_persotarea"));
				    item.setDescripcion(arr.getJSONObject(j).getString("descripcion"));
				    item.setComentario(arr.getJSONObject(j).getString("comentario"));
				    item.setNivel_tarea(arr.getJSONObject(j).getString("id_tipotarea"));
				    item.setFecha_inicio(arr.getJSONObject(j).getString("fecha_inicio"));
				    item.setFecha_fin(arr.getJSONObject(j).getString("fecha_fin"));
				    listaTarea.add(item);
	  			}
	  		
	  		}else{
	  			//Toast.makeText(getApplicationContext(), "No Response!", Toast.LENGTH_SHORT).show();
	  		}
	  		
	  		
	  		}catch (Exception e) {
	  			e.printStackTrace();
	  			//Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_SHORT).show();
	  	  		
	  		}	
        // hasta aki comentar
	  	
	  	if(listaTarea.size()>0){
	  	Iterator<Tarea> iterador = listaTarea.listIterator(); 
	    while( iterador.hasNext() ) {
	  		Tarea item2;
			item2 = new Tarea();
			item2 = iterador.next();
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, 0);
        CharSequence ticker ="Notificacion de TAREA NUEVA";
        CharSequence contentTitle = item2.getDescripcion(); // cambiar por un string "Notificacion"
        CharSequence contentText = item2.getComentario(); // cambiar por un string "Notificacion"
        Notification noti = new NotificationCompat.Builder(this)
                .setContentIntent(pendingIntent)
                .setTicker(ticker)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setSmallIcon(R.drawable.ic_launcher)
                .addAction(R.drawable.ic_launcher, ticker, pendingIntent)
                //.setVibrate(new long[] {100, 250, 100, 500})
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .build();
        notificationManager.notify(notificationID, noti);
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(8000);
		}
	  }
    }
	
	private void tareaLarga()
    { 
    	try { 
    		Log.e("asas", ""+ j);
    		Thread.sleep(1000); 
    		j = j +1;
    	} catch(InterruptedException e) {}
    }
}

