package controller;

import bo.BOFactory;
import bo.custom.LoginFormBO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import dto.UserDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;

public class LogInFormController {

    public AnchorPane logInContext;
    public JFXComboBox<String> cmbUserType;
    public JFXTextField txtUserName;
    public JFXPasswordField txtPassword;
    public Label lblText;
    public JFXButton btnSignUp;
    private final LoginFormBO loginFormBO = (LoginFormBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.LOGIN);

    public void initialize() {
        cmbUserType.getItems().addAll("Cashier", "Admin");
        try {
            ArrayList<UserDTO> all = loginFormBO.getAllUsers();
            for (UserDTO dto : all) {
                if (dto.getUserType().equals("Admin")) {
                    btnSignUp.setDisable(true);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void openSignUpForm(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/SignUpForm.fxml");
        Parent load = FXMLLoader.load(resource);
        logInContext.getChildren().clear();
        logInContext.getChildren().add(load);
    }

    public void openDashBoardFormOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        if(!txtUserName.getText().matches("^[A-z0-9]{6,10}$")){
            new Alert(Alert.AlertType.ERROR,"Invalid username.Username must be at least 6-10 characters long ").show();
            return;
        }else if(!txtPassword.getText().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$")){
            new Alert(Alert.AlertType.ERROR,"Invalid password.Password must be at least 8 characters long including A-Z,a-z,0-9").show();
            return;
        }
        try {
            String uType = cmbUserType.getSelectionModel().getSelectedItem();
            ArrayList<UserDTO> all = loginFormBO.searchAllUsers(uType);
            if (!txtUserName.getText().equals("") && !txtPassword.getText().equals("") && !cmbUserType.getSelectionModel().getSelectedItem().equals("null")) {
                for (UserDTO dto : all) {
                    if (cmbUserType.getSelectionModel().getSelectedItem().equalsIgnoreCase("Cashier")) {
                        if (dto.getUserType().equals(cmbUserType.getSelectionModel().getSelectedItem())) {
                            if (txtPassword.getText().equals(dto.getPassword()) && txtUserName.getText().equals(dto.getUserName())) {
                                new Alert(Alert.AlertType.CONFIRMATION, "Log in successful.", ButtonType.CLOSE).showAndWait();
                                Stage stg = (Stage) logInContext.getScene().getWindow();
                                stg.close();
                                URL resource = getClass().getResource("../view/CashierDashBoardForm.fxml");
                                Parent load = FXMLLoader.load(resource);
                                Scene scene = new Scene(load);
                                Stage stage = new Stage();
                                stage.getIcons().add(new Image("view/assets/images/logo.jpg"));
                                stage.setScene(scene);
                                stage.setTitle("Cashier DashBoard");
                                stage.show();
                                return;
                            }
                        }
                    }

                    if (cmbUserType.getSelectionModel().getSelectedItem().equalsIgnoreCase("Admin")) {
                        if (dto.getUserType().equals(cmbUserType.getSelectionModel().getSelectedItem())) {
                            if (txtPassword.getText().equals(dto.getPassword()) && txtUserName.getText().equals(dto.getUserName())) {
                                new Alert(Alert.AlertType.CONFIRMATION, "Log in successful.", ButtonType.CLOSE).showAndWait();
                                Stage stg = (Stage) logInContext.getScene().getWindow();
                                stg.close();
                                URL resource = getClass().getResource("../view/AdminDashBoardForm.fxml");
                                Parent load = FXMLLoader.load(resource);
                                Scene scene = new Scene(load);
                                Stage stage = new Stage();
                                stage.setScene(scene);
                                stage.getIcons().add(new Image("view/assets/images/logo.jpg"));
                                stage.setTitle("Admin DashBoard");
                                stage.show();
                                return;
                            }
                        }
                    }

                }
                new Alert(Alert.AlertType.ERROR, "Username , password or usertype incorrect!", ButtonType.CANCEL).showAndWait();
            } else {
                new Alert(Alert.AlertType.WARNING, "Please Fill all the fields.", ButtonType.CLOSE).show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.WARNING, "Please Fill all the fields.", ButtonType.CLOSE).show();
        }
    }

    public void moveToPassword(ActionEvent actionEvent) {
        txtPassword.requestFocus();
    }
}
