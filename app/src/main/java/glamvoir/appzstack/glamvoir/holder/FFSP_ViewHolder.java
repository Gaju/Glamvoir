package glamvoir.appzstack.glamvoir.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import glamvoir.appzstack.glamvoir.R;
import glamvoir.appzstack.glamvoir.adapter.LoadableListAdapter;


/**
 * Created by gajendran on 20/8/15.
 */
public class FFSP_ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView title;
    public TextView icon_title;
    public ImageView icon;
    public ImageView user_img;
    public TextView subtitle, followers;
    public LinearLayout ll_shell_follow;

    public LoadableListAdapter.MyViewHolderClick mListener;

    public FFSP_ViewHolder(View view, LoadableListAdapter.MyViewHolderClick mListener) {
        super(view);
        this.mListener = mListener;
        view.setOnClickListener(this);

        title = (TextView) view.findViewById(R.id.title);
        icon = (ImageView) view.findViewById(R.id.icon);
        user_img = (ImageView) view.findViewById(R.id.user_img);
        subtitle = (TextView) view.findViewById(R.id.subtitle);
        icon_title = (TextView) view.findViewById(R.id.icon_title);
        ll_shell_follow = (LinearLayout) view.findViewById(R.id.ll_shell_follow);
        //bt_shell_following_action = (ImageButton) view.findViewById(R.id.bt_shell_action);
        //followingBy_Action = (TextView) view.findViewById(R.id.tv_shell_action_name);

      //  user_img.setOnClickListener(this);
        icon.setOnClickListener(this);
      //  title.setOnClickListener(this);
        ll_shell_follow.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        mListener._OnClick(view, getPosition());
    }
}


