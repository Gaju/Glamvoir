package glamvoir.appzstack.glamvoir.Bean;

/**
 * Created by gajendran on 2/11/15.
 */
public class AddPostBean {

    private String user_id;
    private String post_parent_id = "0";
    private String cat_id;
    private String post_gender;
    private String post_title;
    private String post_description;
    private String post_city;
    private String post_image = "";
    private String post_video = "";
    private String post_end_date = "";
    private String post_location = "";
    private String post_lat = "";
    private String post_long = "";

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPost_parent_id() {
        return post_parent_id;
    }

    public void setPost_parent_id(String post_parent_id) {
        this.post_parent_id = post_parent_id;
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

    public String getPost_title() {
        return post_title;
    }

    public void setPost_title(String post_title) {
        this.post_title = post_title;
    }

    public String getPost_description() {
        return post_description;
    }

    public void setPost_description(String post_description) {
        this.post_description = post_description;
    }

    public String getPost_city() {
        return post_city;
    }

    public void setPost_city(String post_city) {
        this.post_city = post_city;
    }

    public String getPost_image() {
        return post_image;
    }

    public void setPost_image(String post_image) {
        this.post_image = post_image;
    }

    public String getPost_video() {
        return post_video;
    }

    public void setPost_video(String post_video) {
        this.post_video = post_video;
    }

    public String getPost_end_date() {
        return post_end_date;
    }

    public void setPost_end_date(String post_end_date) {
        this.post_end_date = post_end_date;
    }

    public String getPost_location() {
        return post_location;
    }

    public void setPost_location(String post_location) {
        this.post_location = post_location;
    }

    public String getPost_lat() {
        return post_lat;
    }

    public void setPost_lat(String post_lat) {
        this.post_lat = post_lat;
    }

    public String getPost_long() {
        return post_long;
    }

    public void setPost_long(String post_long) {
        this.post_long = post_long;
    }
}
