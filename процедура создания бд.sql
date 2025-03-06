CREATE OR REPLACE PROCEDURE create_database()
LANGUAGE plpgsql
AS $$
BEGIN
    -- Проверяем, существует ли уже база данных с таким именем
    IF EXISTS (SELECT 1 FROM pg_database WHERE datname = 'travel_db') THEN
        RAISE NOTICE 'Database travel_db already exists';
    ELSE
        -- Создаем базу данных
        EXECUTE FORMAT('CREATE DATABASE travel_db');
       
    END IF;
END;
$$;