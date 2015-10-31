package glamvoir.appzstack.glamvoir.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.io.File;
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
import glamvoir.appzstack.glamvoir.helpers.Utility;
import glamvoir.appzstack.glamvoir.model.net.request.RequestBean;

/**
 * Created by jaim on 9/9/2015.
 */
public class AddStory extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, View.OnClickListener, RadioGroup.OnCheckedChangeListener, CompoundButton.OnCheckedChangeListener {

    private static final String TAG = "AddStory";
    private static final int CAPTURE_IMAGE_CAMERA = 1;
    private static final String DIRECTORY_NAME = "Glamvoir";
    private static final String IMAGE_EXTENSION = ".jpg";
    private static final String IMAGE_PREFIX = "IMG";
    private static final int MAX_PHOTOS = 5;

    private static Uri picUri = null;

    // Variable for storing current date and time
    private int mYear, mMonth, mDay, mHour, mMinute;
    String userCurrentDate;
    String userCooseDate, finalDate;

    ArrayList<CustomGallery> dataT = new ArrayList<CustomGallery>();

    GridView gridGallery;
    Handler handler;
    // GalleryAdapter adapter;
    ImageLoader imageLoader;
    Bundle bundle;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
   LinearLayout ll_text_upload,ll_image_upload;

    AddStoryImageAdapter addStoryImageAdapter;
    private ListView lv_addstory;
    String result = null;
    private RequestBean mRequestBean;
    private Toolbar toolbar;
    Spinner dropDownMenu,dropCitySpinner;
    private ToggleButton switching_image_to_text;
    TextView selected_text;
    private LinearLayout lltool;
    ImageButton galleryImages, time_calender, calender_item, cameraImages;
    RadioButton rdbMale, rdbFemale,rblboth;
    RadioGroup rgGender;

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

        initListener();

        getToolbar(toolbar);
        addItemsToSpinner();
        addItemsCityToSpinner();
        CurrentDate();

