package glamvoir.appzstack.glamvoir.asynctaskloader;

import android.content.AsyncTaskLoader;
import android.content.Context;

import glamvoir.appzstack.glamvoir.constant.AppConstant;
import glamvoir.appzstack.glamvoir.model.TaskResponse;
import glamvoir.appzstack.glamvoir.model.net.response.LikedUsers;
import glamvoir.appzstack.glamvoir.network.Communication;

/**
 * Created by gajendran on 19/10/15.
 */
public class GetLikedUsersLoader extends AsyncTaskLoader<TaskResponse<LikedUsers>> {

    private TaskResponse response = null;
    private String userID;
    private String postID;

    public GetLikedUsersLoader(Context context, String user_id, String post_id) {
        super(context);
        this.userID = user_id;
        this.postID = post_id;
    }


    @Override
    public TaskResponse<LikedUsers> loadInBackground() {
        response = new TaskResponse();
        try {
            response.data = Communication.getLikedUsers(AppConstant.METHOD_LIKED_USERS, userID, postID);
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
