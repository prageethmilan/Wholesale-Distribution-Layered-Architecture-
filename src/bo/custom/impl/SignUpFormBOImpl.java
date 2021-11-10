package bo.custom.impl;

import bo.custom.SignUpFormBO;
import dao.DAOFactory;
import dao.custom.UserDAO;
import dto.UserDTO;
import entity.User;

import java.sql.SQLException;

/**
 * @author : Prageeth Milan
 * @e-mail : prageethmilan99@gmail.com
 **/

public class SignUpFormBOImpl implements SignUpFormBO {
    private final UserDAO userDAO = (UserDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USER);

    @Override
    public boolean signUpUser(UserDTO dto) throws SQLException, ClassNotFoundException {
        User user = new User(dto.getFirstName(), dto.getLastName(), dto.getUserType(), dto.getUserName(), dto.getPassword());
        return userDAO.add(user);
    }

}
