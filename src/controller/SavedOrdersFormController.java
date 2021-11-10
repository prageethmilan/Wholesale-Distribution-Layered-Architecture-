package controller;

import bo.BOFactory;
import bo.custom.SavedOrdersBO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dto.ItemDTO;
import dto.ItemDetailsDTO;
import dto.OrderDTO;
import dto.SavedOrderDTO;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import view.tm.CartTM;
import view.tm.SavedOidAndNicDetailsTM;
import view.tm.SavedOrderDetailsTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

public class SavedOrdersFormController {

    private final SavedOrdersBO savedOrdersBO = (SavedOrdersBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.SAVED_ORDER);
    public TableView<SavedOidAndNicDetailsTM> tblOrderIdAndNic;
    public TableColumn colOid;
    public TableColumn colNIC;
    public JFXComboBox<String> cmbOrderId;
    public TableView<CartTM> tblItemDetails;
    public TableColumn colItemCode;
    public TableColumn colDescription;
    public TableColumn colPackSize;
    public TableColumn colQuantity;
    public TableColumn colUnitPrice;
    public TableColumn colDiscount;
    public TableColumn colTotal;
    public Label lblCustomerNIC;
    public JFXComboBox<String> cmbItemDescription;
    public JFXTextField txtItemCode;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtPackSize;
    public JFXTextField txtQuantityOnHand;
    public JFXTextField txtDiscount;
    public JFXTextField txtQty;
    public Label lblGrossAmount;
    public Label lblTotalDiscount;
    public Label lblNetAmount;
    public Label lblTime;
    public Label lblDate;
    public JFXButton btnAddToCart;
    public JFXTextField txtUpdateQty;
    public TextField txtCash;
    public Label lblBalance;
    public AnchorPane savedOrderFormContext;
    public JFXButton btnPlaceOrder;
    double netAmount = 0;
    double balance = 0;
    int cartSelectedRow = -1;
    ObservableList<CartTM> obList = FXCollections.observableArrayList();

