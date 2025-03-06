CREATE OR REPLACE FUNCTION create_user_with_role(
    username VARCHAR(63),
    password TEXT,
    role_name NAME
)
RETURNS VOID
LANGUAGE plpgsql
SECURITY DEFINER 
AS $$
DECLARE
    role_exists BOOLEAN;
BEGIN
   
    SELECT EXISTS (
        SELECT 1
        FROM pg_roles
        WHERE rolname = role_name
    ) INTO role_exists;

    
    IF NOT role_exists THEN
        RAISE EXCEPTION 'Роль "%" не существует', role_name;
    END IF;

    
    EXECUTE FORMAT('CREATE USER %I WITH PASSWORD %L ROLE %I', username, password, role_name);

EXCEPTION
    WHEN DUPLICATE_OBJECT THEN
        RAISE NOTICE 'Пользователь "%" уже существует', username;
    WHEN OTHERS THEN
        RAISE EXCEPTION 'Ошибка при создании пользователя "%": %', username, SQLERRM;
END;
$$;