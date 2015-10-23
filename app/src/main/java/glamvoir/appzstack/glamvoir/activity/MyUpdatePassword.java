package glamvoir.appzstack.glamvoir.activity;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import glamvoir.appzstack.glamvoir.R;
import glamvoir.appzstack.glamvoir.asynctaskloader.LoaderID;
import glamvoir.appzstack.glamvoir.asynctaskloader.PasswordLoader;
import glamvoir.appzstack.glamvoir.constant.AppConstant;
import glamvoir.appzstack.glamvoir.customview.CustomTextBold;
import glamvoir.appzstack.glamvoir.helpers.Utility;
import glamvoir.appzstack.glamvoir.helpers.Validation;
import glamvoir.appzstack.glamvoir.model.TaskResponse;
import glamvoir.appzstack.glamvoir.model.net.request.RequestBean;
import glamvoir.appzstack.glamvoir.model.net.response.PasswordResponse;

/**
 * Created by jai on 10/18/2015.
 */
public class MyUpdatePassword extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private EditText valid_gmail;
    private TextInputLayout tl_Email;
    private EditText  oldpassword, new_password;
    private Button bt_changepassword;
    private TextInputLayout tl_newpassword, tl_oldpassword;
    private RequestBean mRequestBean;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, MyUpdatePassword.class);
        intent.putExtra("ParentClassName", context.getClass().getSimpleName());
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);

        mRequestBean = new RequestBean();
        mRequestBean.setLoader(true);
        mRequestBean.setActivity(this);
        mRequestBean.setLoader(true);

        //initialize all views
        initViews();

        initListener();

        getToolbar(toolbar);

        oldpassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (Validation.isValidPassword(s.toString())) {
                    tl_oldpassword.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        new_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (Validation.isValidPassword(s.toString())) {
                    tl_newpassword.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

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

    private void getToolbar(Toolbar toolbar) {

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SpannableString s = new SpannableString("UPDATE PASSWORD");
        s.setSpan(new CustomTextBold(this), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        getSupportActionBar().setTitle(s);

    }

    private void initListener() {
        bt_changepassword.setOnClickListener(this);



    }

    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        oldpassword = (EditText) findViewById(R.id.oldpassword);
        new_password = (EditText) findViewById(R.id.new_password);
        valid_gmail = (EditText) findViewById(R.id.gmail);
        tl_Email = (TextInputLayout) findViewById(R.id.inputgmail);
        tl_oldpassword = (TextInputLayout) findViewById(R.id.inputoldpassword);
        tl_newpassword = (TextInputLayout) findViewById(R.id.inputnew_password);

        bt_changepassword = (Button) findViewById(R.id.bt_changepassword);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.bt_changepassword:
                if (Validation.isValidEmail(valid_gmail.getText().toString())) {

                        if (Validation.isValidPassword(oldpassword.getText().toString())) {
                            //call signup
                            if (Validation.isValidPassword(new_password.getText().toString())){
                            if (!oldpassword.getText().toString().equals(new_password.getText().toString())) {
                                callPasswordLoader();
                                Utility.hideKeyboard(this, new_password);

                            } else {
                                tl_newpassword.setError(getResources().getString(R.string.password_same));
                            }
                        }
                            else {
                                tl_newpassword.setError(getResources().getString(R.string.password_short));
                            }
                        }
                        else {
                            tl_oldpassword.setError(getResources().getString(R.string.password_short));
                        }

                } else {
                    tl_Email.setError(getResources().getString(R.string.invalid_email));
                }

                break;

            default:
                break;

        }
    }

    private void callPasswordLoader() {
        getLoaderManager().restartLoader(LoaderID.UPDATE_PASSWORD, null, passwordCallback);
    }

    LoaderManager.LoaderCallbacks<TaskResponse<PasswordResponse>> passwordCallback =
            new LoaderManager.LoaderCallbacks<TaskResponse<PasswordResponse>>() {

                @Override
                public Loader<TaskResponse<PasswordResponse>> onCreateLoader(int id, Bundle args) {
                    // loadIndicator.setVisibility(View.VISIBLE);

                    return new PasswordLoader(mRequestBean, AppConstant.METHOD_RESET_PASSWORD, valid_gmail.getText().toString(), oldpassword.getText().toString(),new_password.getText().toString());
                }

                @Override
                public void onLoadFinished(Loader<TaskResponse<PasswordResponse>> loader, TaskResponse<PasswordResponse> data) {
                    if (loader instanceof PasswordLoader) {
                        //  loadIndicator.setVisibility(View.GONE);
                        if (data.error != null) {
                            Utility.showToast(mRequestBean.getContext(), data.error.toString());
                        } else {
                            if (data.data != null && data.data.error_code != null) {

                                Utility.showToast(mRequestBean.getContext(), "success");
                                finish();

                            }
                        }
                    }
                }

                @Override
                public void onLoaderReset(Loader<TaskResponse<PasswordResponse>> loader) {
                }
            };

    @Override
    public Intent getSupportParentActivityIntent() {
        Intent parentIntent = getIntent();
        String className = parentIntent.getStringExtra("ParentClassName"); //getting the parent class name
        Intent newIntent = null;
        try {
            //you need to define the class with package name
            newIntent = new Intent(MyUpdatePassword.this, Class.forName(AppConstant.PACKAGE + className));
            newIntent.putExtra("ParentClassName", "HomeActivity");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return newIntent;
    }
}
