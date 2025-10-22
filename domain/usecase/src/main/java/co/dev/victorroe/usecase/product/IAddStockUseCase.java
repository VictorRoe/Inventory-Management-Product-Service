package co.dev.victorroe.usecase.product;

import co.dev.victorroe.model.product.Product;
import reactor.core.publisher.Mono;

public interface IAddStockUseCase {

    Mono<Product> addStock (Long id, Long quantity);

}
