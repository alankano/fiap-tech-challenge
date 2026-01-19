CREATE TABLE usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255),
    email VARCHAR(255) UNIQUE NOT NULL,
    login VARCHAR(255)  NOT NULL,
    senha VARCHAR(255) NOT NULL,
    data_ultima_alteracao DATE NOT NULL,
    endereco VARCHAR(255),
    tipo_usuario VARCHAR(50) NOT NULL
);

INSERT INTO usuarios (nome, email, login, senha, data_ultima_alteracao, endereco, tipo_usuario) VALUES
('Alan Kano', 'alan.kano@gmail.com', 'alankano', 'alan123', CURRENT_DATE , 'SAO PAULO, SP', '1'),
('mozao', 'mozao@gmail.com', 'mozao', 'mozao123', CURRENT_DATE , 'SAO PAULO, SP', '2'),
('mozao', 'mozao1@gmail.com', 'mozao', 'mozao123', CURRENT_DATE , 'SAO PAULO, SP', '2');

CREATE TABLE tipoUsuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    idTipo VARCHAR(50) NOT NULL,
    tipo VARCHAR(255) NOT NULL
);