package co.dev.victorroe.usecase.product.contract;

import co.dev.victorroe.model.product.Product;
import co.dev.victorroe.model.product.StockOutputType;
import reactor.core.publisher.Mono;

public interface IRemoveStockUseCase {

    Mono<Product> removeStock (Long id, Long quantity, StockOutputType type);
}
