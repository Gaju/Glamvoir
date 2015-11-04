package glamvoir.appzstack.glamvoir.interfaces;

import glamvoir.appzstack.glamvoir.model.FFSP_Response;
import glamvoir.appzstack.glamvoir.model.PhotoUploadResponse;
import glamvoir.appzstack.glamvoir.model.net.response.AddPostResponse;
import glamvoir.appzstack.glamvoir.model.net.response.CityResponse;
import glamvoir.appzstack.glamvoir.model.net.response.CommentResponse;
import glamvoir.appzstack.glamvoir.model.net.response.GetPostLikeFollowResponse;
import glamvoir.appzstack.glamvoir.model.net.response.LikedUsers;
import glamvoir.appzstack.glamvoir.model.net.response.ListNotificationResponse;
import glamvoir.appzstack.glamvoir.model.net.response.LoginResponse;
import glamvoir.appzstack.glamvoir.model.net.response.ObservedFollowResponse;
import glamvoir.appzstack.glamvoir.model.net.response.PasswordResponse;
import glamvoir.appzstack.glamvoir.model.net.response.ProfileResponse;
import glamvoir.appzstack.glamvoir.model.net.response.ServerResponse;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Query;
import retrofit.mime.MultipartTypedOutput;
import retrofit.mime.TypedFile;
import retrofit.mime.TypedString;

/**
 * Created by acer pc on 02-08-2015.
 */
public interface GlamvoirService {

    // http://glamvoir.com/index.php/api

    // user_email, user_fname,user_device_token,user_device_type,user_password

    @FormUrlEncoded
    @POST("/index.php/api")
    LoginResponse loginSignup(@Field("method") String methodType, @Field("user_email") String email, @Field("user_password") String password, @Field("user_fname") String fName, @Field("user_lname") String lName, @Field("user_gender") String gender, @Field("user_device_token") String deviToken, @Field("user_device_type") String deviceType, @Query("user_gcm_token") String user_gcm_token);

    @GET("/index.php/api")
    LoginResponse loginGlamvoir(@Query("method") String methodType, @Query("user_email") String email, @Query("user_password") String password, @Query("user_device_token") String deviToken, @Query("user_device_type") String deviceType, @Query("user_gcm_token") String user_gcm_token);

    @GET("/index.php/api")
    FFSP_Response following(@Query("method") String methodType, @Query("user_id") String user_id);

    @GET("/index.php/api")
    ProfileResponse getProfile(@Query("method") String methodType, @Query("user_id") String user_id);

    @GET("/index.php/api")
    PasswordResponse updatePassword(@Query("method") String methodType, @Query("user_id") String user_id, @Query("user_email") String user_email, @Query("user_password") String user_password, @Query("user_password_new") String user_password_new);

    @GET("/index.php/api")
    PasswordResponse forgotPassword(@Query("method") String methodType, @Query("user_email") String user_email);

    @GET("/index.php/api")
    PasswordResponse resetPassword(@Query("method") String methodType, @Query("user_email") String user_email, @Query("user_otp") String user_otp, @Query("user_password") String user_password);

    @GET("/index.php/api")
    ProfileResponse updateProfile(@Query("method") String methodType, @Query("user_id") String user_id, @Query("user_fname") String user_fname, @Query("user_lname") String user_lname, @Query("user_dob") String user_dob, @Query("user_gender") String user_gender, @Query("user_about") String user_about, @Query("user_contact") String user_contact, @Query("user_city") String user_city);

    @GET("/index.php/api")
    ObservedFollowResponse toggleFollow(@Query("method") String methodType, @Query("following_user_id") String following_user_id, @Query("follower_user_id") String follower_user_id);

    @GET("/index.php/api")
    LikedUsers getLikedUsers(@Query("method") String methodType, @Query("user_id") String user_id, @Query("post_id") String post_id);

    @GET("/index.php/api")
    ListNotificationResponse getListNotification(@Query("method") String methodType, @Query("user_id") String user_id);

    @GET("/index.php/api")
    CityResponse getCity(@Query("method") String methodType);


    //user_id, post_parent_id, cat_id, post_gender, post_title, post_description, post_city, post_image,
    //post_video, post_end_date, post_location, post_lat, post_long


    @GET("/index.php/api")
    AddPostResponse add_Post_Without_Image(@Query("method") String methodType,
                                           @Query("user_id") String user_id,
                                           @Query("post_parent_id") String post_parent_id,
                                           @Query("cat_id") String cat_id,
                                           @Query("post_gender") String post_gender,
                                           @Query("post_title") String post_title,
                                           @Query("post_description") String post_description,
                                           @Query("post_city") String post_city,
                                           @Query("post_image") String post_image,
                                           @Query("post_video") String post_video,
                                           @Query("post_end_date") String post_end_date,
                                           @Query("post_location") String post_location,
                                           @Query("post_lat") String post_lat,
                                           @Query("post_long") String post_long);


    @Multipart
    @POST("/index.php/api")
    PhotoUploadResponse uploadPhoto(@Part("user_image") TypedFile file,
                                    @Part("method") TypedString methodName,
                                    @Part("user_id") TypedString user_id);

    @Multipart
    @POST("/index.php/api")
    PhotoUploadResponse uploadPhoto(@Body MultipartTypedOutput attachments);


    @Multipart
    @POST("/index.php/api")
    AddPostResponse add_Post_With_Image(@Part("post_image") TypedFile file,
                                        @Part("method") TypedString methodType,
                                        @Part("user_id") TypedString user_id,
                                        @Part("post_parent_id") TypedString post_parent_id,
                                        @Part("cat_id") TypedString cat_id,
                                        @Part("post_gender") TypedString post_gender,
                                        @Part("post_title") TypedString post_title,
                                        @Part("post_description") TypedString post_description,
                                        @Part("post_city") TypedString post_city,
                                        @Part("post_video") TypedString post_video,
                                        @Part("post_end_date") TypedString post_end_date,
                                        @Part("post_location") TypedString post_location,
                                        @Part("post_lat") TypedString post_lat,
                                        @Part("post_long") TypedString post_long);


    @GET("/index.php/api")
    GetPostLikeFollowResponse getPostFollow(@Query("method") String methodType, @Query("following_user_id") String following_user_id, @Query("follower_user_id") String follower_user_id);

    @GET("/index.php/api")
    FFSP_Response deleteMySave(@Query("method") String methodType, @Query("user_id") String user_id, @Query("post_id") String post_id);

    @GET("/index.php/api")
    GetPostLikeFollowResponse reportAbuse(@Query("method") String methodType, @Query("user_id") String user_id, @Query("user_fname") String user_fname, @Query("user_lname") String user_lname, @Query("post_id") String post_id, @Query("post_user_id") String post_user_id, @Query("ar_type") String ar_type);

    @GET("/index.php/api")
    ServerResponse savePost(@Query("method") String methodType, @Query("user_id") String user_id, @Query("post_id") String post_id);

    @GET("/index.php/api")
    GetPostLikeFollowResponse likeStatus(@Query("method") String methodType, @Query("user_id") String user_id, @Query("post_id") String post_id);

    @GET("/index.php/api")
    CommentResponse addComment(@Query("method") String methodType, @Query("user_id") String user_id, @Query("post_id") String post_id, @Query("comment") String comment);


    @GET("/index.php/api")
    CommentResponse getComment(@Query("method") String methodType, @Query("user_id") String user_id, @Query("post_id") String post_id);

}
