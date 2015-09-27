package glamvoir.appzstack.glamvoir.intentservice;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import glamvoir.appzstack.glamvoir.constant.AppConstant;
import glamvoir.appzstack.glamvoir.model.TaskResponse;
import glamvoir.appzstack.glamvoir.model.net.response.LikeStatusResponse;
import glamvoir.appzstack.glamvoir.network.Communication;
import retrofit.RetrofitError;

/**
 * Created by gajendran on 27/9/15.
 */
public class LikeStatusIntentService extends IntentService {

    public static final String INTENT_ARG_USERID = "user_id";
    public static final String INTENT_ARG_POSTID = "post_id";
    public static final String INTENT_ARG_METHODTYPE = "methodType";
    public static final String INTENT_ARG_LIKE_STATUS = "like_dislike_status";
    public static final String INTENT_ARG_ACTION = "action";
    public static final String INTENT_ARG_POSITION = "position";
    public static final String BROADCAST_EXTRA_POSITION = "position";


    public static final String BROADCAST_ACTION = "com.LikeStatusIntentService";
    public static final String BROADCAST_EXTRA_LIKE = "like";
    public static final String BROADCAST_EXTRA_LIKE_DISLIKE_STATUS = "like_dislike_status";
    public static final String BROADCAST_EXTRA_DISLIKE = "dislike";

    public static void startIntentService(Context context, String myUserId, String postid, String like_dislike_status, String action, int position) {
        Intent serviceIntent = new Intent(context, LikeStatusIntentService.class);
        serviceIntent.putExtra(INTENT_ARG_USERID, myUserId);
        serviceIntent.putExtra(INTENT_ARG_POSTID, postid);
        serviceIntent.putExtra(INTENT_ARG_ACTION, action);
        serviceIntent.putExtra(INTENT_ARG_LIKE_STATUS, like_dislike_status);
        serviceIntent.putExtra(INTENT_ARG_POSITION, position);
        context.startService(serviceIntent);
    }


    public LikeStatusIntentService() {
        super("LikeStatusIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        String userID = intent.getStringExtra(INTENT_ARG_USERID);
        String postID = intent.getStringExtra(INTENT_ARG_POSTID);
        String likeStatus = intent.getStringExtra(INTENT_ARG_LIKE_STATUS);
        String action = intent.getStringExtra(INTENT_ARG_ACTION);
        int position = intent.getIntExtra(INTENT_ARG_POSITION, 0);

        TaskResponse<LikeStatusResponse> response = new TaskResponse<LikeStatusResponse>();
        try {
            response.data = Communication.likeStatus(AppConstant.METHOD_LIKESTATUS, userID, postID, "1", "like");
            if (response.data.isSucceeded()) {
                if (response.data != null) {
                    sendDeleteMySaveBroadcast(response.data.list.get(0).total_like, response.data.list.get(0).total_dislike, response.data.list.get(0).like_dislike_status, position);
                }

            } else {
                //   sendObservedBroadcastError(response.data.message, isFollowiing, advert.id);
            }

        } catch (RetrofitError e) {
            response.error = e;
            //  response.errorCode = ErrorHelper.getStandardErrorCode(e.getCause());
            // sendObservedBroadcastError(e, isFollowiing, advert.id);
        }
    }

    private void sendDeleteMySaveBroadcast(String totalLike, String totalDislike, String likeStatus, int position) {
        Intent infoIntent = new Intent();
        infoIntent.setAction(BROADCAST_ACTION);
        infoIntent.putExtra(BROADCAST_EXTRA_LIKE, totalLike);
        infoIntent.putExtra(BROADCAST_EXTRA_DISLIKE, totalDislike);
        infoIntent.putExtra(BROADCAST_EXTRA_LIKE_DISLIKE_STATUS, likeStatus);
        infoIntent.putExtra(BROADCAST_EXTRA_POSITION, position);

        sendBroadcast(infoIntent);
    }
}