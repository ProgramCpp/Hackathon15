package hackathon.women.challengeme;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

public class StartupReceiver extends BroadcastReceiver {

	static final String TAG = "SR";	
	final int startupID = 1111111;
	long challengeDuration = 5000;

	@Override
	public void onReceive(Context context, Intent intent) {
		
        final AlarmManager alarmManager = (AlarmManager) context
					.getSystemService(Context.ALARM_SERVICE);
		try{
				Intent i7 = new Intent(context, MyChallengeReceiver.class);
				PendingIntent ServiceManagementIntent = PendingIntent.getBroadcast(context,
						startupID, i7, 0);
				alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME,
						SystemClock.elapsedRealtime(), 
						challengeDuration, ServiceManagementIntent); //every 5 seconds		
			} catch (Exception e) {
				Log.i(TAG, "Exception : "+e);
			}
			
		}
	
}
