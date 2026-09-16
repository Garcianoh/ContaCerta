CREATE TABLE divisao_despesa (
    id BIGSERIAL NOT NULL,
    valor_devido NUMERIC(15, 3) NOT NULL,
    despesa_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    CONSTRAINT pk_divisao_despesa PRIMARY KEY(id),
    CONSTRAINT fk_divisao_despesa_despesa_id FOREIGN KEY(despesa_id) REFERENCES despesa(id),
    CONSTRAINT fk_divisao_despesa_user_id FOREIGN KEY(user_id) REFERENCES users(id)
);