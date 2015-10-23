package glamvoir.appzstack.glamvoir.fragment;


import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;

import glamvoir.appzstack.glamvoir.R;
import glamvoir.appzstack.glamvoir.SlidingTabs.SlidingTabLayout;


public class HomeFragment extends Fragment {
   /* private FragmentTabHost mTabHost;*/

    SlidingTabLayout mSlidingTabLayout;
    public static ViewPager mViewPager;
    EditText search_tab ;
    String[] titlesTAB;
    int Numboftabs = 5;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        search_tab= (EditText) rootView.findViewById(R.id.search_tab);


        search_tab.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                isKeyboardShown(search_tab.getRootView());
            }
        });
    

        mViewPager = (ViewPager) rootView.findViewById(R.id.view_pager);

        titlesTAB = new String[]{"All", "FASHION & LIFESTYLE", "FOOD & PLACES", "MUSIC & GIGS", "INTERESTS"};
        mSlidingTabLayout = (SlidingTabLayout) rootView.findViewById(R.id.sliding_tabs);


        // use own style rules for tab layout
        mSlidingTabLayout.setCustomTabView(R.layout.tab_indicator, android.R.id.text1);

        mSlidingTabLayout.setSelectedIndicatorColors(getResources().getColor(R.color.tab_indicator_color));
        mSlidingTabLayout.setDistributeEvenly(true);
        mViewPager.setAdapter(new ViewPagerAdapter(getChildFragmentManager(), titlesTAB, Numboftabs));
        mSlidingTabLayout.setViewPager(mViewPager);
        mViewPager.setCurrentItem(1);
        // Tab events
        if (mSlidingTabLayout != null) {
            mSlidingTabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset,
                                           int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                   /* for (int i=0;i<mSlidingTabLayout.getChildCount();i++){
                        TextView  tv= (TextView) mSlidingTabLayout.getChildAt(i);
                        if (i==position){
                            tv.setTextColor(Color.BLUE);
                        }
                        else {
                            tv.setTextColor(Color.BLACK);
                        }
                    }
*/
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

        }


        return rootView;
    }

    private boolean isKeyboardShown(View rootView) {
        final int SOFT_KEYBOARD_HEIGHT_DP_THRESHOLD = 128;

        Rect r = new Rect();
        rootView.getWindowVisibleDisplayFrame(r);
        DisplayMetrics dm = rootView.getResources().getDisplayMetrics();
    /* heightDiff = rootView height - status bar height (r.top) - visible frame height (r.bottom - r.top) */
        int heightDiff = rootView.getBottom() - r.bottom;
    /* Threshold size: dp to pixels, multiply with display density */
        boolean isKeyboardShown = heightDiff > SOFT_KEYBOARD_HEIGHT_DP_THRESHOLD * dm.density;

        if (isKeyboardShown){

            search_tab.setBackgroundResource(R.drawable.edit_text_border);
            search_tab.setPadding(20,0,0,0);
            search_tab.setCompoundDrawablesWithIntrinsicBounds(R.drawable.search, 0, 0, 0);
            search_tab.setCursorVisible(true);
            search_tab.requestFocus();
        }
      else {


                search_tab.setCursorVisible(false);

            search_tab.setPadding(20,0,0,0);
                search_tab.setBackgroundResource(R.drawable.edit_text_border_inactive);
                search_tab.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_search_white_24dp, 0, 0, 0);


        }


        return isKeyboardShown;
    }

    @Override
    public void onAttach(Activity activity) {

        super.onAttach(activity);
    }

    @Override
    public void onDetach() {

        super.onDetach();
    }

    public class ViewPagerAdapter extends FragmentStatePagerAdapter {

        CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
        int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created


        // Build a Constructor and assign the passed Values to appropriate values in the class
        public ViewPagerAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb) {
            super(fm);

            this.Titles = mTitles;
            this.NumbOfTabs = mNumbOfTabsumb;

        }

        //This method return the fragment for the every position in the View Pager
        @Override
        public Fragment getItem(int position) {

            if (position == 0) // if the position is 0 we are returning the First tab
            {

                ALL tab1 = new ALL();
                return tab1;
            } else if (position == 1) // if the position is 0 we are returning the First tab
            {

                Fashion tab2 = new Fashion();
                return tab2;
            } else if (position == 2) // if the position is 0 we are returning the First tab
            {
                FoodAndPlaces tab3 = new FoodAndPlaces();
                return tab3;
            } else if (position == 3) // if the position is 0 we are returning the First tab
            {

                StoreAndDeals tab4 = new StoreAndDeals();
                return tab4;
            } else if (position == 4) // if the position is 0 we are returning the First tab
            {

                Interest tab5 = new Interest();
                return tab5;
            }

            return null;
        }


        // This method return the titles for the Tabs in the Tab Strip

        @Override
        public CharSequence getPageTitle(int position) {

            return Titles[position];
        }

        // This method return the Number of tabs for the tabs Strip

        @Override
        public int getCount() {
            return NumbOfTabs;
        }


    }




}
