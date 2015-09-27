package glamvoir.appzstack.glamvoir.adapter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;

import glamvoir.appzstack.glamvoir.R;
import glamvoir.appzstack.glamvoir.activity.FFSPActivity;
import glamvoir.appzstack.glamvoir.activity.FollowersActivity;
import glamvoir.appzstack.glamvoir.activity.FollowingActivity;
import glamvoir.appzstack.glamvoir.activity.MyPostActivity;
import glamvoir.appzstack.glamvoir.activity.MysaveActivity;
import glamvoir.appzstack.glamvoir.apppreference.AppPreferences;
import glamvoir.appzstack.glamvoir.helpers.ImageLoaderInitializer;
import glamvoir.appzstack.glamvoir.holder.FFSP_ViewHolder;
import glamvoir.appzstack.glamvoir.intentservice.ObserveFFSPIntentService;
import glamvoir.appzstack.glamvoir.model.FFSP_Response;

/**
 * Created by gajendran on 19/8/15.
 */
public class FFSP_Adapter extends BaseLoadableListAdapter<FFSP_Response> {

    protected DisplayImageOptions imageOptions;
    protected ImageLoader imageLoader;
    protected HashMap<String, ArrayList<String>> adImages;
    private int mPosition;

    //RecyclerViewOnItemClick onItemClick;

    public FFSP_Adapter(Context context, ArrayList<FFSP_Response> items) {
        super(context, items);
        //   this.onItemClick = onItemClick;
        adImages = new HashMap<String, ArrayList<String>>();
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        ImageLoaderInitializer.initImageLoaderIfNotInited(context);
        imageOptions = ImageLoaderInitializer.getDisplayImageOptionWithFade();

        imageLoader = ImageLoader.getInstance();
    }


    @Override
    public RecyclerView.ViewHolder onCreateBasicItemViewHolder(ViewGroup parent, int viewType) {
        View v = null;

        v = mInflater.inflate(R.layout.item_row, parent, false);
        FFSP_ViewHolder holder = new FFSP_ViewHolder(v, new MyViewHolderClick() {

            @Override
            public void _OnClick(View view, int position) {

                mPosition = position;
                if (view.getId() == R.id.icon) {
                    performAction(view);
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindBasicItemView(RecyclerView.ViewHolder holder1, int position) {

        FFSP_ViewHolder holder = ((FFSP_ViewHolder) holder1);
        FFSP_Response.SingleFollow singleFollow = (FFSP_Response.SingleFollow) getItem(position);

        holder.icon.setTag(singleFollow);

        if (singleFollow.user_fname != null && singleFollow.user_lname != null)
            holder.title.setText(singleFollow.user_fname + " " + singleFollow.user_lname);

        if (context instanceof FollowingActivity) {

            if (Integer.parseInt(singleFollow.total_following) < 1)
                holder.subtitle.setText(singleFollow.total_following + " follower");
            else holder.subtitle.setText(singleFollow.total_following + " followers");


            if (singleFollow.is_following.equalsIgnoreCase("1")) {
                holder.icon.setImageResource(R.drawable.following);
                holder.icon_title.setText("Following");
            } else {
                holder.icon.setImageResource(R.drawable.followers);
                holder.icon_title.setText("Follower");
            }

        } else if (context instanceof FollowersActivity) {

            if (Integer.parseInt(singleFollow.total_following) < 1)
                holder.subtitle.setText(singleFollow.total_following + " follower");
            else holder.subtitle.setText(singleFollow.total_following + " followers");


            if (singleFollow.is_following.equalsIgnoreCase("1")) {
                holder.icon.setImageResource(R.drawable.following);
                holder.icon_title.setText("Following");
            } else {
                holder.icon.setImageResource(R.drawable.followers);
                holder.icon_title.setText("Follower");
            }

        } else if (context instanceof MysaveActivity) {

            //holder.subtitle.setVisibility(View.INVISIBLE);
            holder.subtitle.setText(singleFollow.post_description);
            holder.icon.setImageResource(R.drawable.delete);
            holder.icon_title.setText("Delete");
        } else if (context instanceof MyPostActivity) {

            //holder.subtitle.setVisibility(View.INVISIBLE);
            holder.subtitle.setText(singleFollow.post_description);
            holder.icon.setImageResource(R.drawable.delete);
            holder.icon_title.setText("Delete");
        }
    }

    private void performAction(View view) {

        AppPreferences appPreferences = new AppPreferences(context);
        if (context instanceof MysaveActivity) {
            FFSP_Response.SingleFollow singleFollow = (FFSP_Response.SingleFollow) view.getTag();
            ((FFSPActivity) context).removeItem(mPosition, singleFollow.user_id, singleFollow.post_id);

        } else if (context instanceof FollowingActivity) {

            FFSP_Response.SingleFollow singleFollow = (FFSP_Response.SingleFollow) view.getTag();
            ((FFSPActivity) context).followFollower(singleFollow.user_id, mPosition);

        } else if (context instanceof FollowersActivity) {

            FFSP_Response.SingleFollow singleFollow = (FFSP_Response.SingleFollow) view.getTag();
            ((FFSPActivity) context).followFollower(singleFollow.user_id, mPosition);
        }
    }

    public BroadcastReceiver observeFollowReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(ObserveFFSPIntentService.BROADCAST_ACTION_OBSERVED)) {
                String isFollowing = intent.getStringExtra(ObserveFFSPIntentService.BROADCAST_EXTRA_ISFOLLOW_ADDED);
                String totalCount = intent.getStringExtra(ObserveFFSPIntentService.BROADCAST_EXTRA_TOTAL_FOLLOWER);
                int position = intent.getIntExtra(ObserveFFSPIntentService.INTENT_ARG_FLAG, 0);

                //    (FFSP_Response.SingleFollow) items.get(position).

                FFSP_Response.SingleFollow singleFollow = (FFSP_Response.SingleFollow) getItem(position);
                singleFollow.is_following = isFollowing;
                singleFollow.total_following = totalCount;
                notifyDataSetChanged();
            }
        }
    };
}
