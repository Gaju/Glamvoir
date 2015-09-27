package glamvoir.appzstack.glamvoir.network;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import glamvoir.appzstack.glamvoir.Bean.AllPostsBean;
import glamvoir.appzstack.glamvoir.Bean.ChildPostBean;
import glamvoir.appzstack.glamvoir.Bean.ParentPostBean;
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
        ParentPostBean ParentPostBean = null;
        if (serverResponseString != null && serverResponseString.length() > 0) {
            try {
                JSONObject serverResponseJsonObject = new JSONObject(serverResponseString);
                int mStatus = serverResponseJsonObject.getInt("error_code");
                if (mStatus == 0) {
                    allPostsBean.setSuccessCode(0);

                    /**
                     * This set of code is used to parse the post details
                     */
                    if (serverResponseJsonObject.has("results")) {

                        List<ParentPostBean> parentPostBeanList = new ArrayList<ParentPostBean>();
                        JSONArray jsonArray = serverResponseJsonObject.getJSONArray("results");

                        for (int i = 0; i < jsonArray.length(); i++) {

                            ParentPostBean = new ParentPostBean();
                            JSONObject obj = jsonArray.getJSONObject(i);

                            //get the partial
                            if (Utils.contains(obj, "post_id")) {
                                ParentPostBean.setPost_id(obj.optString("post_id"));
                            }


                            if (Utils.contains(obj, "user_fname")) {
                                ParentPostBean.setUser_fname(obj.optString("user_fname"));
                            }

                            if (Utils.contains(obj, "user_lname")) {
                                ParentPostBean.setUser_lname(obj.optString("user_lname"));
                            }

                            if (Utils.contains(obj, "user_image")) {
                                ParentPostBean.setUser_image(obj.optString("user_image"));
                            }

                            if (Utils.contains(obj, "user_signup_type")) {
                                ParentPostBean.setUser_signup_type(obj.optString("user_signup_type"));
                            }

                            if (Utils.contains(obj, "user_contact")) {
                                ParentPostBean.setContact_no(obj.optString("user_contact"));
                            }


                            if (Utils.contains(obj, "post_parent_id")) {
                                ParentPostBean.setPost_parent_id(obj.optString("post_parent_id"));
                            }

                            //get the partial
                            if (Utils.contains(obj, "user_id")) {
                                ParentPostBean.setUser_id(obj.optString("user_id"));
                            }

                            //get the partial
                            if (Utils.contains(obj, "cat_id")) {
                                ParentPostBean.setCat_id(obj.optString("cat_id"));
                            }

                            //get the partial
                            if (Utils.contains(obj, "post_gender")) {
                                ParentPostBean.setPost_gender(obj.optString("post_gender"));
                            }

                            //get the partial
                            if (Utils.contains(obj, "post_description")) {
                                ParentPostBean.setPost_description(obj.optString("post_description"));
                            }

                            //get the partial
                            if (Utils.contains(obj, "is_saved")) {
                                ParentPostBean.setIs_saved(obj.optInt("is_saved"));
                            }

                            //get the partial
                            if (Utils.contains(obj, "post_video")) {
                                ParentPostBean.setPost_video(obj.optString("post_video"));
                            }

                            //get the partial
                            if (Utils.contains(obj, "post_image")) {
                                ParentPostBean.setPost_image(obj.optString("post_image"));
                            }

                            if (Utils.contains(obj, "post_start_date")) {
                                ParentPostBean.setPost_start_date(obj.optString("post_start_date"));
                            }

                            //get the partial
                            if (Utils.contains(obj, "post_end_date")) {
                                ParentPostBean.setPost_end_date(obj.optString("post_end_date"));
                            }
                            if (Utils.contains(obj, "is_following")) {
                                ParentPostBean.setIs_following(obj.optInt("is_following"));
                            }

                            //get the partial
                            if (Utils.contains(obj, "total_like")) {
                                ParentPostBean.setTotal_like(obj.optInt("total_like"));
                            }
                            if (Utils.contains(obj, "total_dislike")) {
                                ParentPostBean.setTotal_dislike(obj.optInt("total_dislike"));
                            }

                            //get the partial
                            if (Utils.contains(obj, "like_dislike_status")) {
                                ParentPostBean.setLike_dislike_status(obj.optInt("like_dislike_status"));
                            }

                            if (Utils.contains(obj, "total_comment")) {
                                ParentPostBean.setTotal_comment(obj.optInt("total_comment"));
                            }

                            parentPostBeanList.add(ParentPostBean);
                            allPostsBean.setResults(parentPostBeanList);


                            if (obj.has("child_data")) {
                                ChildPostBean childPostBean = null;
                                List<ChildPostBean> childPostBeanList = new ArrayList<ChildPostBean>();
                                JSONArray childjsonArray = obj.getJSONArray("child_data");

                                for (int j = 0; j < childjsonArray.length(); j++) {

                                    childPostBean = new ChildPostBean();
                                    JSONObject childObj = childjsonArray.getJSONObject(j);

                                    //get the partia
                                    if (Utils.contains(childObj, "post_id")) {
                                        childPostBean.setPost_id(obj.optString("post_id"));
                                    }

                                    if (Utils.contains(childObj, "post_parent_id")) {
                                        childPostBean.setPost_parent_id(obj.optString("post_parent_id"));
                                    }

                                    //get the partial
                                    if (Utils.contains(childObj, "user_id")) {
                                        childPostBean.setUser_id(obj.optString("user_id"));
                                    }

                                    //get the partial
                                    if (Utils.contains(childObj, "cat_id")) {
                                        childPostBean.setCat_id(obj.optString("cat_id"));
                                    }

                                    //get the partial
                                    if (Utils.contains(childObj, "post_gender")) {
                                        childPostBean.setPost_gender(obj.optString("post_gender"));
                                    }

                                    //get the partial
                                    if (Utils.contains(childObj, "post_description")) {
                                        childPostBean.setPost_description(obj.optString("post_description"));
                                    }

                                    //get the partial
                                    if (Utils.contains(childObj, "post_video")) {
                                        childPostBean.setPost_video(obj.optString("post_video"));
                                    }

                                    //get the partial
                                    if (Utils.contains(childObj, "post_image")) {
                                        childPostBean.setPost_image(obj.optString("post_image"));
                                    }

                                    //get the partial
                                    if (Utils.contains(childObj, "post_end_date")) {
                                        childPostBean.setPost_end_date(obj.optString("post_end_date"));
                                    }

                                    childPostBeanList.add(childPostBean);
                                    ParentPostBean.setChildResult(childPostBeanList);
                                }
                            }
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