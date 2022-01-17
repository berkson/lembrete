INSERT INTO authorities
VALUES ('ROLE_CADCONTRACT', 'permissão para cadastrar contratos'),
       ('ROLE_CADCONTRATYPE', 'permissão cadastramento de tipo de contrato'),
       ('ROLE_CADUSER', 'permissão para cadastrar usuário')
ON CONFLICT (authority_cod) DO NOTHING;