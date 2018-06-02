/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProjektZespolowy;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import static ProjektZespolowy.Polaczenie.connect;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author MaRkOs
 */

public class Panel_Edycji_GrupController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private ComboBox comboboxWybierzGrupe;
    
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
    private Button buttonAktualizujGrupe;
    
    @FXML
    private Button buttonWroc;    
    
    @FXML
    private TableView tabZnajdz;
    
    @FXML
    private TableView tabGrupa;
    @FXML
    private AnchorPane paneAll;
    
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
    private void ActionComboBoxWybierzLidera(ActionEvent event) {
    }

    @FXML
    private void ActionSzukaj(ActionEvent event) {
    }

    @FXML
    private void ActionDodajDoGrupy(ActionEvent event) {
    }

    @FXML
    private void ActionUsunZGrupy(ActionEvent event) {
    }

    @FXML
    private void ActionAktualizujGrupe(ActionEvent event) {
        
        JOptionPane.showMessageDialog(null, "Zaaktualizowano Grupę", "Aktualizacja Grupy", JOptionPane.INFORMATION_MESSAGE);
    }

    @FXML
    private void ActionComboBoxWybierzGrupe(ActionEvent event) {
    }
}
