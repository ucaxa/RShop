CREATE TABLE IF NOT EXISTS enderecos (
       id BIGINT AUTO_INCREMENT PRIMARY KEY,
       cep VARCHAR(9) NOT NULL,
       logradouro VARCHAR(255) NOT NULL,
       numero VARCHAR(10) NOT NULL,
       complemento VARCHAR(100),
       bairro VARCHAR(100) NOT NULL,
       cidade VARCHAR(100) NOT NULL,
       estado VARCHAR(2) NOT NULL,
       principal BOOLEAN DEFAULT false,
       usuario_id BIGINT REFERENCES usuarios(id)
);