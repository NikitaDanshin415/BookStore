package api.models.request;

import lombok.Data;

@Data
public class AddListOfBooksRq {
    private String userId;
    private Isbn[] collectionOfIsbn;
}