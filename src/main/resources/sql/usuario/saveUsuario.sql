INSERT INTO
usuarios (nome, email, login, senha, data_ultima_alteracao, endereco, tipo_usuario)
VALUES
(:nome, :email, :login, :senha, CURRENT_DATE , :endereco, :tipoUsuario)