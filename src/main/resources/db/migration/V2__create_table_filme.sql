CREATE TABLE filme(
     id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL UNIQUE,
    avaliacao DOUBLE,
    genero VARCHAR(50),
    atores VARCHAR(500),
    poster VARCHAR(500),
    sinopse TEXT
);