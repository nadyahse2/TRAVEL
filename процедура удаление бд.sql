CREATE OR REPLACE PROCEDURE delete_database()
LANGUAGE plpgsql
AS $$ 
BEGIN
   EXECUTE FORMAT('REVOKE CONNECT ON DATABASE %I FROM PUBLIC; -- отзываем право на подключение к базе данных - необходимо для удаления 
                   SELECT pg_terminate_backend(pid) -- функция для завершения процесса, pid аргумент - идентификатор процесса
				   FROM pg_stat_activity - системное представление(динамическая генерация данных в отличии от обычных views )
				   WHERE datename = %L
				   AND pid <> pg_backend_pid(); -- исключаем текущее соединение
				   DROP DATABASE %K','travel_db','travel_db','travel_db');
END;
$$;
