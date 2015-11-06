package glamvoir.appzstack.glamvoir.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import glamvoir.appzstack.glamvoir.helpers.Utility;
import glamvoir.appzstack.glamvoir.intentservice.NetworkIntentService;
import glamvoir.appzstack.glamvoir.model.net.request.RequestBean;

/**
 * Created by gajendran on 4/11/15.
 */
public class SearchResultFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private ListView mlistView;
    private ArrayList<ParentPostBean> list = new ArrayList<ParentPostBean>();
    private Custome_All_ListAdapter adapter;

    private RequestBean mRequestBean;
    private View loadIndicator, view;
    private TextView txt_NoDataFound;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Context context;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /** Inflating the layout for this fragment **/
        View view = inflater.inflate(R.layout.fragment_search, null);

        mRequestBean = new RequestBean();
        mRequestBean.setLoader(true);
        mRequestBean.setActivity(getActivity());

        initViews(view);

        initListener();

        setAdapter();

        return view;
    }

    @Override
    public void onRefresh() {
        // performSearch();
    }


    private void setAdapter() {
        adapter = new Custome_All_ListAdapter(SearchResultFragment.this, list, true);
        mlistView.setAdapter(adapter);
    }


    public Custome_All_ListAdapter getAdapter() {
        return adapter;
    }


    @Override
    public void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(NetworkIntentService.BROADCAST_LIKE_ACTION);
        filter.addAction(NetworkIntentService.BROADCAST_FOLLOW_ACTION);
        filter.addAction(NetworkIntentService.BROADCAST_FOLLOW_ERROR);
        this.getActivity().registerReceiver(getAdapter().observeLikeReceiver, filter);
        registered = true;
    }

    private boolean registered = false;

    @Override
    public void onPause() {
        super.onPause();
        if (registered) {
            this.getActivity().unregisterReceiver(getAdapter().observeLikeReceiver);
            registered = false;
        }
    }

    /**
     * initialize all views listeners
     */
    private void initListener() {
    }

    /**
     * initialize all views
     */
    private void initViews(View view) {
        mlistView = (ListView) view.findViewById(R.id.lv_all);
        txt_NoDataFound = (TextView) view.findViewById(R.id.no_data_found);
        loadIndicator = view.findViewById(R.id.loadIndicator);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
    }


    LoaderManager.LoaderCallbacks<AllPostsBean> searchCallback =
            new LoaderManager.LoaderCallbacks<AllPostsBean>() {

                @Override
                public Loader<AllPostsBean> onCreateLoader(int id, Bundle args) {
                    loadIndicator.setVisibility(View.VISIBLE);
                    if (args != null) {
                        return new GetAllPostLoader(mRequestBean, AppConfig.GETALLPOST, AppConstant.CATEGORY_ALL, args.getString("keyword"));
                    }
                    return null;
                }

                @Override
                public void onLoadFinished(Loader<AllPostsBean> loader, AllPostsBean data) {
                    if (loader instanceof GetAllPostLoader) {
                        loadIndicator.setVisibility(View.GONE);
                        if (data.getSuccessCode() == 1) {
                            if (data.getmExceptionName().equals("No Result Found")) {
                                txt_NoDataFound.setVisibility(View.VISIBLE);
                            } else {
                                Utility.showToast(getActivity(), data.getmExceptionName());
                            }
                        } else {
                            if (data.results != null) {
                                if (data.results.size() > 0) {
                                    list.addAll(data.results);
                                    getAdapter().notifyDataSetChanged();
                                } else {
                                    txt_NoDataFound.setVisibility(View.VISIBLE);
                                }
                            } else {
                                txt_NoDataFound.setVisibility(View.VISIBLE);
                            }
                        }

                        if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }
                }

                @Override
                public void onLoaderReset(Loader<AllPostsBean> loader) {
                }
            };


    public void performSearch(String keyword) {
        Bundle b = new Bundle();
        b.putString("keyword", keyword);
        getLoaderManager().restartLoader(LoaderID.SEARCH, b, searchCallback);
    }


//
//    android.app.LoaderManager.LoaderCallbacks<AllPostsBean> ffspCallback =
//            new android.app.LoaderManager.LoaderCallbacks<AllPostsBean>() {
//
//                @Override
//                public android.content.Loader<AllPostsBean> onCreateLoader(int id, Bundle args) {
//                    loadIndicator.setVisibility(View.VISIBLE);
//                    return new SearchLoader(mRequestBean, AppConfig.GETALLPOST, AppConstant.CATEGORY_ALL, tv_search.getText().toString());
//                }
//
//                @Override
//                public void onLoadFinished(android.content.Loader<AllPostsBean> loader, AllPostsBean data) {
//                    loadIndicator.setVisibility(View.GONE);
//                    if (data.getSuccessCode() == 1) {
//                        if (data.getmExceptionName().equals("No Result Found")) {
//                            txt_NoDataFound.setVisibility(View.VISIBLE);
//                        } else {
//                            Utility.showToast(getActivity(), data.getmExceptionName());
//                        }
//                    } else {
//                        if (data.results != null) {
//                            if (data.results.size() > 0) {
//                                list.addAll(data.results);
//                                getAdapter().notifyDataSetChanged();
//                            } else {
//                                txt_NoDataFound.setVisibility(View.VISIBLE);
//                            }
//                        } else {
//                            txt_NoDataFound.setVisibility(View.VISIBLE);
//                        }
//                    }
//
//                    if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
//                        swipeRefreshLayout.setRefreshing(false);
//                    }
//                }
//
//                @Override
//                public void onLoaderReset(android.content.Loader<AllPostsBean> loader) {
//
//                }
//            };

//    public void savePost(String methodName, String mUserID, String postID, int pos) {
//        if (InternetStatus.isInternetAvailable(this, true)) {
//            new SavePostAsyncTask(this, new AsynTaskListener() {
//                @Override
//                public void success(String success, String listenerId) {
//                }
//
//                @Override
//                public void error(String errorMessage, String errorCode, String listenerId) {
//                    Utility.showToast(context, errorMessage);
//                }
//
//                @Override
//                public void successWithresult(List<Object> sucessObject, String message, String pos) {
//
//                    if (message.equalsIgnoreCase("0")) {
//                        Utility.showToast(context, "Post saved");
//
//                        ParentPostBean item = list.get(Integer.parseInt(pos));
//                        item.setIs_saved(Integer.parseInt("1"));
//                        adapter.notifyDataSetChanged();
//
//                    } else {
//                        Utility.showToast(context, message);
//                    }
//
//                }
//            }, "server").execute(methodName, mUserID, postID, String.valueOf(pos));
//        }
//    }
}
