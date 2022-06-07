package api.models.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeleteBookRq {
    private String isbn;
    private String userId;
}