package glamvoir.appzstack.glamvoir.activity;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

import glamvoir.appzstack.glamvoir.R;
import glamvoir.appzstack.glamvoir.asynctaskloader.ProfileLoader;
import glamvoir.appzstack.glamvoir.asynctaskloader.LoaderID;
import glamvoir.appzstack.glamvoir.constant.AppConstant;
import glamvoir.appzstack.glamvoir.customview.CustomTextBold;
import glamvoir.appzstack.glamvoir.helpers.ImageLoaderInitializer;
import glamvoir.appzstack.glamvoir.helpers.Utility;
import glamvoir.appzstack.glamvoir.model.TaskResponse;
import glamvoir.appzstack.glamvoir.model.net.request.RequestBean;
import glamvoir.appzstack.glamvoir.model.net.response.ProfileResponse;

/**
 * Created by jaim on 9/9/2015.
 */
public class ProfileActivity extends AppCompatActivity {

    private RequestBean mRequestBean;
    private Toolbar toolbar;
    private ImageView userImage;
    private TextView txt_UserName;
    protected View loadIndicator;

    public static void startActivity(Context context, String userID) {
        Intent intent = new Intent(context, ProfileActivity.class);
        intent.putExtra("ParentClassName", context.getClass().getSimpleName());
        intent.putExtra("userID", userID);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_profile);

        initViews();

        mRequestBean = new RequestBean();
        mRequestBean.setLoader(true);
        mRequestBean.setActivity(this);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            getLoaderManager().restartLoader(LoaderID.GETPROFILE, bundle, getProfileCallback);
        }

        getToolbar(toolbar);
    }

    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        userImage = (ImageView) findViewById(R.id.imageView4);
        txt_UserName = (TextView) findViewById(R.id.tv_user_name);
        loadIndicator = findViewById(R.id.loadIndicator);
    }

    private void getToolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SpannableString s = new SpannableString("USER PROFILE");
        s.setSpan(new CustomTextBold(this), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        getSupportActionBar().setTitle(s);

    }

    @Override
    public Intent getSupportParentActivityIntent() {
        Intent newIntent = null;
        finish();
        return newIntent;
    }


    LoaderManager.LoaderCallbacks<TaskResponse<ProfileResponse>> getProfileCallback =
            new LoaderManager.LoaderCallbacks<TaskResponse<ProfileResponse>>() {

                @Override
                public Loader<TaskResponse<ProfileResponse>> onCreateLoader(int id, Bundle args) {
                    loadIndicator.setVisibility(View.VISIBLE);
                    return new ProfileLoader(mRequestBean, AppConstant.METHOD_GETPROFILE, args.getString("userID"));
                }

                @Override
                public void onLoadFinished(Loader<TaskResponse<ProfileResponse>> loader, TaskResponse<ProfileResponse> data) {
                    if (loader instanceof ProfileLoader) {
                        loadIndicator.setVisibility(View.GONE);
                        if (data.error != null) {
                            Utility.showToast(mRequestBean.getContext(), data.error.toString());
                        } else {

                            if (data.data != null && data.data.error_code != null) {
                                updateUI(data.data.results);
                            }
                        }
                    }
                }

                @Override
                public void onLoaderReset(Loader<TaskResponse<ProfileResponse>> loader) {
                }
            };


    private void updateUI(List<ProfileResponse.GetProfileResponse> data) {

        if (data.get(0).user_fname != null) {
            txt_UserName.setText(data.get(0).user_fname + " " + data.get(0).user_lname);
        }

        if (data.get(0).user_image != null && !data.get(0).user_image.equals("")) {
            displayImage(userImage, data.get(0).user_image);
        }else{
            userImage.setImageResource(R.drawable.pic);
        }
    }

    private void displayImage(ImageView imageView, String path) {
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(ProfileActivity.this));
        DisplayImageOptions options = ImageLoaderInitializer.getDisplayImageOptionWithFade();
        imageLoader.displayImage(path, imageView, options);

    }
}
