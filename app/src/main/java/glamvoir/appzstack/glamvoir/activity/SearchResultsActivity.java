package glamvoir.appzstack.glamvoir.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import glamvoir.appzstack.glamvoir.Bean.ParentPostBean;
import glamvoir.appzstack.glamvoir.R;
import glamvoir.appzstack.glamvoir.adapter.Custome_All_ListAdapter;
import glamvoir.appzstack.glamvoir.asynctask.SavePostAsyncTask;
import glamvoir.appzstack.glamvoir.constant.AppConstant;
import glamvoir.appzstack.glamvoir.fragment.SearchResultFragment;
import glamvoir.appzstack.glamvoir.helpers.Utility;
import glamvoir.appzstack.glamvoir.interfaces.AsynTaskListener;
import glamvoir.appzstack.glamvoir.model.net.request.RequestBean;
import glamvoir.appzstack.glamvoir.network.InternetStatus;


/**
 * Created by gajendran on 2/11/15.
 */
public class SearchResultsActivity extends AppCompatActivity {
    private ListView mlistView;
    private ArrayList<ParentPostBean> list = new ArrayList<ParentPostBean>();
    private Custome_All_ListAdapter adapter;

    private Toolbar toolbar;
    private EditText tv_search;
    private RequestBean mRequestBean;
    SearchResultFragment fragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_search);

        mRequestBean = new RequestBean();
        mRequestBean.setLoader(true);
        mRequestBean.setActivity(this);
        fragment = new SearchResultFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container, fragment, "search");
        fragmentTransaction.commit();

        //initialize all views
        initViews();

        initListener();

        getToolbar(toolbar);

    }


    /**
     * initialize all views listeners
     */
    private void initListener() {
    }

    /**
     * initialize all views
     */
    private void initViews() {

        toolbar = (Toolbar) findViewById(R.id.searchtoolbar);

        tv_search = (EditText) findViewById(R.id.tv_search);

        tv_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (tv_search.getText().toString().length() > 0) {
                        fragment.performSearch(tv_search.getText().toString());
                    }

                    return true;
                }
                return false;
            }
        });
    }


//
//    private void performSearch() {
//        getLoaderManager().initLoader(LoaderID.GETPOST, null, ffspCallback);
//    }
//
//    android.app.LoaderManager.LoaderCallbacks<AllPostsBean> ffspCallback =
//            new android.app.LoaderManager.LoaderCallbacks<AllPostsBean>() {
//
//                @Override
//                public android.content.Loader<AllPostsBean> onCreateLoader(int id, Bundle args) {
//                    loadIndicator.setVisibility(View.VISIBLE);
//                    return new SearchLoader(mRequestBean, AppConfig.GETALLPOST, AppConstant.CATEGORY_ALL,tv_search.getText().toString());
//                }
//
//                @Override
//                public void onLoadFinished(android.content.Loader<AllPostsBean> loader, AllPostsBean data)
//                    {
//                        loadIndicator.setVisibility(View.GONE);
//                        if (data.getSuccessCode() == 1) {
//                            if (data.getmExceptionName().equals("No Result Found")) {
//                                txt_NoDataFound.setVisibility(View.VISIBLE);
//                            } else {
//                                Utility.showToast(SearchResultsActivity.this, data.getmExceptionName());
//                            }
//                        } else {
//                            if (data.results != null) {
//                                if (data.results.size() > 0) {
//                                    list.addAll(data.results);
//                                    getAdapter().notifyDataSetChanged();
//                                } else {
//                                    txt_NoDataFound.setVisibility(View.VISIBLE);
//                                }
//                            } else {
//                                txt_NoDataFound.setVisibility(View.VISIBLE);
//                            }
//                        }
//
//                        if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
//                            swipeRefreshLayout.setRefreshing(false);
//                        }
//                    }
//
//                @Override
//                public void onLoaderReset(android.content.Loader<AllPostsBean> loader) {
//
//                }
//            };

    /**
     * customize the toolbar
     *
     * @param toolbar : pass the toolbar reference
     */
    private void getToolbar(Toolbar toolbar) {

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
    }

    @Override
    public Intent getSupportParentActivityIntent() {
        Intent parentIntent = getIntent();
        String className = parentIntent.getStringExtra("ParentClassName"); //getting the parent class name
        Intent newIntent = null;
        try {
            //you need to define the class with package name
            newIntent = new Intent(SearchResultsActivity.this, Class.forName(AppConstant.PACKAGE + className));

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return newIntent;
    }


//    private void setAdapter() {
//        adapter = new Custome_All_ListAdapter(this, list);
//        mlistView.setAdapter(adapter);
//    }
//
//
//    public Custome_All_ListAdapter getAdapter() {
//        return adapter;
//    }
//

//    @Override
//    public void onResume() {
//        super.onResume();
//        IntentFilter filter = new IntentFilter();
//        filter.addAction(NetworkIntentService.BROADCAST_LIKE_ACTION);
//        filter.addAction(NetworkIntentService.BROADCAST_FOLLOW_ACTION);
//        filter.addAction(NetworkIntentService.BROADCAST_FOLLOW_ERROR);
//        this.registerReceiver(getAdapter().observeLikeReceiver, filter);
//        registered = true;
//    }
//
//    private boolean registered = false;
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        if (registered) {
//            this.unregisterReceiver(getAdapter().observeLikeReceiver);
//            registered = false;
//        }
//    }

    public void savePost(String methodName, String mUserID, String postID, int pos) {
        if (InternetStatus.isInternetAvailable(this, true)) {
            new SavePostAsyncTask(this, new AsynTaskListener() {
                @Override
                public void success(String success, String listenerId) {
                }

                @Override
                public void error(String errorMessage, String errorCode, String listenerId) {
                    Utility.showToast(SearchResultsActivity.this, errorMessage);
                }

                @Override
                public void successWithresult(List<Object> sucessObject, String message, String pos) {

                    if (message.equalsIgnoreCase("0")) {
                        Utility.showToast(SearchResultsActivity.this, "Post saved");

                        ParentPostBean item = list.get(Integer.parseInt(pos));
                        item.setIs_saved(Integer.parseInt("1"));
                        adapter.notifyDataSetChanged();

                    } else {
                        Utility.showToast(SearchResultsActivity.this, message);
                    }

                }
            }, "server").execute(methodName, mUserID, postID, String.valueOf(pos));
        }
    }
}
