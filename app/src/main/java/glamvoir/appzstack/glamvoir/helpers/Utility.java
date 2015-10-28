package glamvoir.appzstack.glamvoir.helpers;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import glamvoir.appzstack.glamvoir.apppreference.AppPreferences;
import glamvoir.appzstack.glamvoir.model.net.response.ProfileResponse;
import glamvoir.appzstack.glamvoir.model.net.response.UserDetails;

/**
 * Created by anupam on 5/8/15.
 */
public class Utility {

    public static void hideKeyboard(Context context, EditText editText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    /**
     * This method is used to Toast a message from anywhere in application.
     */
    public static void showToast(Context context, String toastMessage) {
        Toast.makeText(context, toastMessage, Toast.LENGTH_LONG).show();
    }


    public static void saveUserData(Context context, List<UserDetails> userDetailsList) {
        UserDetails userDetails = userDetailsList.get(0);
        AppPreferences mAppPreferences = new AppPreferences(context);
        mAppPreferences.setAuthToken(userDetails.user_authtoken);
        mAppPreferences.setEmailID(userDetails.user_email);
        mAppPreferences.setFirstName(userDetails.user_fname);
        mAppPreferences.setLastName(userDetails.user_lname);
        mAppPreferences.setUserId(userDetails.user_id);
        mAppPreferences.setUserImage(userDetails.user_image);
        mAppPreferences.setGender(userDetails.user_gender);
        mAppPreferences.setUserAbout(userDetails.user_about);
        //mAppPreferences.setUserContact(userDetails.user_contact);
        //mAppPreferences.setUserCity(userDetails.user_city);
    }

    public static void updateUserData(Context context, List<ProfileResponse.GetProfileResponse> userDetailsList) {
        ProfileResponse.GetProfileResponse userDetails = userDetailsList.get(0);
        AppPreferences mAppPreferences = new AppPreferences(context);
        mAppPreferences.setFirstName(userDetails.user_fname);
        mAppPreferences.setLastName(userDetails.user_lname);
        mAppPreferences.setUserId(userDetails.user_id);

        mAppPreferences.setGender(userDetails.user_gender);
        mAppPreferences.setUserAbout(userDetails.user_about);
        mAppPreferences.setUserContact(userDetails.user_contact);
        mAppPreferences.setUserCity(userDetails.user_city);
    }

    public static boolean shouldEnableCacheOnMemory() {
        if (Build.MODEL.equalsIgnoreCase("GT-N7100") || (Build.MANUFACTURER.equalsIgnoreCase("SAMSUNG") && android.os.Build.VERSION.RELEASE.equalsIgnoreCase("4.4.2"))) {
            return false;
        }
        return true;
    }

    /**
     * @return True if the external storage is available.
     * False otherwise.
     */
    public static boolean checkAvailable() {

        // Retrieving the external storage state
        String state = Environment.getExternalStorageState();

        // Check if available
        if (Environment.MEDIA_MOUNTED.equals(state)
                || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    /**
     * method to get current date and time
     *
     * @return
     */
    public static String getDateTime(boolean seperator) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf;
        if (seperator)
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        else
            sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(c.getTime());
    }
}
