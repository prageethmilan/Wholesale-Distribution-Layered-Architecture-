package bo.custom.impl;

import bo.custom.LoginFormBO;
import dao.DAOFactory;
import dao.custom.UserDAO;
import dto.UserDTO;
import entity.User;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Prageeth Milan
 * @e-mail : prageethmilan99@gmail.com
 **/

public class LoginFormBOImpl implements LoginFormBO {
    private final UserDAO userDAO = (UserDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USER);

    @Override
    public ArrayList<UserDTO> getAllUsers() throws SQLException, ClassNotFoundException {
        ArrayList<User> all = userDAO.getAll();
        ArrayList<UserDTO> allUsers = new ArrayList<>();
        for (User u : all) {
            allUsers.add(new UserDTO(u.getFirstName(), u.getLastName(), u.getUserType(), u.getUserName(), u.getPassword()));
        }
        return allUsers;
    }

    @Override
    public ArrayList<UserDTO> searchAllUsers(String userType) throws SQLException, ClassNotFoundException {
        ArrayList<User> all = userDAO.searchAll(userType);
        ArrayList<UserDTO> users = new ArrayList<>();
        for (User u : all) {
            users.add(new UserDTO(u.getFirstName(), u.getLastName(), u.getUserType(), u.getUserName(), u.getPassword()));
        }
        return users;
    }
}
