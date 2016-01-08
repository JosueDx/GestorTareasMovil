package com.example.gestortareasapp;

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
	
	public static final String ACTION_PROGRESO = "net.sgoliver.intent.action.PROGRESO";
	public static final String ACTION_FIN = "net.sgoliver.intent.action.FIN";
	
	static String NAMESPACE = "http://servicio.servicio.com";
	static String URL = "http://192.168.1.11:8080/Servicio_Tarea/services/funciones_servicio?wsdl";
	private String SOAP_ACTION="http://192.168.1.11:8080/Servicio_Tarea/services/funciones_servicio/notificacion";
	private String METODO="notificacion";
	
	private String SOAP_ACTION2="http://192.168.1.11:8080/Servicio_Tarea/services/funciones_servicio/tareaespecifica";
	private String METODO2="tareaespecifica";
	
	int notificationID = 1;
	int j =0;
	int id_empleado;
	
	public MiIntentService() {
        super("MiIntentService");
    }
	
	@Override
	protected void onHandleIntent(Intent intent) 
	{
		int iter = intent.getIntExtra("iteraciones", 0);
		id_empleado = intent.getIntExtra("id_empleado", 0);
		String validacion = "0";
		for(int i=1; i<=iter; i++) {
			tareaLarga();
			
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
		  				validacion = "1";
					}
					if(result.getProperty(0).toString().equals("0")){
						validacion = "0";
					}
		  		
		  		}else{
		  			//Toast.makeText(getApplicationContext(), "No Response!", Toast.LENGTH_SHORT).show();
		  		}
		  		
		  		
		  		}catch (Exception e) {
		  			e.printStackTrace();
		  			//Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_SHORT).show();
		  	  		
		  		}	
			
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
        //i.putExtra("notificationID", notificationID);
        
        
        SoapObject request = new SoapObject(NAMESPACE, METODO2);
		request.addProperty("request1" , ""+id_empleado);
	  	
	  	  SoapSerializationEnvelope Envoltorio = new SoapSerializationEnvelope (SoapEnvelope.VER11);
	      Envoltorio.setOutputSoapObject (request);
	  	  
	  	  HttpTransportSE TransporteHttp = new HttpTransportSE(URL);
	  	
	  	try {
	  		TransporteHttp.call(SOAP_ACTION2, Envoltorio);
	  	   
	  		SoapObject result = (SoapObject) Envoltorio.bodyIn;
	  		
	  		if(result != null){
	  			
	  			
	  			JSONObject row = new JSONObject(result.getProperty(0).toString());
	  		    item.setDescripcion(row.getString("descripcion"));
	  		    item.setComentario(row.getString("comentario"));
	  		    item.setNivel_tarea(row.getString("nivel_tarea"));
	  		    item.setFecha_inicio("20/05/2015");
	  		    item.setFecha_fin("21/05/2015");
	  		
	  		}else{
	  			//Toast.makeText(getApplicationContext(), "No Response!", Toast.LENGTH_SHORT).show();
	  		}
	  		
	  		
	  		}catch (Exception e) {
	  			e.printStackTrace();
	  			//Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_SHORT).show();
	  	  		
	  		}	
        
         
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, 0);
        CharSequence ticker ="Notificacion de TAREA NUEVA";
        CharSequence contentTitle = item.getDescripcion();
        CharSequence contentText = item.getComentario();
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
	
	private void tareaLarga()
    { 
    	try { 
    		Log.e("asas", ""+ j);
    		Thread.sleep(1000); 
    		j = j +1;
    	} catch(InterruptedException e) {}
    }
}

