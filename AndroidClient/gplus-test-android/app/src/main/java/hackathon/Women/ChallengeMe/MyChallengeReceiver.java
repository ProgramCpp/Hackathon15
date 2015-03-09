package hackathon.women.challengeme;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import java.util.List;
import android.app.ActivityManager;
import android.util.Log;
import android.widget.Toast;


public class MyChallengeReceiver extends BroadcastReceiver {
	
	public final String TAG = "MyChallenge"; // MyChallengeReceiver
	public static int challengeWon = 2; //default

	public MyChallengeReceiver() {
	}

	@Override
	public void onReceive(Context context, Intent intent) {
	try {			
			ActivityManager am = (ActivityManager) context
					.getSystemService(Context.ACTIVITY_SERVICE);		
			// Returns a list of the tasks that are currently running, 
			// with the most recent being first and older ones after in order.
			List<ActivityManager.RunningTaskInfo> alltasks = am
					.getRunningTasks(1);

			for (ActivityManager.RunningTaskInfo aTask : alltasks) {
				
                // Used to check for Challenged App screen  
				if (aTask.topActivity.getPackageName().equals(ChooseOpponent.challenge)){

					ChooseOpponent.count++;
					
                    Toast.makeText(context, "You have used: "+ChooseOpponent.challenge+" for "
					+ChooseOpponent.count * 5 +"seconds", Toast.LENGTH_LONG).show();
                    challengeWon = 0;

                    Toast.makeText(context, "Challenge Lost", Toast.LENGTH_LONG).show();
				}
				else{
					ChooseOpponent.count = 0; //refreshing the time counter
					//Toast.makeText(context, "Challenge Won", Toast.LENGTH_LONG).show();
					challengeWon = 1;
				}
				// These are showing current running activity in logcat
				Log.i(TAG, "===============================");
					Log.i(TAG, "aTask.baseActivity: " 
								+ aTask.baseActivity.flattenToShortString());
					Log.i(TAG, "aTask.baseActivity: "
								+ aTask.baseActivity.getClassName());
				Log.i(TAG, "===============================");				
				
			}

		} catch (Throwable t) {
			Log.i(TAG, "Throwable caught: "
						+ t.getMessage(), t);
		}
		
	}
}



