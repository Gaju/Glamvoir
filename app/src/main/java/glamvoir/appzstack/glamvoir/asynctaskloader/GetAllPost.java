package glamvoir.appzstack.glamvoir.asynctaskloader;

import android.content.AsyncTaskLoader;

import glamvoir.appzstack.glamvoir.Bean.AllPostsBean;
import glamvoir.appzstack.glamvoir.model.net.request.RequestBean;
import glamvoir.appzstack.glamvoir.network.NetworkCall;
import glamvoir.appzstack.glamvoir.network.ParserClass;

/**
 * Created by gajendran on 9/9/15.
 */
public class GetAllPost extends AsyncTaskLoader<AllPostsBean> implements BaseLoader {

    private RequestBean requestBean;
    private NetworkCall networkCall;
    private AllPostsBean allPostsBean;
    private String mUrl;

    public GetAllPost(RequestBean requestBean, String url) {
        super(requestBean.getContext());
        this.requestBean = requestBean;
        this.mUrl = url;
        networkCall = NetworkCall.getInstance(requestBean.getContext());
    }

    @Override
    protected void onStartLoading() {
        if (allPostsBean != null) {
            deliverResult(allPostsBean);
        }

        if (allPostsBean == null || takeContentChanged()) {
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
        allPostsBean = null;
    }

    @Override
    public AllPostsBean loadInBackground() {
        allPostsBean = new AllPostsBean();

        String url = null;
        try {
            //url = AppConfig.GLOBALPRODUCTSEARCH + URLEncoder.encode(mProductCode, "UTF-8") + URLEncoder.encode(mProductFilter, "UTF-8");
            String serverResponseString = networkCall.getResponseFromServer(url.trim());

            ParserClass parserClass = ParserClass.getsInstance(requestBean.getContext());
            allPostsBean = parserClass.getAllPost(serverResponseString, allPostsBean);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return allPostsBean;
    }


    @Override
    public void showLoaderDialog() {

        if (requestBean.isLoader()) {
            networkCall.showProgressDialog(requestBean.getActivity(), "Loading", false);
        }
    }

    @Override
    public void hideLoaderDialog() {

        if (requestBean.isLoader()) {
            networkCall.dismissDialog();
        }
    }
}
