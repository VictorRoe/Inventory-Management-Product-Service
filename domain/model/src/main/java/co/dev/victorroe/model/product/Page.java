package co.dev.victorroe.model.product;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Page<T>{

    private List<T> content;
    private int currentPage;
    private long totalElements;
    private int totalPages;
}
