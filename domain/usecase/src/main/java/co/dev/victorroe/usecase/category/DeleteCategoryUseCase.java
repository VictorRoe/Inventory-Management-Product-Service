package co.dev.victorroe.usecase.category;

import co.dev.victorroe.model.category.gateways.CategoryRepository;
import co.dev.victorroe.usecase.category.contract.IDeleteCategoryUseCase;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.logging.Logger;

@RequiredArgsConstructor
public class DeleteCategoryUseCase implements IDeleteCategoryUseCase {

    private final CategoryRepository repository;
    private final Logger log = Logger.getLogger(DeleteCategoryUseCase.class.getName());

    @Override
    public Mono<Void> deleteCategoryById(Long id) {
        log.info("Eliminando categoria");
        return repository.delete(id).then();
    }
}
