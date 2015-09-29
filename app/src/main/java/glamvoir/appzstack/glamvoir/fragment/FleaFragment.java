package glamvoir.appzstack.glamvoir.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import glamvoir.appzstack.glamvoir.Bean.AllPostsBean;
import glamvoir.appzstack.glamvoir.Bean.ParentPostBean;
import glamvoir.appzstack.glamvoir.R;
import glamvoir.appzstack.glamvoir.adapter.Custome_All_ListAdapter;
import glamvoir.appzstack.glamvoir.asynctaskloader.GetAllPostLoader;
import glamvoir.appzstack.glamvoir.asynctaskloader.LoaderID;
import glamvoir.appzstack.glamvoir.config.AppConfig;
import glamvoir.appzstack.glamvoir.constant.AppConstant;
import glamvoir.appzstack.glamvoir.model.net.request.RequestBean;

/**
 * Created by jaim on 9/12/2015.
 */
public class FleaFragment extends Fragment {

    //ImagePagerAdapter adapter;
    private ListView mlistView;
    private RequestBean mRequestBean;

    private TextView tv_search;
    private ArrayList<ParentPostBean> list = new ArrayList<ParentPostBean>();

    public FleaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mRequestBean = new RequestBean();
        mRequestBean.setLoader(true);
        mRequestBean.setActivity(getActivity());

        View rootView = inflater.inflate(R.layout.fragment_feed, container, false);
        mlistView = (ListView) rootView.findViewById(R.id.lv_all);
        tv_search = (TextView) rootView.findViewById(R.id.tv_search);
        tv_search.setVisibility(View.VISIBLE);

        mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadData();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void loadData() {
        getLoaderManager().restartLoader(LoaderID.FLEAMARKET, null, ffspCallback);
    }

    LoaderManager.LoaderCallbacks<AllPostsBean> ffspCallback =
            new LoaderManager.LoaderCallbacks<AllPostsBean>() {

                @Override
                public Loader<AllPostsBean> onCreateLoader(int id, Bundle args) {
                    return new GetAllPostLoader(mRequestBean, AppConfig.GETALLPOST, AppConstant.CATEGORY_ALL);
                }

                @Override
                public void onLoadFinished(Loader<AllPostsBean> loader, AllPostsBean data) {
                    if (loader instanceof GetAllPostLoader) {
                        ((GetAllPostLoader) loader).hideLoaderDialog();
                        if (data.getSuccessCode() == 1) {
                            //error
                        } else {
                            list.addAll(data.results);
                            setAdapter();
                            getLoaderManager().destroyLoader(LoaderID.FLEAMARKET);
                        }
                    }
                }

                @Override
                public void onLoaderReset(Loader<AllPostsBean> loader) {
                }
            };

    private void setAdapter() {
        mlistView.setAdapter(new Custome_All_ListAdapter(FleaFragment.this, list));
    }

}
