package glamvoir.appzstack.glamvoir.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import java.io.InputStream;

import glamvoir.appzstack.glamvoir.R;
import glamvoir.appzstack.glamvoir.apppreference.AppPreferences;
import glamvoir.appzstack.glamvoir.constant.AppConstant;
import glamvoir.appzstack.glamvoir.network.InternetStatus;


/**
 * Created by jaim on 7/21/2015.
 */
public class FrontPageActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final int RC_SIGN_IN = 0;
    // Logcat tag
    private static final String TAG = "MainActivity";

    // Profile pic image size in pixels
    private static final int PROFILE_PIC_SIZE = 400;

    // Google client to interact with Google API
    private GoogleApiClient mGoogleApiClient;

    /**
     * A flag indicating that a PendingIntent is in progress and prevents us
     * from starting further intents.
     */
    private boolean mIntentInProgress;

    private boolean mSignInClicked;

    private ConnectionResult mConnectionResult;

    private Button btnSignIn, btn_SignUp, btn_LoginGmain;
    private CoordinatorLayout coordinatorLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        checkUserSession();

        //initialize all views
        initViews();

        // initializeall views listeners
        initListeners();

        saveDeviceToken();

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
        btnSignIn = (Button) findViewById(R.id.frontpage_signin);
        btn_SignUp = (Button) findViewById(R.id.frontpage_signup);
        btn_LoginGmain = (Button) findViewById(R.id.login_gmail);
    }

    /**
     * initializeall views listeners
     */
    private void initListeners() {

        btnSignIn.setOnClickListener(this);
        btn_SignUp.setOnClickListener(this);
        btn_LoginGmain.setOnClickListener(this);
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

        // Get user's information
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
}
