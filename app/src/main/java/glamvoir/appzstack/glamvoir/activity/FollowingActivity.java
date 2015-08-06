package glamvoir.appzstack.glamvoir.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import glamvoir.appzstack.glamvoir.R;
import glamvoir.appzstack.glamvoir.constant.AppConstant;
import glamvoir.appzstack.glamvoir.model.net.request.RequestBean;

/**
 * Created by gajendran on 5/8/15.
 */
public class FollowingActivity extends AppCompatActivity {

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, FollowingActivity.class);
        intent.putExtra("ParentClassName", context.getClass().getSimpleName());
        context.startActivity(intent);
    }

    private RequestBean mRequestBean;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_following);

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
        getSupportActionBar().setTitle(getResources().getString(R.string.following));
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
            newIntent = new Intent(FollowingActivity.this, Class.forName(AppConstant.PACKAGE + className));

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return newIntent;
    }
}
