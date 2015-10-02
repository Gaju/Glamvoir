package glamvoir.appzstack.glamvoir.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

import glamvoir.appzstack.glamvoir.R;
import glamvoir.appzstack.glamvoir.adapter.FFSP_Adapter;
import glamvoir.appzstack.glamvoir.adapter.LoadableListAdapter;
import glamvoir.appzstack.glamvoir.constant.AppConstant;
import glamvoir.appzstack.glamvoir.model.net.request.RequestBean;

/**
 * Created by gajendran on 5/8/15.
 */
public class FollowersActivity extends FFSPActivity {

    private RequestBean mRequestBean;
    private Toolbar toolbar;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, FollowersActivity.class);
        intent.putExtra("ParentClassName", context.getClass().getSimpleName());
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRequestBean = new RequestBean();
        mRequestBean.setLoader(true);
        mRequestBean.setActivity(this);
        mRequestBean.setLoader(true);
    }

    @Override
    protected String getMethodName() {
        return AppConstant.METHOD_FOLLOWING;
    }

    @Override
    protected RequestBean getRequestBean() {
        return mRequestBean;
    }

    @Override
    protected String getAppBarTitle() {

        return getResources().getString(R.string.followers);
    }

    @Override
    protected LoadableListAdapter createAdapter(ArrayList data) {
        return new FFSP_Adapter(FollowersActivity.this, data);
    }

    @Override
    public Intent getSupportParentActivityIntent() {
        Intent parentIntent = getIntent();
        String className = parentIntent.getStringExtra("ParentClassName"); //getting the parent class name
        Intent newIntent = null;
        try {
            //you need to define the class with package name
            newIntent = new Intent(FollowersActivity.this, Class.forName(AppConstant.PACKAGE + className));

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return newIntent;
    }
}
