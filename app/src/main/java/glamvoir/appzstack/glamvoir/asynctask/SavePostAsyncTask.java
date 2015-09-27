package glamvoir.appzstack.glamvoir.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import glamvoir.appzstack.glamvoir.interfaces.AsynTaskListener;
import glamvoir.appzstack.glamvoir.model.net.response.ServerResponse;
import glamvoir.appzstack.glamvoir.network.Communication;

/**
 * Created by gajendran on 26/9/15.
 */
public class SavePostAsyncTask extends AsyncTask<String, Void, Void> {
    private AsynTaskListener mAsynTaskListener;
    private String mAsynTaskId;
    private Context mContext;
    private ServerResponse response = new ServerResponse();

    public SavePostAsyncTask(Context mCtx, AsynTaskListener asT, String AsyntaskId) {
        mAsynTaskListener = asT;
        mAsynTaskId = AsyntaskId;
        this.mContext = mCtx;
    }

    @Override
    protected Void doInBackground(String... params) {
        try {
            if (mAsynTaskId.equals("server")) {
                String methodName = params[0];
                String userID = params[1];
                String postID = params[2];
                response = Communication.savePost(methodName, userID, postID);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        if (response != null) {
            if (response.error_code.equalsIgnoreCase("0")) {
                mAsynTaskListener.successWithresult(null, response.error_code, mAsynTaskId);
            } else {
                mAsynTaskListener.error(response.msg_string, response.error_code, mAsynTaskId);
            }
        } else {
            mAsynTaskListener.error(response.msg_string, response.error_code, mAsynTaskId);
        }
    }
}
