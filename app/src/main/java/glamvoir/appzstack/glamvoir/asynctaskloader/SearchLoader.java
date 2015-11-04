package glamvoir.appzstack.glamvoir.asynctaskloader;

import android.support.v4.content.AsyncTaskLoader;
import glamvoir.appzstack.glamvoir.Bean.AllPostsBean;
import glamvoir.appzstack.glamvoir.apppreference.AppPreferences;
import glamvoir.appzstack.glamvoir.constant.AppConstant;
import glamvoir.appzstack.glamvoir.model.net.request.RequestBean;
import glamvoir.appzstack.glamvoir.network.NetworkCall;
import glamvoir.appzstack.glamvoir.network.ParserClass;

/**
 * Created by jai on 11/4/2015.
 */

public class SearchLoader extends AsyncTaskLoader<AllPostsBean> {

    private RequestBean requestBean;
    private NetworkCall networkCall;
    private AllPostsBean allPostsBean;
    private String mUrl;
    private String categoryID;
    private String keyword;

    public SearchLoader(RequestBean requestBean, String url, String cat_id, String keyword) {
        super(requestBean.getContext());
        this.requestBean = requestBean;
        this.mUrl = url;
        this.categoryID = cat_id;

        this.keyword = keyword;
        networkCall = NetworkCall.getInstance(requestBean.getContext());
    }

    @Override
    protected void onStartLoading() {
        if (allPostsBean != null) {
            deliverResult(allPostsBean);
        }

        if (allPostsBean == null || takeContentChanged()) {
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

        AppPreferences appPreferences = AppPreferences.getInstance(requestBean.getContext());
        String url = null;
        try {

            url = mUrl + "method=" + AppConstant.METHOD_GETPOST + "&user_gender=" + appPreferences.getGender() + "&cat_id=" + categoryID + "&user_id=" + appPreferences.getUserId() + "&keyword=" + keyword;
            String serverResponseString = networkCall.getResponseFromServer(url.trim());
            ParserClass parserClass = ParserClass.getsInstance(requestBean.getContext());
            allPostsBean = parserClass.getAllPost(serverResponseString, allPostsBean);

        } catch (Exception e) {
            e.printStackTrace();
            allPostsBean.setSuccessCode(1);
        }
        return allPostsBean;
    }
}
