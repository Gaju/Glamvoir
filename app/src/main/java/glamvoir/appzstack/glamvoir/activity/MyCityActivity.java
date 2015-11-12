package glamvoir.appzstack.glamvoir.activity;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.List;

import glamvoir.appzstack.glamvoir.R;
import glamvoir.appzstack.glamvoir.apppreference.AppPreferences;
import glamvoir.appzstack.glamvoir.asynctaskloader.CityLoader;
import glamvoir.appzstack.glamvoir.asynctaskloader.LoaderID;
import glamvoir.appzstack.glamvoir.asynctaskloader.ProfileLoader;
import glamvoir.appzstack.glamvoir.constant.AppConstant;
import glamvoir.appzstack.glamvoir.customview.CustomTextBold;
import glamvoir.appzstack.glamvoir.helpers.Utility;
import glamvoir.appzstack.glamvoir.model.TaskResponse;
import glamvoir.appzstack.glamvoir.model.net.request.RequestBean;
import glamvoir.appzstack.glamvoir.model.net.response.CityResponse;
import glamvoir.appzstack.glamvoir.model.net.response.ProfileResponse;

/**
 * Created by jaim on 9/12/2015.
 */
public class MyCityActivity extends AppCompatActivity implements View.OnClickListener {

    private RequestBean mRequestBean;
    private Toolbar toolbar;
    private LinearLayout layout;
    protected View loadIndicator;
    private  Button bt1,bt2,bt3,bt4,bt5,bt6,bt7,bt8,bt9,bt10,bt11;
    Typeface copperplateGothicLight;
    AppPreferences preferences;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, MyCityActivity.class);
        intent.putExtra("ParentClassName", context.getClass().getSimpleName());
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_mycity);

        mRequestBean = new RequestBean();
        mRequestBean.setLoader(true);
        mRequestBean.setActivity(this);
        copperplateGothicLight = Typeface.createFromAsset(this.getAssets(), "nexa_bold-webfont.ttf");

        preferences = new AppPreferences(MyCityActivity.this);

        initViews();
        getLoaderManager().restartLoader(LoaderID.GET_CITY, null, cityCallback);
        //layout.setOrientation(LinearLayout.VERTICAL);  //Can also be done in xml by android:orientation="vertical"
        iniListiner();

        getToolbar(toolbar);
        updateUI();


    }

    private void iniListiner() {
        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
        bt3.setOnClickListener(this);
        bt4.setOnClickListener(this);
        bt5.setOnClickListener(this);
        bt6.setOnClickListener(this);
        bt7.setOnClickListener(this);
        bt8.setOnClickListener(this);
        bt9.setOnClickListener(this);
        bt10.setOnClickListener(this);
        bt11.setOnClickListener(this);

    }

    private void initViews() {
        layout = (LinearLayout) findViewById(R.id.layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        loadIndicator = findViewById(R.id.loadIndicator);
        bt1= (Button) findViewById(R.id.bt1);
        bt2= (Button) findViewById(R.id.bt2);
        bt3= (Button) findViewById(R.id.bt3);
        bt4= (Button) findViewById(R.id.bt4);
        bt5= (Button) findViewById(R.id.bt5);
        bt6= (Button) findViewById(R.id.bt6);
        bt7= (Button) findViewById(R.id.bt7);
        bt8= (Button) findViewById(R.id.bt8);
        bt9= (Button) findViewById(R.id.bt9);
        bt10= (Button) findViewById(R.id.bt10);
        bt11= (Button) findViewById(R.id.bt11);

    }

    private void getToolbar(Toolbar toolbar) {

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SpannableString s = new SpannableString(getResources().getString(R.string.my_city));
        s.setSpan(new CustomTextBold(this), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        getSupportActionBar().setTitle(s);

    }

    @Override
    public Intent getSupportParentActivityIntent() {
        Intent parentIntent = getIntent();
        String className = parentIntent.getStringExtra("ParentClassName"); //getting the parent class name
        Intent newIntent = null;
        try {
            //you need to define the class with package name
            newIntent = new Intent(MyCityActivity.this, Class.forName(AppConstant.PACKAGE + className));
            newIntent.putExtra("ParentClassName", "HomeActivity");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return newIntent;
    }

    LoaderManager.LoaderCallbacks<TaskResponse<CityResponse>> cityCallback =
            new LoaderManager.LoaderCallbacks<TaskResponse<CityResponse>>() {

                @Override
                public Loader<TaskResponse<CityResponse>> onCreateLoader(int id, Bundle args) {
                    loadIndicator.setVisibility(View.VISIBLE);
                    return new CityLoader(mRequestBean.getContext());
                }

                @Override
                public void onLoadFinished(Loader<TaskResponse<CityResponse>> loader, TaskResponse<CityResponse> data) {
                    if (loader instanceof CityLoader) {
                        loadIndicator.setVisibility(View.GONE);
                        if (data.error != null) {
                            Utility.showToast(mRequestBean.getContext(), data.error.toString());
                        } else {

                            if (data.data != null && data.data.error_code != null) {
                                addButtons(data.data.results);
                            }

                        }
                    }
                }

                @Override
                public void onLoaderReset(Loader<TaskResponse<CityResponse>> loader) {
                }
            };


    private void addButtons(List<CityResponse.City> list) {

         for (int j = 0; j < list.size(); j++) {
           /* Button btnTag = new Button(this);
            btnTag.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            btnTag.setText(list.get(j).city_name);
            btnTag.setId(j + 1);
            row.addView(btnTag);*/
             if(list.get(j).city_id.equals("7")){
                 bt1.setText(list.get(j).city_name);
                 bt1.setTypeface(copperplateGothicLight);
             }


             if(list.get(j).city_id.equals("1")){
                 bt2.setText(list.get(j).city_name);
                 bt2.setTypeface(copperplateGothicLight);  }

             if(list.get(j).city_id.equals("2")){
                 bt3.setText(list.get(2).city_name);
                 bt3.setTypeface(copperplateGothicLight);  }

             if(list.get(j).city_id.equals("3")){
                 bt4.setText(list.get(j).city_name);
                 bt4.setTypeface(copperplateGothicLight);  }

             if(list.get(j).city_id.equals("4")){

                 bt5.setText(list.get(j).city_name);
                 bt5.setTypeface(copperplateGothicLight);
                  }
             if(list.get(j).city_id.equals("5")){
                 bt6.setText(list.get(j).city_name);
                 bt6.setTypeface(copperplateGothicLight);
                 }

             if(list.get(j).city_id.equals("6")){

                 bt7.setText(list.get(j).city_name);
                 bt7.setTypeface(copperplateGothicLight);}

             if(list.get(j).city_id.equals("8")){
                 bt8.setText(list.get(j).city_name);
                 bt8.setTypeface(copperplateGothicLight);
             }
             if(list.get(j).city_id.equals("9")){
                 bt9.setText(list.get(j).city_name);
                 bt9.setTypeface(copperplateGothicLight);
             }

             if(list.get(j).city_id.equals("10")){
                 bt10.setText(list.get(j).city_name);
                 bt10.setTypeface(copperplateGothicLight);  }

             if(list.get(j).city_id.equals("11")){

                 bt11.setText(list.get(j).city_name);
                 bt11.setTypeface(copperplateGothicLight);  }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt1:
                preferences.setUserCity("7");
                update();
                break;
            case R.id.bt2:
                preferences.setUserCity("1");
                update();
                break;
            case R.id.bt3:
                preferences.setUserCity("2");
                update();
                break;
            case R.id.bt4:
                preferences.setUserCity("3");
                update();
                break;
            case R.id.bt5:
                preferences.setUserCity("4");
                update();
                break;
            case R.id.bt6:
                preferences.setUserCity("5");
                update();
                break;
            case R.id.bt7:
                preferences.setUserCity("6");
                update();
                break;
            case R.id.bt8:
                preferences.setUserCity("8");
                update();
                break;
            case R.id.bt9:
                preferences.setUserCity("9");
                update();
                break;
            case R.id.bt10:
                preferences.setUserCity("10");
                update();
                break;
            case R.id.bt11:
                preferences.setUserCity("11");
                update();
                break;

        }

    }
    private void update() {
        getLoaderManager().restartLoader(LoaderID.GETPROFILE, null, updateCityCallback);
    }

    LoaderManager.LoaderCallbacks<TaskResponse<ProfileResponse>> updateCityCallback =
            new LoaderManager.LoaderCallbacks<TaskResponse<ProfileResponse>>() {

                @Override
                public Loader<TaskResponse<ProfileResponse>> onCreateLoader(int id, Bundle args) {
                    loadIndicator.setVisibility(View.VISIBLE);
                    //user_fname, user_lname,user_dob, user_gender, user_about, user_contact, user_city

                    return new ProfileLoader(mRequestBean, AppConstant.METHOD_UPDATEPROFILE, preferences.getUserId(), preferences.getFirstName(), preferences.getLastName(), preferences.getUser_dob(), AppPreferences.getInstance(mRequestBean.getContext()).getGender(), preferences.getUserAbout(), preferences.getUserContact(), preferences.getUserCity());
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
                                finish();
                            }
                        }
                    }
                }

                @Override
                public void onLoaderReset(Loader<TaskResponse<ProfileResponse>> loader) {
                }
            };
    private void updateUI() {


        if (preferences.getUserCity() != null) {
           // edt_age.setText(preferences.getUser_dob());
            if(preferences.getUserCity().equals("7")){
                bt1.setBackgroundResource(R.drawable.city_active_background);
            }
            else if (preferences.getUserCity().equals("1")){
                bt2.setBackgroundResource(R.drawable.city_active_background);
            }
            else if (preferences.getUserCity().equals("2")){
                bt3.setBackgroundResource(R.drawable.city_active_background);
            }
            else if (preferences.getUserCity().equals("3")){
                bt4.setBackgroundResource(R.drawable.city_active_background);
            }
            else if (preferences.getUserCity().equals("4")){
                bt5.setBackgroundResource(R.drawable.city_active_background);
            }
            else if (preferences.getUserCity().equals("5")){
                bt6.setBackgroundResource(R.drawable.city_active_background);

            }
            else if (preferences.getUserCity().equals("6")){
                bt7.setBackgroundResource(R.drawable.city_active_background);

            }
            else if (preferences.getUserCity().equals("8")){
                bt9.setBackgroundResource(R.drawable.city_active_background);

            }
            else if (preferences.getUserCity().equals("9")){
                bt10.setBackgroundResource(R.drawable.city_active_background);

            }
            else if (preferences.getUserCity().equals("10")){
                bt11.setBackgroundResource(R.drawable.city_active_background);

            }  //


        }

    }



}
