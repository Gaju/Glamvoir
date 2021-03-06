package glamvoir.appzstack.glamvoir.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.Arrays;

import glamvoir.appzstack.glamvoir.R;
import glamvoir.appzstack.glamvoir.apppreference.AppPreferences;
import glamvoir.appzstack.glamvoir.config.AppConfig;
import glamvoir.appzstack.glamvoir.constant.AppConstant;
import glamvoir.appzstack.glamvoir.network.InternetStatus;


/**
 * Created by jaim on 7/21/2015.
 */
public class FrontPageActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    // Creating Facebook CallbackManager Value
    public static CallbackManager callbackmanager;
    private static final int RC_SIGN_IN = 0;
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;

    // Logcat tag
    private static final String TAG = "MainActivity";

    // Profile pic image size in pixels
    private static final int PROFILE_PIC_SIZE = 400;

    // Google client to interact with Google API
    private GoogleApiClient mGoogleApiClient;

    /**
     * A functionType indicating that a PendingIntent is in progress and prevents us
     * from starting further intents.
     */
    private boolean mIntentInProgress;

    private boolean mSignInClicked;

    private ConnectionResult mConnectionResult;
    private ImageButton btnSignIn,btn_SignUp;
    private Button  btn_LoginGmain, bt_facebook;
    private CoordinatorLayout coordinatorLayout;

    private TextView term_click,tv_click_privacy_policy;
    private GoogleCloudMessaging gcm;
    private final int ACTION_PLAY_SERVICES_DIALOG = 100;
    private String gcmRegId;
    private int currentAppVersion;
    AppPreferences appPreferences;


    public static void startActivityWithClearTop(Activity activity) {
        Intent intent = new Intent(activity, FrontPageActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
        activity.finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MultiDex.install(this);
        super.onCreate(savedInstanceState);
        /*PackageInfo info;
        try {
            info = getPackageManager().getPackageInfo("glamvoir.appzstack.glamvoir", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                //String something = new String(Base64.encodeBytes(md.digest()));
                Log.e("hash key", something);
                System.out.print("#######"+something);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("no such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }*/

        FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.front_page_two);
        callbackmanager = CallbackManager.Factory.create();
        // Add code to print out the key hash

        appPreferences = new AppPreferences(this);

        checkUserSession();

        //initialize all views
        initViews();

        // initializeall views listeners
        initListeners();

        saveDeviceToken();

        checkForGCMRegisteration();

        // Initializing google Api
        //   initGoogleApi();

    }

