package glamvoir.appzstack.glamvoir.asynctaskloader;

import android.content.AsyncTaskLoader;

import glamvoir.appzstack.glamvoir.constant.AppConstant;
import glamvoir.appzstack.glamvoir.model.TaskResponse;
import glamvoir.appzstack.glamvoir.model.net.request.RequestBean;
import glamvoir.appzstack.glamvoir.model.net.response.CommentResponse;
import glamvoir.appzstack.glamvoir.network.Communication;
import glamvoir.appzstack.glamvoir.widgets.ShowDialog;

/**
 * Created by gajendran on 26/9/15.
 */
public class CommentLoader extends AsyncTaskLoader<TaskResponse<CommentResponse>> implements BaseLoader {

    private RequestBean requestBean;
    private String userID;
    private String postID;
    private String methodType;
    private String comment;
    private TaskResponse response = null;
    private ShowDialog dialog;

    public CommentLoader(RequestBean requestBean, String methodType, String userID, String postID, String comment) {
        super(requestBean.getContext());
        this.requestBean = requestBean;
        this.userID = userID;
        this.postID = postID;
        this.methodType = methodType;
        this.comment = comment;
        dialog = new ShowDialog();
    }

    public CommentLoader(RequestBean requestBean, String methodType, String userID, String postID) {
        super(requestBean.getContext());
        this.requestBean = requestBean;
        this.userID = userID;
        this.postID = postID;
        this.methodType = methodType;
        dialog = new ShowDialog();
    }


    @Override
    public TaskResponse<CommentResponse> loadInBackground() {
        response = new TaskResponse();

        try {
            //Logger.push(Logger.LogType.LOG_DEBUG, TAG, email + "," + password + "," + deviceToken + "," + deviceType);
            switch (methodType) {
                case AppConstant.METHOD_ADDCOMMENT:
                    response.data = Communication.addComment(AppConstant.METHOD_ADDCOMMENT, userID, postID, comment);
                    break;
                case AppConstant.METHOD_GETCOMMENT:
                    response.data = Communication.getComment(AppConstant.METHOD_GETCOMMENT, userID, postID);
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
            //showLoaderDialog();
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
