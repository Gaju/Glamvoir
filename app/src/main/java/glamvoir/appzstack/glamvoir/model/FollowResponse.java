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
public class FollowResponse implements Parcelable {

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

    private FollowResponse(Parcel in) {
        this.error_code = in.readString();
        this.msg_string = in.readString();
        this.results = new ArrayList<SingleFollow>();
        in.readTypedList(this.results, SingleFollow.CREATOR);
    }

    public static final Creator<FollowResponse> CREATOR = new Creator<FollowResponse>() {
        public FollowResponse createFromParcel(Parcel source) {
            return new FollowResponse(source);
        }

        public FollowResponse[] newArray(int size) {
            return new FollowResponse[size];
        }
    };

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SingleFollow implements Parcelable {

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

        @JsonProperty("is_following")
        public String is_following;

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
