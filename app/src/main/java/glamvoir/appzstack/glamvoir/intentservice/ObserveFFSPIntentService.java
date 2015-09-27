package glamvoir.appzstack.glamvoir.intentservice;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;

import glamvoir.appzstack.glamvoir.constant.AppConstant;

import glamvoir.appzstack.glamvoir.model.TaskResponse;
import glamvoir.appzstack.glamvoir.model.net.response.ObservedFollowResponse;
import glamvoir.appzstack.glamvoir.network.Communication;
import retrofit.RetrofitError;

/**
 * Created by gajendran on 2015-09-12.
 */
public class ObserveFFSPIntentService extends IntentService {

    public static final String INTENT_ARG_FOLLOW_USER = "advert_param";
    public static final String INTENT_ARG_MYUSERID = "user_id";
    public static final String INTENT_ARG_FLAG = "flag";

    public static final String BROADCAST_ACTION_OBSERVED_ERROR = "glamvoir.appzstack.glamvoir.observed_changed_error";
    public static final String BROADCAST_ACTION_OBSERVED = "glamvoir.appzstack.glamvoir.observed_changed";

    public static final String BROADCAST_EXTRA_ISFOLLOW_ADDED = "isAddedToObserved";
    public static final String BROADCAST_EXTRA_FOLLOW_ID = "follow_id";
    public static final String BROADCAST_EXTRA_TOTAL_FOLLOWER = "total_follower";
    public static final String BROADCAST_EXTRA_ERROR_MESSAGE = "error_message";

    public ObserveFFSPIntentService() {
        super("ObserveFFSPIntentService");
    }

    public static void startObserveAdService(Context context, String myUserId, String followerID, int position) {
        Intent serviceIntent = new Intent(context, ObserveFFSPIntentService.class);
        serviceIntent.putExtra(INTENT_ARG_FOLLOW_USER, followerID);
        serviceIntent.putExtra(INTENT_ARG_MYUSERID, myUserId);
        serviceIntent.putExtra(INTENT_ARG_FLAG, position);
        context.startService(serviceIntent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        String followerID = intent.getStringExtra(INTENT_ARG_FOLLOW_USER);
        int position = intent.getIntExtra(INTENT_ARG_FLAG, 0);
        String isFollowiing = "0";
        ArrayList<String> followIds = new ArrayList<String>();

//        if (advert.is_following.equalsIgnoreCase("1")) {
//            isFollowiing = "0";
//        } else {
//            isFollowiing = "1";
//        }

        TaskResponse<ObservedFollowResponse> response = new TaskResponse<ObservedFollowResponse>();
        try {
            response.data = Communication.toggleFollow(AppConstant.METHOD_FOLLOWER_FOLLOWING, intent.getStringExtra(INTENT_ARG_MYUSERID), followerID);
            if (response.data.isSucceeded()) {
                if (response.data != null) {
                    sendObservedBroadcast(response.data.observedFollowIds.get(0).is_followng, response.data.observedFollowIds.get(0).total_folower, position);
                }
                //   sendObservedBroadcast(isFollowiing, advert.user_id, followIds.get(0));
            } else {
                //   sendObservedBroadcastError(response.data.message, isFollowiing, advert.id);
            }

        } catch (RetrofitError e) {
            response.error = e;
            //  response.errorCode = ErrorHelper.getStandardErrorCode(e.getCause());
            // sendObservedBroadcastError(e, isFollowiing, advert.id);
        }
    }

    private void sendObservedBroadcast(String isFollowing, String totalFollower, int position) {
        Intent infoIntent = new Intent();
        infoIntent.setAction(BROADCAST_ACTION_OBSERVED);
        infoIntent.putExtra(BROADCAST_EXTRA_ISFOLLOW_ADDED, isFollowing);
        infoIntent.putExtra(INTENT_ARG_FLAG, position);
        infoIntent.putExtra(BROADCAST_EXTRA_TOTAL_FOLLOWER, totalFollower);

        sendBroadcast(infoIntent);
    }

    private void sendObservedBroadcastError(Exception e, boolean isAddedToObserved, String adId) {
        Intent infoIntent = new Intent();
        infoIntent.setAction(BROADCAST_ACTION_OBSERVED_ERROR);
        infoIntent.putExtra(BROADCAST_EXTRA_ISFOLLOW_ADDED, isAddedToObserved);
        infoIntent.putExtra(BROADCAST_EXTRA_TOTAL_FOLLOWER, adId);

        sendBroadcast(infoIntent);
    }

    private void sendObservedBroadcastError(String errorMessage, boolean isAddedToObserved, String adId) {
        Intent infoIntent = new Intent();
        infoIntent.setAction(BROADCAST_ACTION_OBSERVED_ERROR);
        infoIntent.putExtra(BROADCAST_EXTRA_ISFOLLOW_ADDED, isAddedToObserved);
        infoIntent.putExtra(BROADCAST_EXTRA_TOTAL_FOLLOWER, adId);
        infoIntent.putExtra(BROADCAST_EXTRA_ERROR_MESSAGE, errorMessage);

        sendBroadcast(infoIntent);
    }
}
