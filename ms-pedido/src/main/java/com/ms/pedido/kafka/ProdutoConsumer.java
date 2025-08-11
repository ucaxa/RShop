/*package com.ms.pedido.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProdutoConsumer {

    @KafkaListener(topics = "produto-criado", groupId = "rshop-pedido-group")
    public void consumirProdutoCriado(Object event) {
        log.info("ms-pedido recebeu evento produto-criado: {}", event);
        // Exemplo: poderia validar estoque, atualizar cache, etc.
    }
}*/

