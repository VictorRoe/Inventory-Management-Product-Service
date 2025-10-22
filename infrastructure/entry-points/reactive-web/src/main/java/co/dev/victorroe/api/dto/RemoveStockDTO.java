package co.dev.victorroe.api.dto;

import co.dev.victorroe.model.product.StockOutputType;

public record RemoveStockDTO(
        Long quantity,
        StockOutputType type
) {
}
