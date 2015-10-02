package glamvoir.appzstack.glamvoir.model.net.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by gajendran on 1/10/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfileResponse extends BaseResponse {

    @JsonProperty("results")
    public List<GetProfileResponse> results;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class GetProfileResponse {

        @JsonProperty("user_id")
        public String user_id;

        @JsonProperty("user_fname")
        public String user_fname;

        @JsonProperty("user_lname")
        public String user_lname;

        @JsonProperty("user_email")
        public String user_email;

        @JsonProperty("user_gender")
        public String user_gender;

        @JsonProperty("user_about")
        public String user_about;

        @JsonProperty("user_dob")
        public String user_dob;

        @JsonProperty("user_city")
        public String user_city;

        @JsonProperty("user_contact")
        public String user_contact;

        @JsonProperty("user_upadted_by")
        public String user_upadted_by;

        @JsonProperty("user_image")
        public String user_image;

    }
}

