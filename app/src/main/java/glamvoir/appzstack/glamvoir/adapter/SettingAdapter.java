package glamvoir.appzstack.glamvoir.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import glamvoir.appzstack.glamvoir.R;


/**
 * Created by gajendra on 19/5/15.
 */
public class SettingAdapter extends BaseAdapter {

    private Context mContext;
    private String[] settingsLable;
    MyItemClickListener clickListener;

    public SettingAdapter(Context context, String[] strings, MyItemClickListener clickListener) {
        this.settingsLable = strings;
        this.mContext = context;
        this.clickListener=clickListener;
    }

    @Override
    public int getCount() {
        return settingsLable.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        if (convertView == null) {

            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_row_setting, parent, false);
            holder = new ViewHolder();

            holder.mLable = (TextView) convertView.findViewById(R.id.settinglable);
            holder.mMainLayout = (LinearLayout) convertView.findViewById(R.id.settinglayout);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (position % 2 == 0) {
            holder.mMainLayout.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));

        } else {
            holder.mMainLayout.setBackgroundColor(mContext.getResources().getColor(R.color.tabBarBackground));
        }

        holder.mLable.setText(settingsLable[position]);

        holder.mMainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clickListener!=null)
                    clickListener.onClick(position);
            }
        });
        return convertView;
    }

    private class ViewHolder {
        protected TextView mLable;
        protected LinearLayout mMainLayout;
    }

    public interface MyItemClickListener{
        public void onClick(int position);
    }
}

