package co.dev.victorroe.usecase.product;

import co.dev.victorroe.model.product.gateways.ProductRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class DeleteProductUseCase implements IDeleteProductUseCase{

    private final ProductRepository repository;


    @Override
    public Mono<Void> deleteById(Long id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Producto no encontrado")))
                .flatMap( product -> repository.delete(product.getId()))
                .then();
    }
}
