package glamvoir.appzstack.glamvoir.asynctaskloader;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;

import glamvoir.appzstack.glamvoir.Bean.AddPostBean;
import glamvoir.appzstack.glamvoir.activity.CustomGallery;
import glamvoir.appzstack.glamvoir.constant.AppConstant;
import glamvoir.appzstack.glamvoir.model.TaskResponse;
import glamvoir.appzstack.glamvoir.model.net.response.AddPostResponse;
import glamvoir.appzstack.glamvoir.network.Communication;

/**
 * Created by gajendran on 2/11/15.
 */
public class Add_Parent_PostLoader extends AsyncTaskLoader<TaskResponse<AddPostResponse>> {

    private TaskResponse response = null;
    private AddPostBean bean = null;
    private ArrayList<CustomGallery> dataT = null;

    public Add_Parent_PostLoader(Context context, AddPostBean bean, ArrayList<CustomGallery> dataT) {
        super(context);
        this.bean = bean;
        this.dataT = dataT;
    }

    //user_id, post_parent_id, cat_id, post_gender, post_title, post_description, post_city, post_image,
    //post_video, post_end_date, post_location, post_lat, post_long


    @Override
    public TaskResponse<AddPostResponse> loadInBackground() {
        response = new TaskResponse();
        try {
            response.data = Communication.addPost(AppConstant.METHOD_ADD_POST, bean, dataT);
        } catch (Exception e) {
            response.error = e;
        }
        return response;
    }

    @Override
    protected void onStartLoading() {
        if (response != null) {
            deliverResult(response);
        }

        if (response == null || takeContentChanged()) {
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        response = null;
    }

}

