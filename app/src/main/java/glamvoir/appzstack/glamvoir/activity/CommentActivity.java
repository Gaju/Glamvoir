package glamvoir.appzstack.glamvoir.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collection;

import glamvoir.appzstack.glamvoir.R;
import glamvoir.appzstack.glamvoir.adapter.CommentCustomAdapter;
import glamvoir.appzstack.glamvoir.apppreference.AppPreferences;
import glamvoir.appzstack.glamvoir.asynctaskloader.LoaderID;
import glamvoir.appzstack.glamvoir.asynctaskloader.CommentLoader;
import glamvoir.appzstack.glamvoir.constant.AppConstant;
import glamvoir.appzstack.glamvoir.helpers.Utility;
import glamvoir.appzstack.glamvoir.model.TaskResponse;
import glamvoir.appzstack.glamvoir.model.net.request.RequestBean;
import glamvoir.appzstack.glamvoir.model.net.response.CommentResponse;

/**
 * Created by jai on 9/26/2015.
 */
public class CommentActivity extends AppCompatActivity {

    private ListView mlistView;
    protected Toolbar toolbar;
    private RequestBean mRequestBean;
    private CommentCustomAdapter adapter;

    private ArrayList<CommentResponse.AllCommentResponse> list = new ArrayList<>();

    public static void startActvity(Context context, String postID, String comment) {
        Intent intent = new Intent(context, CommentActivity.class);
        //intent.putExtra("ParentClassName", "All");
        intent.putExtra("postID", postID);
        intent.putExtra("comment", comment);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_comment);

        mRequestBean = new RequestBean();
        mRequestBean.setLoader(true);
        mRequestBean.setActivity(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            // comment(extras.getString("postID"), extras.getString("postID"));
            getLoaderManager().initLoader(LoaderID.COMMENT, extras, commentCallback);
        }

        iniview();

        getToolbar(toolbar);

        adapter = new CommentCustomAdapter(CommentActivity.this, list);
        mlistView.setAdapter(adapter);
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
        getSupportActionBar().setTitle("COMMENT");
    }

    private void iniview() {

        mlistView = (ListView) findViewById(R.id.listView);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    @Override
    public Intent getSupportParentActivityIntent() {
        // Intent parentIntent = getIntent();
        // String className = parentIntent.getStringExtra("ParentClassName"); //getting the parent class name
        Intent newIntent = null;
        finish();
        return newIntent;
    }


    private void comment(String postID, String comment) {
        Bundle args = new Bundle();
        args.putString("postID", postID);
        args.putString("comment", comment);
        getLoaderManager().initLoader(LoaderID.COMMENT, args, commentCallback);
    }

    android.app.LoaderManager.LoaderCallbacks<TaskResponse<CommentResponse>> commentCallback =
            new android.app.LoaderManager.LoaderCallbacks<TaskResponse<CommentResponse>>() {

                @Override
                public android.content.Loader<TaskResponse<CommentResponse>> onCreateLoader(int id, Bundle args) {
                    AppPreferences appPreferences = new AppPreferences(mRequestBean.getContext());
                    if (args.getString("comment") != null) {
                        return new CommentLoader(mRequestBean, AppConstant.METHOD_ADDCOMMENT, appPreferences.getUserId(), args.getString("postID"), args.getString("comment"));
                    } else {
                        return new CommentLoader(mRequestBean, AppConstant.METHOD_GETCOMMENT, appPreferences.getUserId(), args.getString("postID"));
                    }
                }

                @Override
                public void onLoadFinished(android.content.Loader<TaskResponse<CommentResponse>> loader, TaskResponse<CommentResponse> data) {
                    if (loader instanceof CommentLoader) {
                       // ((CommentLoader) loader).hideLoaderDialog();
                        if (data.error != null) {
                            Utility.showToast(CommentActivity.this, data.error.toString());
                        } else {

                            list.addAll(data.data.results);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }

                @Override
                public void onLoaderReset(android.content.Loader<TaskResponse<CommentResponse>> loader) {
                }
            };
}
