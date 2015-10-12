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
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
 * Created by jaim on 7/22/2015.
 */
public class SigninActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edt_Email, edt_Password;
    private TextInputLayout tl_Email, tl_Password;
    private RequestBean mRequestBean;
    private Toolbar toolbar;
    private Button btn_SignIn;
    private TextView change_password;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, SigninActivity.class);
        intent.putExtra("ParentClassName", context.getClass().getSimpleName());
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_page);

        mRequestBean = new RequestBean();
        mRequestBean.setLoader(true);
        mRequestBean.setActivity(this);
        mRequestBean.setLoader(true);

        //initialize all views
        initViews();

        initListener();

        getToolbar(toolbar);

        edt_Password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!Validation.isTextEmpty(s.toString())) {
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.singnin:
                //HomeActivity.startActivityWithClearTop(SigninActivity.this);
                if (Validation.isValidEmail(edt_Email.getText().toString())) {
                    if (Validation.isValidPassword(edt_Password.getText().toString())) {
                        login();
                        Utility.hideKeyboard(this, edt_Password);

                    } else {
                        tl_Password.setError(getResources().getString(R.string.password_short));
                    }
                } else {
                    tl_Email.setError(getResources().getString(R.string.invalid_email));
                }

                break;

            case R.id.change_password:

                ChangePasswordActivity.startActivity(SigninActivity.this);

                break;
        }
    }

    private void login() {
        getLoaderManager().restartLoader(LoaderID.LOGIN, null, loginCallback);
    }

    LoaderManager.LoaderCallbacks<TaskResponse<LoginResponse>> loginCallback =
            new LoaderManager.LoaderCallbacks<TaskResponse<LoginResponse>>() {

                @Override
                public Loader<TaskResponse<LoginResponse>> onCreateLoader(int id, Bundle args) {
                    AppPreferences appPreferences = new AppPreferences(mRequestBean.getContext());
                    return new LoginLoader(mRequestBean, edt_Email.getText().toString(), edt_Password.getText().toString(),null,null, appPreferences.getDeviceToken(), appPreferences.getDeviceType(), appPreferences.getDeviceType(), LoginLoader.LOGIN_GLAMVOIR);
                }

                @Override
                public void onLoadFinished(Loader<TaskResponse<LoginResponse>> loader, TaskResponse<LoginResponse> data) {
                    if (loader instanceof LoginLoader) {
                        ((LoginLoader) loader).hideLoaderDialog();
                        if (data.error != null) {
                            Utility.showToast(SigninActivity.this, data.error.toString());
                        } else {
                            if (data.data != null && data.data.error_code != null) {
                                int errorCode = Integer.parseInt(data.data.error_code);
                                switch (errorCode) {
                                    case 0:
                                        Utility.showToast(SigninActivity.this, data.data.msg_string);
                                        Utility.saveUserData(mRequestBean.getContext(), data.data.user);
                                        HomeActivity.startActivityWithClearTop(SigninActivity.this);
                                        break;
                                    case 1:
                                        Utility.showToast(SigninActivity.this, data.data.msg_string);
                                        break;
                                    case 2:
                                        Utility.showToast(SigninActivity.this, data.data.msg_string);
                                        break;
                                }
                            }
                        }
                    }
                }

                @Override
                public void onLoaderReset(Loader<TaskResponse<LoginResponse>> loader) {
                }
            };

    /**
     * customize the toolbar
     *
     * @param toolbar : pass the toolbar reference
     */
    private void getToolbar(Toolbar toolbar) {

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.signin));
    }

    /**
     * initialize all views listeners
     */
    private void initListener() {
        btn_SignIn.setOnClickListener(this);
        change_password.setOnClickListener(this);
    }


    /**
     * initialize all views
     */
    private void initViews() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        btn_SignIn = (Button) findViewById(R.id.singnin);
        edt_Email = (EditText) findViewById(R.id.signin_email);
        edt_Password = (EditText) findViewById(R.id.signin_pwd);
        tl_Email = (TextInputLayout) findViewById(R.id.signin_inputemail);
        tl_Password = (TextInputLayout) findViewById(R.id.signin_inputpwd);
        change_password= (TextView) findViewById(R.id.change_password);

    }

    @Override
    public Intent getSupportParentActivityIntent() {
        Intent parentIntent = getIntent();
        String className = parentIntent.getStringExtra("ParentClassName"); //getting the parent class name
        Intent newIntent = null;
        try {
            //you need to define the class with package name
            newIntent = new Intent(SigninActivity.this, Class.forName(AppConstant.PACKAGE + className));

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return newIntent;
    }
}
