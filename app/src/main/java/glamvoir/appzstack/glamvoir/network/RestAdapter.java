package glamvoir.appzstack.glamvoir.network;

import android.util.Base64;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.apache.http.auth.AUTH;
import org.apache.http.protocol.HTTP;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;


import glamvoir.appzstack.glamvoir.BuildConfig;
import glamvoir.appzstack.glamvoir.config.AppConfig;
import glamvoir.appzstack.glamvoir.helpers.Logger;
import glamvoir.appzstack.glamvoir.interfaces.GlamvoirService;
import retrofit.RequestInterceptor;
import retrofit.converter.JacksonConverter;

public class RestAdapter {
    private static final String API_TAG="APIV2";
    public static Integer MAX_RETRY = 5;

    public static GlamvoirService service;

    public static GlamvoirService getGlamvoirService()
    {
        if(service == null)
        {
            service = RestAdapter.restAdapterGen(AppConfig.getDomain());
        }

        return service;
    }


    public static GlamvoirService restAdapterGen(String baseUrl)
    {
        retrofit.RestAdapter.Builder builder = new retrofit.RestAdapter.Builder();
        builder.setEndpoint(baseUrl);

//        RequestInterceptor requestInterceptor = new RequestInterceptor()
//        {
//            @Override
//            public void intercept(RequestFacade request) {
//                request.addHeader(HTTP.USER_AGENT, connection.userAgent);
//
//                request.addQueryParam("version", AppConfig.API_VERSION);
//                request.addQueryParam("token", AppConfig.deviceToken);
//                request.addQueryParam("app_android", "1");
//
//                if (!connection.authPass.equals(""))
//                {
//                    String userpassword = connection.authUser + ":" + connection.authPass;
//                    request.addHeader("Authorization", "basic " + new String(Base64.encode(userpassword.getBytes(), Base64.DEFAULT)));
//                }
//            }
//        };

        builder.setConverter(new JacksonConverter());
     //   builder.setRequestInterceptor(requestInterceptor);
        retrofit.RestAdapter restAdapter = builder.build();
        restAdapter.setLogLevel(true? retrofit.RestAdapter.LogLevel.FULL : retrofit.RestAdapter.LogLevel.NONE);

        GlamvoirService service = restAdapter.create(GlamvoirService.class);
        return service;
    }


//    public static String doGetQuery(String fullUrl) throws IOException {
//        String sResponse;
//        OkHttpClient client = new OkHttpClient();
//        Request request = getRequestForUrl(fullUrl);
//        Response response = client.newCall(request).execute();
//        sResponse = response.body().string();
//        Logger.push(Logger.LogType.LOG_API, API_TAG + " doGetQuery", "url: " + request.urlString() + "  response: " + sResponse);
//        return sResponse;
//    }
//
//    public static InputStream doGetInputStreamQuery(String fullUrl) throws IOException {
//        OkHttpClient client = new OkHttpClient();
//        Request request = getRequestForUrl(fullUrl);
//        Response response = client.newCall(request).execute();
//        Logger.push(Logger.LogType.LOG_API,API_TAG+" doGetInputStreamQuery","url: "+request.urlString());
//        return response.body().byteStream();
//    }
//
//    private static Request getRequestForUrl(String url)
//    {
//        Request.Builder builder = new Request.Builder();
//        builder.addHeader(HTTP.USER_AGENT, connection.userAgent);
//
//        if (!connection.authPass.equals(""))
//        {
//            String userpassword = connection.authUser + ":" + connection.authPass;
//            builder.addHeader("Authorization", "basic " + new String(Base64.encode(userpassword.getBytes(), Base64.DEFAULT)));
//        }
//
//        return builder.url(Helpers.prepareApiUrl(url)).build();
//    }
//
//    public static String doPostQuery(String fullUrl, java.util.Map<String, String> parameters) throws IOException {
//        String sResponse;
//        OkHttpClient client = new OkHttpClient();
//        Request request = getPostRequestForUrl(fullUrl, parameters);
//        Response response = client.newCall(request).execute();
//        sResponse = response.body().string();
//        Logger.push(Logger.LogType.LOG_API,API_TAG+" doPostQuery","url: "+request.urlString()+"  response: "+sResponse);
//        return sResponse;
//    }
//
//    private static Request getPostRequestForUrl(String url, java.util.Map<String, String> parameters)
//    {
//        FormEncodingBuilder postParams = new FormEncodingBuilder();
//        for(Map.Entry<String, String> entry : parameters.entrySet())
//        {
//            postParams.add(entry.getKey(), entry.getValue());
//        }
//
//        Request.Builder builder = new Request.Builder();
//        builder.addHeader(HTTP.USER_AGENT, connection.userAgent);
//        builder.post(postParams.build());
//
//
//        if (!connection.authPass.equals(""))
//        {
//            String userpassword = connection.authUser + ":" + connection.authPass;
//            builder.addHeader(AUTH.WWW_AUTH_RESP, "basic " + new String(Base64.encode(userpassword.getBytes(), Base64.DEFAULT)));
//        }
//
//        return builder.url(Helpers.prepareApiUrl(url)).build();
//    }
}