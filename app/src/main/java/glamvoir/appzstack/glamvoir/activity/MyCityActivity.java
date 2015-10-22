package glamvoir.appzstack.glamvoir.activity;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.List;

import glamvoir.appzstack.glamvoir.R;
import glamvoir.appzstack.glamvoir.asynctaskloader.CityLoader;
import glamvoir.appzstack.glamvoir.asynctaskloader.LoaderID;
import glamvoir.appzstack.glamvoir.constant.AppConstant;
import glamvoir.appzstack.glamvoir.helpers.Utility;
import glamvoir.appzstack.glamvoir.model.TaskResponse;
import glamvoir.appzstack.glamvoir.model.net.request.RequestBean;
import glamvoir.appzstack.glamvoir.model.net.response.CityResponse;

/**
 * Created by jaim on 9/12/2015.
 */
public class MyCityActivity extends AppCompatActivity {

    private RequestBean mRequestBean;
    private Toolbar toolbar;
    private LinearLayout layout;
    protected View loadIndicator;

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

        initViews();

        //layout.setOrientation(LinearLayout.VERTICAL);  //Can also be done in xml by android:orientation="vertical"

        getToolbar(toolbar);

        getLoaderManager().restartLoader(LoaderID.GET_CITY, null, cityCallback);

    }

    private void initViews() {
        layout = (LinearLayout) findViewById(R.id.layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        loadIndicator = findViewById(R.id.loadIndicator);
    }

    private void getToolbar(Toolbar toolbar) {

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.my_city));
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

        //for (int i = 0; i < list.size(); i++) {
        LinearLayout row = new LinearLayout(this);
        row.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        for (int j = 0; j < list.size(); j++) {
            Button btnTag = new Button(this);
            btnTag.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            btnTag.setText(list.get(j).city_name);
            btnTag.setId(j + 1);
            row.addView(btnTag);
        }

        layout.addView(row);
        // }
    }
}
