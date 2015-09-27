package glamvoir.appzstack.glamvoir.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import glamvoir.appzstack.glamvoir.activity.CommentActivity;
import glamvoir.appzstack.glamvoir.constant.AppConstant;
import glamvoir.appzstack.glamvoir.fragment.ALL;
import glamvoir.appzstack.glamvoir.helpers.ImageLoaderInitializer;
import glamvoir.appzstack.glamvoir.intentservice.SendServerIntentService;

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
    ParentPostBean item;
    Fragment frag;


//    public Custome_All_ListAdapter(FragmentActivity activity, ArrayList<ParentPostBean> allPostsBeans) {
//        this.context = activity;
//        this.list = allPostsBeans;
//        inflater = (LayoutInflater) frag.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        imageLoader = ImageLoader.getInstance();
//        imageLoader.init(ImageLoaderConfiguration.createDefault(frag.getActivity()));
//
//        options = ImageLoaderInitializer.getDisplayImageOptionWithFade();
//    }

    public Custome_All_ListAdapter(Fragment frag, ArrayList<ParentPostBean> allPostsBeans) {
        this.frag = frag;
        this.list = allPostsBeans;
        inflater = (LayoutInflater) frag.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(frag.getActivity()));

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

        item = list.get(position);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.feed_or_felia_market_shell, null);
            holder = new ViewHolder();
            if (item.getChildResult() != null) {
                final ViewPager viewPager = (ViewPager) convertView.findViewById(R.id.view_pager);
                adapter = new ImagePagerAdapter(frag.getActivity(), item.getChildResult());
                viewPager.setAdapter(adapter);
                final CirclePageIndicator circleIndicator = (CirclePageIndicator) convertView.findViewById(R.id.indicator);
                circleIndicator.setViewPager(viewPager);

            } else {
                LinearLayout ll_view_pager = (LinearLayout) convertView.findViewById(R.id.ll_view_pager);

                final ViewPager viewPager = (ViewPager) convertView.findViewById(R.id.view_pager);
                viewPager.setVisibility(View.GONE);
                final CirclePageIndicator circleIndicator = (CirclePageIndicator) convertView.findViewById(R.id.indicator);
                circleIndicator.setVisibility(View.GONE);
                ImageView defaultImage = new ImageView(frag.getActivity());
                defaultImage.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 300));
                defaultImage.setImageResource(R.drawable.pic);
                TextView defaultTextView = new TextView(frag.getActivity());
                defaultTextView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));
                defaultTextView.setGravity(Gravity.CENTER);
                defaultTextView.setText("Image Not Available");
                ll_view_pager.addView(defaultImage);
                ll_view_pager.addView(defaultTextView);


            }


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

        if (item.getTotal_like() > 0) {
            holder.tv_ff_shell_like_count.setVisibility(View.VISIBLE);
            if (item.getTotal_like() == 1) {
                holder.tv_ff_shell_like_count.setText(item.getTotal_like() + " like");
            } else {
                holder.tv_ff_shell_like_count.setText(item.getTotal_like() + " likes");
            }
        } else {
            holder.tv_ff_shell_like_count.setVisibility(View.GONE);
        }


        if (item.getTotal_comment() > 0) {
            holder.tv_ff_shell_comment_count.setVisibility(View.VISIBLE);
            if (item.getTotal_comment() == 1) {
                holder.tv_ff_shell_comment_count.setText(item.getTotal_comment() + " Comment");
            } else {
                holder.tv_ff_shell_comment_count.setText(item.getTotal_comment() + " Comments");
            }
        } else {
            holder.tv_ff_shell_comment_count.setVisibility(View.GONE);
        }


        if (item.getPost_end_date() != null) {
            holder.tv_ff_shell_time.setText(item.getPost_end_date());
        }

        if (item.getUser_image() != null) {
            imageLoader.displayImage(item.getUser_image(), holder.user_Image, options);
        }

        if (item.getContact_no().length() == 10) {
            holder.bt_connect_with_seller.setVisibility(View.VISIBLE);
        } else {
            holder.bt_connect_with_seller.setVisibility(View.GONE);
        }

        holder.tv_ff_shell_comment_count.setText("Comments");

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
                new BottomSheet.Builder(frag.getActivity()).title("Take action on post").sheet(R.menu.menu_complain).listener(new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case R.id.wrong_category:
                                Toast.makeText(frag.getActivity(), "wrong_category", Toast.LENGTH_LONG).show();

                                break;
                            case R.id.dont_show:
                                Toast.makeText(frag.getActivity(), "dont_show", Toast.LENGTH_LONG).show();

                                break;
                            case R.id.roport:
                                Toast.makeText(frag.getActivity(), "roport", Toast.LENGTH_LONG).show();
                                break;
                            case R.id.cacel:
                                Toast.makeText(frag.getActivity(), "Cancel", Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                                break;
                        }
                    }
                }).show();
                break;
            case R.id.bt_connect_with_seller:
                Toast.makeText(frag.getActivity(), "You click connect sellet", Toast.LENGTH_LONG).show();
                break;
            case R.id.bt_ff_shell_shave:
                //  Toast.makeText(frag.getActivity(), "You click connect shave", Toast.LENGTH_LONG).show();
                savePost(item.getUser_id(), item.getPost_id());
                break;
            case R.id.bt_ff_shell_comments:
                CommentActivity.startActvity(frag.getActivity(), item.getPost_id(), null);
                break;
            case R.id.bt_ff_shell_like:
                Toast.makeText(frag.getActivity(), "You click connect like", Toast.LENGTH_LONG).show();
                break;
            case R.id.bt_ff_shell_whatapp:
                Toast.makeText(frag.getActivity(), "You click connect whatapp", Toast.LENGTH_LONG).show();
                break;
            case R.id.bt_ff_shell_share:
                Toast.makeText(frag.getActivity(), "You click connect share", Toast.LENGTH_LONG).show();
                break;
            case R.id.bt_ff_shell_map:
                Toast.makeText(frag.getActivity(), "You click connect Map", Toast.LENGTH_LONG).show();
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

    private void savePost(String userID, String postID) {
        //  SendServerIntentService.startSavePostService(frag.getActivity(), userID, postID, AppConstant.METHOD_SAVEPOST);
        ((ALL) frag).sendServer(AppConstant.METHOD_SAVEPOST, userID, postID);


    }
}
