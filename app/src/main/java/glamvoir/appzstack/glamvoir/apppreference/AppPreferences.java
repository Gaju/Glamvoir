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
        accessToken,
        emailID,
        userLastName
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

    public void setUserId(long userId) {
        mEditor.putLong(SharedPrefrencesKey.userID.toString(), userId);
        mEditor.commit();
    }

    public long getUserId() {
        return mPreferences.getLong(SharedPrefrencesKey.userID.toString(), 0);
    }

    public void setAccessToken(String accessToken) {
        mEditor.putString(SharedPrefrencesKey.accessToken.toString(), accessToken);
        mEditor.commit();
    }

    public String getAccessToken() {
        return mPreferences.getString(SharedPrefrencesKey.accessToken.toString(), null);
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

}

