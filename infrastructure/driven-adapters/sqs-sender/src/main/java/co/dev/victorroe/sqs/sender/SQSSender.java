package co.dev.victorroe.sqs.sender;

import co.dev.victorroe.model.inventorymovement.InventoryMovement;
import co.dev.victorroe.model.inventorymovement.gateways.InventoryMovementRepository;
import co.dev.victorroe.sqs.sender.config.SQSSenderProperties;
import co.dev.victorroe.sqs.sender.exception.EventSerializationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;


@Service
@Log4j2
@RequiredArgsConstructor
public class SQSSender implements InventoryMovementRepository {
    private final SQSSenderProperties properties;
    private final SqsAsyncClient client;

    private final ObjectMapper objectMapper;

    @Override
    public Mono<Void> notifyMovement(InventoryMovement movement) {
        return Mono.fromCallable(() -> serializeMovement(movement))
                .map(this::buildRequest)
                .flatMap(request -> Mono.fromFuture(client.sendMessage(request)))
                .doOnSuccess(response -> log.info("Evento de inventario enviado a SQS, Message ID: {}", response.messageId()))
                .doOnError(e -> log.error("Error al enviar mensaje SQS", e))
                .then();
    }


    private SendMessageRequest buildRequest(String message) {
        return SendMessageRequest.builder()
                .queueUrl(properties.queueUrl())
                .messageBody(message)
                .build();
    }

    private String serializeMovement(InventoryMovement movement) {
        try {
            return objectMapper.writeValueAsString(movement);
        } catch (JsonProcessingException e) {
            log.error("Error al serializar InventoryMovement a JSON", e);
            throw new EventSerializationException("Error al serializar evento", e);
        }
    }

}
