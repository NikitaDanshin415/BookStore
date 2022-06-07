package api.models.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginRq {
    private String userName;
    private String password;
}