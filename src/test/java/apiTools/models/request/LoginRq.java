package apiTools.models.request;

import lombok.Data;

@Data
public class LoginRq {
    public LoginRq(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    private String userName;

    private String password;
}