package com.example.gestortareasapp;


import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import com.modelo.informacion.Tarea;
import com.modelos.DbUsuarios;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.text.Layout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RemoteViews;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class DetalleActivity extends Activity {
	private final int NOTIFICATION_ID = 1010;
	int notificationID = 1;
	TextView textdescripcion, textcomentario, textfechaini, textfechafin, textnivel;
	Tarea tobj = new Tarea();
	TimePicker timePicker1;
	String idTarea;
	Button btn;
	private int hour;
	private int minute;
	private Calendar calendar;
	private String format = "";
	int cal1,cal2,total;
	
	
	private void triggerNotification(){
			
	        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
	        
	        Intent i = new Intent(this, NotificationView.class);
	        i.putExtra("notificationID", notificationID);
	         
	        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, 0);
	        CharSequence ticker ="Notificacion de TAREA";
	        CharSequence contentTitle = ""+ tobj.getDescripcion();
	        CharSequence contentText = ""+ tobj.getComentario();
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
	
		public void tomartiempo() {
		      
			 int hour = timePicker1.getCurrentHour();
		      int min = timePicker1.getCurrentMinute();
		      cal1=(hour*3600)+(min*60);
		      showTime(hour, min);
		      int h,m;
		      calendar = Calendar.getInstance();
				
		       h =calendar.get(Calendar.HOUR_OF_DAY);
		       m = calendar.get(Calendar.MINUTE);
		       cal2=(h*3600)+(m*60);
		       
		       if(cal1>cal2)
		       {
		    	   total=cal1-cal2;
		       }
		          else{
			            if(cal2>cal1){
				         total=cal2-cal1;
			   }
			            else{
			            	 total=0;
			            }
		     }   
		 }
		 
		 public void showTime(int hour, int min) {
		      if (hour == 0) {
		         hour += 12;
		         format = "AM";
		      }
		      else if (hour == 12) {
		         format = "PM";
		      } else if (hour > 12) {
		         hour -= 12;
		         format = "PM";
		      } else {
		         format = "AM";
		      }
		   }
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detalle);
		inicializar();
		Intent intent = this.getIntent();
		idTarea = intent.getStringExtra("idTarea");
		cargar_tarea(idTarea);
		
	}
	
	public void inicializar(){
		timePicker1 = (TimePicker) findViewById(R.id.timePicker1);
		calendar = Calendar.getInstance();
		textdescripcion=(TextView) findViewById(R.id.textViewDDescripcion);
		textcomentario=(TextView) findViewById(R.id.textViewDComentario);
		textfechaini=(TextView) findViewById(R.id.textViewDFechaInicio);
		textfechafin=(TextView) findViewById(R.id.textViewDFechaFin);
		textnivel=(TextView) findViewById(R.id.textViewDNivelTarea);
		
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
	    int min = calendar.get(Calendar.MINUTE);
	     
	    showTime(hour, min); 
		
		 btn = (Button)findViewById(R.id.btn_notificacion);
		 btn.setText("Recordatorio");
		 btn.setEnabled(true);
		 
	      btn.setOnClickListener(new View.OnClickListener()
	      {
	          @Override
	          public void onClick(View v)
	          {
	          	tomartiempo();
	          	triggerAlert();
	              Timer timer = new Timer();
	              TimerTask timerTask = new TimerTask()
	              {
	                  @Override
	                  public void run()
	                  {
	                  	
	                  	triggerNotification();
	                  	
	                    //  funciono();
	                  	
	                  }
	              };
	              
	              timer.schedule(timerTask, (total*1000));
	              timePicker1.setEnabled(false);
	              btn.setText("Recordatorio iniciado");
	              btn.setEnabled(false);
	            
	          }
	      });
	      total=0;
	      
		}
	
	public void triggerAlert(){
		Calendar t = Calendar.getInstance();
        t.add(Calendar.SECOND, total);
 
        Intent i = new Intent(this, AlarmActivity.class);
        i.putExtra("Descripcion", tobj.getDescripcion());
        i.putExtra("Hora", tobj.getFecha_fin());
        PendingIntent pending = PendingIntent.getActivity(this,1235, i, PendingIntent.FLAG_CANCEL_CURRENT);
 
        AlarmManager alarm = (AlarmManager)getSystemService(Activity.ALARM_SERVICE);
        alarm.set(AlarmManager.RTC_WAKEUP, t.getTimeInMillis(),pending);

	}
	
	public void cargar_tarea(String idTarea){
		
		DbUsuarios bd =new DbUsuarios();
		tobj = bd.tareabuscarFutura(idTarea);
		
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
