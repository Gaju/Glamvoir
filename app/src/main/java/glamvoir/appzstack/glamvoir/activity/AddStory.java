package glamvoir.appzstack.glamvoir.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;

import glamvoir.appzstack.glamvoir.R;
import glamvoir.appzstack.glamvoir.adapter.Action;
import glamvoir.appzstack.glamvoir.adapter.CustomSpinnerAdapter;
import glamvoir.appzstack.glamvoir.adapter.GalleryAdapter;
import glamvoir.appzstack.glamvoir.constant.AppConstant;
import glamvoir.appzstack.glamvoir.model.net.request.RequestBean;

/**
 * Created by jaim on 9/9/2015.
 */
public class AddStory extends AppCompatActivity implements View.OnClickListener {


    public static void startActivity(Context context) {
        Intent intent = new Intent(context, AddStory.class);
        intent.putExtra("ParentClassName", context.getClass().getSimpleName());
        context.startActivity(intent);
    }
    GridView gridGallery;
    Handler handler;
    GalleryAdapter adapter;
    ImageLoader imageLoader;


    private RequestBean mRequestBean;
    private Toolbar toolbar;
    Spinner dropDownMenu;
    private LinearLayout lltool;
    ImageButton galleryImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_post);//layout_mypost

        mRequestBean = new RequestBean();
        mRequestBean.setLoader(true);
        mRequestBean.setActivity(this);
        mRequestBean.setLoader(true);

        //initialize all views
        initImageLoader();
        initViews();

        initListener();

        getToolbar(toolbar);
        addItemsToSpinner();

    //    init();
    }

    // add items into spinner dynamically
    public void addItemsToSpinner() {

        ArrayList<String> list = new ArrayList<String>();
        list.add("All");
        list.add("FASHION AND LIFESTYLE");
        list.add("FOOD");
        list.add("MUSIC AND GIGS");
        list.add("INTERST");

        // Custom ArrayAdapter with spinner item layout to set popup background

        CustomSpinnerAdapter spinAdapter = new CustomSpinnerAdapter(
                getApplicationContext(), list);



        // Default ArrayAdapter with default spinner item layout, getting some
        // view rendering problem in lollypop device, need to test in other
        // devices

		/*
		 * ArrayAdapter<String> spinAdapter = new ArrayAdapter<String>(this,
		 * android.R.layout.simple_spinner_item, list);
		 * spinAdapter.setDropDownViewResource
		 * (android.R.layout.simple_spinner_dropdown_item);
		 */

        dropDownMenu.setAdapter(spinAdapter);

        dropDownMenu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapter, View v,
                                       int position, long id) {
                // On selecting a spinner item
                String item = adapter.getItemAtPosition(position).toString();

                // Showing selected spinner item
                Toast.makeText(getApplicationContext(), "Selected  : " + item,
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

    }




    /**
     * customize the toolbar
     *
     * @param toolbar : pass the toolbar reference
     */
    private void getToolbar(Toolbar toolbar) {

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

    }

    /**
     * initialize all views listeners
     */
    private void initListener() {
        galleryImages.setOnClickListener(this);

    }


    /**
     * initialize all views
     */
    private void initViews() {
        handler = new Handler();
        lltool= (LinearLayout) findViewById(R.id.lltool);
        galleryImages= (ImageButton) findViewById(R.id.button3);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        dropDownMenu= (Spinner) findViewById(R.id.spinner_nav);
        lltool.setVisibility(View.VISIBLE);
        gridGallery = (GridView) findViewById(R.id.gridGallery);
        gridGallery.setFastScrollEnabled(true);
        adapter = new GalleryAdapter(AddStory.this, imageLoader);
        adapter.setMultiplePick(false);
        gridGallery.setAdapter(adapter);



    }

    @Override
    public Intent getSupportParentActivityIntent() {
        Intent parentIntent = getIntent();
        String className = parentIntent.getStringExtra("ParentClassName"); //getting the parent class name
        Intent newIntent = null;
        try {
            //you need to define the class with package name //
            newIntent = new Intent(AddStory.this, Class.forName(AppConstant.PACKAGE + className));

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return newIntent;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        //MenuView.ItemView post;
        //post=(MenuView.ItemView)menu.findItem(R.id.action_uplaod);
        //post.setTitle("POST");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_uplaod) {

            Toast.makeText(getApplicationContext(), "upload Clicked",
                    Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button3:
                Intent i = new Intent(AddStory.this,CustomGalleryActivity.class);
                i.setAction(Action.ACTION_MULTIPLE_PICK);
                startActivityForResult(i, 200);
                break;


        }
    }



    private void initImageLoader() {
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisc().imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .bitmapConfig(Bitmap.Config.RGB_565).build();
        ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(
                this).defaultDisplayImageOptions(defaultOptions).memoryCache(
                new WeakMemoryCache());

        ImageLoaderConfiguration config = builder.build();
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(config);
    }

  /*  private void init() {


        gridGallery = (GridView) findViewById(R.id.gridGallery);
        gridGallery.setFastScrollEnabled(true);
        adapter = new GalleryAdapter(getApplicationContext(), imageLoader);
        adapter.setMultiplePick(false);
        gridGallery.setAdapter(adapter);



    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

      if (requestCode == 200 && resultCode == Activity.RESULT_OK) {
            String[] all_path = data.getStringArrayExtra("all_path");

            ArrayList<CustomGallery> dataT = new ArrayList<CustomGallery>();

            for (String string : all_path) {
                CustomGallery item = new CustomGallery();
                item.sdcardPath = string;

                dataT.add(item);
            }
        adapter.addAll(dataT);
        }
    }
}
