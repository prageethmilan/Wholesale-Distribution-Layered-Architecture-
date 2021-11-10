package bo.custom.impl;

import bo.custom.SavedOrdersBO;
import dao.DAOFactory;
import dao.custom.*;
import db.DbConnection;
import dto.ItemDTO;
import dto.ItemDetailsDTO;
import dto.OrderDTO;
import dto.SavedOrderDTO;
import entity.Item;
import entity.Order;
import entity.OrderDetails;
import entity.SavedOrder;
import javafx.scene.control.Label;
import view.tm.SavedOrderDetailsTM;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Prageeth Milan
 * @e-mail : prageethmilan99@gmail.com
 **/

public class SavedOrdersBOImpl implements SavedOrdersBO {

    private final SavedOrderDAO savedOrderDAO = (SavedOrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SAVED_ORDER);
    private final ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    private final OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    private final OrderDetailDAO orderDetailDAO = (OrderDetailDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER_DETAIL);
    private final OrderIdDAO orderIdDAO = (OrderIdDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER_ID);

    @Override
    public ArrayList<SavedOrderDTO> getOrderIdAndNIC() throws SQLException, ClassNotFoundException {
        ArrayList<SavedOrder> orderIdAndNic = savedOrderDAO.getOrderIdAndNic();
        ArrayList<SavedOrderDTO> savedOrderDTOS = new ArrayList<>();
        for (SavedOrder order : orderIdAndNic) {
            savedOrderDTOS.add(new SavedOrderDTO(order.getoId(), order.getNIC(), order.getItemCode(), order.getDescription(), order.getPackSize(), order.getQtyForSell(), order.getUnitPrice(), order.getDiscount(), order.getTotal()));
        }
        return savedOrderDTOS;
    }

    @Override
    public ItemDTO getItemByDescription(String description) throws SQLException, ClassNotFoundException {
        Item i = itemDAO.getItemByDescription(description);
        return new ItemDTO(i.getItemCode(), i.getDescription(), i.getPackSize(), i.getQuantityOnHand(), i.getDiscount(), i.getUnitPrice());
    }

    @Override
    public ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException {
        ArrayList<Item> all = itemDAO.getAll();
        ArrayList<ItemDTO> allItems = new ArrayList<>();
        for (Item i : all) {
            allItems.add(new ItemDTO(i.getItemCode(), i.getDescription(), i.getPackSize(), i.getQuantityOnHand(), i.getDiscount(), i.getUnitPrice()));
        }
        return allItems;
    }

    @Override
    public ArrayList<SavedOrderDetailsTM> getOrderDetails(String newValue, Label lblCustomerNIC) throws SQLException, ClassNotFoundException {
        ArrayList<SavedOrder> details = savedOrderDAO.getOrderDetails(newValue, lblCustomerNIC);
        ArrayList<SavedOrderDetailsTM> savedOrderDetailsTMS = new ArrayList<>();
        for (SavedOrder detail : details) {
            savedOrderDetailsTMS.add(new SavedOrderDetailsTM(detail.getItemCode(), detail.getDescription(), detail.getPackSize(), detail.getQtyForSell(), detail.getUnitPrice(), detail.getDiscount(), detail.getTotal()));
        }
        return savedOrderDetailsTMS;
    }

    @Override
    public ItemDTO searchItem(String code) throws SQLException, ClassNotFoundException {
        Item i = itemDAO.search(code);
        return new ItemDTO(i.getItemCode(), i.getDescription(), i.getPackSize(), i.getQuantityOnHand(), i.getDiscount(), i.getUnitPrice());
    }

    @Override
    public boolean deleteOrderAndOrderIds(String oid) {
        Connection con = null;
        try {
            con = DbConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            boolean delete = savedOrderDAO.delete(oid);
            if (delete) {
                if (orderIdDAO.delete(oid)) {
                    con.commit();
                    return true;
                } else {
                    con.rollback();
                    return false;
                }
            } else {
                con.rollback();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean placeOrder(OrderDTO dto) {
        Connection con = null;
        try {
            con = DbConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            Order order = new Order(dto.getOrderId(), dto.getOrderDate(), dto.getOrderTime(), dto.getCustNIC(), dto.getDiscount(), dto.getCost());
            boolean add = orderDAO.add(order);
            if (!add) {
                con.rollback();
                con.setAutoCommit(true);
                return false;
            }
            for (ItemDetailsDTO i : dto.getItems()) {
                OrderDetails orderDetails = new OrderDetails(dto.getOrderId(), i.getItemCode(), i.getQtyForSell(), i.getDiscount(), i.getUnitPrice());
                boolean orderDetailsAdded = orderDetailDAO.add(orderDetails);
                if (!orderDetailsAdded) {
                    con.rollback();
                    con.setAutoCommit(true);
                    return false;
                }
            }

            boolean delete = savedOrderDAO.delete(dto.getOrderId());
            if (!delete) {
                con.rollback();
                con.setAutoCommit(true);
                return false;
            }

            for (ItemDetailsDTO i : dto.getItems()) {
                boolean updateQty = itemDAO.updateQty(i.getItemCode(), i.getQtyForSell());
                if (!updateQty) {
                    con.rollback();
                    con.setAutoCommit(true);
                    return false;
                }
            }

            con.commit();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
