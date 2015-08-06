package glamvoir.appzstack.glamvoir.widgets;

import android.app.Activity;
import android.view.Window;
import android.widget.TextView;

import glamvoir.appzstack.glamvoir.R;

/**
 * Created by gajendran on 03-08-2015.
 */
public class Dialog {

    public Dialog(){
    }

    private android.app.Dialog mProgressDialog;
    public boolean isShowingDialog = false;
    /**
     * This method is use to show progress dialog before hitting web service
     *
     * @param activity
     * @param loadingMessage
     * @param isCancelable
     * @return void
     */
    public void showProgressDialog(Activity activity, String loadingMessage, boolean isCancelable) {
        mProgressDialog = new android.app.Dialog(activity);
        mProgressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mProgressDialog.setContentView(R.layout.dialog_progress_indicator);
        ((TextView) mProgressDialog.findViewById(R.id.tv_text)).setText(loadingMessage);
        mProgressDialog.setCancelable(isCancelable);
        mProgressDialog.show();
        isShowingDialog = true;
    }


    /**
     * This method is use to dismiss progress dialog after web service task completes
     *
     * @param
     * @return void
     */
    public void dismissDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
            isShowingDialog =false;
        }
    }
}
