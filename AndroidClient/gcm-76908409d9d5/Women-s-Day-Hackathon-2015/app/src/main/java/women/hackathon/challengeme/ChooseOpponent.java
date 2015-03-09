package women.hackathon.challengeme;


import android.app.Activity;
//import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class ChooseOpponent extends Activity {

	public static String challenge;
	public static boolean challengeAccepted = false;
	public static int count = 0;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_opponent);
		Intent challengeTimePage = getIntent();
		challenge = challengeTimePage.getStringExtra("ChallengeApp");
		Toast.makeText(ChooseOpponent.this,"Got it: "+challenge,
				Toast.LENGTH_LONG).show();
	}
	
	private void initialize() {
		getBaseContext().getApplicationContext().sendBroadcast(
			new Intent("StartupReceiver_Manual_Start"));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.challenge_notification_screen, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {	
		switch (item.getItemId()) {
		case R.id.Challenge_accepted:
			Log.v("Main Activity", "Challenge Accepted");
			challengeAccepted = true;
			//isChallengeAccepted(challengeAccepted);
			Toast.makeText(getApplicationContext(), 
					"Challenge Accepted", Toast.LENGTH_SHORT).show();
			return true;
		case R.id.Challenge_refused:
			Log.v("Main Activity", "Challenge Refused");
			challengeAccepted = false;
			//isChallengeAccepted(challengeAccepted);
			Toast.makeText(getApplicationContext(), 
					"Challenge Refused", Toast.LENGTH_SHORT).show();	
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	public void isChallengeAccepted(boolean ChallengeAccepted){
		if(true == challengeAccepted){
			//starting service here
			Intent serviceIntent = new Intent(ChooseOpponent.this,MyChallengeService.class);
			serviceIntent.putExtra("ChallengeName", challenge);
			startService(serviceIntent);
			Log.v("Main Activity", "Starting service");
			
			Toast.makeText(getApplicationContext(), 
					"Starting service", Toast.LENGTH_SHORT).show();
		}
		else if(false == challengeAccepted){
			Log.v("Main Activity", "Waiting for opponent to accept challenge");
			Toast.makeText(getApplicationContext(), 
					"Will notify you when the challenge is accepted", Toast.LENGTH_SHORT).show();	
			this.finish();
		}		
	}
	
	public void onChallengeButtonClick(View view){
		Toast.makeText(getApplicationContext(), 
				"Your friend has been challenged. ", Toast.LENGTH_SHORT).show();	
		Intent ChallengesScreenpage = new Intent(this, ChallengeNotificationScreen.class);
		Log.v("Main Activity", "Navigating to choose ChallengeNotificationScreen page");
		ChallengesScreenpage.putExtra("ChallengeApp", challenge);
		/*
		 * Activates broadcast receiver to listen to the challenge
		 */
		initialize();
		this.finish();
		startActivity(ChallengesScreenpage);
	}
}
