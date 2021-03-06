package glamvoir.appzstack.glamvoir.adapter;

import android.app.LoaderManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
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

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import glamvoir.appzstack.glamvoir.Bean.ParentPostBean;
import glamvoir.appzstack.glamvoir.R;
import glamvoir.appzstack.glamvoir.TimeAgo.TimeAgo;
import glamvoir.appzstack.glamvoir.activity.CommentActivity;
import glamvoir.appzstack.glamvoir.activity.LikeListActivity;
import glamvoir.appzstack.glamvoir.activity.ProfileActivity;
import glamvoir.appzstack.glamvoir.apppreference.AppPreferences;
import glamvoir.appzstack.glamvoir.asynctaskloader.LikeFollowLoader;
import glamvoir.appzstack.glamvoir.asynctaskloader.LoaderID;
import glamvoir.appzstack.glamvoir.constant.AppConstant;
import glamvoir.appzstack.glamvoir.fragment.BaseFragment;
import glamvoir.appzstack.glamvoir.helpers.ImageLoaderInitializer;
import glamvoir.appzstack.glamvoir.helpers.Utility;
import glamvoir.appzstack.glamvoir.intentservice.NetworkIntentService;
import glamvoir.appzstack.glamvoir.model.TaskResponse;
import glamvoir.appzstack.glamvoir.model.net.response.GetPostLikeFollowResponse;

/**
 * Created by jaim on 9/11/2015.
 */
public class Custome_All_ListAdapter extends BaseAdapter implements View.OnClickListener {
    private LayoutInflater inflater = null;
    private ImagePagerAdapter adapter;
    private ArrayList<ParentPostBean> list;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;
    private ParentPostBean item;
    private Fragment frag;
    private String url_post = "http://glamvoir.com/assests/post_images/";
    ViewHolder holder = null;
    private int pos = 0;
    private String postID = null;
    TimeAgo timeAgo;
    Context mContext;
    private boolean isFromSearch;

    public Custome_All_ListAdapter(Fragment frag, ArrayList<ParentPostBean> allPostsBeans, boolean isFromSearch) {
        this.frag = frag;
        this.list = allPostsBeans;
        inflater = (LayoutInflater) frag.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(frag.getActivity()));
        options = ImageLoaderInitializer.getDisplayImageOptionWithFade();
        this.isFromSearch = isFromSearch;
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

