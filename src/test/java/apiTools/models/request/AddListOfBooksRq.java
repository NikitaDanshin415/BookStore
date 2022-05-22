package apiTools.models.request;

import lombok.Data;

@Data
public class AddListOfBooksRq {
    private String userId;
    private Isbn[] collectionOfIsbns;

}
