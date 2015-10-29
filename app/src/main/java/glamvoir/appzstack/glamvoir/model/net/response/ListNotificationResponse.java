package glamvoir.appzstack.glamvoir.model.net.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by gajendran on 29/10/15.
 */
public class ListNotificationResponse extends BaseResponse {
    @JsonProperty("results")
    public List<ListNotification> results;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ListNotification {

        @JsonProperty("user_id")
        public String user_id;

        @JsonProperty("title")
        public String title;

        @JsonProperty("post_id")
        public String post_id;

        @JsonProperty("user_image")
        public String user_image;

        @JsonProperty("notification_type")
        public String notification_type;

        @JsonProperty("creation_date")
        public String creation_date;

        @JsonProperty("message")
        public String message;
    }
}


