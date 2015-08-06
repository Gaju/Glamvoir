package glamvoir.appzstack.glamvoir.asynctaskloader;

import android.content.AsyncTaskLoader;

import glamvoir.appzstack.glamvoir.helpers.Logger;
import glamvoir.appzstack.glamvoir.model.TaskResponse;
import glamvoir.appzstack.glamvoir.model.net.request.RequestBean;
import glamvoir.appzstack.glamvoir.model.net.response.LoginResponse;
import glamvoir.appzstack.glamvoir.network.Communication;
import glamvoir.appzstack.glamvoir.widgets.Dialog;


public class LoginLoader extends AsyncTaskLoader<TaskResponse<LoginResponse>> implements BaseLoader {
    private static final String TAG = LoginLoader.class.getSimpleName();
    private String email;
    private String password;
    private String deviceToken;
    private String deviceType;
    private String fName;
    private RequestBean requestBean;
    private Dialog dialog;
    public static final int LOGIN_GLAMVOIR = 0;
    public static final int LOGIN_FB = 1;
    public static final int LOGIN_GMAIL = 2;
    public static final int LOGIN_SIGNUP = 3;
    private int loginType;

    public LoginLoader(RequestBean requestBean, String email, String password, String fName, String deviceToken, String deviceType, int loginType) {
        super(requestBean.getContext());
        this.email = email;
        this.password = password;
        this.fName = fName;
        this.deviceToken = deviceToken;
        this.deviceType = deviceType;
        this.requestBean = requestBean;
        this.loginType = loginType;
        dialog=new Dialog();
    }

    @Override
    public TaskResponse<LoginResponse> loadInBackground() {
        TaskResponse response = new TaskResponse();

        try {
            Logger.push(Logger.LogType.LOG_DEBUG, TAG, email + "," + password + "," + deviceToken + "," + deviceType);
            switch (loginType) {
                case LOGIN_GLAMVOIR:
                    response.data = Communication.loginGlamvoir("user_login",email, password, deviceToken, deviceType);
                    break;
                case LOGIN_SIGNUP:
                    response.data = Communication.loginSignup("add_user",email, password, fName, "1",deviceToken, deviceType);
                    break;
            }

        } catch (Exception e) {
            response.error = e;
        }
        return response;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public void showLoaderDialog() {

        if (requestBean.isLoader()) {
            dialog.showProgressDialog(requestBean.getActivity(), "Loading", false);
        }
    }

    @Override
    public void hideLoaderDialog() {

        if (requestBean.isLoader()) {
            dialog.dismissDialog();
        }
    }
}