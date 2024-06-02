package model;

import java.sql.SQLException;

import dao.Dao;
import dao.DaoImplJDBC;

public class Employee {
    private int employeeId;
    private String name;
    private String password;
    private Dao dao;

    public Employee(int employeeId, String name, String password) {
        this.employeeId = employeeId;
        this.name = name;
        this.password = password;
        this.dao = new DaoImplJDBC();
    }

    public Employee(String name) {
        this.name = name;
        this.dao = new DaoImplJDBC();
    }

    public boolean login(int employeeId, String password) {
        try {
            dao.connect();
            Employee employee = dao.getEmployee(employeeId, password);
            dao.disconnect();
            return employee != null;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

  
}
