CREATE OR REPLACE PROCEDURE delete_by_destination(
    destination_name VARCHAR(255)
)
LANGUAGE plpgsql
AS $$
BEGIN
    DELETE FROM trips
    WHERE destination ILIKE '%' || destination_name || '%';  -- ILIKE для нечувствительного к регистру поиска
END;
$$;