package glamvoir.appzstack.glamvoir.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import glamvoir.appzstack.glamvoir.model.net.response.BaseResponse;
import glamvoir.appzstack.glamvoir.model.net.response.UserDetails;

/**
 * Created by gajendran on 13/9/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PhotoUploadResponse extends BaseResponse {

    @JsonProperty("results")
    public List<Image> results;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Image {

        @JsonProperty("user_image")
        public String user_image;
    }

}

