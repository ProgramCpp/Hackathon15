package women.hackathon.challengeme;

import java.util.List;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;


public class MyChallengeService extends Service {
		
	public boolean ChallengeAccepted = false;
	String ChallengeName;
	String topActivityName;
	
	public MyChallengeService() {
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO: Return the communication channel to the service.
		//throw new UnsupportedOperationException("Not yet implemented");
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		Toast.makeText(MyChallengeService.this,"Service started",
				Toast.LENGTH_LONG).show();
		//Bundle bundle = intent.getExtras();
		//ChallengeName = (String) bundle.getCharSequence("ChallengeName");
		//ChallengeName = (String) bundle.getString("ChallengeName"); to use
		ChallengeName = intent.getStringExtra("ChallengeName");
		startChallenge();	
//		if(true == isRunningInForeground()){
//			Toast.makeText(getApplicationContext(), 
//					"Called: Name: "+ChallengeName, Toast.LENGTH_SHORT).show();	
//		}
		return Service.START_NOT_STICKY;
	}
	
	private void startChallenge() {
		boolean appNameChecker;
		appNameChecker = isRunningInForeground();
		if(appNameChecker == true){
			if(ChallengeName.equalsIgnoreCase(topActivityName)){
				Toast.makeText(getApplicationContext(), 
						"Matched", Toast.LENGTH_SHORT).show();	
				/* 	Go to lost page	 */
				Intent MissionFailedScreenpage = new Intent(this, MissionFailed.class);
				MissionFailedScreenpage.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				Log.v("Main Activity", "Navigating to choose MissionFailedScreenpage page");
				startActivity(MissionFailedScreenpage);
			}
			else{
				/* 	Go to Won Page	*/
				Intent MissionAccomplishedScreenpage = new Intent(this, MissionAccomplished.class);
				MissionAccomplishedScreenpage.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				Log.v("Main Activity", "Navigating to choose MissionAccomplishedScreenpage page");
				startActivity(MissionAccomplishedScreenpage);
			}
				
		}
	}
	
	protected boolean isRunningInForeground() {
		    ActivityManager manager = 
		         (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		    List<ActivityManager.RunningTaskInfo> tasks = manager.getRunningTasks(1);
		    if (tasks.isEmpty()) {
		        return false;
		    }
		    topActivityName = tasks.get(0).topActivity.getPackageName();
			Toast.makeText(getApplicationContext(), 
					"ActivityOnTop: "+topActivityName, Toast.LENGTH_SHORT).show();	
		    return topActivityName.equalsIgnoreCase(getPackageName());
		}		
	
}




