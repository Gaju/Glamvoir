package glamvoir.appzstack.glamvoir.helpers;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by anupam on 5/8/15.
 */
public class Utility {

    public static void hideKeyboard(Context context, EditText editText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

}
