package glamvoir.appzstack.glamvoir.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import glamvoir.appzstack.glamvoir.interfaces.AsynTaskListener;
import glamvoir.appzstack.glamvoir.model.FFSP_Response;
import glamvoir.appzstack.glamvoir.model.net.response.DeleteMySaveResponse;
import glamvoir.appzstack.glamvoir.network.Communication;

/**
 * Created by gajendran on 28/9/15.
 */
public class DeleteMySaveAsyncTask extends AsyncTask<String, Void, Void> {
    private AsynTaskListener mAsynTaskListener;
    private String mAsynTaskId;
    private Context mContext;
    private FFSP_Response response = new FFSP_Response();
    List<Object> mLIList = new ArrayList<Object>();

    public DeleteMySaveAsyncTask(Context mCtx, AsynTaskListener asT, String AsyntaskId) {
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
                
                response = Communication.deleteMySave(methodName, userID, postID);
                mLIList.add(response);

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
                mAsynTaskListener.successWithresult(mLIList, response.error_code, mAsynTaskId);
            } else {
                mAsynTaskListener.error(response.msg_string, response.error_code, mAsynTaskId);
            }
        } else {
            mAsynTaskListener.error(response.msg_string, response.error_code, mAsynTaskId);
        }
    }
}