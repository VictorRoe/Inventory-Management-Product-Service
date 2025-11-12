package co.dev.victorroe.usecase.product.contract;

import co.dev.victorroe.model.product.Product;
import reactor.core.publisher.Mono;

public interface ISearchUniqueProductUseCase {

    Mono<Product> byId(Long id);

    Mono<Product> bySku(String sku);
}
