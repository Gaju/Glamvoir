package glamvoir.appzstack.glamvoir.activity;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import glamvoir.appzstack.glamvoir.R;
import glamvoir.appzstack.glamvoir.fragment.HomeFragment;
import glamvoir.appzstack.glamvoir.fragment.Setting;
import glamvoir.appzstack.glamvoir.model.net.request.RequestBean;
import glamvoir.appzstack.glamvoir.navigationdrawer.FragmentDrawer;


public class HomeActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {

    private static String TAG = HomeActivity.class.getSimpleName();
    private FragmentDrawer drawerFragment;
    private RequestBean mRequestBean;
    private Toolbar toolbar;


    public static void startActivityWithClearTop(Activity activity) {
        Intent intent = new Intent(activity, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("ParentClassName", activity.getClass().getSimpleName());
        activity.startActivity(intent);
        activity.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRequestBean = new RequestBean();
        mRequestBean.setLoader(true);
        mRequestBean.setActivity(this);
        mRequestBean.setLoader(true);

        //initialize all views
        initViews();

        initListener();

        getToolbar(toolbar);

        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);
        drawerFragment.setDrawerListener(this);

        // display the first navigation drawer view on app launch
        displayView(2);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
        //getSupportActionBar().setTitle(getResources().getString(R.string.signin));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }
*/
        if (id == R.id.action_search) {
            Toast.makeText(getApplicationContext(), "Search action is selected!", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 1:
                break;
            case 2:
                fragment = new HomeFragment();

                if (fragment != null) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.container_body, fragment);
                    fragmentTransaction.commit();

                    // set the toolbar title
                    getSupportActionBar().setTitle(title);
                }
                title = getString(R.string.title_home);
                break;

            case 3:
                FollowersActivity.startActivity(HomeActivity.this);
                break;

            case 4:
                FollowingActivity.startActivity(HomeActivity.this);
                break;

            case 5:
                MysaveActivity.startActivity(HomeActivity.this);
                break;
            case 6:
                MyPostActivity.startActivity(HomeActivity.this);
                break;
            case 7:
                SettingsActivity.startActivity(HomeActivity.this);

                break;
            default:
                break;
        }
    }
}