    public void initialize() {
        loadDateAndTime();
        btnAddToCart.setDisable(true);
        btnPlaceOrder.setDisable(true);
        try {
            colOid.setCellValueFactory(new PropertyValueFactory<>("orderId"));
            colNIC.setCellValueFactory(new PropertyValueFactory<>("NIC"));
            setOrderIdAndNicToTable(savedOrdersBO.getOrderIdAndNIC());
            loadItemDescription();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        cmbOrderId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                obList.clear();
                btnAddToCart.setDisable(false);
                setItemDataToTable(newValue);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        cmbItemDescription.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if(newValue!=null) {
                    setItemData(newValue);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    private void loadDateAndTime() {
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(f.format(date));
        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            lblTime.setText(
                    currentTime.getHour() + ":" + currentTime.getMinute() + ":" + currentTime.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

    private void setItemData(String newValue) throws SQLException, ClassNotFoundException {
        ItemDTO i1 = savedOrdersBO.getItemByDescription(newValue);
        if (i1 == null) {

        } else {
            txtItemCode.setText(i1.getItemCode());
            txtUnitPrice.setText(String.valueOf(i1.getUnitPrice()));
            txtPackSize.setText(i1.getPackSize());
            Optional<CartTM> optOrderDetail = tblItemDetails.getItems().stream().filter(detail -> detail.getDescription().equals(newValue)).findFirst();
            txtQuantityOnHand.setText((optOrderDetail.isPresent() ? i1.getQuantityOnHand() - optOrderDetail.get().getQty() : i1.getQuantityOnHand()) + "");
            txtDiscount.setText(String.valueOf(i1.getDiscount()));
        }
    }

    private void loadItemDescription() throws SQLException, ClassNotFoundException {
        ArrayList<ItemDTO> all = savedOrdersBO.getAllItems();
        ArrayList<String> itemNames = new ArrayList<>();
        for (ItemDTO dto : all) {
            itemNames.add(dto.getDescription());
        }
        cmbItemDescription.getItems().addAll(itemNames);
    }

    private void setItemDataToTable(String newValue) throws SQLException, ClassNotFoundException {
        ArrayList<SavedOrderDetailsTM> s = savedOrdersBO.getOrderDetails(newValue, lblCustomerNIC);
        s.forEach(e -> {
            obList.add(new CartTM(e.getItemCode(), e.getDescription(), e.getPackSize(), e.getQtyForSell(), e.getUnitPrice(), e.getDiscount(), e.getTotal()));
        });
        tblItemDetails.setItems(obList);
        setDataToTable();
        calculateCost();
    }

    private void setDataToTable() {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPackSize.setCellValueFactory(new PropertyValueFactory<>("packSize"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
    }

    private void setOrderIdAndNicToTable(ArrayList<SavedOrderDTO> orderIdAndNic) {
        ObservableList<SavedOidAndNicDetailsTM> savedOIDAndNic = FXCollections.observableArrayList();
        orderIdAndNic.forEach(e -> {
            SavedOidAndNicDetailsTM savedOidAndNicDetailsTM = new SavedOidAndNicDetailsTM(e.getoId(), e.getNIC());
            int i = 0;
            do {
                if (savedOIDAndNic.size() == 0) {
                    savedOIDAndNic.add(savedOidAndNicDetailsTM);
                    break;
                } else if (isExists(savedOidAndNicDetailsTM, savedOIDAndNic)) {

                } else {
                    savedOIDAndNic.add(savedOidAndNicDetailsTM);
                }
                i++;
            } while (i < savedOIDAndNic.size());
        });
        for (int i = 0; i < savedOIDAndNic.size(); i++) {
            cmbOrderId.getItems().add(savedOIDAndNic.get(i).getOrderId());
        }
        tblOrderIdAndNic.setItems(savedOIDAndNic);
    }

    private boolean isExists(SavedOidAndNicDetailsTM savedOidAndNicDetailsTM, ObservableList<SavedOidAndNicDetailsTM> savedOIDAndNic) {
        for (int i = 0; i < savedOIDAndNic.size(); i++) {
            if (savedOidAndNicDetailsTM.getOrderId().equals(savedOIDAndNic.get(i).getOrderId())) {
                return true;
            }
        }
        return false;
    }

    private void calculateCost() {
        double ttl = 0;
        double discount = 0;
        for (CartTM tm : obList
        ) {
            ttl += tm.getTotal();
        }
        for (CartTM tm : obList
        ) {
            discount += tm.getDiscount();
        }
        netAmount = ttl - discount;
        balance = netAmount;
        lblBalance.setText(balance + "0");
        lblGrossAmount.setText(ttl + "0");
        lblTotalDiscount.setText(discount + "0");
        lblNetAmount.setText(netAmount + "0");
    }

    public void addToCartOnAction(ActionEvent actionEvent) {
        if(!txtQty.getText().matches("^\\d+$")){
            new Alert(Alert.AlertType.ERROR,"Invalid quantity").show();
            return;
        }
        try {
            if (!cmbItemDescription.getSelectionModel().getSelectedItem().equals("null") && !txtQty.getText().equals("")) {
                String itemCode = txtItemCode.getText();
                String description = cmbItemDescription.getSelectionModel().getSelectedItem();
                double unitPrice = Double.parseDouble(txtUnitPrice.getText());
                String packSize = txtPackSize.getText();
                int qtyOnHand = Integer.parseInt(txtQuantityOnHand.getText());
                double discount = Double.parseDouble(txtDiscount.getText());
                int qtyForSell = Integer.parseInt(txtQty.getText());
                double totalDiscount = discount * qtyForSell * Double.parseDouble(packSize);
                double total = unitPrice * qtyForSell * Double.parseDouble(packSize);

                if (qtyOnHand < qtyForSell) {
                    new Alert(Alert.AlertType.WARNING, "Invalid Quantity", ButtonType.CLOSE).showAndWait();
                    return;
                }
                CartTM tempTm = new CartTM(itemCode, description, packSize, qtyForSell, unitPrice, totalDiscount, total);
                int rowNumber = isExistsItem(tempTm);

                if (rowNumber == -1) {
                    obList.add(tempTm);
                } else {
                    CartTM temp = obList.get(rowNumber);
                    CartTM newTm = new CartTM(
                            temp.getItemCode(),
                            temp.getDescription(),
                            temp.getPackSize(),
                            temp.getQty() + qtyForSell,
                            unitPrice,
                            totalDiscount + temp.getDiscount(),
                            total + temp.getTotal()
                    );

                    obList.remove(rowNumber);
                    obList.add(newTm);
                }
                tblItemDetails.setItems(obList);
                calculateCost();
                txtItemCode.clear();
                txtUnitPrice.clear();
                txtPackSize.clear();
                txtQuantityOnHand.clear();
                txtDiscount.clear();
                txtQty.clear();
                cmbItemDescription.getSelectionModel().clearSelection();
            } else {
                new Alert(Alert.AlertType.WARNING, "Please select a item", ButtonType.CLOSE).showAndWait();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.WARNING, "Please select a item", ButtonType.CLOSE).showAndWait();
        }
    }

    private int isExistsItem(CartTM tempTm) {
        for (int i = 0; i < obList.size(); i++) {
            if (tempTm.getItemCode().equals(obList.get(i).getItemCode())) {
                return i;
            }
        }
        return -1;
    }

    public void clearOnAction(ActionEvent actionEvent) {
        txtItemCode.clear();
        txtUnitPrice.clear();
        txtPackSize.clear();
        txtQuantityOnHand.clear();
        txtDiscount.clear();
        txtQty.clear();
        cmbItemDescription.getSelectionModel().clearSelection();
    }

    public void clearRow(ActionEvent actionEvent) {
        cartSelectedRow = tblItemDetails.getSelectionModel().getSelectedIndex();
        if (cartSelectedRow == -1) {
            new Alert(Alert.AlertType.WARNING, "Please select a raw.", ButtonType.CLOSE).showAndWait();
        } else {
            obList.remove(cartSelectedRow);
            calculateCost();
            tblItemDetails.refresh();
        }
    }

    public void updateRowOnAction(ActionEvent actionEvent) {
        if(!txtUpdateQty.getText().matches("^\\d+$")){
            new Alert(Alert.AlertType.ERROR,"Invalid quantity").show();
            return;
        }
        try {
            cartSelectedRow = tblItemDetails.getSelectionModel().getSelectedIndex();
            int updateQuantity = Integer.parseInt(txtUpdateQty.getText());
            if (cartSelectedRow == -1) {
                new Alert(Alert.AlertType.WARNING, "Please select a raw.", ButtonType.CLOSE).showAndWait();
            } else {
                CartTM tm = obList.get(cartSelectedRow);
                ItemDTO itemDTO = savedOrdersBO.searchItem(tm.getItemCode());

                if (updateQuantity < itemDTO.getQuantityOnHand()) {
                    tm.setQty(updateQuantity);
                    double tot = updateQuantity * itemDTO.getUnitPrice() * Integer.parseInt(itemDTO.getPackSize());
                    tm.setTotal(tot);
                    double discount = updateQuantity * itemDTO.getDiscount() * Integer.parseInt(itemDTO.getPackSize());
                    tm.setDiscount(discount);
                    obList.remove(cartSelectedRow);
                    obList.add(cartSelectedRow, tm);
                    calculateCost();
                    tblItemDetails.refresh();
                    txtUpdateQty.clear();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Invalid Qty!", ButtonType.CLOSE).showAndWait();
                }
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.WARNING, "Please fill quantity field", ButtonType.CLOSE).showAndWait();
        }
    }

    public void cashPaymentOnAction(ActionEvent actionEvent) {
        if(!txtCash.getText().matches("^[0-9]+[.]?[0-9]{2}$")){
            new Alert(Alert.AlertType.ERROR,"Invalid format. Ex :- 100.00").show();
            return;
        }
        btnPlaceOrder.setDisable(false);
        try {
            double cash = Double.parseDouble(txtCash.getText());
            balance = netAmount - cash;
            lblBalance.setText(balance + "0");
        } catch (Exception e) {
            txtCash.setText("0");
            cashPaymentOnAction(actionEvent);
        }
    }

    public void deleteOrderOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        if (savedOrdersBO.deleteOrderAndOrderIds(cmbOrderId.getSelectionModel().getSelectedItem())) {
            new Alert(Alert.AlertType.CONFIRMATION, "Order Deleted Successfully", ButtonType.CLOSE).showAndWait();
            URL resource = getClass().getResource("../view/SavedOrdersForm.fxml");
            Parent load = FXMLLoader.load(resource);
            savedOrderFormContext.getChildren().clear();
            savedOrderFormContext.getChildren().add(load);
        } else {
            new Alert(Alert.AlertType.WARNING, "Try Again!", ButtonType.CLOSE).showAndWait();
        }
    }

    public void placeOrderOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        ArrayList<ItemDetailsDTO> items = new ArrayList<>();
        double ttl = 0.0;
        double discount = 0.0;
        for (CartTM tempTm : obList
        ) {
            ttl += tempTm.getTotal();
            discount += tempTm.getDiscount();
            items.add(new ItemDetailsDTO(tempTm.getItemCode(), tempTm.getDescription(), tempTm.getPackSize(), tempTm.getQty(), tempTm.getUnitPrice(), tempTm.getDiscount(), tempTm.getTotal()));
        }
        OrderDTO order = new OrderDTO(cmbOrderId.getSelectionModel().getSelectedItem(), lblDate.getText(), lblTime.getText(), lblCustomerNIC.getText(), Double.parseDouble(lblTotalDiscount.getText()), Double.parseDouble(lblNetAmount.getText()), items);
        if (savedOrdersBO.placeOrder(order)) {
            new Alert(Alert.AlertType.CONFIRMATION, "Place Order Successfully.", ButtonType.CLOSE).showAndWait();
            URL resource = getClass().getResource("../view/SavedOrdersForm.fxml");
            Parent load = FXMLLoader.load(resource);
            savedOrderFormContext.getChildren().clear();
            savedOrderFormContext.getChildren().add(load);
        } else {
            new Alert(Alert.AlertType.WARNING, "Try again.", ButtonType.CLOSE).show();
        }
    }
}
