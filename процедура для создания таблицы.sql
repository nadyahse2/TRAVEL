CREATE OR REPLACE PROCEDURE create_trips_table()
LANGUAGE plpgsql
AS $$
BEGIN
  IF NOT EXISTS (SELECT 1 FROM pg_tables WHERE schemaname = 'public' AND tablename = 'trips') THEN
    CREATE TABLE trips (
      trip_id SERIAL PRIMARY KEY,
      destination VARCHAR(255) NOT NULL,
      start_date DATE,
      end_date DATE,
      transport VARCHAR(255)
    );
  END IF;
END;
$$;