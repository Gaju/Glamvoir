package glamvoir.appzstack.glamvoir.navigationdrawer;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cocosw.bottomsheet.BottomSheet;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import glamvoir.appzstack.glamvoir.R;
import glamvoir.appzstack.glamvoir.activity.AlertActivity;
import glamvoir.appzstack.glamvoir.adapter.NavigationDrawerAdapter_Lv;
import glamvoir.appzstack.glamvoir.apppreference.AppPreferences;
import glamvoir.appzstack.glamvoir.asynctask.PhotoUploadAsyncTask;
import glamvoir.appzstack.glamvoir.constant.AppConstant;
import glamvoir.appzstack.glamvoir.helpers.ImageLoaderInitializer;
import glamvoir.appzstack.glamvoir.helpers.Utility;
import glamvoir.appzstack.glamvoir.interfaces.AsynTaskListener;
import glamvoir.appzstack.glamvoir.model.PhotoUploadResponse;
import glamvoir.appzstack.glamvoir.network.InternetStatus;
import glamvoir.appzstack.glamvoir.network.NetworkCall;
import retrofit.mime.TypedFile;

/**
 * Created by jaim on 8/10/2015.
 */
public class FragmentDrawer_Lv extends Fragment implements View.OnClickListener {


    private static String TAG = FragmentDrawer_Lv.class.getSimpleName();
    private static final int CAPTURE_IMAGE_CAMERA = 1;
    private static final int CAPTURE_IMAGE_GALLARY = 2;
    private Bitmap mbitmap;
    private ImageView proFile_Image;
    private ImageView alert;
    File file;
    int save = -1;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private NavigationDrawerAdapter_Lv adapter;
    private View containerView;
    private ListView listView;
    private Toolbar mtoolbar;
    private ProgressBar progressbar;

    /*String TITLES[] = {"FLEA MARKET", "FOLLOWERS", "FOLLOWING", "MY SAVES", "MY POST", "SETTING"};

    int ICONS[] = {R.drawable.ic_delete_black_24dp, R.drawable.ic_star_black_24dp, R.drawable.ic_drafts_black_24dp,
            R.drawable.ic_inbox_black_24dp, R.drawable.ic_report_black_24dp, R.drawable.ic_settings_black_24dp};*/
    String TITLES[] = {"FEED", "FLEA MARKET", "FOLLOWERS", "FOLLOWING", "MY SAVES", "MY POST", "SETTING"};

    int ICONS[] = {R.drawable.feed_active, R.drawable.fleamarket, R.drawable.followers, R.drawable.following,
            R.drawable.mysave, R.drawable.mypost,R.drawable.setting};
    private StringBuilder mName;
    private String mEmail;
    int PROFILE = R.drawable.camera;
    private FragmentDrawerListener_Lv drawerListener_lv;


    public FragmentDrawer_Lv() {

    }

