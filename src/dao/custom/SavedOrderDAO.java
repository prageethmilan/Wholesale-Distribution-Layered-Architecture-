package dao.custom;

import dao.CrudDAO;
import entity.SavedOrder;
import javafx.scene.control.Label;
import view.tm.SavedOrderDetailsTM;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Prageeth Milan
 * @e-mail : prageethmilan99@gmail.com
 **/


public interface SavedOrderDAO extends CrudDAO<SavedOrder, String> {

    ArrayList<SavedOrder> getOrderDetails(String newValue, Label lblCustomerNIC) throws SQLException, ClassNotFoundException;

    ArrayList<SavedOrder> getOrderIdAndNic() throws SQLException, ClassNotFoundException;
}
