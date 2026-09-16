CREATE TABLE refresh_token (
    id BIGSERIAL NOT NULL,
    token VARCHAR(255) NOT NULL,
    user_id BIGINT NOT NULL,
    expiry_date TIMESTAMPTZ  NOT NULL,
    revogado BOOLEAN NOT NULL,
    CONSTRAINT pk_refresh_token PRIMARY KEY(id),
    CONSTRAINT fk_refresh_token_token UNIQUE(token),
    CONSTRAINT fk_refresh_token_user FOREIGN KEY(user_id) REFERENCES users(id)
);