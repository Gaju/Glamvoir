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
    public static final String METHOD_MYPOST = "getmyposts";
    public static final String METHOD_GETPOST = "getPosts";
    public static final String METHOD_GETCOMMENT = "getcomments";
    public static final String METHOD_ADDCOMMENT = "addcomment";
    public static final String METHOD_FOLLOWER_FOLLOWING = "followerfollowing";
    public static final String METHOD_LIKED_USERS= "get_likeduser";
    public static final String METHOD_GET_CITY= "getcity";
    public static final String METHOD_ADD_POST= "addPosts";

    public static final String METHOD_GET_NOTIFICATION= "listnotification";

    public static final String METHOD_DELETE_SAVE_POST = "deletemysavedpost";
    public static final String METHOD_SAVEPOST = "savemypost";
    public static final String METHOD_LIKESTATUS = "likedislike";
    public static final String METHOD_GETPROFILE = "getprofile";
    public static final String METHOD_UPDATEPROFILE = "updateprofile";
    public static final String METHOD_REPORT_ABUSE = "report_abuse";

    public static final String METHOD_FORGOT_PASSWORD = "forgotpassword";
    public static final String METHOD_RESET_PASSWORD = "resetpassword";
    public static final String METHOD_UPDATE_PASSWORD = "updastepassword";


    public static final int GETPOST_LIKE = 11;
    public static final int GETPOST_FOLLOW = 22;

    public static final int REPORT = 1;
    public static final int ABUSE = 2;
    public static final int WRONGCATEGORY = 3;

    public static final int FOLLOWER = 1;
    public static final int FOLLOWING = 2;
    public static final int MY_SAVE = 3;
    public static final int MY_POST = 4;

    public static final String CATEGORY_ALL = "1";
    public static final String CATEGORY_FASHION = "2";
    public static final String CATEGORY_FOOD_PLACE = "3";
    public static final String CATEGORY_STORE_DEAL = "4";
    public static final String CATEGORY_INTEREST = "5";
    public static final String CATEGORY_FLEA_MARKET = "6";

    public static String getDeviceToken(Context context) {
        String deviceId = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return deviceId;
    }

    public static String getDeviceType() {
        return "Android";
    }

}
