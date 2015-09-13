package glamvoir.appzstack.glamvoir.network;

import java.io.File;

import glamvoir.appzstack.glamvoir.interfaces.GlamvoirService;
import glamvoir.appzstack.glamvoir.model.FFSP_Response;
import glamvoir.appzstack.glamvoir.model.PhotoUploadResponse;
import glamvoir.appzstack.glamvoir.model.net.response.LoginResponse;
import glamvoir.appzstack.glamvoir.model.net.response.ObservedFollowResponse;
import retrofit.mime.TypedFile;

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

    public static PhotoUploadResponse uploadImage(String methodType, String userID, String imagePath) {
        GlamvoirService service = RestAdapter.getGlamvoirService();

        File requestFilePath = new File(imagePath);
        String filename = imagePath.substring(imagePath.lastIndexOf("/") + 1);

        PhotoUploadResponse response = service.uploadPhoto(new TypedFile("image/jpeg", requestFilePath), methodType, userID, filename);
        return response;
    }
}
