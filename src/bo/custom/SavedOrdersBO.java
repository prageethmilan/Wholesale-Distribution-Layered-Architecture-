package bo.custom;

import bo.SuperBO;
import dto.ItemDTO;
import dto.OrderDTO;
import dto.SavedOrderDTO;
import javafx.scene.control.Label;
import view.tm.SavedOrderDetailsTM;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Prageeth Milan
 * @e-mail : prageethmilan99@gmail.com
 **/


public interface SavedOrdersBO extends SuperBO {
    ArrayList<SavedOrderDTO> getOrderIdAndNIC() throws SQLException, ClassNotFoundException;

    ItemDTO getItemByDescription(String description) throws SQLException, ClassNotFoundException;

    ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException;

    ArrayList<SavedOrderDetailsTM> getOrderDetails(String newValue, Label lblCustomerNIC) throws SQLException, ClassNotFoundException;

    ItemDTO searchItem(String code) throws SQLException, ClassNotFoundException;

    boolean deleteOrderAndOrderIds(String oid) throws SQLException, ClassNotFoundException;

    boolean placeOrder(OrderDTO order);
}
