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
import javafx.scene.control.TextArea;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

public class Panel_ZlecajacegoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private Button buttonWyjdz;
    
    @FXML
    private Button buttonDodajKomentarz;
    
    @FXML
    private ComboBox comboboxWybierzProjekt;
    
    @FXML
    private ProgressBar progresbarProjekt;
    
    @FXML
    private Label labelLiczbaGrup;
    
    @FXML
    private Label labelLiczbaZadanProjekt;
    
    @FXML
    private Label labelLiczbaZadanUProjekt;
    
    @FXML
    private Label labelKierownikProjektu;
    
    @FXML
    private TextArea textareaOpisProjektu;
    
    @FXML
    private TextArea textareaKomentarzeProjektu;
    
    @FXML
    private TextArea textareaNowyKomentarzProjektu;

    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        WczytanieProjektow();
        
    }
    
    
    public void WczytanieProjektow(){
        
                try {

                Connection connection = connect();
                Statement stat = connection.createStatement();
                
                query = "SELECT NAZWA_PROJEKTU FROM PROJEKTY";
                
                ResultSet rs = stat.executeQuery(query);
                
                while(rs.next()){
                    comboboxWybierzProjekt.getItems().addAll(rs.getString("NAZWA_PROJEKTU"));    
                }

                stat.executeUpdate(query);
                
                stat.close();
                connection.commit();
                connection.close();

            } catch (SQLException e) {
                System.err.println(" nie można wykonac tego zapytania: " + e.getMessage());
            }
    }    
    
    
    Parent root = null;
    Stage stage = new Stage();
    String nazwaOkna = "";
        
    
    public void Okno(String nazwaOkna) throws IOException{
        
        root = FXMLLoader.load(getClass().getResource(nazwaOkna));
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add
        (Glowna.class.getResource("Design.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
    
    
    @FXML
    private void ActionWyjdz(ActionEvent event) throws IOException {
        
        ((Node)(event.getSource())).getScene().getWindow().hide();
        
        nazwaOkna = "Panel_Logowania.fxml";
        Okno(nazwaOkna);
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
            
            query = "SELECT * FROM PROJEKTY WHERE ID_PROJEKTU = '" + idProjektu +"'";
            
            rs = stat.executeQuery(query);
            
            textareaOpisProjektu.setText(rs.getString("OPIS_PROJEKTU"));
            textareaKomentarzeProjektu.setText(rs.getString("KOMENTARZ_ZLECAJACEGO"));
            
            query = "SELECT COUNT(ID_GRUPY) AS LICZGR FROM GRUPY WHERE ID_PROJEKTU = '" + idProjektu + "'";
            
            rs = stat.executeQuery(query);
            
            System.out.println("RS PRZY LICZGR "+rs.getInt("LICZGR"));
            
            labelLiczbaGrup.setText(String.valueOf(rs.getInt("LICZGR")));
            
            query = "SELECT IMIE, NAZWISKO FROM UZYTKOWNICY U INNER JOIN PROJEKTY P ON U.ID_UZYTKOWNIKA = P.ID_KIEROWNIKA";
            
            rs = stat.executeQuery(query);            
            
            labelKierownikProjektu.setText(rs.getString("IMIE") + "&nbps" + rs.getString("NAZWISKO"));
            
            query = "SELECT COUNT(ID_ZADANIA) AS LICZZAD FROM ZADANIA WHERE ID_PROJEKTU = '" + idProjektu + "'";
            
            rs = stat.executeQuery(query);
            
            System.out.println("RS PRZY LICZZAD "+rs.getInt("LICZZAD"));
            
            labelLiczbaZadanProjekt.setText(String.valueOf(rs.getInt("LICZZAD")));

            query = "SELECT COUNT(ID_ZADANIA) AS LICZZADUK FROM ZADANIA WHERE ID_PROJEKTU = '" + idProjektu + "' AND STATUS != 'UKONCZONY'";
            
            rs = stat.executeQuery(query);
            
            System.out.println("RS PRZY LICZZADUK "+rs.getInt("LICZZADUK"));
            
            labelLiczbaZadanUProjekt.setText(String.valueOf(rs.getInt("LICZZADUK")));            
            
            stat.close();
            connection.commit();
            connection.close();

        } catch (SQLException e) {
            System.err.println(" nie można wykonac tego zapytania: " + e.getMessage());
        }        
        
    }

    
    String query;
    String nowyKomentarz;
    int idProjektu; // to jest do pobrania przy comboboxie
    String dataKomentarza;
    
    
    @FXML
    private void ActionDodajKomentarz(ActionEvent event) {
        
        dataKomentarza = String.valueOf(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE));
        
        try {

            Connection connection = connect();
            Statement stat = connection.createStatement();
            
            query = "SELECT KOMENTARZ_ZLECAJACEGO FROM PROJEKTY WHERE ID_PROJEKTU = '" + idProjektu +"'";
            
            ResultSet rs = stat.executeQuery(query);
           
            nowyKomentarz = rs.getString("KOMENTARZ_ZLECAJACEGO") + dataKomentarza + "\n" + textareaNowyKomentarzProjektu.getText() + "\n\n";
            
            query = "UPDATE PROJEKTY SET KOMENTARZ_ZLECAJACEGO = '" + nowyKomentarz + "' WHERE ID_PROJEKTU = '" + idProjektu + "'";
            
            stat.executeUpdate(query);

            query = "SELECT KOMENTARZ_ZLECAJACEGO FROM PROJEKTY WHERE ID_PROJEKTU = '" + idProjektu +"'";
            
            rs = stat.executeQuery(query);
            
            textareaKomentarzeProjektu.setText(rs.getString("KOMENTARZ_ZLECAJACEGO"));            

            stat.executeUpdate(query);            
                
            stat.close();
            connection.commit();
            connection.close();

        } catch (SQLException e) {
            System.err.println(" nie można wykonac tego zapytania: " + e.getMessage());
        }        
        
    }
    
}
