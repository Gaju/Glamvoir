package glamvoir.appzstack.glamvoir.network;

import glamvoir.appzstack.glamvoir.interfaces.GlamvoirService;
import glamvoir.appzstack.glamvoir.model.FollowResponse;
import glamvoir.appzstack.glamvoir.model.net.response.LoginResponse;

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

    public static FollowResponse following(String methodType, String userID) {
        GlamvoirService service = RestAdapter.getGlamvoirService();
        FollowResponse response = service.following(methodType, userID);
        return response;
    }
}
