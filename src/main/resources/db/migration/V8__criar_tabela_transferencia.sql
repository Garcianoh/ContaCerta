CREATE TABLE transferencia (
    id BIGSERIAL NOT NULL,
    valor NUMERIC(15, 3) NOT NULL,
    status VARCHAR(255) NOT NULL,
    data_confirmacao TIMESTAMPTZ,
    grupo_id BIGINT NOT NULL,
    devedor_id BIGINT NOT NULL,
    credor_id BIGINT NOT NULL,
    CONSTRAINT pk_transferencia PRIMARY KEY(id),
    CONSTRAINT chk_transferencia_status CHECK(status IN ('PENDENTE', 'AGUARDANDO_CONFIRMACAO_CREDOR', 'PAGO')),
    CONSTRAINT fk_transferencia_grupo_id FOREIGN KEY(grupo_id) REFERENCES grupo(id),
    CONSTRAINT fk_transferencia_devedor_id FOREIGN KEY(devedor_id) REFERENCES users(id),
    CONSTRAINT fk_transferencia_credor_id FOREIGN KEY(credor_id) REFERENCES users(id)
);