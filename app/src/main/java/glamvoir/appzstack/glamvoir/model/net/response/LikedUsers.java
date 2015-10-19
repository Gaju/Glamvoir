package glamvoir.appzstack.glamvoir.model.net.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by gajendran on 19/10/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LikedUsers extends BaseResponse {

    @JsonProperty("results")
    public List<LikedUser> results;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class LikedUser {

        @JsonProperty("user_id")
        public String user_id;

        @JsonProperty("user_fname")
        public String user_fname;

        @JsonProperty("user_lname")
        public String user_lname;

        @JsonProperty("user_image")
        public String user_image;

        @JsonProperty("user_upadted_by")
        public String user_upadted_by;

        @JsonProperty("creation_date")
        public String creation_date;

        @JsonProperty("total_follower")
        public String total_follower;

        @JsonProperty("is_follower")
        public String is_follower;

    }
}

