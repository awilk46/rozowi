/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProjektZespolowy;

import static ProjektZespolowy.Panel_LogowaniaController.getZalogowany;
import static ProjektZespolowy.Panel_Lidera_GrupyController.getWybraneZadanie;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author MaRkOs
 */

public class Panel_Edycji_ZadanController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
        @FXML
    private Button buttonAktualizujZadanie;
    
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
        
        SprawdzKto();
        WczytajPriorytet();
        WczytaniePracownikow();
        WczytajSprinty();
    }

    
    int idProjektu;
    int idGrupy;
    String nazwaZadania;
    String opis;
    String komentarz;
    
    
    public void SprawdzKto() {
        
        try {
            Connection connection = connect();
            Statement stat = connection.createStatement();

            query = "SELECT ID_STANOWISKA, ID_PROJEKTU, ID_GRUPY FROM UZYTKOWNICY WHERE ID_UZYTKOWNIKA ="+getZalogowany();

            ResultSet rs = stat.executeQuery(query);

            idStanowiska = rs.getInt("ID_STANOWISKA");
            idProjektu = rs.getInt("ID_PROJEKTU");
            idGrupy = rs.getInt("ID_GRUPY");

            switch(idStanowiska) {
                case 3:
                    comboboxWybierzProjekt.setDisable(false);
                    comboboxWybierzGrupe.setDisable(false);
                    break;
                case 4:
                    comboboxWybierzProjekt.setDisable(true);
                    comboboxWybierzGrupe.setDisable(true);
                    break;
                }
        
            stat.close();
            connection.commit();
            connection.close();
                
        } catch (SQLException e) {
                System.err.println(" nie można wykonac tego zapytania: WCZYTAJ sprawdzkto 1" + e.getMessage());
        }
        
        try {

            Connection connection = connect();
            Statement stat = connection.createStatement();            
            
            query = "SELECT NAZWA_ZADANIA, OPIS, KOMENTARZ FROM ZADANIA WHERE ID_ZADANIA ="+getWybraneZadanie();

            ResultSet rs = stat.executeQuery(query);

            nazwaZadania = rs.getString("NAZWA_ZADANIA");
            opis = rs.getString("OPIS");
            komentarz = rs.getString("KOMENTARZ");

            textfieldNazwaZadania.setText(nazwaZadania);
            textareaOpisZadania.setText(opis);
            textareaOpisZadania.setWrapText(true);
            textareaKomentarzZadania.setText(komentarz);
            textareaKomentarzZadania.setWrapText(true);

            stat.close();
            connection.commit();
            connection.close();
        } catch (SQLException e) {
                System.err.println(" nie można wykonac tego zapytania: WCZYTAJ sprawdzkto 2" + e.getMessage());
        }
    }
    
    
    String query;
    int idStanowiska;
    

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
        
        System.out.println("Wybrany priorytet: "+wybranyPriorytet);
    }

    
    String nazwaCzGrupy;
    String[] temp;
    String nazwaCzGrupyImie;
    String nazwaCzGrupyNazwisko;
    int idPrzypisanego;
    
    
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
        System.out.println("Wybrana NAZWA SPRINTU: "+nazwaSprintu);
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
            
            System.out.println("Wybrane ID SPRINTU: "+idSprintu);
            
        } catch (SQLException e) {
            System.err.println(" nie można wykonac tego zapytania: WYBIERZ SPRINT " + e.getMessage());
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
            
            stat.close();
            connection.commit();
            connection.close();
            
            comboboxWybierzCzGrupy.getItems().clear();
            
            WczytaniePracownikow();
            
        } catch (SQLException e) {
            System.err.println(" nie można wykonac tego zapytania:  WYBIERZ GRUPE" + e.getMessage());
        }
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
            
            WczytajGrupy();
            WczytajSprinty();
            
        } catch (SQLException e) {
            System.err.println(" nie można wykonac tego zapytania: WYBIERZ PROJEKT" + e.getMessage());
        }        
        
    }

    
    String opisZadania;
    String komentarzZadania;
    String dataUtworzenia;
    String dataAktualizacji;
    String dataDzisiejsza;
   int idZadania;
    
    
    @FXML
    private void ActionButtonAktualizujZadanie(ActionEvent event) {
        
        nazwaZadania = textfieldNazwaZadania.getText();
        opisZadania = textareaOpisZadania.getText();
        komentarzZadania = textareaKomentarzZadania.getText();
        
        System.out.println("ZALOGOWANY ID = "+getZalogowany()); 
        
        dataAktualizacji = String.valueOf(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE));
        
        try {

                Connection connection = connect();
                Statement stat = connection.createStatement();
                
                query = "UPDATE ZADANIA SET NAZWA_ZADANIA ='" + nazwaZadania + "', PRIORYTET = '" + wybranyPriorytet +"', ID_PRZYPISANEGO = " + idPrzypisanego +", DATA_AKTUALIZACJI = '" + dataAktualizacji + "', OPIS = '" + opisZadania + "', KOMENTARZ = '" + komentarzZadania + "', ID_SPRINTU = " + idSprintu + " WHERE ID_ZADANIA =" + getWybraneZadanie() + "";
            
                stat.executeUpdate(query);
                
                //comboboxWybierzProjekt.getItems().clear();
                //comboboxWybierzGrupe.getItems().clear();
                
                stat.close();
                connection.commit();
                connection.close();
                
                JOptionPane.showMessageDialog(null, "Zaktualizowano Zadanie", "Aktualizacja Zadania", JOptionPane.INFORMATION_MESSAGE);

            } catch (SQLException e) {
                System.err.println(" nie można wykonac tego zapytania: AKTUALIZUJ ZADANIE" + e.getMessage());
            }
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
                
                rs.close();
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
                
                String queryLider = "SELECT ID_UZYTKOWNIKA, IMIE, NAZWISKO FROM UZYTKOWNICY WHERE ID_UZYTKOWNIKA = " + getZalogowany() + " AND ID_GRUPY=" + idGrupy + " AND ID_STANOWISKA = 4";
                
                rs = stat.executeQuery(queryLider);
                System.out.println("IMIE POBRANE: "+rs.getString("IMIE"));
                
                comboboxWybierzCzGrupy.getItems().addAll(rs.getString("IMIE") + " " + rs.getString("NAZWISKO"));

                stat.executeUpdate(query);
                rs.close();
                stat.close();
                connection.commit();
                connection.close();

            } catch (SQLException e) {
                System.err.println(" nie można wykonac tego zapytania: 2" + e.getMessage());
            }
    }


    public void WczytajProjekty() {
        try {

                Connection connection = connect();
                Statement stat = connection.createStatement();
                
                String query = "SELECT NAZWA_PROJEKTU FROM PROJEKTY";
                
                ResultSet rs = stat.executeQuery(query);
                
                while(rs.next()) {
                    comboboxWybierzProjekt.getItems().addAll(rs.getString("NAZWA_PROJEKTU"));   
                }
                rs.close();
                stat.close();
                connection.commit();
                connection.close();

            } catch (SQLException e) {
                System.err.println(" nie można wykonac tego zapytania: " + e.getMessage());
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
                rs.close();
                stat.close();
                connection.commit();
                connection.close();

            } catch (SQLException e) {
                System.err.println(" nie można wykonac tego zapytania: " + e.getMessage());
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
                rs.close();
                stat.close();
                connection.commit();
                connection.close();

            } catch (SQLException e) {
                System.err.println(" nie można wykonac tego zapytania: " + e.getMessage());
            }        
    }


    
    
}
