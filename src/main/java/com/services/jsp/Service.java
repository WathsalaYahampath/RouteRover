package com.services.jsp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.Time;

public class Service {

    private String jdbcUrl = "jdbc:mysql://172.187.178.153:3306/isec_assessment2";
    private String jdbcUser = "isec";
    private String jdbcPassword = "EUHHaYAmtzbv";

    // Method to establish a database connection
    private Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);
    }

    // Method to close the database connection
    private void closeConnection(Connection connection) throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    // Method to add a reservation to the database
    public int addReservation(String location, String mileage, String vehicleNumber, String message, String userName, String reservationDate, String preferredTime) throws ClassNotFoundException, SQLException {
        Connection connection = null;
        Time time = convertToSqlTime(preferredTime);
        try {
            connection = getConnection();

            // Convert mileage to an integer
            int parsedMileage;
            try {
                parsedMileage = Integer.parseInt(mileage);
            } catch (NumberFormatException e) {
                // Handle invalid mileage format
                return -3;
            }

            // Parse a date string into a Date object
            Date parsedDate;
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                parsedDate = dateFormat.parse(reservationDate);
            } catch (ParseException e) {
                // Handle invalid date format
                return -4;
            }

            

            // Perform the insertion into the database using a prepared statement
            String insertQuery = "INSERT INTO vehicle_service (date, time, location,vehicle_no,mileage, message, username) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setDate(1, new java.sql.Date(parsedDate.getTime()));
            preparedStatement.setTime(2, time);
            preparedStatement.setString(3, location);
            preparedStatement.setString(4, vehicleNumber);
            preparedStatement.setInt(5, parsedMileage);    
            preparedStatement.setString(6, message);
            preparedStatement.setString(7, userName);

            int rowsAffected = preparedStatement.executeUpdate();
            preparedStatement.close();

            closeConnection(connection);
            return rowsAffected;

        }catch (SQLException e) {
            e.printStackTrace();
            
            return -1;
        }
    }

    // Method to retrieve future reservations for a user
    public ResultSet displayFutureReservations(String userName) throws ClassNotFoundException, SQLException {
        Connection connection = null;
        ResultSet futureReservations = null;
        try {
            connection = getConnection();

            // Get current date and time in the required format
            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentDateTime = dateTimeFormat.format(new Date());

            // Use the current date and time parameter in the SQL query
            String selectQuery = "SELECT * FROM vehicle_service WHERE username = ? AND CONCAT(date, ' ', time) > ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, currentDateTime);

            futureReservations = preparedStatement.executeQuery();
            
        }catch (SQLException e) {
			e.printStackTrace();
		}
        return futureReservations;
    }

    // Method to retrieve past reservations for a user
    public ResultSet displayPastReservations(String userName) throws ClassNotFoundException, SQLException {
        Connection connection = null;
        ResultSet pastReservations = null;
        try {
            connection = getConnection();

            // Get current date and time in the required format
            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentDateTime = dateTimeFormat.format(new Date());

            // Use the current date and time parameter in the SQL query
            String selectQuery = "SELECT * FROM vehicle_service WHERE username = ? AND CONCAT(date, ' ', time) <= ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, currentDateTime);

            pastReservations = preparedStatement.executeQuery();
        } catch (SQLException e) {
			e.printStackTrace();	
		}
        return pastReservations;
    }

    // Method to delete a reservation by ID
    public int deleteReservations(int bookingId) throws ClassNotFoundException, SQLException {
        Connection connection = null;
        try {
            connection = getConnection();
            String deleteQuery = "DELETE FROM vehicle_service WHERE booking_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
            preparedStatement.setInt(1, bookingId);

            return preparedStatement.executeUpdate();
        } finally {
            closeConnection(connection);
        }
    }
    
    
    private static Time convertToSqlTime(String timeString) {
        try {
            // Parse the time string to java.util.Date
            SimpleDateFormat format = new SimpleDateFormat("hh:mm a");
            Date date = format.parse(timeString);

            // Convert java.util.Date to java.sql.Time
            return new Time(date.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return null; // Handle parsing error appropriately
        }
    }

}
