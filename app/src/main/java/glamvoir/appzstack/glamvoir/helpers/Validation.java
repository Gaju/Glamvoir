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
        if (pass != null && pass.length() >= 5) {
            return true;
        }
        return false;
    }

    public static boolean isTextEmpty(String message) {
        if (message.length() == 0)
            return true;
        return false;
    }

    public static boolean isValidName(String name) {
        if (name != null && name.length() >= 1) {
            return true;
        }
        return false;
    }

    public static boolean isValidMobile(String phone2) {
        boolean check;
        if (phone2.length() < 6 || phone2.length() > 13) {
            check = false;
        } else {
            check = true;
        }
        return check;
    }
}
