package glamvoir.appzstack.glamvoir.interfaces;

import glamvoir.appzstack.glamvoir.model.FFSP_Response;
import glamvoir.appzstack.glamvoir.model.PhotoUploadResponse;
import glamvoir.appzstack.glamvoir.model.net.response.CommentResponse;
import glamvoir.appzstack.glamvoir.model.net.response.DeleteMySaveResponse;
import glamvoir.appzstack.glamvoir.model.net.response.GetPostLikeFollowResponse;
import glamvoir.appzstack.glamvoir.model.net.response.LoginResponse;
import glamvoir.appzstack.glamvoir.model.net.response.ObservedFollowResponse;
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

/**
 * Created by acer pc on 02-08-2015.
 */
public interface GlamvoirService {

    // http://glamvoir.com/index.php/api

    // user_email, user_fname,user_device_token,user_device_type,user_password

    @FormUrlEncoded
    @POST("/index.php/api")
    LoginResponse loginSignup(@Field("method") String methodType, @Field("user_email") String email, @Field("user_password") String password, @Field("user_fname") String fName, @Field("user_gender") String gender, @Field("user_device_token") String deviToken, @Field("user_device_type") String deviceType);

    @GET("/index.php/api")
    LoginResponse loginGlamvoir(@Query("method") String methodType, @Query("user_email") String email, @Query("user_password") String password, @Query("user_device_token") String deviToken, @Query("user_device_type") String deviceType);

    @GET("/index.php/api")
    FFSP_Response following(@Query("method") String methodType, @Query("user_id") String user_id);

    @GET("/index.php/api")
    ObservedFollowResponse toggleFollow(@Query("method") String methodType, @Query("following_user_id") String following_user_id, @Query("follower_user_id") String follower_user_id);

    @GET("/index.php/api")
    GetPostLikeFollowResponse getPostFollow(@Query("method") String methodType, @Query("following_user_id") String following_user_id, @Query("follower_user_id") String follower_user_id);

    @GET("/index.php/api")
    DeleteMySaveResponse deleteMySave(@Query("method") String methodType, @Query("user_id") String user_id, @Query("post_id") String post_id);

    @GET("/index.php/api")
    ServerResponse savePost(@Query("method") String methodType, @Query("user_id") String user_id, @Query("post_id") String post_id);

    @GET("/index.php/api")
    GetPostLikeFollowResponse likeStatus(@Query("method") String methodType, @Query("user_id") String user_id, @Query("post_id") String post_id);


    @GET("/index.php/api")
    CommentResponse addComment(@Query("method") String methodType, @Query("user_id") String user_id, @Query("post_id") String post_id, @Query("comment") String comment);


    @GET("/index.php/api")
    CommentResponse getComment(@Query("method") String methodType, @Query("user_id") String user_id, @Query("post_id") String post_id);


    @Multipart
    @POST("/index.php/api")
    PhotoUploadResponse uploadPhoto(@Part("file") TypedFile file,
                                    @Part("method") String methodName,
                                    @Part("user_id") String user_id,
                                    @Part("user_image") String user_image);

    @Multipart
    @POST("/index.php/api")
    PhotoUploadResponse uploadPhoto(@Body MultipartTypedOutput attachments);

}
