package co.dev.victorroe.usecase.product.contract;

import co.dev.victorroe.model.product.Product;
import reactor.core.publisher.Mono;

public interface IAddStockUseCase {

    Mono<Product> addStock (Long id, Long quantity);

}
