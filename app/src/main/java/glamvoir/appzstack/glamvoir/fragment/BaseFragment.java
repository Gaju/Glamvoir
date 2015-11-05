package glamvoir.appzstack.glamvoir.fragment;

import android.content.IntentFilter;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import glamvoir.appzstack.glamvoir.Bean.AllPostsBean;
import glamvoir.appzstack.glamvoir.Bean.ParentPostBean;
import glamvoir.appzstack.glamvoir.R;
import glamvoir.appzstack.glamvoir.adapter.Custome_All_ListAdapter;
import glamvoir.appzstack.glamvoir.asynctask.SavePostAsyncTask;
import glamvoir.appzstack.glamvoir.asynctaskloader.GetAllPostLoader;
import glamvoir.appzstack.glamvoir.asynctaskloader.LoaderID;
import glamvoir.appzstack.glamvoir.helpers.Utility;
import glamvoir.appzstack.glamvoir.intentservice.NetworkIntentService;
import glamvoir.appzstack.glamvoir.interfaces.AsynTaskListener;
import glamvoir.appzstack.glamvoir.model.net.request.RequestBean;
import glamvoir.appzstack.glamvoir.network.InternetStatus;

/**
 * Created by gajendran on 1/10/15.
 */
public abstract class BaseFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    protected ListView mlistView;
    protected ArrayList<ParentPostBean> list = new ArrayList<ParentPostBean>();
    protected Custome_All_ListAdapter adapter;
    protected TextView txt_NoDataFound;
    protected View loadIndicator, view;
    protected EditText edt_Search;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
      public void onRefresh() {
        loadData();
    }

    protected abstract RequestBean getRequestBean();

    protected abstract String getPostType();

    protected abstract String getCategoryType();

    protected abstract Fragment getFragment();

    protected abstract View getHeaderView();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.layout_basefragment, container, false);
        mlistView = (ListView) rootView.findViewById(R.id.lv_all);

     //   QuickReturnListView listView = (QuickReturnListView) mlistView;
      //  listView.setQuickReturnView(getHeaderView());

        txt_NoDataFound = (TextView) rootView.findViewById(R.id.no_data_found);
        loadIndicator = rootView.findViewById(R.id.loadIndicator);
        edt_Search = (EditText) rootView.findViewById(R.id.tv_search);

        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        loadData();

        if (getFragment() instanceof FleaFragment) {
            edt_Search.setVisibility(View.VISIBLE);
            edt_Search.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    isKeyboardShown(edt_Search.getRootView());
                }
            });

        } else {
            edt_Search.setVisibility(View.GONE);
        }

        setAdapter();

        mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        protected int currentFirstVisibleItem;

        mlistView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                boolean enable = false;
                if(mlistView != null && mlistView.getChildCount() > 0){

                    // check if the first item of the list is visible
                    boolean firstItemVisible = mlistView.getFirstVisiblePosition() == 0;
                    // check if the top of the first item is visible
                    boolean topOfFirstItemVisible = mlistView.getChildAt(0).getTop() == 0;
                    // enabling or disabling the refresh layout
                    enable = firstItemVisible && topOfFirstItemVisible;
                }
                swipeRefreshLayout.setEnabled(enable);
               // else swipeRefreshLayout.setEnabled(false);
            }
        });

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    protected void loadData() {
        getLoaderManager().initLoader(LoaderID.GETPOST, null, ffspCallback);
    }

    LoaderManager.LoaderCallbacks<AllPostsBean> ffspCallback =
            new LoaderManager.LoaderCallbacks<AllPostsBean>() {

                @Override
                public Loader<AllPostsBean> onCreateLoader(int id, Bundle args) {
                    loadIndicator.setVisibility(View.VISIBLE);
                    return new GetAllPostLoader(getRequestBean(), getPostType(), getCategoryType());
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





    private void setAdapter() {
        adapter = new Custome_All_ListAdapter(getFragment(), list);
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
        getActivity().registerReceiver(getAdapter().observeLikeReceiver, filter);
        registered = true;
    }

    private boolean registered = false;

    @Override
    public void onPause() {
        super.onPause();
        if (registered) {
            getActivity().unregisterReceiver(getAdapter().observeLikeReceiver);
            registered = false;
        }
    }

    public void savePost(String methodName, String mUserID, String postID, int pos) {
        if (InternetStatus.isInternetAvailable(getActivity(), true)) {
            new SavePostAsyncTask(getActivity(), new AsynTaskListener() {
                @Override
                public void success(String success, String listenerId) {
                }

                @Override
                public void error(String errorMessage, String errorCode, String listenerId) {
                    Utility.showToast(getActivity(), errorMessage);
                }

                @Override
                public void successWithresult(List<Object> sucessObject, String message, String pos) {

                    if (message.equalsIgnoreCase("0")) {
                        Utility.showToast(getActivity(), "Post saved");

                        ParentPostBean item = list.get(Integer.parseInt(pos));
                        item.setIs_saved(Integer.parseInt("1"));
                        adapter.notifyDataSetChanged();

                    } else {
                        Utility.showToast(getActivity(), message);
                    }

                }
            }, "server").execute(methodName, mUserID, postID, String.valueOf(pos));
        }
    }

    private boolean isKeyboardShown(View rootView) {
        final int SOFT_KEYBOARD_HEIGHT_DP_THRESHOLD = 128;

        Rect r = new Rect();
        rootView.getWindowVisibleDisplayFrame(r);
        DisplayMetrics dm = rootView.getResources().getDisplayMetrics();
    /* heightDiff = rootView height - status bar height (r.top) - visible frame height (r.bottom - r.top) */
        int heightDiff = rootView.getBottom() - r.bottom;
    /* Threshold size: dp to pixels, multiply with display density */
        boolean isKeyboardShown = heightDiff > SOFT_KEYBOARD_HEIGHT_DP_THRESHOLD * dm.density;

        if (isKeyboardShown) {

            edt_Search.setBackgroundResource(R.drawable.edit_text_border);
            edt_Search.setPadding(20, 0, 0, 0);
            edt_Search.setCompoundDrawablesWithIntrinsicBounds(R.drawable.search, 0, 0, 0);

            edt_Search.setCursorVisible(true);
            edt_Search.requestFocus();

        } else {

            edt_Search.setCursorVisible(false);
            edt_Search.setBackgroundResource(R.drawable.edit_text_border_inactive);
            edt_Search.setPadding(20, 0, 0, 0);
            edt_Search.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_search_white_24dp, 0, 0, 0);

        }
        return isKeyboardShown;
    }


}
