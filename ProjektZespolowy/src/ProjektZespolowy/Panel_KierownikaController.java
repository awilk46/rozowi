/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProjektZespolowy;

import static ProjektZespolowy.Polaczenie.connect;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableView;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author MaRkOs
 */

public class Panel_KierownikaController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private ComboBox comboboxWybierzProjekt;
    
    @FXML
    private ComboBox comboboxWybierzGrupe;
    
    @FXML
    private ComboBox comboboxWybierzSprint;
    
    @FXML
    private ProgressBar progresbarProjekt;
    
    @FXML
    private ProgressBar progresbarSprint;
    
    @FXML
    private Label labelLiczbaCzlonkow;
    
    @FXML
    private Label labelLiczbaZadanGrupa;
    
    @FXML
    private Label labelLiczbaUZadanGrupa;
    
    @FXML
    private Label labelLiczbaGrup;
    
    @FXML
    private Label labelLiczbaZadanProjekt;
    
    @FXML
    private Label labelLiczbaZadanUProjekt;
    
    @FXML
    private Label labelLiderGrupy;
    
    @FXML
    private Label labelChatNazwaProjekt;
    
    @FXML
    private Button buttonWyjdz;
    
    @FXML
    private Button buttonStworzGrupe;
    
    @FXML
    private Button buttonEdytujGrupy;
    
    @FXML
    private Button buttonEdytujProjekty;
    
    @FXML
    private Button buttonStworzProjekt;
    
    @FXML
    private Button buttonWyslijProjekt;
    
    @FXML
    private Button buttonCzyscChatProjekt; 
    
    @FXML
    private TableView tableviewZadania;
    
    @FXML
    private AnchorPane paneChatProjekt;
    
    @FXML
    private TextArea textareaChatProjekt;
    
    @FXML
    private TextField textfieldChatProjekt; 
    @FXML
    private Label labelChatTytulProjekt;
    
    String query;
    
    int idProjektu;
    int idGrupy;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {

                Connection connection = connect();
                Statement stat = connection.createStatement();
                
                query = "SELECT NAZWA_PROJEKTU FROM PROJEKTY";
                
                ResultSet rs = stat.executeQuery(query);
                
                while(rs.next()) {
                    comboboxWybierzProjekt.getItems().addAll(rs.getString("NAZWA_PROJEKTU"));   
                }
                
                stat.close();
                connection.commit();
                connection.close();

            } catch (SQLException e) {
                System.err.println(" nie można wykonac tego zapytania: " + e.getMessage());
            }        
        
    }

    @FXML
    private void ActionWyjdz(ActionEvent event) throws IOException {
        
        ((Node)(event.getSource())).getScene().getWindow().hide();
        
        Parent root = null;
        Stage stage = new Stage();
        
        root = FXMLLoader.load(getClass().getResource("Panel_Logowania.fxml"));
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add
        (Glowna.class.getResource("Design.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }    

    String nazwaProjektu;
    
    @FXML
    private void ActionComboBoxWybierzProjekt(ActionEvent event) {
        
        nazwaProjektu = (String) comboboxWybierzProjekt.getSelectionModel().getSelectedItem();
        
        try {

            Connection connection = connect();
            Statement stat = connection.createStatement();
            
            query = "SELECT ID_PROJEKTU FROM PROJEKTY WHERE NAZWA_PROJEKTU = '" + nazwaProjektu +"'";
            
            ResultSet rs = stat.executeQuery(query);
            
            idProjektu = rs.getInt("ID_PROJEKTU");
            
            stat.close();
            connection.commit();
            connection.close();
            
            comboboxWybierzGrupe.getItems().clear();
            
        } catch (SQLException e) {
            System.err.println(" nie można wykonac tego zapytania: " + e.getMessage());
        }
        
         try {

                Connection connection = connect();
                Statement stat = connection.createStatement();
                
                query = "SELECT ID_GRUPY, NAZWA_GRUPY FROM GRUPY WHERE ID_PROJEKTU = "+idProjektu;
                
                ResultSet rs = stat.executeQuery(query);
                
                idGrupy = rs.getInt("ID_GRUPY");
                
                while(rs.next()) {
                    comboboxWybierzGrupe.getItems().addAll(rs.getString("NAZWA_GRUPY"));   
                }

                stat.executeUpdate(query);
                
                stat.close();
                connection.commit();
                connection.close();

            } catch (SQLException e) {
                System.err.println(" nie można wykonac tego zapytania: " + e.getMessage());
            }
         
    }

    String nazwaGrupy;
    
    @FXML
    private void ActionComboBoxWybierzGrupe(ActionEvent event) {
        
        nazwaGrupy = (String) comboboxWybierzGrupe.getSelectionModel().getSelectedItem();
        
        try {

            Connection connection = connect();
            Statement stat = connection.createStatement();
            
            query = "SELECT ID_GRUPY FROM GRUPY WHERE NAZWA_GRUPY = '" + nazwaGrupy +"'";
            
            ResultSet rs = stat.executeQuery(query);
            
            idProjektu = rs.getInt("ID_GRUPY");
            
            stat.close();
            connection.commit();
            connection.close();
            
            comboboxWybierzSprint.getItems().clear();
            
        } catch (SQLException e) {
            System.err.println(" nie można wykonac tego zapytania: " + e.getMessage());
        }        
        
        
        try {

                Connection connection = connect();
                Statement stat = connection.createStatement();
                
                query = "SELECT NAZWA_SPRINTU FROM SPRINTY WHERE ID_PROJEKTU = "+idProjektu;
                
                ResultSet rs = stat.executeQuery(query);
                
                while(rs.next()) {
                    comboboxWybierzSprint.getItems().addAll(rs.getString("NAZWA_SPRINTU"));
                }

                stat.executeUpdate(query);
                
                stat.close();
                connection.commit();
                connection.close();

            } catch (SQLException e) {
                System.err.println(" nie można wykonac tego zapytania: " + e.getMessage());
            }
        
    }

    
    @FXML
    private void ActionComboBoxWybierzSprint(ActionEvent event) {
    }

    
    @FXML
    private void ActionButtonStworzProjekt(ActionEvent event) throws IOException {
        
        ((Node)(event.getSource())).getScene().getWindow().hide();
        
        Parent root = null;
        Stage stage = new Stage();
        
        root = FXMLLoader.load(getClass().getResource("Panel_Tworzenia_Projektow.fxml"));
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add
        (Glowna.class.getResource("Design.css").toExternalForm());
        stage.setScene(scene);
        stage.show();        
    }

    
    @FXML
    private void ActionButtonStworzGrupe(ActionEvent event) throws IOException {
        
        ((Node)(event.getSource())).getScene().getWindow().hide();
        
        Parent root = null;
        Stage stage = new Stage();
        
        root = FXMLLoader.load(getClass().getResource("Panel_Tworzenia_Grup.fxml"));
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add
        (Glowna.class.getResource("Design.css").toExternalForm());
        stage.setScene(scene);
        stage.show();         
    }

    
    @FXML
    private void ActionButtonEdytujGrupy(ActionEvent event) throws IOException {
        
        ((Node)(event.getSource())).getScene().getWindow().hide();
        
        Parent root = null;
        Stage stage = new Stage();
        
        root = FXMLLoader.load(getClass().getResource("Panel_Edycji_Grup.fxml"));
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add
        (Glowna.class.getResource("Design.css").toExternalForm());
        stage.setScene(scene);
        stage.show(); 
    }

    
    @FXML
    private void ActionButtonEdytujProjekty(ActionEvent event) throws IOException {
        
        ((Node)(event.getSource())).getScene().getWindow().hide();
        
        Parent root = null;
        Stage stage = new Stage();
        
        root = FXMLLoader.load(getClass().getResource("Panel_Edycji_Projektow.fxml"));
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add
        (Glowna.class.getResource("Design.css").toExternalForm());
        stage.setScene(scene);
        stage.show();         
    }

    
    @FXML
    private void ActionButtonWyslijProjekt(ActionEvent event) {
    }

    
    @FXML
    private void ActionButtonCzyscChatProjekt(ActionEvent event) {
    }
    
}
