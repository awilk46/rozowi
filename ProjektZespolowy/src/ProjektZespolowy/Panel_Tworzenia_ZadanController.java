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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import static ProjektZespolowy.Panel_LogowaniaController.getZalogowany;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
        
        WczytajIdProjektu();
        WczytajSprinty();
        WczytajPriorytet();
        WczytajGrupy();
        WczytaniePracownikow();
        
        System.out.println("ZALOGOWANY ID "+getZalogowany());
        
    }
    
    
    public void WczytajPriorytet() {
        try {

                Connection connection = connect();
                Statement stat = connection.createStatement();
                
                String query = "SELECT NAZWA_PRIORYTETU FROM PRIORYTETY";
                
                ResultSet rs = stat.executeQuery(query);
                
                while(rs.next()) {
                    comboboxWybierzPriorytet.getItems().addAll(rs.getString("NAZWA_PRIORYTETU"));   
                }
                
                stat.close();
                connection.commit();
                connection.close();

            } catch (SQLException e) {
                System.err.println(" nie można wykonac tego zapytania: " + e.getMessage());
            }        
    }    


    public void WczytaniePracownikow() {
        
                try {

                Connection connection = connect();
                Statement stat = connection.createStatement(); //INNER JOIN PROJEKTY ON UZYTKOWNICY.ID_UZYTKOWNIKA != PROJEKTY.ID_KIEROWNIKA
                
                String query = "SELECT DISTINCT ID_UZYTKOWNIKA, IMIE, NAZWISKO FROM UZYTKOWNICY WHERE ID_STANOWISKA = 5 AND ID_GRUPY="+idGrupy;
                
                ResultSet rs = stat.executeQuery(query);
                
                comboboxWybierzCzGrupy.getItems().clear();
                
                while(rs.next()){
                    comboboxWybierzCzGrupy.getItems().addAll(rs.getString("IMIE") + " " + rs.getString("NAZWISKO"));    
                }
                
                rs.close();
                stat.close();
                connection.commit();
                connection.close();

            } catch (SQLException e) {
                System.err.println(" nie można wykonac tego zapytania: 2" + e.getMessage());
            }
    }
   
    
    public void WczytajGrupy() {
        try {

                Connection connection = connect();
                Statement stat = connection.createStatement();
                
                String query = "SELECT NAZWA_GRUPY FROM GRUPY WHERE ID_PROJEKTU ="+idProjektu;
                
                ResultSet rs = stat.executeQuery(query);
                
                while(rs.next()) {
                    comboboxWybierzGrupe.getItems().addAll(rs.getString("NAZWA_GRUPY"));   
                }
                        
                stat.close();
                connection.commit();
                connection.close();

            } catch (SQLException e) {
                System.err.println(" nie można wykonac tego zapytania: " + e.getMessage());
            }        
    }    

    
    public void WczytajIdProjektu() {
                
        try {

                Connection connection = connect();
                Statement stat = connection.createStatement();
                
                String query = "SELECT ID_PROJEKTU FROM UZYTKOWNICY WHERE ID_UZYTKOWNIKA ="+getZalogowany();
                
                ResultSet rs = stat.executeQuery(query);
                
                idProjektu = rs.getInt("ID_PROJEKTU");
                
                stat.close();
                connection.commit();
                connection.close();

            } catch (SQLException e) {
                System.err.println(" nie można wykonac tego zapytania: WCZYTAJ ID PROJEKTU" + e.getMessage());
            }
    }    
    

    public void WczytajSprinty() {
        try {

                Connection connection = connect();
                Statement stat = connection.createStatement();
                
                String query = "SELECT NAZWA_SPRINTU FROM SPRINTY WHERE ID_PROJEKTU ="+idProjektu;
                
                ResultSet rs = stat.executeQuery(query);
                
                while(rs.next()) {
                    comboboxWybierzSprint.getItems().addAll(rs.getString("NAZWA_SPRINTU"));   
                }
                
                stat.close();
                connection.commit();
                connection.close();

            } catch (SQLException e) {
                System.err.println(" nie można wykonac tego zapytania: " + e.getMessage());
            }        
    }    
    
    
    int idStanowiska;
    String query;
    

    @FXML
    private void ActionWroc(ActionEvent event) throws IOException, SQLException {
        
        ((Node)(event.getSource())).getScene().getWindow().hide();
        
        Parent root = null;
        Stage stage = new Stage();
        
        Connection connection = connect();
        Statement stat = connection.createStatement();
            
        query = "SELECT ID_STANOWISKA FROM UZYTKOWNICY WHERE ID_UZYTKOWNIKA ="+getZalogowany();
            
        ResultSet rs = stat.executeQuery(query);
        
        idStanowiska = rs.getInt("ID_STANOWISKA");
        
        switch(idStanowiska) {
            
            case 3:
                root = FXMLLoader.load(getClass().getResource("Panel_Kierownika.fxml"));
                break;
            case 4: 
                root = FXMLLoader.load(getClass().getResource("Panel_Lidera_Grupy.fxml"));
                break;
        }
        
        rs.close();
        stat.close();
        connection.commit();
        connection.close();
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add
        (Glowna.class.getResource("Design.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }    

    
    String wybranyPriorytet;
    
    
    @FXML
    private void ActionComboBoxWybierzPriorytet(ActionEvent event) {
        
        wybranyPriorytet = (String) comboboxWybierzPriorytet.getSelectionModel().getSelectedItem();
        
    }

    
    int idGrupy;
    String nazwaCzGrupyImie;
    String nazwaCzGrupyNazwisko;
    String nazwaCzGrupy;
    String[] temp;
    
    
    @FXML
    private void ActionComboBoxWybierzCzGrupy(ActionEvent event) {
        
        nazwaCzGrupy = (String) comboboxWybierzCzGrupy.getSelectionModel().getSelectedItem();
        
        temp = nazwaCzGrupy.split(" ");
        
        nazwaCzGrupyImie = temp[0];
        System.out.println(""+nazwaCzGrupyImie);
        nazwaCzGrupyNazwisko = temp[1];
        
            try {

                Connection connection = connect();
                Statement stat = connection.createStatement();
                
                query = "SELECT ID_UZYTKOWNIKA FROM UZYTKOWNICY WHERE IMIE='" + nazwaCzGrupyImie +"' AND NAZWISKO='" + nazwaCzGrupyNazwisko + "'";
                
                ResultSet rs = stat.executeQuery(query);

                idPrzypisanego = rs.getInt("ID_UZYTKOWNIKA");
                
                System.out.println("Id CzGrupy: " + idPrzypisanego);
                
                rs.close();
                stat.close();
                connection.commit();
                connection.close();

            } catch (SQLException e) {
                System.err.println(" nie można wykonac tego zapytania: WYBIERZ CZ GRUPY" + e.getMessage());
            }         
        
    }

    
    String nazwaSprintu;
    int idSprintu;
    
    
    @FXML
    private void ActionComboBoxWybierzSprint(ActionEvent event) {
        
        nazwaSprintu = (String) comboboxWybierzSprint.getSelectionModel().getSelectedItem();
        
        try {

            Connection connection = connect();
            Statement stat = connection.createStatement();
            
            query = "SELECT ID_SPRINTU FROM SPRINTY WHERE NAZWA_SPRINTU = '" + nazwaSprintu +"' AND ID_PROJEKTU ="+idProjektu;
            
            ResultSet rs = stat.executeQuery(query);
            
            idSprintu = rs.getInt("ID_SPRINTU");
            
            rs.close();
            stat.close();
            connection.commit();
            connection.close();
            
        } catch (SQLException e) {
            System.err.println(" nie można wykonac tego zapytania: WYBIERZ SPRINT" + e.getMessage());
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
            
            idGrupy = rs.getInt("ID_GRUPY");
            
            rs.close();
            stat.close();
            connection.commit();
            connection.close();
            
            comboboxWybierzCzGrupy.getItems().clear();
            
            WczytaniePracownikow();
            
        } catch (SQLException e) {
            System.err.println(" nie można wykonac tego zapytania:  WYBIERZ GRUPE" + e.getMessage());
        }
                        
    }

    
    int idProjektu;
    String nazwaProjektu;

    
    String nazwaZadania;
    String opisZadania;
    String komentarzZadania;
    int statusZadania = 1;
    int idZglaszajacego = getZalogowany();
    int idPrzypisanego;
    String dataUtworzenia;
    String dataAktualizacji;
    
    
    @FXML
    private void ActionButtonUtworzZadanie(ActionEvent event) {
        
        nazwaZadania = textfieldNazwaZadania.getText();
        opisZadania = textareaOpisZadania.getText();
        komentarzZadania = textareaKomentarzZadania.getText();
        
        System.out.println("ZALOGOWANY ID = "+getZalogowany()); 
        
        dataUtworzenia = String.valueOf(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE));
        dataAktualizacji = dataUtworzenia;
        
        try {

                Connection connection = connect();
                Statement stat = connection.createStatement();

                query = "INSERT INTO ZADANIA(NAZWA_ZADANIA, STATUS, PRIORYTET, ID_ZGLASZAJACEGO, ID_PRZYPISANEGO, DATA_UTWORZENIA, DATA_AKTUALIZACJI, OPIS, KOMENTARZ, ID_PROJEKTU, ID_SPRINTU, ID_GRUPY) VALUES"
                        + " ('" + nazwaZadania + "','" + statusZadania + "','" + wybranyPriorytet + "','" + idZglaszajacego + "','" + idPrzypisanego + "','" + dataUtworzenia + "','" + dataAktualizacji + "','" + opisZadania + "','" + komentarzZadania + "','" + idProjektu + "','" + idSprintu + "','" + idGrupy + "')";
                
                stat.executeUpdate(query);
                
                JOptionPane.showMessageDialog(null, "Utworzono Zadanie", "Tworzenie Zadania", JOptionPane.INFORMATION_MESSAGE);
                
                WczytajPriorytet();
                
                stat.close();
                connection.commit();
                connection.close();

            } catch (SQLException e) {
                System.err.println(" nie można wykonac tego zapytania: UTWORZ ZADANIE" + e.getMessage());
            }
    }
    
}
