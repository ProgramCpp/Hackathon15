package hackathon.women.challengeme;
import hackathon.women.challengeme.MyChallengeService;

import java.util.Calendar;

import android.app.Activity;
//import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.google.android.gms.plus.model.people.PersonBuffer;

import java.util.ArrayList;

import hackathon.women.challengeme.R;

public class ChooseOpponent extends FragmentActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        ResultCallback<People.LoadPeopleResult>{

	public static String challenge;
	public static boolean challengeAccepted = false;
	public static int count = 0;
		public static double duration = 3000.0;
    private static final String TAG = "Womens-Hackathon";
    private ListView mCirclesListView;
    private ArrayAdapter<String> mCirclesAdapter;
    private ArrayList<String> mCirclesList;
    private GoogleApiClient mGoogleApiClient;
    private String mFriend;
	Calendar now = Calendar.getInstance();
	Time time = new Time();
    @Override
    public void onConnectionSuspended(int cause) {
        // The connection to Google Play services was lost for some reason.
        // We call connect() to attempt to re-establish the connection or get a
        // ConnectionResult that we can attempt to resolve.
        mGoogleApiClient.connect();
    }
    /* onConnected is called when our Activity successfully connects to Google
     * Play services.  onConnected indicates that an account was selected on the
     * device, that the selected account has granted any requested permissions to
     * our app and that we were able to establish a service connection to Google
     * Play services.
     */
    @Override
    public void onConnected(Bundle connectionHint) {
        // Reaching onConnected means we consider the user signed in.
        Log.i(TAG, "onConnected");

        // Update the user interface to reflect that the user is signed in.

        // Retrieve some profile information to personalize our app for the user.
        Person currentUser = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);


        String personPhotoUrl = currentUser.getImage().getUrl();
    }
    /* onConnectionFailed is called when our Activity could not connect to Google
    * Play services.  onConnectionFailed indicates that the user needs to select
    * an account, grant permissions or resolve an error in order to sign in.
    */
    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // Refer to the javadoc for ConnectionResult to see what error codes might
        // be returned in onConnectionFailed.
        Log.i(TAG, "onConnectionFailed: ConnectionResult.getErrorCode() = "
                + result.getErrorCode());

    }
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_opponent);
		Intent challengeTimePage = getIntent();
		challenge = challengeTimePage.getStringExtra("ChallengeApp");
		Toast.makeText(ChooseOpponent.this,"Got it: "+challenge,
				Toast.LENGTH_LONG).show();
        mCirclesListView = (ListView) findViewById(R.id.circles_list);
        mCirclesList = new ArrayList<String>();
        mCirclesAdapter = new ArrayAdapter<String>(
                this, R.layout.circle_member, mCirclesList);
        mCirclesListView.setAdapter(mCirclesAdapter);

        mCirclesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                mFriend = ((TextView)view).getText().toString();

                Toast.makeText(getBaseContext(), mFriend, Toast.LENGTH_LONG).show();

            }
        });
        mGoogleApiClient = buildGoogleApiClient();

        Plus.PeopleApi.loadVisible(mGoogleApiClient, null)
                .setResultCallback(this);
    }

    private GoogleApiClient buildGoogleApiClient() {
        // When we build the GoogleApiClient we specify where connected and
        // connection failed callbacks should be returned, which Google APIs our
        // app uses and which OAuth 2.0 scopes our app requests.
        return new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API, Plus.PlusOptions.builder().build())
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
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
			Intent serviceIntent = new Intent(ChooseOpponent.this, MyChallengeService.class);
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
		Intent ChallengesScreenpage = new Intent(this, hackathon.women.challengeme.ChallengeNotificationScreen.class);
		Log.v("Main Activity", "Navigating to choose ChallengeNotificationScreen page");
		ChallengesScreenpage.putExtra("ChallengeApp", challenge);
		/*
		 * Activates broadcast receiver to listen to the challenge
		 */
        Toast.makeText(getBaseContext(), mFriend, Toast.LENGTH_LONG).show();
		initialize();
		this.finish();
		startActivity(ChallengesScreenpage);
	}

    @Override
    public void onResult(People.LoadPeopleResult peopleData) {
        if (peopleData.getStatus().getStatusCode() == CommonStatusCodes.SUCCESS) {
            mCirclesList.clear();
            PersonBuffer personBuffer = peopleData.getPersonBuffer();
            try {
                int count = personBuffer.getCount();
                for (int i = 0; i < count; i++) {
                    mCirclesList.add(personBuffer.get(i).getDisplayName());
                }
            } finally {
                personBuffer.close();
            }

            mCirclesAdapter.notifyDataSetChanged();
        } else {
            Log.e(TAG, "Error requesting visible circles: " + peopleData.getStatus());
        }
    }
	public void onDurationButtonClick(View view){
		Toast.makeText(getApplicationContext(), 
				"Duration set"+duration, Toast.LENGTH_SHORT).show();	
	}
}
