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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

import glamvoir.appzstack.glamvoir.R;
import glamvoir.appzstack.glamvoir.adapter.CommentCustomAdapter;
import glamvoir.appzstack.glamvoir.apppreference.AppPreferences;
import glamvoir.appzstack.glamvoir.asynctaskloader.CommentLoader;
import glamvoir.appzstack.glamvoir.asynctaskloader.LoaderID;
import glamvoir.appzstack.glamvoir.constant.AppConstant;
import glamvoir.appzstack.glamvoir.customview.CustomTextBold;
import glamvoir.appzstack.glamvoir.helpers.Utility;
import glamvoir.appzstack.glamvoir.model.TaskResponse;
import glamvoir.appzstack.glamvoir.model.net.request.RequestBean;
import glamvoir.appzstack.glamvoir.model.net.response.CommentResponse;

/**
 * Created by jai on 9/26/2015.
 */
public class CommentActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView mlistView;
    protected Toolbar toolbar;
    private RequestBean mRequestBean;
    private CommentCustomAdapter adapter;
    private EditText et_comment;
    private ImageButton bt_sent;
    private String postID;
    private ArrayList<CommentResponse.AllCommentResponse> list = new ArrayList<>();
    protected View loadIndicator;

    public static void startActvity(Context context, String postID, String comment) {
        Intent intent = new Intent(context, CommentActivity.class);
        intent.putExtra("postID", postID);
        intent.putExtra("comment", comment);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_comment);

        iniview();

        getToolbar(toolbar);

        initClickListener();

        mRequestBean = new RequestBean();
        mRequestBean.setLoader(true);
        mRequestBean.setActivity(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            // comment(extras.getString("postID"), extras.getString("postID"));
            postID = extras.getString("postID");
            getLoaderManager().initLoader(LoaderID.COMMENT, extras, commentCallback);
        }

        adapter = new CommentCustomAdapter(CommentActivity.this, list);
        mlistView.setAdapter(adapter);
    }

    private void initClickListener() {
        bt_sent.setOnClickListener(this);
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
        SpannableString s = new SpannableString("COMMENT");
        s.setSpan(new CustomTextBold(this), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        getSupportActionBar().setTitle(s);

    }

    private void iniview() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mlistView = (ListView) findViewById(R.id.listView);
        et_comment = (EditText) findViewById(R.id.et_comment);
        bt_sent = (ImageButton) findViewById(R.id.bt_sent);
        loadIndicator = findViewById(R.id.loadIndicator);
    }

    @Override
    public Intent getSupportParentActivityIntent() {

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

    LoaderManager.LoaderCallbacks<TaskResponse<CommentResponse>> commentCallback =
            new LoaderManager.LoaderCallbacks<TaskResponse<CommentResponse>>() {

                @Override
                public Loader<TaskResponse<CommentResponse>> onCreateLoader(int id, Bundle args) {
                    AppPreferences appPreferences = new AppPreferences(mRequestBean.getContext());
                    loadIndicator.setVisibility(View.VISIBLE);
                    if (args.getString("comment") != null) {
                        Loader loader = new CommentLoader(mRequestBean, AppConstant.METHOD_ADDCOMMENT, appPreferences.getUserId(), args.getString("postID"), args.getString("comment"));
                        return loader;
                    } else {
                        Loader loader = new CommentLoader(mRequestBean, AppConstant.METHOD_GETCOMMENT, appPreferences.getUserId(), args.getString("postID"));
                        return loader;
                    }
                }

                @Override
                public void onLoadFinished(android.content.Loader<TaskResponse<CommentResponse>> loader, TaskResponse<CommentResponse> data) {
                    if (loader instanceof CommentLoader) {
                        loadIndicator.setVisibility(View.GONE);
                        if (data.error != null) {
                            Utility.showToast(CommentActivity.this, data.error.toString());
                        } else {
                            clearComment();
                            list.clear();
                            list.addAll(data.data.results);
                            adapter.notifyDataSetChanged();
                            mlistView.setSelection(list.size());
                            getLoaderManager().destroyLoader(LoaderID.COMMENT);
                        }
                    }
                }

                @Override
                public void onLoaderReset(android.content.Loader<TaskResponse<CommentResponse>> loader) {
                }
            };

    @Override
    public void onClick(View v) {
        if (et_comment.getText().toString().length() > 0) {

            comment(postID, et_comment.getText().toString());
            et_comment.setText("");
        } else {
            Utility.showToast(mRequestBean.getContext(), "Comment should not be empty");
        }
    }

    private void clearComment() {
        et_comment.setText("");
    }


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_likes, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.likes) {

            LikeListActivity.startActivity(CommentActivity.this,postID);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/


}