    private void setAdapter() {
        adapter = new ImagePagerAdapter(frag.getActivity(), item.getChildResult());
        holder.viewPager.setAdapter(adapter);
        //adapter.setImages(item.getChildResult());
        //adapter.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        //convertView = null;
        item = list.get(position);
        if (convertView == null) {

            convertView = inflater.inflate(R.layout.feed_or_felia_market_shell, null);
            holder = new ViewHolder();

            holder.viewPager = (ViewPager) convertView.findViewById(R.id.view_pager);
            holder.circleIndicator = (CirclePageIndicator) convertView.findViewById(R.id.indicator);

            holder.tv_ff_shell_username = (TextView) convertView.findViewById(R.id.tv_ff_shell_username);
            holder.ll_like_click = (LinearLayout) convertView.findViewById(R.id.ll_like_click);
            holder.ll_comment_click = (LinearLayout) convertView.findViewById(R.id.ll_comment_click);
            //@@@@@@@@
            holder.tv_ff_shell_time = (TextView) convertView.findViewById(R.id.tv_ff_shell_time);
            holder.tv_posting_date_of_post = (TextView) convertView.findViewById(R.id.tv_posting_date_of_post);

            holder.tv_actiontext_checkBox_ff_shell = (TextView) convertView.findViewById(R.id.tv_actiontext_checkBox_ff_shell);
            holder.tv_ff_shell_like_count = (TextView) convertView.findViewById(R.id.tv_ff_shell_like_count);
            holder.tv_ff_shell_comment_count = (TextView) convertView.findViewById(R.id.tv_ff_shell_comment_count);

            holder.ll_view_pager = (LinearLayout) convertView.findViewById(R.id.ll_view_pager);

            holder.checkBox_ff_shell = (CheckBox) convertView.findViewById(R.id.checkBox_ff_shell);

            holder.bt_connect_with_seller = (Button) convertView.findViewById(R.id.bt_connect_with_seller);

            holder.bt_ff_shell_shave = (ImageButton) convertView.findViewById(R.id.bt_ff_shell_shave);
            holder.bt_ff_shell_comments = (ImageButton) convertView.findViewById(R.id.bt_ff_shell_comments);
            holder.bt_ff_shell_like = (ImageButton) convertView.findViewById(R.id.bt_ff_shell_like);
            holder.bt_ff_shell_whatapp = (ImageButton) convertView.findViewById(R.id.bt_ff_shell_whatapp);
            holder.bt_ff_shell_share = (ImageButton) convertView.findViewById(R.id.bt_ff_shell_share);

            holder.ll_ff_shell_complain = (LinearLayout) convertView.findViewById(R.id.ll_ff_shell_complain);
            holder.bt_ff_shell_map = (ImageButton) convertView.findViewById(R.id.bt_ff_shell_map);

            holder.user_Image = (ImageView) convertView.findViewById(R.id.imageView);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.checkBox_ff_shell.setTag(position);
        holder.bt_ff_shell_like.setTag(position);
        holder.bt_ff_shell_comments.setTag(position);
        holder.bt_ff_shell_shave.setTag(position);
        holder.bt_ff_shell_share.setTag(position);
        holder.bt_ff_shell_whatapp.setTag(position);
        holder.user_Image.setTag(position);
        holder.tv_ff_shell_username.setTag(position);
        holder.ll_like_click.setTag(position);
        holder.ll_comment_click.setTag(position);
        //   holder.bt_ff_shell_complain.setTag(position);
        holder.viewPager.setTag(position);
        holder.circleIndicator.setTag(position);
        //@@@@@@@
        holder.ll_ff_shell_complain.setTag(position);
        holder.ll_view_pager.setTag(position);

        if (item.getChildResult().size() <= 1) {
            if (item.getChildResult().get(0).getPost_image().equals("")) {

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.height = frag.getActivity().getResources().getDimensionPixelOffset(R.dimen.height_100dp);
                holder.viewPager.setLayoutParams(params);

                adapter = new ImagePagerAdapter(frag.getActivity(), item.getChildResult());
                holder.viewPager.setAdapter(adapter);
            } else {

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.height = frag.getActivity().getResources().getDimensionPixelOffset(R.dimen.height_300dp);
                holder.viewPager.setLayoutParams(params);


                adapter = new ImagePagerAdapter(frag.getActivity(), item.getChildResult());
                holder.viewPager.setAdapter(adapter);
            }
        } else {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.height = frag.getActivity().getResources().getDimensionPixelOffset(R.dimen.height_300dp);
            holder.viewPager.setLayoutParams(params);


            adapter = new ImagePagerAdapter(frag.getActivity(), item.getChildResult());
            holder.viewPager.setAdapter(adapter);
        }

        if (item.getChildResult().size() > 1) {
            holder.circleIndicator.setVisibility(View.VISIBLE);
            holder.circleIndicator.setViewPager(holder.viewPager);
        } else {
            holder.circleIndicator.setVisibility(View.GONE);
        }

        if (item.getUser_fname() != null) {
            holder.tv_ff_shell_username.setText(item.getUser_fname() + " " + item.getUser_lname());
        }


        if (item.getPost_location() != null && !item.getPost_location().equals("")) {
            holder.bt_ff_shell_map.setVisibility(View.VISIBLE);
        } else {
            holder.bt_ff_shell_map.setVisibility(View.GONE);
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

        if (!AppPreferences.getInstance(frag.getActivity()).getUserId().equals(item.user_id)) {
            holder.tv_actiontext_checkBox_ff_shell.setVisibility(View.VISIBLE);
            holder.checkBox_ff_shell.setVisibility(View.VISIBLE);
            if (item.getIs_following() == 0) {
                holder.tv_actiontext_checkBox_ff_shell.setText("Follow");
                holder.checkBox_ff_shell.setChecked(false);
            } else {
                holder.tv_actiontext_checkBox_ff_shell.setText("Unfollow");
                holder.checkBox_ff_shell.setChecked(true);
            }
        } else {
            holder.tv_actiontext_checkBox_ff_shell.setVisibility(View.GONE);
            holder.checkBox_ff_shell.setVisibility(View.GONE);
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

        if (item.like_dislike_status == 1) {
            holder.bt_ff_shell_like.setImageResource(R.drawable.heart_active);
        } else {
            holder.bt_ff_shell_like.setImageResource(R.drawable.heart);
        }

        if (item.is_saved == 0) {
            holder.bt_ff_shell_shave.setImageResource(R.drawable.mysave);
        } else {
            holder.bt_ff_shell_shave.setImageResource(R.drawable.mysave_active);
        }

        if (item.getcreation_date() != null) {
            String string_date = item.getcreation_date();

            SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date d = null;
            try {
                d = f.parse(string_date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            long milliseconds = d.getTime();
            timeAgo = new TimeAgo();
            String setTimeago = TimeAgo.DateDifference(milliseconds);
            holder.tv_ff_shell_time.setText(setTimeago);
        } else {
            holder.tv_ff_shell_time.setVisibility(View.GONE);
        }

        if (item.getcreation_date() != null) {
            String string_date = item.getcreation_date();
            SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Format formatter = new SimpleDateFormat("E, dd MMM yyyy");
            Date d = null;
            try {
                d = formate.parse(string_date);
                String date_claender = formatter.format(d.getTime());
                holder.tv_posting_date_of_post.setText(date_claender);

            } catch (ParseException e) {
                e.printStackTrace();
            }


        } else {
            holder.tv_posting_date_of_post.setVisibility(View.GONE);
        }

        if (item.getUser_image() != null) {
            imageLoader.displayImage(item.getUser_image(), holder.user_Image, options);
        } else {
            holder.user_Image.setBackgroundResource(R.drawable.no_media);
        }

        if (item.getContact_no().length() == 10) {
            holder.bt_connect_with_seller.setVisibility(View.VISIBLE);


        } else {
            holder.bt_connect_with_seller.setVisibility(View.INVISIBLE);
        }

        holder.bt_connect_with_seller.setOnClickListener(this);
        holder.bt_ff_shell_shave.setOnClickListener(this);
        holder.bt_ff_shell_comments.setOnClickListener(this);
        holder.bt_ff_shell_like.setOnClickListener(this);
        holder.bt_ff_shell_whatapp.setOnClickListener(this);
        holder.bt_ff_shell_share.setOnClickListener(this);
        holder.ll_ff_shell_complain.setOnClickListener(this);
        holder.bt_ff_shell_map.setOnClickListener(this);
        holder.checkBox_ff_shell.setOnClickListener(this);
        holder.user_Image.setOnClickListener(this);
        holder.tv_ff_shell_username.setOnClickListener(this);
        holder.ll_like_click.setOnClickListener(this);
        holder.ll_comment_click.setOnClickListener(this);

        return convertView;
    }

    @Override
    public void onClick(View v) {
        if (v.getTag() != null) {
            pos = (Integer) v.getTag();
            postID = list.get(pos).getPost_id();
        }

        switch (v.getId()) {

            case R.id.ll_ff_shell_complain:
                new BottomSheet.Builder(frag.getActivity()).title("Take action on post").sheet(R.menu.menu_complain).listener(new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case R.id.wrong_category:
                                //Toast.makeText(frag.getActivity(), "wrong_category", Toast.LENGTH_LONG).show();
                                if (list.get(pos).getWrongcat_status().equals("0")) {
                                    NetworkIntentService.startAbuseReportServivce(frag.getActivity(), AppPreferences.getInstance(frag.getActivity()).getUserId(), list.get(pos).user_fname, list.get(pos).getUser_lname(), postID, list.get(pos).user_id, AppConstant.WRONGCATEGORY);
                                } else {
                                    Utility.showToast(frag.getActivity(), "Already Reported");
                                }
                                break;
                            /*case R.id.dont_show:
                                Toast.makeText(frag.getActivity(), "dont_show", Toast.LENGTH_LONG).show();

                                break;*/
                            case R.id.roport:
                                if (list.get(pos).getWrongcat_status().equals("0")) {
                                    NetworkIntentService.startAbuseReportServivce(frag.getActivity(), AppPreferences.getInstance(frag.getActivity()).getUserId(), list.get(pos).user_fname, list.get(pos).getUser_lname(), postID, list.get(pos).user_id, AppConstant.ABUSE);
                                } else {
                                    Utility.showToast(frag.getActivity(), "Already Reported");
                                }
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
               /* try {

                    // set the data

                    String uri = "tel:" + item.getContact_no();
                    Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(uri));
                    frag.startActivity(callIntent);

                } catch (Exception e) {

                    Toast.makeText(frag.getActivity(), "Your call has failed...", Toast.LENGTH_LONG).show();
                    e.printStackTrace();

                }*/
                new BottomSheet.Builder(frag.getActivity()).title("Take action on post").sheet(R.menu.menu_main_call).listener(new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case R.id.user_call:

                                try {

                                    // set the data
                                    if(item.getContact_no().length()!=0) {
                                        String uri = "tel:" + item.getContact_no();
                                        Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(uri));
                                        frag.startActivity(callIntent);
                                    }

                                } catch (Exception e) {

                                    Toast.makeText(frag.getActivity(), "Your call has failed...", Toast.LENGTH_LONG).show();
                                    e.printStackTrace();

                                }

                                break;

                            case R.id.user_message:
                                try {

                                    // set the data
                                    if(item.getContact_no().length()!=0) {
                                        frag.startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", item.getContact_no(), null)));
                                    }

                                } catch (Exception e) {

                                    Toast.makeText(frag.getActivity(), "Your call has failed...", Toast.LENGTH_LONG).show();
                                    e.printStackTrace();

                                }

                                break;
                            case R.id.cacel:
                                Toast.makeText(frag.getActivity(), "Cancel", Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                                break;
                        }
                    }
                }).show();


                break;

            case R.id.imageView:

                if (!AppPreferences.getInstance(frag.getActivity()).getUserId().equals(list.get(pos).user_id)) {
                    ProfileActivity.startActivity(frag.getActivity(), list.get(pos).user_id);
                }
                break;

            case R.id.checkBox_ff_shell:
                // Toast.makeText(frag.getActivity(), "You click connect sellet", Toast.LENGTH_LONG).show();
                String followerID = list.get(pos).getUser_id();
                NetworkIntentService.startFollowIntentServicen(frag.getActivity(), AppPreferences.getInstance(frag.getActivity()).getUserId(), followerID, pos, AppConstant.GETPOST_FOLLOW);
                break;
            case R.id.bt_ff_shell_shave:
                //  Toast.makeText(frag.getActivity(), "You click connect shave", Toast.LENGTH_LONG).show();
                savePost(AppPreferences.getInstance(frag.getActivity()).getUserId(), list.get(pos).getPost_id(), pos);
                break;
            case R.id.bt_ff_shell_comments:
                CommentActivity.startActvity(frag.getActivity(), postID, null);
                break;
            case R.id.bt_ff_shell_like:
                // Toast.makeText(frag.getActivity(), "You click connect like", Toast.LENGTH_LONG).show();
                  NetworkIntentService.startLikeIntentService(frag.getActivity(), AppPreferences.getInstance(frag.getActivity()).getUserId(), postID, pos, AppConstant.GETPOST_LIKE);

//                Bundle bundle = new Bundle();
//                bundle.putInt("functionType", AppConstant.GETPOST_LIKE);
//                bundle.putString("postID", postID);
//                bundle.putInt("pos", pos);
//                frag.getActivity().getLoaderManager().initLoader(LoaderID.LIKE_FOLLOW, bundle, likeFollowCallback);

                break;
            case R.id.bt_ff_shell_whatapp:
                item = list.get(pos);
                try {
                    GetXMLTask task = new GetXMLTask();
                    if (item.getPost_image() != null || item.getPost_image() != "") {
                        task.execute(url_post + item.getPost_image());
                    }

                } catch (Exception e) {
                    e.getMessage();
                }

                break;
            case R.id.bt_ff_shell_share:
                item = list.get(pos);
                try {
                    NormalGetXMLTask task = new NormalGetXMLTask();
                    if (item.getPost_image() != null || item.getPost_image() != "") {
                        task.execute(url_post + item.getPost_image());
                    }

                } catch (Exception e) {
                    e.getMessage();
                }

              /*  Toast.makeText(frag.getActivity(), "You click connect share", Toast.LENGTH_LONG).show();
                Intent txtIntent = new Intent(android.content.Intent.ACTION_SEND);
                txtIntent.setType("text/plain");

                txtIntent.putExtra(android.content.Intent.EXTRA_TEXT, item.getPost_description());
                frag.startActivity(Intent.createChooser(txtIntent, "Share"));*/
                break;
            case R.id.bt_ff_shell_map:
                Toast.makeText(frag.getActivity(), "You click connect Map", Toast.LENGTH_LONG).show();
                break;
            case R.id.tv_ff_shell_username:
                if (!AppPreferences.getInstance(frag.getActivity()).getUserId().equals(list.get(pos).user_id)) {
                    ProfileActivity.startActivity(frag.getActivity(), list.get(pos).user_id);
                }

                break;

            case R.id.ll_like_click:
                LikeListActivity.startActvity(frag.getActivity(), postID);
                break;

            case R.id.ll_comment_click:
                CommentActivity.startActvity(frag.getActivity(), postID, null);
                break;

        }
    }

    static class ViewHolder {
        TextView tv_ff_shell_username;
        TextView tv_ff_shell_time, tv_posting_date_of_post;
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
        //   ImageButton bt_ff_shell_complain;
        ImageButton bt_ff_shell_map;
        ImageView user_Image;
        LinearLayout ll_like_click;
        LinearLayout ll_comment_click;
        CirclePageIndicator circleIndicator;
        ViewPager viewPager;
        LinearLayout ll_view_pager;
        LinearLayout ll_ff_shell_complain;
    }

    private void savePost(String userID, String postID, int pos) {
        if (isFromSearch) {
            // ((SearchResultFragment) frag).savePost(AppConstant.METHOD_SAVEPOST, userID, postID, pos);
        } else {
            ((BaseFragment) frag).savePost(AppConstant.METHOD_SAVEPOST, userID, postID, pos);
        }

    }


    public BroadcastReceiver observeLikeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(NetworkIntentService.BROADCAST_LIKE_ACTION)) {

                String totalLike = intent.getStringExtra(NetworkIntentService.BROADCAST_EXTRA_LIKE);
                String like_dislike_status = intent.getStringExtra(NetworkIntentService.BROADCAST_EXTRA_LIKE_DISLIKE_STATUS);
                int position = intent.getIntExtra(NetworkIntentService.BROADCAST_EXTRA_POSITION, 0);
                try {
                    ParentPostBean item = list.get(position);
                    item.setLike_dislike_status(Integer.parseInt(like_dislike_status));
                    item.setTotal_like(Integer.parseInt(totalLike));
                    holder.bt_ff_shell_like.setImageResource(R.drawable.heart_active);

                } catch (Exception e) {
                    e.getMessage();
                }

                notifyDataSetChanged();

            } else if (action.equals(NetworkIntentService.BROADCAST_FOLLOW_ACTION)) {

                String totalFollow = intent.getStringExtra(NetworkIntentService.BROADCAST_EXTRA_TOTAL_FOLLOWE);
                String is_followng = intent.getStringExtra(NetworkIntentService.BROADCAST_EXTRA_IS_FOLLOWING);
                int position = intent.getIntExtra(NetworkIntentService.BROADCAST_EXTRA_POSITION, 0);
                try {
                    ParentPostBean item = list.get(position);
                    item.setIs_following(Integer.parseInt(is_followng));
                } catch (Exception e) {
                    e.getMessage();
                }

                notifyDataSetChanged();
            } else if (action.equals(NetworkIntentService.BROADCAST_FOLLOW_ERROR)) {
                String error = intent.getStringExtra(NetworkIntentService.BROADCAST_EXTRA_ERROR);
                Utility.showToast(frag.getActivity(), error);
            }
        }
    };


