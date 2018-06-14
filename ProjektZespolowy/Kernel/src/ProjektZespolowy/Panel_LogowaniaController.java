/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProjektZespolowy;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import static ProjektZespolowy.Polaczenie.connect;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javax.swing.JFrame;

/**
 *
 * @author MaRkOs
 */

public class Panel_LogowaniaController implements Initializable {

    @FXML
    private Label label;
    
    @FXML
    private TextField textfieldLogin;
    
    @FXML
    private TextField textfieldHaslo;
    
    @FXML
    private Button buttonZaloguj;
    
    @FXML
    private Hyperlink hyperlinkForgotPassword;
    
    @FXML
    private AnchorPane signPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
        
    
    int idStanowiska=0;
    Stage stage = new Stage();
    Parent root = null;
    public static int idZalogowanego;
    public static int idJegoFirmy;
    
    
    public static int getZalogowany() {
        return idZalogowanego;
    }
    
    
    public static int getFirmaZalogowanego() {
        return idJegoFirmy;
    }
    
    
    
    @FXML
    private void ActionButtonZaloguj(ActionEvent event) throws IOException {
                                                 
        String haslo = textfieldHaslo.getText();
        String login = textfieldLogin.getText();

        System.out.println("Login: "+login);
        System.out.println("Haslo: "+haslo);
        Statement stat = null;
        
            try {
                    Connection connection = connect();
                    stat = connection.createStatement();
                    String query = "SELECT * FROM UZYTKOWNICY WHERE LOGIN=" + "'" + login + "'" + " AND HASLO=" + "'" + haslo + "'";
                    System.out.println(query);

                    ResultSet rs = stat.executeQuery(query);  

                    if (rs.next()) {

                        idStanowiska = rs.getInt("ID_STANOWISKA");

                        idZalogowanego = rs.getInt("ID_UZYTKOWNIKA");

                        idJegoFirmy = rs.getInt("ID_FIRMY");

                        switch(idStanowiska){
                            case 1:
                                root = FXMLLoader.load(getClass().getResource("Panel_Zlecajacego.fxml"));
                                break;

                            case 2:
                                root = FXMLLoader.load(getClass().getResource("Panel_Kierownika.fxml"));
                                break;

                            case 3:
                                root = FXMLLoader.load(getClass().getResource("Panel_Kierownika.fxml"));
                                break;

                            case 4:
                                root = FXMLLoader.load(getClass().getResource("Panel_Lidera_Grupy.fxml"));
                                break;

                            case 5:
                                root = FXMLLoader.load(getClass().getResource("Panel_Cz_Grupy.fxml"));
                                break;                          
                        }
                            ((Node)(event.getSource())).getScene().getWindow().hide();
                            Scene scene = new Scene(root);
                            scene.getStylesheets().add
                            (Glowna.class.getResource("Design.css").toExternalForm());
                            stage.setScene(scene);
                            stage.show();

                    } else {
                        
                        PokazAlert("Informacja","Błąd","Niestety nie udało Ci się zalogować. Sprawdź poprawność wprowadzonych danych.");
                        textfieldHaslo.setText(null);
                        textfieldLogin.setText(null);
                    }

                    stat.close();
                    connection.close();

            } catch (SQLException e) {
                
                PokazAlert("Informacja","Błąd","Niestety nie udało Ci się zalogować. Sprawdź poprawność wprowadzonych danych.");
                
                System.err.println(e.getClass().getName()+": "+e.getMessage());
            }
    }


    public void PokazAlert(String tytul, String headText, String content) {
    
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(tytul);
        alert.setHeaderText(headText);
        alert.setContentText(content);

        alert.showAndWait();
    }   
  

}
