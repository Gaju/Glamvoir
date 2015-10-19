package glamvoir.appzstack.glamvoir.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import glamvoir.appzstack.glamvoir.R;
import glamvoir.appzstack.glamvoir.constant.AppConstant;

/**
 * Created by jai on 10/18/2015.
 */
public class LikeListActivity extends AppCompatActivity {

    protected Toolbar toolbar;
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, LikeListActivity.class);
        intent.putExtra("ParentClassName", context.getClass().getSimpleName());
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_likes_list);
        iniview();
        getToolbar(toolbar);
    }

    private void iniview() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
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
        getSupportActionBar().setTitle("LIKE");
    }

    @Override
    public Intent getSupportParentActivityIntent() {
        Intent newIntent = null;
        finish();
        return newIntent;
    }
}
