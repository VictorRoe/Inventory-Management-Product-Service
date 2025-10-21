package co.dev.victorroe.r2dbc;

import co.dev.victorroe.model.product.Page;
import co.dev.victorroe.model.product.Product;
import co.dev.victorroe.model.product.gateways.ProductRepository;
import co.dev.victorroe.r2dbc.entity.ProductEntity;
import co.dev.victorroe.r2dbc.helper.ReactiveAdapterOperations;
import co.dev.victorroe.r2dbc.mapper.ProductMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class ProductReactiveRepositoryAdapter extends ReactiveAdapterOperations<
        Product,
        ProductEntity,
        Long,
        ProductReactiveRepository
        > implements ProductRepository {

    private final TransactionalOperator transactionalOperator;

    public ProductReactiveRepositoryAdapter(ProductReactiveRepository repository, TransactionalOperator transactionalOperator, ProductMapper mapper) {
        super(repository, mapper::toEntity, mapper::toDomain);
        this.transactionalOperator = transactionalOperator;
    }

    @Override
    public Mono<Product> create(Product product) {
        log.info("Guardando producto en la base de datos");
        return save(product).as(transactionalOperator::transactional)
                .doOnSuccess(saved -> log.info("El producto se ha guardado sastifactoriamente"))
                .doOnError(error -> log.error("Error al guardar producto. Hubo un problema en la capa de driven adapters: {}", error.getMessage()));
    }

    @Override
    public Mono<Product> findById(Long id) {
        return super.findById(id)
                .doOnSuccess(product -> log.info("El producto se ha encontrado sastifactoriamente"))
                .doOnError(error -> log.error("Error al buscar producto. Hubo un problema en la capa de driven adapters: {}", error.getMessage()));
    }

    @Override
    public Mono<Page<Product>> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return Mono.zip(
                repository.count(),
                repository.findAllBy(pageable)
                        .map(this::toEntity)
                        .collectList()
        ).map(tuple -> {
            long totalElements = tuple.getT1();
            List<Product> products = tuple.getT2();
            int totalPages = (int) Math.ceil((double) totalElements / size);

            return Page.<Product>builder()
                    .content(products)
                    .currentPage(page)
                    .totalElements(totalElements)
                    .totalPages(totalPages)
                    .build();
        });
    }

    @Override
    public Mono<Product> findBySku(String sku) {
        return repository.findBySku(sku).map(this::toEntity);
    }

    @Override
    public Mono<Page<Product>> findByNameContaining(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return Mono.zip(
                repository.countByNameContainingIgnoreCase(name),
                repository.findByNameContainingIgnoreCase(name, pageable)
                        .map(this::toEntity)
                        .collect(Collectors.toList())
        ).map(tuple -> {
            long totalElements = tuple.getT1();
            List<Product> products = tuple.getT2();
            int totalPages = (int) Math.ceil((double) totalElements / size);

            return Page.<Product>builder()
                    .content(products)
                    .currentPage(page)
                    .totalElements(totalElements)
                    .totalPages(totalPages)
                    .build();
        });
    }

    @Override
    public Mono<Product> update(Product product) {
        return save(product).as(transactionalOperator::transactional);
    }

    @Override
    public Mono<Void> delete(Long id) {
        return repository.deleteById(id);
    }
}
