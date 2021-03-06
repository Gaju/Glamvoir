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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import glamvoir.appzstack.glamvoir.R;
import glamvoir.appzstack.glamvoir.TimeAgo.TimeAgo;
import glamvoir.appzstack.glamvoir.activity.CommentActivity;
import glamvoir.appzstack.glamvoir.helpers.ImageLoaderInitializer;
import glamvoir.appzstack.glamvoir.model.net.response.CommentResponse;

/**
 * Created by jai on 9/26/2015.
 */
public class CommentCustomAdapter extends BaseAdapter {
    private static LayoutInflater inflater = null;
    ImagePagerAdapter adapter;
    Context context;
    ImageLoader imageLoader;
    DisplayImageOptions options;
    ArrayList<CommentResponse.AllCommentResponse> list;
    private int mPosition;

    public CommentCustomAdapter(CommentActivity commentActivity, ArrayList<CommentResponse.AllCommentResponse> list) {
        inflater = (LayoutInflater) commentActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.list = list;
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(commentActivity));
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
        CommentResponse.AllCommentResponse item = list.get(position);

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



        if (item.user_fname != null) {
            holder.user_name.setText(item.user_fname +" "+ item.user_lname);
        }

        if (item.comment_text != null) {
            holder.comment.setText(item.comment_text);
        }

        if (item.comment_date != null) {
            TimeAgo timeAgo;
            String string_date = item.comment_date;

            SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date d = null;
            try {
                d = f.parse(string_date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            long milliseconds = d.getTime();
            timeAgo= new TimeAgo();
            String setTimeago=  timeAgo.DateDifference(milliseconds);
            holder.comment_time.setText(setTimeago);
        }

        if (item.user_image != null && !item.user_image.equals("")) {
            imageLoader.displayImage(item.user_image, holder.user_image, options);
        }
        else {
            holder.user_image.setBackgroundResource(R.drawable.no_media);
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
