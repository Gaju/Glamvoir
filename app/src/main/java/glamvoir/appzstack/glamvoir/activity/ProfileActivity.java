package glamvoir.appzstack.glamvoir.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import glamvoir.appzstack.glamvoir.R;
import glamvoir.appzstack.glamvoir.adapter.Custome_All_ListAdapter;
import glamvoir.appzstack.glamvoir.constant.AppConstant;

/**
 * Created by jaim on 9/9/2015.
 */
public class ProfileActivity extends AppCompatActivity {

    private Toolbar toolbar;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, ProfileActivity.class);
        intent.putExtra("ParentClassName", context.getClass().getSimpleName());
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //
        setContentView(R.layout.layout_profile);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        getToolbar(toolbar);

    }
    private void getToolbar(Toolbar toolbar) {

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("USER PROFILE");
    }
    @Override
    public Intent getSupportParentActivityIntent() {
        Intent parentIntent = getIntent();
        String className = parentIntent.getStringExtra("ParentClassName"); //getting the parent class name
        Intent newIntent = null;
        try {
            //you need to define the class with package name
            newIntent = new Intent(ProfileActivity.this, Class.forName(AppConstant.PACKAGE + className));

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return newIntent;
    }

}
