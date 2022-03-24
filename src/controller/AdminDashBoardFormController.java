package controller;

import bo.BOFactory;
import bo.custom.AdminDashboardBO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import db.DbConnection;
import dto.CustomerDTO;
import dto.ItemDTO;
import dto.UserDTO;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import view.tm.CustomerTM;
import view.tm.ItemTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class AdminDashBoardFormController {

    private final AdminDashboardBO adminDashboardBO = (AdminDashboardBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ADMIN);
    public Label lblDate;
    public Label lblTime;
    public AnchorPane adminDashBoardContext;
    public JFXTextField txtItemCode;
    public JFXTextField txtDescription;
    public JFXTextField txtPackSize;
    public JFXTextField txtQuantityOnHand;
    public JFXTextField txtDiscount;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtSearchCode;
    public JFXButton btnAddItem;
    public TableView<ItemTM> tblItem;
    public TableColumn colItemCode;
    public TableColumn colDescription;
    public TableColumn colPackSize;
    public TableColumn colQuantity;
    public TableColumn colDiscount;
    public TableColumn colUnitPrice;
    public JFXTextField txtFirstName;
    public JFXTextField txtLastName;
    public JFXTextField txtUserName;
    public JFXComboBox<String> cmbUserType;
    public TableView<CustomerTM> tblCustomer;
    public TableColumn colCustId;
    public TableColumn colCustomerTitle;
    public TableColumn colCustomerName;
    public TableColumn colNIC;
    public TableColumn colCustomerAddress;
    public TableColumn colCustomerCity;
    public TableColumn colCustomerProvince;
    public TableColumn colPostalCode;
    public JFXPasswordField txtPassword;
    public JFXPasswordField txtConfirmPassword;
    public ImageView imgTick;
    public ImageView imgPasswordTick;
    public Spinner<Integer> spYear;
    public Spinner<String> spMonth;
    public Spinner<Integer> spYearForMonthly;
    public DatePicker date;
    public ComboBox<String> cmbCustomerNIC;
    public ToggleGroup tgReports;
    public JFXButton btnGenerate;


    public void initialize() {

        spYear.setDisable(true);
        spYearForMonthly.setDisable(true);
        spMonth.setDisable(true);
        date.setDisable(true);
        cmbCustomerNIC.setDisable(true);
        btnGenerate.setDisable(true);

        btnGenerate.setDisable(true);
        cmbUserType.getItems().addAll("Cashier", "Admin");
        loadDateAndTime();
        setItemCode();
        imgTick.setVisible(false);
        imgPasswordTick.setVisible(false);

        SpinnerValueFactory<Integer> yearValues = new SpinnerValueFactory.IntegerSpinnerValueFactory(2021, 2099);
        spYear.setValueFactory(yearValues);
        spYearForMonthly.setValueFactory(yearValues);

        ObservableList<String> months = FXCollections.observableArrayList();
        months.addAll("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
        SpinnerValueFactory<String> monthValues = new SpinnerValueFactory.ListSpinnerValueFactory<>(months);
        spMonth.setValueFactory(monthValues);


        tgReports.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            RadioButton selected = (RadioButton) tgReports.getSelectedToggle();
            if (selected.getText().equalsIgnoreCase("Annual Income Report")) {
                clearAllComponents();
                spYear.setDisable(false);
                spYearForMonthly.setDisable(true);
                spMonth.setDisable(true);
                date.setDisable(true);
                cmbCustomerNIC.setDisable(true);
                btnGenerate.setDisable(false);
            } else if (selected.getText().equalsIgnoreCase("Monthly Income Report")) {
                clearAllComponents();
                spYear.setDisable(true);
                spYearForMonthly.setDisable(false);
                spMonth.setDisable(false);
                date.setDisable(true);
                cmbCustomerNIC.setDisable(true);
                btnGenerate.setDisable(false);
            } else if (selected.getText().equalsIgnoreCase("Daily Income Report")) {
                clearAllComponents();
                spYear.setDisable(true);
                spYearForMonthly.setDisable(true);
                spMonth.setDisable(true);
                date.setDisable(false);
                cmbCustomerNIC.setDisable(true);
                btnGenerate.setDisable(false);
            } else if (selected.getText().equalsIgnoreCase("Customer wise Income Report")) {
                clearAllComponents();
                spYear.setDisable(true);
                spYearForMonthly.setDisable(true);
                spMonth.setDisable(true);
                date.setDisable(true);
                cmbCustomerNIC.setDisable(false);
                btnGenerate.setDisable(false);
            } else if (selected.getText().equalsIgnoreCase("Most Movable Item")) {
                clearAllComponents();
                spYear.setDisable(true);
                spYearForMonthly.setDisable(true);
                spMonth.setDisable(true);
                date.setDisable(true);
                cmbCustomerNIC.setDisable(true);
                btnGenerate.setDisable(false);
            } else if (selected.getText().equalsIgnoreCase("Least Movable Item")) {
                clearAllComponents();
                spYear.setDisable(true);
                spYearForMonthly.setDisable(true);
                spMonth.setDisable(true);
                date.setDisable(true);
                cmbCustomerNIC.setDisable(true);
                btnGenerate.setDisable(false);
            }
        });


        try {
            setCustomerNICtoComboBox();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            ArrayList<CustomerDTO> all = adminDashboardBO.getAllCustomers();
            setCustomerToTable(all);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPackSize.setCellValueFactory(new PropertyValueFactory<>("packSize"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantityOnHand"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));

        colCustId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colCustomerTitle.setCellValueFactory(new PropertyValueFactory<>("customerTitle"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colNIC.setCellValueFactory(new PropertyValueFactory<>("custNIC"));
        colCustomerAddress.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        colCustomerCity.setCellValueFactory(new PropertyValueFactory<>("customerCity"));
        colCustomerProvince.setCellValueFactory(new PropertyValueFactory<>("customerProvince"));
        colPostalCode.setCellValueFactory(new PropertyValueFactory<>("customerPostalCode"));


        try {
            setItemsToTable(adminDashboardBO.getAllItems());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void clearAllComponents() {
        date.setValue(null);
        cmbCustomerNIC.getSelectionModel().clearSelection();
    }

    private void setCustomerNICtoComboBox() throws SQLException, ClassNotFoundException {
        ArrayList<CustomerDTO> allCustomers = adminDashboardBO.getAllCustomers();
        for (CustomerDTO customer : allCustomers) {
            cmbCustomerNIC.getItems().add(customer.getCustomerNIC());
        }
    }

    private void setCustomerToTable(ArrayList<CustomerDTO> allCustomers) {
        ObservableList<CustomerTM> customerObList = FXCollections.observableArrayList();
        allCustomers.forEach(e -> {
            customerObList.add(new CustomerTM(e.getCustomerId(), e.getCustomerTitle(), e.getCustomerName(), e.getCustomerNIC(), e.getAddress(), e.getCity(), e.getProvince(), e.getPostalCode()));
        });
        tblCustomer.setItems(customerObList);
    }

    private void setItemsToTable(ArrayList<ItemDTO> items) {
        ObservableList<ItemTM> itemList = FXCollections.observableArrayList();
        items.forEach(e -> {
            itemList.add(new ItemTM(e.getItemCode(), e.getDescription(), e.getPackSize(), e.getQuantityOnHand(), e.getDiscount(), e.getUnitPrice()));
        });
        tblItem.setItems(itemList);
    }

    private void setItemCode() {
        try {
            txtItemCode.setText(adminDashboardBO.generateItemCode());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadDateAndTime() {
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy - MM - dd");
        lblDate.setText(f.format(date));

        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            SimpleDateFormat s = new SimpleDateFormat("hh:mm aa");
            lblTime.setText(
                    currentTime.getHour() + ":" + currentTime.getMinute() + ":" + currentTime.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

    public void backToLogin(ActionEvent actionEvent) throws IOException {
        Stage stg = (Stage) adminDashBoardContext.getScene().getWindow();
        stg.close();
        URL resource = getClass().getResource("../view/LogInForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.getIcons().add(new Image("view/assets/images/logo.jpg"));
        stage.setScene(scene);
        stage.setTitle("Wholesale Distribution");
        stage.show();
    }

    public void clearOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/AdminDashBoardForm.fxml");
        Parent load = FXMLLoader.load(resource);
        adminDashBoardContext.getChildren().clear();
        adminDashBoardContext.getChildren().add(load);
    }

    public void updateItemOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        if (!txtDescription.getText().matches("^[A-z0-9 &,]{3,}$")) {
            new Alert(Alert.AlertType.ERROR, "Description must be at least 3 characters long").show();
            return;
        } else if (!txtPackSize.getText().matches("^[1-9]+$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid Pack Size.(Ex :- 4)").show();
            return;
        } else if (!txtQuantityOnHand.getText().matches("^\\d+$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid Quantity.(Ex :- 100)").show();
            return;
        } else if (!txtDiscount.getText().matches("^[0-9]+[.]?[0-9]{2}$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid discount.(Ex :- 50.00)").show();
            return;
        } else if (!txtUnitPrice.getText().matches("^[0-9]+[.]?[0-9]{2}$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid unit price.(Ex :- 50.00)").show();
            return;
        }
        if (!txtDescription.getText().equals("") && !txtPackSize.getText().equals("") && !txtQuantityOnHand.getText().equals("") && !txtUnitPrice.getText().equals("") && !txtDiscount.getText().equals("")) {
            try {
                ItemDTO i = new ItemDTO(
                        txtItemCode.getText(),
                        txtDescription.getText(),
                        txtPackSize.getText(),
                        Integer.parseInt(txtQuantityOnHand.getText()),
                        Double.parseDouble(txtDiscount.getText()),
                        Double.parseDouble(txtUnitPrice.getText())
                );
                boolean update = adminDashboardBO.updateItem(i);
                if (update) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Updated Successfully.", ButtonType.CLOSE).showAndWait();
                    URL resource = getClass().getResource("../view/AdminDashBoardForm.fxml");
                    Parent load = FXMLLoader.load(resource);
                    adminDashBoardContext.getChildren().clear();
                    adminDashBoardContext.getChildren().add(load);
                } else {
                    new Alert(Alert.AlertType.WARNING, "Try Again.", ButtonType.CLOSE).showAndWait();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Error! Enter correct data format and try again", ButtonType.CLOSE).showAndWait();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please fill all fields.", ButtonType.CLOSE).showAndWait();
        }
    }

    public void addItemOnAction(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        if (!txtDescription.getText().matches("^[A-z0-9 &,]{3,}$")) {
            new Alert(Alert.AlertType.ERROR, "Description must be at least 3 characters long").show();
            return;
        } else if (!txtPackSize.getText().matches("^[0-9]+$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid Pack Size.(Ex :- 4)").show();
            return;
        } else if (!txtQuantityOnHand.getText().matches("^\\d+$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid Quantity.(Ex :- 100)").show();
            return;
        } else if (!txtDiscount.getText().matches("^[0-9]+[.]?[0-9]{2}$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid discount.(Ex :- 50.00)").show();
            return;
        } else if (!txtUnitPrice.getText().matches("^[0-9]+[.]?[0-9]{2}$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid unit price.(Ex :- 50.00)").show();
            return;
        }


        if (!txtDescription.getText().equals("") && !txtPackSize.getText().equals("") && !txtQuantityOnHand.getText().equals("") && !txtUnitPrice.getText().equals("") && !txtDiscount.getText().equals("")) {
            try {
                ItemDTO i1 = new ItemDTO(
                        txtItemCode.getText(),
                        txtDescription.getText(),
                        txtPackSize.getText(),
                        Integer.parseInt(txtQuantityOnHand.getText()),
                        Double.parseDouble(txtDiscount.getText()),
                        Double.parseDouble(txtUnitPrice.getText())
                );
                boolean add = adminDashboardBO.addItem(i1);
                if (add) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Item Saved Successfully", ButtonType.CLOSE).showAndWait();
                    URL resource = getClass().getResource("../view/AdminDashBoardForm.fxml");
                    Parent load = FXMLLoader.load(resource);
                    adminDashBoardContext.getChildren().clear();
                    adminDashBoardContext.getChildren().add(load);
                } else {
                    new Alert(Alert.AlertType.WARNING, "Try Again!", ButtonType.CLOSE).showAndWait();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Error! Enter correct data format and try again", ButtonType.CLOSE).showAndWait();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please fill all fields.", ButtonType.CLOSE).showAndWait();
        }
    }

    public void deleteItemOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        if(!txtSearchCode.getText().isEmpty()) {
            boolean delete = adminDashboardBO.deleteItem(txtSearchCode.getText());
            if (delete) {
                new Alert(Alert.AlertType.CONFIRMATION, "Item Deleted Successfully", ButtonType.CLOSE).showAndWait();
                URL resource = getClass().getResource("../view/AdminDashBoardForm.fxml");
                Parent load = FXMLLoader.load(resource);
                adminDashBoardContext.getChildren().clear();
                adminDashBoardContext.getChildren().add(load);
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again!", ButtonType.CLOSE).showAndWait();
            }
        }else{
            new Alert(Alert.AlertType.WARNING,"Please search item",ButtonType.CLOSE).show();
        }
    }

    public void searchItemOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        if (!txtSearchCode.getText().matches("^(I-)[0-9]{3}$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid item code").show();
            return;
        }
        String code = txtSearchCode.getText();
        ItemDTO i1 = adminDashboardBO.searchItem(code);
        if (i1 == null) {
            new Alert(Alert.AlertType.WARNING, "Item does not exist.", ButtonType.CLOSE).showAndWait();

        } else {
            setItemData(i1);
            btnAddItem.setDisable(true);
        }
    }

    void setItemData(ItemDTO i) {
        txtItemCode.setText(i.getItemCode());
        txtDescription.setText(i.getDescription());
        txtPackSize.setText(i.getPackSize());
        txtQuantityOnHand.setText(String.valueOf(i.getQuantityOnHand()));
        txtUnitPrice.setText(String.valueOf(i.getUnitPrice()));
        txtDiscount.setText(String.valueOf(i.getDiscount()));
    }

    public void signUpUsersOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (!txtFirstName.getText().matches("^[A-z]{3,}$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid First name").show();
            return;
        } else if (!txtLastName.getText().matches("^[A-z]{3,}$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid last name").show();
            return;
        } else if (!txtUserName.getText().matches("^[A-z0-9]{6,10}$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid username.Username must be at least 6-10 characters long ").show();
            return;
        } else if (!txtPassword.getText().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid password.Password must be at least 8 characters long including A-Z,a-z,0-9").show();
            return;
        } else if (!txtConfirmPassword.getText().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid password.Password must be at least 8 characters long including A-Z,a-z,0-9").show();
            return;
        }
        if (!cmbUserType.getSelectionModel().isEmpty()) {
            if (txtPassword.getText().equals(txtConfirmPassword.getText())) {
                if (!txtFirstName.getText().equals("") && !txtLastName.getText().equals("") && !txtUserName.getText().equals("") && !txtPassword.getText().equals("") && !txtConfirmPassword.getText().equals("") && !cmbUserType.getSelectionModel().getSelectedItem().equalsIgnoreCase("null")) {
                    UserDTO u1 = new UserDTO(txtFirstName.getText(), txtLastName.getText(), cmbUserType.getSelectionModel().getSelectedItem(), txtUserName.getText(), txtPassword.getText());
                    if (adminDashboardBO.signUp(u1)) {
                        new Alert(Alert.AlertType.CONFIRMATION, "Registration Successful", ButtonType.CLOSE).showAndWait();
                        txtFirstName.clear();
                        txtLastName.clear();
                        txtUserName.clear();
                        txtPassword.clear();
                        txtConfirmPassword.clear();
                        cmbUserType.getSelectionModel().clearSelection();
                        imgPasswordTick.setVisible(false);
                        imgTick.setVisible(false);
                    } else {
                        new Alert(Alert.AlertType.WARNING, "Try Again", ButtonType.CLOSE).showAndWait();
                    }
                } else {
                    new Alert(Alert.AlertType.WARNING, "Please fill all the fields", ButtonType.CLOSE).showAndWait();
                    return;
                }
            } else {
                new Alert(Alert.AlertType.WARNING, "Password is not matched.", ButtonType.CLOSE).showAndWait();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please select user type", ButtonType.CLOSE).showAndWait();
        }
    }

    public void moveToPackSize(ActionEvent actionEvent) {
        txtPackSize.requestFocus();
    }

    public void moveToQtyOnHand(ActionEvent actionEvent) {
        txtQuantityOnHand.requestFocus();
    }

    public void moveToDiscount(ActionEvent actionEvent) {
        txtDiscount.requestFocus();
    }

    public void moveToUnitPrice(ActionEvent actionEvent) {
        txtUnitPrice.requestFocus();
    }

    public void moveToLastName(ActionEvent actionEvent) {
        txtLastName.requestFocus();
    }

    public void moveToUserName(ActionEvent actionEvent) {
        txtUserName.requestFocus();
    }

    public void moveToPassword(ActionEvent actionEvent) {
        txtPassword.requestFocus();
    }

    public void moveToConfirmPassword(ActionEvent actionEvent) {
        txtConfirmPassword.requestFocus();
    }

    public void deleteCustomerOnAction(ActionEvent actionEvent) {
        CustomerTM selectedItem = tblCustomer.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            new Alert(Alert.AlertType.WARNING, "No data selected.Please try again.", ButtonType.CLOSE).showAndWait();
        } else {
            try {
                boolean delete = adminDashboardBO.deleteCustomer(selectedItem.getCustNIC());
                if (delete) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Customer deleted successfully", ButtonType.OK).showAndWait();
                    setCustomerToTable(adminDashboardBO.getAllCustomers());
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void checkPassword(KeyEvent keyEvent) {
        if (txtConfirmPassword.getText().equals(txtPassword.getText())) {
            imgTick.setVisible(true);
            imgPasswordTick.setVisible(true);
        } else {
            imgTick.setVisible(false);
            imgPasswordTick.setVisible(false);
        }
    }

    public void generateReportOnAction(ActionEvent actionEvent) {
        RadioButton selected = (RadioButton) tgReports.getSelectedToggle();
        if (selected.getText().equalsIgnoreCase("Annual Income Report")) {
            try {
                int y = spYear.getValue();
                String year = String.valueOf(y);

                HashMap map = new HashMap();
                map.put("year", year);

                JasperDesign jasperDesign = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/reports/AnnualIncomeReport.jrxml"));
                JasperReport compileReport = JasperCompileManager.compileReport(jasperDesign);
                JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, map, DbConnection.getInstance().getConnection());
                JasperViewer.viewReport(jasperPrint, false);

            } catch (JRException | SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else if (selected.getText().equalsIgnoreCase("Monthly Income Report")) {
            try {
                int y = spYear.getValue();
                String month = spMonth.getValue();
                String year = String.valueOf(y);

                HashMap map = new HashMap();
                map.put("yearMonthly", year);
                map.put("month", month);

                JasperDesign jasperDesign = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/reports/MonthlyIncomeReport.jrxml"));
                JasperReport compileReport = JasperCompileManager.compileReport(jasperDesign);
                JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, map, DbConnection.getInstance().getConnection());
                JasperViewer.viewReport(jasperPrint, false);

            } catch (JRException | SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else if (selected.getText().equalsIgnoreCase("Daily Income Report")) {
            if (date.getValue() != null) {
                try {
                    String d = String.valueOf(date.getValue());
                    HashMap map = new HashMap();
                    map.put("date", d);

                    JasperDesign jasperDesign = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/reports/DailyIncomeReport.jrxml"));
                    JasperReport compileReport = JasperCompileManager.compileReport(jasperDesign);
                    JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, map, DbConnection.getInstance().getConnection());
                    JasperViewer.viewReport(jasperPrint, false);

                } catch (JRException | SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                new Alert(Alert.AlertType.WARNING, "Please select date", ButtonType.CLOSE).show();
            }
        } else if (selected.getText().equalsIgnoreCase("Customer wise Income Report")) {
            if (!cmbCustomerNIC.getSelectionModel().isEmpty()) {
                try {
                    String nic = cmbCustomerNIC.getValue();

                    HashMap map = new HashMap();
                    map.put("customerNIC", nic);

                    JasperDesign jasperDesign = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/reports/CustomerWiseIncomeReport.jrxml"));
                    JasperReport compileReport = JasperCompileManager.compileReport(jasperDesign);
                    JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, map, DbConnection.getInstance().getConnection());
                    JasperViewer.viewReport(jasperPrint, false);

                } catch (JRException | SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                new Alert(Alert.AlertType.WARNING, "Please select customer nic", ButtonType.CLOSE).show();
            }

        } else if (selected.getText().equalsIgnoreCase("Most Movable Item")) {
            try {
                JasperDesign jasperDesign = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/reports/MostMovableItemReport.jrxml"));
                JasperReport compileReport = JasperCompileManager.compileReport(jasperDesign);
                JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, null, DbConnection.getInstance().getConnection());
                JasperViewer.viewReport(jasperPrint, false);
            } catch (JRException | SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else if (selected.getText().equalsIgnoreCase("Least Movable Item")) {
            try{
                JasperDesign jasperDesign = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/reports/LeastMovableItemReport.jrxml"));
                JasperReport compileReport = JasperCompileManager.compileReport(jasperDesign);
                JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, null, DbConnection.getInstance().getConnection());
                JasperViewer.viewReport(jasperPrint, false);
            } catch (JRException | SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
