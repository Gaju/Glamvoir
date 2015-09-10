package glamvoir.appzstack.glamvoir.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;

import glamvoir.appzstack.glamvoir.R;
import glamvoir.appzstack.glamvoir.helpers.ImageLoaderInitializer;
import glamvoir.appzstack.glamvoir.holder.FollowViewHolder;
import glamvoir.appzstack.glamvoir.model.FollowResponse;

/**
 * Created by gajendran on 19/8/15.
 */
public class AdAdapterWithGallery extends BaseLoadableListAdapter<FollowResponse> {

    protected Fragment fragment;
    protected Boolean removeOnUnobserve = false;
    protected DisplayImageOptions imageOptions;
    protected ImageLoader imageLoader;
    protected HashMap<String, ArrayList<String>> adImages;

    //RecyclerViewOnItemClick onItemClick;

    public AdAdapterWithGallery(Context context, ArrayList<FollowResponse> items, Fragment fragment, int listType) {
        super(context, items);
        this.fragment = fragment;
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

        v = mInflater.inflate(R.layout.following_drawer_shell, parent, false);

        FollowViewHolder holder = new FollowViewHolder(v, new MyViewHolderClick() {

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

        final FollowViewHolder holder = ((FollowViewHolder) holder1);
        FollowResponse ad = (FollowResponse) getItem(position);

    }


    private void doFollow() {

    }
}
