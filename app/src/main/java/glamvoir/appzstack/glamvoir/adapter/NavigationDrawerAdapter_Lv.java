package glamvoir.appzstack.glamvoir.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import glamvoir.appzstack.glamvoir.R;
import glamvoir.appzstack.glamvoir.model.NavDrawerItem;

/**
 * Created by jaim on 8/10/2015.
 */
public class NavigationDrawerAdapter_Lv extends BaseAdapter {

    List<NavDrawerItem> data = Collections.emptyList();

    private LayoutInflater inflater;
    private Context context;

    //private static final int TYPE_HEADER = 0;  // Declaring Variable to Understand which View is being worked on
    // IF the view under inflation and population is header or Item
   // private static final int TYPE_ITEM = 1;

    private String mNavTitles[]; // String Array to store the passed titles Value from MainActivity.java
    private int mIcons[];       // Int Array to store the passed icons resource value from MainActivity.java

 //   private String name;        //String Resource for header View Name
  //  private int profile;        //int Resource for header view profile picture
  //  private String email;       //String Resource for header view email


    public NavigationDrawerAdapter_Lv(Context context, String Titles[], int Icons[]/*, String Name, String Email, int Profile*/) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        // titles, icons, name, email, profile pic are passed from the main activity as we
      this.mNavTitles = Titles;                //have seen earlier
       this.mIcons = Icons;
     //   name = Name;
     //   email = Email;
     //   profile = Profile;
        //here we assign those passed values to the values we declared here
        //in adapter
    }



    @Override
    public int getCount() {

        return  mNavTitles.length ;
    }

    @Override
    public Object getItem(int position) {


        return null;
    }

    @Override
    public long getItemId(int position) {


        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
       if (convertView==null){
           convertView= inflater.inflate(R.layout.nav_drawer_row, null);
           holder = new ViewHolder(convertView);
           convertView.setTag(holder);
       } else {
           holder = (ViewHolder) convertView.getTag();
       }
          // view.setTag(holder);
        holder.textView.setText(mNavTitles[position]); // Setting the Text with the array of our Titles
        holder.imageView.setImageResource(mIcons[position]);// Settimg the image with array of our icons

        return convertView;
    }


    class ViewHolder{

        TextView textView;
        ImageView imageView;
        /*ImageView img_alert;
        RoundedImageView profile;
        TextView Name;
        TextView email;*/
        public ViewHolder(View view) {
            textView = (TextView)view.findViewById(R.id.title);
            imageView=(ImageView)view.findViewById(R.id.image);

        }

    }



}
