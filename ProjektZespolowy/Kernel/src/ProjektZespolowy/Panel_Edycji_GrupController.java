/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProjektZespolowy;

import static ProjektZespolowy.Panel_LogowaniaController.getStanowiskoZalogowanego;
import static ProjektZespolowy.Panel_LogowaniaController.getZalogowany;
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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author MaRkOs
 * 
 * Klasa umożliwia przeprowadzenie edycji grup, informacje o niej oraz o jej składzie osobowym
 * 
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
    private ComboBox comboboxWybierzPracownika;
    
    @FXML
    private TextField textfieldNadajGrupieNazwe;
    
    @FXML
    private TextArea textareaOpisGrupy;
    
    @FXML
    private Button buttonDodajDoGrupy;
    
    @FXML
    private Button buttonUsunZGrupy;
    
    @FXML
    private Button buttonAktualizujGrupe;
    
    @FXML
    private Button buttonWroc;
    
    @FXML
    private ListView listviewDodani;
    
    /**
     * 
     * W pierwszej kolejności wczytujemy projekty
     * następnei wczytujemy wolnych liderów, 
     * czyli liderów nie przypisanych do żadnej grupy
     * 
     * kolejno wczytujemy pracowników nie przypisanych do żadnego projektu i grupy
     * 
     * @param url
     * @param rb 
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        
        WczytajWolnychLiderow();
        
        WczytajWolnychPracownikow();

        //WczytajAktualnaGrupe();
        
        if(getStanowiskoZalogowanego()==3) {
            WczytajProjekt();
        } else {
            WczytajProjekty();
        }
    }
    
    
//    public void WczytajAktualnaGrupe() {
//     
//        try {
//
//                Connection connection = connect();
//                Statement stat = connection.createStatement();
//                
//                query = "SELECT * FROM GRUPY WHERE ID_GRUPY =(SELECT ID_GRUPY FROM UZYTKOWNICY WHERE ID_UZYTKOWNIKA ="+getZalogowany()+")";
//                
//                ResultSet rs = stat.executeQuery(query);
//                
//                while(rs.next()) {
//                    comboboxWybierzProjekt.getItems().addAll(rs.getString("NAZWA_PROJEKTU"));   
//                }
//                
//                stat.close();
//                connection.commit();
//                connection.close();
//
//            } catch (SQLException e) {
//                System.err.println(" nie można wykonac tego zapytania: " + e.getMessage());
//            }        
//        
//    }
    
    
    String query;
    
    /**
     *
     * Zostają pobrane projekty i umieszczone w comboboxie
     * 
     */
    
    public void WczytajProjekty() {
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
                
                PokazAlert("Informacja","Błąd","Niestety wystąpił błąd z bazą danych. Nie udało się wczytać projektów");
                
                System.err.println(" nie można wykonac tego zapytania WCZYTAJ PROJEKTY: " + e.getMessage());
            }        
    }


            public void WczytajProjekt() {
        try {

                Connection connection = connect();
                Statement stat = connection.createStatement();
                
                query = "SELECT NAZWA_PROJEKTU FROM PROJEKTY WHERE ID_KIEROWNIKA =" + getZalogowany();
                
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
    
    
     /**
     *
     * Zostają pobrani wolni liderzy i umieszczeni w comboboxie
     * 
     */
    
    public void WczytajWolnychLiderow(){
        
                try {

                Connection connection = connect();
                Statement stat = connection.createStatement();
                
                query = "SELECT DISTINCT ID_UZYTKOWNIKA, IMIE, NAZWISKO FROM UZYTKOWNICY WHERE ID_STANOWISKA = 4 AND ID_GRUPY is NULL";
                
                ResultSet rs = stat.executeQuery(query);
                
                comboboxWybierzLidera.getItems().clear();
                
                while(rs.next()){
                    comboboxWybierzLidera.getItems().addAll(rs.getString("IMIE") + " " + rs.getString("NAZWISKO"));    
                }

                stat.executeUpdate(query);
                
                stat.close();
                connection.commit();
                connection.close();

            } catch (SQLException e) {
                
                PokazAlert("Informacja","Błąd","Niestety wystąpił błąd z bazą danych. Nie udało się wczytać liderów");
                
                System.err.println(" nie można wykonac tego zapytania WCZYTAJ LIDERÓW: " + e.getMessage());
            }
    }    
    
    
     /**
     *
     * Zostają pobrani wolni pracownicy i umieszczeni w comboboxie
     * 
     */
    
    public void WczytajWolnychPracownikow(){
        
                try {

                Connection connection = connect();
                Statement stat = connection.createStatement();
                
                query = "SELECT DISTINCT ID_UZYTKOWNIKA, IMIE, NAZWISKO FROM UZYTKOWNICY WHERE ID_STANOWISKA = 5 AND ID_GRUPY is NULL";
                
                ResultSet rs = stat.executeQuery(query);
                
                comboboxWybierzPracownika.getItems().clear();
                
                while(rs.next()){
                    comboboxWybierzPracownika.getItems().addAll(rs.getString("IMIE") + " " + rs.getString("NAZWISKO"));    
                }

                stat.executeUpdate(query);
                
                stat.close();
                connection.commit();
                connection.close();

            } catch (SQLException e) {
                
                PokazAlert("Informacja","Błąd","Niestety wystąpił błąd z bazą danych. Nie udało się wczytać pracowników");
                
                System.err.println(" nie można wykonac tego zapytania WCZYTAJ PRACOWNIKOW: " + e.getMessage());
            }
    }


     /**
     *
     * Zostają wczytane grupy i umieszczone w comboboxie
     * 
     */
    
    public void WczytajGrupy() {
        
        comboboxWybierzGrupe.getItems().clear();
        
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
                
                PokazAlert("Informacja","Błąd","Niestety wystąpił błąd z bazą danych. Nie udało się wczytać grup");
                
                System.err.println(" nie można wykonac tego zapytania WCZYTAJ GRUPY: " + e.getMessage());
            }        
    }    
    
    
    String nazwaProjektu;
    int idProjektu;
    

    /**
     *
     * Po wybraniu projektu, pobierane jest ID projektu
     * 
     * @param event 
     */
    
    @FXML
    private void ActionComboBoxWybierzProjekt(ActionEvent event) {
        
        nazwaProjektu = (String) comboboxWybierzProjekt.getSelectionModel().getSelectedItem();
        
        try {

            Connection connection = connect();
            Statement stat = connection.createStatement();
            
            query = "SELECT ID_PROJEKTU FROM PROJEKTY WHERE NAZWA_PROJEKTU = '" + nazwaProjektu +"'";
            
            ResultSet rs = stat.executeQuery(query);
            
            idProjektu = rs.getInt("ID_PROJEKTU");
            
            //query = "SELECT * FROM PROJEKTY WHERE ID_PROJEKTU = '" + idProjektu +"'";
            
            //rs = stat.executeQuery(query);           
            
            rs.close();
            stat.close();
            connection.commit();
            connection.close();
            
            WczytajGrupy();

        } catch (SQLException e) {
            
            PokazAlert("Informacja","Błąd","Niestety wystąpił błąd z bazą danych. Nie udało się wybrać projektu");
            
            System.err.println(" nie można wykonac tego zapytania WYBIERZ PROJEKT: " + e.getMessage());
        }        
    }
    
    
    /**
     * 
     * po kliknięciu przycisku AKTUALIZUJ GRUPE pojawia się okno dialogowe
     * potwierdzające dokonaną akcję
     * 
     * @param event 
     */
    
    @FXML
    private void ActionAktualizujGrupe(ActionEvent event) {
        
        PokazAlert("Informacja","Potwierdzenie","Zaaktualizowano Grupę!");
    }
    
    
String aktualnyLiderNazwisko;    
String aktualnyLiderImie;    


    /**
     * 
     * Po wybraniu grupy pobierane są wszystkie informacje o grupie
     * w tym informacje o liderze oraz każdym członku grupy
     * 
     * Informacje zostają dodane w odpowiednie pola oraz listy
     * 
     * @param event 
     */

    @FXML
    private void ActionComboBoxWybierzGrupe(ActionEvent event) {
        
        nazwaGrupy = (String) comboboxWybierzGrupe.getSelectionModel().getSelectedItem();
            
        wybraniPracownicy.clear();
        listviewDodani.getItems().clear();
                
        try {

            Connection connection = connect();
            Statement stat = connection.createStatement();
            
            query = "SELECT * FROM GRUPY WHERE NAZWA_GRUPY = '" + nazwaGrupy +"'";
            
            ResultSet rs = stat.executeQuery(query);
            
            idGrupy = rs.getInt("ID_GRUPY");
            nazwaGrupy = rs.getString("NAZWA_GRUPY");
            idWybranegoLidera = rs.getInt("ID_LIDERA");
            opisGrupy = rs.getString("OPIS_GRUPY");
            
            rs.close();
            
            query = "SELECT IMIE, NAZWISKO FROM UZYTKOWNICY WHERE ID_UZYTKOWNIKA = "+idWybranegoLidera;
            
            rs = stat.executeQuery(query);
            
            aktualnyLiderImie = rs.getString("IMIE");
            System.out.println("IMIE: "+aktualnyLiderImie);
            aktualnyLiderNazwisko = rs.getString("NAZWISKO");
            
            rs.close();
            
            query = "SELECT IMIE, NAZWISKO FROM UZYTKOWNICY WHERE ID_GRUPY = "+idGrupy;
            
            rs = stat.executeQuery(query);
            
            while(rs.next()){
                wybraniPracownicy.add(rs.getString("IMIE") + " " + rs.getString("NAZWISKO"));
            }

            listviewDodani.setItems(wybraniPracownicy);
            
//            wybraniPracownicy.add(nazwaWybranegoPracownika);
//            listviewDodani.setItems(wybraniPracownicy);

            rs.close();
            stat.close();
            connection.commit();
            connection.close();
            
            textfieldNadajGrupieNazwe.setText(nazwaGrupy);
            textareaOpisGrupy.setText(opisGrupy);
            comboboxWybierzLidera.getItems().addAll(aktualnyLiderImie + " " + aktualnyLiderNazwisko);
            comboboxWybierzLidera.setValue(aktualnyLiderImie + " " + aktualnyLiderNazwisko);
            
        } catch (SQLException e) {
            
            PokazAlert("Informacja","Błąd","Niestety wystąpił błąd z bazą danych. Nie udało się wybrać grupy");
            
            System.err.println(" nie można wykonac tego zapytania WYBIERZ GRUPE: " + e.getMessage());
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

    
    String nazwaWybranegoLidera;
    String nazwaWybranegoLideraImie;
    String nazwaWybranegoLideraNazwisko;
    String[] nazwa;
    int idWybranegoLidera;    
    
    
    // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@             TRZEBA DODAĆ LUŹNEGO LIDERA I SPRAWDZIĆ CZY WSZYSTKO DZIAŁA
    
    /**
     * 
     * Następuje wybranie lidera i pobranie o nim informacji z bazy danych
     * 
     * @param event 
     */
    
    @FXML
    private void ActionCombBboxWybierzLidera(ActionEvent event) {
        
      if(!comboboxWybierzLidera.getSelectionModel().isEmpty()) {
            nazwaWybranegoLidera = (String) comboboxWybierzLidera.getSelectionModel().getSelectedItem();
        
        
        nazwa = nazwaWybranegoLidera.split(" ");
        
        nazwaWybranegoLideraImie = nazwa[0];
        nazwaWybranegoLideraNazwisko = nazwa[1];
        
            try {

                Connection connection = connect();
                Statement stat = connection.createStatement();
                
                query = "SELECT ID_UZYTKOWNIKA FROM UZYTKOWNICY WHERE IMIE='" + nazwaWybranegoLideraImie +"' AND NAZWISKO='" + nazwaWybranegoLideraNazwisko + "'";
                
                ResultSet rs = stat.executeQuery(query);

                idWybranegoLidera = rs.getInt("ID_UZYTKOWNIKA");
                
                stat.close();
                connection.commit();
                connection.close();

            } catch (SQLException e) {
                
                PokazAlert("Informacja","Błąd","Niestety wystąpił błąd z bazą danych. Nie udało się wybrać lidera");
                
                System.err.println(" nie można wykonac tego zapytania WYBIERZ LIDERA: " + e.getMessage());
            }
      }
    }

    
    String znajdzImie;
    String znajdzNazwisko;
    Statement stat = null;
    ObservableList<String> wybraniPracownicy = FXCollections.observableArrayList();
    ObservableList<String> usunieciPracownicy = FXCollections.observableArrayList();
    int idGrupy;    
    
    
    /**
     *
     * po kliknięciu DODAJ DO GRUPY następuje dodanie wybranego pracownika do listy grupy
     * 
     * @param event 
     */
    
    @FXML
    private void ActionButtonDodajDoGrupy(ActionEvent event) {
        
        wybraniPracownicy.add(nazwaWybranegoPracownika);
        listviewDodani.setItems(wybraniPracownicy);
        
        comboboxWybierzPracownika.getItems().remove(comboboxWybierzPracownika.getSelectionModel().getSelectedIndex());        
        
        // WczytajWolnychPracownikow();
    }

    
     /**
     *
     * po kliknięciu USUN Z GRUPY następuje usunięcie wybranego pracownika z listy grupy
     * 
     * @param event 
     */
    
    @FXML
    private void ActionButtonUsunZGrupy(ActionEvent event) {
        
        usunieciPracownicy.add(wybraniPracownicy.get(listviewDodani.getSelectionModel().getSelectedIndex()));
        wybraniPracownicy.remove(listviewDodani.getSelectionModel().getSelectedIndex());
        listviewDodani.refresh();
        
        WczytajWolnychPracownikow();
    }

    
    String nazwaGrupy;
    String opisGrupy;
    String[] nazwa2;
    String imieWybranego;
    String nazwiskoWybranego;
    int idWybranego;

    String nazwaWybranegoPracownika;
    String nazwaWybranegoPracownikaImie;
    String nazwaWybranegoPracownikaNazwisko;
    int idWybranegoPracownika;    
    
    
    /**
     * 
     * po wyborze pracownika w comboboxie następuje pobranie o nim informacji
     * i przypisanie ich pod odpowiednie zmienne
     * 
     * @param event 
     */
    
    @FXML
    private void ActionCombBboxWybierzPracownika(ActionEvent event) {
        
        if(comboboxWybierzPracownika.getSelectionModel().getSelectedItem()==null) {
            System.out.println("Niewybrany pracownik - wyjątek");
        }
        else {
            nazwaWybranegoPracownika = (String) comboboxWybierzPracownika.getSelectionModel().getSelectedItem();
            System.out.println("WYBRANY: "+nazwaWybranegoPracownika);
            nazwa = nazwaWybranegoPracownika.split(" ");

            nazwaWybranegoPracownikaImie = nazwa[0];
            nazwaWybranegoPracownikaNazwisko = nazwa[1];

                try {

                    Connection connection = connect();
                    Statement stat = connection.createStatement();

                    query = "SELECT ID_UZYTKOWNIKA FROM UZYTKOWNICY WHERE IMIE='" + nazwaWybranegoPracownikaImie +"' AND NAZWISKO='" + nazwaWybranegoPracownikaNazwisko + "'";

                    ResultSet rs = stat.executeQuery(query);

                    idWybranegoPracownika = rs.getInt("ID_UZYTKOWNIKA");

                    stat.close();
                    connection.commit();
                    connection.close();

                } catch (SQLException e) {
                    
                    PokazAlert("Informacja","Błąd","Niestety wystąpił błąd z bazą danych. Nie udało się wybrać pracownika");
                    
                    System.err.println(" nie można wykonac tego zapytania WYBIERZ PRACOWNIKA: " + e.getMessage());
                }
        }   
    } 
    
    
    /**
     *
     * Metoda powooduje aktualizację informacji o grupie poprzez połączenie z bazą danych
     * i przesłanie do niej danych prosto ze zmiennych
     * 
     * @param event 
     */
    
    @FXML
    private void ActionButtonAktualizujGrupe(ActionEvent event) {
        
        nazwaGrupy = textfieldNadajGrupieNazwe.getText();
        opisGrupy = textareaOpisGrupy.getText();
        
        try {
            Connection connection = connect();
            //Statement stat = connection.createStatement();
            
            //query = "UPDATE GRUPY SET NAZWA_GRUPY = '" + nazwaGrupy + "', ID_LIDERA =" + idWybranegoLidera + ", OPIS_GRUPY = '" + opisGrupy + "' WHERE ID_GRUPY =" + idGrupy;
            
            //stat.executeUpdate(query);
            
            query = "UPDATE GRUPY SET NAZWA_GRUPY = ?, ID_LIDERA = ?, OPIS_GRUPY = ? WHERE ID_GRUPY = ?";    
            PreparedStatement stat = connection.prepareStatement(query);
            stat.setString(1, nazwaGrupy);
            stat.setInt(2, idWybranegoLidera);
            stat.setString(3, opisGrupy);
            stat.setInt(4, idGrupy);
            
            stat.executeUpdate();
            
            stat.close();
            
            Statement stat1 = connection.createStatement();
            
            query = "UPDATE UZYTKOWNICY SET ID_GRUPY ='" + idGrupy + "', ID_PROJEKTU ='" + idProjektu + "' WHERE ID_UZYTKOWNIKA ='" + idWybranegoLidera + "'";
            
            stat1.executeUpdate(query);
            
            // DODAWANIE
            
            for(int i=0; i<wybraniPracownicy.size(); i++) {
                
                nazwa2 = wybraniPracownicy.get(i).split(" ");
                imieWybranego = nazwa2[0];
                nazwiskoWybranego = nazwa2[1];
                
                System.out.println("imie wybranego: "+nazwa2[0]+" nazwisko: "+nazwa2[1]);
                
                query = "SELECT ID_UZYTKOWNIKA FROM UZYTKOWNICY WHERE IMIE = '" + imieWybranego + "' AND NAZWISKO ='" + nazwiskoWybranego +"'";
                
                ResultSet rs = stat1.executeQuery(query);
                
                idWybranego = rs.getInt("ID_UZYTKOWNIKA");
                
                rs.close();
                
                query = "UPDATE UZYTKOWNICY SET ID_PROJEKTU = '" + idProjektu + "', ID_GRUPY = '" + idGrupy + "' WHERE ID_UZYTKOWNIKA = '" + idWybranego + "'";

                stat1.executeUpdate(query);
            }
            
            // USUWANIE 
            
            for(int j=0; j<usunieciPracownicy.size(); j++) {
                
                nazwa2 = usunieciPracownicy.get(j).split(" ");
                imieWybranego = nazwa2[0];
                nazwiskoWybranego = nazwa2[1];
                
                System.out.println("imie wybranego do usuniecia: "+nazwa2[0]+" nazwisko: "+nazwa2[1]);
                
                query = "SELECT ID_UZYTKOWNIKA FROM UZYTKOWNICY WHERE IMIE = '" + imieWybranego + "' AND NAZWISKO ='" + nazwiskoWybranego +"'";
                
                ResultSet rs = stat1.executeQuery(query);
                
                idWybranego = rs.getInt("ID_UZYTKOWNIKA");
                
                rs.close();
                
                query = "UPDATE UZYTKOWNICY SET ID_PROJEKTU = '" + idProjektu + "', ID_GRUPY = NULL WHERE ID_UZYTKOWNIKA = '" + idWybranego + "'";

                stat1.executeUpdate(query);
            }

            stat1.close();
            connection.commit();
            connection.close();
            
            usunieciPracownicy.clear();

            WczytajProjekty();

            WczytajWolnychLiderow();

            WczytajWolnychPracownikow();
            
//            textfieldNadajGrupieNazwe.clear();
//            textareaOpisGrupy.clear();
//            listviewDodani.getItems().clear();
//            wybraniPracownicy.clear();

            PokazAlert("Informacja","Potwierdzenie","Zaktualizowano Grupę!");
        
        } catch (SQLException e){
            
            PokazAlert("Informacja","Błąd","Niestety wystąpił błąd z bazą danych. Nie udało się zaaktualizować grupę");
            
            System.err.println(" nie można wykonac tego zapytania AKTUALIZUJ GRUPE: " + e.getMessage());    
        }

            usunieciPracownicy.clear();
            textfieldNadajGrupieNazwe.clear();
            textareaOpisGrupy.clear();
            listviewDodani.getItems().clear();
            wybraniPracownicy.clear();
    }
    
    
    public void PokazAlert(String tytul, String headText, String content) {
    
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(tytul);
        alert.setHeaderText(headText);
        alert.setContentText(content);

        alert.showAndWait();
    }    
    
        
}
