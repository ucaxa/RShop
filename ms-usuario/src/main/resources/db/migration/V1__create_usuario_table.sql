CREATE TABLE IF NOT EXISTS (
       id BIGINT AUTO_INCREMENT PRIMARY KEY,
       email VARCHAR(255) UNIQUE NOT NULL,
       senha VARCHAR(255) NOT NULL,
       role VARCHAR(20) NOT NULL DEFAULT 'CLIENTE',
       enabled BOOLEAN NOT NULL DEFAULT false,
       token_confirmacao VARCHAR(255),
       data_expiracao_token TIMESTAMP,
       data_criacao TIMESTAMP NOT NULL,
       data_ultimo_login TIMESTAMP
);