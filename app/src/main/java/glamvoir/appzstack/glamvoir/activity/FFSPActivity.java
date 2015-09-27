package glamvoir.appzstack.glamvoir.activity;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;

import glamvoir.appzstack.glamvoir.R;
import glamvoir.appzstack.glamvoir.adapter.LoadableListAdapter;
import glamvoir.appzstack.glamvoir.apppreference.AppPreferences;
import glamvoir.appzstack.glamvoir.asynctaskloader.FFSPLoader;
import glamvoir.appzstack.glamvoir.asynctaskloader.LoaderID;
import glamvoir.appzstack.glamvoir.constant.AppConstant;
import glamvoir.appzstack.glamvoir.helpers.Utility;
import glamvoir.appzstack.glamvoir.model.FFSP_Response;
import glamvoir.appzstack.glamvoir.model.TaskResponse;
import glamvoir.appzstack.glamvoir.model.net.request.RequestBean;

/**
 * Created by gajendran on 16/8/15.
 */
public abstract class FFSPActivity extends AppCompatActivity {

    protected Toolbar toolbar;
    protected RecyclerView recyclerView;
    protected ArrayList<FFSP_Response.SingleFollow> data = new ArrayList<FFSP_Response.SingleFollow>();
    protected LoadableListAdapter<FFSP_Response.SingleFollow> adapter;
    protected TextView txt_Nodata;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_following);

        //initialize all views
        initViews();

        initListener();

        init();

        getToolbar(toolbar);
    }

    @Override
    protected void onResume() {
        super.onResume();

        loadData();
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

    }

    LoaderManager.LoaderCallbacks<TaskResponse<FFSP_Response>> ffspCallback =
            new LoaderManager.LoaderCallbacks<TaskResponse<FFSP_Response>>() {

                @Override
                public Loader<TaskResponse<FFSP_Response>> onCreateLoader(int id, Bundle args) {
                    AppPreferences appPreferences = new AppPreferences(getRequestBean().getContext());
                    return new FFSPLoader(getRequestBean(), getMethodName(), appPreferences.getUserId());
                }

                @Override
                public void onLoadFinished(Loader<TaskResponse<FFSP_Response>> loader, TaskResponse<FFSP_Response> data) {
                    if (loader instanceof FFSPLoader) {
                        ((FFSPLoader) loader).hideLoaderDialog();
                        if (data.error != null) {
                            Utility.showToast(getRequestBean().getContext(), data.error.toString());
                        } else {

                            if (data.data != null && data.data.error_code != null) {
                                dataLoaded(data.data);
                            }
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

    public void removeItem(int position) {
        adapter.removeItem(position);
    }
}
