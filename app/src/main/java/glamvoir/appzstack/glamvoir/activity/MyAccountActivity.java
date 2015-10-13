package glamvoir.appzstack.glamvoir.activity;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Fade;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.Calendar;

import glamvoir.appzstack.glamvoir.R;
import glamvoir.appzstack.glamvoir.apppreference.AppPreferences;
import glamvoir.appzstack.glamvoir.constant.AppConstant;
import glamvoir.appzstack.glamvoir.helpers.Validation;
import glamvoir.appzstack.glamvoir.model.net.request.RequestBean;

/**
 * Created by acer pc on 09-08-2015.
 */
public class MyAccountActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edt_fName, edt_Phone, edt_Email, edt_Password,myaccount_lastname,edt_age;
    private TextInputLayout tl_fName, tl_Phone, tl_Email, tl_Password;
    RadioButton rdbMale, rdbFemale;
    RadioGroup rgGender;
    private Calendar cal;
    private int day;
    private int month;
    private int year;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, MyAccountActivity.class);
        intent.putExtra("ParentClassName", context.getClass().getSimpleName());
        context.startActivity(intent);
    }

    private RequestBean mRequestBean;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_myaccount);

        mRequestBean = new RequestBean();
        mRequestBean.setLoader(true);
        mRequestBean.setActivity(this);
        mRequestBean.setLoader(true);

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

        edt_Phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!Validation.isValidMobile(s.toString())) {
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
    }

    private void updateUI(){

        edt_Email.setFocusable(false);
        edt_Email.setClickable(true);

        AppPreferences preferences = new AppPreferences(MyAccountActivity.this);


        edt_fName.setText(preferences.getFirstName() + " " + preferences.getLastName());


        edt_Email.setText(preferences.getEmailID());

    }


    /**
     * initialize all views
     */
    private void initViews() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        edt_fName = (EditText) findViewById(R.id.myaccount_name);
        edt_Phone = (EditText) findViewById(R.id.myaccount_phone);
        edt_Email = (EditText) findViewById(R.id.myaccount_mail);
        edt_age= (EditText) findViewById(R.id.myaccount_age);
        myaccount_lastname = (EditText) findViewById(R.id.myaccount_lastname);
        tl_fName = (TextInputLayout) findViewById(R.id.myaccount_inputname);
        tl_Phone = (TextInputLayout) findViewById(R.id.myaccount_inputphone);
        tl_Email = (TextInputLayout) findViewById(R.id.myaccount_inputmail);
        rgGender = (RadioGroup) findViewById(R.id.rgGender);
        rdbMale = (RadioButton) findViewById(R.id.rdbMale);
        rdbFemale = (RadioButton) findViewById(R.id.rdbMale);
    }

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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.myaccount_age:
                // Launch Date Picker Dialog
                DatePickerDialog dpd = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // Display Selected date in textbox
                                edt_age.setText( year + "-"+(monthOfYear + 1)+ "-"+dayOfMonth);
                               // 2008-07-01"
                            }
                        }, year, month, day);
                dpd.show();

                break;
        }
    }
}
