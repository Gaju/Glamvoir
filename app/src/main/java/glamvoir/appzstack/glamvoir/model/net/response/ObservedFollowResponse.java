package glamvoir.appzstack.glamvoir.model.net.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by gajendran on 27/9/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ObservedFollowResponse extends BaseResponse {
    @JsonProperty("results")
    public List<FollowResponse> observedFollowIds;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class FollowResponse {

        @JsonProperty("total_folower")
        public String total_folower;

        @JsonProperty("is_followng")
        public String is_followng;

    }
}
