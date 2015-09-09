package glamvoir.appzstack.glamvoir.model.net.post;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 * Created by Gajendran on 06-09-2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Post implements Parcelable {

    @JsonProperty("post_id")
    public String post_id;

    @JsonProperty("post_parent_id")
    public String post_parent_id;

    @JsonProperty("user_id")
    public String user_id;

    @JsonProperty("cat_id")
    public String cat_id;

    @JsonProperty("post_gender")
    public String post_gender;

    @JsonProperty("post_description")
    public String post_description;

    @JsonProperty("post_video")
    public String post_video;

    @JsonProperty("post_image")
    public String post_image;

    @JsonProperty("post_start_date")
    public String post_start_date;

    @JsonProperty("post_end_date")
    public String post_end_date;

    @JsonProperty("is_following")
    public String is_following;

    @JsonProperty("total_like")
    public String total_like;

    @JsonProperty("total_dislike")
    public String total_dislike;

    @JsonProperty("like_dislike_status")
    public String like_dislike_status;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(this.post_id);
        dest.writeString(this.post_parent_id);
        dest.writeString(this.user_id);
        dest.writeString(this.cat_id);
        dest.writeString(this.post_gender);
        dest.writeString(this.post_description);
        dest.writeString(this.post_video);
        dest.writeString(this.post_image);
        dest.writeString(this.post_start_date);
        dest.writeString(this.post_end_date);
        dest.writeString(this.is_following);
        dest.writeString(this.total_like);
        dest.writeString(this.total_dislike);
        dest.writeString(this.like_dislike_status);
    }

    private Post(Parcel in) {
        this.post_id = in.readString();
        this.post_parent_id = in.readString();
        this.user_id = in.readString();
        this.cat_id = in.readString();
        this.post_parent_id = in.readString();
        this.post_gender = in.readString();
        this.post_description = in.readString();
        this.post_video = in.readString();
        this.post_image = in.readString();
        this.post_start_date = in.readString();
        this.post_end_date = in.readString();
        this.is_following = in.readString();
        this.total_like = in.readString();
        this.total_dislike = in.readString();
        this.like_dislike_status = in.readString();

        //this.results = new ArrayList<Post>();
        //in.readTypedList(this.results, Post.CREATOR);
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        public Post createFromParcel(Parcel source) {
            return new Post(source);
        }

        public Post[] newArray(int size) {
            return new Post[size];
        }
    };
}