        //    init();
    }

    private void CurrentDate() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        userCurrentDate = df.format(c.getTime());
       // Toast.makeText(this, userCurrentDate, Toast.LENGTH_SHORT).show();
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

        ArrayList<String> list = new ArrayList<String>();
        list.add("FASHION AND LIFESTYLE");
        list.add("FOOD AND PLACE");
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
//                Toast.makeText(getApplicationContext(), "Selected  : " + item,
//                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

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
        switching_image_to_text.setOnCheckedChangeListener(this);
    }


    /**
     * initialize all views
     */
    private void initViews() {
        handler = new Handler();
        lltool = (LinearLayout) findViewById(R.id.lltool);
        galleryImages = (ImageButton) findViewById(R.id.button3);
        cameraImages = (ImageButton) findViewById(R.id.button2);
        switching_image_to_text = (ToggleButton) findViewById(R.id.switching_image_to_text);
        ll_text_upload= (LinearLayout) findViewById(R.id.ll_text_upload);
        ll_image_upload = (LinearLayout) findViewById(R.id.ll_image_upload);
        rgGender = (RadioGroup) findViewById(R.id.rgGender);
        rdbMale = (RadioButton) findViewById(R.id.rdbMale);
        rdbFemale = (RadioButton) findViewById(R.id.rdbMale);
        rblboth=(RadioButton)findViewById(R.id.rdbboth);
        dropCitySpinner = (Spinner) findViewById(R.id.spinner);
        cameraImages.setOnClickListener(this);
        lv_addstory = (ListView) findViewById(R.id.lv_addstory);
        lv_addstory.setFastScrollEnabled(true);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        dropDownMenu = (Spinner) findViewById(R.id.spinner_nav);
        lltool.setVisibility(View.VISIBLE);
        selected_text = (TextView) findViewById(R.id.selected_text);
        calender_item = (ImageButton) findViewById(R.id.calender_item);
        time_calender = (ImageButton) findViewById(R.id.time_calender);
        if (bundle != null) {
            selected_text.setVisibility(View.VISIBLE);
            selected_text.setText(bundle.getString("CATOGERYNAME"));
            selected_text.setSelected(true);
        }

        //gridGallery = (GridView) findViewById(R.id.gridGallery);
        // gridGallery.setFastScrollEnabled(true);
        addStoryImageAdapter = new AddStoryImageAdapter(this, imageLoader, AddStory.this);
        lv_addstory.setAdapter(addStoryImageAdapter);
        // adapter = new GalleryAdapter(AddStory.this, imageLoader);
        // adapter.setMultiplePick(false);
        //  gridGallery.setAdapter(adapter);
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
        getMenuInflater().inflate(R.menu.menu_likes, menu);
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
        if (id == R.id.likes) {
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
                                userCooseDate = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;
                                DataComprision(userCooseDate, userCurrentDate);
                                if (result.equals("1") || result.equals("0")) {
                                    finalDate = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;
                                } else {
                                    Toast.makeText(AddStory.this, "Please give current and future event date", Toast.LENGTH_LONG).show();
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

            case R.id.button2:

                if (Utility.checkAvailable()) {
                    Intent i1 = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    File file = getOutputMediaFile();
                    picUri = Uri.fromFile(file); // create
                    i1.putExtra(MediaStore.EXTRA_OUTPUT, picUri); // set the image file
                    startActivityForResult(i1, CAPTURE_IMAGE_CAMERA);
                } else {
                    Utility.showToast(AddStory.this, "No External Storage");
                }
                break;
        }
    }


    /**
     * Create a File for saving an image
     */
    private File getOutputMediaFile() {

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), DIRECTORY_NAME);

        /**Create the storage directory if it does not exist*/
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        File mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                IMAGE_PREFIX + Utility.getDateTime(false) + IMAGE_EXTENSION);
        return mediaFile;
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

            for (String string : all_path) {
                CustomGallery item = new CustomGallery();
                item.sdcardPath = string;
                dataT.add(item);
            }

        } else if (requestCode == CAPTURE_IMAGE_CAMERA) {
            if (dataT.size() <= MAX_PHOTOS) {
                CustomGallery item = new CustomGallery();
                if (picUri != null) {
                    String modifyURL = picUri.toString().replace("file://", "");
                    item.sdcardPath = modifyURL;
                    dataT.add(item);
                }
            } else {
                Utility.showToast(AddStory.this, "can't able to add more than 5 photos");
            }
        }
        addStoryImageAdapter.addAll(dataT);
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




    public String DataComprision(String userCooseDate, String userCurrentDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            Date date1 = sdf.parse(userCooseDate);
            Date date2 = sdf.parse(userCurrentDate);

            if (date1.after(date2)) {
                System.out.println("Date1 is after Date2");
                result = "1";
            }

            if (date1.before(date2)) {
                System.out.println("Date1 is before Date2");
                result = "-1";
            }

            if (date1.equals(date2)) {
                System.out.println("Date1 is equal Date2");
                result = "0";
            }

        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        return result;
    }


    /**
     * Called when the checked state of a compound button has changed.
     *
     * @param buttonView The compound button view whose state has changed.
     * @param isChecked  The new checked state of buttonView.
     */
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            // The toggle is enabled
            galleryImages .setClickable(false);
            galleryImages .setFocusable(false);
            cameraImages .setClickable(false);
            cameraImages .setFocusable(false);
            ll_text_upload.setVisibility(View.VISIBLE);
            ll_image_upload.setVisibility(View.GONE);
        } else {

            galleryImages .setClickable(true);
            galleryImages .setFocusable(true);
            cameraImages .setClickable(true);
            cameraImages .setFocusable(true);
            ll_text_upload.setVisibility(View.GONE);
            ll_image_upload.setVisibility(View.VISIBLE);

        }
    }
}
