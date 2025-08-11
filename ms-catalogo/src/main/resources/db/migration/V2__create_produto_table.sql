CREATE TABLE IF NOT EXISTS produtos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    descricao VARCHAR(255) NOT NULL,
    valor DECIMAL(19,2) NOT NULL,
    data_cadastro TIMESTAMP NOT NULL,
    categoria_id BIGINT,
    CONSTRAINT fk_produto_categoria FOREIGN KEY (categoria_id) REFERENCES categorias(id)
    );
