package dao.custom;

import dao.CrudDAO;
import dto.ItemDTO;
import entity.Item;

import java.sql.SQLException;

/**
 * @author : Prageeth Milan
 * @e-mail : prageethmilan99@gmail.com
 **/


public interface ItemDAO extends CrudDAO<Item, String> {
    String generateItemCode() throws SQLException, ClassNotFoundException;

    Item getItemByDescription(String description) throws SQLException, ClassNotFoundException;

    boolean updateQty(String itemCode, int qtyForSell) throws SQLException, ClassNotFoundException;
}
