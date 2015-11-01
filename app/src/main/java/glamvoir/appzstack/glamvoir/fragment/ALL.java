package glamvoir.appzstack.glamvoir.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import glamvoir.appzstack.glamvoir.config.AppConfig;
import glamvoir.appzstack.glamvoir.constant.AppConstant;
import glamvoir.appzstack.glamvoir.model.net.request.RequestBean;


public class ALL extends BaseFragment {

    private View view;

    public ALL(View headerTab) {
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
        return AppConstant.CATEGORY_ALL;
    }

    @Override
    protected Fragment getFragment() {
        return ALL.this;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}