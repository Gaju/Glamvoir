package glamvoir.appzstack.glamvoir.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by gajendran on 21/8/15.
 */
public abstract class LoadableListAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
//    private static final int TYPE_HEADER = Integer.MIN_VALUE;
//    private static final int TYPE_FOOTER = Integer.MIN_VALUE + 1;
//    private static final int TYPE_ADAPTEE_OFFSET = 2;

    public static interface MyViewHolderClick {
        public void _OnClick(View view, int position);
    }

    public static final int TYPE_AD_ITEM = 0;
    public static final int TYPE_LOADING_ITEM = 1;

    protected ArrayList<T> items;

    protected LayoutInflater mInflater;

    protected Context context;

    protected LoadingFooterType footerType = LoadingFooterType.None;

    //private RetryListener retryListener;

    public enum LoadingFooterType {
        None, Loading, ConnectionProblem
    }

    public LoadableListAdapter(Context context, ArrayList<T> items) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.items = items;
    }

    public void setLoadingFooterType(LoadingFooterType type) {
        footerType = type;
    }

    public LoadingFooterType getLoadingFooterType() {
        return footerType;
    }

    public void saveAdapterState(Bundle outState) {
        outState.putInt("LoadableListAdapter_loadingFooterType", footerType.ordinal());
    }

    public void restoreAdapterState(Bundle savedInstanceState) {
        int enumPos = savedInstanceState.getInt("LoadableListAdapter_loadingFooterType", 0);
        footerType = LoadingFooterType.values()[enumPos];
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        if (viewType == TYPE_HEADER) {
//            return onCreateHeaderViewHolder(parent, viewType);
//        } else if (viewType == TYPE_FOOTER) {
//            return onCreateFooterViewHolder(parent, viewType);
//        }
//        return onCreateBasicItemViewHolder(parent, viewType - TYPE_ADAPTEE_OFFSET);
//

        if (viewType == TYPE_LOADING_ITEM) {
            return onCreateFooterViewHolder(parent, viewType);
        }
        return onCreateBasicItemViewHolder(parent, viewType);

    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        if (position == 0 && holder.getItemViewType() == TYPE_HEADER) {
//            onBindHeaderView(holder, position);
//        } else if (position == getBasicItemCount() && holder.getItemViewType() == TYPE_FOOTER) {
//            onBindFooterView(holder, position);
//        } else {
//            onBindBasicItemView(holder, position - (useHeader() ? 1 : 0));
//        }

        int type = getItemViewType(position);
        if (type == TYPE_AD_ITEM) {
            onBindBasicItemView(holder, position);
        } else {
            onBindFooterView(footerType, holder, position);
        }
    }

    @Override
    public int getItemCount() {
//        int itemCount = getBasicItemCount();
//        if (useHeader()) {
//            itemCount += 1;
//        }
//        if (useFooter()) {
//            itemCount += 1;
//        }
//        return itemCount;

        if (footerType != LoadingFooterType.None) {
            return items.size() + 1;
        } else {
            return items.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
//        if (position == 0 && useHeader()) {
//            return TYPE_HEADER;
//        }
//        if (position == getBasicItemCount() && useFooter()) {
//            return TYPE_FOOTER;
//        }
//        if (getBasicItemType(position) >= Integer.MAX_VALUE - TYPE_ADAPTEE_OFFSET) {
//            new IllegalStateException("HeaderRecyclerViewAdapter offsets your BasicItemType by " + TYPE_ADAPTEE_OFFSET + ".");
//        }
//        return getBasicItemType(position) + TYPE_ADAPTEE_OFFSET;

        if (position < items.size()) {
            return TYPE_AD_ITEM;
        } else {
            return TYPE_LOADING_ITEM;
        }
    }

    public T getItem(int position) {
        if (position < items.size()) {
            return items.get(position);
        } else {
            return (T) new Object();
        }
    }

    public abstract RecyclerView.ViewHolder onCreateFooterViewHolder(ViewGroup parent, int viewType);

    public abstract void onBindFooterView(LoadingFooterType type, RecyclerView.ViewHolder holder, int position);

    public abstract RecyclerView.ViewHolder onCreateBasicItemViewHolder(ViewGroup parent, int viewType);

    public abstract void onBindBasicItemView(RecyclerView.ViewHolder holder, int position);

//    public void setRetryListener(RetryListener listener) {
//        this.retryListener = listener;
//    }
//
//    public void onRetryPressed() {
//        if (retryListener != null) {
//            retryListener.onRetry();
//        }
//    }



    /*
     * Inserting a new item at the head of the list. This uses a specialized
     * RecyclerView method, notifyItemRemoved(), to trigger any enabled item
     * animations in addition to updating the view.
     */
    public void removeItem(int position) {
        if (position >= items.size()) return;

        items.remove(position);
        notifyItemRemoved(position);
    }

}