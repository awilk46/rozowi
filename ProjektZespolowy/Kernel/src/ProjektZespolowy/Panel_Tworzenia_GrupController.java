/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProjektZespolowy;

import static ProjektZespolowy.Polaczenie.connect;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;

/**
 * FXML Controller class
 *
 * @author MaRkOs
 */
public class Panel_Tworzenia_GrupController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
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
    private Button buttonWroc;    
    
    
    @FXML
    private ListView listviewDodani;
    @FXML
    private Button buttonAktualizujGrupe;
    @FXML
    private ComboBox<?> comboboxWybierzGrupe;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        WczytajProjekty();
        
        WczytajWolnychLiderow();
        
        WczytajWolnychPracownikow();
        
    }

    
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
                
                query = "SELECT DISTINCT ID_UZYTKOWNIKA, IMIE, NAZWISKO FROM UZYTKOWNICY, GRUPY WHERE UZYTKOWNICY.ID_STANOWISKA = 4 AND UZYTKOWNICY.ID_GRUPY is NULL";
                
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
                
                query = "SELECT DISTINCT ID_UZYTKOWNIKA, IMIE, NAZWISKO FROM UZYTKOWNICY, GRUPY WHERE UZYTKOWNICY.ID_STANOWISKA = 5 AND UZYTKOWNICY.ID_GRUPY is NULL";
                
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
            
            query = "SELECT * FROM PROJEKTY WHERE ID_PROJEKTU = '" + idProjektu +"'";
            
            rs = stat.executeQuery(query);           
            
            stat.close();
            connection.commit();
            connection.close();

        } catch (SQLException e) {
            System.err.println(" nie można wykonac tego zapytania: " + e.getMessage());
        }        
    }

    
    String nazwaWybranegoLidera;
    String nazwaWybranegoLideraImie;
    String nazwaWybranegoLideraNazwisko;
    String[] nazwa;
    int idWybranegoLidera;
    
    
    @FXML
    private void ActionCombBboxWybierzLidera(ActionEvent event) {
        
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

    
    String znajdzImie;
    String znajdzNazwisko;
    Statement stat = null;
    ObservableList<String> wybraniPracownicy = FXCollections.observableArrayList();
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
    
    
    private void ActionButtonStworzGrupe(ActionEvent event) {
        
        nazwaGrupy = textfieldNadajGrupieNazwe.getText();
        opisGrupy = textareaOpisGrupy.getText();
        
        try {
            Connection connection = connect();
            Statement stat = connection.createStatement();        
            
            //query = "SELECT ID_GRUPY FROM GRUPY WHERE ID_GRUPY = (SELECT MAX(ID_GRUPY) FROM GRUPY)";
            
            //idGrupy = rs.getInt("ID_GRUPY")+1;
            
            query = "INSERT INTO GRUPY(NAZWA_GRUPY,ID_LIDERA,ID_PROJEKTU,OPIS_GRUPY) VALUES"
                    + " ('" + nazwaGrupy + "','" + idWybranegoLidera + "','" + idProjektu + "','" + opisGrupy + "')";
                
            stat.executeUpdate(query);
            
            query = "SELECT ID_GRUPY FROM GRUPY WHERE NAZWA_GRUPY ='"+nazwaGrupy+"'";
            
            ResultSet rs = stat.executeQuery(query);
            
            idGrupy = rs.getInt("ID_GRUPY");
            
            System.out.println(" ID GRUPY: " + idGrupy);
            
            query = "UPDATE UZYTKOWNICY SET ID_GRUPY ='" + idGrupy + "', ID_PROJEKTU ='" + idProjektu + "' WHERE ID_UZYTKOWNIKA ='" + idWybranegoLidera + "'";
            
           stat.executeUpdate(query);
            
            for(int i=0; i<wybraniPracownicy.size(); i++) {
                
                nazwa2 = wybraniPracownicy.get(i).split(" ");
                imieWybranego = nazwa2[0];
                nazwiskoWybranego = nazwa2[1];
                
                System.out.println("imie wybranego: "+nazwa2[0]+" nazwisko: "+nazwa2[1]);
                
                query = "SELECT ID_UZYTKOWNIKA FROM UZYTKOWNICY WHERE IMIE = '" + imieWybranego + "' AND NAZWISKO ='" + nazwiskoWybranego +"'";
                
                rs = stat.executeQuery(query);
                
                idWybranego = rs.getInt("ID_UZYTKOWNIKA");
                
                query = "UPDATE UZYTKOWNICY SET ID_PROJEKTU = '" + idProjektu + "', ID_GRUPY = '" + idGrupy + "' WHERE ID_UZYTKOWNIKA = '" + idWybranego + "'";

                stat.executeUpdate(query);
            }

            stat.close();
            connection.commit();
            connection.close();

            WczytajProjekty();

            WczytajWolnychLiderow();

            WczytajWolnychPracownikow();
            
            textfieldNadajGrupieNazwe.clear();
            textareaOpisGrupy.clear();
            listviewDodani.getItems().clear();
            wybraniPracownicy.clear();
        
        } catch (SQLException e){
            System.err.println(" nie można wykonac tego zapytania: 5 " + e.getMessage());    
        }        
        
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Informacja");
                alert.setHeaderText("Potwierdzenie");
                alert.setContentText("Utworzono Grupę!");

                alert.showAndWait();        
        
        //JOptionPane.showMessageDialog(null, "Utworzono Grupę", "Tworzenie Grupy", JOptionPane.INFORMATION_MESSAGE);
    }

    
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
    
}
