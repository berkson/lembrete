INSERT INTO authorities
VALUES ('ROLE_CADCONTRACT', 'permissão para cadastrar contratos'),
       ('ROLE_CADCONTRATYPE', 'permissão cadastramento de tipo de contrato'),
       ('ROLE_CADUSER', 'permissão para cadastrar usuário'),
       ('ROLE_CADADITIVO', 'permissão para cadastrar aditivo'),
       ('ROLE_CADUSUARIO', 'permissão básica de usuário do sistema')
ON CONFLICT (authority_cod) DO NOTHING;