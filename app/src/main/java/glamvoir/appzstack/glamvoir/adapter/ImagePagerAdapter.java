package glamvoir.appzstack.glamvoir.adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

import glamvoir.appzstack.glamvoir.Bean.ChildPostBean;
import glamvoir.appzstack.glamvoir.R;
import glamvoir.appzstack.glamvoir.config.AppConfig;
import glamvoir.appzstack.glamvoir.helpers.ImageLoaderInitializer;

/**
 * Created by jai on 9/10/2015.
 */
public class ImagePagerAdapter extends PagerAdapter {

    private Context mContext;
    private int mPosition;
    ChildPostBean item;

    private List<ChildPostBean> list = null;
    ImageLoader imageLoader;
    DisplayImageOptions options;

    public ImagePagerAdapter(Context context, List<ChildPostBean> list) {
        mContext = context;
        this.list = list;
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        options = ImageLoaderInitializer.getDisplayImageOptionWithFade();
    }

    public int getCount() {
        if (list == null) {
            return -1;
        }
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

        mPosition = position;

        LayoutInflater inflater = (LayoutInflater) container.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View convertView = inflater.inflate(R.layout.view_item_shell, null);

        item = list.get(position);

        String url = AppConfig.POST_IMAGE_BASE_PATH + item.getPost_image();

        ImageView view_image = (ImageView) convertView
                .findViewById(R.id.view_image);

        TextView description = (TextView) convertView
                .findViewById(R.id.description);

        view_image.setScaleType(ImageView.ScaleType.FIT_CENTER);

        view_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TouchImageView imgDisplay;
                final Dialog dialog = new Dialog(mContext);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.layout_fullscreen_image);
                imgDisplay = (TouchImageView) dialog.findViewById(R.id.imgDisplay);
                imageLoader.displayImage(AppConfig.POST_IMAGE_BASE_PATH + item.getPost_image(), imgDisplay, options);
                dialog.show();
            }
        });

        imageLoader.displayImage(AppConfig.POST_IMAGE_BASE_PATH + item.getPost_image(), view_image, options);

        description.setText(item.getPost_description());

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


