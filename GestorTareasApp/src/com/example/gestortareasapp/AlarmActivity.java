package com.example.gestortareasapp;


import android.util.Log;
import java.io.IOException;
import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AlarmActivity extends Activity {
	
	private MediaPlayer player;
	TextView textDescripcion, textHora;
    final Context context=this;
    String op1, op2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_alarm);
		
		op1=this.getIntent().getStringExtra("Descripcion");
		op2=this.getIntent().getStringExtra("Hora");
		Log.e("loooooog", op1);
		textDescripcion=(TextView) findViewById(R.id.txtDescripcionTarea);
		textHora=(TextView) findViewById(R.id.txtHoraTarea);
		textDescripcion.setText(op1);
		textHora.setText(op2);
		
		
		Button stop = (Button) findViewById(R.id.alarm);
		
		stop.setOnClickListener(new View.OnClickListener()
	      {
	          @Override
	          public void onClick(View v)
	          {
	        	//triggerAlert();
	        	  player.stop();
	        	  finish();
	            
	          }
	      });
	      
       /* stop.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View arg0, MotionEvent arg1) {
                player.stop();
                Intent i=new Intent(context,ByeActivity.class);
                startActivity(i);
                return false;
            }
        });*/
        
        play(this, getAlarmSound());
 
	}
	
	
	private void play(Context context, Uri alert) {
	        player = new MediaPlayer();
	        try {
	            player.setDataSource(context, alert);
	            final AudioManager audio = (AudioManager) context
	                    .getSystemService(Context.AUDIO_SERVICE);
	            if (audio.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
	                player.setAudioStreamType(AudioManager.STREAM_ALARM);
	                player.prepare();
	                player.start();
	            }
	        } catch (IOException e) {
	            Log.e("Error....","Check code...");
	        }
	    }
	 
	    private Uri getAlarmSound() {
	        Uri alertSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
	        if (alertSound == null) {
	            alertSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
	            if (alertSound == null) {
	                alertSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
	            }
	        }
	        return alertSound;
	    }
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.alarm, menu);
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
