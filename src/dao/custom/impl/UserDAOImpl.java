package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.UserDAO;
import dto.UserDTO;
import entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Prageeth Milan
 * @e-mail : prageethmilan99@gmail.com
 **/

public class UserDAOImpl implements UserDAO {

    @Override
    public boolean add(User user) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO `UserDetail` VALUES (?,?,?,?,?)", user.getFirstName(), user.getLastName(), user.getUserType(), user.getUserName(), user.getPassword());
    }

    @Override
    public User search(String s) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public boolean update(User userDTO) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public ArrayList<User> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<User> allUsers = new ArrayList<>();
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM `UserDetail`");
        while (rst.next()) {
            allUsers.add(new User(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4), rst.getString(5)));
        }
        return allUsers;
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public ArrayList<User> searchAll(String userType) throws SQLException, ClassNotFoundException {
        ArrayList<User> users = new ArrayList<>();
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM `UserDetail` WHERE userType=?", userType);
        while (rst.next()) {
            users.add(new User(rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4),rst.getString(5)));
        }
        return users;
    }
}
