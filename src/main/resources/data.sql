INSERT INTO authorities
VALUES ('ROLE_ADMIN', 'administrador do sistema'),
       ('ROLE_SUPORTE', 'equipe de suporte'),
       ('ROLE_CADCONTRACT', 'permissão para cadastrar contratos'),
       ('ROLE_CADCONTRATYPE', 'permissão cadastramento de tipo de contrato'),
       ('ROLE_CADUSER', 'permissão para cadastrar usuário'),
       ('ROLE_CADADITIVO', 'permissão para cadastrar aditivo'),
       ('ROLE_PUBLICO_SIGECON', 'permissão básica de usuário do sistema')
ON CONFLICT (authority_cod) DO NOTHING;