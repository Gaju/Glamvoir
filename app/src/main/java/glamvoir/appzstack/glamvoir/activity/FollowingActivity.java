package glamvoir.appzstack.glamvoir.activity;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.Collection;

import glamvoir.appzstack.glamvoir.R;
import glamvoir.appzstack.glamvoir.adapter.LoadableListAdapter;
import glamvoir.appzstack.glamvoir.apppreference.AppPreferences;
import glamvoir.appzstack.glamvoir.asynctaskloader.FollowLoader;
import glamvoir.appzstack.glamvoir.asynctaskloader.LoaderID;
import glamvoir.appzstack.glamvoir.constant.AppConstant;
import glamvoir.appzstack.glamvoir.helpers.Utility;
import glamvoir.appzstack.glamvoir.model.FollowResponse;
import glamvoir.appzstack.glamvoir.model.TaskResponse;
import glamvoir.appzstack.glamvoir.model.net.request.RequestBean;

/**
 * Created by gajendran on 5/8/15.
 */
public class FollowingActivity extends BaseActivity {

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, FollowingActivity.class);
        intent.putExtra("ParentClassName", context.getClass().getSimpleName());
        context.startActivity(intent);
    }

    private RequestBean mRequestBean;
    private Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected String getAppBarTitle() {
        return getResources().getString(R.string.following);
    }

    @Override
    public void loadData() {
        getLoaderManager().restartLoader(LoaderID.FOLLOW, null, followingCallback);
    }

    @Override
    protected LoadableListAdapter createAdapter(ArrayList data) {
        return null;
    }


    LoaderManager.LoaderCallbacks<TaskResponse<FollowResponse>> followingCallback =
            new LoaderManager.LoaderCallbacks<TaskResponse<FollowResponse>>() {

                @Override
                public Loader<TaskResponse<FollowResponse>> onCreateLoader(int id, Bundle args) {
                    AppPreferences appPreferences = new AppPreferences(mRequestBean.getContext());
                    return new FollowLoader(mRequestBean, AppConstant.FLAG_FOLLOWING, appPreferences.getUserId());
                }

                @Override
                public void onLoadFinished(Loader<TaskResponse<FollowResponse>> loader, TaskResponse<FollowResponse> data) {
                    if (loader instanceof FollowLoader) {
                        ((FollowLoader) loader).hideLoaderDialog();
                        if (data.error != null) {
                            Utility.showToast(FollowingActivity.this, data.error.toString());
                        } else {

                            if (data.data != null && data.data.error_code != null) {
                                onDataLoaded(data);
                            }
                        }
                    }
                }

                @Override
                public void onLoaderReset(Loader<TaskResponse<FollowResponse>> loader) {
                }
            };

    public void onDataLoaded(TaskResponse data) {

        dataLoaded((Collection) data);

        int pageno = 0;//default
        FollowResponse followResponseList = (FollowResponse) data.data;
        if (followResponseList != null) {

            adapter.notifyDataSetChanged();
        }

    }

    @Override
    public Intent getSupportParentActivityIntent() {
        Intent parentIntent = getIntent();
        String className = parentIntent.getStringExtra("ParentClassName"); //getting the parent class name
        Intent newIntent = null;
        try {
            //you need to define the class with package name
            newIntent = new Intent(FollowingActivity.this, Class.forName(AppConstant.PACKAGE + className));

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return newIntent;
    }
}
