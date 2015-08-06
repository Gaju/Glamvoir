package glamvoir.appzstack.glamvoir.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import glamvoir.appzstack.glamvoir.R;
import glamvoir.appzstack.glamvoir.model.NavDrawerItem;


public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.ViewHolder> {
    List<NavDrawerItem> data = Collections.emptyList();

    private LayoutInflater inflater;
    private Context context;

    private static final int TYPE_HEADER = 0;  // Declaring Variable to Understand which View is being worked on
    // IF the view under inflation and population is header or Item
    private static final int TYPE_ITEM = 1;

    private String mNavTitles[]; // String Array to store the passed titles Value from MainActivity.java
    private int mIcons[];       // Int Array to store the passed icons resource value from MainActivity.java

    private String name;        //String Resource for header View Name
    private int profile;        //int Resource for header view profile picture
    private String email;       //String Resource for header view email


    // Creating a ViewHolder which extends the RecyclerView View Holder
    // ViewHolder are used to to store the inflated views in order to recycle them


    public NavigationDrawerAdapter(Context context, String Titles[], int Icons[], String Name, String Email, int Profile) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        // titles, icons, name, email, profile pic are passed from the main activity as we
        mNavTitles = Titles;                //have seen earlier
        mIcons = Icons;
        name = Name;
        email = Email;
        profile = Profile;
         //here we assign those passed values to the values we declared here
        //in adapter


    }

    public void delete(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    @Override


    public  NavigationDrawerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {



        if (viewType == TYPE_ITEM) {
            View v = inflater.from(parent.getContext()).inflate(R.layout.nav_drawer_row,parent,false); //Inflating the layout

            ViewHolder vhItem = new ViewHolder(v,viewType); //Creating ViewHolder and passing the object of type view

            return vhItem; // Returning the created object

            //inflate your layout and pass it to view holder

        } else if (viewType == TYPE_HEADER) {

            View v = inflater.from(parent.getContext()).inflate(R.layout.header,parent,false); //Inflating the layout

            ViewHolder vhHeader = new ViewHolder(v,viewType); //Creating ViewHolder and passing the object of type view

            return vhHeader; //returning the object created


        }
        return null;
    }



    @Override
    public void onBindViewHolder(NavigationDrawerAdapter.ViewHolder holder, int position) {
        if(holder.Holderid ==1) {                              // as the list view is going to be called after the header view so we decrement the
            // position by 1 and pass it to the holder while setting the text and image
            holder.textView.setText(mNavTitles[position - 1]); // Setting the Text with the array of our Titles
            holder.imageView.setImageResource(mIcons[position - 1]);// Settimg the image with array of our icons
        }
        else{

            holder.profile.setImageResource(profile);           // Similarly we set the resources for header view
            holder.Name.setText(name);
            holder.email.setText(email);
        }

    }

    @Override
    public int getItemCount() {


        return  mNavTitles.length+1;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        int Holderid;

        TextView textView;
        ImageView imageView;
        ImageView profile;
        TextView Name;
        TextView email;

        public ViewHolder(View itemView,int ViewType) {                 // Creating ViewHolder Constructor with View and viewType As a parameter
            super(itemView);


            // Here we set the appropriate view in accordance with the the view type as passed when the holder object is created

            if(ViewType == TYPE_ITEM) {
                textView = (TextView) itemView.findViewById(R.id.title); // Creating TextView object with the id of textView from item_row.xml
                imageView = (ImageView) itemView.findViewById(R.id.image);// Creating ImageView object with the id of ImageView from item_row.xml
                Holderid = 1;                                               // setting holder id as 1 as the object being populated are of type item row
            }
            else{


                Name = (TextView) itemView.findViewById(R.id.name);         // Creating Text View object from header.xml for name
                email = (TextView) itemView.findViewById(R.id.email);       // Creating Text View object from header.xml for email
                profile = (ImageView) itemView.findViewById(R.id.circleView);// Creating Image view object from header.xml for profile pic
                Holderid = 0;                                                // Setting holder id = 0 as the object being populated are of type header view
            }
        }
    }



    // Witht the following method we check what type of view is being passed
    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;

        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }
}
