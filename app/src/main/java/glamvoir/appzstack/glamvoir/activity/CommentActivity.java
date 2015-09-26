package glamvoir.appzstack.glamvoir.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import glamvoir.appzstack.glamvoir.R;
import glamvoir.appzstack.glamvoir.adapter.CommentCustomAdapter;
import glamvoir.appzstack.glamvoir.constant.AppConstant;

/**
 * Created by jai on 9/26/2015.
 */
public class CommentActivity extends AppCompatActivity{

    private ListView mlistView;
    protected Toolbar toolbar;


  public static void startActvity(Context context) {
      Intent intent = new Intent(context, CommentActivity.class);
      //intent.putExtra("ParentClassName", "All");
      context.startActivity(intent);
    }

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_comment);
        iniview();
        getToolbar(toolbar);

         mlistView.setAdapter(new CommentCustomAdapter(CommentActivity.this));


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

        mlistView= (ListView) findViewById(R.id.listView);
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

}
