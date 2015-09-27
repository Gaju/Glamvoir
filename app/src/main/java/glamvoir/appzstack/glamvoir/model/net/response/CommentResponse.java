package glamvoir.appzstack.glamvoir.model.net.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by gajendran on 26/9/15.
 */
public class CommentResponse extends BaseResponse {

    @JsonProperty("results")
    public List<AllCommentResponse> results;

   public static class AllCommentResponse {

        @JsonProperty("user_image")
        public String user_image;

        @JsonProperty("user_id")
        public String user_id;

        @JsonProperty("user_fname")
        public String user_fname;

        @JsonProperty("user_lname")
        public String user_lname;

        @JsonProperty("post_id")
        public String post_id;

        @JsonProperty("comment_id")
        public String comment_id;

        @JsonProperty("comment_text")
        public String comment_text;

        @JsonProperty("comment_date")
        public String comment_date;

    }
}
