CREATE OR REPLACE FUNCTION search_by_destination(
    destination_name VARCHAR(255)
)
RETURNS TABLE (
    p_trip_id INTEGER,
    p_destination VARCHAR(255),
    p_start_date DATE,
    p_end_date DATE,
    p_transport VARCHAR(255)
)
LANGUAGE plpgsql
AS $$
BEGIN
    RETURN QUERY
    SELECT trip_id as p_trip_id , destination as p_destination , start_date as p_start_date, end_date as p_end_date, transport as p_transport
    FROM trips
    WHERE destination ILIKE '%' || destination_name || '%'; -- ILIKE для нечувствительного к регистру поиска
END;
$$;