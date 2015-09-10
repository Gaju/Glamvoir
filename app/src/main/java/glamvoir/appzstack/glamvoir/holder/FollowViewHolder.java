package glamvoir.appzstack.glamvoir.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import glamvoir.appzstack.glamvoir.R;
import glamvoir.appzstack.glamvoir.adapter.LoadableListAdapter;


/**
 * Created by gajendran on 20/8/15.
 */
public class FollowViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView title;
    public TextView nophoto;
    public LoadableListAdapter.MyViewHolderClick mListener;

    public FollowViewHolder(View view, LoadableListAdapter.MyViewHolderClick mListener) {
        super(view);
        this.mListener = mListener;
        view.setOnClickListener(this);

        title = (TextView) view.findViewById(R.id.title);

        //chatButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        mListener._OnClick(view, getPosition());
    }
}


