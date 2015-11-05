package glamvoir.appzstack.glamvoir.acra;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import org.acra.ACRA;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

import glamvoir.appzstack.glamvoir.R;


@ReportsCrashes(formKey = "", //will not be used
        mailTo = "jaimishra54@gmail.com",
        mode = ReportingInteractionMode.TOAST,
        resToastText = R.string.crash_text)

/**
 * Created by ankit on 4/5/15.
 */
public class GlamvoirApplication extends Application {
    @Override
    public void onCreate() {
        ACRA.init(this);
        super.onCreate();
        LocalBroadcastManager.getInstance(this).registerReceiver(onDownloadBroadcastReceiver, new IntentFilter("Download_Complete"));

    }

    private BroadcastReceiver onDownloadBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

        }
    };
}
