package bo.custom;

import bo.SuperBO;
import dto.UserDTO;

import java.sql.SQLException;

/**
 * @author : Prageeth Milan
 * @e-mail : prageethmilan99@gmail.com
 **/


public interface SignUpFormBO extends SuperBO {
    boolean signUpUser(UserDTO dto) throws SQLException, ClassNotFoundException;
}
