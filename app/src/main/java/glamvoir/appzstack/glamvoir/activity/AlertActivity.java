package glamvoir.appzstack.glamvoir.activity;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.View;
import android.widget.ListView;

import glamvoir.appzstack.glamvoir.R;
import glamvoir.appzstack.glamvoir.adapter.LikedUserAdapter;
import glamvoir.appzstack.glamvoir.adapter.NotificationAdapter;
import glamvoir.appzstack.glamvoir.apppreference.AppPreferences;
import glamvoir.appzstack.glamvoir.asynctaskloader.GetListNotificationLoader;
import glamvoir.appzstack.glamvoir.asynctaskloader.LoaderID;
import glamvoir.appzstack.glamvoir.constant.AppConstant;
import glamvoir.appzstack.glamvoir.customview.CustomTextBold;
import glamvoir.appzstack.glamvoir.helpers.Utility;
import glamvoir.appzstack.glamvoir.model.TaskResponse;
import glamvoir.appzstack.glamvoir.model.net.request.RequestBean;
import glamvoir.appzstack.glamvoir.model.net.response.ListNotificationResponse;

/**
 * Created by acer pc on 08-08-2015.
 */
public class AlertActivity extends AppCompatActivity {

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, AlertActivity.class);
        intent.putExtra("ParentClassName", context.getClass().getSimpleName());
        context.startActivity(intent);
    }

    private RequestBean mRequestBean;
    private Toolbar toolbar;
    protected View loadIndicator;
    private ListView mlistView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_alert);
        mRequestBean = new RequestBean();
        mRequestBean.setLoader(true);
        mRequestBean.setActivity(this);
        mRequestBean.setLoader(true);

        //initialize all views
        initViews();

        initListener();

        getToolbar(toolbar);

        getAlertNotification();
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
        SpannableString s = new SpannableString(getResources().getString(R.string.alert));
        s.setSpan(new CustomTextBold(this), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        getSupportActionBar().setTitle(s);

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

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        loadIndicator = findViewById(R.id.loadIndicator);
        mlistView = (ListView) findViewById(R.id.listView);
    }

    @Override
    public Intent getSupportParentActivityIntent() {
        Intent parentIntent = getIntent();
        String className = parentIntent.getStringExtra("ParentClassName"); //getting the parent class name
        Intent newIntent = null;
        try {
            //you need to define the class with package name
            newIntent = new Intent(AlertActivity.this, Class.forName(AppConstant.PACKAGE + className));

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return newIntent;
    }


    private void getAlertNotification() {
        getLoaderManager().restartLoader(LoaderID.GET_NOTIFICATION, null, getNotificationCallback);
    }

    LoaderManager.LoaderCallbacks<TaskResponse<ListNotificationResponse>> getNotificationCallback =
            new LoaderManager.LoaderCallbacks<TaskResponse<ListNotificationResponse>>() {

                @Override
                public Loader<TaskResponse<ListNotificationResponse>> onCreateLoader(int id, Bundle args) {
                    loadIndicator.setVisibility(View.VISIBLE);
                    return new GetListNotificationLoader(mRequestBean.getContext());
                }

                @Override
                public void onLoadFinished(Loader<TaskResponse<ListNotificationResponse>> loader, TaskResponse<ListNotificationResponse> data) {
                    if (loader instanceof GetListNotificationLoader) {
                        loadIndicator.setVisibility(View.GONE);
                        if (data.error != null) {
                            Utility.showToast(mRequestBean.getContext(), data.error.toString());
                        } else {

                            if (data.data != null && data.data.error_code != null) {
                                mlistView.setAdapter(new NotificationAdapter(mRequestBean.getContext(), data.data.results));
                            }
                        }
                    }
                }

                @Override
                public void onLoaderReset(Loader<TaskResponse<ListNotificationResponse>> loader) {
                }
            };


}
