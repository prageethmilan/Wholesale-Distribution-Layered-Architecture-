package bo.custom.impl;

import bo.custom.CashierDashboardBO;
import dao.DAOFactory;
import dao.custom.CustomerDAO;
import dao.custom.ItemDAO;
import dao.custom.OrderIdDAO;
import dao.custom.SavedOrderDAO;
import db.DbConnection;
import dto.*;
import entity.Customer;
import entity.Item;
import entity.OrderId;
import entity.SavedOrder;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Prageeth Milan
 * @e-mail : prageethmilan99@gmail.com
 **/

public class CashierDashboardBOImpl implements CashierDashboardBO {

    private final OrderIdDAO orderIdDAO = (OrderIdDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER_ID);
    private final ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    private final CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);
    private final SavedOrderDAO savedOrderDAO = (SavedOrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SAVED_ORDER);

    @Override
    public String getOrderId() throws SQLException, ClassNotFoundException {
        return orderIdDAO.getOrderId();
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
    public String generateCustomerId() throws SQLException, ClassNotFoundException {
        return customerDAO.generateCustomerId();
    }

    @Override
    public boolean addCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException {
        Customer c = new Customer(dto.getCustomerId(), dto.getCustomerTitle(), dto.getCustomerName(), dto.getCustomerNIC(), dto.getAddress(), dto.getCity(), dto.getProvince(), dto.getPostalCode());
        return customerDAO.add(c);
    }

    @Override
    public boolean ifExistCustomer(String nic) throws SQLException, ClassNotFoundException {
        return customerDAO.ifExistCustomer(nic);
    }

    @Override
    public CustomerDTO searchCustomer(String nic) throws SQLException, ClassNotFoundException {
        Customer c = customerDAO.search(nic);
        return new CustomerDTO(c.getCustomerId(), c.getCustomerName(), c.getCustomerTitle(), c.getCustomerNIC(), c.getAddress(), c.getCity(), c.getProvince(), c.getPostalCode());
    }

    @Override
    public boolean updateCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException {
        Customer c = new Customer(dto.getCustomerId(), dto.getCustomerTitle(), dto.getCustomerName(), dto.getCustomerNIC(), dto.getAddress(), dto.getCity(), dto.getProvince(), dto.getPostalCode());
        return customerDAO.update(c);
    }

    @Override
    public ItemDTO searchItem(String code) throws SQLException, ClassNotFoundException {
        Item i = itemDAO.search(code);
        return new ItemDTO(i.getItemCode(), i.getDescription(), i.getPackSize(), i.getQuantityOnHand(), i.getDiscount(), i.getUnitPrice());
    }

    @Override
    public boolean saveOrder(OrderDTO dto) {
        Connection con = null;
        try {
            con = DbConnection.getInstance().getConnection();
            con.setAutoCommit(false);

            for (ItemDetailsDTO i : dto.getItems()) {
                SavedOrder savedOrder = new SavedOrder(dto.getOrderId(), dto.getCustNIC(), i.getItemCode(), i.getDescription(), i.getPackSize(), i.getQtyForSell(), i.getUnitPrice(), i.getDiscount(), i.getTotal());
                boolean add = savedOrderDAO.add(savedOrder);
                if (!add) {
                    con.rollback();
                    con.setAutoCommit(true);
                    return false;
                }
            }
            OrderId orderId = new OrderId(dto.getOrderId(), dto.getCustNIC());
            boolean addOrderId = orderIdDAO.add(orderId);
            if (!addOrderId) {
                con.rollback();
                con.setAutoCommit(true);
                return false;
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
