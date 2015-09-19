package glamvoir.appzstack.glamvoir.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import glamvoir.appzstack.glamvoir.interfaces.AsynTaskListener;
import glamvoir.appzstack.glamvoir.model.PhotoUploadResponse;
import glamvoir.appzstack.glamvoir.network.Communication;


public class PhotoUploadAsyncTask extends AsyncTask<String, Void, Void> {
    private AsynTaskListener mAsynTaskListener;
    private String mAsynTaskId;
    List<Object> mLIList = new ArrayList<Object>();
    private Context mContext;
    private PhotoUploadResponse photoUploadResponse = new PhotoUploadResponse();

    public PhotoUploadAsyncTask(Context mCtx, AsynTaskListener asT, String AsyntaskId) {
        mAsynTaskListener = asT;
        mAsynTaskId = AsyntaskId;
        this.mContext = mCtx;
    }

    @Override
    protected Void doInBackground(String... params) {
        try {
            if (mAsynTaskId.equals("photo_upload")) {
                String methodName = params[0];
                String userID = params[1];
                String userImagePath = params[2];

                photoUploadResponse = Communication.uploadImage(methodName, userID, userImagePath);

                //String webservice = AppNetworkConstants.PRODUCT_DETAIL + SLASH + URLEncoder.encode(mProductType, "UTF-8") + SLASH + URLEncoder.encode(mProductCode, "UTF-8") + "?DocumentID=" + URLEncoder.encode(mSearhValue, "UTF-8");
                //serviceResult = NetworkCall.getInstance(mContext).getResponseFromServer(webservice);
                //ParserClass parserClass = ParserClass.getsInstance(mContext);
                //photoUploadResponse = parserClass.parseProductDetailResponse(serviceResult, photoUploadResponse);
                //photoUploadResponse.setSearchKey(mSearhedKey);

                mLIList.add(photoUploadResponse);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        if (photoUploadResponse != null) {
            if (photoUploadResponse.error_code.equalsIgnoreCase("0")) {
                mAsynTaskListener.successWithresult(mLIList, photoUploadResponse.error_code + "", mAsynTaskId);
            } else {
                mAsynTaskListener.error(photoUploadResponse.msg_string, photoUploadResponse.error_code + "", mAsynTaskId);
            }
        } else {
            mAsynTaskListener.error(photoUploadResponse.msg_string, photoUploadResponse.error_code + "", mAsynTaskId);
        }
    }
}
