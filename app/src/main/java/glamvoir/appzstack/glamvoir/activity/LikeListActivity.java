package glamvoir.appzstack.glamvoir.activity;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.View;
import android.widget.ListView;

import glamvoir.appzstack.glamvoir.R;
import glamvoir.appzstack.glamvoir.adapter.LikedUserAdapter;
import glamvoir.appzstack.glamvoir.apppreference.AppPreferences;
import glamvoir.appzstack.glamvoir.asynctaskloader.GetLikedUsersLoader;
import glamvoir.appzstack.glamvoir.asynctaskloader.LoaderID;
import glamvoir.appzstack.glamvoir.customview.CustomTextBold;
import glamvoir.appzstack.glamvoir.helpers.Utility;
import glamvoir.appzstack.glamvoir.model.TaskResponse;
import glamvoir.appzstack.glamvoir.model.net.request.RequestBean;
import glamvoir.appzstack.glamvoir.model.net.response.LikedUsers;

/**
 * Created by jai on 10/18/2015.
 */
public class LikeListActivity extends AppCompatActivity {

    protected Toolbar toolbar;
    private RequestBean mRequestBean;
    protected View loadIndicator;
    private ListView mlistView;
    private String postID;

    public static void startActivity(Context context, String postID) {
        Intent intent = new Intent(context, LikeListActivity.class);
        intent.putExtra("postID", postID);
        intent.putExtra("ParentClassName", context.getClass().getSimpleName());
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_likes_list);

        mRequestBean = new RequestBean();
        mRequestBean.setLoader(true);
        mRequestBean.setActivity(this);

        iniview();

        getToolbar(toolbar);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            postID = extras.getString("postID");
            getLikedUsers();

        }
    }

    private void iniview() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        loadIndicator = findViewById(R.id.loadIndicator);
        mlistView = (ListView) findViewById(R.id.listView);
    }

    private void getLikedUsers() {
        getLoaderManager().restartLoader(LoaderID.LIKED_USERS, null, likedUsersCallback);
    }

    LoaderManager.LoaderCallbacks<TaskResponse<LikedUsers>> likedUsersCallback =
            new LoaderManager.LoaderCallbacks<TaskResponse<LikedUsers>>() {

                @Override
                public Loader<TaskResponse<LikedUsers>> onCreateLoader(int id, Bundle args) {
                    loadIndicator.setVisibility(View.VISIBLE);
                    return new GetLikedUsersLoader(mRequestBean.getContext(), AppPreferences.getInstance(mRequestBean.getContext()).getUserId(), postID);
                }

                @Override
                public void onLoadFinished(Loader<TaskResponse<LikedUsers>> loader, TaskResponse<LikedUsers> data) {
                    if (loader instanceof GetLikedUsersLoader) {
                        loadIndicator.setVisibility(View.GONE);
                        if (data.error != null) {
                            Utility.showToast(mRequestBean.getContext(), data.error.toString());
                        } else {

                            if (data.data != null && data.data.error_code != null) {
                                mlistView.setAdapter(new LikedUserAdapter(mRequestBean.getContext(), data.data.results));
                            }
                        }
                    }
                }

                @Override
                public void onLoaderReset(Loader<TaskResponse<LikedUsers>> loader) {
                }
            };


    /**
     * customize the toolbar
     *
     * @param toolbar : pass the toolbar reference
     */
    private void getToolbar(Toolbar toolbar) {

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SpannableString s = new SpannableString("LIKE");
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
}
