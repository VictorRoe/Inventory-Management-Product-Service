package co.dev.victorroe.usecase.product;

import reactor.core.publisher.Mono;

public interface IDeleteProductUseCase {

    Mono<Void> deleteById (Long id);
}
