CREATE TABLE IF NOT EXISTS pedido (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    numero_controle VARCHAR(100),
    data_cadastro TIMESTAMP,
    nome_cliente VARCHAR(255),
    valor_total DECIMAL(19,2),
    quantidade_total INT,
    codigo_cliente VARCHAR(100)
    );

CREATE TABLE IF NOT EXISTS item_pedido (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    produto_id BIGINT,
    produto_nome VARCHAR(255),
    valor_unitario DECIMAL(19,2),
    quantidade INT,
    pedido_id BIGINT,
    CONSTRAINT fk_item_pedido_pedido FOREIGN KEY (pedido_id) REFERENCES pedido(id) ON DELETE CASCADE
    );
