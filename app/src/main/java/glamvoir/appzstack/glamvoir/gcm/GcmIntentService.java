package glamvoir.appzstack.glamvoir.gcm;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import glamvoir.appzstack.glamvoir.R;
import glamvoir.appzstack.glamvoir.activity.HomeActivity;
import glamvoir.appzstack.glamvoir.config.AppConfig;

public class GcmIntentService extends IntentService {

    private final String TAG = "GcmIntentService";

    public static final int NOTIFICATION_ID = 1;
    NotificationCompat.Builder builder;

    private NotificationManager myNotificationManager;
    private int notificationIdOne = 111;
    private int numMessagesOne = 0;

    public GcmIntentService() {
        super("GcmIntentService");
    }

//	user_id, user_image,  post_id, notification_type,  message, creation_date
//	Note* notification_type =   1->follow, 2->like, 3-> comment
//			creation_date = date and time action performed like 2015-10-22 16:16:16
//	message = show this message i send like “shivendra suman likes your post”


    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();

        if (!extras.isEmpty()) {
            // read extras as sent from server
            sendNotification(extras);
        }
        // Release the wake lock provided by the WakefulBroadcastReceiver.
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    // Put the message into a notification and post it.
    // This is just one simple example of what you might choose to do with
    // a GCM message.
    private void sendNotification(Bundle extras) {

//                        notification_type=2,
//                        post_id=17,
//                        user_id=32,
//                        from=790774834022,
//                        title=likesyourpost,
//                        message=gajagajalikesyourpost,
//                        android.support.content.wakelockid=1,
//                        collapse_key=do_not_collapse,
//                        user_image=,
//                        creation_date=2015-10-2414: 29: 46


        String user_id = extras.getString("user_id");
        String title = extras.getString("title");
        String user_image = extras.getString("user_image");
        String post_id = extras.getString("post_id");
        String notification_type = extras.getString("notification_type");
        String message = extras.getString("message");
        String creation_date = extras.getString("creation_date");

        //storing alert counter globally
        AppConfig.ALERT_COUNTER = extras.getInt("badge");

        // Invoking the default notification service
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                this);

        mBuilder.setContentTitle(title);
        mBuilder.setContentText(message);
        mBuilder.setTicker("Glamvoir Notification");
        mBuilder.setSmallIcon(R.mipmap.main_logo);

        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        String[] events = null;

//        switch (notification_type){
//
//            events = new String[3];
//            case "1":
//                break;
//
//            case "2":
//                break;
//
//            case "3":
//                break;
//        }

        // if (!driverName.equals("")) {

        events = new String[3];
        events[0] = new String(title);
        events[2] = new String(message);
//        } else {
//            events = new String[1];
//            events[0] = new String(title);
//        }

        // Sets a title for the Inbox style big view
        inboxStyle.setBigContentTitle(title);
        // Moves events into the big view
        for (int i = 0; i < events.length; i++) {
            inboxStyle.addLine(events[i]);
        }
        mBuilder.setStyle(inboxStyle);

        // Increase notification number every time a new notification arrives
        mBuilder.setNumber(++numMessagesOne);

        // when the user presses the notification, it is auto-removed
        mBuilder.setAutoCancel(true);

        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(this, HomeActivity.class);
        // resultIntent.putExtra("notificationId", notificationIdOne);

        // This ensures that navigating backward from the Activity leads out of
        // the app to Home page
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        // Adds the back stack for the Intent
        stackBuilder.addParentStack(HomeActivity.class);

        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,
                PendingIntent.FLAG_ONE_SHOT // can only be used once
        );
        // start the activity when the user clicks the notification text
        mBuilder.setContentIntent(resultPendingIntent);

        myNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // pass the Notification object to the swipeRefreshLayoutsystem
        myNotificationManager.notify(notificationIdOne, mBuilder.build());

        // mNotificationManager.cancel(numMessagesOne);

    }

}