package glamvoir.appzstack.glamvoir.asynctaskloader;

import android.content.AsyncTaskLoader;

import glamvoir.appzstack.glamvoir.model.FollowResponse;
import glamvoir.appzstack.glamvoir.model.TaskResponse;
import glamvoir.appzstack.glamvoir.model.net.request.RequestBean;
import glamvoir.appzstack.glamvoir.network.Communication;

import static glamvoir.appzstack.glamvoir.constant.AppConstant.FLAG_FOLLOWER;
import static glamvoir.appzstack.glamvoir.constant.AppConstant.FLAG_FOLLOWING;

/**
 * Created by gajendran on 10/9/15.
 */
public class FollowLoader extends AsyncTaskLoader<TaskResponse<FollowResponse>> implements BaseLoader {

    private RequestBean requestBean;
    private TaskResponse response = null;
    private int flag_Type;
    private String user_id;

    public FollowLoader(RequestBean requestBean, int flag, String user_id) {
        super(requestBean.getContext());
        this.requestBean = requestBean;
        this.flag_Type = flag;
        this.user_id = user_id;
    }


    @Override
    public TaskResponse<FollowResponse> loadInBackground() {
        response = new TaskResponse();

        try {
            //Logger.push(Logger.LogType.LOG_DEBUG, TAG, email + "," + password + "," + deviceToken + "," + deviceType);
            switch (flag_Type) {
                case FLAG_FOLLOWER:
                    //      response.data = Communication.loginGlamvoir("user_login", email, password, deviceToken, deviceType);
                    break;
                case FLAG_FOLLOWING:
                    response.data = Communication.following("getfollowing", user_id);
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

    }

    @Override
    public void hideLoaderDialog() {

    }
}
