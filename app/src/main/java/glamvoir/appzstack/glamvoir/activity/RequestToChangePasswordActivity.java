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
import glamvoir.appzstack.glamvoir.model.net.request.RequestBean;

/**
 * Created by jaim on 10/12/2015.
 */
public class RequestToChangePasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private EditText valid_gmail;
    private Button bt_requestTochange;
    private TextInputLayout tl_Email;
    private RequestBean mRequestBean;


    public static void startActivity(Context context) {
        Intent intent = new Intent(context, RequestToChangePasswordActivity.class);
        intent.putExtra("ParentClassName", context.getClass().getSimpleName());
        context.startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_change_password);
        mRequestBean = new RequestBean();
        mRequestBean.setActivity(this);
        mRequestBean.setLoader(true);

        initViews();

        initListener();

        getToolbar(toolbar);

        valid_gmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (Validation.isValidEmail(s.toString())) {
                    tl_Email.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

}

    private void initListener() {

        bt_requestTochange.setOnClickListener(this);


    }

    private void initViews() {



        toolbar = (Toolbar) findViewById(R.id.toolbar);
        valid_gmail= (EditText) findViewById(R.id.gmail);
        tl_Email = (TextInputLayout) findViewById(R.id.inputgmail);
        bt_requestTochange= (Button) findViewById(R.id.bt_send_request_email);

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
        getSupportActionBar().setTitle("SEND REQUEST");
    }

    @Override
    public Intent getSupportParentActivityIntent() {
        Intent parentIntent = getIntent();
        String className = parentIntent.getStringExtra("ParentClassName"); //getting the parent class name
        Intent newIntent = null;
        try {
            //you need to define the class with package name
            newIntent = new Intent(RequestToChangePasswordActivity.this, Class.forName(AppConstant.PACKAGE + className));
            newIntent.putExtra("ParentClassName", "HomeActivity");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return newIntent;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.bt_send_request_email:

                if (Validation.isValidEmail(valid_gmail.getText().toString())) {

                        requestToChangePassword();
                        Utility.hideKeyboard(this, valid_gmail);

                } else {
                    tl_Email.setError(getResources().getString(R.string.invalid_email));
                }

                break;


            default:
                break;

        }



    }

    private void requestToChangePassword() {

    }


}
