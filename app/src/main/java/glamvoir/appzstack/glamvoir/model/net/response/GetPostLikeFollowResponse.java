package glamvoir.appzstack.glamvoir.model.net.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by gajendran on 27/9/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetPostLikeFollowResponse extends BaseResponse {
    @JsonProperty("results")
    public List<LikeStatus> list;

    public int functionType;
    public int itemPosition;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class LikeStatus {

        @JsonProperty("total_like")
        public String total_like;

        @JsonProperty("like_dislike_status")
        public String like_dislike_status;

        @JsonProperty("total_folower")
        public String total_folower;

        @JsonProperty("is_followng")
        public String is_followng;

    }
}
