package glamvoir.appzstack.glamvoir.fragment;

import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import glamvoir.appzstack.glamvoir.Bean.AllPostsBean;
import glamvoir.appzstack.glamvoir.Bean.ParentPostBean;
import glamvoir.appzstack.glamvoir.R;
import glamvoir.appzstack.glamvoir.adapter.Custome_All_ListAdapter;
import glamvoir.appzstack.glamvoir.asynctask.SavePostAsyncTask;
import glamvoir.appzstack.glamvoir.asynctaskloader.GetAllPostLoader;
import glamvoir.appzstack.glamvoir.asynctaskloader.LoaderID;
import glamvoir.appzstack.glamvoir.config.AppConfig;
import glamvoir.appzstack.glamvoir.constant.AppConstant;
import glamvoir.appzstack.glamvoir.helpers.Utility;
import glamvoir.appzstack.glamvoir.intentservice.NetworkIntentService;
import glamvoir.appzstack.glamvoir.interfaces.AsynTaskListener;
import glamvoir.appzstack.glamvoir.model.net.request.RequestBean;
import glamvoir.appzstack.glamvoir.network.InternetStatus;


/**
 * Created by Edwin on 15/02/2015.
 */
public class Fashion extends BaseFragment {

    private View view;

    public Fashion(View headerTab) {
        super();
        this.view = headerTab;
    }

    @Override
    protected View getHeaderView() {
        return view;
    }

    private RequestBean mRequestBean;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRequestBean = new RequestBean();
        mRequestBean.setLoader(true);
        mRequestBean.setActivity(getActivity());
    }

    @Override
    protected RequestBean getRequestBean() {
        return mRequestBean;
    }

    @Override
    protected String getPostType() {
        return AppConfig.GETALLPOST;
    }

    @Override
    protected String getCategoryType() {
        return AppConstant.CATEGORY_FASHION;
    }

    @Override
    protected Fragment getFragment() {
        return Fashion.this;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}