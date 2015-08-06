package glamvoir.appzstack.glamvoir.interfaces;

import glamvoir.appzstack.glamvoir.model.net.response.LoginResponse;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by acer pc on 02-08-2015.
 */
public interface GlamvoirService {

    // http://glamvoir.com/index.php/api

    // user_email, user_fname,user_device_token,user_device_type,user_password

    @FormUrlEncoded
    @POST("/index.php/api")
    LoginResponse loginSignup(@Field("method") String methodType, @Field("user_email") String email, @Field("user_password") String password, @Field("user_fname") String fName, @Field("user_gender") String gender, @Field("user_device_token") String deviToken, @Field("user_device_type") String deviceType);


    //user_email, user_device_token, user_device_type, user_password

    @GET("/index.php/api")
    LoginResponse loginGlamvoir(@Query("method") String methodType, @Query("user_email") String email, @Query("user_password") String password, @Query("user_device_token") String deviToken, @Query("user_device_type") String deviceType);

}
