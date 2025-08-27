-- V4__create_admin_user.sql
INSERT INTO usuarios (email,nome_completo, senha, role, enabled, data_criacao)
SELECT 'admin@rshop.com','Usuário ADMIN', '$2a$10$hashedPassword', 'ADMIN', true, NOW()
WHERE NOT EXISTS (SELECT 1 FROM usuarios WHERE email = 'admin@rshop.com');