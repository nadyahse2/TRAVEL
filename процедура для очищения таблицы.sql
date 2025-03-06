CREATE OR REPLACE PROCEDURE clear_table()
LANGUAGE plpgsql
AS $$
BEGIN
    TRUNCATE TABLE trips;
END;
$$;