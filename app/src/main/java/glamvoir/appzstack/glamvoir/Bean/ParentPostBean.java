package glamvoir.appzstack.glamvoir.Bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gajendran on 9/9/15.
 */
public class ParentPostBean extends BaseBean implements Serializable {

    public String post_id;
    public String post_parent_id;
    public String user_id;
    public String cat_id;
    public String post_gender;
    public String post_description;
    public String post_video;
    public String post_image;
    public String post_start_date;
    public String post_end_date;
    public int is_following;
    public int total_like;
    public int total_dislike;
    public int like_dislike_status;

    public List<ChildPostBean> childResult;

    public List<ChildPostBean> getChildResult() {
        return childResult;
    }

    public void setChildResult(List<ChildPostBean> childResult) {
        this.childResult = childResult;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getPost_parent_id() {
        return post_parent_id;
    }

    public void setPost_parent_id(String post_parent_id) {
        this.post_parent_id = post_parent_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getPost_gender() {
        return post_gender;
    }

    public void setPost_gender(String post_gender) {
        this.post_gender = post_gender;
    }

    public String getPost_description() {
        return post_description;
    }

    public void setPost_description(String post_description) {
        this.post_description = post_description;
    }

    public String getPost_video() {
        return post_video;
    }

    public void setPost_video(String post_video) {
        this.post_video = post_video;
    }

    public String getPost_image() {
        return post_image;
    }

    public void setPost_image(String post_image) {
        this.post_image = post_image;
    }

    public String getPost_start_date() {
        return post_start_date;
    }

    public void setPost_start_date(String post_start_date) {
        this.post_start_date = post_start_date;
    }

    public String getPost_end_date() {
        return post_end_date;
    }

    public void setPost_end_date(String post_end_date) {
        this.post_end_date = post_end_date;
    }

    public int getIs_following() {
        return is_following;
    }

    public void setIs_following(int is_following) {
        this.is_following = is_following;
    }

    public int getTotal_like() {
        return total_like;
    }

    public void setTotal_like(int total_like) {
        this.total_like = total_like;
    }

    public int getTotal_dislike() {
        return total_dislike;
    }

    public void setTotal_dislike(int total_dislike) {
        this.total_dislike = total_dislike;
    }

    public int getLike_dislike_status() {
        return like_dislike_status;
    }

    public void setLike_dislike_status(int like_dislike_status) {
        this.like_dislike_status = like_dislike_status;
    }
}