    private class NormalGetXMLTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... urls) {
            Bitmap map = null;
            for (String url : urls) {
                map = downloadImage(url);
            }
            return map;
        }

        // Sets the Bitmap returned by doInBackground
        @Override
        protected void onPostExecute(Bitmap result) {

            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, item.getPost_description());
            intent.setType("text/plain");
            String path = MediaStore.Images.Media.insertImage(frag.getActivity().getContentResolver(), result, item.getPost_title() + "" + item.getPost_description(), null);
            if (path == null) {
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, item.getPost_title() + "" + item.getPost_description());
                frag.startActivity(Intent.createChooser(intent, "Share"));
            } else {
                Uri uri = Uri.parse(path);
                intent.putExtra(Intent.EXTRA_STREAM, uri);
                intent.setType("image/*");
                intent.setType("image/jpeg");
                frag.startActivity(Intent.createChooser(intent, "Share"));


            }
        }

        // Creates Bitmap from InputStream and returns it
        private Bitmap downloadImage(String url) {
            Bitmap bitmap = null;
            InputStream stream = null;
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inSampleSize = 1;

            try {
                stream = getHttpConnection(url);
                bitmap = BitmapFactory.
                        decodeStream(stream, null, bmOptions);
                stream.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return bitmap;
        }

        // Makes HttpURLConnection and returns InputStream
        private InputStream getHttpConnection(String urlString)
                throws IOException {
            InputStream stream = null;
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();

            try {
                HttpURLConnection httpConnection = (HttpURLConnection) connection;
                httpConnection.setRequestMethod("GET");
                httpConnection.connect();

                if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    stream = httpConnection.getInputStream();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return stream;
        }
    }


    private class GetXMLTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... urls) {
            Bitmap map = null;
            for (String url : urls) {
                map = downloadImage(url);
            }
            return map;
        }

        // Sets the Bitmap returned by doInBackground
        @Override
        protected void onPostExecute(Bitmap result) {
            // imageView.setImageBitmap(result);
            PackageManager pm = frag.getActivity().getPackageManager();
            try {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, item.getPost_title() + "" + item.getPost_description());
                intent.setType("text/plain");

                String path = MediaStore.Images.Media.insertImage(frag.getActivity().getContentResolver(), result, item.getPost_title() + "" + item.getPost_description(), null);
                if (path == null) {

                    PackageInfo info = pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
                    intent.setType("text/plain");
                    intent.setPackage("com.whatsapp");
                    intent.putExtra(Intent.EXTRA_TEXT, item.getPost_title() + "" + item.getPost_description());
                    try {
                        frag.startActivity(intent);
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(frag.getActivity(), "WhatsApp not Installed", Toast.LENGTH_SHORT)
                                .show();
                    }

                } else {

                    PackageInfo info = pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);

                    Uri uri = Uri.parse(path);
                    intent.putExtra(Intent.EXTRA_STREAM, uri);
                    intent.setType("image/*");

                    intent.setType("image/jpeg");
                    intent.setPackage("com.whatsapp");
                    frag.startActivity(intent);
                }

            } catch (PackageManager.NameNotFoundException e) {
                Toast.makeText(frag.getActivity(), "WhatsApp not Installed", Toast.LENGTH_SHORT)
                        .show();
            }
        }

        // Creates Bitmap from InputStream and returns it
        private Bitmap downloadImage(String url) {
            Bitmap bitmap = null;
            InputStream stream = null;
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inSampleSize = 1;

            try {
                stream = getHttpConnection(url);
                bitmap = BitmapFactory.
                        decodeStream(stream, null, bmOptions);
                stream.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return bitmap;
        }

        // Makes HttpURLConnection and returns InputStream
        private InputStream getHttpConnection(String urlString)
                throws IOException {
            InputStream stream = null;
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();

            try {
                HttpURLConnection httpConnection = (HttpURLConnection) connection;
                httpConnection.setRequestMethod("GET");
                httpConnection.connect();

                if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    stream = httpConnection.getInputStream();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return stream;
        }
    }


    LoaderManager.LoaderCallbacks<TaskResponse<GetPostLikeFollowResponse>> likeFollowCallback =
            new LoaderManager.LoaderCallbacks<TaskResponse<GetPostLikeFollowResponse>>() {

                @Override
                public Loader<TaskResponse<GetPostLikeFollowResponse>> onCreateLoader(int id, Bundle args) {

                    int functionType = args.getInt("functionType", 0);
                    String postID = args.getString("postID");
                    int position = args.getInt("pos", 0);

                    if (functionType == AppConstant.GETPOST_LIKE) {

                        //  NetworkIntentService.startLikeIntentService(frag.getActivity(), AppPreferences.getInstance(frag.getActivity()).getUserId(), postID, pos, AppConstant.GETPOST_LIKE);

                        Loader loader = new LikeFollowLoader(frag.getActivity(), AppPreferences.getInstance(frag.getActivity()).getUserId(), postID, position, AppConstant.GETPOST_LIKE);
                        return loader;
                    } else {
                        //  Loader loader = new LikeFollowLoader(mRequestBean, AppConstant.METHOD_GETCOMMENT, appPreferences.getUserId(), args.getString("postID"));
                        //  return loader;
                    }

                    return null;
                }

                @Override
                public void onLoadFinished(android.content.Loader<TaskResponse<GetPostLikeFollowResponse>> loader, TaskResponse<GetPostLikeFollowResponse> data) {
                    if (loader instanceof LikeFollowLoader) {
                        if (data.error != null) {
                            Utility.showToast(frag.getActivity(), data.error.toString());
                        } else {
                            int functionType = data.data.functionType;
                            switch (functionType) {
                                case AppConstant.GETPOST_LIKE:

                                    String totalLike = data.data.list.get(0).total_like;
                                    String like_dislike_status = data.data.list.get(0).like_dislike_status;
                                    int position = data.data.itemPosition;

                                    try {
                                        ParentPostBean item = list.get(position);
                                        item.setLike_dislike_status(Integer.parseInt(like_dislike_status));
                                        item.setTotal_like(Integer.parseInt(totalLike));
                                        holder.bt_ff_shell_like.setImageResource(R.drawable.heart_active);
                                        //notifyDataSetChanged();

                                    } catch (Exception e) {
                                        e.getMessage();
                                    }
                                    break;

                                case AppConstant.GETPOST_FOLLOW:
                                    break;
                            }
                        }
                    }
                }

                @Override
                public void onLoaderReset(android.content.Loader<TaskResponse<GetPostLikeFollowResponse>> loader) {
                }
            };


}