//    protected void onStart() {
//        super.onStart();
//        mGoogleApiClient.connect();
//    }
//
//    protected void onStop() {
//        super.onStop();
//        if (mGoogleApiClient.isConnected()) {
//            mGoogleApiClient.disconnect();
//        }
//    }


    private void checkForGCMRegisteration() {

        // Check device for Play Services APK.
        if (checkPlayServices()) {

            gcm = GoogleCloudMessaging.getInstance(this);

            // Read saved registration id from shared preferences.
            gcmRegId = appPreferences.getGcmID();

            // Read saved app version
            int savedAppVersion = appPreferences.getAppVersion();

            // Get current Application version
            currentAppVersion = getAppVersion(getApplicationContext());

            // register if saved registration id not found
            if (gcmRegId.isEmpty() || savedAppVersion != currentAppVersion) {
                registerInBackground();
            }

            // // register if saved registration id not found
            // if (gcmRegId.isEmpty()) {
            // registerInBackground();
            // }
        }
    }


    /**
     * Registers the application with GCM servers asynchronously.
     * <p/>
     * Stores the registration ID and app versionCode in the application's
     * shared preferences.
     */
    private void registerInBackground() {
        new RegistrationTask().execute(null, null, null);
    }

    // Async task to register in background
    private class RegistrationTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            String msg = "";
            try {
                if (gcm == null) {
                    gcm = GoogleCloudMessaging
                            .getInstance(getApplicationContext());
                }
                gcmRegId = gcm.register(AppConfig.GCM_SENDER_ID);
                String sss = gcmRegId;
                System.out.println(sss);

				/*
                 * At this point, you have gcm registration id. Save this
				 * registration id in your users table against this user. This
				 * id will be used to send push notifications from server.
				 */

                msg = "Successfully Registered for GCM";

            } catch (Exception ex) {
                ex.printStackTrace();
                msg = "Error while registering for GCM.";
            }
            return msg;
        }

        @Override
        protected void onPostExecute(String msg) {
            if (!gcmRegId.isEmpty()) {
                appPreferences.setGcmID(gcmRegId);
                appPreferences.setAppVersion(currentAppVersion);
            }
        }
    }


    /**
     * Initializing google plus api client
     */
    private void initGoogleApi() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).addApi(Plus.API, null)
                .addScope(Plus.SCOPE_PLUS_LOGIN).build();
    }

    /**
     * initialize all views
     */
    private void initViews() {

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id
                .coordinatorLayout);
        btnSignIn = (ImageButton) findViewById(R.id.frontpage_signin);
        btn_SignUp = (ImageButton) findViewById(R.id.frontpage_signup);
        btn_LoginGmain = (Button) findViewById(R.id.login_gmail);
        bt_facebook = (Button) findViewById(R.id.bt_facebook);
        term_click= (TextView) findViewById(R.id. term_click);
        tv_click_privacy_policy= (TextView) findViewById(R.id.tv_click_privacy_policy);
    }

    /**
     * initializeall views listeners
     */
    private void initListeners() {

        btnSignIn.setOnClickListener(this);
        btn_SignUp.setOnClickListener(this);
        btn_LoginGmain.setOnClickListener(this);
        bt_facebook.setOnClickListener(this);
        term_click.setOnClickListener(this);
        tv_click_privacy_policy.setOnClickListener(this);


    }

    @Override
    protected void onResume() {
        super.onResume();

        if (InternetStatus.getConnectivityStatus(this) == InternetStatus.TYPE_NOT_CONNECTED) {
            enableSnackbar();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.frontpage_signin:
                SigninActivity.startActivity(FrontPageActivity.this);
                break;

            case R.id.frontpage_signup:
                SignUpActivity.startActivity(FrontPageActivity.this);
                break;

            case R.id.login_gmail:
                // Signin button clicked
                //signInWithGplus();
                break;
            case R.id.bt_facebook:
                // Signin button clicked
                onFblogin();
                break;

            case R.id.term_click:
                Term_and_Condition.startActivity(FrontPageActivity.this);

                break;

            case R.id.tv_click_privacy_policy:

                Privacy_Policy.startActivity(FrontPageActivity.this);
                break;

            default:
                break;
        }
    }


    /**
     * show when no internet connection.
     */
    private void enableSnackbar() {
        Snackbar
                .make(coordinatorLayout, getResources().getString(R.string.internet_error),
                        Snackbar.LENGTH_LONG)
                .setAction(getResources().getString(R.string.internet_settings), snackbarClickListener)
                .show();
    }

    View.OnClickListener snackbarClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.setClassName("com.android.settings", "com.android.settings.wifi.WifiSettings");
            startActivity(intent);
        }
    };

    public class NetworkChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(final Context context, final Intent intent) {
            String statusa = InternetStatus.getConnectivityStatusString(context);
            Toast.makeText(context, statusa, Toast.LENGTH_LONG).show();
            int status = InternetStatus.getConnectivityStatus(context);
            if (status == InternetStatus.TYPE_NOT_CONNECTED) {
                enableSnackbar();
            }
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if (!result.hasResolution()) {
            GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this,
                    0).show();
            return;
        }

        if (!mIntentInProgress) {
            // Store the ConnectionResult for later usage
            mConnectionResult = result;

            if (mSignInClicked) {
                // The user has already clicked 'sign-in' so we attempt to
                // resolve all
                // errors until the user is signed in, or they cancel.
                resolveSignInError();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode,
                                    Intent intent) {
        callbackmanager.onActivityResult(requestCode, responseCode, intent);
        if (requestCode == RC_SIGN_IN) {
            if (responseCode != RESULT_OK) {
                mSignInClicked = false;
            }

            mIntentInProgress = false;

            if (!mGoogleApiClient.isConnecting()) {
                mGoogleApiClient.connect();
            }
        }


    }

    @Override
    public void onConnected(Bundle arg0) {
        mSignInClicked = false;
        Toast.makeText(this, "User is connected!", Toast.LENGTH_LONG).show();

        // Get user's information gv
        getProfileInformation();

    }

    @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();
    }

    /**
     * Sign-in into google
     */
    private void signInWithGplus() {
        if (!mGoogleApiClient.isConnecting()) {
            mSignInClicked = true;
            resolveSignInError();
        }
    }

    /**
     * Method to resolve any signin errors
     */
    private void resolveSignInError() {
        if (mConnectionResult.hasResolution()) {
            try {
                mIntentInProgress = true;
                mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
            } catch (IntentSender.SendIntentException e) {
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }
    }


    /**
     * Fetching user's information name, email, profile pic
     */
    private void getProfileInformation() {
        try {
            if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
                Person currentPerson = Plus.PeopleApi
                        .getCurrentPerson(mGoogleApiClient);
                String personName = currentPerson.getDisplayName();
                String personPhotoUrl = currentPerson.getImage().getUrl();
                String personGooglePlusProfile = currentPerson.getUrl();
                String email = Plus.AccountApi.getAccountName(mGoogleApiClient);

                Log.e(TAG, "Name: " + personName + ", plusProfile: "
                        + personGooglePlusProfile + ", email: " + email
                        + ", Image: " + personPhotoUrl);


                // by default the profile url gives 50x50 px image only
                // we can replace the value with whatever dimension we want by
                // replacing sz=X
                personPhotoUrl = personPhotoUrl.substring(0,
                        personPhotoUrl.length() - 2)
                        + PROFILE_PIC_SIZE;

                //   new LoadProfileImage(imgProfilePic).execute(personPhotoUrl);

            } else {
                Toast.makeText(getApplicationContext(),
                        "Person information is null", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Background Async task to load user profile picture from url
     */
    private class LoadProfileImage extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public LoadProfileImage(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    /**
     * save the device token and device type.
     */
    private void saveDeviceToken() {
        AppPreferences appPreferences = new AppPreferences(FrontPageActivity.this);
        if (appPreferences.getDeviceToken() == null)
            appPreferences.setDeviceToken(AppConstant.getDeviceToken(this));
        if (appPreferences.getDeviceType() == null)
            appPreferences.setDeviceType(AppConstant.getDeviceType());
    }

    /**
     * check the auth token for user session
     */
    private void checkUserSession() {
        AppPreferences appPreferences = new AppPreferences(FrontPageActivity.this);
        if (appPreferences.getAuthToken() != null) {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        }
    }

    // Private method to handle Facebook login and callback
    private void onFblogin() {


        // Set permissions
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"));

        LoginManager.getInstance().registerCallback(callbackmanager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(final LoginResult loginResult) {

                        System.out.println("Success");
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,last_name,link,email,picture");

                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                                    @Override

                                    public void onCompleted(
                                            JSONObject json,
                                            GraphResponse response) {
                                        // TODO Auto-generated method stub

                                        if (response.getError() != null) {
                                            // handle error
                                            System.out.println("ERROR");
                                        } else {
                                            System.out.println("Success");
                                            try {

                                                String jsonresult = String.valueOf(json);
                                                System.out.println("JSON Result" + jsonresult);
                                                String str_email = json.getString("email");
                                                String str_id = json.getString("id");
                                                String str_firstname = json.getString("first_name");
                                                String str_lastname = json.getString("last_name");

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }


                                    }

                                });
                        request.setParameters(parameters);
                        request.executeAsync();

                    }

                    @Override
                    public void onCancel() {
                        // (TAG_CANCEL,"On cancel");
                    }

                    @Override
                    public void onError(FacebookException error) {
                        //  Log.dddddr(TAG_ERROR,error.toString());
                    }
                });
    }

    /**
     * Method to verify google play services on the device
     */
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(FrontPageActivity.this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
                finish();
            }
            return false;
        }
        return true;
    }

    /**
     * @return Application's version code from the {@code PackageManager}.
     */
    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

}
