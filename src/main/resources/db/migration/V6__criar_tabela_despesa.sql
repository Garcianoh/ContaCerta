CREATE TABLE despesa (
    id BIGSERIAL NOT NULL,
    descricao VARCHAR(255) NOT NULL,
    valor_total NUMERIC(15, 3) NOT NULL,
    data_despesa TIMESTAMPTZ NOT NULL,
    data_criacao TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP NOT NULL,
    grupo_id BIGINT NOT NULL,
    pagador_id BIGINT NOT NULL,
    CONSTRAINT pk_despesa PRIMARY KEY(id),
    CONSTRAINT fk_despesa_grupo_id FOREIGN KEY(grupo_id) REFERENCES grupo(id),
    CONSTRAINT fk_despesa_pagador_id FOREIGN KEY(pagador_id) REFERENCES users(id)
);