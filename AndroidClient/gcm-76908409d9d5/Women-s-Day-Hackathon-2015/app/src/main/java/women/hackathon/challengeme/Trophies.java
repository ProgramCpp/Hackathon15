package women.hackathon.challengeme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class Trophies extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trophies);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.trophies, menu);
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
	public void onBringItOnButtonClick(View view){
		Toast.makeText(getApplicationContext(), 
				"Navigating to Home Page", Toast.LENGTH_SHORT).show();	
		Intent HomeScreen = new Intent(this, MainScreen.class);
		Log.v("Main Activity", "Navigating to choose Home page");
		this.finish();
		startActivity(HomeScreen);
		}
}
