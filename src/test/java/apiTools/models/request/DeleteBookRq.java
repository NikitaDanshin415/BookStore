package apiTools.models.request;

import lombok.Data;

@Data
public class DeleteBookRq {
    public DeleteBookRq(String isbn, String userId) {
        this.isbn = isbn;
        this.userId = userId;
    }

    private String isbn;
    private String userId;
}
