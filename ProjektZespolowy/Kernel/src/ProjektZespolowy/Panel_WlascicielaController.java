/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProjektZespolowy;

import static ProjektZespolowy.Panel_LogowaniaController.getZalogowany;
import static ProjektZespolowy.Polaczenie.connect;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
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
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author MaRkOs
 */

public class Panel_WlascicielaController implements Initializable {

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
    private Button buttonZarejestrujUzytkownika;
    
    @FXML
    private Button buttonGenerujRaport; 
    
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
                
                PokazAlert("Informacja","Błąd","Niestety wystąpił błąd z bazą danych. Nie udało się wczytać projektów");
                
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
                idProjektuR = idProjektu;
            }
            
            rs.close();
            System.out.println("Zamkniete przy rs 1");
            stat.close();
            connection.commit();
            connection.close();
            
            
            WczytajChatProjektu(0);
            
            comboboxWybierzGrupe.getItems().clear();
            
        } catch (SQLException e) {
            
            PokazAlert("Informacja","Błąd","Niestety wystąpił błąd z bazą danych. Nie udało się wybrać projektu ID: #1");
            
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
                
                PokazAlert("Informacja","Błąd","Niestety wystąpił błąd z bazą danych. Nie udało się wybrać projektu ID: #2");
                
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
                
                PokazAlert("Informacja","Błąd","Niestety wystąpił błąd z bazą danych. Nie udało się wybrać projektu ID: #3");
                
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
                
                PokazAlert("Informacja","Błąd","Niestety wystąpił błąd z bazą danych. Nie udało się wybrać projektu ID: #4");
                
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
                
                PokazAlert("Informacja","Błąd","Niestety wystąpił błąd z bazą danych. Nie udało się wybrać projektu ID: #5");
                
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
            
            PokazAlert("Informacja","Błąd","Niestety wystąpił błąd z bazą danych. Nie udało się wybrać grupy ID: #1");
            
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
                
                PokazAlert("Informacja","Błąd","Niestety wystąpił błąd z bazą danych. Nie udało się wybrać grupy ID: #2");
                
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
                
                PokazAlert("Informacja","Błąd","Niestety wystąpił błąd z bazą danych. Nie udało się wybrać grupy ID: #3");
                
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
                
                PokazAlert("Informacja","Błąd","Niestety wystąpił błąd z bazą danych. Nie udało się wybrać grupy ID: #4");
                
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
                
                PokazAlert("Informacja","Błąd","Niestety wystąpił błąd z bazą danych. Nie udało się wybrać grupy ID: #5");
                
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
                
                PokazAlert("Informacja","Błąd","Niestety wystąpił błąd z bazą danych. Nie udało się wybrać grupy ID: #6");
                
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
            
            PokazAlert("Informacja","Błąd","Niestety wystąpił błąd z bazą danych. Nie udało się wybrać sprintu ID: #1");
            
            System.err.println(" nie można wykonac tego zapytania WYBIERZ SPRINT #1: " + e.getMessage());
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
                
                PokazAlert("Informacja","Błąd","Niestety wystąpił błąd z bazą danych. Nie udało się wybrać sprintu ID: #2");
                
                System.err.println(" nie można wykonac tego zapytania WYBIERZ SPRINT #2: " + e.getMessage());
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
                
                PokazAlert("Informacja","Błąd","Niestety wystąpił błąd z bazą danych. Nie udało się wybrać sprintu ID: #3");
                
                System.err.println(" nie można wykonac tego zapytania WYBIERZ SPRINT #3: " + e.getMessage());
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
                
                PokazAlert("Informacja","Błąd","Niestety wystąpił błąd z bazą danych. Nie udało się wybrać sprintu ID: #4");
                
                System.err.println(" nie można wykonac tego zapytania WYBIERZ SPRINT #4: " + e.getMessage());
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
                
                PokazAlert("Informacja","Błąd","Niestety wystąpił błąd z bazą danych. Nie udało się wczytać chatu ID #1");
                
                System.err.println(" nie można wykonac tego zapytania: WCZYTAJ CHAT #1" + e.getMessage());
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
                
                PokazAlert("Informacja","Błąd","Niestety wystąpił błąd z bazą danych. Nie udało się wczytać chatu ID #2");
                
                System.err.println(" nie można wykonac tego zapytania: WCZYTAJ CHAT #2" + e.getMessage());
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
                
                PokazAlert("Informacja","Błąd","Niestety wystąpił błąd z bazą danych. Nie udało się wczytać chatu ID #3");
                
                System.err.println(" nie można wykonac tego zapytania: WCZYTAJ CHAT #3" + e.getMessage());
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
                
                PokazAlert("Informacja","Błąd","Niestety wystąpił błąd z bazą danych. Nie udało się wysłać wiadomości na chacie projektu");
                
                System.err.println(" nie można wykonac tego zapytania: CHAT PROJEKTU WYŚLIJ" + e.getMessage());
            }   
    }

    
    @FXML
    private void ActionButtonCzyscChatProjekt(ActionEvent event) {
        
        czyscProjektu = 1;
        WczytajChatProjektu(czyscProjektu);
    }

    @FXML
    private void ActionButtonZarejestrujUzytkownika(ActionEvent event) throws IOException {
        
        ((Node)(event.getSource())).getScene().getWindow().hide();
        
        Parent root = null;
        Stage stage = new Stage();
        
        root = FXMLLoader.load(getClass().getResource("Panel_Rejestracji.fxml"));
        
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

    
    int idProjektuR = 0; // pobrane i przypisane przy wybierz projekt combobox
    String nazwaProjektuR = "";
    int idFirmyZlecajacejR = 0;
    String dataStartR = "";
    String dataEndR = "";
    int kosztProjektuR = 0;
    String opisProjektuR = "";
    String statusProjektuR = "";
    String komentarzZlecajacegoR = "";
    int idKierownikaR = 0;    
    String nazwaFirmyZlecajacej = "";
    String imieReprezentant = "";
    String nazwiskoReprezentant = "";
    String dataUtworzeniaRaportu = "";
    
    int idZleceniobiorcyR = 0;
    String imieZleceniobiorcyR = "";
    String nazwiskoZleceniobiorcyR = "";
    String nazwaFirmyZleceniobiorcy = "";
    
    String imieKierownikaR = "";
    String nazwiskoKierownikaR = "";
    String nazwaFirmyKierownika = "";   
    
    int idLideraR = 0;
    String imieLideraR = "";
    String nazwiskoLideraR = "";
    String nazwaFirmyLidera = "";
    String nazwaGrupyLidera = "";   
    
    int liczbaCzlonkowGrup = 0;
    
    String elo = null;
    
    ObservableList<Lider> Liderzy = FXCollections.observableArrayList();
    
    
    @FXML
    private void ActionButtonGenerujRaport(ActionEvent event) {
        
      try {
        // pobieranie danych o przedstawicielu 
        try {
            
                dataUtworzeniaRaportu = String.valueOf(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE));

                Connection connection = connect();
                Statement stat = connection.createStatement();
                
                //query = "SELECT * FROM PROJEKTY P, UZYTKOWNICY U, FIRMY F WHERE P.ID_PROJEKTU =" + idProjektuR + "";
                query = "SELECT DISTINCT P.NAZWA_PROJEKTU, F.NAZWA_FIRMY, P.DATA_START, P.DATA_END, P.KOSZT_PROJEKTU, P.OPIS_PROJEKTU, P.STATUS_PROJEKTU, P.KOMENTARZ_ZLECAJACEGO, U.IMIE, U.NAZWISKO FROM PROJEKTY P, UZYTKOWNICY U, FIRMY F INNER JOIN PROJEKTY ON P.ID_PROJEKTU =" + idProjektuR + " INNER JOIN FIRMY ON F.ID_FIRMY = P.ID_FIRMY_ZLECAJACEJ INNER JOIN UZYTKOWNICY ON (SELECT U.ID_UZYTKOWNIKA WHERE U.ID_FIRMY = P.ID_FIRMY_ZLECAJACEJ) = U.ID_UZYTKOWNIKA";
                //query = "SELECT F.NAZWA_FIRMY, U.IMIE, U.NAZWISKO FROM PROJEKTY P, UZYTKOWNICY U, FIRMY F INNER JOIN FIRMY ON F.ID_FIRMY = P.ID_FIRMY_ZLECAJACEJ INNER JOIN UZYTKOWNICY ON P.ID_KIEROWNIKA = U.ID_UZYTKOWNIKA";
                
                ResultSet rs = stat.executeQuery(query);
                
                    nazwaProjektuR = rs.getString("NAZWA_PROJEKTU");
                    //idFirmyZlecajacejR = rs.getInt("ID_FIRMY_ZLECAJACEJ"); @@
                    nazwaFirmyZlecajacej = rs.getString("NAZWA_FIRMY");
                    dataStartR = rs.getString("DATA_START");
                    dataEndR = rs.getString("DATA_END");
                    kosztProjektuR = rs.getInt("KOSZT_PROJEKTU");
                    opisProjektuR = rs.getString("OPIS_PROJEKTU");
                    statusProjektuR = rs.getString("STATUS_PROJEKTU");
                    komentarzZlecajacegoR = rs.getString("KOMENTARZ_ZLECAJACEGO");
                    //idKierownikaR = rs.getInt("ID_KIEROWNIKA"); @@
                    imieReprezentant = rs.getString("IMIE");
                    nazwiskoReprezentant = rs.getString("NAZWISKO");

                    System.out.println("Firma zleceniodawcy: "+nazwaFirmyZlecajacej+" Imie: "+imieReprezentant+" Nazwisko: " +nazwiskoReprezentant);
                
                rs.close();
                stat.close();
                connection.commit();
                connection.close();

            } catch (SQLException e) {
                
                PokazAlert("Informacja","Błąd","Niestety wystąpił błąd z bazą danych. Nie udało się stworzyć raportu ID: #1");
                
                System.err.println(" nie można wykonac tego zapytania: GENERUJ RAPORT #1 " + e.getMessage());
            }
        
        // pobieranie danych o przedstawicielu zleceniobiorcy
        try {

                Connection connection = connect();
                Statement stat = connection.createStatement();
                
                query = "SELECT DISTINCT U.ID_UZYTKOWNIKA, U.IMIE, U.NAZWISKO, F.NAZWA_FIRMY FROM UZYTKOWNICY U, FIRMY F, STANOWISKA S INNER JOIN FIRMY ON F.ID_FIRMY = U.ID_FIRMY WHERE U.ID_STANOWISKA = 2";
 
                ResultSet rs = stat.executeQuery(query);
                
                    idZleceniobiorcyR = rs.getInt("ID_UZYTKOWNIKA");
                    imieZleceniobiorcyR = rs.getString("IMIE");
                    nazwiskoZleceniobiorcyR = rs.getString("NAZWISKO");
                    nazwaFirmyZleceniobiorcy = rs.getString("NAZWA_FIRMY");

                    System.out.println("Firma Zleceniobiorcy: "+nazwaFirmyZleceniobiorcy+" Imie: "+imieZleceniobiorcyR+" Nazwisko: " +nazwiskoZleceniobiorcyR);                    
                
                
                rs.close();
                stat.close();
                connection.commit();
                connection.close();

            } catch (SQLException e) {
                
                PokazAlert("Informacja","Błąd","Niestety wystąpił błąd z bazą danych. Nie udało się stworzyć raportu ID: #2");
                
                System.err.println(" nie można wykonac tego zapytania: GENERUJ RAPORT #2 " + e.getMessage());
            }        
        
        
        // pobieranie danych o kierowniku
        try {

                Connection connection = connect();
                Statement stat = connection.createStatement();
                
                query = "SELECT DISTINCT U.ID_UZYTKOWNIKA, U.IMIE, U.NAZWISKO, F.NAZWA_FIRMY FROM UZYTKOWNICY U, FIRMY F, STANOWISKA S INNER JOIN UZYTKOWNICY ON U.ID_PROJEKTU =" + idProjektuR + " INNER JOIN FIRMY ON F.ID_FIRMY = U.ID_FIRMY WHERE U.ID_STANOWISKA = 3";
 
                ResultSet rs = stat.executeQuery(query);
                
                    idKierownikaR = rs.getInt("ID_UZYTKOWNIKA");
                    imieKierownikaR = rs.getString("IMIE");
                    nazwiskoKierownikaR = rs.getString("NAZWISKO");
                    nazwaFirmyKierownika = rs.getString("NAZWA_FIRMY");

                    System.out.println("Firma Kierownika: "+nazwaFirmyKierownika+" Imie: "+imieKierownikaR+" Nazwisko: " +nazwiskoKierownikaR);                    
                
                
                rs.close();
                stat.close();
                connection.commit();
                connection.close();

            } catch (SQLException e) {
                
                PokazAlert("Informacja","Błąd","Niestety wystąpił błąd z bazą danych. Nie udało się stworzyć raportu ID: #3");
                
                System.err.println(" nie można wykonac tego zapytania: GENERUJ RAPORT #3 " + e.getMessage());
            }        
        
        // pobieranie danych o liderach
        try {

                Connection connection = connect();
                Statement stat = connection.createStatement();
                
                query = "SELECT DISTINCT U.ID_UZYTKOWNIKA, U.IMIE, U.NAZWISKO, G.NAZWA_GRUPY FROM UZYTKOWNICY U, GRUPY G INNER JOIN UZYTKOWNICY ON U.ID_PROJEKTU =" + idProjektuR + " INNER JOIN GRUPY ON G.ID_GRUPY = U.ID_GRUPY WHERE U.ID_STANOWISKA = 4";
 
                ResultSet rs = stat.executeQuery(query);
                
                while(rs.next()) {
                    idLideraR = rs.getInt("ID_UZYTKOWNIKA");
                    imieLideraR = rs.getString("IMIE");
                    nazwiskoLideraR = rs.getString("NAZWISKO");
                    nazwaGrupyLidera = rs.getString("NAZWA_GRUPY");
                    
                    Liderzy.add(new Lider(idLideraR, imieLideraR, nazwiskoLideraR, nazwaGrupyLidera)); 
                }
                
                nazwaFirmyLidera = nazwaFirmyKierownika;
               
                System.out.println("Firma Lidera: "+nazwaFirmyLidera+" Imie: "+imieLideraR+" Nazwisko: " +nazwiskoLideraR);                    
                
                rs.close();
                stat.close();
                connection.commit();
                connection.close();

            } catch (SQLException e) {
                
                PokazAlert("Informacja","Błąd","Niestety wystąpił błąd z bazą danych. Nie udało się stworzyć raportu ID: #4");
                
                System.err.println(" nie można wykonac tego zapytania: GENERUJ RAPORT #4 " + e.getMessage());
            }
        
        // pobierani liczby czlonkow grup pracujacych nad projektem
        try {

                Connection connection = connect();
                Statement stat = connection.createStatement();
                
                query = "SELECT COUNT(ID_UZYTKOWNIKA) FROM UZYTKOWNICY WHERE ID_PROJEKTU = "+idProjektuR;
                
                ResultSet rs = stat.executeQuery(query);
                
                while(rs.isBeforeFirst()){
                    liczbaCzlonkowGrup = rs.getInt(1) - 1;
                    rs.next();
                }
                
                rs.close();
                stat.close();
                connection.commit();
                connection.close();

            } catch (SQLException e) {
                PokazAlert("Informacja","Błąd","Niestety wystąpił błąd z bazą danych. Nie udało się stworzyć raportu ID: #5");
                
                System.err.println(" nie można wykonac tego zapytania: GENERUJ RAPORT #5 " + e.getMessage());
            }
        
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new java.io.File(System.getProperty("user.home"), "Desktop"));
            chooser.setDialogTitle("Wybierz lokalizacjÄ™");
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setApproveButtonText("Zapisz");

            chooser.setAcceptAllFileFilterUsed(false);

            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                JOptionPane.showMessageDialog(null, "Zapisano w: " + chooser.getSelectedFile());
                elo =""+chooser.getSelectedFile();
                
                createPdf();
                        
            } else {
               PokazAlert("Informacja","Błąd","Nie wybrano lokalizacji!");
            }            
        
        
      } catch (Exception e) {
                PokazAlert("Informacja","Błąd","Niestety wystąpił błąd z bazą danych. Nie udało się stworzyć raportu ID: #6");
                
                System.err.println(" nie można wykonac GENERUJ RAPORT #6 " + e.getMessage());
            } 
    }
    
    
    public void createPdf() {
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        
        try {

            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(elo+"/raport.pdf"));

            document.open();

            BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
            Font helvetica16=new Font(helvetica,12);
            Font helvetica16x=new Font(helvetica,16);
            
            Paragraph dataUtworzenia = new Paragraph(new Chunk(""+dataUtworzeniaRaportu+"\n\n", helvetica16));
            Paragraph raport = new Paragraph(new Chunk("RAPORT \n\n", helvetica16x));
            Paragraph nazwaProjektu = new Paragraph(new Chunk("Projekt: " + nazwaProjektuR + "\n\n", helvetica16x));
            Paragraph nazwaFirmyZl = new Paragraph(new Chunk("Zleceniodawca: " + nazwaFirmyZlecajacej, helvetica16));
            Paragraph reprezentant = new Paragraph(new Chunk("Przedstawiciel zleceniodawcy: : " +imieReprezentant + " " + nazwiskoReprezentant + "\n\n", helvetica16));
            Paragraph nazwaFirmy = new Paragraph(new Chunk("Zleceniobiorca: " +nazwaFirmyKierownika, helvetica16));
            Paragraph zleceniobiorca = new Paragraph(new Chunk("Przedstawiciel zleceniobiorcy: " + imieZleceniobiorcyR + " " + nazwiskoZleceniobiorcyR + "\n\n", helvetica16));
            Paragraph kierownik = new Paragraph(new Chunk("Kierownik Projektu: " + imieKierownikaR + " " + nazwiskoKierownikaR, helvetica16));
            Paragraph dataStart = new Paragraph(new Chunk("Termin rozpoczęcia prac: " +dataStartR, helvetica16));
            Paragraph dataEnd = new Paragraph(new Chunk("Termin ukończenia prac: " +dataEndR, helvetica16));
            Paragraph koszt = new Paragraph(new Chunk("Koszt projektu: " +kosztProjektuR, helvetica16));
            Paragraph status = new Paragraph(new Chunk("Status projektu: " +statusProjektuR + "\n\n", helvetica16));
            Paragraph opis = new Paragraph(new Chunk("Opis projektu: " +opisProjektuR + "\n\n", helvetica16));
            Paragraph liczbaGr = new Paragraph(new Chunk("Liczba grup: " + liczbaGrup + "       Liczba członkow: " + liczbaCzlonkowGrup + "        Liczba zadan: " + liczbaWszystkichZadan, helvetica16));
            Paragraph LiczbaUZadan = new Paragraph(new Chunk("Liczba ukończonych zadań: " + liczbaUkonczonychZadan + "\n\n", helvetica16));
            
            Paragraph prowadzacy = new Paragraph(new Chunk("Prowadzący grup:"));
            
            dataUtworzenia.setAlignment(Element.ALIGN_RIGHT);
            raport.setAlignment(Element.ALIGN_CENTER);
            nazwaProjektu.setAlignment(Element.ALIGN_CENTER);
            nazwaFirmyZl.setAlignment(Element.ALIGN_LEFT);
            reprezentant.setAlignment(Element.ALIGN_LEFT);
            nazwaFirmy.setAlignment(Element.ALIGN_LEFT);
            zleceniobiorca.setAlignment(Element.ALIGN_LEFT);
            kierownik.setAlignment(Element.ALIGN_LEFT);
            dataStart.setAlignment(Element.ALIGN_LEFT);
            dataEnd.setAlignment(Element.ALIGN_LEFT);
            koszt.setAlignment(Element.ALIGN_LEFT);
            status.setAlignment(Element.ALIGN_LEFT);
            opis.setAlignment(Element.ALIGN_LEFT);
            liczbaGr.setAlignment(Element.ALIGN_LEFT);
            LiczbaUZadan.setAlignment(Element.ALIGN_LEFT);
            prowadzacy.setAlignment(Element.ALIGN_LEFT);
            
            document.add(dataUtworzenia);
            document.add(raport);
            document.add(nazwaProjektu);
            document.add(nazwaFirmyZl);
            document.add(reprezentant);
            document.add(nazwaFirmy);
            document.add(zleceniobiorca);
            document.add(kierownik);
            document.add(dataStart);
            document.add(dataEnd);

            document.add(koszt);
            document.add(status);
            document.add(opis);
            document.add(liczbaGr);
            document.add(LiczbaUZadan);
            document.add(prowadzacy);            
            
            int j = 1;
            
            while(j<Liderzy.size()) {
                Paragraph lider = new Paragraph(new Chunk("Lider: " + Liderzy.get(j).imieLideraR + " " + Liderzy.get(j).nazwiskoLideraR + "     Nazwa grupy: " + Liderzy.get(j).nazwaGrupyLidera, helvetica16));
                lider.setAlignment(Element.ALIGN_LEFT);
                document.add(lider);
                j++;
            }

        } catch (Exception e) {
            
            e.printStackTrace();
           
            PokazAlert("Informacja","Błąd","Niestety wystąpił błąd. Nie udało się utworzyć raportu ID: #7.");
                
             System.err.println(" nie można wykonac GENERUJ RAPORT #7 " + e.getMessage());
        }
        document.close();
    }
      
    
}
