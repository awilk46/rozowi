/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication2;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author adria
 */
public class FXMLDocumentController implements Initializable {

    private Label label;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwField;
    @FXML
    private Button logInButton;
    @FXML
    private Hyperlink forgotPassLink;
    @FXML
    private Button registrationButton;
    @FXML
    private AnchorPane signPane;



    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void logInAction(ActionEvent event) throws IOException {
       Osoba admin = new Osoba("admin", "admin");
       Osoba user = new Osoba("user", "user");


//        if (usernameField.equals(admin.getUsername()) && passwField.equals(admin.getPassword())) {
if (usernameField.getText().equals(admin.getUsername())&& passwField.getText().equals(admin.getPassword())) {

            AnchorPane rootPane = FXMLLoader.load(getClass().getResource("FXMLDocument2.fxml"));
//        FXMLLoader floader = FXMLLoader.load(getClass().getResource("FXMLDocument2.fxml"));
//        floader.setRoot(this);
//        floader.setController(this);

            signPane.getChildren().setAll(rootPane);
//        }else if (usernameField.equals(user.getUsername()) && passwField.equals(user.getPassword())) {
}else if (usernameField.getText().equals(user.getUsername())&& passwField.getText().equals(user.getPassword())) {

            AnchorPane rootPane = FXMLLoader.load(getClass().getResource("FXMLDocument2.fxml"));
//        FXMLLoader floader = FXMLLoader.load(getClass().getResource("FXMLDocument2.fxml"));
//        floader.setRoot(this);
//        floader.setController(this);

            signPane.getChildren().setAll(rootPane);
        }

    }

}
