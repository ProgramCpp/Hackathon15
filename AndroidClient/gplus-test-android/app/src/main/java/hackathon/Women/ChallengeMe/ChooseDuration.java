package hackathon.women.challengeme;

import java.util.Calendar;

import hackathon.women.challengeme.MyTimePickerDialog;
import hackathon.women.challengeme.TimePicker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ChooseDuration extends Activity {

	private TextView time;
	String challenge;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_duration);
		Intent challengeAppPage = getIntent();
		challenge = challengeAppPage.getStringExtra("ChallengeApp");
		Toast.makeText(ChooseDuration.this,"Got it: "+challenge,
				Toast.LENGTH_LONG).show();
		updateViews();
	}
	
	private void updateViews(){
		time = (TextView) findViewById(R.id.time);
	}
	
	public void showPicker(View v){
		Calendar now = Calendar.getInstance();
		MyTimePickerDialog mTimePicker = new MyTimePickerDialog(this, new MyTimePickerDialog.OnTimeSetListener() {

			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute, int seconds) {
				// TODO Auto-generated method stub
				time.setText(getString(R.string.time) + String.format("%02d", hourOfDay)+
						":" + String.format("%02d", minute) + 
						":" + String.format("%02d", seconds));	
				
			}
		}, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), now.get(Calendar.SECOND), true);
		mTimePicker.show();	
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.choose_duration, menu);
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
	
	public void onDurationButtonClick(View view){
		Toast.makeText(getApplicationContext(), 
				"Duration is set", Toast.LENGTH_SHORT).show();	
		Intent challengeOpponentPage = new Intent(this, hackathon.women.challengeme.ChooseOpponent.class);
		Log.v("Main Activity", "Navigating to choose challengeOpponent page");
		challengeOpponentPage.putExtra("ChallengeApp", challenge);
		this.finish();
		startActivity(challengeOpponentPage);
		
		}
}
