package women.hackathon.challengeme;

import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class ChallengeNotificationScreen extends Activity {

	View root;
	String challenge;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//View view = null;
		String challengeAccepted = "false"; //Toggle value based on push notification
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_challenge_notification_screen);	    
	    Intent challengeTimePage = getIntent();
		challenge = challengeTimePage.getStringExtra("ChallengeApp");
		Toast.makeText(ChallengeNotificationScreen.this,"Got it: "+challenge,
				Toast.LENGTH_LONG).show();
		/*
		 * 
		 * If notification value received from server is Accepted, then open Challenge Accepted screen
		 * else open Challenge Refused Screen
		 * 
		 */
		 new CountDownTimer(30000, 1000) {
		     public void onTick(long millisUntilFinished) {
		         Log.v("Timer countdown","seconds remaining: " + millisUntilFinished / 1000);
		     }
		     public void onFinish() {
		         Log.v("Timer countdown","Done!");
			         if(true == ChooseOpponent.challengeAccepted){
			        	Toast.makeText(getApplicationContext(), 
					 				"Your friend has accepted the challenge. Are you ready???", Toast.LENGTH_SHORT).show();	
			 			//starting service here
			 			Intent serviceIntent = new Intent(ChallengeNotificationScreen.this,MyChallengeService.class);
			 			serviceIntent.putExtra("ChallengeName", challenge);
			 			startService(serviceIntent);
			 			Log.v("Main Activity", "Starting service");
			 			Toast.makeText(getApplicationContext(), 
			 					"Starting service", Toast.LENGTH_SHORT).show();
			 		}
			 		else if(false == ChooseOpponent.challengeAccepted){
			 			Log.v("Main Activity", "Waiting for opponent to accept challenge");
			 			Toast.makeText(getApplicationContext(), 
			 					"Will notify you when the challenge is accepted", Toast.LENGTH_SHORT).show();	
			 			ChallengeNotificationScreen.this.finish();
			 		} 
		     }
		  }.start();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.challenge_notification_screen, menu);
		return true;
		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);

	}


	public void onAlrightButtonClick(View view){
		Toast.makeText(getApplicationContext(), 
				"Are you ready???", Toast.LENGTH_SHORT).show();	
		this.finish();
		}
}
