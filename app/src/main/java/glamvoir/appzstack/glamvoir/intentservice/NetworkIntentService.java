package glamvoir.appzstack.glamvoir.intentservice;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import glamvoir.appzstack.glamvoir.constant.AppConstant;
import glamvoir.appzstack.glamvoir.model.TaskResponse;
import glamvoir.appzstack.glamvoir.model.net.response.GetPostLikeFollowResponse;
import glamvoir.appzstack.glamvoir.network.Communication;
import retrofit.RetrofitError;

/**
 * Created by gajendran on 27/9/15.
 */
public class NetworkIntentService extends IntentService {

    public static final String INTENT_ARG_USERID = "user_id";
    public static final String INTENT_ARG_POSTID = "post_id";
    public static final String INTENT_ARG_FLAG = "flag";
    public static final String INTENT_ARG_LIKE_STATUS = "like_dislike_status";
    public static final String INTENT_ARG_ACTION = "action";
    public static final String INTENT_ARG_POSITION = "position";
    public static final String BROADCAST_EXTRA_POSITION = "position";
    public static final String INTENT_ARG_FOLLOWERID = "followerid";

    public static final String BROADCAST_EXTRA_TOTAL_FOLLOWE = "total_follower";
    public static final String BROADCAST_EXTRA_IS_FOLLOWING = "is_following";


    public static final String BROADCAST_LIKE_ACTION = "com.like";
    public static final String BROADCAST_FOLLOW_ACTION = "com.follow";
    public static final String BROADCAST_EXTRA_LIKE = "like";
    public static final String BROADCAST_EXTRA_LIKE_DISLIKE_STATUS = "like_dislike_status";
    public static final String BROADCAST_EXTRA_DISLIKE = "dislike";

    public static void startLikeIntentService(Context context, String myUserId, String postid, int position, int flag) {
        Intent serviceIntent = new Intent(context, NetworkIntentService.class);
        serviceIntent.putExtra(INTENT_ARG_USERID, myUserId);
        serviceIntent.putExtra(INTENT_ARG_POSTID, postid);
        serviceIntent.putExtra(INTENT_ARG_POSITION, position);
        serviceIntent.putExtra(INTENT_ARG_FLAG, flag);
        context.startService(serviceIntent);
    }


    public static void startFollowIntentServicen(Context context, String myUserId, String followerID, int position, int flag) {
        Intent serviceIntent = new Intent(context, NetworkIntentService.class);
        serviceIntent.putExtra(INTENT_ARG_USERID, myUserId);
        serviceIntent.putExtra(INTENT_ARG_FOLLOWERID, followerID);
        serviceIntent.putExtra(INTENT_ARG_POSITION, position);
        serviceIntent.putExtra(INTENT_ARG_FLAG, flag);
        context.startService(serviceIntent);
    }


    public NetworkIntentService() {
        super("NetworkIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        int flag = intent.getIntExtra(INTENT_ARG_FLAG, 0);

        TaskResponse<GetPostLikeFollowResponse> response = new TaskResponse<GetPostLikeFollowResponse>();
        try {
            switch (flag) {
                case AppConstant.GETPOST_LIKE:

                    String userID = intent.getStringExtra(INTENT_ARG_USERID);
                    String postID = intent.getStringExtra(INTENT_ARG_POSTID);
                    int position = intent.getIntExtra(INTENT_ARG_POSITION, 0);

                    String url = "http://glamvoir.com/index.php/api?method=" + AppConstant.METHOD_LIKESTATUS + "&post_id=" + postID + "&user_id=" + userID;

                    response.data = Communication.likeStatus(AppConstant.METHOD_LIKESTATUS, userID, postID);

                    if (response.data.isSucceeded()) {
                        if (response.data != null) {
                            sendLikeBroadcast(response.data.list.get(0).total_like, response.data.list.get(0).like_dislike_status, position);
                        }
                    }
                    break;

                case AppConstant.GETPOST_FOLLOW:

                    String userID1 = intent.getStringExtra(INTENT_ARG_USERID);
                    String follower_ID = intent.getStringExtra(INTENT_ARG_FOLLOWERID);
                    int position1 = intent.getIntExtra(INTENT_ARG_POSITION, 0);

                    String url1 = "http://glamvoir.com/index.php/api?method=" + AppConstant.METHOD_FOLLOWER_FOLLOWING + "&following_user_id=" + follower_ID + "&follower_user_id=" + userID1;

                    response.data = Communication.getPostFollow(AppConstant.METHOD_FOLLOWER_FOLLOWING, userID1, follower_ID);
                    if (response.data.isSucceeded()) {
                        if (response.data != null) {
                            sendFollowBroadcast(response.data.list.get(0).total_folower, response.data.list.get(0).is_followng, position1);
                        }
                    }
                    break;
            }


        } catch (RetrofitError e) {
            response.error = e;
            //  response.errorCode = ErrorHelper.getStandardErrorCode(e.getCause());
            // sendObservedBroadcastError(e, isFollowiing, advert.id);
        }
    }

    private void sendLikeBroadcast(String totalLike, String likeStatus, int position) {
        Intent infoIntent = new Intent();
        infoIntent.setAction(BROADCAST_LIKE_ACTION);
        infoIntent.putExtra(BROADCAST_EXTRA_LIKE, totalLike);
        infoIntent.putExtra(BROADCAST_EXTRA_LIKE_DISLIKE_STATUS, likeStatus);
        infoIntent.putExtra(BROADCAST_EXTRA_POSITION, position);
        sendBroadcast(infoIntent);
    }

    private void sendFollowBroadcast(String totalFollower, String isFollowing, int position) {
        Intent infoIntent = new Intent();
        infoIntent.setAction(BROADCAST_FOLLOW_ACTION);
        infoIntent.putExtra(BROADCAST_EXTRA_TOTAL_FOLLOWE, totalFollower);
        infoIntent.putExtra(BROADCAST_EXTRA_IS_FOLLOWING, isFollowing);
        infoIntent.putExtra(BROADCAST_EXTRA_POSITION, position);

        sendBroadcast(infoIntent);
    }
}