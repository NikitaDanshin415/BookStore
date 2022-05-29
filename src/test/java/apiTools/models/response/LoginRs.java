package apiTools.models.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginRs {
    private String token;

    private String userId;

    private String username;

    private String expires;
}