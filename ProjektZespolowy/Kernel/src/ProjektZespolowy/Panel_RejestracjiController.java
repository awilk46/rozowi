/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProjektZespolowy;

import static ProjektZespolowy.Panel_LogowaniaController.getZalogowany;
import static ProjektZespolowy.Polaczenie.connect;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author MaRkOs
 */

public class Panel_RejestracjiController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private TextField textfieldImie;
    
    @FXML
    private TextField textfieldNazwisko;
    
    @FXML
    private TextField textfieldLogin;
    
    @FXML
    private TextField textfieldHaslo;
    
    @FXML
    private ComboBox comboboxStanowisko;
    
    @FXML
    private Button buttonStworzKonto;
    
    @FXML
    private Button buttonWroc;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        WczytajStanowiska();
        
    }    

    
    @FXML
    private void ActionComboboxStanowisko(ActionEvent event) {
    }

    
    @FXML
    private void ActionButtonWroc(ActionEvent event) throws SQLException, IOException {
        
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

    
    String imie;
    String nazwisko;
    String login;
    String haslo;
    int idWybranegoStanowiska = 0;
    int idGrupy;
    int idProjektu;
    int idFirmy = 1;
    String loginTest;
    
    
    @FXML
    private void ActionButtonStworzKonto(ActionEvent event) {
        
        imie = textfieldImie.getText();
        nazwisko = textfieldNazwisko.getText();
        login = textfieldLogin.getText();
        haslo = textfieldHaslo.getText();
        
        if(!SprawdzCzyIstnieje(login)) {
        
            switch(""+comboboxStanowisko.getSelectionModel().getSelectedItem()) {
                case "Właściciel Firmy":
                    idWybranegoStanowiska = 2;
                    break;

                case "Kierownik Projektu":
                    idWybranegoStanowiska = 3;
                    break;

                case "Lider Grupy":
                    idWybranegoStanowiska = 4;
                    break;

                case "Pracownik":
                    idWybranegoStanowiska = 5;
                    break;
            }

            if(!imie.isEmpty() || !nazwisko.isEmpty() || !login.isEmpty() || !haslo.isEmpty() || idWybranegoStanowiska!=0){
                
                if(imie.matches("^[a-zA-Z]+$") && nazwisko.matches("^[a-zA-Z]+$")) {
                
                    try {

                            Connection connection = connect();
                            Statement stat = connection.createStatement();

                            query = "INSERT INTO UZYTKOWNICY(IMIE,NAZWISKO,ID_STANOWISKA,ID_FIRMY,LOGIN,HASLO) VALUES"
                                    + " ('" + imie + "','" + nazwisko + "','" + idWybranegoStanowiska + "','" + idFirmy + "','" + login + "','" + haslo + "')";                

                            stat.executeUpdate(query);

                            stat.close();
                            connection.commit();
                            connection.close();

                            PokazAlert("Informacja","Potwierdzenie","Dodałeś użytkownika do bazy danych");

                    } catch (SQLException e) {
                        
                        PokazAlert("Informacja","Błąd","Niestety wystąpił błąd z oprogramowaniem i bazą danych. Skontaktuj się z administratorem");
                        
                        System.err.println(" nie można wykonac tego zapytania: 3" + e.getMessage());
                    }
                } else {
                        
                    PokazAlert("Informacja","Błąd","Niestety wprowadziłeś niedozwolone znaki w polu IMIE lub NAZWISKO!");
                }
            }
        }
    }
    
    
    public void PokazAlert(String tytul, String headText, String content) {
    
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(tytul);
        alert.setHeaderText(headText);
        alert.setContentText(content);

        alert.showAndWait();
    }    

    
    String query;
    String nazwaStanowiska;
    String opisStanowiska;
    
    
    public void WczytajStanowiska() {
        
        try {

                Connection connection = connect();
                Statement stat = connection.createStatement();
                
                query = "SELECT * FROM STANOWISKA";
                
                ResultSet rs = stat.executeQuery(query);
                
                while(rs.next()) {
                    comboboxStanowisko.getItems().addAll(rs.getString("NAZWA_STANOWISKA"));   
                }
                
                rs.close();
                System.out.println("Zamkniete przy rs WczytajStanowiska()");
                stat.close();
                connection.commit();
                connection.close();

            } catch (SQLException e) {
                
                PokazAlert("Informacja","Błąd","Niestety wystąpił błąd z oprogramowaniem i bazą danych. Skontaktuj się z administratorem");
                
                System.err.println(" nie można wykonac tego zapytania WCZYTAJ STANOWISKA: " + e.getMessage());
            }        
    }
    
    
    boolean test = false;
    
    
    public boolean SprawdzCzyIstnieje(String login) {
        
        try {

                Connection connection = connect();
                Statement stat = connection.createStatement();
                
                query = "SELECT LOGIN FROM UZYTKOWNICY";
                
                ResultSet rs = stat.executeQuery(query);
                
                while(rs.next()) {
                    loginTest = rs.getString("LOGIN");
                    if(loginTest == login) {
                        
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Informacja");
                        alert.setHeaderText("Problem");
                        alert.setContentText("Istnieje już użytkownik o podanym loginie!");

                        alert.showAndWait();
                        
                        test = true;
                        
                        break;
                    }  
                }
                
                rs.close();
                System.out.println("Zamkniete przy rs WczytajStanowiska()");
                stat.close();
                connection.commit();
                connection.close();

            } catch (SQLException e) {
                
                PokazAlert("Informacja","Błąd","Niestety wystąpił błąd z bazą danych. Skontaktuj się z administratorem");
                
                System.err.println(" nie można wykonac tego zapytania WCZYTAJ STANOWISKA: " + e.getMessage());
            }   
        
        return test;
    }
    
    
}
