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

import java.util.List;

import glamvoir.appzstack.glamvoir.R;
import glamvoir.appzstack.glamvoir.helpers.ImageLoaderInitializer;
import glamvoir.appzstack.glamvoir.model.net.response.LikedUsers;

/**
 * Created by gajendran on 19/10/15.
 */
public class LikedUserAdapter extends BaseAdapter {
    private static LayoutInflater inflater = null;
    ImagePagerAdapter adapter;
    Context context;
    ImageLoader imageLoader;
    DisplayImageOptions options;
    List<LikedUsers.LikedUser> list;
    private int mPosition;

    public LikedUserAdapter(Context context, List<LikedUsers.LikedUser> list) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.list = list;
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        options = ImageLoaderInitializer.getDisplayImageOptionWithFade();
    }

    @Override
    public int getCount() {
        return list.size();
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
        ViewHolder holder = null;

        mPosition = position;
        LikedUsers.LikedUser item = list.get(position);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_comment_shell, null);
            holder = new ViewHolder();
            holder.user_image = (ImageView) convertView.findViewById(R.id.circleView);

            holder.user_name = (TextView) convertView.findViewById(R.id.user_name);
            holder.comment = (TextView) convertView.findViewById(R.id.user_comment);
            holder.comment_time = (TextView) convertView.findViewById(R.id.comment_date);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.comment.setVisibility(View.GONE);
        holder.comment_time.setVisibility(View.GONE);
        if (item.user_fname != null) {
            holder.user_name.setText(item.user_fname + " " + item.user_lname);
        }

        if (item.user_image != null && !item.user_image.equals("")) {
            imageLoader.displayImage(item.user_image, holder.user_image, options);
        }

        return convertView;
    }

    static class ViewHolder {

        ImageView user_image;
        TextView user_name;
        TextView comment;
        TextView comment_time;
    }
}
