package controller;

import bo.BOFactory;
import bo.custom.CashierDashboardBO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dto.CustomerDTO;
import dto.ItemDTO;
import dto.ItemDetailsDTO;
import dto.OrderDTO;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import view.tm.CartTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

public class CashierDashBoardFormController {

    private final CashierDashboardBO cashierDashboardBO = (CashierDashboardBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CASHIER);
    public Label lblDate;
    public Label lblTime;
    public JFXTextField txtCustomerId;
    public JFXTextField txtAddress;
    public JFXTextField txtPostalCode;
    public JFXTextField txtCustomerName;
    public JFXComboBox<String> cmbCity;
    public JFXComboBox<String> cmbProvince;
    public AnchorPane cashierDashBoardContext;
    public JFXTextField txtCustomerNIC;
    public JFXTextField txtSearchNIC;
    public JFXButton btnAddCustomer;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtPackSize;
    public JFXTextField txtQuantityOnHand;
    public JFXTextField txtDiscount;
    public JFXTextField txtQuantityForSell;
    public TableView<CartTM> tblOrderDetail;
    public Label lblGrossAmount;
    public Label lblDiscount;
    public Label lblNetAmount;
    public TableColumn colItemCode;
    public TableColumn colDescription;
    public TableColumn colQuantity;
    public TableColumn colUnitPrice;
    public TableColumn colDiscount;
    public TableColumn colTotal;
    public JFXTextField txtItemCode;
    public JFXComboBox<String> cmbDescription;
    public TableColumn colPackSize;
    public Label lblOrderId;
    public JFXButton btnAddToCart;
    public JFXTextField txtUpdateQty;
    public JFXComboBox<String> cmbCustomerTitle;
    double netAmount = 0;
    int cartSelectedRow = -1;
    ObservableList<CartTM> obList = FXCollections.observableArrayList();

