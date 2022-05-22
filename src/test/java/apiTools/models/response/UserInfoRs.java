package apiTools.models.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserInfoRs {
    @JsonProperty("userId")
    private String userId;

    @JsonProperty("username")
    private String userName;

    private BookRs[] books;
}