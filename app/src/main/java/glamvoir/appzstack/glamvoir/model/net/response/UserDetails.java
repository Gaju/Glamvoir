package glamvoir.appzstack.glamvoir.model.net.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by gajendran on 2015-08-03.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDetails implements Parcelable {

    @JsonProperty("user_id")
    public String user_id;

    @JsonProperty("user_image")
    public String user_image;

    @JsonProperty("user_fname")
    public String user_fname;

    @JsonProperty("user_lname")
    public String user_lname;

    @JsonProperty("user_email")
    public String user_email;

    @JsonProperty("user_authtoken")
    public String user_authtoken;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.user_id);
        dest.writeString(this.user_fname);
        dest.writeString(this.user_lname);
        dest.writeString(this.user_email);
        dest.writeString(this.user_authtoken);
    }

    public UserDetails() {
    }

    private UserDetails(Parcel in) {
        this.user_id = in.readString();
        this.user_fname = in.readString();
        this.user_lname = in.readString();
        this.user_email = in.readString();
        this.user_authtoken = in.readString();

    }

    public static final Creator<UserDetails> CREATOR = new Creator<UserDetails>() {
        public UserDetails createFromParcel(Parcel source) {
            return new UserDetails(source);
        }

        public UserDetails[] newArray(int size) {
            return new UserDetails[size];
        }
    };
}