    public void setDrawerListener_lv(FragmentDrawerListener_Lv listener) {
        this.drawerListener_lv = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppPreferences appPreferences = new AppPreferences(getActivity());
        String jai=appPreferences.getLastName().toString();
        if (appPreferences.getFirstName() != null && appPreferences.getEmailID() != null) {
            mName = new StringBuilder().append(appPreferences.getFirstName()).append(" ").append((appPreferences.getLastName() == null) ? "" : appPreferences.getLastName());
            mEmail = appPreferences.getEmailID();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflating view layout
        View layout = inflater.inflate(R.layout.fragment_navigation_drawer_lv, container, false);
        TextView tv_name = (TextView) layout.findViewById(R.id.name);
        TextView tv_email = (TextView) layout.findViewById(R.id.email);
        progressbar = (ProgressBar) layout.findViewById(R.id.progressbar);
        proFile_Image = (ImageView) layout.findViewById(R.id.circleView);
        proFile_Image.setOnClickListener(this);
        alert = (ImageView) layout.findViewById(R.id.alert);
        alert.setOnClickListener(this);
        tv_name.setText(mName);
        tv_email.setText(mEmail);
        listView = (ListView) layout.findViewById(R.id.drawerList);
        adapter = new NavigationDrawerAdapter_Lv(getActivity(), TITLES, ICONS);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


               /* parent.getChildAt(position).setBackgroundColor(
                        Color.parseColor("#A9BCF5"));*/
                drawerListener_lv.onDrawerItemSelected_Lv(position);
                mDrawerLayout.closeDrawer(containerView);

                /*if (save != -1 && save != position) {
                    parent.getChildAt(save).setBackgroundColor(
                            Color.parseColor("#d6e6ff"));
                    drawerListener_lv.onDrawerItemSelected_Lv(position);
                    mDrawerLayout.closeDrawer(containerView);
                    drawerListener_lv.onDrawerItemSelected_Lv(position);
                    mDrawerLayout.closeDrawer(containerView);
                }

                save = position;*/
            }
        });

        return layout;
    }


    public void setUp(int fragmentId, DrawerLayout drawerLayout, Toolbar toolbar) {


        containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mtoolbar = toolbar;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                mtoolbar.setAlpha(1 - slideOffset / 2);
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {

                mDrawerToggle.syncState();
            }
        });


    }


    public interface FragmentDrawerListener_Lv {
        void onDrawerItemSelected_Lv(int position);


    }

    @Override
    public void onClick(View v) {

        new BottomSheet.Builder(getActivity()).title("Upload Profile Pic").sheet(R.menu.list).listener(new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case R.id.share:
                        Toast.makeText(getActivity(), "Gallery", Toast.LENGTH_LONG).show();
                        file = new File(Environment.getExternalStorageDirectory() + "/" + getDateTime() + ".jpg");
                        try {
                            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                            intent.setType("image/*");
                            startActivityForResult(intent, CAPTURE_IMAGE_GALLARY);
                        } catch (ActivityNotFoundException e) {
                            e.printStackTrace();
                        }
                        break;
                    case R.id.upload:
                        Toast.makeText(getActivity(), "Camera", Toast.LENGTH_LONG).show();
                        file = new File(Environment.getExternalStorageDirectory() + "/" + getDateTime() + ".jpg");
                        // capture image from camera.
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                        startActivityForResult(intent, CAPTURE_IMAGE_CAMERA);

                        break;
                    case R.id.call:
                        Toast.makeText(getActivity(), "Delete", Toast.LENGTH_LONG).show();
                        mbitmap.recycle();
                        break;
                    case R.id.help:
                        Toast.makeText(getActivity(), "Cancel", Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                        break;
                }
            }
        }).show();

        switch (v.getId()) {
            case R.id.alert:
                AlertActivity.startActivity(getActivity());
                break;
            default:
                break;

        }


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;

        if (requestCode == CAPTURE_IMAGE_CAMERA) {
           // photoUpload(AppConstant.METHOD_UPDATE_IMAGE, AppPreferences.getInstance(getActivity()).getUserId(), file.getAbsolutePath());

            mbitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
            proFile_Image.setImageBitmap(mbitmap);

        } else if (requestCode == CAPTURE_IMAGE_GALLARY) {
            try {
                // We need to recyle unused bitmaps
                if (mbitmap != null) {
                    mbitmap.recycle();
                }
              //  photoUpload(AppConstant.METHOD_UPDATE_IMAGE, AppPreferences.getInstance(getActivity()).getUserId(), getRealPathFromURI(data.getData()));

                InputStream stream = getActivity().getContentResolver().openInputStream(
                        data.getData());
                new TypedFile("image/jpeg", new File(data.getData().toString()));
                mbitmap = BitmapFactory.decodeStream(stream);
                stream.close();
                proFile_Image.setImageBitmap(mbitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getActivity().getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    private void photoUpload(String methodName, String mUserID, String mpath) {
        if (InternetStatus.isInternetAvailable(getActivity(), true)) {
            progressbar.setVisibility(View.VISIBLE);
            new PhotoUploadAsyncTask(getActivity(), new AsynTaskListener() {
                @Override
                public void success(String success, String listenerId) {

                }

                @Override
                public void error(String errorMessage, String errorCode, String listenerId) {
                    progressbar.setVisibility(View.GONE);
                    Utility.showToast(getActivity(), errorMessage);
                }

                @Override
                public void successWithresult(List<Object> sucessObject, String message, String listenerId) {
                    progressbar.setVisibility(View.GONE);
                    Utility.showToast(getActivity(), "success");

                    PhotoUploadResponse photoUploadResponse = (PhotoUploadResponse) sucessObject.get(0);

                }
            }, "photo_upload").execute(methodName, mUserID, mpath);
        }
    }


    private void displayImage(ImageView imageView, String path) {

        ImageLoader imageLoader = ImageLoader.getInstance();
//        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
//                .cacheOnDisc(true).resetViewBeforeLoading(true)
//                .showImageForEmptyUri(fallback)
//                .showImageOnFail(fallback)
//                .showImageOnLoading(fallback).build();

        DisplayImageOptions options = ImageLoaderInitializer.getDisplayImageOptionWithFade();
        imageLoader.displayImage(path, imageView, options);

    }

    private String getDateTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(c.getTime());
    }
}
