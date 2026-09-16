CREATE TABLE convite (
    id BIGSERIAL NOT NULL,
    codigo VARCHAR(255) NOT NULL, 
    data_criacao TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP NOT NULL,
    status VARCHAR(255) NOT NULL,
    grupo_id BIGINT NOT NULL,
    CONSTRAINT pk_convite PRIMARY KEY(id),
    CONSTRAINT chk_convite_status CHECK(status IN ('ATIVO', 'USADO', 'EXPIRADO')),
    CONSTRAINT fk_convite_grupo_id FOREIGN KEY(grupo_id) REFERENCES grupo (id)
);