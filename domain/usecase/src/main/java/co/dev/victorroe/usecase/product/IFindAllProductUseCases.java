package co.dev.victorroe.usecase.product;

import co.dev.victorroe.model.product.Page;
import co.dev.victorroe.model.product.Product;
import reactor.core.publisher.Mono;

public interface IFindAllProductUseCases {

    Mono<Page<Product>> apply(int page, int size);
}
