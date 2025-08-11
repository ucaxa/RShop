/*package com.ms.pedido.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PedidoProducer {

    private static final String TOPIC = "pedido-criado";

    private final KafkaTemplate<String, PedidoEvent> kafkaTemplate;

    public void enviarPedidoCriado(PedidoEvent event) {
        kafkaTemplate.send(TOPIC, event);
    }
}*/

