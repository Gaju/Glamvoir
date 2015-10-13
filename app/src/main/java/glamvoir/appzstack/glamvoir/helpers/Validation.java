package glamvoir.appzstack.glamvoir.helpers;

import android.text.TextUtils;

/**
 * Created by gajendran on 5/8/15.
 */
public class Validation {

    public final static boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public static boolean isValidPassword(String pass) {
        return pass != null && pass.length() >= 5;
    }
    public static boolean isGmailOTP(String pass) {
        return pass != null && pass.length() == 4;
    }

    public static boolean isTextEmpty(String message) {
        return message.length() == 0;
    }

    public static boolean isValidName(String name) {
        return name != null && name.length() >= 1;
    }

    public static boolean isValidMobile(String phone2) {
        boolean check;
        check = !(phone2.length() < 6 || phone2.length() > 13);
        return check;
    }
}
