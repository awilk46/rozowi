/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProjektZespolowy;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import static ProjektZespolowy.Polaczenie.connect;
import javafx.scene.Node;

/**
 * FXML Controller class
 *
 * @author MaRkOs
 */
public class Panel_Tworzenia_GrupController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private ComboBox comboboxWybierzProjekt;
    
    @FXML
    private ComboBox comboboxWybierzLidera;
    
    @FXML
    private TextField textfieldNadajGrupieNazwe;
    
    @FXML
    private TextArea textareaOpisGrupy;
    
    @FXML
    private TextField textfieldZnajdzImie;
    
    @FXML
    private TextField textfieldZnajdzNazwisko;
    
    @FXML
    private Button buttonSzukajPracownika;
    
    @FXML
    private Button buttonDodajDoGrupy;
    
    @FXML
    private Button buttonUsunZGrupy;
    
    
    @FXML
    private Button buttonWroc;    
    
    @FXML
    private TableView tabZnajdz;
    
    @FXML
    private TableView tabGrupa;
    @FXML
    private Button buttonStworzGrupe;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void ActionWroc(ActionEvent event) throws IOException {
        
        ((Node)(event.getSource())).getScene().getWindow().hide();
        
        Parent root = null;
        Stage stage = new Stage();
        
        root = FXMLLoader.load(getClass().getResource("Panel_Kierownika.fxml"));
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add
        (Glowna.class.getResource("Design.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void ActionComboBoxWybierzProjekt(ActionEvent event) {
    }

    @FXML
    private void ActionCombBboxWybierzLidera(ActionEvent event) {
    }

    String znajdzImie;
    String znajdzNazwisko;
    Statement stat = null;
    
    @FXML
    private void ActionButtonSzukajPracownika(ActionEvent event) {
        
//        try {
//                Connection connection = connect();
//
//                String query = "SELECT * FROM UZYTKOWNICY WHERE IMIE = " + znajdzImie + " OR NAZWISKO = " + znajdzNazwisko;
//                
//                stat = connection.createStatement();
//                
//                ResultSet rs = stat.executeQuery(query);
//
//                // TUTAJ POBRANE DANE TRZEBA UMIESCIC W TABELI
//                
//                while (rs.next()) {
//                    Sprzedawcy sp = new Sprzedawcy();
//                    sp.setID_Sprzedawca(rs.getInt("id_sprzedawca"));
//                    sp.setImie(rs.getString("imie"));
//                    sp.setNazwisko(rs.getString("nazwisko"));
//
//                    model.addElement(sp);
//
//                }
//
//                stat.close();
//                connection.close();
//
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, e);
//        }        
        
    }

    @FXML
    private void ActionButtonDodajDoGrupy(ActionEvent event) {
    }

    @FXML
    private void ActionButtonUsunZGrupy(ActionEvent event) {
    }

    @FXML
    private void ActionButtonStworzGrupe(ActionEvent event) {
    }
    
}
