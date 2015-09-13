package glamvoir.appzstack.glamvoir.model.net.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by michal.luszczuk on 2014-09-17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ObservedFollowResponse extends BaseResponse {
    @JsonProperty("results")
    public List<String> observedFollowIds;


}
