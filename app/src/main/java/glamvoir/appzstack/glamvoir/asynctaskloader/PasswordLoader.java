package glamvoir.appzstack.glamvoir.asynctaskloader;

import android.content.AsyncTaskLoader;

import glamvoir.appzstack.glamvoir.apppreference.AppPreferences;
import glamvoir.appzstack.glamvoir.constant.AppConstant;
import glamvoir.appzstack.glamvoir.model.TaskResponse;
import glamvoir.appzstack.glamvoir.model.net.request.RequestBean;
import glamvoir.appzstack.glamvoir.model.net.response.PasswordResponse;
import glamvoir.appzstack.glamvoir.network.Communication;

/**
 * Created by gajendran on 13/10/15.
 */
public class PasswordLoader extends AsyncTaskLoader<TaskResponse<PasswordResponse>> {

    private RequestBean requestBean;
    private TaskResponse response = null;
    private String oldPassword;
    private String newPassword;
    private String methodType;
    private String otp;
    private String emailSend;
    private String email;
    private String password;

    public PasswordLoader(RequestBean requestBean, String methodType, String oldPassword, String newPassword) {
        super(requestBean.getContext());
        this.requestBean = requestBean;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.methodType = methodType;
    }
//user_otp,user_password

    public PasswordLoader(RequestBean requestBean, String methodType, String emailSend) {
        super(requestBean.getContext());
        this.requestBean = requestBean;
        this.emailSend = emailSend;
        this.methodType = methodType;
    }


    public PasswordLoader(RequestBean requestBean,String methodType, String email,String otp,String password) {
        super(requestBean.getContext());
        this.methodType = methodType;
        this.email = email;
        this.otp = otp;
        this.password = password;

    }


    @Override
    public TaskResponse<PasswordResponse> loadInBackground() {
        response = new TaskResponse();
        try {
            String url = "http://glamvoir.com/index.php/api?method=" + oldPassword + "&newPassword=" + newPassword;
            AppPreferences appPreferences = new AppPreferences(requestBean.getContext());

            switch (methodType) {
                case AppConstant.METHOD_UPDATE_PASSWORD:
                    response.data = Communication.updatePassword(methodType, appPreferences.getUserId(), appPreferences.getEmailID(), oldPassword, newPassword);
                    break;
                case AppConstant.METHOD_RESET_PASSWORD:
                    response.data = Communication.resetPassword(methodType, emailSend, otp,password);
                    break;

                case AppConstant.METHOD_FORGOT_PASSWORD:
                    response.data = Communication.forgotPassword(methodType, emailSend);
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

}
