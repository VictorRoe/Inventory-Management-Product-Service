package co.dev.victorroe.usecase.product;

import co.dev.victorroe.model.product.Product;
import co.dev.victorroe.model.product.gateways.ProductRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.logging.Logger;

@RequiredArgsConstructor
public class CreateProductUseCase implements ICreateProductUseCase{

    private final ProductRepository repository;
    private final Logger log = Logger.getLogger(CreateProductUseCase.class.getName());

    @Override
    public Mono<Product> create(Product product) {
        log.info("[CreateProductUseCase] Creando producto");
        return repository.create(product)
                .doOnSuccess(logger -> log.info("[CreateProductUseCase] Se ha creado correctamente el producto"))
                .doOnError(err -> log.warning("[CreateProductUseCase] Hubo un problema al crear el producto"));

    }
}
