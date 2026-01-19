UPDATE usuarios
SET nome = :nome,
    email = :email,
    login = :login,
    data_ultima_alteracao = CURRENT_DATE,
    endereco = :endereco,
    tipo_usuario = :tipoUsuario
WHERE id = :id;

