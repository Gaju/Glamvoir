package glamvoir.appzstack.glamvoir.activity;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.squareup.okhttp.internal.Util;

import glamvoir.appzstack.glamvoir.R;
import glamvoir.appzstack.glamvoir.apppreference.AppPreferences;
import glamvoir.appzstack.glamvoir.asynctaskloader.LoaderID;
import glamvoir.appzstack.glamvoir.asynctaskloader.LoginLoader;
import glamvoir.appzstack.glamvoir.constant.AppConstant;
import glamvoir.appzstack.glamvoir.helpers.Utility;
import glamvoir.appzstack.glamvoir.helpers.Validation;
import glamvoir.appzstack.glamvoir.model.TaskResponse;
import glamvoir.appzstack.glamvoir.model.net.request.RequestBean;
import glamvoir.appzstack.glamvoir.model.net.response.LoginResponse;


/**
 * Created by jaim on 7/21/2015.
 */
public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edt_fName, edt_lName, edt_Email, edt_Password;
    private TextInputLayout tl_fName, tl_lName, tl_Email, tl_Password;
    private Button btn_Signup;
    private RequestBean mRequestBean;
    private Toolbar toolbar;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, SignUpActivity.class);
        intent.putExtra("ParentClassName", context.getClass().getSimpleName());
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_page);

        mRequestBean = new RequestBean();
        mRequestBean.setLoader(true);
        mRequestBean.setActivity(this);
        mRequestBean.setLoader(true);

        //initialize all views
        initViews();

        initListener();

        getToolbar(toolbar);

        edt_fName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (Validation.isValidName(s.toString())) {
                    tl_fName.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edt_lName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (Validation.isValidName(s.toString())) {
                    tl_lName.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        edt_Password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (Validation.isValidPassword(s.toString())) {
                    tl_Password.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        edt_Email.addTextChangedListener(new TextWatcher() {
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


    /**
     * customize the toolbar
     *
     * @param toolbar : pass the toolbar reference
     */
    private void getToolbar(Toolbar toolbar) {

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.signup));
    }

    /**
     * initialize all views listeners
     */
    private void initListener() {
        btn_Signup.setOnClickListener(this);
    }


    /**
     * initialize all views
     */
    private void initViews() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        btn_Signup = (Button) findViewById(R.id.join_glamvoir);
        edt_fName = (EditText) findViewById(R.id.firstname);
        edt_lName = (EditText) findViewById(R.id.lastname);
        edt_Email = (EditText) findViewById(R.id.email);
        edt_Password = (EditText) findViewById(R.id.password);

        tl_fName = (TextInputLayout) findViewById(R.id.inputfirstname);
        tl_lName = (TextInputLayout) findViewById(R.id.inputlastname);
        tl_Email = (TextInputLayout) findViewById(R.id.inputemail);
        tl_Password = (TextInputLayout) findViewById(R.id.inputpassword);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.join_glamvoir:
                if (Validation.isValidName(edt_fName.getText().toString())) {
                    if (Validation.isValidName(edt_lName.getText().toString())) {
                        if (Validation.isValidEmail(edt_Email.getText().toString())) {
                            if (Validation.isValidPassword(edt_Password.getText().toString())) {
                                //call signup
                                joinGlamvoir();
                                Utility.hideKeyboard(this,edt_Password);
                            } else {
                                tl_Password.setError(getResources().getString(R.string.password_short));
                            }
                        } else {
                            tl_Email.setError(getResources().getString(R.string.invalid_email));
                        }
                    } else {
                        tl_lName.setError(getResources().getString(R.string.invalid_lname));
                    }
                } else {
                    tl_fName.setError(getResources().getString(R.string.invalid_fname));
                }
                break;

            default:
                break;

        }
    }

    private void joinGlamvoir() {
        getLoaderManager().restartLoader(LoaderID.LOGIN, null, loginCallback);
    }


    LoaderManager.LoaderCallbacks<TaskResponse<LoginResponse>> loginCallback =
            new LoaderManager.LoaderCallbacks<TaskResponse<LoginResponse>>() {

                @Override
                public Loader<TaskResponse<LoginResponse>> onCreateLoader(int id, Bundle args) {
                    return new LoginLoader(mRequestBean, edt_Email.getText().toString(), edt_Password.getText().toString(), edt_fName.getText().toString(), AppConstant.getDeviceToken(mRequestBean.getContext()), AppConstant.getDeviceType(), LoginLoader.LOGIN_SIGNUP);
                }

                @Override
                public void onLoadFinished(Loader<TaskResponse<LoginResponse>> loader, TaskResponse<LoginResponse> data) {

                    if(data.error!=null){

                    }else{
                        if(data.data!=null){
                            AppPreferences mAppPreferences=new AppPreferences(SignUpActivity.this);


                        }
                    }

                }

                @Override
                public void onLoaderReset(Loader<TaskResponse<LoginResponse>> loader) {
                }
            };

    @Override
    public Intent getSupportParentActivityIntent() {
        Intent parentIntent = getIntent();
        String className = parentIntent.getStringExtra("ParentClassName"); //getting the parent class name
        Intent newIntent = null;
        try {
            //you need to define the class with package name
            newIntent = new Intent(SignUpActivity.this, Class.forName(AppConstant.PACKAGE + className));

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return newIntent;
    }
}
