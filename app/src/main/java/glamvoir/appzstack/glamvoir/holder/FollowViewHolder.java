package glamvoir.appzstack.glamvoir.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import glamvoir.appzstack.glamvoir.R;
import glamvoir.appzstack.glamvoir.adapter.LoadableListAdapter;


/**
 * Created by gajendran on 20/8/15.
 */
public class FollowViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView title;
    public TextView nophoto;
    private ImageView userImage;
    private TextView userName,followers,followingBy_Action;
    private ImageButton bt_shell_following_action;

    public LoadableListAdapter.MyViewHolderClick mListener;

    public FollowViewHolder(View view, LoadableListAdapter.MyViewHolderClick mListener) {
        super(view);
        this.mListener = mListener;
        view.setOnClickListener(this);

        title = (TextView) view.findViewById(R.id.title);
        userImage= (ImageView) view.findViewById(R.id.iv_shell_userimage);
        userName= (TextView) view.findViewById(R.id.tv_shell_user_name);
        followers= (TextView) view.findViewById(R.id.tv_shell_follwer);
        bt_shell_following_action= (ImageButton) view.findViewById(R.id.bt_shell_action);
        followingBy_Action= (TextView) view.findViewById(R.id.tv_shell_action_name);


        //chatButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        mListener._OnClick(view, getPosition());
        switch (view.getId()){
            case R.id.bt_shell_action:

        }
    }
}


