package glamvoir.appzstack.glamvoir.constant;

import android.content.Context;
import android.provider.Settings;

/**
 * Created by anupam on 4/8/15.
 */
public class AppConstant {

    public static String PACKAGE = "glamvoir.appzstack.glamvoir.activity.";
    public static final String METHOD_FOLLOWING = "getfollowing";
    public static final String METHOD_FOLLOWER = "getfollower";
    public static final String METHOD_MYSAVE = "mysavedpost";
    public static final String METHOD_UPDATE_IMAGE = "updateImage";
    public static final String METHOD_MYPOST = "";

    public static String getDeviceToken(Context context) {
        String deviceId = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return deviceId;
    }

    public static String getDeviceType() {
        return "Android";
    }

}
