package glamvoir.appzstack.glamvoir.apppreference;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * Created by gajendran on 03-08-2015..
 * This is singleton class for SharedPreference of application
 */
public class AppPreferences {

    public static AppPreferences mAppPreference;
    private SharedPreferences mPreferences;
    private Editor mEditor;

    /**
     * Enum tha wrap the various key that will be used in app shared preferences
     */
    enum SharedPrefrencesKey {
        userID,
        userFirstName,
        authToken,
        emailID,
        userLastName,
        deviceToken,
        deviceType,
        firstTime,
        gender
    }

    public AppPreferences(Context context) {
        mPreferences = context.getSharedPreferences(PreferenceID.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        mEditor = mPreferences.edit();
    }

    public static synchronized AppPreferences getInstance(Context context) {
        if (mAppPreference == null) {
            mAppPreference = new AppPreferences(context);
        }
        return mAppPreference;
    }

    public void clearAppPreference() {
        if (mPreferences != null) {
            mEditor.clear().commit();
        }
    }

    public void setUserId(String userId) {
        mEditor.putString(SharedPrefrencesKey.userID.toString(), userId);
        mEditor.commit();
    }

    public String getUserId() {
        return mPreferences.getString(SharedPrefrencesKey.userID.toString(), null);
    }

    public void setAuthToken(String accessToken) {
        mEditor.putString(SharedPrefrencesKey.authToken.toString(), accessToken);
        mEditor.commit();
    }

    public String getAuthToken() {
        return mPreferences.getString(SharedPrefrencesKey.authToken.toString(), null);
    }

    public void setFirstName(String fName) {
        mEditor.putString(SharedPrefrencesKey.userFirstName.toString(), fName);
        mEditor.commit();
    }

    public String getFirstName() {
        return mPreferences.getString(SharedPrefrencesKey.userFirstName.toString(), null);
    }

    public void setEmailID(String emailID) {
        mEditor.putString(SharedPrefrencesKey.emailID.toString(), emailID);
        mEditor.commit();
    }

    public String getEmailID() {
        return mPreferences.getString(SharedPrefrencesKey.emailID.toString(), null);
    }

    public void setLastName(String lName) {
        mEditor.putString(SharedPrefrencesKey.userLastName.toString(), lName);
        mEditor.commit();
    }

    public String getLastName() {
        return mPreferences.getString(SharedPrefrencesKey.userLastName.toString(), null);
    }

    public void setDeviceType(String deviceType) {
        mEditor.putString(SharedPrefrencesKey.deviceType.toString(), deviceType);
        mEditor.commit();
    }

    public String getDeviceType() {
        return mPreferences.getString(SharedPrefrencesKey.deviceType.toString(), null);
    }

    public void setDeviceToken(String deviceToken) {
        mEditor.putString(SharedPrefrencesKey.deviceToken.toString(), deviceToken);
        mEditor.commit();
    }

    public String getDeviceToken() {
        return mPreferences.getString(SharedPrefrencesKey.deviceToken.toString(), null);
    }

    public void setFirstTime(boolean mFirstTime) {
        mEditor.putBoolean(SharedPrefrencesKey.firstTime.toString(), mFirstTime);
        mEditor.commit();
    }

    public boolean getFirstTime() {
        return mPreferences.getBoolean(SharedPrefrencesKey.firstTime.toString(), true);
    }

    public String getGender() {
        return mPreferences.getString(SharedPrefrencesKey.gender.toString(), null);
    }

    public void setGender(String gender) {
        mEditor.putString(SharedPrefrencesKey.gender.toString(), gender);
        mEditor.commit();
    }
}

