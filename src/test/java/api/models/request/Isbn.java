package api.models.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Isbn {
    public Isbn(String isbn) {
        this.isbn = isbn;
    }

    @JsonProperty("isbn")
    private String isbn;
}
