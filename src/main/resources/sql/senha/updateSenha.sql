UPDATE usuarios
SET senha = :senha,
    data_ultima_alteracao = CURRENT_DATE
WHERE id = :id;

