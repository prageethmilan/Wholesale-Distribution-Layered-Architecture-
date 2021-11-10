package dao.custom;

import dao.CrudDAO;
import entity.User;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Prageeth Milan
 * @e-mail : prageethmilan99@gmail.com
 **/


public interface UserDAO extends CrudDAO<User, String> {
    ArrayList<User> searchAll(String userType) throws SQLException, ClassNotFoundException;
}
