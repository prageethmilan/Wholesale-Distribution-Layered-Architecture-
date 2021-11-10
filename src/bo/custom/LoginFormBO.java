package bo.custom;

import bo.SuperBO;
import dto.UserDTO;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Prageeth Milan
 * @e-mail : prageethmilan99@gmail.com
 **/


public interface LoginFormBO extends SuperBO {
    ArrayList<UserDTO> getAllUsers() throws SQLException, ClassNotFoundException;

    ArrayList<UserDTO> searchAllUsers(String userType) throws SQLException, ClassNotFoundException;
}
