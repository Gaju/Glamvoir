package glamvoir.appzstack.glamvoir.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import glamvoir.appzstack.glamvoir.Bean.ChildPostBean;
import glamvoir.appzstack.glamvoir.R;

/**
 * Created by jai on 9/10/2015.
 */
public class ImagePagerAdapter extends PagerAdapter {

    private Context mContext;

    private List<ChildPostBean> list;

    private final int[] mImageIds = new int[]{ //
            R.drawable.chiang_mai,
            R.drawable.himeji,
            R.drawable.petronas_twin_tower,
            R.drawable.ulm,
            R.drawable.chiang_mai,

    };

    public ImagePagerAdapter(Context context, List<ChildPostBean> list) {
        mContext = context;
        this.list = list;
    }

    public int getCount() {


        return list.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        ChildPostBean item = list.get(position);

        LayoutInflater inflater = (LayoutInflater) container.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View convertView = inflater.inflate(R.layout.view_item_shell, null);

        ImageView view_image = (ImageView) convertView
                .findViewById(R.id.view_image);
        TextView description = (TextView) convertView
                .findViewById(R.id.description);

        view_image.setImageResource(mImageIds[position]);
        view_image.setScaleType(ImageView.ScaleType.FIT_XY);

        description.setText(item.getPost_description());

        container.addView(convertView, position);

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


