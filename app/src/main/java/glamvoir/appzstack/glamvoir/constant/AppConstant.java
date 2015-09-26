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
    public static final String METHOD_GETPOST = "getPosts";

    public static final String METHOD_SAVEPOST = "savemypost";

    public static final String CATEGORY_ALL = "1";
    public static final String CATEGORY_FASHION = "2";
    public static final String CATEGORY_FOOD_PLACE = "3";
    public static final String CATEGORY_INTEREST = "4";
    public static final String CATEGORY_STORE_DEAL = "5";

    public static String getDeviceToken(Context context) {
        String deviceId = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return deviceId;
    }

    public static String getDeviceType() {
        return "Android";
    }

}
