package glamvoir.appzstack.glamvoir.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

import glamvoir.appzstack.glamvoir.Bean.ParentPostBean;
import glamvoir.appzstack.glamvoir.R;
import glamvoir.appzstack.glamvoir.activity.CommentActivity;
import glamvoir.appzstack.glamvoir.helpers.ImageLoaderInitializer;

/**
 * Created by jai on 9/26/2015.
 */
public class CommentCustomAdapter extends BaseAdapter {
    private static LayoutInflater inflater = null;
    ImagePagerAdapter adapter;
    Context context;
    ImageLoader imageLoader;
    DisplayImageOptions options;
    public CommentCustomAdapter(CommentActivity commentActivity) {
        inflater = (LayoutInflater) commentActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(commentActivity));

        options = ImageLoaderInitializer.getDisplayImageOptionWithFade();
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if (convertView==null){
            convertView = inflater.inflate(R.layout.layout_comment_shell, null);
            holder = new ViewHolder();
            holder.user_image= (ImageView) convertView.findViewById(R.id.circleView);

            holder.user_name= (TextView) convertView.findViewById(R.id.user_name);
            holder.user_name= (TextView) convertView.findViewById(R.id.user_comment);
            holder.user_name= (TextView) convertView.findViewById(R.id.comment_date);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }



        return convertView;
    }

    static class ViewHolder{

        ImageView user_image;
        TextView user_name;
        TextView comment;
        TextView comment_time;
    }
}
