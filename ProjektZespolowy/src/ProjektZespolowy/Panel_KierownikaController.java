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
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableView;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
    private ProgressBar progresbarGrupa;
    
    @FXML
    private Label labelLiczbaCzlonkow;
    
    @FXML
    private Label labelLiczbaZadanGrupaSprint;
    
    @FXML
    private Label labelLiczbaUZadanGrupaSprint;
    
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
    private Label labelLiczbaUZadanGrupa;
    
    @FXML
    private Label labelLiczbaZadanGrupa;
    
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
    private TableColumn<Zadania, String> kolumnaNazwaZadania;
    
    @FXML
    private TableColumn<Zadania, String> kolumnaStatus;
    
    @FXML
    private TableColumn<Zadania, String> kolumnaPriorytet;
    
    @FXML
    private TableColumn<Zadania, String> kolumnaDataUtworzenia;
    
    @FXML
    private TableColumn<Zadania, String> kolumnaDataAktualizacji;
    
    @FXML
    private TableColumn<Zadania, String> kolumnaOpis;
    
    @FXML
    private TableColumn<Zadania, String> kolumnaKomentarz;
    
    @FXML
    private AnchorPane paneChatProjekt;
    
    @FXML
    private TextArea textareaChatProjekt;
    
    @FXML
    private TextField textfieldChatProjekt; 
    
    @FXML
    private Label labelChatTytulProjekt;
    
    String query;
    
    int idProjektu = 0;
    int idGrupy = 0;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        WczytajProjekty(); 
        //WczytajChatProjektu(0);
        
         kolumnaNazwaZadania.setText("NAZWA_ZADANIA");
         
         kolumnaStatus.setText("STATUS"); 

         kolumnaPriorytet.setText("PRIORYTET"); 

         kolumnaDataUtworzenia.setText("DATA_UTWORZENIA"); 

         kolumnaDataAktualizacji.setText("DATA_AKTUALIZACJI"); 

         kolumnaOpis.setText("OPIS");
         
         kolumnaKomentarz.setText("KOMENTARZ");
        
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
                System.err.println(" nie można wykonac tego zapytania WCZYTAJ PROJEKTY: " + e.getMessage());
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

    
    String nazwaProjektu ="";
    int liczbaGrup = 0;
    double liczbaWszystkichZadan = 0;
    double liczbaUkonczonychZadan = 0;
    double progresWartoscProjektu = 0;
    
    
    @FXML
    private void ActionComboBoxWybierzProjekt(ActionEvent event) {
        
        nazwaProjektu = (String) comboboxWybierzProjekt.getSelectionModel().getSelectedItem();
        
        comboboxWybierzGrupe.getItems().clear();
        comboboxWybierzSprint.getItems().clear();
        
        try {

            Connection connection = connect();
            Statement stat = connection.createStatement();
            
            query = "SELECT ID_PROJEKTU FROM PROJEKTY WHERE NAZWA_PROJEKTU = '" + nazwaProjektu +"'";
            
            ResultSet rs = stat.executeQuery(query);
            
            while(rs.next()){
                idProjektu = rs.getInt("ID_PROJEKTU");
                idProjektuChat = idProjektu;
            }
            
            rs.close();
            System.out.println("Zamkniete przy rs 1");
            stat.close();
            connection.commit();
            connection.close();
            
            
            WczytajChatProjektu(0);
            
            comboboxWybierzGrupe.getItems().clear();
            
        } catch (SQLException e) {
            System.err.println(" nie można wykonac tego zapytania WYBIERZ PROJEKT 1: " + e.getMessage());
        }
        
        System.out.println("ID PROJEKTU PRZY BLEDZIE: "+idProjektu);
        
         try {

                Connection connectiond = connect();
                Statement stat = connectiond.createStatement();
                
                query = "SELECT ID_GRUPY, NAZWA_GRUPY FROM GRUPY WHERE ID_PROJEKTU = "+idProjektu;
                
                ResultSet rs = stat.executeQuery(query);
                
                if(rs.isBeforeFirst()){
                    idGrupy = rs.getInt("ID_GRUPY");
                }
                
                if(rs.next()){
                    do {
                            comboboxWybierzGrupe.getItems().addAll(rs.getString("NAZWA_GRUPY"));
                    } while(rs.next());
                }
                
//                while(rs.next()) {
//                    comboboxWybierzGrupe.getItems().addAll(rs.getString("NAZWA_GRUPY"));   
//                }
                
                rs.close();
                System.out.println("Zamkniete przy rs 2");
                stat.close();
                connectiond.commit();
                connectiond.close();
                
            } catch (SQLException e) {
                System.err.println(" nie można wykonac tego zapytania WYBIERZ PROJEKT 2: " + e.getMessage());
            }
         
            try {
                
                Connection connection = connect();
                Statement stat = connection.createStatement();                
                
                query = "SELECT COUNT(ID_GRUPY) FROM GRUPY WHERE ID_PROJEKTU = "+idProjektu;
                
               ResultSet rs = stat.executeQuery(query);
                
               while(rs.isBeforeFirst()){
                    liczbaGrup = rs.getInt(1);
                    rs.next();
               } 
                labelLiczbaGrup.setText(""+liczbaGrup);
                
                rs.close();
                System.out.println("Zamkniete przy rs 3");
                stat.close();
                connection.commit();
                connection.close();
                
            } catch (SQLException e) {
                System.err.println(" nie można wykonac tego zapytania WYBIERZ PROJEKT 3: " + e.getMessage());
            }
            
            try {
                
                Connection connection = connect();
                Statement stat = connection.createStatement();                
                
                query = "SELECT COUNT(ID_ZADANIA) FROM ZADANIA WHERE ID_PROJEKTU = "+idProjektu;
                
               ResultSet rs = stat.executeQuery(query);
                
               while(rs.isBeforeFirst()){
                liczbaWszystkichZadan = rs.getInt(1);
                rs.next();                
               }
               
                labelLiczbaZadanProjekt.setText(""+liczbaWszystkichZadan);
                
                System.out.println(" WSZYSTKIE: "+liczbaWszystkichZadan);
                
                rs.close();
                System.out.println("Zamkniete przy rs 4");
                stat.close();
                connection.commit();
                connection.close();
                
            } catch (SQLException e) {
                System.err.println(" nie można wykonac tego zapytania WYBIERZ PROJEKT 4: " + e.getMessage());
            }
            
            try {
                
                Connection connection = connect();
                Statement stat = connection.createStatement();                
                
                query = "SELECT COUNT(ID_ZADANIA) FROM ZADANIA WHERE ID_PROJEKTU ="+idProjektu+" AND STATUS = 4";
                
                ResultSet rs = stat.executeQuery(query);
                
                while(rs.isBeforeFirst()){
                liczbaUkonczonychZadan = rs.getInt(1);
                rs.next();
                }
                
                System.out.println(" UKONCZONE: "+liczbaUkonczonychZadan);                
                
                labelLiczbaZadanUProjekt.setText(""+liczbaUkonczonychZadan);

                if(liczbaWszystkichZadan!=0) {
                    progresWartoscProjektu = liczbaUkonczonychZadan/liczbaWszystkichZadan;
                }
                
                System.out.println("Wartosc Projektu: "+progresWartoscProjektu);
                
                progresbarProjekt.setProgress(progresWartoscProjektu);
                
                rs.close();
                System.out.println("Zamkniete przy rs 5");
                stat.close();
                connection.commit();
                connection.close();
                
            } catch (SQLException e) {
                System.err.println(" nie można wykonac tego zapytania WYBIERZ PROJEKT 5: " + e.getMessage());
            }
    }

    
    String nazwaGrupy = "";
    int liczbaCzlonkow = 0;
    double liczbaZadanGrupa = 0;
    double liczbaZadanUGrupa = 0;
    String liderGrupy = "";
    double progresWartoscGrupy = 0;
    
    
    @FXML
    private void ActionComboBoxWybierzGrupe(ActionEvent event) {
        
        nazwaGrupy = (String) comboboxWybierzGrupe.getSelectionModel().getSelectedItem();
        
        try {

            Connection connection = connect();
            Statement stat = connection.createStatement();
            
            query = "SELECT ID_GRUPY FROM GRUPY WHERE NAZWA_GRUPY = '" + nazwaGrupy +"'";
            
            ResultSet rs = stat.executeQuery(query);
            
            while(rs.isBeforeFirst()){
            idGrupy = rs.getInt("ID_GRUPY");
            rs.next();
            }
            
            rs.close();
            System.out.println("Zamkniete przy rs 1 WybierzGrupe");
            stat.close();
            connection.commit();
            connection.close();
            
            comboboxWybierzSprint.getItems().clear();
            
        } catch (SQLException e) {
            System.err.println(" nie można wykonac tego zapytania WYBIERZ GRUPE 1: " + e.getMessage());
        }        
        
        
        try {

                Connection connection = connect();
                Statement stat = connection.createStatement();
                
                query = "SELECT NAZWA_SPRINTU FROM SPRINTY WHERE ID_PROJEKTU = "+idProjektu;
                
                ResultSet rs = stat.executeQuery(query);
                
                while(rs.next()) {
                    comboboxWybierzSprint.getItems().addAll(rs.getString("NAZWA_SPRINTU"));
                }
                
                rs.close();
                System.out.println("Zamkniete przy rs 2 WybierzGrupe");
                stat.close();
                connection.commit();
                connection.close();

            } catch (SQLException e) {
                System.err.println(" nie można wykonac tego zapytania WYBIERZ GRUPE 2: " + e.getMessage());
            }
        
        try {

                Connection connection = connect();
                Statement stat = connection.createStatement();
                
                query = "SELECT COUNT(ID_UZYTKOWNIKA) FROM UZYTKOWNICY WHERE ID_GRUPY = "+idGrupy;
                
                ResultSet rs = stat.executeQuery(query);
                
                while(rs.isBeforeFirst()){
                    liczbaCzlonkow = rs.getInt(1);
                    rs.next();
                }
                
                labelLiczbaCzlonkow.setText(""+liczbaCzlonkow);
                
                rs.close();
                System.out.println("Zamkniete przy rs 3 WybierzGrupe");
                stat.close();
                connection.commit();
                connection.close();

            } catch (SQLException e) {
                System.err.println(" nie można wykonac tego zapytania WYBIERZ GRUPE 3: " + e.getMessage());
            }        
        
        try {

                Connection connection = connect();
                Statement stat = connection.createStatement();
                
                query = "SELECT COUNT(ID_ZADANIA) FROM ZADANIA WHERE ID_GRUPY = "+idGrupy+" AND ID_PROJEKTU = "+idProjektu;
                
                ResultSet rs = stat.executeQuery(query);
                
                while(rs.isBeforeFirst()){
                liczbaZadanGrupa = rs.getInt(1);
                rs.next();
                }
                
                labelLiczbaZadanGrupa.setText(""+liczbaZadanGrupa);
                
                System.out.println("Zadania Wszystkie gupy: "+liczbaZadanGrupa);
                
                rs.close();
                System.out.println("Zamkniete przy rs 4 WybierzGrupe");
                stat.close();
                connection.commit();
                connection.close();

            } catch (SQLException e) {
                System.err.println(" nie można wykonac tego zapytania WYBIERZ GRUPE 4: " + e.getMessage());
            }
                
        try {

                Connection connection = connect();
                Statement stat = connection.createStatement();                
                
                query = "SELECT COUNT(ID_ZADANIA) FROM ZADANIA WHERE ID_GRUPY = "+idGrupy+" AND ID_PROJEKTU = '"+idProjektu+"' AND STATUS = 4";
                
                ResultSet rs = stat.executeQuery(query);
                
                while(rs.isBeforeFirst()){
                liczbaZadanUGrupa = rs.getInt(1);
                rs.next();
                }
                
                System.out.println("Zadania Ukończone gupy: "+liczbaZadanUGrupa);
                
                labelLiczbaUZadanGrupa.setText(""+liczbaZadanUGrupa);
                
                if(liczbaZadanGrupa!=0) {
                    progresWartoscGrupy = liczbaZadanUGrupa/liczbaZadanGrupa;
                }
                
                System.out.println("Wartosc Grupa: "+progresWartoscGrupy);
                
                progresbarGrupa.setProgress(progresWartoscGrupy);
                
                rs.close();
                System.out.println("Zamkniete przy rs 5 WybierzGrupe");
                stat.close();
                connection.commit();
                connection.close();

            } catch (SQLException e) {
                System.err.println(" nie można wykonac tego zapytania WYBIERZ GRUPE 5: " + e.getMessage());
            }
                
        try {

                Connection connection = connect();
                Statement stat = connection.createStatement();                
                
                query = "SELECT IMIE, NAZWISKO FROM UZYTKOWNICY WHERE ID_GRUPY = "+idGrupy+" AND ID_STANOWISKA = 4";
                
                ResultSet rs = stat.executeQuery(query);
                
                while(rs.isBeforeFirst()){
                liderGrupy = rs.getString("IMIE")+" "+rs.getString("NAZWISKO");
                rs.next();
                }
                
                labelLiderGrupy.setText(liderGrupy);
                
                rs.close();
                System.out.println("Zamkniete przy rs 6 WybierzGrupe");
                stat.close();
                connection.commit();
                connection.close();

            } catch (SQLException e) {
                System.err.println(" nie można wykonac tego zapytania WYBIERZ GRUPE 6: " + e.getMessage());
            }
    }

    
    double progresWartoscSprintu = 0;
    double liczbaZadanSprint = 0;
    double liczbaZadanUSprint = 0;
    String nazwaSprintu = "";
    int idSprintu = 0;

    int idZadania = 0;
    String nazwaZadania = "";
    int status = 0;
    String statusNazwa = "";
    String priorytet = "";
    int idZglaszajacego = 0;
    int idPrzypisanego = 0;
    String dataUtworzenia = "";
    String dataAktualizacji = "";
    String opis = "";
    String komentarz = "";
    
    
    @FXML
    private void ActionComboBoxWybierzSprint(ActionEvent event) {
        
        tableviewZadania.getItems().clear();
        tableviewZadania.getColumns().clear();
        
        nazwaSprintu = (String) comboboxWybierzSprint.getSelectionModel().getSelectedItem();
        
        try {

            Connection connection = connect();
            Statement stat = connection.createStatement();
            
            query = "SELECT ID_SPRINTU FROM SPRINTY WHERE NAZWA_SPRINTU = '"+ nazwaSprintu +"'";
            
            ResultSet rs = stat.executeQuery(query);
            
            while(rs.isBeforeFirst()){
            idSprintu = rs.getInt("ID_SPRINTU");
            rs.next();
            }
            
            rs.close();
            System.out.println("Zamkniete przy rs 1 WYBIERZ SPRINT");
            stat.close();
            connection.commit();
            connection.close();
            
        } catch (SQLException e) {
            System.err.println(" nie można wykonac tego zapytania WYBIERZ SPRINT 1: " + e.getMessage());
        }

        try {

                Connection connection = connect();
                Statement stat = connection.createStatement();      // ID_GRUPY = "+idGrupy+" AND ID_PROJEKTU = "+idProjektu+" AND
                
                query = "SELECT COUNT(ID_ZADANIA) FROM ZADANIA WHERE ID_SPRINTU ="+idSprintu;
                
                ResultSet rs = stat.executeQuery(query);
                
                while(rs.isBeforeFirst()){
                liczbaZadanSprint = rs.getInt(1);
                rs.next();
                }
                
                labelLiczbaZadanGrupaSprint.setText(""+liczbaZadanSprint);
                
                System.out.println("Zadania Wszystkie sprintu: "+liczbaZadanSprint);
                
                // ID_GRUPY = "+idGrupy+" AND ID_PROJEKTU = "+idProjektu+" AND 
                
                rs.close();
                System.out.println("Zamkniete przy rs 2 WYBIERZ SPRINT");
                stat.close();
                connection.commit();
                connection.close();        
            } catch (SQLException e) {
                System.err.println(" nie można wykonac tego zapytania WYBIERZ SPRINT 2: " + e.getMessage());
            }
                
        try {

                Connection connection = connect();
                Statement stat = connection.createStatement();                
                
                query = "SELECT COUNT(ID_ZADANIA) FROM ZADANIA WHERE ID_SPRINTU ="+idSprintu+" AND STATUS = 4";
                
                ResultSet rs = stat.executeQuery(query);
                
                while(rs.isBeforeFirst()){
                liczbaZadanUSprint = rs.getInt(1);
                rs.next();
                }
                
                System.out.println("Zadania Ukończone sprintu: "+liczbaZadanUSprint);
                
                if(liczbaZadanSprint!=0) {
                    progresWartoscSprintu = liczbaZadanUSprint/liczbaZadanSprint;
                }
                
                System.out.println("Wartosc Sprintu: "+progresWartoscSprintu);
                
                progresbarSprint.setProgress(progresWartoscSprintu);
                
                labelLiczbaUZadanGrupaSprint.setText(""+liczbaZadanUSprint);

                kolumnaNazwaZadania.setCellValueFactory(new PropertyValueFactory<>("nazwaZadania"));
                kolumnaStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
                kolumnaPriorytet.setCellValueFactory(new PropertyValueFactory<>("priorytet"));
                kolumnaDataUtworzenia.setCellValueFactory(new PropertyValueFactory<>("dataUtworzenia"));
                kolumnaDataAktualizacji.setCellValueFactory(new PropertyValueFactory<>("dataAktualizacji"));
                kolumnaOpis.setCellValueFactory(new PropertyValueFactory<>("opis"));
                kolumnaKomentarz.setCellValueFactory(new PropertyValueFactory<>("komentarz"));
                
                rs.close();
                System.out.println("Zamkniete przy rs 3 WYBIERZ SPRINT");
                stat.close();
                connection.commit();
                connection.close();        
            } catch (SQLException e) {
                System.err.println(" nie można wykonac tego zapytania WYBIERZ SPRINT 3: " + e.getMessage());
            }
                
        try {

                Connection connection = connect();
                Statement stat = connection.createStatement();                
                
                query = "SELECT * FROM ZADANIA WHERE ID_SPRINTU = "+idSprintu;
                
                ResultSet rs = stat.executeQuery(query);
                
                ObservableList<Zadania> daneTabela = FXCollections.observableArrayList();
                
                while(rs.next()) {
                    tableviewZadania.getColumns().clear();
                       
//                    idZglaszajacego = rs.getInt("ID_ZGLASZAJACEGO");
//                    System.out.println("Id zglaszajacego: "+idZglaszajacego);
//                    
//                    idPrzypisanego = rs.getInt("ID_PRZYPISANEGO");
//                    System.out.println("Id przypisanego: "+idPrzypisanego);
//                    
//                    idProjektu = rs.getInt("ID_PROJEKTU");
//                    System.out.println("Id projektu: "+idProjektu);
                    
                    nazwaZadania = rs.getString("NAZWA_ZADANIA");
                    System.out.println("Nazwa zadania: "+nazwaZadania);
                    
                    status = rs.getInt("STATUS");        
                    System.out.println("status: "+status);
                    
                    switch(status) {
                        case 1:
                            statusNazwa = "oczekuje";
                            break;
                        case 2:
                            statusNazwa = "w trakcie";
                            break;
                        case 3:
                            statusNazwa = "testy";
                            break;
                        case 4:
                            statusNazwa = "gotowe";
                            break;                            
                    }
                    
                    priorytet = rs.getString("PRIORYTET");
                    System.out.println("priorytet: "+priorytet);
                    
                    dataUtworzenia = rs.getString("DATA_UTWORZENIA");
                    System.out.println("data utw: "+dataUtworzenia);
                    
                    dataAktualizacji = rs.getString("DATA_AKTUALIZACJI");
                    System.out.println("data akt: "+dataAktualizacji);
                    
                    opis = rs.getString("OPIS");
                    System.out.println("opis: "+opis);
                    
                    komentarz = rs.getString("KOMENTARZ");
                    System.out.println("komentarz: "+komentarz);
                    
                    daneTabela.add(new Zadania(nazwaZadania, statusNazwa, priorytet, dataUtworzenia, dataAktualizacji, opis, komentarz));
                    //daneTabela.add(new Zadania(rs.getString("NAZWA_ZADANIA"), rs.getInt("STATUS"), rs.getString("PRIORYTET"), rs.getString("DATA_UTWORZENIA"), rs.getString("DATA_AKTUALIZACJI"), rs.getString("OPIS"), rs.getString("KOMENTARZ")));                    
//                   System.out.println("z tabeli: "+tableviewZadania.getItems().get(2));
                }
                
                tableviewZadania.setItems(daneTabela);
                tableviewZadania.getColumns().addAll(kolumnaNazwaZadania, kolumnaStatus, kolumnaPriorytet, kolumnaDataUtworzenia, kolumnaDataAktualizacji, kolumnaOpis, kolumnaKomentarz);//if(daneTabela!=null) {
                
                rs.close();
                System.out.println("Zamkniete przy rs 4 WYBIERZ SPRINT");
                stat.close();
                connection.commit();
                connection.close();        
            } catch (SQLException e) {
                System.err.println(" nie można wykonac tego zapytania WYBIERZ SPRINT 4: " + e.getMessage());
            }
        
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
    
    
    String loginChat = "";
    String login = "";
    String tresc = "";
    String dataWyslania = "";
    int czyscProjektu = 0;
    int idProjektuChat = 0;
    
    
public void WczytajChatProjektu(int czyscProjektu) {
        
        textareaChatProjekt.clear();
    
        try {

                Connection connection = connect();
                Statement stat = connection.createStatement();
                
                query = "SELECT LOGIN FROM UZYTKOWNICY WHERE ID_UZYTKOWNIKA ="+getZalogowany();
                
                ResultSet rs = stat.executeQuery(query);
                
                while(rs.isBeforeFirst()){
                    //idProjektuChat = rs.getInt("ID_PROJEKTU");
                    loginChat = rs.getString("LOGIN");
                    rs.next();
                }
                
                System.out.println("ZALOGOWANY LOGIN: "+loginChat);
                
                rs.close();
                System.out.println("Zamkniete przy rs 1 WCZYTAJ CHAT");
                stat.close();
                connection.commit();
                connection.close();
            } catch (SQLException e) {
                System.err.println(" nie można wykonac tego zapytania: WCZYTAJ CHAT 1" + e.getMessage());
            }                
                
        try {

                Connection connection = connect();
                Statement stat = connection.createStatement();                
                
                query = "SELECT NAZWA_PROJEKTU FROM PROJEKTY WHERE ID_PROJEKTU ="+idProjektuChat;
                
                ResultSet rs = stat.executeQuery(query);
                
                while(rs.isBeforeFirst()){
                nazwaProjektu = rs.getString("NAZWA_PROJEKTU");
                rs.next();
                }
                
                labelChatNazwaProjekt.setText(nazwaProjektu);
                
                rs.close();
                System.out.println("Zamkniete przy rs 2 WCZYTAJ CHAT");
                stat.close();
                connection.commit();
                connection.close();
            } catch (SQLException e) {
                System.err.println(" nie można wykonac tego zapytania: WCZYTAJ CHAT 2" + e.getMessage());
            }        
        
        try {

                Connection connection = connect();
                Statement stat = connection.createStatement();
                
                switch(czyscProjektu) {
                    case 0:
                        query = "SELECT * FROM CHAT WHERE ID_PROJEKTU ="+idProjektuChat+" AND ID_GRUPY = 0";
                        czyscProjektu = 0;
                        break;
                    case 1:
                        String dataDzisiejsza = String.valueOf(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMM hh:mm")));
                        query = "SELECT * FROM CHAT WHERE ID_PROJEKTU ="+idProjektuChat+" AND ID_GRUPY = 0 AND DATA_WYSLANIA='"+dataDzisiejsza+"'";
                        czyscProjektu = 1;
                        break;
                }
                
                ResultSet rs = stat.executeQuery(query);
                
                while(rs.next()) {
//                    idWiadomosci = rs.getInt("ID_WIADOMOSCI");
                    tresc = rs.getString("TRESC");
                    dataWyslania = rs.getString("DATA_WYSLANIA");
                    login = rs.getString("LOGIN");
                    
                    textareaChatProjekt.setWrapText(true);
                    textareaChatProjekt.appendText(dataWyslania+" | "+login+" -> "+tresc+"\n");
                    System.out.println(dataWyslania+" "+login+" "+tresc+"\n");
                }
                
                rs.close();
                System.out.println("Zamkniete przy rs 3 WCZYTAJ CHAT");
                stat.close();
                connection.commit();
                connection.close();

            } catch (SQLException e) {
                System.err.println(" nie można wykonac tego zapytania: WCZYTAJ CHAT 3" + e.getMessage());
            }        
    }     

    
    @FXML
    private void ActionButtonWyslijProjekt(ActionEvent event) {
        
        try {

                Connection connection = connect();
                Statement stat = connection.createStatement();
                
                tresc = textfieldChatProjekt.getText();
                dataWyslania = String.valueOf(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMM hh:mm")));

                query = "INSERT INTO CHAT(LOGIN, TRESC, DATA_WYSLANIA, ID_PROJEKTU, ID_GRUPY) VALUES"
                        + " ('" + loginChat + "','" + tresc + "','" + dataWyslania + "','" + idProjektuChat + "', 0)";
                
                stat.executeUpdate(query);
                
                textfieldChatProjekt.clear();
                
                stat.close();
                connection.commit();
                connection.close();
                
                switch(czyscProjektu) {
                    case 0:
                        WczytajChatProjektu(czyscProjektu);
                        break;
                    case 1:
                        WczytajChatProjektu(czyscProjektu);
                        break;
                }

            } catch (SQLException e) {
                System.err.println(" nie można wykonac tego zapytania: CHAT PROJEKTU WYŚLIJ" + e.getMessage());
            }   
    }

    
    @FXML
    private void ActionButtonCzyscChatProjekt(ActionEvent event) {
        
        czyscProjektu = 1;
        WczytajChatProjektu(czyscProjektu);
    }
    
}
