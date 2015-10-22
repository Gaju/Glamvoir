package glamvoir.appzstack.glamvoir.model.net.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by gajendran on 22/10/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CityResponse extends BaseResponse {

    @JsonProperty("results")
    public List<City> results;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class City {

        @JsonProperty("city_id")
        public String city_id;

        @JsonProperty("city_name")
        public String city_name;
    }
}
