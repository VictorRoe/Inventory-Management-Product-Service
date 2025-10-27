package co.dev.victorroe.usecase.category;

import co.dev.victorroe.model.category.Category;
import co.dev.victorroe.model.category.gateways.CategoryRepository;
import co.dev.victorroe.usecase.category.contract.IFindCategoryByIdUseCase;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.logging.Logger;

@RequiredArgsConstructor
public class FindCategoryById implements IFindCategoryByIdUseCase {

    private final CategoryRepository repository;
    private final Logger log = Logger.getLogger(FindCategoryById.class.getName());

    @Override
    public Mono<Category> findCategoryById(Long id) {
        log.info("Buscando categoria");
        return repository.findById(id)
                .doOnSuccess(logger -> log.info("Se ha encontrado la categoria"))
                .doOnError(error -> log.warning("Hubo un error al encontrar categoria"));
    }
}
