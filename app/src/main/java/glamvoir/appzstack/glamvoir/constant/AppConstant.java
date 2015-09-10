package glamvoir.appzstack.glamvoir.constant;

import android.content.Context;
import android.provider.Settings;

/**
 * Created by anupam on 4/8/15.
 */
public class AppConstant {

    public static String PACKAGE = "glamvoir.appzstack.glamvoir.activity.";
    public static final int FLAG_FOLLOWING = 1;
    public static final int FLAG_FOLLOWER = 2;

    public static String getDeviceToken(Context context) {
        String deviceId = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return deviceId;
    }

    public static String getDeviceType() {
        return "Android";
    }

}
