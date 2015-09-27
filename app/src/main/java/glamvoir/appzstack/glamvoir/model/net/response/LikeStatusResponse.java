package glamvoir.appzstack.glamvoir.model.net.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by gajendran on 27/9/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LikeStatusResponse extends BaseResponse {
    @JsonProperty("results")
    public List<LikeStatus> list;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class LikeStatus {

        @JsonProperty("total_like")
        public String total_like;

        @JsonProperty("total_dislike")
        public String total_dislike;

        @JsonProperty("like_dislike_status")
        public String like_dislike_status;

    }
}
