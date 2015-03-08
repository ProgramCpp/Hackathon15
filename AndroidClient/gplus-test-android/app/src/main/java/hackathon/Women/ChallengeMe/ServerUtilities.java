/**
 * Copyright 2013 Google Inc. All Rights Reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */

package hackathon.women.challengeme;

import hackathon.women.challengeme.CommonUtilities;
import static hackathon.women.challengeme.CommonUtilities.SERVER_URL;
import static hackathon.women.challengeme.CommonUtilities.TAG;
import static hackathon.women.challengeme.CommonUtilities.displayMessage;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.content.pm.PackageInfo;
import java.io.IOException;


import android.os.AsyncTask;

//import com.google.android.gcm.GCMRegistrar;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.concurrent.atomic.AtomicInteger;

public final class ServerUtilities {
    private static final int MAX_ATTEMPTS = 5;
    private static final int BACKOFF_MILLI_SECONDS = 2000;
    private static final Random random = new Random();
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    /**
     * Register this account/device pair within the server.
     *
     */
    static void register(final Context context, String name, String email, final String regId) {
        Log.i(TAG, "registering device (regId = " + regId + ")");


        long backoff = BACKOFF_MILLI_SECONDS + random.nextInt(1000);
        // Once GCM returns a registration id, we need to register on our server
        // As the server might be down, we will retry it a couple
        // times.
        for (int i = 1; i <= MAX_ATTEMPTS; i++) {
            Log.d(TAG, "Attempt #" + i + " to register");
            try {
                displayMessage(context, context.getString(
                        R.string.server_registering, i, MAX_ATTEMPTS));
                int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
                if (resultCode != ConnectionResult.SUCCESS) {
                    //if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                    //    GooglePlayServicesUtil.getErrorDialog(resultCode, context,
                    //            PLAY_SERVICES_RESOLUTION_REQUEST).show();
                    //} else {
                    //    Log.i(TAG, "This device is not supported.");
                    //    finish();
                    //}
                    String message = context.getString(R.string.device_not_supported);
                    CommonUtilities.displayMessage(context, message);
                }
                else {
                    //post(serverUrl, params);
                    //String SENDER_ID = "My-Sender-ID";
                    GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(context);
                    String regid = getRegistrationId(context);

                    if (regid.isEmpty()) {
                        registerInBackground(context, gcm);
                    }

                    //String registrationId = gcm.register(SENDER_ID);
                    //GCMRegistrar.setRegisteredOnServer(context, true);
                    String message = context.getString(R.string.server_registeration_in_progress);
                    CommonUtilities.displayMessage(context, message);
                }
                return;
            } catch (Exception e) {
                // Here we are simplifying and retrying on any error; in a real
                // application, it should retry only on unrecoverable errors
                // (like HTTP error code 503).
                Log.e(TAG, "Failed to register on attempt " + i + ":" + e);
                if (i == MAX_ATTEMPTS) {
                    break;
                }
                try {
                    Log.d(TAG, "Sleeping for " + backoff + " ms before retry");
                    Thread.sleep(backoff);
                } catch (InterruptedException e1) {
                    // Activity finished before we complete - exit.
                    Log.d(TAG, "Thread interrupted: abort remaining retries!");
                    Thread.currentThread().interrupt();
                    return;
                }
                // increase backoff exponentially
                backoff *= 2;
            }
        }
        String message = context.getString(R.string.server_register_error,
                MAX_ATTEMPTS);
        CommonUtilities.displayMessage(context, message);
    }

