package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.SavedOrderDAO;
import dto.ItemDetailsDTO;
import dto.OrderDTO;
import dto.SavedOrderDTO;
import entity.SavedOrder;
import javafx.scene.control.Label;
import view.tm.SavedOrderDetailsTM;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Prageeth Milan
 * @e-mail : prageethmilan99@gmail.com
 **/

public class SavedOrderDAOImpl implements SavedOrderDAO {
    @Override
    public boolean add(SavedOrder dto) throws SQLException, ClassNotFoundException {
            return CrudUtil.executeUpdate("INSERT INTO `SavedOrder` VALUES (?,?,?,?,?,?,?,?,?)", dto.getoId(), dto.getNIC(), dto.getItemCode(), dto.getDescription(), dto.getPackSize(), dto.getQtyForSell(), dto.getUnitPrice(), dto.getDiscount(), dto.getTotal());
    }

    @Override
    public SavedOrder search(String s) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public boolean update(SavedOrder orderDTO) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public ArrayList<SavedOrder> getAll() throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public boolean delete(String oid) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("DELETE FROM `SavedOrder` WHERE oId=?", oid);
    }

    @Override
    public ArrayList<SavedOrder> getOrderDetails(String newValue, Label lblCustomerNIC) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM `SavedOrder` WHERE oId=?", newValue);
        ArrayList<SavedOrder> items = new ArrayList<>();
        while (rst.next()) {
            items.add(new SavedOrder(rst.getString(1),rst.getString(2),rst.getString(3), rst.getString(4), rst.getString(5), rst.getInt(6), rst.getDouble(7), rst.getDouble(8), rst.getDouble(9)));
            lblCustomerNIC.setText(rst.getString(2));
        }
        return items;
    }

    @Override
    public ArrayList<SavedOrder> getOrderIdAndNic() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM `SavedOrder`");
        ArrayList<SavedOrder> savedOrders = new ArrayList<>();
        while (rst.next()) {
            savedOrders.add(new SavedOrder(rst.getString(1), rst.getString(2),rst.getString(3),rst.getString(4),rst.getString(5),rst.getInt(6),rst.getDouble(7),rst.getDouble(8),rst.getDouble(9)));
        }
        return savedOrders;
    }

}