package glamvoir.appzstack.glamvoir.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import glamvoir.appzstack.glamvoir.R;
import glamvoir.appzstack.glamvoir.adapter.Action;
import glamvoir.appzstack.glamvoir.adapter.AddStoryImageAdapter;
import glamvoir.appzstack.glamvoir.adapter.CustomSpinnerAdapter;
import glamvoir.appzstack.glamvoir.constant.AppConstant;
import glamvoir.appzstack.glamvoir.model.net.request.RequestBean;

/**
 * Created by jaim on 9/9/2015.
 */
public class AddStory extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, View.OnClickListener, RadioGroup.OnCheckedChangeListener{


    private static final String TAG = "AddStory";

    // Variable for storing current date and time
    private int mYear, mMonth, mDay, mHour, mMinute;
    String userCurrentDate;
    String userCooseDate,finalDate;


   // GridView gridGallery;
    Handler handler;
   // GalleryAdapter adapter;
    ImageLoader imageLoader;
    Bundle bundle;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    RadioButton rdbMale, rdbFemale,rblboth;
    RadioGroup rgGender;
    AddStoryImageAdapter addStoryImageAdapter;
    private ListView lv_addstory;
    String result=null;
    private RequestBean mRequestBean;
    private Toolbar toolbar;
    Spinner dropDownMenu,dropCitySpinner;
    TextView selected_text;
    private LinearLayout lltool;
    ImageButton galleryImages,time_calender,calender_item;
    ArrayList<CustomGallery> dataT;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!isGooglePlayServicesAvailable()) {
            finish();
        }

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        setContentView(R.layout.my_post);//layout_mypost

        mRequestBean = new RequestBean();
        mRequestBean.setLoader(true);
        mRequestBean.setActivity(this);
        mRequestBean.setLoader(true);

        bundle = getIntent().getExtras();

        initImageLoader();
        initViews();
        getToolbar(toolbar);
        initListener();



        addItemsToSpinner();

        addItemsCityToSpinner();
        CurrentDate();

        //    init();
    }

    private void CurrentDate() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        userCurrentDate = df.format(c.getTime());
        Toast.makeText(this, userCurrentDate, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart fired ..............");
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop fired ..............");
        mGoogleApiClient.disconnect();
        Log.d(TAG, "isConnected ...............: " + mGoogleApiClient.isConnected());
    }


    @Override
    public void onConnected(Bundle bundle) {
        Log.d(TAG, "onConnected - isConnected ...............: " + mGoogleApiClient.isConnected());
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        Log.d(TAG, "onConnected - isConnected ...............: " + mGoogleApiClient.isConnected());

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "Connection failed: " + connectionResult.toString());
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mGoogleApiClient.isConnected()) {
            //  startLocationUpdates();
            Log.d(TAG, "Location update resumed .....................");
        }
    }

    /**
     * check whether device have google play store or not
     *
     * @return
     */
    private boolean isGooglePlayServicesAvailable() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS == status) {
            return true;
        } else {
            GooglePlayServicesUtil.getErrorDialog(status, this, 0).show();
            return false;
        }
    }

    private double getLatitude() {
        if (mLastLocation != null) {
            return mLastLocation.getLatitude();
        }
        return 0;
    }

    private double getLongitude() {
        if (mLastLocation != null) {
            return mLastLocation.getLongitude();
        }
        return 0;
    }


   // add items into spinner dynamically
    public void addItemsToSpinner() {

        ArrayList<String> list= new ArrayList<String>();
        list.add("FASHION & LIFESTYLE");
        list.add("FOOD & PLACES");
        list.add("MUSIC & GIGS");
        list.add("INTERESTS");

        // Custom ArrayAdapter with spinner item layout to set popup background

        CustomSpinnerAdapter spinAdapter = new CustomSpinnerAdapter(
                getApplicationContext(), list);

        dropDownMenu.setAdapter(spinAdapter);



    }




    public void addItemsCityToSpinner() {

        ArrayList<String> list= new ArrayList<String>();
        list.add("All");
        list.add("Delhi");
        list.add("Mumbai");
        list.add("Kolkata");
        list.add("Gurgaon");

        // Custom ArrayAdapter with spinner item layout to set popup background

        CustomSpinnerAdapter spinAdapter = new CustomSpinnerAdapter(
                getApplicationContext(), list);

        dropCitySpinner.setAdapter(spinAdapter);

      /*  dropCitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapter, View v,
                                       int position, long id) {
                // On selecting a spinner item
                String item = adapter.getItemAtPosition(position).toString();


                Toast.makeText(getApplicationContext(), "Selected  : " + item,
                        Toast.LENGTH_LONG).show();


            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
*/
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
        calender_item.setOnClickListener(this);
        time_calender.setOnClickListener(this);
        rgGender.setOnCheckedChangeListener(this);
        dropDownMenu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapter, View view, int position, long id) {

                String item = adapter.getItemAtPosition(position).toString();

                // Showing selected spinner item
                if (item.equalsIgnoreCase("FASHION & LIFESTYLE")){
                    selected_text.setText(item);
                    Toast.makeText(getApplicationContext(), "Selected  : " + item,
                            Toast.LENGTH_LONG).show();
                }
                else if (item.equalsIgnoreCase("FOOD & PLACES")){
                    selected_text.setText(item);
                    Toast.makeText(getApplicationContext(), "Selected  : " + item,
                            Toast.LENGTH_LONG).show();
                }

                else if (item.equalsIgnoreCase("MUSIC & GIGS")){
                    selected_text.setText(item);
                    Toast.makeText(getApplicationContext(), "Selected  : " + item,
                            Toast.LENGTH_LONG).show();
                }

                else if (item.equalsIgnoreCase("INTERESTS")){
                    selected_text.setText(item);
                    Toast.makeText(getApplicationContext(), "Selected  : " + item,
                            Toast.LENGTH_LONG).show();
                }
                else if (item.equalsIgnoreCase("FLEA MARKET")){
                    selected_text.setText(item);
                    Toast.makeText(getApplicationContext(), "Selected  : " + item,
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
     //   dropCitySpinner.setOnItemSelectedListener(this);

        dropCitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapter, View v,
                                       int position, long id) {
                // On selecting a spinner item
                String item = adapter.getItemAtPosition(position).toString();


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
     * initialize all views
     */
    private void initViews() {
        handler = new Handler();
        lltool = (LinearLayout) findViewById(R.id.lltool);
        galleryImages = (ImageButton) findViewById(R.id.button3);
        lv_addstory= (ListView) findViewById(R.id.lv_addstory);
        lv_addstory.setFastScrollEnabled(true);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        dropDownMenu = (Spinner) findViewById(R.id.spinner_nav);
        dropCitySpinner = (Spinner) findViewById(R.id.spinner);
        lltool.setVisibility(View.VISIBLE);
        selected_text = (TextView) findViewById(R.id.selected_text);
        calender_item=(ImageButton)findViewById(R.id.calender_item);
        time_calender=(ImageButton)findViewById(R.id.time_calender);

        rgGender = (RadioGroup) findViewById(R.id.rgGender);
        rdbMale = (RadioButton) findViewById(R.id.rdbMale);
        rdbFemale = (RadioButton) findViewById(R.id.rdbMale);
        rblboth=(RadioButton)findViewById(R.id.rdbboth);

        if (bundle != null) {
            selected_text.setVisibility(View.VISIBLE);
            selected_text.setText(bundle.getString("CATOGERYNAME"));
            selected_text.setSelected(true);



        }
        dropDownMenu.setSelection(getIndex(dropDownMenu, selected_text.getText().toString()));

        //gridGallery = (GridView) findViewById(R.id.gridGallery);
       // gridGallery.setFastScrollEnabled(true);
        addStoryImageAdapter= new AddStoryImageAdapter(this,imageLoader,AddStory.this);
        lv_addstory.setAdapter(addStoryImageAdapter);
      // adapter = new GalleryAdapter(AddStory.this, imageLoader);
       // adapter.setMultiplePick(false);
      //  gridGallery.setAdapter(adapter);
    }

    private int getIndex(Spinner spinner, String myString){

        int index = 0;

        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).equals(myString)){
                index = i;
            }
        }
        return index;
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
        switch (v.getId()) {
            case R.id.button3:
                Intent i = new Intent(AddStory.this, CustomGalleryActivity.class);
                i.setAction(Action.ACTION_MULTIPLE_PICK);
                startActivityForResult(i, 200);
                break;
            case R.id.calender_item:
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dpd = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                Toast.makeText(AddStory.this, dayOfMonth + "-" + (monthOfYear + 1) + "-" + year, Toast.LENGTH_LONG).show();
                                userCooseDate = year+ "/" + (monthOfYear + 1) + "/" + dayOfMonth;
                                DataComprision(userCooseDate,userCurrentDate);
                                if (result.equals("1")||result.equals("0")){
                                    finalDate=year+ "/" + (monthOfYear + 1) + "/" + dayOfMonth;
                                }
                                else {
                                    Toast.makeText(AddStory.this,"Please give current and future event date",Toast.LENGTH_LONG).show();
                                }

                            }
                        }, mYear, mMonth, mDay);
                dpd.show();

                break;
            case R.id.time_calender:
                TimePickerDialog tpd = new TimePickerDialog(this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                Toast.makeText(AddStory.this, hourOfDay + ":" + minute, Toast.LENGTH_LONG).show();


                            }
                        }, mHour, mMinute, false);
                tpd.show();
                break;

        }
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        // TODO Auto-generated method stub
        switch (checkedId) {

            case R.id.rdbboth:
                // AppPreferences.getInstance(this).setGender(GENDER_MALE);
                Toast.makeText(AddStory.this,"Selected both",Toast.LENGTH_LONG).show();
                break;

            case R.id.rdbMale:
               // AppPreferences.getInstance(this).setGender(GENDER_MALE);
                Toast.makeText(AddStory.this,"Selected Men",Toast.LENGTH_LONG).show();

                break;
            case R.id.rdbFemale:
             //   AppPreferences.getInstance(this).setGender(GENDER_FEMALE);
                Toast.makeText(AddStory.this,"Selected Women",Toast.LENGTH_LONG).show();

                break;

            default:


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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 200 && resultCode == Activity.RESULT_OK) {
            String[] all_path = data.getStringArrayExtra("all_path");

             dataT = new ArrayList<CustomGallery>();

            for (String string : all_path) {
                CustomGallery item = new CustomGallery();
                item.sdcardPath = string;

                dataT.add(item);
            }
            addStoryImageAdapter.addAll(dataT);
           // list.addAll(dataT);
        }
    }

   public String DataComprision(String userCooseDate, String userCurrentDate) {

       try{

           SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

           Date date1 = sdf.parse(userCooseDate);
           Date date2 = sdf.parse(userCurrentDate);

           System.out.println(sdf.format(date1));
           System.out.println(sdf.format(date2));

           if(date1.after(date2)){
               System.out.println("Date1 is after Date2");
               result="1";

           }

           if(date1.before(date2)){
               System.out.println("Date1 is before Date2");
               result="-1";
           }

           if(date1.equals(date2)){
               System.out.println("Date1 is equal Date2");
               result="0";
           }

       }catch( ParseException ex){
           ex.printStackTrace();
       }

       return result;
   }

}
