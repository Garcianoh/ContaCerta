CREATE TABLE grupo_membro (
    id BIGSERIAL NOT NULL,
    data_entrada TIMESTAMPTZ NOT NULL,
    papel VARCHAR(255) NOT NULL,
    user_id BIGINT NOT NULL,
    grupo_id BIGINT NOT NULL,
    CONSTRAINT pk_grupo_membro PRIMARY KEY(id),
    CONSTRAINT chk_grupo_membro_papel CHECK(papel IN ('ADMIN', 'MEMBRO')),
    CONSTRAINT fk_grupo_membro_user_id FOREIGN KEY(user_id) REFERENCES users(id),
    CONSTRAINT fk_grupo_membro_grupo_id FOREIGN KEY(grupo_id) REFERENCES grupo(id)
);