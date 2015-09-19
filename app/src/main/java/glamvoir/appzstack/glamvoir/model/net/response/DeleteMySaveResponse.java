package glamvoir.appzstack.glamvoir.model.net.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by gajendran on 15/9/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeleteMySaveResponse extends BaseResponse {

    @JsonProperty("results")
    public List<String> count;

}
