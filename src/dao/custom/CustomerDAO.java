package dao.custom;

import dao.CrudDAO;
import dto.CustomerDTO;
import entity.Customer;

import java.sql.SQLException;

/**
 * @author : Prageeth Milan
 * @e-mail : prageethmilan99@gmail.com
 **/


public interface CustomerDAO extends CrudDAO<Customer, String> {
    String generateCustomerId() throws SQLException, ClassNotFoundException;

    boolean ifExistCustomer(String nic) throws SQLException, ClassNotFoundException;
}
