package controller;

import bo.BOFactory;
import bo.custom.StockReportBO;
import dto.ItemDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import view.tm.ItemTM;

import java.sql.SQLException;
import java.util.ArrayList;

public class StockReportController {

    public TableView<ItemTM> tblItem;
    public TableColumn colItemCode;
    public TableColumn colDescription;
    public TableColumn colPackSize;
    public TableColumn colQtyOnHand;
    public TableColumn colDiscount;
    public TableColumn colUnitPrice;
    private final StockReportBO stockReportBO = (StockReportBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.STOCK);

    public void initialize() {
        try {
            colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
            colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
            colPackSize.setCellValueFactory(new PropertyValueFactory<>("packSize"));
            colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("quantityOnHand"));
            colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
            colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
            setDataToTable(stockReportBO.getAllItems());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setDataToTable(ArrayList<ItemDTO> allItems) {
        ObservableList<ItemTM> obList = FXCollections.observableArrayList();
        allItems.forEach(e -> {
            obList.add(new ItemTM(e.getItemCode(), e.getDescription(), e.getPackSize(), e.getQuantityOnHand(), e.getDiscount(), e.getUnitPrice()));
        });
        tblItem.setItems(obList);
    }
}
