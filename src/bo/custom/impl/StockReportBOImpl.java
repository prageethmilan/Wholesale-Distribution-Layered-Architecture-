package bo.custom.impl;

import bo.custom.StockReportBO;
import dao.DAOFactory;
import dao.custom.ItemDAO;
import dto.ItemDTO;
import entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Prageeth Milan
 * @e-mail : prageethmilan99@gmail.com
 **/

public class StockReportBOImpl implements StockReportBO {
    private final ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);

    @Override
    public ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException {
        ArrayList<Item> all = itemDAO.getAll();
        ArrayList<ItemDTO> allItems = new ArrayList<>();
        for (Item i : all) {
            allItems.add(new ItemDTO(i.getItemCode(), i.getDescription(), i.getPackSize(), i.getQuantityOnHand(), i.getDiscount(), i.getUnitPrice()));
        }
        return allItems;
    }
}
