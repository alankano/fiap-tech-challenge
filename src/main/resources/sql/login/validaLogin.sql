SELECT *
FROM usuarios
WHERE login = :login
  AND senha = :senha;