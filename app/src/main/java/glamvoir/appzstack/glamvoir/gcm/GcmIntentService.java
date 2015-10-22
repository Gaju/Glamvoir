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

	@Override
	protected void onHandleIntent(Intent intent) {
		Bundle extras = intent.getExtras();

		if (!extras.isEmpty()) {
			// read extras as sent from server
			String message = extras.getString("message");
			String title = extras.getString("title");

			String driverName = extras.getString("driver_name");
			String vehicleNumber = extras.getString("vehicle_no");

			sendNotification(message, title, driverName, vehicleNumber);
		}
		// Release the wake lock provided by the WakefulBroadcastReceiver.
		GcmBroadcastReceiver.completeWakefulIntent(intent);
	}

	// Put the message into a notification and post it.
	// This is just one simple example of what you might choose to do with
	// a GCM message.
	private void sendNotification(String message, String title,
			String driverName, String vehicleNumber) {

		// Invoking the default notification service
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				this);

		mBuilder.setContentTitle(title);
		mBuilder.setContentText(message);
		mBuilder.setTicker("CityRide: Booking Confirmation");
	//	mBuilder.setSmallIcon(R.drawable.main_logo);

		NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
		String[] events = null;

		if (!driverName.equals("")) {

			events = new String[3];
			events[0] = new String("Driver Name: " + driverName);
			events[1] = new String("Vehicle Number: " + vehicleNumber);
			events[2] = new String(message);
		} else {
			events = new String[1];
			events[0] = new String(title);
		}

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

		// pass the Notification object to the system
		myNotificationManager.notify(notificationIdOne, mBuilder.build());

		// mNotificationManager.cancel(numMessagesOne);

	}
}