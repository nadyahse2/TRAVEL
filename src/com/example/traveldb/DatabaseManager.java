package com.example.traveldb;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String POSTGRES_DB_URL = "jdbc:postgresql://localhost:5432/postgres?characterEncoding=UTF-8";
    private static final String TRAVEL_DB_URL = "jdbc:postgresql://localhost:5432/travel_db?characterEncoding=UTF-8";
    private String dbUser;
    private String dbPass;
    private String currentUserRole;
    private Connection postgresConnection;
    private Connection travelConnection;

    public DatabaseManager(String dbUser, String dbPass, String currentUserRole) {
        this.dbUser = dbUser;
        this.dbPass = dbPass;
        this.currentUserRole = currentUserRole;
    }
    
    public String getCurrentUserRole() {
        return currentUserRole;
    }

    public void connectToPostgres() throws SQLException {
    	
        try {
            postgresConnection = DriverManager.getConnection(POSTGRES_DB_URL, dbUser, dbPass);
            System.out.println("Connected to postgres database as " + dbUser);

        } catch (SQLException e) {
            System.err.println("Connection to postgres failed: " + e.getMessage());
            throw e;
        }
    }
    
    public void connectToTravel() throws SQLException {
        try {
            travelConnection = DriverManager.getConnection(TRAVEL_DB_URL, dbUser, dbPass);
            System.out.println("Connected to travel_db database as " + dbUser);
        } catch (SQLException e) {
            System.err.println("Connection to travel_db failed: " + e.getMessage());
            throw e;
        }
    }
    
    public void disconnect() {
        try {
            if (postgresConnection != null && !postgresConnection.isClosed()) {
                postgresConnection.close();
                System.out.println("Disconnected from postgres database.");
            }
            if (travelConnection != null && !travelConnection.isClosed()) {
                travelConnection.close();
                System.out.println("Disconnected from travel_db database.");
            }
        } catch (SQLException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }

    public void createDatabase() throws SQLException {
        String sql = "CALL create_database()";
        try (CallableStatement statement = postgresConnection.prepareCall(sql)) {
            statement.execute();
            System.out.println("Database travel_db created (if not exists).");
        } catch (SQLException e) {
            System.err.println("Error creating database: " + e.getMessage());
            throw e;
        }
    }

    public void dropDatabase() throws SQLException {
        String sql = "call delete_database()";
        try (CallableStatement statement = postgresConnection.prepareCall(sql)) {
            statement.execute();
            System.out.println("Database travel_db dropped (if exists).");
        } catch (SQLException e) {
            System.err.println("Error dropping database: " + e.getMessage());
            throw e;
        }
    }
    
    public void insertData(String destination, java.sql.Date startDate, java.sql.Date endDate, String transport) throws SQLException {
        String sql = "call add_data(?, ?, ?, ?)";
        try (CallableStatement statement = travelConnection.prepareCall(sql)) {
            statement.setString(1, destination);
            statement.setDate(2, startDate);
            statement.setDate(3, endDate);
            statement.setString(4, transport);
            statement.execute();
            System.out.println("Data inserted.");
        } catch (SQLException e) {
            System.err.println("Error inserting data: " + e.getMessage());
            throw e;
        }
    }

    
    public List<Trip> searchByDestination(String destination) throws SQLException {
        List<Trip> trips = new ArrayList<>();
        String sql = "SELECT p_trip_id, p_destination, p_start_date, p_end_date, p_transport FROM search_by_destination(?)"; 
        try (PreparedStatement statement = travelConnection.prepareStatement(sql)) {
            statement.setString(1, destination);
           
            try (ResultSet resultSet = (ResultSet) statement.executeQuery()) { 
                while (resultSet.next()) {
                    Trip trip = new Trip(
                            resultSet.getInt("p_trip_id"),
                            resultSet.getString("p_destination"),
                            resultSet.getDate("p_start_date"),
                            resultSet.getDate("p_end_date"),
                            resultSet.getString("p_transport")
                    );
                    trips.add(trip);
                    
                }
            } catch (SQLException e) {
                System.err.println("Error processing ResultSet: " + e.getMessage());
                throw e;
            }
        } catch (SQLException e) {
            System.err.println("Error searching by destination: " + e.getMessage());
            throw e;
        }
		return trips;
		
        
    }

  public void updateData(int tripId, String destination, java.sql.Date startDate, java.sql.Date endDate, String transport) throws SQLException {
        String sql = "call update_trip(?, ?, ?, ?, ?)";
        try (CallableStatement statement = travelConnection.prepareCall(sql)) {
            statement.setInt(1, tripId);
            statement.setString(2, destination);
            statement.setDate(3, startDate);
            statement.setDate(4, endDate);
            statement.setString(5, transport);
            statement.execute();
            System.out.println("Data updated.");
        } catch (SQLException e) {
            System.err.println("Error updating data: " + e.getMessage());
            throw e;
        }
    }

    public void deleteData(String destination) throws SQLException {
        String sql = "call delete_by_destination(?)";
        try (CallableStatement statement = travelConnection.prepareCall(sql)) {
            statement.setString(1, destination);
            statement.execute();
            System.out.println("Data deleted.");
        } catch (SQLException e) {
            System.err.println("Error deleting data: " + e.getMessage());
            throw e;
        }
    }

    public void createTripsTable() throws SQLException {
        String sql = "call create_trips_table()";
        try (CallableStatement statement = travelConnection.prepareCall(sql)) {
            statement.execute();
            System.out.println("Table created.");
        } catch (SQLException e) {
            System.err.println("Error creating table: " + e.getMessage());
            throw e;
        }
    }

    public void dropTripsTable() throws SQLException {
        String sql = "call drop_trips_table()";
        try (CallableStatement statement = travelConnection.prepareCall(sql)) {
            statement.execute();
            System.out.println("Table dropped.");
        } catch (SQLException e) {
            System.err.println("Error dropping table: " + e.getMessage());
            throw e;
        }
    }

    public void truncateTripsTable() throws SQLException {
        String sql = "call clear_table()";
        try (CallableStatement statement = travelConnection.prepareCall(sql)) {
            statement.execute();
            System.out.println("Table truncated.");
        } catch (SQLException e) {
            System.err.println("Error truncating table: " + e.getMessage());
            throw e;
        }
    }

   
    
}
