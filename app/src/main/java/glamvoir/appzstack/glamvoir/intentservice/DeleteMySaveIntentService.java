package glamvoir.appzstack.glamvoir.intentservice;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import glamvoir.appzstack.glamvoir.model.TaskResponse;
import glamvoir.appzstack.glamvoir.model.net.response.DeleteMySaveResponse;
import glamvoir.appzstack.glamvoir.network.Communication;
import retrofit.RetrofitError;

/**
 * Created by gajendran on 15/9/15.
 */
public class DeleteMySaveIntentService extends IntentService {


    public static final String INTENT_ARG_USERID = "user_id";
    public static final String INTENT_ARG_POSTID = "post_id";
    public static final String INTENT_ARG_METHODTYPE = "methodType";
    public static final String BROADCAST_ACTION_DELETE_MYSAVE = "com.DeleteMySaveIntentService";


    public static void startDeleteMysaveService(Context context, String myUserId, String postid, String methodType) {
        Intent serviceIntent = new Intent(context, DeleteMySaveIntentService.class);
        serviceIntent.putExtra(INTENT_ARG_USERID, myUserId);
        serviceIntent.putExtra(INTENT_ARG_POSTID, postid);
        serviceIntent.putExtra(INTENT_ARG_METHODTYPE, methodType);
        context.startService(serviceIntent);
    }


    public DeleteMySaveIntentService() {
        super("DeleteMySaveIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        String userID = intent.getStringExtra(INTENT_ARG_USERID);
        String postID = intent.getStringExtra(INTENT_ARG_POSTID);
        String methodType = intent.getStringExtra(INTENT_ARG_METHODTYPE);

        TaskResponse<DeleteMySaveResponse> response = new TaskResponse<DeleteMySaveResponse>();
        try {
            response.data = Communication.deleteMySave(methodType, userID, postID);
            if (response.data.isSucceeded()) {
                if (response.data != null) {
                    sendDeleteMySaveBroadcast();
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

    private void sendDeleteMySaveBroadcast() {
        Intent infoIntent = new Intent();
        infoIntent.setAction(BROADCAST_ACTION_DELETE_MYSAVE);
        sendBroadcast(infoIntent);
    }
}
