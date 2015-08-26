package glamvoir.appzstack.glamvoir.model.net.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Gajendran on 16-08-2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PhotoUploadResponse {

    @JsonProperty("url")
    public String url;

    @JsonProperty("url_thumb")
    public String thumbnailUrl;

}