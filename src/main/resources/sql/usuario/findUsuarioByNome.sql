SELECT *
FROM usuarios
WHERE LOWER(nome)
LIKE CONCAT('%', :nome, '%')