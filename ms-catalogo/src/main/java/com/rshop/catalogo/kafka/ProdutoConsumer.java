/*package com.rshop.catalogo.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProdutoConsumer {

    @KafkaListener(topics = "produto-criado", groupId = "rshop-catalogo-group")
    public void consumirEventoProdutoCriado(ProdutoEvent event) {
        log.info("Evento recebido no ms-catalogo: {}", event);
        // Aqui poderia atualizar cache, sincronizar dados, etc
    }
}*/

