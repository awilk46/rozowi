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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import static ProjektZespolowy.Polaczenie.connect;
import java.sql.Connection;
import java.sql.Date;
import static java.sql.JDBCType.NULL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javax.swing.JOptionPane;
import static javax.xml.bind.DatatypeConverter.parseString;



/**
 * FXML Controller class
 *
 * @author MaRkOs
 */

public class Panel_Tworzenia_ProjektowController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private TextField textfieldNazwaFirmy;
    
    @FXML
    private TextField textfieldNazwaProjektu;
    
    @FXML
    private TextField textfieldAdresFirmy;
    
    @FXML
    private TextField textfieldImiePrzedstawiciela;
    
    @FXML
    private TextField textfieldNazwiskoPrzedstawiciela;
    
    @FXML
    private TextField textfieldEmailFirmy;
    
    @FXML
    private TextField textfieldKoszt;
    
    @FXML
    private TextField textfieldLoginPrzedstawiciela;
    
    @FXML
    private PasswordField passwordfieldHasloPrzedstawiciela;
    
    @FXML
    private TextArea textareaOpisFirmy;
    
    @FXML
    private TextArea textareaOpisProjektu;
    
    @FXML
    private ComboBox comboboxWybierzFirme;
    
    @FXML
    private ComboBox comboboxWybierzKierownika;
    
    @FXML
    private DatePicker datepickerDataEnd;
    
    @FXML
    private Button buttonDodajProjekt;
    
    @FXML
    private Button buttonDodajFirme;
    
    @FXML
    private Button buttonWroc;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        WczytanieFirm();
        
        WczytanieWolnychKierownikow();
    
    }    
    
    
    public void WczytanieFirm(){
        
                try {

                Connection connection = connect();
                Statement stat = connection.createStatement();
                
                query = "SELECT NAZWA_FIRMY FROM FIRMY";
                
                ResultSet rs = stat.executeQuery(query);
                
                while(rs.next()){
                    comboboxWybierzFirme.getItems().addAll(rs.getString("NAZWA_FIRMY"));    
                }

                stat.executeUpdate(query);
                
                stat.close();
                connection.commit();
                connection.close();

            } catch (SQLException e) {
                System.err.println(" nie można wykonac tego zapytania: 1" + e.getMessage());
            }
    }
    
    
    public void WczytanieWolnychKierownikow(){
        
                try {

                Connection connection = connect();
                Statement stat = connection.createStatement(); //INNER JOIN PROJEKTY ON UZYTKOWNICY.ID_UZYTKOWNIKA != PROJEKTY.ID_KIEROWNIKA
                
                query = "SELECT DISTINCT ID_UZYTKOWNIKA, IMIE, NAZWISKO FROM UZYTKOWNICY, PROJEKTY WHERE UZYTKOWNICY.ID_STANOWISKA = 3 AND UZYTKOWNICY.ID_PROJEKTU is NULL";
                
                ResultSet rs = stat.executeQuery(query);
                
                while(rs.next()){
                    comboboxWybierzKierownika.getItems().addAll(rs.getString("IMIE") + " " + rs.getString("NAZWISKO"));    
                }

                stat.executeUpdate(query);
                
                stat.close();
                connection.commit();
                connection.close();

            } catch (SQLException e) {
                System.err.println(" nie można wykonac tego zapytania: 2" + e.getMessage());
            }
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

    String query;
    String wybranaFirma;
    String nazwaProjektu;
    int koszt;
    String opisProjektu; 
    int idFirmyZlecajacej;
    int idZadania;
    String  dataStart;
    String dataEnd; // to do ustalenia przy DATA PICKER
    
    
    // CAŁY COMBOBOX DO ZROBIENIA, DO TEGO TRZEBA ZROBIĆ KLASY DLA PROJEKTOW / ZADAN / GRUP
    
    
    @FXML
    private void ActionComboBoxWybierzFirme(ActionEvent event) {        
        
    }

    
    @FXML
    private void ActionButtonDodajFirme(ActionEvent event) {
        
        String nazwaFirmy = textfieldNazwaFirmy.getText();
        String adresFirmy = textfieldAdresFirmy.getText();
        String imiePrzedstawiciela = textfieldImiePrzedstawiciela.getText();
        String nazwiskoPrzedstawiciela = textfieldNazwiskoPrzedstawiciela.getText();
        String opisFirmy = textareaOpisFirmy.getText();
        String email = textfieldEmailFirmy.getText();
        int idPrzedstawiciela = 0;
        int idProjektu = 0;
        int idStanowiska = 5;
        int idFirmy = 0;
        String loginPrzedstawiciela = textfieldLoginPrzedstawiciela.getText();
        String hasloPrzedstawiciela = passwordfieldHasloPrzedstawiciela.getText();

        if(!nazwaFirmy.isEmpty() || !adresFirmy.isEmpty() || !imiePrzedstawiciela.isEmpty() || !nazwiskoPrzedstawiciela.isEmpty() || !opisFirmy.isEmpty() || !email.isEmpty() || !loginPrzedstawiciela.isEmpty() || !hasloPrzedstawiciela.isEmpty()){
            try {

                Connection connection = connect();
                Statement stat = connection.createStatement();

                query = "INSERT INTO FIRMY(NAZWA_FIRMY,ADRES_FIRMY,OPIS_FIRMY,EMAIL_FIRMY) VALUES"
                        + " ('" + nazwaFirmy + "','" + adresFirmy + "','" + opisFirmy + "','" + email + "')";
                
                stat.executeUpdate(query);
                
                query = "SELECT ID_FIRMY FROM FIRMY WHERE NAZWA_FIRMY ='" + nazwaFirmy +"'";
                
                ResultSet rs = stat.executeQuery(query);
                
                idFirmy = rs.getInt("ID_FIRMY");
                
                query = "INSERT INTO UZYTKOWNICY(IMIE,NAZWISKO,ID_STANOWISKA,ID_FIRMY,LOGIN,HASLO) VALUES"
                        + " ('" + imiePrzedstawiciela + "','" + nazwiskoPrzedstawiciela + "','" + idStanowiska + "','" + idFirmy + "','" + loginPrzedstawiciela + "','" + hasloPrzedstawiciela + "')";                

                stat.executeUpdate(query);
                
                stat.close();
                connection.commit();
                connection.close();
                
                textfieldNazwaFirmy.clear();
                textfieldAdresFirmy.clear();
                textfieldImiePrzedstawiciela.clear();
                textfieldNazwiskoPrzedstawiciela.clear();
                textareaOpisFirmy.clear();
                textfieldEmailFirmy.clear();
                textfieldLoginPrzedstawiciela.clear();
                passwordfieldHasloPrzedstawiciela.clear();
                
                JOptionPane.showMessageDialog(null, "Dodałeś firmę do bazy danych", "Stworzenie Firmy", JOptionPane.INFORMATION_MESSAGE);

            } catch (SQLException e) {
                System.err.println(" nie można wykonac tego zapytania: 3" + e.getMessage());
            }
        }
       
        WczytanieFirm();
    }

    
    @FXML
    private void ActionDatePickerTerminEnd(ActionEvent event) {
        
        dataEnd = String.valueOf(datepickerDataEnd.getValue());
        
        System.out.println("Wybrana data: "+dataEnd);
        
    }

    
    String statusProjektu;
      
    @FXML
    private void ActionButtonDodajProjekt(ActionEvent event) {
        
        nazwaProjektu = textfieldNazwaProjektu.getText();
        koszt = Integer.valueOf(textfieldKoszt.getText());
        opisProjektu = textareaOpisProjektu.getText();
        
        dataStart = String.valueOf(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE));
        statusProjektu = "Zlecony";        
        
        wybranaFirma = (String) comboboxWybierzFirme.getSelectionModel().getSelectedItem();
        
            try {

                Connection connection = connect();
                Statement stat = connection.createStatement();
                
                query = "SELECT ID_FIRMY FROM FIRMY WHERE NAZWA_FIRMY ='" + wybranaFirma +"'";
                
                ResultSet rs = stat.executeQuery(query);

                idFirmyZlecajacej = rs.getInt("ID_FIRMY");
                
                System.out.println("Id firmyzlecajacej: " + idFirmyZlecajacej);
                
                stat.executeUpdate(query);
                
                stat.close();
                connection.commit();
                connection.close();

            } catch (SQLException e) {
                System.err.println(" nie można wykonac tego zapytania: 4" + e.getMessage());
            }        
        
        try {

                Connection connection = connect();
                Statement stat = connection.createStatement();

                query = "INSERT INTO PROJEKTY(NAZWA_PROJEKTU, ID_FIRMY_ZLECAJACEJ, DATA_START, DATA_END, KOSZT_PROJEKTU, OPIS_PROJEKTU, STATUS_PROJEKTU, ID_KIEROWNIKA) VALUES"
                        + " ('" + nazwaProjektu + "','" + idFirmyZlecajacej + "','" + dataStart + "','" + dataEnd + "','" + koszt + "','" + opisProjektu + "','" + statusProjektu + "','" + idWybranegoKierownika + "')";
                
                stat.executeUpdate(query);
                
                stat.close();
                connection.commit();
                connection.close();

            } catch (SQLException e) {
                System.err.println(" nie można wykonac tego zapytania: 5" + e.getMessage());
            }        
        
    }

        int idWybranegoKierownika;
        String nazwaWybranegoKierownika;
        String[] nazwa;
        String nazwaWybranegoKierownikaImie;
        String nazwaWybranegoKierownikaNazwisko;
    
    @FXML
    private void ActionComboBoxWybierzKierownika(ActionEvent event) {
     
        nazwaWybranegoKierownika = (String) comboboxWybierzKierownika.getSelectionModel().getSelectedItem();
        
        nazwa = nazwaWybranegoKierownika.split(" ");
        
        nazwaWybranegoKierownikaImie = nazwa[0];
        nazwaWybranegoKierownikaNazwisko = nazwa[1];
        
            try {

                Connection connection = connect();
                Statement stat = connection.createStatement();
                
                query = "SELECT ID_UZYTKOWNIKA FROM UZYTKOWNICY WHERE IMIE='" + nazwaWybranegoKierownikaImie +"' AND NAZWISKO='" + nazwaWybranegoKierownikaNazwisko + "'";
                
                ResultSet rs = stat.executeQuery(query);

                idWybranegoKierownika = rs.getInt("ID_UZYTKOWNIKA");
                
                System.out.println("Id kierownika: " + idWybranegoKierownika);
                
                stat.close();
                connection.commit();
                connection.close();

            } catch (SQLException e) {
                System.err.println(" nie można wykonac tego zapytania: 6" + e.getMessage());
            }       
        
    }
    
}
