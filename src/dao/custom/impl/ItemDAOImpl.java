package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.ItemDAO;
import dto.ItemDTO;
import entity.Item;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Prageeth Milan
 * @e-mail : prageethmilan99@gmail.com
 **/

public class ItemDAOImpl implements ItemDAO {
    @Override
    public boolean add(Item dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO Item VALUES (?,?,?,?,?,?)", dto.getItemCode(), dto.getDescription(), dto.getPackSize(), dto.getUnitPrice(), dto.getQuantityOnHand(), dto.getDiscount());
    }

    @Override
    public Item search(String code) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Item WHERE itemCode=?", code);
        if (rst.next()) {
            return new Item(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4),
                    rst.getInt(5),
                    rst.getDouble(6)
            );
        } else {
            return null;
        }
    }

    @Override
    public boolean update(Item dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("UPDATE Item SET description=?, packSize=?, unitPrice=?, qtyOnHand=?, discount=? WHERE itemCode=?", dto.getDescription(), dto.getPackSize(), dto.getUnitPrice(), dto.getQuantityOnHand(), dto.getDiscount(), dto.getItemCode());
    }

    @Override
    public ArrayList<Item> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Item");
        ArrayList<Item> items = new ArrayList<>();
        while (rst.next()) {
            items.add(new Item(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4),
                    rst.getInt(5),
                    rst.getDouble(6)
            ));
        }
        return items;
    }

    @Override
    public boolean delete(String code) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("DELETE FROM Item WHERE itemCode=?", code);
    }

    @Override
    public String generateItemCode() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT itemCode FROM Item ORDER BY itemCode DESC LIMIT 1");
        if (rst.next()) {

            int tempCode = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempCode = tempCode + 1;
            if (tempCode <= 9) {
                return "I-00" + tempCode;
            } else if (tempCode <= 99) {
                return "I-0" + tempCode;
            } else {
                return "I-" + tempCode;
            }

        } else {
            return "I-001";
        }
    }

    @Override
    public Item getItemByDescription(String description) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Item WHERE description=?", description);
        if (rst.next()) {
            return new Item(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4),
                    rst.getInt(5),
                    rst.getDouble(6)
            );
        } else {
            return null;
        }
    }

    @Override
    public boolean updateQty(String itemCode, int qtyForSell) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("UPDATE Item SET qtyOnHand=(qtyOnHand-" + qtyForSell + ") WHERE itemCode='" + itemCode + "'");
    }
}