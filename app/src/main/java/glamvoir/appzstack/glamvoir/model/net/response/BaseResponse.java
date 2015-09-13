package glamvoir.appzstack.glamvoir.model.net.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by gajendran on 2015-09-11.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseResponse {

    private ResponseStatus statusType;

    @JsonProperty("error_code")
    public String error_code;

    @JsonProperty("msg_string")
    public String msg_string;


    public ResponseStatus getStatusType() {
        if (statusType == null) {
            statusType = ResponseStatus.fromName(error_code);
        }

        return statusType;
    }

    public String getMessage() {
        return msg_string;
    }

    public boolean isSucceeded() {
        ResponseStatus localStatus = getStatusType();

        if (localStatus != null && localStatus == ResponseStatus.Success) {
            return true;
        } else {
            return false;
        }
    }
}
