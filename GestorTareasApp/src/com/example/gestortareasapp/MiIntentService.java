package com.example.gestortareasapp;

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

public class MiIntentService extends IntentService {
	
	public static final String ACTION_PROGRESO = "net.sgoliver.intent.action.PROGRESO";
	public static final String ACTION_FIN = "net.sgoliver.intent.action.FIN";
	int notificationID = 1;
	int j =0;
	
	public MiIntentService() {
        super("MiIntentService");
    }
	
	@Override
	protected void onHandleIntent(Intent intent) 
	{
		int iter = intent.getIntExtra("iteraciones", 0);
		String validacion = "0";
		for(int i=1; i<=iter; i++) {
			tareaLarga();
			
			//Comunicamos el progreso
			/*Intent bcIntent = new Intent();
			bcIntent.setAction(ACTION_PROGRESO);
			bcIntent.putExtra("progreso", i*1);
			sendBroadcast(bcIntent);*/
			if (i==100){
				validacion="1";
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
		
		/*Intent bcIntent = new Intent();
		bcIntent.setAction(ACTION_FIN);
		sendBroadcast(bcIntent);*/
	}
	
	private void triggerNotification(){
		
        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        
        Intent i = new Intent(this, NotificationView.class);
        i.putExtra("notificationID", notificationID);
         
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, 0);
        CharSequence ticker ="Notificacion de TAREA";
        CharSequence contentTitle = "nueva tarea";
        CharSequence contentText = "debes realizar la tarea";
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

