package glamvoir.appzstack.glamvoir.network;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import glamvoir.appzstack.glamvoir.R;



/**
 * Created by Created by gajendran on 23/4/15.
 * This class is used to hit the webservice
 */
public class NetworkCall {

    private static NetworkCall networkCall;
    private Dialog mProgressDialog;
    public boolean isShowingDialog = false;
    private NetworkCall(Context context) {

    }

    public static synchronized NetworkCall getInstance(Context context) {
        if (networkCall == null) {
            networkCall = new NetworkCall(context);
        }
        return networkCall;
    }

    /**
     * This method is used to get response from webservice using get method
     */
    public String getResponseFromServer(String url) {
        //AppLogger.e("Requested URL "+url);
        String response_string = null;
        url = url.trim();
        url = url.replace(" ", "%20");
        HttpClient httpClient = new DefaultHttpClient();
        HttpConnectionParams.setConnectionTimeout(httpClient.getParams(), 0);
        HttpConnectionParams.setSoTimeout(httpClient.getParams(), 0);
        HttpResponse httpResponse;
        try {
            HttpGet httpGet = new HttpGet(url);
            httpResponse = httpClient.execute(httpGet);
            if (httpResponse != null) {
                InputStream inputStream = httpResponse.getEntity().getContent();
                response_string = convertInputStreamToString(inputStream);
               // AppLogger.e("Response " + response_string);
                //  return response_string;
            } else {
                return null;
            }
        } catch (IOException e) {
            response_string = null;
            e.printStackTrace();
        }
        return response_string;
    }


    /**
     * This method is use to show progress dialog before hitting web service
     *
     * @param activity
     * @param loadingMessage
     * @param isCancelable
     * @return void
     */
    public void showProgressDialog(Activity activity, String loadingMessage, boolean isCancelable) {
        mProgressDialog = new Dialog(activity);
        mProgressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mProgressDialog.setContentView(R.layout.dialog_progress_indicator);
        TextView mDialogTV=(TextView)mProgressDialog.findViewById(R.id.tv_text);

        mDialogTV.setText(loadingMessage);
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

    public String convertInputStreamToString(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
        } catch (IOException e) {
            return "IOException";
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return stringBuilder.toString();
    }
}
