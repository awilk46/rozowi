/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProjektZespolowy;

import static ProjektZespolowy.Panel_Lidera_GrupyController.getWybraneZadanie;
import static ProjektZespolowy.Panel_LogowaniaController.getZalogowany;
import static ProjektZespolowy.Polaczenie.connect;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author MaRkOs
 */



public class Panel_Edycji_ProjektowController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private TextField textfieldNazwaProjektu;
    
    @FXML
    private TextField textfieldKoszt;
    
    @FXML
    private ComboBox comboboxWybierzProjekt;
    
    @FXML
    private ComboBox comboboxWybierzKierownika;
    
    @FXML
    private TextArea textareaOpisProjektu;
    
    @FXML
    private TextArea textareaKomentarzProjektu;
    
    @FXML
    private Button buttonAktualizujProjekt;
    
    @FXML
    private DatePicker datepickerDataEnd;
    
    @FXML
    private Button buttonWroc;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        WczytanieWolnychKierownikow();
        WczytajProjekty();
    }

    
    String query;
    

    public void WczytanieWolnychKierownikow(){
        
                try {

                Connection connection = connect();
                Statement stat = connection.createStatement();
                
                query = "SELECT DISTINCT ID_UZYTKOWNIKA, IMIE, NAZWISKO FROM UZYTKOWNICY, PROJEKTY WHERE UZYTKOWNICY.ID_STANOWISKA = 3 AND UZYTKOWNICY.ID_PROJEKTU is NULL";
                
                ResultSet rs = stat.executeQuery(query);
                
                comboboxWybierzKierownika.getItems().clear();
                
                while(rs.next()){
                    comboboxWybierzKierownika.getItems().addAll(rs.getString("IMIE") + " " + rs.getString("NAZWISKO"));    
                }

                stat.executeUpdate(query);
                
                stat.close();
                connection.commit();
                connection.close();

            } catch (SQLException e) {
                
                PokazAlert("Informacja","Błąd","Niestety wystąpił błąd z bazą danych. Nie udało się wczytać kierowników");
                
                System.err.println(" nie można wykonac tego zapytania WCZYTAJ KIEROWNIKÓW: " + e.getMessage());
            }
    }

    
        public void WczytajProjekty() {
        try {

                Connection connection = connect();
                Statement stat = connection.createStatement();
                
                query = "SELECT NAZWA_PROJEKTU FROM PROJEKTY";
                
                ResultSet rs = stat.executeQuery(query);
                
                while(rs.next()) {
                    comboboxWybierzProjekt.getItems().addAll(rs.getString("NAZWA_PROJEKTU"));   
                }
                
                rs.close();
                System.out.println("Zamkniete przy rs WczytajProjekty()");
                stat.close();
                connection.commit();
                connection.close();

            } catch (SQLException e) {
                
                PokazAlert("Informacja","Błąd","Niestety wystąpił błąd z bazą danych. Nie udało się wczytać projektów");
                
                System.err.println(" nie można wykonac tego zapytania WCZYTAJ PROJEKTY: " + e.getMessage());
            }        
    }
    
        
     String nazwaWybranegoKierownika = "";   
     String nazwaWybranegoKierownikaImie = "";   
     String nazwaWybranegoKierownikaNazwisko = "";   
     String[] nazwa;  
     int idWybranegoKierownika;
     
        
    @FXML
    private void ActionComboBoxWybierzKierownika(ActionEvent event) {
     
        nazwaWybranegoKierownika = (String) comboboxWybierzKierownika.getSelectionModel().getSelectedItem();
        
        nazwa = nazwaWybranegoKierownika.split(" ");
        
        nazwaWybranegoKierownikaImie = nazwa[0];
        System.out.println(""+nazwaWybranegoKierownikaImie);
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
                
                PokazAlert("Informacja","Błąd","Niestety wystąpił błąd z bazą danych. Nie udało się wybrać kierownika");
                
                System.err.println(" nie można wykonac tego zapytania WYBIERZ KIEROWNIKA: " + e.getMessage());
            }       
        
    }

    
    String nazwaProjektu;
    int idProjektu;
    
    
    
    @FXML
    private void ActionComboBoxWybierzProjekt(ActionEvent event) {
        
        nazwaProjektu = (String) comboboxWybierzProjekt.getSelectionModel().getSelectedItem();
        
        try {

            Connection connection = connect();
            Statement stat = connection.createStatement();
            
            query = "SELECT * FROM PROJEKTY WHERE NAZWA_PROJEKTU = '" + nazwaProjektu +"'";
            
            ResultSet rs = stat.executeQuery(query);
            
            idProjektu = rs.getInt("ID_PROJEKTU");
            nazwaProjektu = rs.getString("NAZWA_PROJEKTU");
            dataEnd = rs.getString("DATA_END");
            kosztProjektu = rs.getString("KOSZT_PROJEKTU");
            opisProjektu = rs.getString("OPIS_PROJEKTU");
            komentarzProjektu = rs.getString("KOMENTARZ_ZLECAJACEGO");
            idWybranegoKierownika = rs.getInt("ID_KIEROWNIKA");
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            
            LocalDate localDate = LocalDate.parse(dataEnd, formatter);
            
            datepickerDataEnd.setValue(localDate);
            
            rs.close();
            
            query = "SELECT IMIE, NAZWISKO FROM UZYTKOWNICY WHERE ID_UZYTKOWNIKA = " + idWybranegoKierownika +"";
            
            rs = stat.executeQuery(query);
            
            nazwaWybranegoKierownikaImie = rs.getString("IMIE");
            nazwaWybranegoKierownikaNazwisko = rs.getString("NAZWISKO");            
            
            comboboxWybierzKierownika.setValue(nazwaWybranegoKierownikaImie + " " + nazwaWybranegoKierownikaNazwisko);
            
            rs.close();
            stat.close();
            connection.commit();
            connection.close();
            
            textfieldNazwaProjektu.setText(nazwaProjektu);
            textfieldKoszt.setText(kosztProjektu);
            textareaOpisProjektu.setText(opisProjektu);
            textareaKomentarzProjektu.setText(komentarzProjektu);
            
        } catch (SQLException e) {
            
            PokazAlert("Informacja","Błąd","Niestety wystąpił błąd z bazą danych. Nie udało się wybrać projektu");
            
            System.err.println(" nie można wykonac tego zapytania WYBIERZ PROJEKT: " + e.getMessage());
        }
    }

    
    String dataEnd;
    
    
    @FXML
    private void ActionDatePickerTerminEnd(ActionEvent event) {
        
        dataEnd = String.valueOf(datepickerDataEnd.getValue());
        
        System.out.println("Wybrana data: "+dataEnd);
        
    }        

    
    String opisProjektu;
    String komentarzProjektu;
    String kosztProjektu;
    
    
    @FXML
    private void ActionAktualizujProjekt(ActionEvent event) {
        
        nazwaProjektu = textfieldNazwaProjektu.getText();
        opisProjektu = textareaOpisProjektu.getText();
        komentarzProjektu = textareaKomentarzProjektu.getText();
        kosztProjektu = textfieldKoszt.getText();
        
        try {

                Connection connection = connect();
//                Statement stat = connection.createStatement();
//                
//                query = "UPDATE PROJEKTY SET NAZWA_PROJEKTU ='" + nazwaProjektu + "', DATA_END = '" + dataEnd +"', KOSZT_PROJEKTU = " + kosztProjektu +", OPIS_PROJEKTU = '" + opisProjektu + "', KOMENTARZ_ZLECAJACEGO = '" + komentarzProjektu + "' WHERE ID_PROJEKTU =" + idProjektu + "";
//            
//                stat.executeUpdate(query);
//                
//                stat.close();

                query = "UPDATE PROJEKTY SET NAZWA_PROJEKTU = ?, DATA_END = ?, KOSZT_PROJEKTU = ?, OPIS_PROJEKTU = ?, KOMENTARZ_ZLECAJACEGO = ? WHERE ID_PROJEKTU = ?";
                PreparedStatement stat = connection.prepareStatement(query);
                stat.setString(1, nazwaProjektu);
                stat.setString(2, dataEnd);
                stat.setString(3, kosztProjektu);
                stat.setString(4, opisProjektu);
                stat.setString(5, komentarzProjektu);
                stat.setInt(6, idProjektu);

                stat.executeUpdate();

                stat.close();

                connection.commit();
                connection.close();
                
                PokazAlert("Informacja","Potwierdzenie","Zaaktualizowano Projekt!");

            } catch (SQLException e) {
                
                PokazAlert("Informacja","Błąd","Niestety wystąpił błąd z bazą danych. Nie udało się zaaktualizować projektu");
                
                System.err.println(" nie można wykonac tego zapytania: AKTUALIZUJ PROJEKT" + e.getMessage());
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
    
    
    public void PokazAlert(String tytul, String headText, String content) {
    
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(tytul);
        alert.setHeaderText(headText);
        alert.setContentText(content);

        alert.showAndWait();
    }    
    
    
}
