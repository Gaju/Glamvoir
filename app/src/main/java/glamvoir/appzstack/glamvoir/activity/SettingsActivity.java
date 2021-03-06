package glamvoir.appzstack.glamvoir.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.widget.ListView;
import android.widget.Toast;

import glamvoir.appzstack.glamvoir.R;
import glamvoir.appzstack.glamvoir.adapter.SettingAdapter;
import glamvoir.appzstack.glamvoir.apppreference.AppPreferences;
import glamvoir.appzstack.glamvoir.constant.AppConstant;
import glamvoir.appzstack.glamvoir.customview.CustomTextBold;
import glamvoir.appzstack.glamvoir.model.net.request.RequestBean;

/**
 * Created by gajendran on 5/8/15.
 */
public class SettingsActivity extends AppCompatActivity implements SettingAdapter.MyItemClickListener {

    ListView listView;
    String[] settingsLabels;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, SettingsActivity.class);
        intent.putExtra("ParentClassName", context.getClass().getSimpleName());
        context.startActivity(intent);
    }

    private RequestBean mRequestBean;
    private Toolbar toolbar; //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_settings);

        mRequestBean = new RequestBean();
        mRequestBean.setLoader(true);
        mRequestBean.setActivity(this);
        mRequestBean.setLoader(true);

        //initialize all views
        initViews();

        initListener();

        getToolbar(toolbar);


        settingsLabels = getResources().getStringArray(R.array.settings_labels);

        SettingAdapter adapter = new SettingAdapter(this, settingsLabels, this);

        // Assign adapter to ListView
        listView.setAdapter(adapter);
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
        SpannableString s = new SpannableString(getResources().getString(R.string.setting));
        s.setSpan(new CustomTextBold(this), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        getSupportActionBar().setTitle(s);
    }

    /**
     * initialize all views listeners
     */
    private void initListener() {

    }


    /**
     * initialize all views
     */
    private void initViews() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        listView = (ListView) findViewById(R.id.list);
    }

    @Override
    public Intent getSupportParentActivityIntent() {
        Intent parentIntent = getIntent();
        String className = parentIntent.getStringExtra("ParentClassName"); //getting the parent class name
        Intent newIntent = null;
        try {
            //you need to define the class with package name
            newIntent = new Intent(SettingsActivity.this, Class.forName(AppConstant.PACKAGE + className));

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return newIntent;
    }

    @Override
    public void onClick(int position) {
        switch (position) {

            case 0:
                MyAccountActivity.startActivity(SettingsActivity.this);
                break;
            case 1:
                MyCityActivity.startActivity(SettingsActivity.this);
                break;
            case 2:


                Contact_Us.startActivity(SettingsActivity.this);


                break;
            case 3:

             try {

                 Intent testIntent = new Intent(Intent.ACTION_VIEW);
                 Uri data = Uri.parse("mailto:?subject=" + " " + "&body=" + "" + "&to=" + "jobs@boommarcom.com");
                 testIntent.setData(data);
                 startActivity(testIntent);

             } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getApplicationContext(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
            break;
            case 4:
                Term_and_Condition.startActivity(SettingsActivity.this);
                break;
            case 5:
                Privacy_Policy.startActivity(SettingsActivity.this);
                break;
            case 6:
                FaqActivity.startActivity(SettingsActivity.this);
                break;
            case 7:
                signOut();
                break;
            default:
                break;
        }
    }

    private void signOut(){
        AppPreferences appPreferences=new AppPreferences(this);
        appPreferences.clearAppPreference();
        FrontPageActivity.startActivityWithClearTop(SettingsActivity.this);
    }
}
