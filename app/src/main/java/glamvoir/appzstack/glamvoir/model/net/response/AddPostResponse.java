package glamvoir.appzstack.glamvoir.model.net.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by gajendran on 2/11/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddPostResponse extends BaseResponse {

    @JsonProperty("results")
    public List<AddPost> results;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AddPost {

        @JsonProperty("post_parent_id")
        public String post_parent_id;

    }
}
