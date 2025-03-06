CREATE OR REPLACE PROCEDURE update_trip(
    p_trip_id INTEGER,
    p_destination VARCHAR(255),
    p_start_date DATE,
    p_end_date DATE,
    p_transport VARCHAR(255)
)
LANGUAGE plpgsql
AS $$
BEGIN
    UPDATE trips
    SET destination = p_destination,
        start_date = p_start_date,
        end_date = p_end_date,
        transport = p_transport
    WHERE trip_id = p_trip_id;
END;
$$;