package api.models.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;


@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class TokenRs {
    @JsonProperty("token")
    private String token;

    @JsonProperty("status")
    private String status;

    @JsonProperty("result")
    private String result;
}
