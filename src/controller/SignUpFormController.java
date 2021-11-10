package controller;

import bo.BOFactory;
import bo.custom.SignUpFormBO;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

public class SignUpFormController {

    public AnchorPane signUpFormContext;
    public JFXTextField txtFirstName;
    public JFXTextField txtLastName;
    public JFXTextField txtUserName;
    public JFXComboBox<String> cmbUserType;
    private final SignUpFormBO signUpFormBO = (SignUpFormBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.SIGNUP);
    public JFXPasswordField txtPassword;
    public JFXPasswordField txtConfirmPassword;
    public ImageView imgConfirmTick;
    public ImageView imgPasswordTick;

    public void initialize() {
        cmbUserType.getItems().addAll("Admin");
        imgPasswordTick.setVisible(false);
        imgConfirmTick.setVisible(false);
    }

    public void backToLoginForm(MouseEvent mouseEvent) throws IOException {
        URL resource = getClass().getResource("../view/LogInForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) signUpFormContext.getScene().getWindow();
        window.setScene(new Scene(load));
    }

    public void signUpUsersOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if(!txtFirstName.getText().matches("^[A-z]{3,}$")){
            new Alert(Alert.AlertType.ERROR,"Invalid First name").show();
            return;
        }else if(!txtLastName.getText().matches("^[A-z]{3,}$")){
            new Alert(Alert.AlertType.ERROR,"Invalid last name").show();
            return;
        }else if(!txtUserName.getText().matches("^[A-z0-9]{6,10}$")){
            new Alert(Alert.AlertType.ERROR,"Invalid username.Username must be at least 6-10 characters long ").show();
            return;
        }else if(!txtPassword.getText().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$")){
            new Alert(Alert.AlertType.ERROR,"Invalid password.Password must be at least 8 characters long including A-Z,a-z,0-9").show();
            return;
        }else if(!txtConfirmPassword.getText().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$")){
            new Alert(Alert.AlertType.ERROR,"Invalid password.Password must be at least 8 characters long including A-Z,a-z,0-9").show();
            return;
        }
        if(!cmbUserType.getSelectionModel().isEmpty()) {
            if (txtPassword.getText().equals(txtConfirmPassword.getText())) {
                if (!txtFirstName.getText().equals("") && !txtLastName.getText().equals("") && !txtUserName.getText().equals("") && !txtPassword.getText().equals("") && !txtConfirmPassword.getText().equals("") && !cmbUserType.getSelectionModel().getSelectedItem().equalsIgnoreCase("null")) {
                    UserDTO u1 = new UserDTO(txtFirstName.getText(), txtLastName.getText(), cmbUserType.getSelectionModel().getSelectedItem(), txtUserName.getText(), txtPassword.getText());
                    if (signUpFormBO.signUpUser(u1)) {
                        new Alert(Alert.AlertType.CONFIRMATION, "Registration Successful", ButtonType.CLOSE).showAndWait();
                        txtFirstName.clear();
                        txtLastName.clear();
                        txtUserName.clear();
                        txtPassword.clear();
                        txtConfirmPassword.clear();
                        cmbUserType.getSelectionModel().clearSelection();
                        imgConfirmTick.setVisible(false);
                        imgPasswordTick.setVisible(false);
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
        }else{
            new Alert(Alert.AlertType.WARNING, "Please select user type", ButtonType.CLOSE).showAndWait();
        }
    }

    public void moveToLastName(ActionEvent actionEvent) {
        txtLastName.requestFocus();
    }

    public void moveToUserName(ActionEvent actionEvent) {
        txtUserName.requestFocus();
    }

    public void moveToSignupPassword(ActionEvent actionEvent) {
        txtPassword.requestFocus();
    }

    public void moveToConfirmPassword(ActionEvent actionEvent) {
        txtConfirmPassword.requestFocus();
    }

    public void checkPassword(KeyEvent keyEvent) {
        if(txtConfirmPassword.getText().equals(txtPassword.getText())){
            imgConfirmTick.setVisible(true);
            imgPasswordTick.setVisible(true);
        }else{
            imgConfirmTick.setVisible(false);
            imgPasswordTick.setVisible(false);
        }
    }
}
