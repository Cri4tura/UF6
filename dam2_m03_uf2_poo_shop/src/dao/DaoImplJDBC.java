package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Employee;

public class DaoImplJDBC implements Dao {
    private Connection connection;

    @Override
    public void connect() throws SQLException {
        try {
            String url = "jdbc:mysql://localhost:3306/shop";
            String user = "root";
            String pass = "root";
            this.connection = DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Connection failed!", e);
        }
    }

    @Override
    public Employee getEmployee(int employeeId, String password) {
        Employee employee = null;
        try {
            String query = "SELECT * FROM employee WHERE employeeId = ? AND password = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, employeeId);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                employee = new Employee(employeeId, rs.getString("name"), password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employee;
    }

    @Override
    public void disconnect() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
}
