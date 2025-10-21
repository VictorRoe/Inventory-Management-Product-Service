package co.dev.victorroe.usecase.product;

import co.dev.victorroe.model.product.Product;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Optional;

public interface IUpdateProductUseCase {

    Mono<Product> update(Long id, Optional<BigDecimal> newPrice, Optional<String> newDescription);
}
