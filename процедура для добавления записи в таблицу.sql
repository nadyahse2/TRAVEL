CREATE OR REPLACE PROCEDURE add_data(
    destination VARCHAR(255),
    start_date DATE,
    end_date DATE,
    transport VARCHAR(255)
)
LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO trips (destination, start_date, end_date, transport)
    VALUES (destination, start_date, end_date, transport);
END;
$$;