package dao.custom;

import dao.CrudDAO;
import dto.SavedOrderDTO;
import entity.OrderId;

import java.sql.SQLException;

/**
 * @author : Prageeth Milan
 * @e-mail : prageethmilan99@gmail.com
 **/


public interface OrderIdDAO extends CrudDAO<OrderId, String> {
    String getOrderId() throws SQLException, ClassNotFoundException;
}
