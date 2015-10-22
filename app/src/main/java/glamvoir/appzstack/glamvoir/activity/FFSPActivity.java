package glamvoir.appzstack.glamvoir.activity;

import android.app.LoaderManager;
import android.content.IntentFilter;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import glamvoir.appzstack.glamvoir.R;
import glamvoir.appzstack.glamvoir.adapter.FFSP_Adapter;
import glamvoir.appzstack.glamvoir.adapter.LoadableListAdapter;
import glamvoir.appzstack.glamvoir.apppreference.AppPreferences;
import glamvoir.appzstack.glamvoir.asynctask.DeleteMySaveAsyncTask;
import glamvoir.appzstack.glamvoir.asynctaskloader.FFSPLoader;
import glamvoir.appzstack.glamvoir.asynctaskloader.LoaderID;
import glamvoir.appzstack.glamvoir.constant.AppConstant;
import glamvoir.appzstack.glamvoir.helpers.Utility;
import glamvoir.appzstack.glamvoir.intentservice.ObserveFFSPIntentService;
import glamvoir.appzstack.glamvoir.interfaces.AsynTaskListener;
import glamvoir.appzstack.glamvoir.model.FFSP_Response;
import glamvoir.appzstack.glamvoir.model.TaskResponse;
import glamvoir.appzstack.glamvoir.model.net.request.RequestBean;
import glamvoir.appzstack.glamvoir.network.InternetStatus;

/**
 * Created by gajendran on 16/8/15.
 */
public abstract class FFSPActivity extends AppCompatActivity {

    protected Toolbar toolbar;
    protected RecyclerView recyclerView;
    protected ArrayList<FFSP_Response.SingleFollow> data = new ArrayList<FFSP_Response.SingleFollow>();
    protected LoadableListAdapter<FFSP_Response.SingleFollow> adapter;
    protected TextView txt_Nodata;
    protected View loadIndicator;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ffsp);

        //initialize all views
        initViews();

        initListener();

        init();

        getToolbar(toolbar);
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadData();
    }

    @Override
    public void onResume() {
        super.onResume();
        //Utility.showToast(getActivity(), "on resume");
        IntentFilter filter = new IntentFilter();
        filter.addAction(ObserveFFSPIntentService.BROADCAST_ACTION_OBSERVED);

        registerReceiver(getAdapter().observeFollowReceiver, filter);
        registered = true;
    }

    private boolean registered = false;

    @Override
    public void onPause() {
        super.onPause();
        //Utility.showToast(getActivity(), "onPause frag");

        if (registered) {
            unregisterReceiver(getAdapter().observeFollowReceiver);
            registered = false;
        }
    }

    /**
     * customize the toolbar
     *
     * @param toolbar : pass the toolbar reference
     */
    private void getToolbar(Toolbar toolbar) {

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getAppBarTitle());
    }

    protected abstract String getMethodName();

    protected abstract RequestBean getRequestBean();

    protected abstract String getAppBarTitle();

    protected abstract LoadableListAdapter<FFSP_Response.SingleFollow> createAdapter(ArrayList<FFSP_Response.SingleFollow> data);

    protected void loadData() {
        getLoaderManager().restartLoader(LoaderID.FFSP, null, ffspCallback);
    }

    public FFSP_Adapter getAdapter() {
        return (FFSP_Adapter) adapter;
    }


    /**
     * initialize all views listeners
     */
    private void initListener() {

    }

    public void init() {

        adapter = createAdapter(data);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }


    /**
     * initialize all views
     */
    private void initViews() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        txt_Nodata = (TextView) findViewById(R.id.nodata);
        loadIndicator = findViewById(R.id.loadIndicator);

    }

    LoaderManager.LoaderCallbacks<TaskResponse<FFSP_Response>> ffspCallback =
            new LoaderManager.LoaderCallbacks<TaskResponse<FFSP_Response>>() {

                @Override
                public Loader<TaskResponse<FFSP_Response>> onCreateLoader(int id, Bundle args) {
                    loadIndicator.setVisibility(View.VISIBLE);
                    AppPreferences appPreferences = new AppPreferences(getRequestBean().getContext());
                    return new FFSPLoader(getRequestBean(), getMethodName(), appPreferences.getUserId());
                }

                @Override
                public void onLoadFinished(Loader<TaskResponse<FFSP_Response>> loader, TaskResponse<FFSP_Response> data) {
                    if (loader instanceof FFSPLoader) {
                        loadIndicator.setVisibility(View.GONE);
                        if (data.error != null) {
                            Utility.showToast(getRequestBean().getContext(), data.error.toString());
                        } else {

                            if (data.data != null && data.data.error_code != null) {
                                dataLoaded(data.data);
                            }
                            getLoaderManager().destroyLoader(LoaderID.FFSP);
                        }
                    }
                }

                @Override
                public void onLoaderReset(Loader<TaskResponse<FFSP_Response>> loader) {
                }
            };


    public void dataLoaded(FFSP_Response responseData) {

        if (responseData != null && responseData.results.size() > 0) {
            if (this.data != null)
                data.clear();
            this.data.addAll(responseData.results);
            adapter.notifyDataSetChanged();
        } else {
            txt_Nodata.setVisibility(View.VISIBLE);
        }

//            this.totalRows = totalRows;
//
//            if (totalRows > this.data.size()) {
//                adapter.setLoadingFooterType(LoadableListAdapter1.LoadingFooterType.Loading);
//            } else {
//                adapter.setLoadingFooterType(LoadableListAdapter1.LoadingFooterType.None);
//            }


    }

    public void removeItem(int position, String userID, String postID) {
        // adapter.removeItem(position);
        deletePost(AppConstant.METHOD_DELETE_SAVE_POST, userID, postID, position);
    }


    public void followFollower(String followerID, int position) {
        ObserveFFSPIntentService.startObserveAdService(FFSPActivity.this, AppPreferences.getInstance(this).getUserId(), followerID, position);
    }


    public void deletePost(String methodName, String mUserID, String postID, final int position) {
        if (InternetStatus.isInternetAvailable(this, true)) {
            new DeleteMySaveAsyncTask(this, new AsynTaskListener() {
                @Override
                public void success(String success, String listenerId) {

                }

                @Override
                public void error(String errorMessage, String errorCode, String listenerId) {
                    Utility.showToast(FFSPActivity.this, errorMessage);
                }

                @Override
                public void successWithresult(List<Object> sucessObject, String message, String listenerId) {

                    if (message.equalsIgnoreCase("0")) {
                        Utility.showToast(FFSPActivity.this, "Post Deleted");
                        adapter.removeItem(position);
//                        FFSP_Response response = (FFSP_Response) sucessObject.get(0);
//                        data.clear();
//                        data.addAll(response.results);
//                        adapter.notifyDataSetChanged();
                    } else {
                        Utility.showToast(FFSPActivity.this, message);
                    }

                }
            }, "server").execute(methodName, mUserID, postID);
        }
    }
}
