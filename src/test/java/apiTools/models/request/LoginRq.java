package apiTools.models.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LoginRq {
    public LoginRq(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    @JsonProperty("userName")
    private String userName;

    @JsonProperty("password")
    private String password;

}
