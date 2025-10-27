package co.dev.victorroe.usecase.category;

import co.dev.victorroe.model.category.Category;
import co.dev.victorroe.model.category.gateways.CategoryRepository;
import co.dev.victorroe.usecase.category.contract.IUpdateCategoryUseCase;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.logging.Logger;

@RequiredArgsConstructor
public class UpdateCategoryUseCase implements IUpdateCategoryUseCase {

    private final CategoryRepository repository;
    private final Logger log = Logger.getLogger(UpdateCategoryUseCase.class.getName());

    @Override
    public Mono<Category> updateCategory(Long id, Optional<String> newName) {
        log.info("Buscando Categoria");
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Categoria no encontrado con ID: " + id)))
                .flatMap(ifCategoryExist -> {
                    Category.CategoryBuilder categoryBuilder = ifCategoryExist.toBuilder();

                    newName.ifPresent(categoryBuilder::name);

                    Category updateCategory = categoryBuilder.build();

                    return repository.update(updateCategory);
                })
                .doOnSuccess(logger -> log.info("Se ha actualizado correctamente Category"));
    }
}
