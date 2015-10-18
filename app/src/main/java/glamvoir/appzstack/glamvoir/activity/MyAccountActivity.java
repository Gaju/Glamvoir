package glamvoir.appzstack.glamvoir.activity;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Fade;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import glamvoir.appzstack.glamvoir.R;
import glamvoir.appzstack.glamvoir.apppreference.AppPreferences;
import glamvoir.appzstack.glamvoir.asynctaskloader.LoaderID;
import glamvoir.appzstack.glamvoir.asynctaskloader.ProfileLoader;
import glamvoir.appzstack.glamvoir.constant.AppConstant;
import glamvoir.appzstack.glamvoir.helpers.Utility;
import glamvoir.appzstack.glamvoir.helpers.Validation;
import glamvoir.appzstack.glamvoir.model.TaskResponse;
import glamvoir.appzstack.glamvoir.model.net.request.RequestBean;
import glamvoir.appzstack.glamvoir.model.net.response.ProfileResponse;

/**
 * Created by acer pc on 09-08-2015.
 */
public class MyAccountActivity extends AppCompatActivity implements View.OnClickListener {

    private int day;
    private int month;
    private int year;
    private EditText edt_fName, edt_age, edt_Phone, myaccount_lastname, edt_Email, edt_lName, edt_AboutMe, edt_City, edt_Contact;
    private TextInputLayout tl_fName, tl_Phone, tl_Email, tl_lName, tl_AboutMe, tl_City, tl_Contact;
    private RadioButton rdbMale, rdbFemale;
    private RadioGroup rgGender;
    private Button btn_Done;
    protected View loadIndicator;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, MyAccountActivity.class);
        intent.putExtra("ParentClassName", context.getClass().getSimpleName());
        context.startActivity(intent);
    }

    private RequestBean mRequestBean;
    private Toolbar toolbar;
    AppPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_myaccount);

        mRequestBean = new RequestBean();
        mRequestBean.setLoader(true);
        mRequestBean.setActivity(this);
        mRequestBean.setLoader(true);

        preferences = new AppPreferences(MyAccountActivity.this);

        //initialize all views
        initViews();

        initListener();

        getToolbar(toolbar);

        setupWindowAnimations();

        updateUI();

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

        edt_Phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!Validation.isValidMobile(s.toString())) {
                    tl_Phone.setErrorEnabled(true);
                }else{
                    tl_Phone.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupWindowAnimations() {
        Fade fade = new Fade();
        fade.setDuration(1000);
        getWindow().setEnterTransition(fade);
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
        getSupportActionBar().setTitle(getResources().getString(R.string.myaccount));
    }

    /**
     * initialize all views listeners
     */
    private void initListener() {
        edt_age.setOnClickListener(this);

        btn_Done.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.done:
                if (Validation.isValidName(edt_fName.getText().toString())) {
                    if (Validation.isValidPassword(edt_lName.getText().toString())) {
                        if (Validation.isValidMobile(edt_Contact.getText().toString())) {
                            update();
                            Utility.hideKeyboard(this, edt_lName);
                        } else {
                            tl_Contact.setError(getResources().getString(R.string.invalid_number));
                        }
                    } else {
                        tl_lName.setError(getResources().getString(R.string.invalid_lname));
                    }

                } else {
                    tl_fName.setError(getResources().getString(R.string.invalid_fname));
                }
                break;

            case R.id.myaccount_age:
                // Launch Date Picker Dialog
                DatePickerDialog dpd = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // Display Selected date in textbox
                                edt_age.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                                // 2008-07-01"
                            }
                        }, year, month, day);
                dpd.show();

                break;
            default:
                break;
        }
    }

    private void updateUI() {
        edt_Email.setFocusable(false);
        edt_Email.setClickable(true);

        edt_fName.setText(preferences.getFirstName());

        edt_lName.setText(preferences.getLastName());

        edt_Email.setText(preferences.getEmailID());

        if (preferences.getUserAbout() != null) {
            edt_AboutMe.setText(preferences.getUserAbout());
        }

        if (preferences.getUserCity() != null) {
            edt_City.setText(preferences.getUserCity());
        }

        if (preferences.getUserContact() != null) {
            edt_Contact.setText(preferences.getUserContact());
        }

        if (preferences.getGender() != null) {
            if (preferences.getGender().equals("1")) {
                rdbMale.setChecked(true);
                rdbFemale.setChecked(false);
            } else {
                rdbMale.setChecked(false);
                rdbFemale.setChecked(true);
            }
        }
    }


    /**
     * initialize all views
     */
    private void initViews() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        edt_fName = (EditText) findViewById(R.id.myaccount_name);
        edt_Phone = (EditText) findViewById(R.id.myaccount_phone);
        edt_Email = (EditText) findViewById(R.id.myaccount_mail);
        edt_age = (EditText) findViewById(R.id.myaccount_age);
        myaccount_lastname = (EditText) findViewById(R.id.myaccount_lastname);
        edt_lName = (EditText) findViewById(R.id.myaccount_lastname);
        edt_AboutMe = (EditText) findViewById(R.id.myaccount_abtme);
        edt_City = (EditText) findViewById(R.id.myaccount_loction);
        edt_Contact = (EditText) findViewById(R.id.myaccount_phone);

        tl_fName = (TextInputLayout) findViewById(R.id.myaccount_inputname);
        tl_Phone = (TextInputLayout) findViewById(R.id.myaccount_inputphone);
        tl_Email = (TextInputLayout) findViewById(R.id.myaccount_inputmail);
        tl_lName = (TextInputLayout) findViewById(R.id.myaccount_inputlastname);
        tl_AboutMe = (TextInputLayout) findViewById(R.id.myaccount_inputabtme);
        tl_City = (TextInputLayout) findViewById(R.id.myaccount_inputlocation);
        tl_Contact = (TextInputLayout) findViewById(R.id.myaccount_inputphone);

        rgGender = (RadioGroup) findViewById(R.id.rgGender);
        rdbMale = (RadioButton) findViewById(R.id.rdbMale);
        rdbFemale = (RadioButton) findViewById(R.id.rdbFemale);

        btn_Done = (Button) findViewById(R.id.done);
        loadIndicator = findViewById(R.id.loadIndicator);
    }

    private void update() {
        getLoaderManager().restartLoader(LoaderID.GETPROFILE, null, updateProfileCallback);
    }


    LoaderManager.LoaderCallbacks<TaskResponse<ProfileResponse>> updateProfileCallback =
            new LoaderManager.LoaderCallbacks<TaskResponse<ProfileResponse>>() {

                @Override
                public Loader<TaskResponse<ProfileResponse>> onCreateLoader(int id, Bundle args) {
                    loadIndicator.setVisibility(View.VISIBLE);
                    //user_fname, user_lname,user_dob, user_gender, user_about, user_contact, user_city

                    return new ProfileLoader(mRequestBean, AppConstant.METHOD_UPDATEPROFILE, preferences.getUserId(), edt_fName.getText().toString(), edt_lName.getText().toString(), edt_age.getText().toString(), AppPreferences.getInstance(mRequestBean.getContext()).getGender(), edt_AboutMe.getText().toString(), edt_Contact.getText().toString(), edt_City.getText().toString());
                }

                @Override
                public void onLoadFinished(Loader<TaskResponse<ProfileResponse>> loader, TaskResponse<ProfileResponse> data) {
                    if (loader instanceof ProfileLoader) {
                        loadIndicator.setVisibility(View.GONE);
                        if (data.error != null) {
                            Utility.showToast(mRequestBean.getContext(), data.error.toString());
                        } else {
                            if (data.data != null && data.data.error_code != null) {
                                Utility.updateUserData(mRequestBean.getContext(), data.data.results);
                                updateUI();
                            }
                        }
                    }
                }

                @Override
                public void onLoaderReset(Loader<TaskResponse<ProfileResponse>> loader) {
                }
            };

    @Override
    public Intent getSupportParentActivityIntent() {
        Intent parentIntent = getIntent();
        String className = parentIntent.getStringExtra("ParentClassName"); //getting the parent class name
        Intent newIntent = null;
        try {
            //you need to define the class with package name
            newIntent = new Intent(MyAccountActivity.this, Class.forName(AppConstant.PACKAGE + className));
            newIntent.putExtra("ParentClassName", "HomeActivity");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return newIntent;
    }
}
