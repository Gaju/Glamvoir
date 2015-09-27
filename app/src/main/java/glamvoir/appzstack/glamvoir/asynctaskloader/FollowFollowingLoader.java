package glamvoir.appzstack.glamvoir.asynctaskloader;

import android.content.AsyncTaskLoader;
import android.content.Context;

import glamvoir.appzstack.glamvoir.constant.AppConstant;
import glamvoir.appzstack.glamvoir.model.TaskResponse;
import glamvoir.appzstack.glamvoir.model.net.request.RequestBean;
import glamvoir.appzstack.glamvoir.model.net.response.ObservedFollowResponse;
import glamvoir.appzstack.glamvoir.network.Communication;
import glamvoir.appzstack.glamvoir.widgets.ShowDialog;

/**
 * Created by gajendran on 27/9/15.
 */
public class FollowFollowingLoader extends AsyncTaskLoader<TaskResponse<ObservedFollowResponse>> implements BaseLoader {

    private TaskResponse response = null;
    private String methodName;
    private String following_ID;
    private String follower_ID;
    private ShowDialog dialog;

    public FollowFollowingLoader(Context context, String methodName, String user_id, String follower_id) {
        super(context);
        this.methodName = methodName;
        this.following_ID = user_id;
        this.follower_ID = follower_id;
        dialog = new ShowDialog();
    }


    @Override
    public TaskResponse<ObservedFollowResponse> loadInBackground() {
        response = new TaskResponse();
        try {
            response.data = Communication.toggleFollow(AppConstant.METHOD_FOLLOWER_FOLLOWING, following_ID, follower_ID);
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
//        if (requestBean.isLoader()) {
//            dialog.showProgressDialog(requestBean.getActivity(), "Loading", false);
//        }
    }

    @Override
    public void hideLoaderDialog() {
//        if (requestBean.isLoader()) {
//            dialog.dismissDialog();
//        }
    }
}
