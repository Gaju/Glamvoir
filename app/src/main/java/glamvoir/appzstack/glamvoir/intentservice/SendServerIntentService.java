package glamvoir.appzstack.glamvoir.intentservice;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;

/**
 * Created by gajendran on 25/9/15.
 */
public class SendServerIntentService extends IntentService {

    public static final String INTENT_ARG_USERID = "user_id";
    public static final String INTENT_ARG_POSTID = "post_id";
    public static final String INTENT_ARG_METHODTYPE = "methodType";
    public static final String BROADCAST_ACTION = "com.SendServerIntentService";
    public static final String BROADCAST_EXTRA_MESSAGE = "message";
    public static final String BROADCAST_EXTRA_ERROR_CODE = "error_code";

    private Handler handler ;

    public static void startSavePostService(Context context, String myUserId, String postid, String methodType) {
        Intent serviceIntent = new Intent(context, SendServerIntentService.class);
        serviceIntent.putExtra(INTENT_ARG_USERID, myUserId);
        serviceIntent.putExtra(INTENT_ARG_POSTID, postid);
        serviceIntent.putExtra(INTENT_ARG_METHODTYPE, methodType);
        context.startService(serviceIntent);
    }


    public SendServerIntentService() {
        super("SendServerIntentService");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handler = new Handler();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // TODO Auto-generated method stub
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "Handling Intent..", Toast.LENGTH_LONG).show();
            }
        });
    }

//    @Override
//    protected void onHandleIntent(Intent intent) {
//
//        String userID = intent.getStringExtra(INTENT_ARG_USERID);
//        String postID = intent.getStringExtra(INTENT_ARG_POSTID);
//        String methodType = intent.getStringExtra(INTENT_ARG_METHODTYPE);
//
//        TaskResponse<ServerResponse> response = new TaskResponse<ServerResponse>();
//        try {
//            response.data = Communication.savePost(methodType, userID, postID);
//            if (response.data.isSucceeded()) {
//                if (response.data != null) {
//                    sendDeleteMySaveBroadcast(response.data.error_code, response.data.msg_string);
//                }
//
//            } else {
//                //   sendObservedBroadcastError(response.data.message, isFollowiing, advert.id);
//            }
//
//        } catch (RetrofitError e) {
//            response.error = e;
//            //  response.errorCode = ErrorHelper.getStandardErrorCode(e.getCause());
//            // sendObservedBroadcastError(e, isFollowiing, advert.id);
//        }
//    }

    private void sendDeleteMySaveBroadcast(String errorCode, String message) {
        Intent infoIntent = new Intent();
        infoIntent.setAction(BROADCAST_ACTION);
        infoIntent.putExtra(BROADCAST_EXTRA_ERROR_CODE, errorCode);
        infoIntent.putExtra(BROADCAST_EXTRA_MESSAGE, message);
        sendBroadcast(infoIntent);
    }
}

