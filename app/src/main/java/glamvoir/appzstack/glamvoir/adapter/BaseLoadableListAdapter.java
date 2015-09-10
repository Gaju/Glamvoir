package glamvoir.appzstack.glamvoir.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import glamvoir.appzstack.glamvoir.R;

/**
 * Created by gajendran on 21/8/15.
 */
public abstract class BaseLoadableListAdapter<T> extends LoadableListAdapter implements View.OnClickListener {

    public BaseLoadableListAdapter(Context context, ArrayList items) {
        super(context, items);
    }

    private static class FooterViewHolder extends RecyclerView.ViewHolder {
        public View loadingFooterLayout;
        public View connectionErrorLayout;

        public View retryButton;

        public FooterViewHolder(View itemView) {
            super(itemView);

            connectionErrorLayout = itemView.findViewById(R.id.connectionErrorFooter);
            loadingFooterLayout = itemView.findViewById(R.id.loadingFooter);
            retryButton = itemView.findViewById(R.id.retryButton);
        }
    }

    @Override
    public void onBindFooterView(LoadingFooterType type, RecyclerView.ViewHolder holder, int position) {

        if (type == LoadingFooterType.Loading) {

            ((FooterViewHolder) holder).connectionErrorLayout.setVisibility(View.GONE);
            ((FooterViewHolder) holder).loadingFooterLayout.setVisibility(View.VISIBLE);

        } else if (type == LoadingFooterType.ConnectionProblem) {

            ((FooterViewHolder) holder).loadingFooterLayout.setVisibility(View.GONE);
            ((FooterViewHolder) holder).connectionErrorLayout.setVisibility(View.VISIBLE);
            ((FooterViewHolder) holder).retryButton.setOnClickListener(this);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateFooterViewHolder(ViewGroup parent, int viewType) {

        View layoutView = mInflater.inflate(R.layout.listitem_ad_list_footer, parent, false);
        FooterViewHolder holder = new FooterViewHolder(layoutView);
        return holder;
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.retryButton) {
            onRetryPressed();
        }
    }
}
