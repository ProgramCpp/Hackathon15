package women.hackathon.challengeme;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

public class ChooseChallengeTime extends Activity implements OnClickListener{
	
	// Widget GUI
	ImageButton ImageButton_choose_date, ImageButton_choose_time;
	EditText txtDate, txtTime;
	
	// Variable for storing current date and time
	private int mYear, mMonth, mDay, mHour, mMinute;

	public static String date, time; //to access it anywhere else in the app
	String challenge;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_challenge_time);
		Intent challengeAppPage = getIntent();
		challenge = challengeAppPage.getStringExtra("ChallengeApp");
		Toast.makeText(ChooseChallengeTime.this,"Got it: "+challenge,
				Toast.LENGTH_LONG).show();
		ImageButton_choose_date = (ImageButton) findViewById(R.id.ImageButton_choose_date);
		ImageButton_choose_time = (ImageButton) findViewById(R.id.ImageButton_choose_time);
 
        txtDate = (EditText) findViewById(R.id.txtDate);
        txtTime = (EditText) findViewById(R.id.txtTime);
 
        ImageButton_choose_date.setOnClickListener(this);
        ImageButton_choose_time.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.choose_challenge_time, menu);
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
	
	public void onNextButtonClick(View view){
		Toast.makeText(getApplicationContext(), 
				"Now, whom do you want to challenge?", Toast.LENGTH_SHORT).show();	
		Intent challengeOpponentPage = new Intent(this, ChooseOpponent.class);
		Log.v("Main Activity", "Navigating to choose challengeOpponent page");
		challengeOpponentPage.putExtra("ChallengeApp", challenge);
		this.finish();
		startActivity(challengeOpponentPage);
		}
	
	@Override
	public void onClick(View v) {
		if (v == ImageButton_choose_date) {
			 
            // Process to get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
 
            // Launch Date Picker Dialog
            DatePickerDialog dpd = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {
 
                        @Override
                        public void onDateSet(DatePicker view, int year,
                                int monthOfYear, int dayOfMonth) {
                            txtDate.setText(dayOfMonth + "/"
                                    + (monthOfYear + 1) + "/" + year);
 
                        }
                    }, mYear, mMonth, mDay);
            dpd.show();
            date = mDay + "/"+ (mMonth + 1) + "/" + mYear;
        }	
		if (v == ImageButton_choose_time) {
			 
            // Process to get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);
 
            // Launch Time Picker Dialog
            TimePickerDialog tpd = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {
 
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                int minute) {
                            txtTime.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            tpd.show();
            time = mHour + ":" + mMinute; 
        }
	}
	
}
