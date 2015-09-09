//package glamvoir.appzstack.glamvoir.adapter;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import java.util.ArrayList;
//
//import glamvoir.appzstack.glamvoir.R;
//
///**
// * Created by Gajendran on 05-09-2015.
// */
//public class Follow_LikeAdapter extends ArrayAdapter {
//
//    private Context mContext;
//    private String[] settingsLable;
//
//     List<T> items;
//
//    public Follow_LikeAdapter(Context context, String[] strings) {
//        this.settingsLable = strings;
//        this.mContext = context;
//    }
//
//    @Override
//    public int getCount() {
//        return settingsLable.length;
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return null;
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return 0;
//    }
//
//    @Override
//    public View getView(final int position, View convertView, ViewGroup parent) {
//
//        ViewHolder holder = null;
//
//        if (convertView == null) {
//
//            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_row_setting, parent, false);
//            holder = new ViewHolder();
//
//            holder.mLable = (TextView) convertView.findViewById(R.id.settinglable);
//            holder.mMainLayout = (LinearLayout) convertView.findViewById(R.id.settinglayout);
//            convertView.setTag(holder);
//
//        } else {
//            holder = (ViewHolder) convertView.getTag();
//        }
//
//        if (position % 2 == 0) {
//            holder.mMainLayout.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
//
//        } else {
//            holder.mMainLayout.setBackgroundColor(mContext.getResources().getColor(R.color.tabBarBackground));
//        }
//
//        holder.mLable.setText(settingsLable[position]);
//
//
//        return convertView;
//    }
//
//    private class ViewHolder {
//        protected TextView mLable;
//        protected LinearLayout mMainLayout;
//    }
//}