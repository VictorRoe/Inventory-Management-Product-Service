package co.dev.victorroe.usecase.category;

import co.dev.victorroe.model.category.Category;
import co.dev.victorroe.model.category.gateways.CategoryRepository;
import co.dev.victorroe.usecase.category.contract.ICreateCategoryUseCase;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.logging.Logger;

@RequiredArgsConstructor
public class CreateCategoryUseCase implements ICreateCategoryUseCase {

    private final CategoryRepository repository;
    private final Logger log = Logger.getLogger(CreateCategoryUseCase.class.getName());

    @Override
    public Mono<Category> createCategory(Category category) {
        log.info("[CreateCategory] Creando Categoria");
        return repository.create(category);
    }
}
