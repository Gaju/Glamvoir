package glamvoir.appzstack.glamvoir.activity;

import android.app.LoaderManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.Loader;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

import glamvoir.appzstack.glamvoir.R;
import glamvoir.appzstack.glamvoir.apppreference.AppPreferences;
import glamvoir.appzstack.glamvoir.asynctaskloader.LoaderID;
import glamvoir.appzstack.glamvoir.asynctaskloader.ProfileLoader;
import glamvoir.appzstack.glamvoir.constant.AppConstant;
import glamvoir.appzstack.glamvoir.customview.CustomTextBold;
import glamvoir.appzstack.glamvoir.helpers.ImageLoaderInitializer;
import glamvoir.appzstack.glamvoir.helpers.Utility;
import glamvoir.appzstack.glamvoir.intentservice.ObserveFFSPIntentService;
import glamvoir.appzstack.glamvoir.model.TaskResponse;
import glamvoir.appzstack.glamvoir.model.net.request.RequestBean;
import glamvoir.appzstack.glamvoir.model.net.response.ObservedFollowResponse;
import glamvoir.appzstack.glamvoir.model.net.response.ProfileResponse;
import glamvoir.appzstack.glamvoir.network.Communication;

/**
 * Created by jaim on 9/9/2015.
 */
public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private RequestBean mRequestBean;
    private Toolbar toolbar;
    private ImageView userImage;
    private TextView txt_UserName;
    protected View loadIndicator;
    private ProgressBar progressbar;
    private String followerID;
    private Button btn_ToggleFollow;
    private Button btn_ListFollower, btn_ListFollowing, btn_List_Activity;

    public static void startActivity(Context context, String userID) {
        Intent intent = new Intent(context, ProfileActivity.class);
        intent.putExtra("ParentClassName", context.getClass().getSimpleName());
        intent.putExtra("userID", userID);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_profile);

        initViews();

        initListener();

        mRequestBean = new RequestBean();
        mRequestBean.setLoader(true);
        mRequestBean.setActivity(this);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            getLoaderManager().restartLoader(LoaderID.GETPROFILE, bundle, getProfileCallback);
        }

        getToolbar(toolbar);
    }

    private void initListener() {

        btn_ToggleFollow.setOnClickListener(this);
        btn_ListFollower.setOnClickListener(this);
        btn_ListFollowing.setOnClickListener(this);
        btn_List_Activity.setOnClickListener(this);
    }

    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        userImage = (ImageView) findViewById(R.id.imageView4);
        txt_UserName = (TextView) findViewById(R.id.tv_user_name);
        loadIndicator = findViewById(R.id.loadIndicator);
        progressbar = (ProgressBar) findViewById(R.id.progressbar);
        btn_ToggleFollow = (Button) findViewById(R.id.bt_pro_following);
        btn_ListFollower = (Button) findViewById(R.id.bt_pro_followers);
        btn_ListFollowing = (Button) findViewById(R.id.bt_can_following);
        btn_List_Activity = (Button) findViewById(R.id.bt_pro_activity);
    }

    private void getToolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SpannableString s = new SpannableString("USER PROFILE");
        s.setSpan(new CustomTextBold(this), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        getSupportActionBar().setTitle(s);

    }

    @Override
    public Intent getSupportParentActivityIntent() {
        Intent newIntent = null;
        finish();
        return newIntent;
    }


    LoaderManager.LoaderCallbacks<TaskResponse<ProfileResponse>> getProfileCallback =
            new LoaderManager.LoaderCallbacks<TaskResponse<ProfileResponse>>() {

                @Override
                public Loader<TaskResponse<ProfileResponse>> onCreateLoader(int id, Bundle args) {
                    loadIndicator.setVisibility(View.VISIBLE);
                    return new ProfileLoader(mRequestBean, AppConstant.METHOD_GETPROFILE, args.getString("userID"));
                }

                @Override
                public void onLoadFinished(Loader<TaskResponse<ProfileResponse>> loader, TaskResponse<ProfileResponse> data) {
                    if (loader instanceof ProfileLoader) {
                        loadIndicator.setVisibility(View.GONE);
                        if (data.error != null) {
                            Utility.showToast(mRequestBean.getContext(), data.error.toString());
                        } else {

                            if (data.data != null && data.data.error_code != null) {
                                followerID = data.data.results.get(0).user_id;
                                updateUI(data.data.results);
                            }
                        }
                    }
                }

                @Override
                public void onLoaderReset(Loader<TaskResponse<ProfileResponse>> loader) {
                }
            };


    private void updateUI(List<ProfileResponse.GetProfileResponse> data) {

        if (data.get(0).user_fname != null) {
            txt_UserName.setText(data.get(0).user_fname + " " + data.get(0).user_lname);
        }

        if (data.get(0).user_image != null && !data.get(0).user_image.equals("")) {
            displayImage(userImage, data.get(0).user_image);
        } else {
            userImage.setImageResource(R.drawable.pic);
        }
    }

    private void displayImage(ImageView imageView, String path) {
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(ProfileActivity.this));
        DisplayImageOptions options = ImageLoaderInitializer.getDisplayImageOptionWithFade();
        imageLoader.displayImage(path, imageView, options);

    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.bt_pro_following:
                ObserveFFSPIntentService.startObserveAdService(ProfileActivity.this, AppPreferences.getInstance(this).getUserId(), followerID, 0);
                progressbar.setVisibility(View.VISIBLE);
               // new ToggleFollow().execute();
                break;
            case R.id.bt_pro_followers:
                break;
            case R.id.bt_can_following:
                break;
            case R.id.bt_pro_activity:
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //Utility.showToast(getActivity(), "on resume");
        IntentFilter filter = new IntentFilter();
        filter.addAction(ObserveFFSPIntentService.BROADCAST_ACTION_OBSERVED);

        registerReceiver(observeFollowReceiver, filter);
        registered = true;
    }

    private boolean registered = false;

    @Override
    public void onPause() {
        super.onPause();
        //Utility.showToast(getActivity(), "onPause frag");

        if (registered) {
            unregisterReceiver(observeFollowReceiver);
            registered = false;
        }
    }


    public BroadcastReceiver observeFollowReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            progressbar.setVisibility(View.GONE);
            if (action.equals(ObserveFFSPIntentService.BROADCAST_ACTION_OBSERVED)) {
                String isFollowing = intent.getStringExtra(ObserveFFSPIntentService.BROADCAST_EXTRA_ISFOLLOW_ADDED);
                String totalCount = intent.getStringExtra(ObserveFFSPIntentService.BROADCAST_EXTRA_TOTAL_FOLLOWER);
                int position = intent.getIntExtra(ObserveFFSPIntentService.INTENT_ARG_FLAG, 0);

                if (isFollowing.equals("0")) {
                    btn_ToggleFollow.setText("Follow");
                } else {
                    btn_ToggleFollow.setText("Following");
                }
            }
        }
    };


    class ToggleFollow extends AsyncTask<Void, Void, String> {
        TaskResponse<ObservedFollowResponse> response = new TaskResponse<ObservedFollowResponse>();

        protected void onPreExecute() {
            progressbar.setVisibility(View.VISIBLE);
        }

        protected String doInBackground(Void... arg0) {

            response.data = Communication.toggleFollow(AppConstant.METHOD_FOLLOWER_FOLLOWING, followerID, AppPreferences.getInstance(ProfileActivity.this).getUserId());

            if (response.data.isSucceeded()) {
                if (response.data != null) {
                    return response.data.observedFollowIds.get(0).is_followng;
                }
            }
            return null;
        }

        protected void onPostExecute(String isFollowing) {

            progressbar.setVisibility(View.GONE);
            if (isFollowing.equals("0")) {
                btn_ToggleFollow.setText("Follow");
            } else {
                btn_ToggleFollow.setText("Following");
            }
        }
    }
}
