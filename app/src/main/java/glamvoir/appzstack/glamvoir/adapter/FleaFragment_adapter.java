package glamvoir.appzstack.glamvoir.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.viewpagerindicator.CirclePageIndicator;

import glamvoir.appzstack.glamvoir.R;

/**
 * Created by jaim on 9/12/2015.
 */
public class FleaFragment_adapter extends BaseAdapter implements View.OnClickListener {

    private static LayoutInflater inflater=null;
    ImagePagerAdapter adapter;
    Context context;
    public FleaFragment_adapter(FragmentActivity activity) {
        this.context=activity;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);



    }

    @Override
    public int getCount() {
        return 10;
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
        ViewHolder holder  = null;
        if(convertView==null){
            convertView = inflater.inflate(R.layout.feed_or_felia_market_shell, null);
            holder = new ViewHolder();
            final ViewPager viewPager = (ViewPager)convertView.findViewById(R.id.view_pager);
           // adapter = new ImagePagerAdapter(context);
            viewPager.setAdapter(adapter);
            final CirclePageIndicator circleIndicator = (CirclePageIndicator)convertView.findViewById(R.id.indicator);
            circleIndicator.setViewPager(viewPager);
            holder.tv_ff_shell_username             = (TextView)convertView.findViewById(R.id.tv_ff_shell_username);
            holder.tv_ff_shell_time                 = (TextView)convertView.findViewById(R.id.tv_ff_shell_time);
            holder.tv_actiontext_checkBox_ff_shell  = (TextView)convertView.findViewById(R.id.tv_actiontext_checkBox_ff_shell);
            holder.tv_ff_shell_like_count           = (TextView)convertView.findViewById(R.id.tv_ff_shell_like_count);
            holder.tv_ff_shell_comment_count        = (TextView)convertView.findViewById(R.id.tv_ff_shell_comment_count);
            holder.checkBox_ff_shell                = (CheckBox)convertView.findViewById(R.id.checkBox_ff_shell);
            holder.bt_connect_with_seller           = (Button)convertView.findViewById(R.id.bt_connect_with_seller);
            holder.bt_ff_shell_shave                = (ImageButton)convertView.findViewById(R.id.bt_ff_shell_shave);
            holder.bt_ff_shell_comments             = (ImageButton)convertView.findViewById(R.id.bt_ff_shell_comments);
            holder.bt_ff_shell_like                 = (ImageButton)convertView.findViewById(R.id.bt_ff_shell_like);
            holder.bt_ff_shell_whatapp              = (ImageButton)convertView.findViewById(R.id.bt_ff_shell_whatapp);
            holder.bt_ff_shell_share                = (ImageButton)convertView.findViewById(R.id.bt_ff_shell_share);
            convertView.setTag(holder);

        }
        holder = (ViewHolder)convertView.getTag();
        holder.bt_connect_with_seller.setOnClickListener(this);
        holder.bt_ff_shell_shave.setOnClickListener(this);
        holder.bt_ff_shell_comments.setOnClickListener(this);
        holder.bt_ff_shell_like.setOnClickListener(this);
        holder.bt_ff_shell_whatapp.setOnClickListener(this);
        holder.bt_ff_shell_share.setOnClickListener(this);




        return convertView;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
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
        }



    }


    static class  ViewHolder{

        TextView          tv_ff_shell_username;
        TextView          tv_ff_shell_time;
        TextView          tv_actiontext_checkBox_ff_shell;
        TextView          tv_ff_shell_like_count;
        TextView          tv_ff_shell_comment_count;
        CheckBox          checkBox_ff_shell;
        Button            bt_connect_with_seller;
        ImageButton       bt_ff_shell_shave;
        ImageButton       bt_ff_shell_comments;
        ImageButton       bt_ff_shell_like;
        ImageButton       bt_ff_shell_whatapp;
        ImageButton       bt_ff_shell_share;


    }
}