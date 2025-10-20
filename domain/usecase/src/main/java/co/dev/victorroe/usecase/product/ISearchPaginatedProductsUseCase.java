package co.dev.victorroe.usecase.product;

import co.dev.victorroe.model.product.Page;
import co.dev.victorroe.model.product.Product;
import reactor.core.publisher.Mono;

public interface ISearchPaginatedProductsUseCase {

    Mono<Page<Product>> byName (String name, int page, int size);
}