    /**
     * Unregister this account/device pair within the server.

    static void unregister(final Context context, final String regId) {
        Log.i(TAG, "unregistering device (regId = " + regId + ")");
        String serverUrl = SERVER_URL + "/unregister";
        Map<String, String> params = new HashMap<String, String>();
        params.put("regId", regId);
        try {
            post(serverUrl, params);
            GCMRegistrar.setRegisteredOnServer(context, false);
            String message = context.getString(R.string.server_unregistered);
            CommonUtilities.displayMessage(context, message);
        } catch (IOException e) {
            // At this point the device is unregistered from GCM, but still
            // registered in the server.
            // We could try to unregister again, but it is not necessary:
            // if the server tries to send a message to the device, it will get
            // a "NotRegistered" error message and should unregister the device.
            String message = context.getString(R.string.server_unregister_error,
                    e.getMessage());
            CommonUtilities.displayMessage(context, message);
        }
    }
     */
    /**
     * Gets the current registration ID for application on GCM service, if there is one.
     * <p>
     * If result is empty, the app needs to register.
     *
     * @return registration ID, or empty string if there is no existing
     *         registration ID.
     */
    public static String getRegistrationId(Context context) {
        //final SharedPreferences prefs = getGcmPreferences(context);
        String registrationId = PreferenceManager.getDefaultSharedPreferences(context).getString("DEV_REG_ID", "defaultStringIfNothingFound");
        if (registrationId == "defaultStringIfNothingFound") {
            Log.i(TAG, "Registration not found.");
            return "";
        }
        // Check if app was updated; if so, it must clear the registration ID
        // since the existing regID is not guaranteed to work with the new
        // app version.
        int registeredVersion = PreferenceManager.getDefaultSharedPreferences(context).getInt("REGISTERED_VERSION", 0);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.i(TAG, "App version changed.");
            return "";
        }
        return registrationId;
    }

    /**
     * Registers the application with GCM servers asynchronously.
     * <p>
     * Stores the registration ID and the app versionCode in the application's
     * shared preferences.
     */
    static private void registerInBackground(final Context context, final GoogleCloudMessaging gcm) {

                String msg = "";
                try {
                    String regid = gcm.register(CommonUtilities.SENDER_ID);
                    msg = "Device registered, registration ID=" + regid;

                    // You should send the registration ID to your server over HTTP, so it
                    // can use GCM/HTTP or CCS to send messages to your app.
                    sendRegistrationIdToBackend(regid,"name");

                    // For this demo: we don't need to send it because the device will send
                    // upstream messages to a server that echo back the message using the
                    // 'from' address in the message.

                    // Persist the regID - no need to register again.
                    storeRegistrationId(context, regid);
                    Log.i("device id", regid);
                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                    // If there is an error, don't just keep trying to register.
                    // Require the user to click a button again, or perform
                    // exponential back-off.
                }

              //  CommonUtilities.displayMessage(this,  R.string.server_registered);

    }

    /**
     * @return Application's version code from the {@code PackageManager}.
     */
    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (Exception e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }
    /**
     * Sends the registration ID to your server over HTTP, so it can use GCM/HTTP or CCS to send
     * messages to your app. Not needed for this demo since the device sends upstream messages
     * to a server that echoes back the message using the 'from' address in the message.
     */
    private static void sendRegistrationIdToBackend(String regId, String name) {
        try {
        // Your implementation here.
        String serverUrl = SERVER_URL;
            List<NameValuePair> params = new ArrayList<NameValuePair>(2);
            params.add(new BasicNameValuePair("regId", regId));
            params.add(new BasicNameValuePair("name", name));
            params.add(new BasicNameValuePair("email", "Blah@box.com"));
        post(serverUrl, params);
        } catch (IOException e) {
            // Here we are simplifying and retrying on any error; in a real
            // application, it should retry only on unrecoverable errors
            // (like HTTP error code 503).
            Log.e(TAG, "Failed to register:"  + e);

        }

    }
    /**
     * Stores the registration ID and the app versionCode in the application's
     * {@code SharedPreferences}.
     *
     * @param context application's context.
     * @param regId registration ID
     */
    private static void storeRegistrationId(Context context, String regId) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString("DEV_REG_ID",regId).commit();
    }

    /**
     * Issue a POST request to the server.
     *
     * @param endpoint POST address.
     * @param params request parameters.
     *
     * @throws IOException propagated from POST.
     */
    private static void post(String endpoint, List<NameValuePair> params)
            throws IOException {

        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(endpoint);

        try {
            // Add your data
            httppost.setEntity(new UrlEncodedFormEntity(params));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            int status = response.getStatusLine().getStatusCode();
            if (status != 200) {
                Log.i("MyChallenge﹕", "POST to server failed with error code" + status);
            }
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }

    }
}