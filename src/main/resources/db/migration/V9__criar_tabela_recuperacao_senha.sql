CREATE TABLE recuperacao_senha (
    id BIGSERIAL NOT NULL,
    codigo VARCHAR(50) NOT NULL,
    data_expiracao TIMESTAMPTZ NOT NULL,
    data_criacao TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP NOT NULL,
    status VARCHAR(255) NOT NULL,
    user_id BIGINT NOT NULL,
    CONSTRAINT pk_recuperacao_senha PRIMARY KEY(id),
    CONSTRAINT chk_recuperacao_senha_status CHECK(status IN ('PENDENTE', 'USADO', 'EXPIRADO')),
    CONSTRAINT fk_recuperacao_senha_user_id FOREIGN KEY(user_id) REFERENCES users(id)
);