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
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import glamvoir.appzstack.glamvoir.R;
import glamvoir.appzstack.glamvoir.apppreference.AppPreferences;
import glamvoir.appzstack.glamvoir.fragment.FleaFragment;
import glamvoir.appzstack.glamvoir.fragment.HomeFragment;
import glamvoir.appzstack.glamvoir.model.net.request.RequestBean;
import glamvoir.appzstack.glamvoir.navigationdrawer.FragmentDrawer_Lv;


public class HomeActivity extends AppCompatActivity implements FragmentDrawer_Lv.FragmentDrawerListener_Lv {

    private static String TAG = HomeActivity.class.getSimpleName();
    private FragmentDrawer_Lv drawerFragment;
    private RequestBean mRequestBean;
    private Toolbar toolbar;
    int fleaposition;
    AppPreferences appPreferences;


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
        appPreferences = new AppPreferences(this);

        mRequestBean = new RequestBean();
        mRequestBean.setLoader(true);
        mRequestBean.setActivity(this);
        mRequestBean.setLoader(true);

        //initialize all views
        initViews();

        initListener();

        getToolbar(toolbar);

       /* drawerFragment = (FragmentDrawer_Lv)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);
        drawerFragment.setDrawerListener(this);*/

        drawerFragment = (FragmentDrawer_Lv)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer_lv);
        drawerFragment.setUp(R.id.fragment_navigation_drawer_lv, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);
        drawerFragment.setDrawerListener_lv(this);
        // display the first navigation drawer view on app launch
        displayView(0);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.jai
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

            Intent intent = new Intent(HomeActivity.this, SearchResultsActivity.class);
            intent.putExtra("ParentClassName", this.getClass().getSimpleName());
            startActivity(intent);
        }

        if (id == R.id.action_uplaod) {
            Toast.makeText(getApplicationContext(), "Search action is selected!", Toast.LENGTH_SHORT).show();


            if (HomeFragment.mViewPager.getCurrentItem() == 1) {
                //      AddStory.startActivity(HomeActivity.this);
                Intent intent = new Intent(HomeActivity.this, AddStory.class);
                intent.putExtra("ParentClassName", this.getClass().getSimpleName());
                intent.putExtra("CATOGERYNAME", "FASHION & LIFESTYLE");
                startActivity(intent);

            }
            if (HomeFragment.mViewPager.getCurrentItem() == 2) {
                //  AddStory.startActivity(HomeActivity.this);
                Intent intent = new Intent(HomeActivity.this, AddStory.class);
                intent.putExtra("ParentClassName", this.getClass().getSimpleName());
                intent.putExtra("CATOGERYNAME", "FOOD & PLACES");
                startActivity(intent);

            }
            if (HomeFragment.mViewPager.getCurrentItem() == 3) {
                // AddStory.startActivity(HomeActivity.this);
                Intent intent = new Intent(HomeActivity.this, AddStory.class);
                intent.putExtra("ParentClassName", this.getClass().getSimpleName());
                intent.putExtra("CATOGERYNAME", "MUSIC & GIGS");
                startActivity(intent);

            }
            if (HomeFragment.mViewPager.getCurrentItem() == 4) {
                // AddStory.startActivity(HomeActivity.this);
                Intent intent = new Intent(HomeActivity.this, AddStory.class);
                intent.putExtra("ParentClassName", this.getClass().getSimpleName());
                intent.putExtra("CATOGERYNAME", "INTERESTS");
                startActivity(intent);

            }
            if (fleaposition == 1) {
                Intent intent = new Intent(HomeActivity.this, AddStory.class);
                intent.putExtra("ParentClassName", this.getClass().getSimpleName());
                intent.putExtra("CATOGERYNAME", "FLEA MARKET");
                startActivity(intent);
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDrawerItemSelected_Lv(int position) {
        displayView(position);
    }

    private void displayView(int position) {
        fleaposition = position;
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                fragment = new HomeFragment();
                title = getString(R.string.title_feed);
                if (fragment != null) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.container_body, fragment);
                    fragmentTransaction.commit();

                    // set the toolbar title
                    getSupportActionBar().setTitle(title);
                }


                break;
            case 1:
                fragment = new FleaFragment();
                title = getString(R.string.title_home);
                if (fragment != null) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.container_body, fragment);
                    fragmentTransaction.commit();

                    // set the toolbar title
                    getSupportActionBar().setTitle(title);
                }

                break;

            case 2:
                FollowersActivity.startActivity(HomeActivity.this, appPreferences.getUserId());
                break;

            case 3:
                FollowingActivity.startActivity(HomeActivity.this, appPreferences.getUserId());
                break;

            case 4:
                MysaveActivity.startActivity(HomeActivity.this, appPreferences.getUserId());
                break;
            case 5:
                MyPostActivity.startActivity(HomeActivity.this, appPreferences.getUserId(), false);
                break;


            case 6:
                SettingsActivity.startActivity(HomeActivity.this);

                break;
            default:
                break;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        View view2 = getCurrentFocus();
        boolean ret = super.dispatchTouchEvent(event);

        if (view2 instanceof EditText) {
            View w = getCurrentFocus();
            int scrcoords[] = new int[2];
            w.getLocationOnScreen(scrcoords);
            float x = event.getRawX() + w.getLeft() - scrcoords[0];
            float y = event.getRawY() + w.getTop() - scrcoords[1];

            if (event.getAction() == MotionEvent.ACTION_UP
                    && (x < w.getLeft() || x >= w.getRight()
                    || y < w.getTop() || y > w.getBottom())) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);

            }
        }
        return ret;
    }

}
