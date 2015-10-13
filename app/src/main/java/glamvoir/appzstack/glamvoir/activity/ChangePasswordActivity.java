package glamvoir.appzstack.glamvoir.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import glamvoir.appzstack.glamvoir.R;
import glamvoir.appzstack.glamvoir.constant.AppConstant;
import glamvoir.appzstack.glamvoir.helpers.Utility;
import glamvoir.appzstack.glamvoir.helpers.Validation;

/**
 * Created by jaim on 10/12/2015.
 */
public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private EditText gmail_otp,password,veri_password;
    private Button bt_changepassword,bt_requestTochange;
    private TextInputLayout tl_fpassword, tl_verifypassword,tl_gmialOtp;


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

        gmail_otp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (Validation.isGmailOTP(s.toString())) {
                    tl_gmialOtp.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (Validation.isValidPassword(s.toString())) {
                    tl_fpassword.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        veri_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (Validation.isValidPassword(s.toString())) {
                    tl_verifypassword.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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


        tl_gmialOtp = (TextInputLayout) findViewById(R.id.inputgmail_otp);
        tl_fpassword = (TextInputLayout) findViewById(R.id.inputpassword);
        tl_verifypassword = (TextInputLayout) findViewById(R.id.inputveri_password);

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

        switch (view.getId()){

            case R.id.bt_changepassword:

                if (Validation.isGmailOTP(gmail_otp.getText().toString())) {
                    if (Validation.isValidPassword(password.getText().toString())) {
                        //call signup
                        if (password.getText().toString().equals(veri_password.getText().toString())) {
                            resetPassword();
                            Utility.hideKeyboard(this, password);
                        }
                        else {
                            tl_verifypassword.setError(getResources().getString(R.string.password_mismatch));
                        }
                    } else {
                        tl_fpassword.setError(getResources().getString(R.string.password_short));
                    }
                }
                else {
                    tl_gmialOtp.setError(getResources().getString(R.string.gmail_otp));
                }

                break;

            case  R.id.bt_requestTochange:

                RequestToChangePasswordActivity.startActivity(ChangePasswordActivity.this);


                break;

            default:
                break;

        }



    }

    private void resetPassword() {

    }


}
