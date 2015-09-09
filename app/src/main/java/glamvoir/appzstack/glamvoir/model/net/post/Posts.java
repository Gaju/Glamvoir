package glamvoir.appzstack.glamvoir.model.net.post;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gajendran on 06-09-2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Posts implements Parcelable {

    @JsonProperty("error_code")
    public String error_code;

    @JsonProperty("msg_string")
    public String msg_string;

    @JsonProperty("results")
    public List<Post> results;

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

    private Posts(Parcel in) {
        this.error_code = in.readString();
        this.msg_string = in.readString();
        this.results = new ArrayList<Post>();
        in.readTypedList(this.results, Post.CREATOR);
    }

    public static final Creator<Posts> CREATOR = new Creator<Posts>() {
        public Posts createFromParcel(Parcel source) {
            return new Posts(source);
        }

        public Posts[] newArray(int size) {
            return new Posts[size];
        }
    };
}
