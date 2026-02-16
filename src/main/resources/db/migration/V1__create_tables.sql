CREATE TABLE series (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL UNIQUE,
    total_temporadas INT,
    avaliacao DOUBLE,
    genero VARCHAR(50),
    atores VARCHAR(500),
    poster VARCHAR(500),
    sinopse TEXT
);

CREATE TABLE episodios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    temporada INT,
    titulo VARCHAR(255),
    numero_episodio INT,
    avaliacao DOUBLE,
    data_lancamento DATE,
    serie_id BIGINT,
    CONSTRAINT fk_episodio_serie
        FOREIGN KEY (serie_id)
        REFERENCES series(id)
        ON DELETE CASCADE
);
