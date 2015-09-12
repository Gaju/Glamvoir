package glamvoir.appzstack.glamvoir.asynctaskloader;

import android.content.AsyncTaskLoader;

import glamvoir.appzstack.glamvoir.model.FFSP_Response;
import glamvoir.appzstack.glamvoir.model.TaskResponse;
import glamvoir.appzstack.glamvoir.model.net.request.RequestBean;
import glamvoir.appzstack.glamvoir.network.Communication;
import glamvoir.appzstack.glamvoir.widgets.ShowDialog;

/**
 * Created by gajendran on 10/9/15.
 */
public class FFSPLoader extends AsyncTaskLoader<TaskResponse<FFSP_Response>> implements BaseLoader {

    private RequestBean requestBean;
    private TaskResponse response = null;
    private String methodName;
    private String user_id;
    private ShowDialog dialog;

    public FFSPLoader(RequestBean requestBean, String methodName, String user_id) {
        super(requestBean.getContext());
        this.requestBean = requestBean;
        this.methodName = methodName;
        this.user_id = user_id;
        dialog = new ShowDialog();
    }


    @Override
    public TaskResponse<FFSP_Response> loadInBackground() {
        response = new TaskResponse();
        try {
            response.data = Communication._FFSP(methodName, user_id);
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
            showLoaderDialog();
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


    @Override
    public void showLoaderDialog() {
        if (requestBean.isLoader()) {
            dialog.showProgressDialog(requestBean.getActivity(), "Loading", false);
        }
    }

    @Override
    public void hideLoaderDialog() {
        if (requestBean.isLoader()) {
            dialog.dismissDialog();
        }
    }
}
