package glamvoir.appzstack.glamvoir.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import glamvoir.appzstack.glamvoir.R;
import glamvoir.appzstack.glamvoir.adapter.FFSP_Adapter;
import glamvoir.appzstack.glamvoir.adapter.LoadableListAdapter;
import glamvoir.appzstack.glamvoir.constant.AppConstant;
import glamvoir.appzstack.glamvoir.model.net.request.RequestBean;

/**
 * Created by gajendran on 5/8/15.
 */
public class MyPostActivity extends FFSPActivity {
    private boolean canDelete = false;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, MyPostActivity.class);
        intent.putExtra("ParentClassName", context.getClass().getSimpleName());
        context.startActivity(intent);
    }

    private RequestBean mRequestBean;
    private Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRequestBean = new RequestBean();
        mRequestBean.setLoader(true);
        mRequestBean.setActivity(this);
        mRequestBean.setLoader(true);

        //initialize all views
        initViews();

        initListener();

        getToolbar(toolbar);
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
        getSupportActionBar().setTitle(getResources().getString(R.string.mypost));
    }

    @Override
    protected String getMethodName() {
        return AppConstant.METHOD_MYPOST;
    }

    @Override
    protected RequestBean getRequestBean() {
        return mRequestBean;
    }

    @Override
    protected String getAppBarTitle() {

        return "MY POSTS";
    }

    @Override
    protected LoadableListAdapter createAdapter(ArrayList data) {
        return new FFSP_Adapter(MyPostActivity.this, data);
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
    }

    @Override
    public Intent getSupportParentActivityIntent() {
        Intent parentIntent = getIntent();
        String className = parentIntent.getStringExtra("ParentClassName"); //getting the parent class name
        Intent newIntent = null;
        try {
            //you need to define the class with package name
            newIntent = new Intent(MyPostActivity.this, Class.forName(AppConstant.PACKAGE + className));

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return newIntent;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.edit) {

            if (!canDelete) {
                getAdapter().canDelete(true);
                canDelete = true;
            } else {
                getAdapter().canDelete(false);
                canDelete = false;
            }

            getAdapter().notifyDataSetChanged();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
