package glamvoir.appzstack.glamvoir.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cocosw.bottomsheet.BottomSheet;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;

import glamvoir.appzstack.glamvoir.Bean.ParentPostBean;
import glamvoir.appzstack.glamvoir.R;
import glamvoir.appzstack.glamvoir.helpers.ImageLoaderInitializer;

/**
 * Created by jaim on 9/11/2015.
 */
public class Custome_All_ListAdapter extends BaseAdapter implements View.OnClickListener {

    private static LayoutInflater inflater = null;
    ImagePagerAdapter adapter;
    Context context;
    ArrayList<ParentPostBean> list;
    ImageLoader imageLoader;
    DisplayImageOptions options;

    public Custome_All_ListAdapter(FragmentActivity activity, ArrayList<ParentPostBean> allPostsBeans) {
        this.context = activity;
        this.list = allPostsBeans;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        ParentPostBean item = list.get(position);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.feed_or_felia_market_shell, null);
            holder = new ViewHolder();
            final ViewPager viewPager = (ViewPager) convertView.findViewById(R.id.view_pager);
            adapter = new ImagePagerAdapter(context, item.getChildResult());
            viewPager.setAdapter(adapter);
            final CirclePageIndicator circleIndicator = (CirclePageIndicator) convertView.findViewById(R.id.indicator);
            circleIndicator.setViewPager(viewPager);
            holder.tv_ff_shell_username = (TextView) convertView.findViewById(R.id.tv_ff_shell_username);
            holder.tv_ff_shell_time = (TextView) convertView.findViewById(R.id.tv_ff_shell_time);

            holder.tv_actiontext_checkBox_ff_shell = (TextView) convertView.findViewById(R.id.tv_actiontext_checkBox_ff_shell);
            holder.tv_ff_shell_like_count = (TextView) convertView.findViewById(R.id.tv_ff_shell_like_count);
            holder.tv_ff_shell_comment_count = (TextView) convertView.findViewById(R.id.tv_ff_shell_comment_count);
            holder.checkBox_ff_shell = (CheckBox) convertView.findViewById(R.id.checkBox_ff_shell);
            holder.bt_connect_with_seller = (Button) convertView.findViewById(R.id.bt_connect_with_seller);
            holder.bt_ff_shell_shave = (ImageButton) convertView.findViewById(R.id.bt_ff_shell_shave);
            holder.bt_ff_shell_comments = (ImageButton) convertView.findViewById(R.id.bt_ff_shell_comments);
            holder.bt_ff_shell_like = (ImageButton) convertView.findViewById(R.id.bt_ff_shell_like);
            holder.bt_ff_shell_whatapp = (ImageButton) convertView.findViewById(R.id.bt_ff_shell_whatapp);
            holder.bt_ff_shell_share = (ImageButton) convertView.findViewById(R.id.bt_ff_shell_share);
            holder.bt_ff_shell_complain = (ImageButton) convertView.findViewById(R.id.bt_ff_shell_complain);
            holder.bt_ff_shell_map = (ImageButton) convertView.findViewById(R.id.bt_ff_shell_map);

            holder.user_Image = (ImageView) convertView.findViewById(R.id.imageView);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        if (item.getUser_fname() != null) {
            holder.tv_ff_shell_username.setText(item.getUser_fname() + " " + item.getUser_lname());
        }

        if (item.getTotal_like() != 0) {
            holder.tv_ff_shell_like_count.setText(item.getTotal_like() + "likes");
        } else {
            holder.tv_ff_shell_like_count.setText(item.getTotal_like() + "like");
        }

        if (item.getPost_end_date() != null) {
            holder.tv_ff_shell_time.setText(item.getPost_end_date());
        }

        if (item.getUser_image() != null) {
            imageLoader.displayImage(item.getUser_image(), holder.user_Image, options);
        }

        if(item.getContact_no().length()==10){
            holder.bt_connect_with_seller.setVisibility(View.VISIBLE);
        }else {
            holder.bt_connect_with_seller.setVisibility(View.GONE);
        }

        holder.bt_connect_with_seller.setOnClickListener(this);
        holder.bt_ff_shell_shave.setOnClickListener(this);
        holder.bt_ff_shell_comments.setOnClickListener(this);
        holder.bt_ff_shell_like.setOnClickListener(this);
        holder.bt_ff_shell_whatapp.setOnClickListener(this);
        holder.bt_ff_shell_share.setOnClickListener(this);
        holder.bt_ff_shell_complain.setOnClickListener(this);
        holder.bt_ff_shell_map.setOnClickListener(this);


        return convertView;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.bt_ff_shell_complain:
                new BottomSheet.Builder((Activity) context).title("Take action on post").sheet(R.menu.menu_complain).listener(new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case R.id.wrong_category:
                                Toast.makeText(context, "wrong_category", Toast.LENGTH_LONG).show();

                                break;
                            case R.id.dont_show:
                                Toast.makeText(context, "dont_show", Toast.LENGTH_LONG).show();

                                break;
                            case R.id.roport:
                                Toast.makeText(context, "roport", Toast.LENGTH_LONG).show();
                                break;
                            case R.id.cacel:
                                Toast.makeText(context, "Cancel", Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                                break;
                        }
                    }
                }).show();
                break;
            case R.id.bt_connect_with_seller:
                Toast.makeText(context, "You click connect sellet", Toast.LENGTH_LONG).show();
                break;
            case R.id.bt_ff_shell_shave:
                Toast.makeText(context, "You click connect shave", Toast.LENGTH_LONG).show();
                break;
            case R.id.bt_ff_shell_comments:
                Toast.makeText(context, "You click connect comments", Toast.LENGTH_LONG).show();
                break;
            case R.id.bt_ff_shell_like:
                Toast.makeText(context, "You click connect like", Toast.LENGTH_LONG).show();
                break;
            case R.id.bt_ff_shell_whatapp:
                Toast.makeText(context, "You click connect whatapp", Toast.LENGTH_LONG).show();
                break;
            case R.id.bt_ff_shell_share:
                Toast.makeText(context, "You click connect share", Toast.LENGTH_LONG).show();
                break;
            case R.id.bt_ff_shell_map:
                Toast.makeText(context, "You click connect Map", Toast.LENGTH_LONG).show();
                break;


        }


    }


    static class ViewHolder {

        TextView tv_ff_shell_username;
        TextView tv_ff_shell_time;
        TextView tv_actiontext_checkBox_ff_shell;
        TextView tv_ff_shell_like_count;
        TextView tv_ff_shell_comment_count;
        CheckBox checkBox_ff_shell;
        Button bt_connect_with_seller;
        ImageButton bt_ff_shell_shave;
        ImageButton bt_ff_shell_comments;
        ImageButton bt_ff_shell_like;
        ImageButton bt_ff_shell_whatapp;
        ImageButton bt_ff_shell_share;
        ImageButton bt_ff_shell_complain;
        ImageButton bt_ff_shell_map;
        ImageView user_Image;


    }
}
