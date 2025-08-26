CREATE TABLE IF NOT EXISTS perfis(
        id BIGINT AUTO_INCREMENT PRIMARY KEY,
        nome_completo VARCHAR(255) NOT NULL,
        telefone VARCHAR(20),
        cpf VARCHAR(14),
        data_nascimento DATE,
        usuario_id BIGINT NOT NULL UNIQUE REFERENCES usuarios(id)
);