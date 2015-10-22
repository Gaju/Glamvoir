package glamvoir.appzstack.glamvoir.asynctaskloader;

import android.content.AsyncTaskLoader;
import android.content.Context;

import glamvoir.appzstack.glamvoir.constant.AppConstant;
import glamvoir.appzstack.glamvoir.model.TaskResponse;
import glamvoir.appzstack.glamvoir.model.net.response.CityResponse;
import glamvoir.appzstack.glamvoir.network.Communication;

/**
 * Created by gajendran on 22/10/15.
 */
public class CityLoader extends AsyncTaskLoader<TaskResponse<CityResponse>> {

    private TaskResponse response = null;

    public CityLoader(Context context) {
        super(context);
    }

    @Override
    public TaskResponse<CityResponse> loadInBackground() {
        response = new TaskResponse();
        try {
            response.data = Communication.getCity(AppConstant.METHOD_GET_CITY);
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
