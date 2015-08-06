package glamvoir.appzstack.glamvoir.model.net.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gajendran on 03-08-2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginResponse implements Parcelable {


    @JsonProperty("error_code")
    public String error_code;

    @JsonProperty("msg_string")
    public String msg_string;

    @JsonProperty("results")
    public List<UserDetails> user;

    @Override
    public int describeContents() {
        return 0;
    }

    public LoginResponse() {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.error_code);
        dest.writeString(this.msg_string);
        dest.writeTypedList(this.user);
    }

    private LoginResponse(Parcel in) {
        this.error_code = in.readString();
        this.msg_string = in.readString();
        //this.user = in.readParcelable(UserDetails.class.getClassLoader());
        this.user = new ArrayList<UserDetails>();
        in.readTypedList(this.user, UserDetails.CREATOR);
    }

    public static final Creator<LoginResponse> CREATOR = new Creator<LoginResponse>() {
        public LoginResponse createFromParcel(Parcel source) {
            return new LoginResponse(source);
        }

        public LoginResponse[] newArray(int size) {
            return new LoginResponse[size];
        }
    };
}





