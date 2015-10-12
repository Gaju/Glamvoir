package glamvoir.appzstack.glamvoir.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import glamvoir.appzstack.glamvoir.R;
import glamvoir.appzstack.glamvoir.constant.AppConstant;

/**
 * Created by jaim on 10/12/2015.
 */
public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private EditText gmail_otp,password,veri_password;
    private Button bt_changepassword,bt_requestTochange;


    public static void startActivity(Context context) {
        Intent intent = new Intent(context, ChangePasswordActivity.class);
        intent.putExtra("ParentClassName", context.getClass().getSimpleName());
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changed_password);
        //initialize all views
        initViews();

        initListener();

        getToolbar(toolbar);
    }

    private void initListener() {
        bt_changepassword.setOnClickListener(this);
        bt_requestTochange.setOnClickListener(this);


    }

    private void initViews() {



        toolbar = (Toolbar) findViewById(R.id.toolbar);
        gmail_otp= (EditText) findViewById(R.id.gmail_otp);
        password= (EditText) findViewById(R.id.password);
        veri_password= (EditText) findViewById(R.id.veri_password);
        bt_changepassword= (Button) findViewById(R.id.bt_changepassword);
        bt_requestTochange= (Button) findViewById(R.id.bt_requestTochange);

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
        getSupportActionBar().setTitle("CHANGE PASSWORD");
    }

    @Override
    public Intent getSupportParentActivityIntent() {
        Intent parentIntent = getIntent();
        String className = parentIntent.getStringExtra("ParentClassName"); //getting the parent class name
        Intent newIntent = null;
        try {
            //you need to define the class with package name
            newIntent = new Intent(ChangePasswordActivity.this, Class.forName(AppConstant.PACKAGE + className));
            newIntent.putExtra("ParentClassName", "HomeActivity");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return newIntent;
    }

    @Override
    public void onClick(View view) {


       // bt_changepassword.setOnClickListener(this);
        //bt_requestTochange.setOnClickListener(this);
        switch (view.getId()){

            case R.id.bt_changepassword:

                break;

            case  R.id.bt_requestTochange:

                RequestToChangePasswordActivity.startActivity(ChangePasswordActivity.this);


                break;

            default:
                break;

        }



    }


}
