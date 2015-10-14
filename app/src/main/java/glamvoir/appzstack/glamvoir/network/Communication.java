package glamvoir.appzstack.glamvoir.network;

import java.io.File;

import glamvoir.appzstack.glamvoir.interfaces.GlamvoirService;
import glamvoir.appzstack.glamvoir.model.FFSP_Response;
import glamvoir.appzstack.glamvoir.model.PhotoUploadResponse;
import glamvoir.appzstack.glamvoir.model.net.response.CommentResponse;
import glamvoir.appzstack.glamvoir.model.net.response.GetPostLikeFollowResponse;
import glamvoir.appzstack.glamvoir.model.net.response.LoginResponse;
import glamvoir.appzstack.glamvoir.model.net.response.ObservedFollowResponse;
import glamvoir.appzstack.glamvoir.model.net.response.PasswordResponse;
import glamvoir.appzstack.glamvoir.model.net.response.ProfileResponse;
import glamvoir.appzstack.glamvoir.model.net.response.ServerResponse;
import retrofit.mime.TypedFile;
import retrofit.mime.TypedString;

/**
 * Created by gajendran on 03-08-2015.
 */
public class Communication {

    public static LoginResponse loginSignup(String methodType, String emailId, String password, String fName, String lName, String gender, String deviceToken, String deviceType) {
        GlamvoirService service = RestAdapter.getGlamvoirService();
        LoginResponse response = service.loginSignup(methodType, emailId, password, fName, lName, gender, deviceToken, deviceType);
        return response;
    }

    public static LoginResponse loginGlamvoir(String methodType, String emailId, String password, String deviceToken, String deviceType) {
        GlamvoirService service = RestAdapter.getGlamvoirService();
        LoginResponse response = service.loginGlamvoir(methodType, emailId, password, deviceToken, deviceType);
        return response;
    }

    public static FFSP_Response _FFSP(String methodType, String userID) {
        GlamvoirService service = RestAdapter.getGlamvoirService();
        FFSP_Response response = service.following(methodType, userID);
        return response;
    }

    public static ProfileResponse getProfile(String methodType, String userID) {
        GlamvoirService service = RestAdapter.getGlamvoirService();
        ProfileResponse response = service.getProfile(methodType, userID);
        return response;
    }

    public static PasswordResponse updatePassword(String methodType, String userID, String email, String oldPassword, String newPassword) {
        GlamvoirService service = RestAdapter.getGlamvoirService();
        PasswordResponse response = service.updatePassword(methodType, userID, email, oldPassword, newPassword);
        return response;
    }

    public static PasswordResponse forgotPassword(String methodType, String email) {
        GlamvoirService service = RestAdapter.getGlamvoirService();
        PasswordResponse response = service.forgotPassword(methodType, email);
        return response;
    }

    public static PasswordResponse resetPassword(String methodType, String email, String otp, String password) {
        GlamvoirService service = RestAdapter.getGlamvoirService();
        PasswordResponse response = service.resetPassword(methodType, email, otp,password);
        return response;
    }


    public static ProfileResponse updateProfile(String methodType, String userID,String user_fname,String user_lname,String user_dob,String user_gender,String user_about,String user_contact,String user_city) {
        GlamvoirService service = RestAdapter.getGlamvoirService();
        ProfileResponse response = service.updateProfile(methodType, userID,user_fname, user_lname,user_dob, user_gender, user_about, user_contact, user_city);
        return response;
    }


    public static ObservedFollowResponse toggleFollow(String methodType, String followingUserID, String followerUserID) {
        GlamvoirService service = RestAdapter.getGlamvoirService();
        ObservedFollowResponse response = service.toggleFollow(methodType, followingUserID, followerUserID);
        return response;
    }


    public static GetPostLikeFollowResponse getPostFollow(String methodType, String followingUserID, String followerUserID) {
        GlamvoirService service = RestAdapter.getGlamvoirService();
        GetPostLikeFollowResponse response = service.getPostFollow(methodType, followingUserID, followerUserID);
        return response;
    }

    public static FFSP_Response deleteMySave(String methodType, String userID, String postID) {
        GlamvoirService service = RestAdapter.getGlamvoirService();
        FFSP_Response response = service.deleteMySave(methodType, userID, postID);
        return response;
    }

    public static ServerResponse savePost(String methodType, String userID, String postID) {
        GlamvoirService service = RestAdapter.getGlamvoirService();
        ServerResponse response = service.savePost(methodType, userID, postID);
        return response;
    }

    public static GetPostLikeFollowResponse likeStatus(String methodType, String userID, String postID) {
        GlamvoirService service = RestAdapter.getGlamvoirService();
        GetPostLikeFollowResponse response = service.likeStatus(methodType, userID, postID);
        return response;
    }

    public static CommentResponse addComment(String methodType, String userID, String postID, String comment) {
        GlamvoirService service = RestAdapter.getGlamvoirService();
        CommentResponse response = service.addComment(methodType, userID, postID, comment);
        return response;
    }

    public static CommentResponse getComment(String methodType, String userID, String postID) {
        GlamvoirService service = RestAdapter.getGlamvoirService();
        CommentResponse response = service.getComment(methodType, userID, postID);
        return response;
    }

    public static PhotoUploadResponse uploadImage(String methodType, String userID, String imagePath) {
        GlamvoirService service = RestAdapter.getGlamvoirService();

        File requestFilePath = new File(imagePath);
        String filename = imagePath.substring(imagePath.lastIndexOf("/") + 1);

//        MultipartTypedOutput multipartTypedOutput = new MultipartTypedOutput();
//        multipartTypedOutput.addPart("method", new TypedString(methodType));
//        multipartTypedOutput.addPart("user_id", new TypedString(userID));
//        multipartTypedOutput.addPart("user_image", new TypedString(filename));
//        multipartTypedOutput.addPart("file", new TypedFile("image/*", requestFilePath));


        // PhotoUploadResponse response = service.uploadPhoto(multipartTypedOutput);

        PhotoUploadResponse response = service.uploadPhoto(new TypedFile("image/*", requestFilePath), new TypedString(methodType), new TypedString(userID));
        return response;
    }
}
