package bo.custom.impl;

import bo.custom.AdminDashboardBO;
import dao.DAOFactory;
import dao.custom.CustomerDAO;
import dao.custom.ItemDAO;
import dao.custom.UserDAO;
import dto.CustomerDTO;
import dto.ItemDTO;
import dto.UserDTO;
import entity.Customer;
import entity.Item;
import entity.User;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Prageeth Milan
 * @e-mail : prageethmilan99@gmail.com
 **/

public class AdminDashBoardBOImpl implements AdminDashboardBO {
    private final CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);
    private final ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    private final UserDAO userDAO = (UserDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USER);

    @Override
    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException {
        ArrayList<Customer> all = customerDAO.getAll();
        ArrayList<CustomerDTO> allCustomers = new ArrayList<>();
        for (Customer customer : all) {
            allCustomers.add(new CustomerDTO(customer.getCustomerId(), customer.getCustomerName(), customer.getCustomerTitle(), customer.getCustomerNIC(), customer.getAddress(), customer.getCity(), customer.getProvince(), customer.getPostalCode()));
        }
        return allCustomers;
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
    public String generateItemCode() throws SQLException, ClassNotFoundException {
        return itemDAO.generateItemCode();
    }

    @Override
    public boolean updateItem(ItemDTO dto) throws SQLException, ClassNotFoundException {
        Item i = new Item(dto.getItemCode(), dto.getDescription(), dto.getPackSize(), dto.getUnitPrice(), dto.getQuantityOnHand(), dto.getDiscount());
        return itemDAO.update(i);
    }

    @Override
    public boolean addItem(ItemDTO dto) throws SQLException, ClassNotFoundException {
        Item i = new Item(dto.getItemCode(), dto.getDescription(), dto.getPackSize(), dto.getUnitPrice(), dto.getQuantityOnHand(), dto.getDiscount());
        return itemDAO.add(i);
    }

    @Override
    public boolean deleteItem(String code) throws SQLException, ClassNotFoundException {
        return itemDAO.delete(code);
    }

    @Override
    public ItemDTO searchItem(String code) throws SQLException, ClassNotFoundException {
        Item item = itemDAO.search(code);
        return new ItemDTO(item.getItemCode(), item.getDescription(), item.getPackSize(), item.getQuantityOnHand(), item.getDiscount(), item.getUnitPrice());
    }

    @Override
    public boolean deleteCustomer(String nic) throws SQLException, ClassNotFoundException {
        return customerDAO.delete(nic);
    }

    @Override
    public boolean signUp(UserDTO dto) throws SQLException, ClassNotFoundException {
        User user = new User(dto.getFirstName(), dto.getLastName(), dto.getUserType(), dto.getUserName(), dto.getPassword());
        return userDAO.add(user);
    }
}