    public void initialize() {

        cmbCustomerTitle.getItems().addAll("Mrs.","Mr.","Ms.","Dr.");

        setItemDataToTable();


        btnAddToCart.setDisable(true);
        loadDateAndTime();
        loadCity();
        loadProvince();
        setCustomerId();
        setOrderId();
        try {
            loadItemDescriptions();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        cmbDescription.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
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

    private void setItemDataToTable() {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colPackSize.setCellValueFactory(new PropertyValueFactory<>("packSize"));
    }

    private void setOrderId() {
        try {
            lblOrderId.setText(cashierDashboardBO.getOrderId());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setItemData(String newValue) throws SQLException, ClassNotFoundException {
        ItemDTO i1 = cashierDashboardBO.getItemByDescription(newValue);
        if (i1 == null) {

        } else {
            txtItemCode.setText(i1.getItemCode());
            txtUnitPrice.setText(String.valueOf(i1.getUnitPrice()));
            txtPackSize.setText(i1.getPackSize());
            Optional<CartTM> optOrderDetail = tblOrderDetail.getItems().stream().filter(detail -> detail.getDescription().equals(newValue)).findFirst();
            txtQuantityOnHand.setText((optOrderDetail.isPresent() ? i1.getQuantityOnHand() - optOrderDetail.get().getQty() : i1.getQuantityOnHand()) + "");
            txtDiscount.setText(String.valueOf(i1.getDiscount()));
        }
    }

    private void loadItemDescriptions() throws SQLException, ClassNotFoundException {
        ArrayList<ItemDTO> all = cashierDashboardBO.getAllItems();
        ArrayList<String> itemNames = new ArrayList<>();
        for (ItemDTO dto : all) {
            itemNames.add(dto.getDescription());
        }
        cmbDescription.getItems().addAll(itemNames);
    }

    private void setCustomerId() {
        try {
            txtCustomerId.setText(cashierDashboardBO.generateCustomerId());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadProvince() {
        cmbProvince.getItems().addAll("Northern Province", "Eastern Province", "Southern Province", "Western Province", "Central Province", "North Central Province", "North Western Province", "Sabaragamuwa Province", "Uva Province");
    }

    private void loadCity() {
        cmbCity.getItems().addAll("Ampara", "Anuradhapura", "Badulla", "Batticaloa", "Colombo", "Gampaha", "Galle", "Hambantota", "Jaffna", "Kaluthara", "Kegalle", "Kandy", "Kilinochchi", "Kurunegala", "Mannar", "Matale", "Matara", "Monaragala", "Mullathivu", "Nuwara Eliya", "Polonnaruwa", "Puttalam", "Ratnapura", "Trincomalee", "Vavuniya");
    }

    private void loadDateAndTime() {
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy - MM - dd");
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

    public void addCustomerOnAction(ActionEvent actionEvent) {
        if(!txtCustomerName.getText().matches("^[A-z. ]{3,}$")){
            new Alert(Alert.AlertType.ERROR,"Invalid name").show();
            return;
        }else if(!txtAddress.getText().matches("^[A-z0-9, ]{3,}$")){
            new Alert(Alert.AlertType.ERROR,"Invalid Address").show();
            return;
        }else if(!txtPostalCode.getText().matches("^[0-9]{5}$")){
            new Alert(Alert.AlertType.ERROR,"Postal code must be at least 5 characters long").show();
            return;
        }else if(!txtCustomerNIC.getText().matches("^[0-9v]{9,12}$")){
            new Alert(Alert.AlertType.ERROR,"Invalid NIC Number").show();
            return;
        }
        try {
            if (!txtCustomerName.getText().equals("") && !cmbCustomerTitle.getSelectionModel().isEmpty() && !txtCustomerNIC.getText().equals("") && !txtAddress.getText().equals("") && !txtPostalCode.getText().equals("") && !cmbCity.getSelectionModel().getSelectedItem().equalsIgnoreCase("null") && !cmbProvince.getSelectionModel().getSelectedItem().equalsIgnoreCase("null")) {
                if (existCustomer(txtCustomerNIC.getText())) {
                    new Alert(Alert.AlertType.WARNING, "Customer already exists", ButtonType.CLOSE).show();
                } else {
                    CustomerDTO c1 = new CustomerDTO(
                            txtCustomerId.getText(),
                            txtCustomerName.getText(),
                            cmbCustomerTitle.getValue(),
                            txtCustomerNIC.getText(),
                            txtAddress.getText(),
                            cmbCity.getSelectionModel().getSelectedItem(),
                            cmbProvince.getSelectionModel().getSelectedItem(),
                            txtPostalCode.getText()
                    );
                    boolean add = cashierDashboardBO.addCustomer(c1);
                    if (add) {
                        new Alert(Alert.AlertType.CONFIRMATION, "Customer Saved Successfully", ButtonType.CLOSE).showAndWait();
                        URL resource = getClass().getResource("../view/CashierDashBoardForm.fxml");
                        Parent load = FXMLLoader.load(resource);
                        cashierDashBoardContext.getChildren().clear();
                        cashierDashBoardContext.getChildren().add(load);
                    } else {
                        new Alert(Alert.AlertType.WARNING, "Try Again", ButtonType.CLOSE).showAndWait();
                    }
                }
            } else {
                new Alert(Alert.AlertType.WARNING, "Please fill all the fields.", ButtonType.CLOSE).showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean existCustomer(String nic) throws SQLException, ClassNotFoundException {
        return cashierDashboardBO.ifExistCustomer(nic);
    }

    public void backToLogin(ActionEvent actionEvent) throws IOException {
        Stage stg = (Stage) cashierDashBoardContext.getScene().getWindow();
        stg.close();
        URL resource = getClass().getResource("../view/LogInForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.getIcons().add(new Image("view/assets/images/logo.jpg"));
        stage.setTitle("Wholesale Distribution");
        stage.show();
    }

    public void searchCustomerOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if(!txtSearchNIC.getText().matches("^[0-9v]{9,12}$")){
            new Alert(Alert.AlertType.ERROR,"Invalid NIC Number").show();
            return;
        }
        String nic = txtSearchNIC.getText();
        CustomerDTO c1 = cashierDashboardBO.searchCustomer(nic);
        if (c1 == null) {
            new Alert(Alert.AlertType.WARNING, "CustomerDTO does not exist.", ButtonType.CLOSE).showAndWait();
        } else {
            setCustomerData(c1);
            btnAddCustomer.setDisable(true);
            btnAddToCart.setDisable(false);
        }
    }

    void setCustomerData(CustomerDTO c1) {
        txtCustomerId.setText(c1.getCustomerId());
        txtAddress.setText(c1.getAddress());
        txtCustomerName.setText(c1.getCustomerName());
        txtCustomerNIC.setText(c1.getCustomerNIC());
        cmbCustomerTitle.setValue(c1.getCustomerTitle());
        txtPostalCode.setText(c1.getPostalCode());
        cmbCity.setValue(c1.getCity());
        cmbProvince.setValue(c1.getProvince());
    }

    public void updateCustomerOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        if(!txtCustomerName.getText().matches("^[A-z. ]{3,}$")){
            new Alert(Alert.AlertType.ERROR,"Invalid name").show();
            return;
        }else if(!txtAddress.getText().matches("^[A-z0-9, ]{3,}$")){
            new Alert(Alert.AlertType.ERROR,"Invalid Address").show();
            return;
        }else if(!txtPostalCode.getText().matches("^[0-9]{5}$")){
            new Alert(Alert.AlertType.ERROR,"Postal code must be at least 5 characters long").show();
            return;
        }else if(!txtCustomerNIC.getText().matches("^[0-9v]{9,12}$")){
            new Alert(Alert.AlertType.ERROR,"Invalid NIC Number").show();
            return;
        }
        if (!txtCustomerName.getText().equals("") && !cmbCustomerTitle.getSelectionModel().isEmpty() && !txtCustomerNIC.getText().equals("") && !txtAddress.getText().equals("") && !txtPostalCode.getText().equals("") && !cmbCity.getSelectionModel().getSelectedItem().equalsIgnoreCase("null") && !cmbProvince.getSelectionModel().getSelectedItem().equalsIgnoreCase("null")) {
            CustomerDTO c1 = new CustomerDTO(
                    txtCustomerId.getText(),
                    txtCustomerName.getText(),
                    cmbCustomerTitle.getValue(),
                    txtCustomerNIC.getText(),
                    txtAddress.getText(),
                    cmbCity.getSelectionModel().getSelectedItem(),
                    cmbProvince.getSelectionModel().getSelectedItem(),
                    txtPostalCode.getText()
            );
            boolean update = cashierDashboardBO.updateCustomer(c1);
            if (update) {
                new Alert(Alert.AlertType.CONFIRMATION, "Updated Successfully.", ButtonType.CLOSE).showAndWait();
                URL resource = getClass().getResource("../view/CashierDashBoardForm.fxml");
                Parent load = FXMLLoader.load(resource);
                cashierDashBoardContext.getChildren().clear();
                cashierDashBoardContext.getChildren().add(load);
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again.", ButtonType.CLOSE).showAndWait();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please fill all fields.", ButtonType.CLOSE).showAndWait();
        }
    }

    public void clearCustomerDetails(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/CashierDashBoardForm.fxml");
        Parent load = FXMLLoader.load(resource);
        cashierDashBoardContext.getChildren().clear();
        cashierDashBoardContext.getChildren().add(load);
    }

    public void addToCartOnAction(ActionEvent actionEvent) {
        if(!txtQuantityForSell.getText().matches("^\\d+$")){
            new Alert(Alert.AlertType.ERROR,"Invalid quantity").show();
            return;
        }
        try {
            if (!cmbDescription.getSelectionModel().getSelectedItem().equals("null") && !txtQuantityForSell.getText().equals("")) {
                String itemCode = txtItemCode.getText();
                String description = cmbDescription.getSelectionModel().getSelectedItem();
                double unitPrice = Double.parseDouble(txtUnitPrice.getText());
                String packSize = txtPackSize.getText();
                int qtyOnHand = Integer.parseInt(txtQuantityOnHand.getText());
                double discount = Double.parseDouble(txtDiscount.getText());
                int qtyForSell = Integer.parseInt(txtQuantityForSell.getText());
                double totalDiscount = discount * qtyForSell * Double.parseDouble(packSize);
                double total = unitPrice * qtyForSell * Double.parseDouble(packSize);

                if (qtyOnHand < qtyForSell) {
                    new Alert(Alert.AlertType.WARNING, "Invalid Quantity", ButtonType.CLOSE).showAndWait();
                    return;
                }
                CartTM cartTm = new CartTM(itemCode, description, packSize, qtyForSell, unitPrice, totalDiscount, total);
                int rowNumber = isExists(cartTm);

                if (rowNumber == -1) {
                    obList.add(cartTm);
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
                tblOrderDetail.setItems(obList);
                calculateCost();
                txtItemCode.clear();
                txtUnitPrice.clear();
                txtPackSize.clear();
                txtQuantityOnHand.clear();
                txtDiscount.clear();
                txtQuantityForSell.clear();
                cmbDescription.getSelectionModel().clearSelection();
            } else {
                new Alert(Alert.AlertType.WARNING, "Please select a item", ButtonType.CLOSE).showAndWait();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.WARNING, "Please select a item", ButtonType.CLOSE).showAndWait();
        }
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
        lblGrossAmount.setText(ttl + "0");
        lblDiscount.setText(discount + "0");
        lblNetAmount.setText(netAmount + "0");
    }

    private int isExists(CartTM cartTm) {
        for (int i = 0; i < obList.size(); i++) {
            if (cartTm.getItemCode().equals(obList.get(i).getItemCode())) {
                return i;
            }
        }
        return -1;
    }

    public void clearOnAction(ActionEvent actionEvent) {
        cartSelectedRow = tblOrderDetail.getSelectionModel().getSelectedIndex();
        if (cartSelectedRow == -1) {
            new Alert(Alert.AlertType.WARNING, "Please select a raw.", ButtonType.CLOSE).showAndWait();
        } else {
            obList.remove(cartSelectedRow);
            calculateCost();
            tblOrderDetail.refresh();
        }
    }


    public void openStockReport(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../view/StockReport.fxml"));
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Stock Report");
        stage.getIcons().add(new Image("view/assets/images/logo.jpg"));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.show();
    }

    public void updateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if(!txtUpdateQty.getText().matches("^\\d+$")){
            new Alert(Alert.AlertType.ERROR,"Invalid quantity").show();
            return;
        }
        try {
            int updateQuantity = Integer.parseInt(txtUpdateQty.getText());
            cartSelectedRow = tblOrderDetail.getSelectionModel().getSelectedIndex();
            if (cartSelectedRow == -1) {
                new Alert(Alert.AlertType.WARNING, "Please select a raw.", ButtonType.CLOSE).showAndWait();
            } else {
                CartTM tm = obList.get(cartSelectedRow);
                ItemDTO itemDTO = cashierDashboardBO.searchItem(tm.getItemCode());

                if (updateQuantity < itemDTO.getQuantityOnHand()) {
                    tm.setQty(updateQuantity);
                    double tot = updateQuantity * itemDTO.getUnitPrice() * Integer.parseInt(itemDTO.getPackSize());
                    tm.setTotal(tot);
                    double discount = updateQuantity * itemDTO.getDiscount() * Integer.parseInt(itemDTO.getPackSize());
                    tm.setDiscount(discount);
                    obList.remove(cartSelectedRow);
                    obList.add(cartSelectedRow, tm);
                    calculateCost();
                    tblOrderDetail.refresh();
                    txtUpdateQty.clear();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Invalid Qty!", ButtonType.CLOSE).showAndWait();
                }
            }
        }catch (Exception e){
            new Alert(Alert.AlertType.WARNING, "Please fill quantity field", ButtonType.CLOSE).showAndWait();
        }
    }

    public void saveOrderOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        if (obList.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please Try again", ButtonType.CLOSE).showAndWait();
        } else {
            ArrayList<ItemDetailsDTO> items = new ArrayList<>();
            for (CartTM tempTm : obList
            ) {
                items.add(new ItemDetailsDTO(
                        tempTm.getItemCode(),
                        tempTm.getDescription(),
                        tempTm.getPackSize(),
                        tempTm.getQty(),
                        tempTm.getUnitPrice(),
                        tempTm.getDiscount(),
                        tempTm.getTotal()
                ));
            }

            OrderDTO order = new OrderDTO(lblOrderId.getText(), txtCustomerNIC.getText(), items);

            boolean saveOrder = cashierDashboardBO.saveOrder(order);
            if (saveOrder) {
                new Alert(Alert.AlertType.CONFIRMATION, "Order Saved Successfully.", ButtonType.CLOSE).showAndWait();
                URL resource = getClass().getResource("../view/CashierDashBoardForm.fxml");
                Parent load = FXMLLoader.load(resource);
                cashierDashBoardContext.getChildren().clear();
                cashierDashBoardContext.getChildren().add(load);
            } else {
                new Alert(Alert.AlertType.WARNING, "Try again", ButtonType.CLOSE).showAndWait();
            }
        }
    }

    public void cancleOrderOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/CashierDashBoardForm.fxml");
        Parent load = FXMLLoader.load(resource);
        cashierDashBoardContext.getChildren().clear();
        cashierDashBoardContext.getChildren().add(load);
    }

    public void loadSavedOrdersOnAction(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../view/SavedOrdersForm.fxml"));
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Saved Orders");
        stage.getIcons().add(new Image("view/assets/images/logo.jpg"));
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();

        stage.setOnHiding((e)->{
            try {
                lblOrderId.setText(cashierDashboardBO.getOrderId());
            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        });
    }

    public void moveToCustomerNic(ActionEvent actionEvent) {
        txtCustomerNIC.requestFocus();
    }
}
