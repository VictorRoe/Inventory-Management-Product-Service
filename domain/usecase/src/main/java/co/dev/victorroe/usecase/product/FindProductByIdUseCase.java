package co.dev.victorroe.usecase.product;

import co.dev.victorroe.model.product.Product;
import co.dev.victorroe.model.product.gateways.ProductRepository;
import co.dev.victorroe.usecase.product.contract.IFindProductByIdUseCase;
import co.dev.victorroe.usecase.product.contract.IProductDataEnricherUseCase;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.logging.Logger;

@RequiredArgsConstructor
public class FindProductByIdUseCase implements IFindProductByIdUseCase {

    private final Logger log = Logger.getLogger(FindProductByIdUseCase.class.getName());
    private final ProductRepository repository;
    private final List<IProductDataEnricherUseCase> enrichers;

    @Override
    public Mono<Product> findById(Long id) {
        log.info("[FindProductByIdUseCase] Buscando producto");
        return repository.findById(id)
                .flatMap(this::applyEnrichers)
                .doOnSuccess(logger -> log.info("[FindProductByIdUseCase] se ha encontrado el producto"))
                .doOnError(err -> log.warning("[FindProductByIdUseCase] no se ha encontrado el producto"));
    }

    private Mono<Product> applyEnrichers(Product product) {
        return Flux.fromIterable(enrichers)
                .reduce(Mono.just(product), (productMono, enricher) -> productMono.flatMap(enricher::enrich))
                .flatMap(mono -> mono);
    }
}
