package glamvoir.appzstack.glamvoir.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import glamvoir.appzstack.glamvoir.R;

/**
 * Created by jai on 9/10/2015.
 */
public class ImagePagerAdapter extends PagerAdapter{

    private Context mContext;


    private final int[] mImageIds = new int[] { //
            R.drawable.chiang_mai,
            R.drawable.himeji,
            R.drawable.petronas_twin_tower,
            R.drawable.ulm,
            R.drawable.chiang_mai,

    };

    public ImagePagerAdapter(Context context) {
        mContext = context;
    }

    public int getCount() {
        return mImageIds.length;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        LayoutInflater inflater = (LayoutInflater) container.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View convertView = inflater.inflate(R.layout.view_item_shell, null);

        ImageView view_image = (ImageView) convertView
                .findViewById(R.id.view_image);
        TextView description = (TextView) convertView
                .findViewById(R.id.description);

        view_image.setImageResource(mImageIds[position]);
        view_image.setScaleType(ImageView.ScaleType.FIT_XY);

        description.setText("The natural habitat of the Niligiri tahr,Rajamala          Rajamala is 2695 Mts above sea level"
                + "The natural habitat of the Niligiri tahr,Rajamala Rajamala is 2695 Mts above sea level"
                + "The natural habitat of the Niligiri tahr,Rajamala Rajamala is 2695 Mts above sea level");

        container.addView(convertView, 0);

        return convertView;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ViewGroup) object);
    }
}


