package glamvoir.appzstack.glamvoir.network;

import java.io.File;

import glamvoir.appzstack.glamvoir.interfaces.GlamvoirService;
import glamvoir.appzstack.glamvoir.model.FFSP_Response;
import glamvoir.appzstack.glamvoir.model.PhotoUploadResponse;
import glamvoir.appzstack.glamvoir.model.net.response.CommentResponse;
import glamvoir.appzstack.glamvoir.model.net.response.DeleteMySaveResponse;
import glamvoir.appzstack.glamvoir.model.net.response.LikeStatusResponse;
import glamvoir.appzstack.glamvoir.model.net.response.LoginResponse;
import glamvoir.appzstack.glamvoir.model.net.response.ObservedFollowResponse;
import glamvoir.appzstack.glamvoir.model.net.response.ServerResponse;
import retrofit.mime.MultipartTypedOutput;
import retrofit.mime.TypedFile;
import retrofit.mime.TypedString;

/**
 * Created by gajendran on 03-08-2015.
 */
public class Communication {

    public static LoginResponse loginSignup(String methodType, String emailId, String password, String fName, String gender, String deviceToken, String deviceType) {
        GlamvoirService service = RestAdapter.getGlamvoirService();
        LoginResponse response = service.loginSignup(methodType, emailId, password, fName, gender, deviceToken, deviceType);
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

    public static ObservedFollowResponse toggleFollow(String methodType, String followingUserID, String followerUserID) {
        GlamvoirService service = RestAdapter.getGlamvoirService();
        ObservedFollowResponse response = service.toggleFollow(methodType, followingUserID, followerUserID);
        return response;
    }


    public static DeleteMySaveResponse deleteMySave(String methodType, String userID, String postID) {
        GlamvoirService service = RestAdapter.getGlamvoirService();
        DeleteMySaveResponse response = service.deleteMySave(methodType, userID, postID);
        return response;
    }

    public static ServerResponse savePost(String methodType, String userID, String postID) {
        GlamvoirService service = RestAdapter.getGlamvoirService();
        ServerResponse response = service.savePost(methodType, userID, postID);
        return response;
    }

    public static LikeStatusResponse likeStatus(String methodType, String userID, String postID, String like_dislike_status, String action) {
        GlamvoirService service = RestAdapter.getGlamvoirService();
        LikeStatusResponse response = service.likeStatus(methodType, userID, postID, like_dislike_status, action);
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


        MultipartTypedOutput multipartTypedOutput = new MultipartTypedOutput();
        multipartTypedOutput.addPart("method", new TypedString(methodType));
        multipartTypedOutput.addPart("user_id", new TypedString(userID));
        multipartTypedOutput.addPart("user_image", new TypedString(filename));
        multipartTypedOutput.addPart("file", new TypedFile("image/*", requestFilePath));


        // PhotoUploadResponse response = service.uploadPhoto(multipartTypedOutput);

        PhotoUploadResponse response = service.uploadPhoto(new TypedFile("image/*", requestFilePath), methodType, userID, filename);
        return response;
    }
}
