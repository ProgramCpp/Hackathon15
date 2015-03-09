package women.hackathon.challengeme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MyProfile extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_profile);
		
		/*
		 * 
		 * Show the results from the DB
		 * Name, Challenges, Points, My Trophy Room
		 * 
		 */
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_profile, menu);
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
	
	public void myTrophyRoomButtonClick(View view){
		Toast.makeText(getApplicationContext(), 
				"I want to see my Trophy Room", Toast.LENGTH_SHORT).show();	
		Intent challengeOpponentPage = new Intent(this, MyTrophyRoom.class);
		Log.v("Main Activity", "Navigating to choose myTrophyRoom page");
		this.finish();
		startActivity(challengeOpponentPage);
		}
	
	
}
