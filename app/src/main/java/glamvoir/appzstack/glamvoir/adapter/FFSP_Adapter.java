package glamvoir.appzstack.glamvoir.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import glamvoir.appzstack.glamvoir.R;
import glamvoir.appzstack.glamvoir.activity.FollowersActivity;
import glamvoir.appzstack.glamvoir.activity.FollowingActivity;
import glamvoir.appzstack.glamvoir.activity.MysaveActivity;
import glamvoir.appzstack.glamvoir.helpers.ImageLoaderInitializer;
import glamvoir.appzstack.glamvoir.holder.FFSP_ViewHolder;
import glamvoir.appzstack.glamvoir.model.FFSP_Response;

/**
 * Created by gajendran on 19/8/15.
 */
public class FFSP_Adapter extends BaseLoadableListAdapter<FFSP_Response> {

    protected DisplayImageOptions imageOptions;
    protected ImageLoader imageLoader;
    protected HashMap<String, ArrayList<String>> adImages;

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

//                if (view.getId() == R.id.chatButton) {
//                    doFollow();
//                } else {
//                    if (onItemClick != null)
//                        onItemClick.onItemClick(view, position);
//                }
            }
        });
        return holder;
    }

    @Override
    public void onBindBasicItemView(RecyclerView.ViewHolder holder1, int position) {

        FFSP_ViewHolder holder = ((FFSP_ViewHolder) holder1);
        FFSP_Response.SingleFollow singleFollow = (FFSP_Response.SingleFollow) getItem(position);

        if (singleFollow.user_fname != null && singleFollow.user_lname != null)
            holder.title.setText(singleFollow.user_fname + " " + singleFollow.user_lname);

        if (context instanceof FollowingActivity) {

            if (Integer.parseInt(singleFollow.total_following) < 1)
                holder.subtitle.setText(singleFollow.total_following + "follower");
            else holder.subtitle.setText(singleFollow.total_following + "followers");


            holder.icon.setImageResource(R.drawable.following);
            holder.icon_title.setText("Following");
        } else if (context instanceof FollowersActivity) {

            if (Integer.parseInt(singleFollow.total_following) < 1)
                holder.subtitle.setText(singleFollow.total_following + "follower");
            else holder.subtitle.setText(singleFollow.total_following + "followers");


            holder.icon.setImageResource(R.drawable.followers);
            holder.icon_title.setText("Follower");
        } else if (context instanceof MysaveActivity) {

            holder.subtitle.setVisibility(View.INVISIBLE);
            holder.icon.setImageResource(R.drawable.delete);
            holder.icon_title.setText("Delete");
        }
    }


    private void doFollow() {

    }
}
