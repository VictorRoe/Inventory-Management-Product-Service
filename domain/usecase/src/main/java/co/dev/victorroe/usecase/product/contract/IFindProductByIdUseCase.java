package co.dev.victorroe.usecase.product.contract;

import co.dev.victorroe.model.product.Product;
import reactor.core.publisher.Mono;

public interface IFindProductByIdUseCase {
    Mono<Product> findById(Long id);
}
