package glamvoir.appzstack.glamvoir.intentservice;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;

import glamvoir.appzstack.glamvoir.constant.AppConstant;
import glamvoir.appzstack.glamvoir.helpers.Utility;
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
    public static final String INTENT_ARG_FNAME = "fname";
    public static final String INTENT_ARG_LNAME = "lname";
    public static final String INTENT_ARG_POST_USERID = "post_user_id";
    public static final String INTENT_ARG_POSITION = "position";
    public static final String BROADCAST_EXTRA_POSITION = "position";
    public static final String INTENT_ARG_FOLLOWERID = "followerid";

    public static final String BROADCAST_EXTRA_TOTAL_FOLLOWE = "total_follower";
    public static final String BROADCAST_EXTRA_IS_FOLLOWING = "is_following";

    public static final String BROADCAST_LIKE_ACTION = "com.like";
    public static final String BROADCAST_FOLLOW_ACTION = "com.follow";

    public static final String BROADCAST_EXTRA_LIKE = "like";
    public static final String BROADCAST_EXTRA_LIKE_DISLIKE_STATUS = "like_dislike_status";

    private Handler handler;

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

    public static void startAbuseReportServivce(Context context, String myUserId, String fname, String lname, String postId, String postUserdId, int flag) {
        Intent serviceIntent = new Intent(context, NetworkIntentService.class);
        serviceIntent.putExtra(INTENT_ARG_USERID, myUserId);
        serviceIntent.putExtra(INTENT_ARG_FNAME, fname);
        serviceIntent.putExtra(INTENT_ARG_LNAME, lname);
        serviceIntent.putExtra(INTENT_ARG_POSTID, postId);
        serviceIntent.putExtra(INTENT_ARG_POST_USERID, postUserdId);
        serviceIntent.putExtra(INTENT_ARG_FLAG, flag);
        context.startService(serviceIntent);
    }


    public NetworkIntentService() {
        super("NetworkIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        int flag = intent.getIntExtra(INTENT_ARG_FLAG, 0);
        String userID = null;
        String postID = null;
        int position;

        TaskResponse<GetPostLikeFollowResponse> response = new TaskResponse<GetPostLikeFollowResponse>();
        try {
            switch (flag) {
                case AppConstant.GETPOST_LIKE:

                    userID = intent.getStringExtra(INTENT_ARG_USERID);
                    postID = intent.getStringExtra(INTENT_ARG_POSTID);
                    position = intent.getIntExtra(INTENT_ARG_POSITION, 0);

                    //String url = "http://glamvoir.com/index.php/api?method=" + AppConstant.METHOD_LIKESTATUS + "&post_id=" + postID + "&user_id=" + userID;

                    response.data = Communication.likeStatus(AppConstant.METHOD_LIKESTATUS, userID, postID);

                    if (response.data.isSucceeded()) {
                        if (response.data != null) {
                            sendLikeBroadcast(response.data.list.get(0).total_like, response.data.list.get(0).like_dislike_status, position);
                        }
                    }
                    break;

                case AppConstant.GETPOST_FOLLOW:

                    userID = intent.getStringExtra(INTENT_ARG_USERID);
                    String follower_ID = intent.getStringExtra(INTENT_ARG_FOLLOWERID);
                    position = intent.getIntExtra(INTENT_ARG_POSITION, 0);

                    //String url1 = "http://glamvoir.com/index.php/api?method=" + AppConstant.METHOD_FOLLOWER_FOLLOWING + "&following_user_id=" + follower_ID + "&follower_user_id=" + userID1;

                    response.data = Communication.getPostFollow(AppConstant.METHOD_FOLLOWER_FOLLOWING, userID, follower_ID);
                    if (response.data.isSucceeded()) {
                        if (response.data != null) {
                            sendFollowBroadcast(response.data.list.get(0).total_folower, response.data.list.get(0).is_followng, position);
                        }
                    }
                    break;

                case AppConstant.ABUSE:
                    userID = intent.getStringExtra(INTENT_ARG_USERID);
                    String postUserId = intent.getStringExtra(INTENT_ARG_POST_USERID);
                    String fName = intent.getStringExtra(INTENT_ARG_FNAME);
                    String lName = intent.getStringExtra(INTENT_ARG_LNAME);
                    postID = intent.getStringExtra(INTENT_ARG_POSTID);

                    //String url1 = "http://glamvoir.com/index.php/api?method=" + AppConstant.METHOD_FOLLOWER_FOLLOWING + "&following_user_id=" + follower_ID + "&follower_user_id=" + userID1;

                    response.data = Communication.reportAbuse(AppConstant.METHOD_REPORT_ABUSE, userID, fName, lName, postID, postUserId, String.valueOf(flag));
                    if (response.data.isSucceeded()) {
                        if (response.data.error_code.equals("0")) {
                            handler = new Handler(Looper.getMainLooper());
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Utility.showToast(getBaseContext(), "success");
                                }
                            });
                        }
                    }
                    break;

                case AppConstant.WRONGCATEGORY:
                    userID = intent.getStringExtra(INTENT_ARG_USERID);
                    String postUserId1 = intent.getStringExtra(INTENT_ARG_POST_USERID);
                    String fName1 = intent.getStringExtra(INTENT_ARG_FNAME);
                    String lName1 = intent.getStringExtra(INTENT_ARG_LNAME);
                    postID = intent.getStringExtra(INTENT_ARG_POSTID);

                    //String url1 = "http://glamvoir.com/index.php/api?method=" + AppConstant.METHOD_FOLLOWER_FOLLOWING + "&following_user_id=" + follower_ID + "&follower_user_id=" + userID1;

                    response.data = Communication.reportAbuse(AppConstant.METHOD_REPORT_ABUSE, userID, fName1, lName1, postID, postUserId1, String.valueOf(flag));
                    if (response.data.isSucceeded()) {
                        if (response.data.error_code.equals("0")) {
                            handler = new Handler(Looper.getMainLooper());
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Utility.showToast(getBaseContext(), "success");
                                }
                            });
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