package glamvoir.appzstack.glamvoir.asynctaskloader;

import android.content.AsyncTaskLoader;
import android.content.Context;

import glamvoir.appzstack.glamvoir.constant.AppConstant;
import glamvoir.appzstack.glamvoir.model.TaskResponse;
import glamvoir.appzstack.glamvoir.model.net.request.RequestBean;
import glamvoir.appzstack.glamvoir.model.net.response.GetPostLikeFollowResponse;
import glamvoir.appzstack.glamvoir.network.Communication;

/**
 * Created by gajendran on 6/11/15.
 */
public class LikeFollowLoader extends AsyncTaskLoader<TaskResponse<GetPostLikeFollowResponse>> {

    private RequestBean requestBean;
    private String userID;
    private String postID;
    private String methodType;
    private TaskResponse<GetPostLikeFollowResponse> response = null;
    private int position;
    private int flag;

    public LikeFollowLoader(Context context, String myUserId, String postid, int position, int flag) {
        super(context);
        this.userID = myUserId;
        this.postID = postid;
        this.position = position;
        this.flag = flag;
    }


//    public LikeFollowLoader(Context context, String myUserId, String followerID, int position, int functionType) {
//        super(context);
//
//        Intent serviceIntent = new Intent(context, NetworkIntentService.class);
//        serviceIntent.putExtra(INTENT_ARG_USERID, myUserId);
//        serviceIntent.putExtra(INTENT_ARG_FOLLOWERID, followerID);
//        serviceIntent.putExtra(INTENT_ARG_POSITION, position);
//        serviceIntent.putExtra(INTENT_ARG_FLAG, functionType);
//        context.startService(serviceIntent);
//    }


    @Override
    public TaskResponse<GetPostLikeFollowResponse> loadInBackground() {
        response = new TaskResponse();

        try {

            switch (flag) {
                case AppConstant.GETPOST_LIKE:

                    //String url = "http://glamvoir.com/index.php/api?method=" + AppConstant.METHOD_LIKESTATUS + "&post_id=" + postID + "&user_id=" + userID;

                    response.data = Communication.likeStatus(AppConstant.METHOD_LIKESTATUS, userID, postID);
                    response.data.functionType = flag;
                    response.data.itemPosition = position;

//                    if (response.data.isSucceeded()) {
//                        if (response.data != null) {
//                            sendLikeBroadcast(response.data.list.get(0).total_like, response.data.list.get(0).like_dislike_status, position);
//                        }
//                    }
                    break;

//                case AppConstant.GETPOST_FOLLOW:
//
//                    userID = intent.getStringExtra(INTENT_ARG_USERID);
//                    String follower_ID = intent.getStringExtra(INTENT_ARG_FOLLOWERID);
//                    position = intent.getIntExtra(INTENT_ARG_POSITION, 0);
//
//                    //String url1 = "http://glamvoir.com/index.php/api?method=" + AppConstant.METHOD_FOLLOWER_FOLLOWING + "&following_user_id=" + follower_ID + "&follower_user_id=" + userID1;
//
//                    response.data = Communication.getPostFollow(AppConstant.METHOD_FOLLOWER_FOLLOWING, userID, follower_ID);
//                    if (response.data.isSucceeded()) {
//                        if (response.data != null) {
//                            sendFollowBroadcast(response.data.list.get(0).total_folower, response.data.list.get(0).is_followng, position);
//                        }
//                    }
//                    break;

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
