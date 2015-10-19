package glamvoir.appzstack.glamvoir.fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import glamvoir.appzstack.glamvoir.R;
import glamvoir.appzstack.glamvoir.SlidingTabs.SlidingTabLayout;
import glamvoir.appzstack.glamvoir.activity.HomeActivity;


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
        search_tab.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {


                if (hasFocus) {
                    Toast.makeText(getActivity(), "got the focus", Toast.LENGTH_LONG).show();
                    search_tab.setBackgroundResource(R.drawable.edit_text_border);
                    search_tab.setCompoundDrawablesWithIntrinsicBounds(R.drawable.search, 0, 0, 0);
                    search_tab.setCursorVisible(true);
                    search_tab.requestFocus();

                } else {
                    Toast.makeText(getActivity(), "lost the focus", Toast.LENGTH_LONG).show();
                    search_tab.setCursorVisible(false);
                    search_tab.setBackgroundResource(R.drawable.edit_text_border_inactive);
                    search_tab.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_search_white_24dp, 0, 0, 0);

                }
            }

        });

        mViewPager = (ViewPager) rootView.findViewById(R.id.view_pager);
        // mViewPager.setOffscreenPageLimit(2);
        // tabcachesize (=tabcount for better performance)
        titlesTAB = new String[]{"All", "FASHION & LIFESTYLE", "FOOD & PLACE", "MUSIC & GIGS", "INTEREST"};
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
