package co.dev.victorroe.r2dbc;

import co.dev.victorroe.model.product.Product;
import co.dev.victorroe.model.product.gateways.ProductRepository;
import co.dev.victorroe.r2dbc.entity.ProductEntity;
import co.dev.victorroe.r2dbc.helper.ReactiveAdapterOperations;
import lombok.extern.slf4j.Slf4j;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;

@Slf4j
@Repository
public class ProductReactiveRepositoryAdapter extends ReactiveAdapterOperations<
        Product,
        ProductEntity,
        String,
        ProductReactiveRepository
        > implements ProductRepository {

    private final TransactionalOperator transactionalOperator;

    public ProductReactiveRepositoryAdapter(ProductReactiveRepository repository, ObjectMapper mapper, TransactionalOperator transactionalOperator) {
        super(repository, mapper, d -> mapper.map(d, Product.class));
        this.transactionalOperator = transactionalOperator;
    }

    @Override
    public Mono<Product> create(Product product) {
        log.info("Guardando producto en la base de datos");
        return save(product).as(transactionalOperator::transactional)
                .doOnSuccess(saved -> log.info("El producto se ha guardado sastifactoriamente"))
                .doOnError(error -> log.error("Error al guardar producto. Hubo un problema en la capa de driven adapters: {}", error.getMessage()));
    }
}
