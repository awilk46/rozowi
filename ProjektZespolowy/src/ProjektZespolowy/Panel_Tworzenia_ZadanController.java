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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import static ProjektZespolowy.Polaczenie.connect;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author MaRkOs
 */

public class Panel_Tworzenia_ZadanController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private Button buttonUtworzZadanie;
    
    @FXML
    private Button buttonWroc;
    
    @FXML
    private ComboBox comboboxWybierzProjekt;
    
    @FXML
    private ComboBox comboboxWybierzGrupe;
    
    @FXML
    private ComboBox comboboxWybierzSprint;
    
    @FXML
    private ComboBox comboboxWybierzPriorytet;
    
    @FXML
    private ComboBox comboboxWybierzCzGrupy;
    
    @FXML
    private TextField textfieldNazwaZadania;
    
    @FXML
    private TextArea textareaOpisZadania;
    
    @FXML
    private TextArea textareaKomentarzZadania;
    
    
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
    private void ActionComboBoxWybierzPriorytet(ActionEvent event) {
    }

    @FXML
    private void ActionComboBoxWybierzCzGrupy(ActionEvent event) {
    }

    @FXML
    private void ActionComboBoxWybierzSprint(ActionEvent event) {
    }

    @FXML
    private void ActionComboBoxWybierzGrupe(ActionEvent event) {
    }

    @FXML
    private void ActionComboBoxWybierzProjekt(ActionEvent event) {
    }

    @FXML
    private void ActionButtonUtworzZadanie(ActionEvent event) {
    }
    
}
