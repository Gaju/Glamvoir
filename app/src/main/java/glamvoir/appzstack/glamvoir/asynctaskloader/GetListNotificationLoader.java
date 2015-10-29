package glamvoir.appzstack.glamvoir.asynctaskloader;

import android.content.AsyncTaskLoader;
import android.content.Context;

import glamvoir.appzstack.glamvoir.apppreference.AppPreferences;
import glamvoir.appzstack.glamvoir.constant.AppConstant;
import glamvoir.appzstack.glamvoir.model.TaskResponse;
import glamvoir.appzstack.glamvoir.model.net.response.ListNotificationResponse;
import glamvoir.appzstack.glamvoir.network.Communication;

/**
 * Created by gajendran on 29/10/15.
 */
public class GetListNotificationLoader extends AsyncTaskLoader<TaskResponse<ListNotificationResponse>> {

    private TaskResponse response = null;
    private Context mContext;

    public GetListNotificationLoader(Context context) {
        super(context);
        mContext = context;
    }


    @Override
    public TaskResponse<ListNotificationResponse> loadInBackground() {
        response = new TaskResponse();
        try {
            response.data = Communication.getListNotification(AppConstant.METHOD_GET_NOTIFICATION, AppPreferences.getInstance(mContext).getUserId());
        } catch (Exception e) {
            response.error = e;
        }
        return response;
    }

    @Override
    protected void onStartLoading() {
        if (response != null) {
            deliverResult(response);
        }

        if (response == null || takeContentChanged()) {
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        response = null;
    }
}

