CREATE TABLE IF NOT EXISTS usuarios (
       id BIGINT AUTO_INCREMENT PRIMARY KEY,
       nome_completo VARCHAR(255) NOT NULL,
       telefone VARCHAR(20),
       cpf VARCHAR(14),
       data_nascimento DATE,
       email VARCHAR(255) UNIQUE NOT NULL,
       senha VARCHAR(255) NOT NULL,
       role ENUM('CLIENTE', 'ADMIN','GERENTE') NOT NULL DEFAULT 'CLIENTE',
       enabled BOOLEAN NOT NULL DEFAULT false,
       token_confirmacao VARCHAR(255),
       data_expiracao_token TIMESTAMP,
       data_criacao TIMESTAMP NOT NULL,
       data_ultimo_login TIMESTAMP
);