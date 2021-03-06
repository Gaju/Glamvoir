package glamvoir.appzstack.glamvoir.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;
import java.util.Arrays;

import glamvoir.appzstack.glamvoir.R;
import glamvoir.appzstack.glamvoir.activity.CustomGallery;

/**
 * Created by jaim on 10/26/2015.
 */
public class AddStoryImageAdapter extends BaseAdapter implements View.OnClickListener {

    private Context mContext;
    private LayoutInflater infalter;
    ImageLoader imageLoader;
    private ArrayList<CustomGallery> data = new ArrayList<CustomGallery>();
    private Activity parentActivity;
    ViewHolder holder;
    private String[] tempHeading = new String[5];
    private String[] tempDescription = new String[5];

    public AddStoryImageAdapter(Context c, ImageLoader imageLoader, Activity parentActivity) {

        infalter = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContext = c;
        this.imageLoader = imageLoader;
        this.parentActivity = parentActivity;
        clearCache();
    }

    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        return data.size();
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link LayoutInflater#inflate(int, ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position    The position of the item within the adapter's data set of the item whose view
     *                    we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *                    is non-null and of an appropriate type before using. If it is not possible to convert
     *                    this view to display the correct data, this method can create a new view.
     *                    Heterogeneous lists can specify their number of view types, so that this View is
     *                    always of the right type (see {@link #getViewTypeCount()} and
     *                    {@link #getItemViewType(int)}).
     * @param parent      The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        if (convertView == null) {

            convertView = infalter.inflate(R.layout.addstory_imageupload_shell, null);
            holder = new ViewHolder();

            holder.ll_heading_description = (LinearLayout) convertView.findViewById(R.id.ll_heading_description);
            holder.tv_add_heading = (TextView) convertView.findViewById(R.id.addstory_heading);
            holder.tv_add_description = (TextView) convertView.findViewById(R.id.addstory_description);
            holder.upload_Image = (ImageView) convertView.findViewById(R.id.addshell_images);
            holder.remove_images= (ImageButton) convertView.findViewById(R.id.remove_images);
            holder.upload_Image.setScaleType(ImageView.ScaleType.FIT_CENTER);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.ll_heading_description.setTag(position);
  try {
      holder.tv_add_heading.setText(tempHeading[position]);
  }
  catch (Exception e){
   e.getMessage();
  }
        try {
            holder.tv_add_description.setText(tempDescription[position]);
        }
        catch (Exception e){
            e.getMessage();
        }



        try {

            imageLoader.displayImage("file://" + data.get(position).sdcardPath,
                    holder.upload_Image, new SimpleImageLoadingListener() {
                        @Override
                        public void onLoadingStarted(String imageUri, View view) {
                            holder.upload_Image.setImageResource(R.drawable.no_media);
                            super.onLoadingStarted(imageUri, view);
                        }

                        @Override
                        public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                            // Empty implementation
                            //Utility.showToast(mContext, failReason.toString());
                        }

                        @Override
                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                            // Empty implementation
                            //Utility.showToast(mContext, "uri = " + imageUri);
                            holder.upload_Image.setImageBitmap(loadedImage);
                        }

                        @Override
                        public void onLoadingCancelled(String imageUri, View view) {
                            // Empty implementation
                            // Utility.showToast(mContext, "canccel");
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }

        // holder.tv_add_heading.setOnClickListener(this);
        // holder.tv_add_description.setOnClickListener(this);
        holder.ll_heading_description.setOnClickListener(this);

        return convertView;

    }


    public void addAll(ArrayList<CustomGallery> files) {
        try {
            this.data.clear();
            this.data.addAll(files);

//            tempHeading = new String[data.size()];
//            tempDescription = new String[data.size()];

            for (int i = 0; i < data.size(); i++) {
                if (tempHeading[i] != "" && tempHeading[i] != null) {

                } else {
                    tempHeading[i] = "";
                    tempDescription[i] = "";
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        notifyDataSetChanged();
    }

    public boolean isTitleAdded() {
//        int counter = 0;
//        if (tempHeading != null) {
//            for (int i = 0; i < tempHeading.length; i++) {
//                if (tempHeading[i] != null && tempHeading[i] == "" ) {
//                    counter = counter + i;
//                }
//            }

        if (Arrays.asList(tempHeading).contains("")) {
            return false;
        } else {
            return true;
        }
//            if (counter > 0) {
//                return false;
//            } else {
//                return true;
//            }

        // return false;
    }

    public String[] getTitles() {
        if (tempHeading != null)
            return tempHeading;
        return null;
    }

    public String[] getDescription() {
        if (tempDescription != null)
            return tempDescription;
        return null;
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        final int pos = (Integer) v.getTag();

        switch (v.getId()) {
            case R.id.ll_heading_description:

                LayoutInflater inflater = parentActivity.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.add_story_alertdialog_head_des, null);

                AlertDialog.Builder builder = new AlertDialog.Builder(parentActivity);
                builder.setView(dialogView);
                final EditText heading = (EditText) dialogView.findViewById(R.id.heading);
                final EditText description = (EditText) dialogView.findViewById(R.id.description);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        tempHeading[pos] = heading.getText().toString();
                        tempDescription[pos] = description.getText().toString();
                        notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });
                builder.create();
                builder.show();


                break;
        }
    }

    static class ViewHolder {
        TextView tv_add_heading;
        TextView tv_add_description;
        ImageView upload_Image;
        LinearLayout ll_heading_description;
        ImageButton remove_images;
        int ref;
    }

    public void clearCache() {
        imageLoader.clearDiscCache();
        imageLoader.clearMemoryCache();
    }
}
