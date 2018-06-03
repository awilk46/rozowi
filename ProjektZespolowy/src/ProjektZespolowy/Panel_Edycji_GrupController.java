/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProjektZespolowy;

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
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        WczytajProjekty();
        
        WczytajWolnychLiderow();
        
        WczytajWolnychPracownikow();

        //WczytajAktualnaGrupe();
        
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
                System.err.println(" nie można wykonac tego zapytania: " + e.getMessage());
            }        
    }    
    
    
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
                System.err.println(" nie można wykonac tego zapytania: 2" + e.getMessage());
            }
    }    
    
    
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
                System.err.println(" nie można wykonac tego zapytania: 2" + e.getMessage());
            }
    }


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
                System.err.println(" nie można wykonac tego zapytania: " + e.getMessage());
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
            System.err.println(" nie można wykonac tego zapytania: " + e.getMessage());
        }        
    }
    
    
    @FXML
    private void ActionAktualizujGrupe(ActionEvent event) {
        
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Informacja");
                alert.setHeaderText("Potwierdzenie");
                alert.setContentText("Zaaktualizowano Grupę!");

                alert.showAndWait();
        
        //JOptionPane.showMessageDialog(null, "Zaaktualizowano Grupę", "Aktualizacja Grupy", JOptionPane.INFORMATION_MESSAGE);
    }
    
    
String aktualnyLiderNazwisko;    
String aktualnyLiderImie;    


    @FXML
    private void ActionComboBoxWybierzGrupe(ActionEvent event) {
        
        nazwaGrupy = (String) comboboxWybierzGrupe.getSelectionModel().getSelectedItem();
        
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
            System.err.println(" nie można wykonac tego zapytania:  WYBIERZ GRUPE" + e.getMessage());
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
                System.err.println(" nie można wykonac tego zapytania: 6" + e.getMessage());
            }
      }
    }

    
    String znajdzImie;
    String znajdzNazwisko;
    Statement stat = null;
    ObservableList<String> wybraniPracownicy = FXCollections.observableArrayList();
    ObservableList<String> usunieciPracownicy = FXCollections.observableArrayList();
    int idGrupy;    
    
    
    @FXML
    private void ActionButtonDodajDoGrupy(ActionEvent event) {
        
        wybraniPracownicy.add(nazwaWybranegoPracownika);
        listviewDodani.setItems(wybraniPracownicy);
        
        comboboxWybierzPracownika.getItems().remove(comboboxWybierzPracownika.getSelectionModel().getSelectedIndex());        
        
        // WczytajWolnychPracownikow();
    }

    
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
                    System.err.println(" nie można wykonac tego zapytania: 6" + e.getMessage());
                }
        }   
    } 
    
    
    @FXML
    private void ActionButtonAktualizujGrupe(ActionEvent event) {
        
        nazwaGrupy = textfieldNadajGrupieNazwe.getText();
        opisGrupy = textareaOpisGrupy.getText();
        
        try {
            Connection connection = connect();
            Statement stat = connection.createStatement();
            
            query = "UPDATE GRUPY SET NAZWA_GRUPY = '" + nazwaGrupy + "', ID_LIDERA =" + idWybranegoLidera + ", OPIS_GRUPY = '" + opisGrupy + "' WHERE ID_GRUPY =" + idGrupy;
                
            stat.executeUpdate(query);
            
            query = "UPDATE UZYTKOWNICY SET ID_GRUPY ='" + idGrupy + "', ID_PROJEKTU ='" + idProjektu + "' WHERE ID_UZYTKOWNIKA ='" + idWybranegoLidera + "'";
            
            stat.executeUpdate(query);
            
            // DODAWANIE
            
            for(int i=0; i<wybraniPracownicy.size(); i++) {
                
                nazwa2 = wybraniPracownicy.get(i).split(" ");
                imieWybranego = nazwa2[0];
                nazwiskoWybranego = nazwa2[1];
                
                System.out.println("imie wybranego: "+nazwa2[0]+" nazwisko: "+nazwa2[1]);
                
                query = "SELECT ID_UZYTKOWNIKA FROM UZYTKOWNICY WHERE IMIE = '" + imieWybranego + "' AND NAZWISKO ='" + nazwiskoWybranego +"'";
                
                ResultSet rs = stat.executeQuery(query);
                
                idWybranego = rs.getInt("ID_UZYTKOWNIKA");
                
                rs.close();
                
                query = "UPDATE UZYTKOWNICY SET ID_PROJEKTU = '" + idProjektu + "', ID_GRUPY = '" + idGrupy + "' WHERE ID_UZYTKOWNIKA = '" + idWybranego + "'";

                stat.executeUpdate(query);
            }
            
            // USUWANIE 
            
            for(int j=0; j<usunieciPracownicy.size(); j++) {
                
                nazwa2 = usunieciPracownicy.get(j).split(" ");
                imieWybranego = nazwa2[0];
                nazwiskoWybranego = nazwa2[1];
                
                System.out.println("imie wybranego do usuniecia: "+nazwa2[0]+" nazwisko: "+nazwa2[1]);
                
                query = "SELECT ID_UZYTKOWNIKA FROM UZYTKOWNICY WHERE IMIE = '" + imieWybranego + "' AND NAZWISKO ='" + nazwiskoWybranego +"'";
                
                ResultSet rs = stat.executeQuery(query);
                
                idWybranego = rs.getInt("ID_UZYTKOWNIKA");
                
                rs.close();
                
                query = "UPDATE UZYTKOWNICY SET ID_PROJEKTU = '" + idProjektu + "', ID_GRUPY = NULL WHERE ID_UZYTKOWNIKA = '" + idWybranego + "'";

                stat.executeUpdate(query);
            }

            stat.close();
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

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Informacja");
                alert.setHeaderText("Potwierdzenie");
                alert.setContentText("Zaktualizowano Grupę!");

                alert.showAndWait();
        
        } catch (SQLException e){
            System.err.println(" nie można wykonac tego zapytania: 5 " + e.getMessage());    
        }

            usunieciPracownicy.clear();
            textfieldNadajGrupieNazwe.clear();
            textareaOpisGrupy.clear();
            listviewDodani.getItems().clear();
            wybraniPracownicy.clear();
        
    }   
        
}
