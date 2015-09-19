package glamvoir.appzstack.glamvoir.Bean;

import java.io.Serializable;

/**
 * Created by gajendran on 19/9/15.
 */
public class ChildPostBean extends BaseBean implements Serializable{

    public String post_id;
    public String post_parent_id;
    public String user_id;
    public String cat_id;
    public String post_gender;
    public String post_description;
    public String post_video;
    public String post_image;
    public String post_end_date;

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

    public String getPost_end_date() {
        return post_end_date;
    }

    public void setPost_end_date(String post_end_date) {
        this.post_end_date = post_end_date;
    }
}