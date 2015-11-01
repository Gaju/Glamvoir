package glamvoir.appzstack.glamvoir.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gajendran on 10-09-2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FFSP_Response implements Parcelable {

    public FFSP_Response() {
    }

    @JsonProperty("error_code")
    public String error_code;

    @JsonProperty("msg_string")
    public String msg_string;

    @JsonProperty("results")
    public List<SingleFollow> results;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(this.error_code);
        dest.writeString(this.msg_string);
        dest.writeTypedList(this.results);
    }

    private FFSP_Response(Parcel in) {
        this.error_code = in.readString();
        this.msg_string = in.readString();
        this.results = new ArrayList<SingleFollow>();
        in.readTypedList(this.results, SingleFollow.CREATOR);
    }

    public static final Creator<FFSP_Response> CREATOR = new Creator<FFSP_Response>() {
        public FFSP_Response createFromParcel(Parcel source) {
            return new FFSP_Response(source);
        }

        public FFSP_Response[] newArray(int size) {
            return new FFSP_Response[size];
        }
    };

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SingleFollow implements Parcelable {

        public SingleFollow() {
        }

        @JsonProperty("post_id")
        public String post_id;

        @JsonProperty("post_parent_id")
        public String post_parent_id;

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

        @JsonProperty("post_end_date")
        public String post_end_date;


        @JsonProperty("user_id")
        public String user_id;

        @JsonProperty("user_fname")
        public String user_fname;

        @JsonProperty("user_lname")
        public String user_lname;

        @JsonProperty("user_gender")
        public String user_gender;

        @JsonProperty("user_image")
        public String user_image;

        @JsonProperty("total_following")
        public String total_following;

        @JsonProperty("total_follower")
        public String total_follower;

        @JsonProperty("is_following")
        public String is_following;

        @JsonProperty("is_follower")
        public String is_follower;

        @JsonProperty("post_title")
        public String post_title;



        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {

            dest.writeString(this.user_id);
            dest.writeString(this.user_fname);
            dest.writeString(this.user_lname);
            dest.writeString(this.user_gender);
            dest.writeString(this.user_image);
            dest.writeString(this.total_following);
            dest.writeString(this.is_following);

        }

        private SingleFollow(Parcel in) {
            this.user_id = in.readString();
            this.user_fname = in.readString();
            this.user_lname = in.readString();
            this.user_gender = in.readString();
            this.user_image = in.readString();
            this.total_following = in.readString();
            this.is_following = in.readString();
        }

        public static final Creator<SingleFollow> CREATOR = new Creator<SingleFollow>() {
            public SingleFollow createFromParcel(Parcel source) {
                return new SingleFollow(source);
            }

            public SingleFollow[] newArray(int size) {
                return new SingleFollow[size];
            }
        };
    }
}
