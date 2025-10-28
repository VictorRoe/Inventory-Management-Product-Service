package co.dev.victorroe.api;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import co.dev.victorroe.api.dto.product.*;
import co.dev.victorroe.model.product.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterRest {
    @Bean
    @RouterOperations({@RouterOperation(
            path = "/api/v1/product/{id}",
            method = RequestMethod.GET,
            beanClass = ProductHandler.class, beanMethod = "findProductById",
            operation = @Operation(operationId = "findProductById", summary = "Buscar un producto por su ID", tags = {"Productos"},
                    parameters = {@Parameter(in = ParameterIn.PATH, name = "id", description = "ID del producto", required = true, example = "1")},
                    responses = {
                            @ApiResponse(responseCode = "200", description = "Producto encontrado", content = @Content(schema = @Schema(implementation = ResponseProductDTO.class))),
                            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
                    }
            )
    ),
            @RouterOperation(
                    path = "/api/v1/product",
                    method = RequestMethod.POST,
                    beanClass = ProductHandler.class, beanMethod = "createCategory",
                    operation = @Operation(operationId = "createCategory", summary = "Crear un nuevo producto", tags = {"Productos"},
                            requestBody = @RequestBody(description = "Datos del nuevo producto", required = true, content = @Content(schema = @Schema(implementation = RequestProductDTO.class))),
                            responses = {
                                    @ApiResponse(responseCode = "201", description = "Producto creado exitosamente"),
                                    @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/api/v1/product",
                    method = RequestMethod.GET,
                    beanClass = ProductHandler.class, beanMethod = "findAllProducts",
                    operation = @Operation(operationId = "findAllProducts", summary = "Listar todos los productos de forma paginada", tags = {"Productos"},
                            parameters = {@Parameter(in = ParameterIn.QUERY, name = "page", description = "Número de la página a solicitar (empieza en 0)", example = "0")},
                            responses = {@ApiResponse(responseCode = "200", description = "Página de productos", content = @Content(schema = @Schema(implementation = Page.class)))}
                    )
            ),
            @RouterOperation(
                    path = "/api/v1/product/search",
                    method = RequestMethod.GET,
                    beanClass = ProductHandler.class, beanMethod = "searchProducts",
                    operation = @Operation(operationId = "searchProducts", summary = "Búsqueda flexible de productos", tags = {"Productos"},
                            parameters = {
                                    @Parameter(in = ParameterIn.QUERY, name = "id", description = "Buscar por ID exacto.", example = "1"),
                                    @Parameter(in = ParameterIn.QUERY, name = "sku", description = "Buscar por SKU exacto.", example = "LPX15-512-GR"),
                                    @Parameter(in = ParameterIn.QUERY, name = "name", description = "Buscar por nombre (búsqueda parcial).", example = "Laptop"),
                                    @Parameter(in = ParameterIn.QUERY, name = "page", description = "Número de página para la búsqueda por nombre.", example = "0")
                            },
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Resultado de la búsqueda", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {ResponseProductDTO.class, Page.class}))),
                                    @ApiResponse(responseCode = "400", description = "Criterio de búsqueda inválido o faltante"),
                                    @ApiResponse(responseCode = "404", description = "Producto no encontrado (para búsqueda por id/sku)")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/api/v1/product/{id}",
                    method = RequestMethod.PATCH,
                    beanClass = ProductHandler.class, beanMethod = "updateProduct",
                    operation = @Operation(operationId = "updateProduct", summary = "Actualizar un producto parcialmente", tags = {"Productos"},
                            parameters = {@Parameter(in = ParameterIn.PATH, name = "id", description = "ID del producto a actualizar", required = true, example = "1")},
                            requestBody = @RequestBody(description = "Campos a actualizar", required = true, content = @Content(schema = @Schema(implementation = UpdateProductDTO.class))),
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Producto actualizado exitosamente", content = @Content(schema = @Schema(implementation = ResponseProductDTO.class))),
                                    @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
                                    @ApiResponse(responseCode = "404", description = "Producto no encontrado")
                            }
                    )

            ),
            @RouterOperation(
                    path = "/api/v1/delete/product/{id}",
                    method = RequestMethod.DELETE,
                    beanClass = ProductHandler.class, beanMethod = "deleteProduct",
                    operation = @Operation(operationId = "deleteProduct", summary = "Eliminar un producto", tags = {"Productos"},
                            parameters = {@Parameter(in = ParameterIn.PATH, name = "id", description = "ID del producto a eliminar", required = true, example = "1")},
                            responses = {
                                    @ApiResponse(responseCode = "204", description = "Producto eliminado exitosamente (sin contenido)"),
                                    @ApiResponse(responseCode = "404", description = "Producto no encontrado")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/api/v1/product/{id}/stock",
                    method = RequestMethod.POST,
                    beanClass = ProductHandler.class, beanMethod = "addStock",
                    operation = @Operation(operationId = "addStock", summary = "Registrar una entrada de stock", tags = {"Productos"},
                            parameters = {@Parameter(in = ParameterIn.PATH, name = "id", description = "ID del producto", required = true, example = "1")},
                            requestBody = @RequestBody(description = "Cantidad de stock a AÑADIR", required = true, content = @Content(schema = @Schema(implementation = AddStockDTO.class))),
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Stock actualizado exitosamente", content = @Content(schema = @Schema(implementation = ResponseProductDTO.class))),
                                    @ApiResponse(responseCode = "400", description = "Cantidad inválida (debe ser > 0)"),
                                    @ApiResponse(responseCode = "404", description = "Producto no encontrado")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/api/v1/product/{id}/stock-exit",
                    method = RequestMethod.POST,
                    beanClass = ProductHandler.class, beanMethod = "removeStock",
                    operation = @Operation(operationId = "removeStock", summary = "Registrar una salida de stock (venta o merma)", tags = {"Productos"},
                            parameters = {@Parameter(in = ParameterIn.PATH, name = "id", description = "ID del producto", required = true, example = "1")},
                            requestBody = @RequestBody(description = "Cantidad y tipo de salida de stock", required = true, content = @Content(schema = @Schema(implementation = RemoveStockDTO.class))), // Nuevo DTO
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Stock actualizado exitosamente", content = @Content(schema = @Schema(implementation = ResponseProductDTO.class))),
                                    @ApiResponse(responseCode = "400", description = "Cantidad inválida (debe ser > 0)"),
                                    @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
                                    @ApiResponse(responseCode = "409", description = "Conflicto: Stock insuficiente")
                            }
                    )
            )
    })

    public RouterFunction<ServerResponse> routerFunction(ProductHandler productHandler, CategoryHandler categoryHandler) {
        return route(POST("/api/v1/product"), productHandler::createProduct)
                .andRoute(GET("/api/v1/product"), productHandler::findAllProducts)
                .andRoute(GET("/api/v1/product/search"), productHandler::searchProducts)
                .andRoute(PATCH("/api/v1/product/{id}"), productHandler::updateProduct)
                .andRoute(DELETE("/api/v1/delete/product/{id}"), productHandler::deleteProductById)
                .andRoute(POST("/api/v1/product/{id}/stock"), productHandler::addStock)
                .andRoute(POST("/api/v1/product/{id}/stock-exit"),productHandler::removeStock)
                .andRoute(GET("/api/v1/product/{id}"), productHandler::findProductById)

                .andRoute(POST("/api/v1/category"), categoryHandler::createCategory)
                .andRoute(PATCH("/api/v1/update/category/{id}"), categoryHandler::updateCategory)
                .andRoute(DELETE("/api/v1/delete/category/{id}"), categoryHandler::deleteCategoryById)
                .andRoute(GET("/api/v1/category/{id}"), categoryHandler::findCategoryById);
    }
}
