package glamvoir.appzstack.glamvoir.activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import glamvoir.appzstack.glamvoir.R;
import glamvoir.appzstack.glamvoir.adapter.Action;
import glamvoir.appzstack.glamvoir.adapter.GalleryAdapter;
import glamvoir.appzstack.glamvoir.constant.AppConstant;

public class CustomGalleryActivity extends AppCompatActivity {

    private static final int MAX_PHOTO = 5;
    GridView gridGallery;
    Handler handler;
    GalleryAdapter adapter;
     AddStory addStory;
    ImageView imgNoMedia;
    Button btnGalleryOk;
    Toolbar toolbar;
    String action;
    private ImageLoader imageLoader;

    List maxLength = new ArrayList();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        //
        setContentView(R.layout.gallery);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        addStory=new AddStory();
        action = getIntent().getAction();
        if (action == null) {
            finish();
        }
        initImageLoader();
        init();
        getToolbar(toolbar);
    }

    private void initImageLoader() {
        try {
            String CACHE_DIR = Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + "/.temp_tmp";
            new File(CACHE_DIR).mkdirs();

            File cacheDir = StorageUtils.getOwnCacheDirectory(getBaseContext(),
                    CACHE_DIR);

            DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                    .cacheOnDisc(true).imageScaleType(ImageScaleType.EXACTLY)
                    .bitmapConfig(Bitmap.Config.RGB_565).build();
            ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(
                    getBaseContext())
                    .defaultDisplayImageOptions(defaultOptions)
                /*.discCache(new UnlimitedDiscCache(cacheDir))*/
                    .memoryCache(new WeakMemoryCache());

            ImageLoaderConfiguration config = builder.build();
            imageLoader = ImageLoader.getInstance();
            imageLoader.init(config);

        } catch (Exception e) {

        }
    }

    private void init() {

        handler = new Handler();
        gridGallery = (GridView) findViewById(R.id.gridGallery);
        gridGallery.setFastScrollEnabled(true);
        adapter = new GalleryAdapter(getApplicationContext(), imageLoader);
        PauseOnScrollListener listener = new PauseOnScrollListener(imageLoader,
                true, true);
        gridGallery.setOnScrollListener(listener);

        if (action.equalsIgnoreCase(Action.ACTION_MULTIPLE_PICK)) {

            findViewById(R.id.llBottomContainer).setVisibility(View.VISIBLE);
            gridGallery.setOnItemClickListener(mItemMulClickListener);
            adapter.setMultiplePick(true);

        } else if (action.equalsIgnoreCase(Action.ACTION_PICK)) {

            findViewById(R.id.llBottomContainer).setVisibility(View.GONE);
            gridGallery.setOnItemClickListener(mItemSingleClickListener);
            adapter.setMultiplePick(false);

        }

        gridGallery.setAdapter(adapter);
        imgNoMedia = (ImageView) findViewById(R.id.imgNoMedia);

        btnGalleryOk = (Button) findViewById(R.id.btnGalleryOk);
        btnGalleryOk.setOnClickListener(mOkClickListener);

        try {
            new Thread() {
                @Override
                public void run() {
                    Looper.prepare();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            adapter.addAll(getGalleryPhotos());
                            checkImageStatus();
                        }
                    });
                    Looper.loop();
                }
            }.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkImageStatus() {
        if (adapter.isEmpty()) {
            imgNoMedia.setVisibility(View.VISIBLE);
        } else {
            imgNoMedia.setVisibility(View.GONE);
        }
    }

    View.OnClickListener mOkClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            ArrayList<CustomGallery> selected = adapter.getSelected();

            String[] allPath = new String[selected.size()];
            if (allPath.length<=5 && addStory.dataT.size()<=4){
            for (int i = 0; i < allPath.length; i++) {
                allPath[i] = selected.get(i).sdcardPath;
             }
                Intent data = new Intent().putExtra("all_path", allPath);
                setResult(RESULT_OK, data);
                finish();

            }
            else {
                Toast.makeText(CustomGalleryActivity.this,"Cant not share more than 5 media items.", Toast.LENGTH_LONG).show();
            }



        }
    };
    AdapterView.OnItemClickListener mItemMulClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> l, View v, int position, long id) {
//            maxLength.add(position);
//            if (maxLength.size() <= MAX_PHOTO || maxLength.contains(position)) {
            adapter.changeSelection(v, position);
//            } else {
//                Utility.showToast(CustomGalleryActivity.this, "not possible");
//            }
        }
    };

    AdapterView.OnItemClickListener mItemSingleClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> l, View v, int position, long id) {
            CustomGallery item = adapter.getItem(position);
            Intent data = new Intent().putExtra("single_path", item.sdcardPath);
            setResult(RESULT_OK, data);
            finish();
        }
    };

    private ArrayList<CustomGallery> getGalleryPhotos() {
        ArrayList<CustomGallery> galleryList = new ArrayList<CustomGallery>();

        try {
            final String[] columns = {MediaStore.Images.Media.DATA,
                    MediaStore.Images.Media._ID};
            final String orderBy = MediaStore.Images.Media._ID;

            Cursor imagecursor = managedQuery(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns,
                    null, null, orderBy);

            if (imagecursor != null && imagecursor.getCount() > 0) {

                while (imagecursor.moveToNext()) {
                    CustomGallery item = new CustomGallery();

                    int dataColumnIndex = imagecursor
                            .getColumnIndex(MediaStore.Images.Media.DATA);

                    item.sdcardPath = imagecursor.getString(dataColumnIndex);

                    galleryList.add(item);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();


        }

        // show newest photo at beginning of the list
        Collections.reverse(galleryList);
        return galleryList;
    }

    private void getToolbar(Toolbar toolbar) {

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("SEND IMAGES");

    }

    @Override
    public Intent getSupportParentActivityIntent() {
        Intent parentIntent = getIntent();
        String className = parentIntent.getStringExtra("ParentClassName"); //getting the parent class name
        Intent newIntent = null;
        finish();
        return newIntent;
    }

}
