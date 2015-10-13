package glamvoir.appzstack.glamvoir.asynctaskloader;

import android.content.AsyncTaskLoader;

import glamvoir.appzstack.glamvoir.config.AppConfig;
import glamvoir.appzstack.glamvoir.constant.AppConstant;
import glamvoir.appzstack.glamvoir.model.TaskResponse;
import glamvoir.appzstack.glamvoir.model.net.request.RequestBean;
import glamvoir.appzstack.glamvoir.model.net.response.ProfileResponse;
import glamvoir.appzstack.glamvoir.network.Communication;

/**
 * Created by gajendran on 1/10/15.
 */
public class ProfileLoader extends AsyncTaskLoader<TaskResponse<ProfileResponse>> {

    private RequestBean requestBean;
    private TaskResponse response = null;
    private String methodName;
    private String user_id;

    public ProfileLoader(RequestBean requestBean, String methodName, String user_id) {
        super(requestBean.getContext());
        this.requestBean = requestBean;
        this.methodName = methodName;
        this.user_id = user_id;
    }


    @Override
    public TaskResponse<ProfileResponse> loadInBackground() {
        response = new TaskResponse();
        try {
            String url = "http://glamvoir.com/index.php/api?method=" + methodName + "&user_id=" + user_id;

            switch (methodName) {
                case AppConstant.METHOD_GETPROFILE:
                    response.data = Communication.getProfile(methodName, user_id);
                    break;
                case AppConstant.METHOD_UPDATEPROFILE:
                    response.data = Communication.updateProfile(methodName, user_id);
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
