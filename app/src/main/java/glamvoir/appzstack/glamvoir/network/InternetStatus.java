package glamvoir.appzstack.glamvoir.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import glamvoir.appzstack.glamvoir.R;
import glamvoir.appzstack.glamvoir.helpers.Utility;

public class InternetStatus {
	
	public static int TYPE_WIFI = 1;
	public static int TYPE_MOBILE = 2;
	public static int TYPE_NOT_CONNECTED = 0;
	
	
	public static int getConnectivityStatus(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		if (null != activeNetwork) {
			if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
				return TYPE_WIFI;
			
			if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
				return TYPE_MOBILE;
		} 
		return TYPE_NOT_CONNECTED;
	}


	public static String getConnectivityStatusString(Context context) {
		int conn = InternetStatus.getConnectivityStatus(context);
		String status = null;
		if (conn == InternetStatus.TYPE_WIFI) {
			status = "Wifi enabled";
		} else if (conn == InternetStatus.TYPE_MOBILE) {
			status = "Mobile data enabled";
		} else if (conn == InternetStatus.TYPE_NOT_CONNECTED) {
			status = "Not connected to Internet";
		}
		return status;
	}

	/**
	 * This method is used to check whether Internet is available or not
	 *
	 * @param mcontext
	 * @param showToast
	 * @return
	 */
	public static boolean isInternetAvailable(Context mcontext, boolean showToast) {
		boolean isConnected = false;
		ConnectivityManager connectivity = (ConnectivityManager) mcontext.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null)
				for (int i = 0; i < info.length; i++)
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						isConnected = true;
//					}
					}

		} else
			isConnected = false;
		if (isConnected) {
			return true;
		} else {
			if (showToast)
				Utility.showToast(mcontext, mcontext.getString(R.string.internet_connection_error));
			return false;
		}
	}
}
