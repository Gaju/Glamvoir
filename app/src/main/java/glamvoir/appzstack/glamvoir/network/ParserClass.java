package glamvoir.appzstack.glamvoir.network;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import glamvoir.appzstack.glamvoir.Bean.AllPostsBean;
import glamvoir.appzstack.glamvoir.Bean.singlePostBean;
import glamvoir.appzstack.glamvoir.json.Utils;

/**
 * Created by gajendran on 09/09/15.
 * This class is used to parse the json response from server
 */
public class ParserClass {
    private static ParserClass parserClass;
    private Context context;

    public ParserClass(Context context) {
        this.context = context;
    }

    public static synchronized ParserClass getsInstance(Context context) {
        if (parserClass == null) {
            parserClass = new ParserClass(context);
        }
        return parserClass;
    }


    /**
     * This method is used to parse the Book detail webservice response
     *
     * @param serverResponseString
     * @param allPostsBean
     * @return
     */
    public AllPostsBean getAllPost(String serverResponseString,
                                                   AllPostsBean allPostsBean) {
        String error = null;
        int successCode;
        allPostsBean = new AllPostsBean();
        if (serverResponseString != null && serverResponseString.length() > 0) {
            try {
                JSONObject serverResponseJsonObject = new JSONObject(serverResponseString);
                int mStatus = serverResponseJsonObject.getInt("error_code");
                if (mStatus == 0) {
                    allPostsBean.setSuccessCode(0);

                    /**
                     * This set of code is used to parse the product details
                     */
                    if (serverResponseJsonObject.has("results")) {

                        singlePostBean singlePostBean = null;
                        List<singlePostBean> singlePostBeanList = new ArrayList<singlePostBean>();
                        JSONArray jsonArray = serverResponseJsonObject.getJSONArray("results");

                        for (int i = 0; i < jsonArray.length(); i++) {

                            singlePostBean = new singlePostBean();
                            JSONObject obj = jsonArray.getJSONObject(i);

                            //get the partial
                            if (Utils.contains(obj, "post_id")) {
                                singlePostBean.setPost_id(obj.optString("post_id"));
                            }

                            if (Utils.contains(obj, "post_parent_id")) {
                                singlePostBean.setPost_parent_id(obj.optString("post_parent_id"));
                            }

                            //get the partial
                            if (Utils.contains(obj, "user_id")) {
                                singlePostBean.setUser_id(obj.optString("user_id"));
                            }

                            //get the partial
                            if (Utils.contains(obj, "cat_id")) {
                                singlePostBean.setCat_id(obj.optString("cat_id"));
                            }

                            //get the partial
                            if (Utils.contains(obj, "post_gender")) {
                                singlePostBean.setPost_gender(obj.optString("post_gender"));
                            }

                            //get the partial
                            if (Utils.contains(obj, "post_description")) {
                                singlePostBean.setPost_description(obj.optString("post_description"));
                            }

                            //get the partial
                            if (Utils.contains(obj, "post_video")) {
                                singlePostBean.setPost_video(obj.optString("post_video"));
                            }

                            //get the partial
                            if (Utils.contains(obj, "post_image")) {
                                singlePostBean.setPost_image(obj.optString("post_image"));
                            }

                            if (Utils.contains(obj, "post_start_date")) {
                                singlePostBean.setPost_start_date(obj.optString("post_start_date"));
                            }

                            //get the partial
                            if (Utils.contains(obj, "post_end_date")) {
                                singlePostBean.setPost_end_date(obj.optString("post_end_date"));
                            }
                            if (Utils.contains(obj, "is_following")) {
                                singlePostBean.setIs_following(obj.optInt("is_following"));
                            }

                            //get the partial
                            if (Utils.contains(obj, "total_like")) {
                                singlePostBean.setTotal_like(obj.optInt("total_like"));
                            }
                            if (Utils.contains(obj, "total_dislike")) {
                                singlePostBean.setTotal_dislike(obj.optInt("total_dislike"));
                            }

                            //get the partial
                            if (Utils.contains(obj, "like_dislike_status")) {
                                singlePostBean.setLike_dislike_status(obj.optInt("like_dislike_status"));
                            }

                            singlePostBeanList.add(singlePostBean);
                            allPostsBean.setResults(singlePostBeanList);
                        }
                    }

                } else {
                    allPostsBean.setSuccessCode(1);
                }

            } catch (JSONException e) {
                allPostsBean.setSuccessCode(1);
                allPostsBean.setmExceptionName(e.getMessage());
            }
        } else {
            allPostsBean.setSuccessCode(1);
            allPostsBean.setmExceptionName("No Result Found");
        }
        return allPostsBean;
    }


}