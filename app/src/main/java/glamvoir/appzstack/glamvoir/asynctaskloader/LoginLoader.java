package glamvoir.appzstack.glamvoir.asynctaskloader;

import android.content.AsyncTaskLoader;

import glamvoir.appzstack.glamvoir.apppreference.AppPreferences;
import glamvoir.appzstack.glamvoir.helpers.Logger;
import glamvoir.appzstack.glamvoir.model.TaskResponse;
import glamvoir.appzstack.glamvoir.model.net.request.RequestBean;
import glamvoir.appzstack.glamvoir.model.net.response.LoginResponse;
import glamvoir.appzstack.glamvoir.network.Communication;
import glamvoir.appzstack.glamvoir.widgets.ShowDialog;


public class LoginLoader extends AsyncTaskLoader<TaskResponse<LoginResponse>> implements BaseLoader {
    private static final String TAG = LoginLoader.class.getSimpleName();
    private String fgender;
    private String email;
    private String password;
    private String deviceToken;
    private String deviceType;
    private String fName;
    private String lName;
    private RequestBean requestBean;
    private ShowDialog dialog;
    public static final int LOGIN_GLAMVOIR = 0;
    public static final int LOGIN_FB = 1;
    public static final int LOGIN_GMAIL = 2;
    public static final int LOGIN_SIGNUP = 3;
    private int loginType;
    private TaskResponse response = null;
    AppPreferences appPreferences = null;

    public LoginLoader(RequestBean requestBean, String email, String password, String fName, String lName, String fgender, String deviceToken, String deviceType, int loginType) {
        super(requestBean.getContext());
        this.email = email;
        this.password = password;
        this.fName = fName;
        this.lName = lName;
        this.fgender = fgender;
        this.deviceToken = deviceToken;
        this.deviceType = deviceType;
        this.requestBean = requestBean;
        this.loginType = loginType;
        dialog = new ShowDialog();
        appPreferences = new AppPreferences(requestBean.getContext());
    }

    @Override
    public TaskResponse<LoginResponse> loadInBackground() {
        response = new TaskResponse();

        try {
            Logger.push(Logger.LogType.LOG_DEBUG, TAG, email + "," + password + "," + deviceToken + "," + deviceType);
            switch (loginType) {
                case LOGIN_GLAMVOIR:
                    response.data = Communication.loginGlamvoir("user_login", email, password, deviceToken, deviceType, appPreferences.getGcmID());
                    break;
                case LOGIN_SIGNUP:
                    response.data = Communication.loginSignup("add_user", email, password, fName, lName, fgender, deviceToken, deviceType, appPreferences.getGcmID());
                    break;
            }
        } catch (Exception e) {
            response.error = e;
        }
        return response;
    }

    @Override
    protected void onStartLoading() {
        if (response != null) {
            deliverResult(response);
        }

        if (response == null || takeContentChanged()) {
            showLoaderDialog();
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {

        cancelLoad();
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        response = null;
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