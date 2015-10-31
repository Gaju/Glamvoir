package glamvoir.appzstack.glamvoir.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import glamvoir.appzstack.glamvoir.R;
import glamvoir.appzstack.glamvoir.constant.AppConstant;

/**
 * Created by jaim on 9/14/2015.
 */
public class Contact_Us  extends AppCompatActivity implements View.OnClickListener {
    protected Toolbar toolbar;
    Context context;
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, Contact_Us.class);
        intent.putExtra("ParentClassName", context.getClass().getSimpleName());
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_contact_us); //

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        getToolbar(toolbar);
        TextView t2 = (TextView) findViewById(R.id.textView22);
        t2.setOnClickListener(this);



    }

    private void getToolbar(Toolbar toolbar) {

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("CONTACT US");
    }

    @Override
    public Intent getSupportParentActivityIntent() {
        Intent parentIntent = getIntent();
        String className = parentIntent.getStringExtra("ParentClassName"); //getting the parent class name
        Intent newIntent = null;
        try {
            //you need to define the class with package name
            newIntent = new Intent(Contact_Us.this, Class.forName(AppConstant.PACKAGE + className));
            newIntent.putExtra("ParentClassName", "HomeActivity");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return newIntent;
    }

    @Override
    public void onClick(View v) {

        Intent testIntent = new Intent(Intent.ACTION_VIEW);
        Uri data = Uri.parse("mailto:?subject=" + " " + "&body=" + "" + "&to=" + "glamvoir@gmail.com");
        testIntent.setData(data);
        startActivity(testIntent);

    }
}
