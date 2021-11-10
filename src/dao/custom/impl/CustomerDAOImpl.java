package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.CustomerDAO;
import entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Prageeth Milan
 * @e-mail : prageethmilan99@gmail.com
 **/

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public boolean add(Customer c) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO Customer VALUES(?,?,?,?,?,?,?,?)", c.getCustomerId(), c.getCustomerTitle(), c.getCustomerName(), c.getCustomerNIC(), c.getAddress(), c.getCity(), c.getProvince(), c.getPostalCode());
    }

    @Override
    public Customer search(String nic) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Customer WHERE custNIC=?", nic);
        if (rst.next()) {
            return new Customer(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7),
                    rst.getString(8)
            );
        } else {
            return null;
        }
    }

    @Override
    public boolean update(Customer c) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("UPDATE Customer SET custNIC=?, custTitle=?, custName=?, custAddress=?, city=?, province=?, postalCode=? WHERE custID=?", c.getCustomerNIC(), c.getCustomerTitle(), c.getCustomerName(), c.getAddress(), c.getCity(), c.getProvince(), c.getPostalCode(), c.getCustomerId());
    }

    @Override
    public ArrayList<Customer> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Customer ORDER BY custID ASC;");
        ArrayList<Customer> allCustomers = new ArrayList<>();
        while (rst.next()) {
            allCustomers.add(new Customer(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4), rst.getString(5), rst.getString(6), rst.getString(7), rst.getString(8)));
        }
        return allCustomers;
    }

    @Override
    public boolean delete(String nic) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("DELETE FROM Customer WHERE custNIC=?", nic);
    }

    @Override
    public String generateCustomerId() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT custID FROM Customer ORDER BY custID DESC LIMIT 1");
        if (rst.next()) {
            int tempId = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempId = tempId + 1;
            if (tempId <= 9) {
                return "C-00" + tempId;
            } else if (tempId <= 99) {
                return "C-0" + tempId;
            } else {
                return "C-" + tempId;
            }
        } else {
            return "C-001";
        }
    }

    @Override
    public boolean ifExistCustomer(String nic) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeQuery("SELECT custNIC FROM Customer WHERE custNIC=?", nic).next();
    }
}
