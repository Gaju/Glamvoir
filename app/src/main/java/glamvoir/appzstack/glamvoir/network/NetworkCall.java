package glamvoir.appzstack.glamvoir.network;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.List;

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

    public String getForgortPasswordResponse(StringEntity entity,String url){
        try {
            HttpParams httpParameters = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParameters, 30000);
            HttpConnectionParams.setSoTimeout(httpParameters, 30000);

            HttpClient httpclient = new DefaultHttpClient(httpParameters);
            HttpPost httppost = new HttpPost(url);
            // Add your data
            httppost.setEntity(entity);
            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            String jsonResponce=convertStreamToString(response.getEntity().getContent());
            Log.i("Network Call" + " RESPONCE ENTITY", "" + jsonResponce);
            return jsonResponce;

        } catch(SocketTimeoutException e){
            return "connection time out";
        }catch (ClientProtocolException e) {
            e.getMessage();
        } catch (IOException e) {
            e.getMessage();
        }
        return null;
    }

    /**
     * This method is used to get response from webservice using get method
     */
    public String getResponseFromServer(String url, String token) {
        String responseEntity = null;
        url = url.trim();
        url = url.replace(" ", "%20");
        HttpClient httpClient = new DefaultHttpClient();
        HttpConnectionParams.setConnectionTimeout(httpClient.getParams(), 0);
        HttpConnectionParams.setSoTimeout(httpClient.getParams(), 0);
        HttpResponse httpResponse;
        try {
            HttpGet httpGet = new HttpGet(url);
            httpGet.addHeader("Content-Type", "latin1_swedish_ci");
            httpGet.addHeader("accessToken", token);
            httpResponse = httpClient.execute(httpGet);
            if (httpResponse != null) {
                InputStream inputStream = httpResponse.getEntity().getContent();
                String response_string = convertInputStreamToString(inputStream);
                return response_string;
            } else {
                return null;
            }
        } catch (IOException e) {
            responseEntity = "IOException";
            e.printStackTrace();
        }
        return responseEntity;
    }


    public String getDeleteResponseFromServer(String url, String token,String cardID) {
        String responseEntity = null;
        url = url.trim();
        url = url.replace(" ", "%20");
        HttpClient httpClient = new DefaultHttpClient();
        HttpConnectionParams.setConnectionTimeout(httpClient.getParams(), 0);
        HttpConnectionParams.setSoTimeout(httpClient.getParams(), 0);
        HttpResponse httpResponse;
        try {
            HttpDelete httpDelete  = new HttpDelete(url);
            httpDelete.addHeader("Content-Type", "latin1_swedish_ci");
            httpDelete.addHeader("accessToken", token);
            httpDelete.addHeader("CardID",cardID);
            httpResponse = httpClient.execute(httpDelete);
            if (httpResponse != null) {
                InputStream inputStream = httpResponse.getEntity().getContent();
                String response_string = convertInputStreamToString(inputStream);
                return response_string;
            } else {
                return null;
            }
        } catch (IOException e) {
            responseEntity = "IOException";
            e.printStackTrace();
        }
        return responseEntity;
    }




    public String getResponseFromServer(String url, String token,String appType) {
        String responseEntity = null;
        url = url.trim();
        url = url.replace(" ", "%20");
        HttpClient httpClient = new DefaultHttpClient();
        HttpConnectionParams.setConnectionTimeout(httpClient.getParams(), 0);
        HttpConnectionParams.setSoTimeout(httpClient.getParams(), 0);
        HttpResponse httpResponse;
        try {
            HttpGet httpGet = new HttpGet(url);
            httpGet.addHeader("Content-Type", "latin1_swedish_ci");
            httpGet.addHeader("accessToken", token);
            httpGet.addHeader("TypeID",appType);
            httpResponse = httpClient.execute(httpGet);
            if (httpResponse != null) {
                InputStream inputStream = httpResponse.getEntity().getContent();
                String response_string = convertInputStreamToString(inputStream);
                return response_string;
            } else {
                return null;
            }
        } catch (IOException e) {
            responseEntity = "IOException";
            e.printStackTrace();
        }
        return responseEntity;
    }

    /**
     * This method is used to response from webservice using post method and param in key-value pairs
     *
     * @param url
     * @param nameValuePairList
     * @return response
     */
    public String getResponseFromServer(String url, List<NameValuePair> nameValuePairList) {
        String responseEntity = null;
        url = url.trim();
        url = url.replace(" ", "%20");
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpConnectionParams.setConnectionTimeout(httpClient.getParams(), 0);
            HttpConnectionParams.setSoTimeout(httpClient.getParams(), 0);
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairList, "UTF-8"));
            HttpResponse httpResponse = httpClient.execute(httpPost);
            responseEntity = EntityUtils.toString(httpResponse.getEntity());
        } catch (UnsupportedEncodingException e) {
            responseEntity = "UnsupportedEncodingException";
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            responseEntity = "ClientProtocolException";
            e.printStackTrace();
        } catch (IOException e) {
            responseEntity = "ClientProtocolException";
            e.printStackTrace();
        }
        return responseEntity;
    }

    /**
     * this method is use to hit web service
     *
     * @param url
     * @param jsonObject return String
     */
    public String getResponseFromServer(String url, JSONObject jsonObject) {
        String responseEntity = null;
        url = url.trim();
        url = url.replace(" ", "%20");
        HttpClient client = new DefaultHttpClient();
        HttpConnectionParams.setConnectionTimeout(client.getParams(), 0);
        HttpConnectionParams.setSoTimeout(client.getParams(), 0);
        HttpResponse response;
        try {
            HttpPost post = new HttpPost(url);
            StringEntity entity = new StringEntity(jsonObject.toString());
            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            post.setEntity(entity);
            response = client.execute(post);
            /*Checking response */
            if (response != null) {
                InputStream inputStream = response.getEntity().getContent(); //Get the data in the entity
                String response_string = convertInputStreamToString(inputStream);

                return response_string;
            } else {
                return null;
            }

        } catch (UnsupportedEncodingException e) {
            responseEntity = "UnsupportedEncodingException";
            e.printStackTrace();
        } catch (ClientProtocolException e1) {
            responseEntity = "ClientProtocolException";
            e1.printStackTrace();
        } catch (IOException e) {
            responseEntity = "IOException";
            e.printStackTrace();
        }

        return responseEntity;
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

    public static String getJSONString(String url) {
        String jsonString = null;
        HttpURLConnection linkConnection = null;
        try {

            URL linkurl = new URL(url);
            linkConnection = (HttpURLConnection) linkurl.openConnection();
            linkConnection
                    .setRequestProperty(
                            "User-Agent",
                            "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
            int responseCode = linkConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream linkinStream = linkConnection.getInputStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                int j = 0;
                while ((j = linkinStream.read()) != -1) {
                    baos.write(j);
                }
                byte[] data = baos.toByteArray();
                jsonString = new String(data);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (linkConnection != null) {
                linkConnection.disconnect();
            }
        }
        return jsonString;
    }

    /**
     * this method is use to hit web service
     *
     * @param url
     * @param jsonObject return String
     */
    public String getResponseFromServer(String url, JSONObject jsonObject,String token) {
        String responseEntity = null;
        url = url.trim();
        url = url.replace(" ", "%20");
        HttpClient client = new DefaultHttpClient();
        HttpConnectionParams.setConnectionTimeout(client.getParams(), 0);
        HttpConnectionParams.setSoTimeout(client.getParams(), 0);
        HttpResponse response;
        try {
            HttpPost post = new HttpPost(url);
            StringEntity entity = new StringEntity(jsonObject.toString());
            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            post.setEntity(entity);
            post.setHeader("Content-Type", "latin1_swedish_ci");
            post.setHeader("accessToken", token);
            response = client.execute(post);
            /*Checking response */
            if (response != null) {
                InputStream inputStream = response.getEntity().getContent(); //Get the data in the entity
                String response_string = convertInputStreamToString(inputStream);

                return response_string;
            } else {
                return null;
            }

        } catch (UnsupportedEncodingException e) {
            responseEntity = "UnsupportedEncodingException";
            e.printStackTrace();
        } catch (ClientProtocolException e1) {
            responseEntity = "ClientProtocolException";
            e1.printStackTrace();
        } catch (IOException e) {
            responseEntity = "IOException";
            e.printStackTrace();
        }

        return responseEntity;
    }

    public String getResponseFromServer(String url, JSONObject jsonObject,String accessToken, long userId) {
        String responseEntity = null;
        url = url.trim();
        url = url.replace(" ", "%20");
        HttpClient client = new DefaultHttpClient();
        HttpConnectionParams.setConnectionTimeout(client.getParams(), 0);
        HttpConnectionParams.setSoTimeout(client.getParams(), 0);
        HttpResponse response;
        try {
            HttpPost post = new HttpPost(url);
            StringEntity entity = new StringEntity(jsonObject.toString());
            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            post.setEntity(entity);
            post.setHeader("Content-Type", "latin1_swedish_ci");
            post.setHeader("accessToken", accessToken);
            post.setHeader("UserID", Long.toString(userId));
            response = client.execute(post);
            /*Checking response */
            if (response != null) {
                InputStream inputStream = response.getEntity().getContent(); //Get the data in the entity
                String response_string = convertInputStreamToString(inputStream);

                return response_string;
            } else {
                return null;
            }

        } catch (UnsupportedEncodingException e) {
            responseEntity = "UnsupportedEncodingException";
            e.printStackTrace();
        } catch (ClientProtocolException e1) {
            responseEntity = "ClientProtocolException";
            e1.printStackTrace();
        } catch (IOException e) {
            responseEntity = "IOException";
            e.printStackTrace();
        }

        return responseEntity;
    }


    public String getResponseFromServer(String url, String token,long userID) {
        String responseEntity = null;
        url = url.trim();
        url = url.replace(" ", "%20");
        HttpClient httpClient = new DefaultHttpClient();
        HttpConnectionParams.setConnectionTimeout(httpClient.getParams(), 0);
        HttpConnectionParams.setSoTimeout(httpClient.getParams(), 0);
        HttpResponse httpResponse;
        try {
            HttpGet httpGet = new HttpGet(url);
            httpGet.addHeader("Content-Type", "latin1_swedish_ci");
            httpGet.addHeader("accessToken", token);
            httpGet.addHeader("TypeID", Long.toString(userID));
            httpResponse = httpClient.execute(httpGet);
            if (httpResponse != null) {
                InputStream inputStream = httpResponse.getEntity().getContent();
                String response_string = convertInputStreamToString(inputStream);
                return response_string;
            } else {
                return null;
            }
        } catch (IOException e) {
            responseEntity = "IOException";
            e.printStackTrace();
        }
        return responseEntity;
    }



    private static String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append((line + "\n"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

}
