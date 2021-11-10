package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.OrderIdDAO;
import dto.SavedOrderDTO;
import entity.OrderId;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Prageeth Milan
 * @e-mail : prageethmilan99@gmail.com
 **/

public class OrderIdDAOImpl implements OrderIdDAO {
    @Override
    public boolean add(OrderId orderId) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO OrderId VALUES(?,?)", orderId.getOrderId(), orderId.getNic());
    }

    @Override
    public OrderId search(String s) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public boolean update(OrderId savedOrderDTO) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public ArrayList<OrderId> getAll() throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public boolean delete(String oid) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("DELETE FROM OrderId WHERE oId=?", oid);
    }

    @Override
    public String getOrderId() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT oId FROM OrderId ORDER BY oId DESC LIMIT 1");
        if (rst.next()) {

            int tempId = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempId = tempId + 1;
            if (tempId <= 9) {
                return "O-00" + tempId;
            } else if (tempId <= 99) {
                return "O-0" + tempId;
            } else {
                return "O-" + tempId;
            }

        } else {
            return "O-001";
        }
    }
}
