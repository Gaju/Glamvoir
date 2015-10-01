package glamvoir.appzstack.glamvoir.Bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gajendran on 9/9/15.
 */
public class AllPostsBean extends BaseBean implements Serializable {

    public String error_code;
    public String msg_string;
    public List<ParentPostBean> results;

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getMsg_string() {
        return msg_string;
    }

    public void setMsg_string(String msg_string) {
        this.msg_string = msg_string;
    }

    public List<ParentPostBean> getResults() {
        return results;
    }

    public void setResults(List<ParentPostBean> results) {
        this.results = results;
    }
}
