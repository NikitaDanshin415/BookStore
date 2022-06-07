package api.models.request;

import lombok.Data;

@Data
public class Isbn {
    public Isbn(String isbn) {
        this.isbn = isbn;
    }
    private String isbn;
}