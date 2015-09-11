package glamvoir.appzstack.glamvoir.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import glamvoir.appzstack.glamvoir.R;
import glamvoir.appzstack.glamvoir.adapter.Custome_All_ListAdapter;


public class ALL extends Fragment {

    //ImagePagerAdapter adapter;
    private ListView mlistView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.all,container,false);
        //feed_or_felia_market_shell

        mlistView= (ListView) v.findViewById(R.id.lv_all);
        mlistView.setAdapter(new Custome_All_ListAdapter(getActivity()));


        /*final ViewPager viewPager = (ViewPager)v.findViewById(R.id.view_pager);
        adapter = new ImagePagerAdapter(getActivity());
        viewPager.setAdapter(adapter);

        final CirclePageIndicator circleIndicator = (CirclePageIndicator)v.findViewById(R.id.indicator);
        circleIndicator.setViewPager(viewPager);*/

       /* final TextView tvText = (TextView) v.findViewById(R.id.text);
        tvText.setText(getResources().getString(R.string.bodyText));*/
        return v;
    }


   /* private class ImagePagerAdapter extends PagerAdapter {
        private final int[] mImages = new int[] { //
                R.drawable.chiang_mai,
                R.drawable.himeji,
                R.drawable.petronas_twin_tower,
                R.drawable.ulm,
                R.drawable.chiang_mai,

        };

        @Override
        public void destroyItem(final ViewGroup container, final int position, final Object object) {
            container.removeView((ImageView) object);
        }

        @Override
        public int getCount() {

            return this.mImages.length;
        }

        @Override
        public Object instantiateItem(final ViewGroup container, final int position) {
            final Context context = getActivity();
            final ImageView imageView = new ImageView(context);
            final int padding = context.getResources().getDimensionPixelSize(
                    R.dimen.padding_medium);
            imageView.setPadding(padding, padding, padding, padding);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageResource(this.mImages[position]);
            container.addView(imageView, 0);
            return imageView;
        }

        @Override
        public boolean isViewFromObject(final View view, final Object object) {
            return view == object;
        }
    }
*/
}