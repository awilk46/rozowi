/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProjektZespolowy;

import static ProjektZespolowy.Panel_Lidera_GrupyController.getWybraneZadanie;
import static ProjektZespolowy.Panel_Lidera_GrupyController.wybraneZadanie;
import static ProjektZespolowy.Panel_LogowaniaController.getZalogowany;
import static ProjektZespolowy.Polaczenie.connect;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.logging.Level;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ProgressBar;


/**
 * FXML Controller class
 *
 * @author MaRkOs
 * 
 * 
 * Dana klasa jest odzwierciedleniem możliwości
 * nadanych zwykłemu członkowi grupy pracowników.
 * 
 * 
 * 
 */

public class Panel_Cz_GrupyController implements Initializable{

@FXML
private AnchorPane paneAll;

@FXML
private AnchorPane paneLeft;

@FXML
private AnchorPane paneMiddle;

@FXML
private AnchorPane paneRight;

@FXML
private AnchorPane paneToDo;

@FXML
private AnchorPane paneDuring;

@FXML
private AnchorPane paneTesting;

@FXML
private AnchorPane paneReady;

@FXML
private AnchorPane paneChatGrupa;

@FXML 
private ScrollPane ScrollPaneRight;

@FXML 
private ScrollPane scrollToDo;

@FXML 
private ScrollPane scrollDuring;

@FXML 
private ScrollPane scrollTesting;

@FXML 
private ScrollPane scrollReady;

@FXML
private Pane paneZadanie;

@FXML
private Pane paneTitle;

@FXML
private ComboBox comboboxWybierzProjekt;

@FXML
private ComboBox comboboxWybierzSprint;

@FXML
private Button buttonWyczyscZadanie;

@FXML
private Button buttonWyjdz;

@FXML
private Button buttonWyslijGrupa;

@FXML
private Button buttonCzyscChatGrupa;

@FXML
private Label labelToDo;

@FXML
private Label labelDuring;

@FXML
private Label labelTesting;

@FXML
private Label labelReady;

@FXML
private Label labelDate2;

@FXML
private Label labelTitle2;

@FXML
private Label labelDate1;

@FXML
private Label labelTitle1;

@FXML
private Label labelInfo;

@FXML
private Label labelPriorytet;

@FXML
private Label labelOsobaZglaszajaca;

@FXML
private Label labelOsobaPrzypisana;

@FXML
private Label labelUtworzono;

@FXML
private Label labelZaaktualizowano;

@FXML
private Label labelOpis;

@FXML
private Label labelKomentarz;

@FXML
private Label labelChatNazwaGrupy;

@FXML
private TextField textfieldStatus;

@FXML
private TextField textfieldPriorytet;

@FXML
private TextField textfieldOsobaZglaszajaca;

@FXML
private TextField textfieldOsobaPrzypisana;

@FXML
private TextField textfieldUtworzono;

@FXML
private TextField textfieldZaaktualizowano;

@FXML
private TextArea textareaOpis;

@FXML
private TextArea textareaKomentarz;

@FXML
private TextArea textareaChatGrupa;

@FXML
private TextField textfieldChatGrupa;

@FXML
private Label labelChatTytulGrupa;

@FXML
private Button buttonZapiszZmianyZadanie;

@FXML
private ProgressBar barSprint;

@FXML
private ProgressBar barDataSprint;


    @Override
    public void initialize(URL url, ResourceBundle rb) {   

        //WczytajProjekty();
        
        /**
         * 
         * Po zalogowaniu się do danego modułu,
         * automatycznie zostają wczytane dane związane
         * ze sprintami / chatem
         * 
         * Przy okazji blokujemy kilka pół znajdujących się
         * po prawej stronie interfejsu - są to miejsca
         * do których dostęp ma tylko Lider
         *  
         */
        
        WczytajSprinty();
        WczytajChat(0);
        textfieldStatus.setDisable(true);
        textfieldPriorytet.setDisable(true);
        textfieldUtworzono.setDisable(true);
        textfieldZaaktualizowano.setDisable(true);

    }
    
    /**
     * 
     * Po naciśnięciu w przycisk Wyjdź, aplikacja wyłącza aktualne okno
     * i przechodzi do okna logowania
     * 
     * @param event
     * @throws IOException 
     */
    
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
    
    /**
     *
     * Metoda zaczytuje listę wszytkich projektów z bazy danych
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
                
                System.err.println(" nie można wykonac tego zapytania: WCZYTAJ PROJEKTY" + e.getMessage());
            }        
    }    
    
    
    int idProjektu;
    String nazwaProjektu;
    
    
    /**
     * 
     * Metoda zaczytuje listę wszystkich sprintów z bazy danych
     * 
     */
    
    public void WczytajSprinty() {
        
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
                
                PokazAlert("Informacja","Błąd","Niestety wystąpił błąd z bazą danych. Nie udało się wczytać sprintu ID: #1");
                
                System.err.println(" nie można wykonac tego zapytania: WCZYTAJ ID PROJEKTU" + e.getMessage());
            }        
        
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
                
                PokazAlert("Informacja","Błąd","Niestety wystąpił błąd z bazą danych. Nie udało się wczytać sprintów ID: #2");
                
                System.err.println(" nie można wykonac tego zapytania: WCZYTAJ SPRINTY" + e.getMessage());
            }        
    }     

    
    String query;
    int idGrupy;
    int idGrupyChat;
    int idWiadomosci;
    String dataWyslania;
    String login;
    String loginChat;
    String tresc;
    String nazwaGrupy;
    int czyscGrupy = 0;
    
    
    /**
     * 
     * Metoda wczytuję wiadomości z chatu, zapisane w bazie danych
     * 
     * zmienna mówiąca o tym czy został wciśnięty przycisk WYCZYŚĆ CHAT
     * @param czyscGrupy 
     */
    
    public void WczytajChat(int czyscGrupy) {
        
        textareaChatGrupa.clear();
    
        try {

                Connection connection = connect();
                Statement stat = connection.createStatement();
                
                query = "SELECT ID_GRUPY, LOGIN FROM UZYTKOWNICY WHERE ID_UZYTKOWNIKA ="+getZalogowany();
                
                ResultSet rs = stat.executeQuery(query);
                
                idGrupyChat = rs.getInt("ID_GRUPY");
                loginChat = rs.getString("LOGIN");
                
                query = "SELECT NAZWA_GRUPY FROM GRUPY WHERE ID_GRUPY ="+idGrupyChat;
                
                rs = stat.executeQuery(query);

                nazwaGrupy = rs.getString("NAZWA_GRUPY");
                
                labelChatNazwaGrupy.setText(nazwaGrupy);
                
                stat.close();
                connection.commit();
                connection.close();

            } catch (SQLException e) {
                
                PokazAlert("Informacja","Błąd","Niestety wystąpił błąd z bazą danych. Nie udało się wczytać chatu ID: #1");
                
                System.err.println(" nie można wykonac tego zapytania: WCZYTAJ CHAT 1" + e.getMessage());
            }        
        
        try {
                Connection connection = connect();
                Statement stat = connection.createStatement();
                
                switch(czyscGrupy) {
                    case 0:
                        query = "SELECT * FROM CHAT WHERE ID_PROJEKTU ="+idProjektu+" AND ID_GRUPY ="+idGrupyChat;
                        czyscGrupy = 0;
                        break;
                    case 1:
                        String dataDzisiejsza = String.valueOf(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMM hh:mm")));
                        query = "SELECT * FROM CHAT WHERE ID_PROJEKTU ="+idProjektu+" AND ID_GRUPY = "+idGrupyChat+" AND DATA_WYSLANIA='"+dataDzisiejsza+"'";
                        czyscGrupy = 1;
                        break;
                }
                
                ResultSet rs = stat.executeQuery(query);
                
                while(rs.next()) {
//                    idWiadomosci = rs.getInt("ID_WIADOMOSCI");
                    tresc = rs.getString("TRESC");
                    dataWyslania = rs.getString("DATA_WYSLANIA");
                    login = rs.getString("LOGIN");
                    
                    textareaChatGrupa.setWrapText(true);
                    textareaChatGrupa.appendText(dataWyslania+" | "+login+" -> "+tresc+"\n");
                    System.out.println(dataWyslania+" "+login+" "+tresc+"\n");
                }
                
                stat.close();
                connection.commit();
                connection.close();

            } catch (SQLException e) {
                
                PokazAlert("Informacja","Błąd","Niestety wystąpił błąd z bazą danych. Nie udało się wczytać chatu ID: #2");
                
                System.err.println(" nie można wykonac tego zapytania: WCZYTAJ CHAT 2" + e.getMessage());
            }        
    }
    
    
    String nazwaSprintu;
    int idSprintu;
    String dataStartSprintu;
    String dataEndSprintu;
    double liczbaZadanSprintu;
    double liczbaUZadanSprintu;
    double wartoscProgresuZadan;
    
    
    /**
     * 
     * Pobierane są wszystkie informacje na temat wybranego sprintu
     * 
     * a następnie odpowiednie dane są wyświetlane w interfejsie
     * 
     * @param event 
     */
    
    @FXML
    private void ActionComboBoxWybierzSprint(ActionEvent event) {
        
        nazwaSprintu = (String) comboboxWybierzSprint.getSelectionModel().getSelectedItem();

        paneDuring.getChildren().clear();
        paneToDo.getChildren().clear();
        paneTesting.getChildren().clear();
        paneReady.getChildren().clear();

        try {

            Connection connection = connect();
            Statement stat = connection.createStatement();
            
            query = "SELECT ID_SPRINTU, DATA_START_SPRINTU, DATA_END_SPRINTU FROM SPRINTY WHERE NAZWA_SPRINTU = '" + nazwaSprintu +"' AND ID_PROJEKTU ="+idProjektu;
            
            ResultSet rs = stat.executeQuery(query);
            
            idSprintu = rs.getInt("ID_SPRINTU");
            
            dataStartSprintu = rs.getString("DATA_START_SPRINTU");
            dataEndSprintu = rs.getString("DATA_END_SPRINTU");
            
            labelDate1.setText(dataStartSprintu);
            labelDate2.setText(dataEndSprintu);
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH);
            LocalDate date1 = LocalDate.parse(dataStartSprintu, formatter);
            LocalDate date2 = LocalDate.parse(dataEndSprintu, formatter);
            System.out.println("TEST DATY: "+date1); // 2010-01-02
            System.out.println("TEST DATY: "+date2); // 2010-01-02
            
            final long dniSprintu = ChronoUnit.DAYS.between(date1, date2);

            System.out.println("Liczba dni sprintu: " + dniSprintu);

            String dataDzisiejsza = String.valueOf(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));

            LocalDate date3 = LocalDate.parse(dataDzisiejsza, formatter);

            final long dniDoKoncaSprintu = ChronoUnit.DAYS.between(date3, date2);

            System.out.println("liczba dni do zakonczenia sprintu: " + dniDoKoncaSprintu);

            double d1 = (int) dniDoKoncaSprintu;
            double d2 = (int) dniSprintu;

            double obliczenia = 0;

            obliczenia = (1-(d1/d2));

            System.out.println("wartosc obliczen przed: "+obliczenia);

            barDataSprint.setProgress(obliczenia);

            stat.close();
            connection.commit();
            connection.close();
            
            WczytajZadania();
            WczytajBarSprint();
            
        } catch (SQLException e) {
            
            PokazAlert("Informacja","Błąd","Niestety wystąpił błąd z bazą danych. Nie udało się wybrać sprintu ID: #1");
            
            System.err.println(" nie można wykonac tego zapytania: WYBIERZ SPRINT 1" + e.getMessage());
        }
    }
    
    
    /**
     *
     * Metoda odpowiada za prawidłowe pobranie i wyświetlenie danych statystycznych związanych
     * z zadaniami wybranego sprintu
     * 
     */
    
    public void WczytajBarSprint() {
        
        barSprint.setProgress(0);
        liczbaUZadanSprintu = 0;
        liczbaZadanSprintu = 0;
        
            try {

                Connection connection = connect();
                Statement stat = connection.createStatement();

                query = "SELECT COUNT(ID_ZADANIA) FROM ZADANIA WHERE ID_SPRINTU = " + idSprintu + " AND ID_PRZYPISANEGO = "+getZalogowany();

                ResultSet rs = stat.executeQuery(query);

                if(rs.next()) {
                    liczbaZadanSprintu = rs.getInt(1);
                }
                else {
                    liczbaZadanSprintu = 0;
                }
                
                System.out.println("Liczba zadań w sprincie: "+liczbaZadanSprintu);

                query = "SELECT COUNT(ID_ZADANIA) FROM ZADANIA WHERE ID_SPRINTU = "+idSprintu+" AND STATUS = '4' AND ID_PRZYPISANEGO = "+getZalogowany();

                rs = stat.executeQuery(query);
                
                if(rs.next()) {
                    liczbaUZadanSprintu = rs.getInt(1);
                }
                else {
                    liczbaUZadanSprintu = 0;
                }
                
                System.out.println("Liczba ukonczonych zadań w sprincie: "+liczbaUZadanSprintu);

                if(liczbaZadanSprintu!=0) {
                    wartoscProgresuZadan = liczbaUZadanSprintu/liczbaZadanSprintu;
                }
                else {
                    wartoscProgresuZadan = 0;
                }

                System.out.println("Wartosc zadan: "+wartoscProgresuZadan);

                barSprint.setProgress(wartoscProgresuZadan); 
                
                rs.close();
                stat.close();
                connection.commit();
                connection.close();            

            } catch (SQLException e) {
                
                PokazAlert("Informacja","Błąd","Niestety wystąpił błąd z bazą danych. Nie udało się wczytać progresu sprintu");
                
                System.err.println(" nie można wykonac tego zapytania: WYBIERZ SPRINT 1" + e.getMessage());
            }
    }


    int idZadania;
    String nazwaZadania;
    int status;
    String priorytet;
    int idZglaszajacego;
    int idPrzypisanego;
    String dataUtworzenia;
    String dataAktualizacji;
    String opis;
    String komentarz;
    String priorytetWybranegoZadania;
    static int wybraneZadanie;
    
    int klikZadanie = 0;
    int klikPaneToDo = 0;
    int klikPaneDuring = 0;
    int klikPaneTesting = 0;
    int klikPaneReady = 0;
    int klikPane = 0;
    
    int wybranyStatus;
    int klikNaSiebie = 0;
    String statusText;
            
    
    /**
     *
     * Metoda odpowiada za wczytanie danych o zadaniach dla zalogowanego użytkownika
     * oraz umieszczenie owych danych w odpowiednich miejscach interfejsu
     * 
     * @throws SQLException 
     */
    
    public void WczytajZadania() throws SQLException {
        
        paneToDo.getChildren().clear();
        paneDuring.getChildren().clear();
        paneTesting.getChildren().clear();
        paneReady.getChildren().clear();
        klikNaSiebie = 0;
        Connection connection = connect();
        Statement stat = connection.createStatement();
            
        query = "SELECT * FROM ZADANIA WHERE ID_PRZYPISANEGO ="+getZalogowany()+" AND ID_SPRINTU="+idSprintu;
            
        ResultSet rs = stat.executeQuery(query);

       // int i = 0;
        int layoutYToDo = 0;
        int layoutYDuring = 0;
        int layoutYTesting = 0;
        int layoutYReady = 0;

       // int layoutYTytul = 0;
    
        AnchorPane kopia = new AnchorPane();
       
        while(rs.next()) {
            
            idZadania = rs.getInt("ID_ZADANIA");
            nazwaZadania = rs.getString("NAZWA_ZADANIA");
            status = rs.getInt("STATUS");
            priorytet = rs.getString("PRIORYTET");
            idZglaszajacego = rs.getInt("ID_ZGLASZAJACEGO");
            idPrzypisanego = rs.getInt("ID_PRZYPISANEGO");
            dataUtworzenia = rs.getString("DATA_UTWORZENIA");
            dataAktualizacji = rs.getString("DATA_AKTUALIZACJI");
            opis = rs.getString("OPIS");
            komentarz = rs.getString("KOMENTARZ");
            
            Zadania zadanko = new Zadania(idZadania, nazwaZadania, String.valueOf(status), priorytet, idZglaszajacego, idPrzypisanego, dataUtworzenia, dataAktualizacji, opis, komentarz, idProjektu, idSprintu, idGrupy);
            
            Pane zadanie = new Pane();
            zadanie.setStyle("-fx-background-color: #5bbdcf; -fx-border-color: #5bbdcf; -fx-border-width: 1;");
            zadanie.setPrefSize(201, 65);
            //zadanie.setId(""+i);
            
            
            /**
             * 
             * Po kliknięciu na zadanie pobierane jest ID zadania
             * a następnie wszelkie informacje o zadaniu
             * i zostają one wyświetlone w prawej części interfejsu
             * 
             */
            
            zadanie.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                
                    wybraneZadanie = zadanko.getIdZadania();
                    System.out.println("Wybrane zadanie id: "+wybraneZadanie);
                    priorytetWybranegoZadania = zadanko.getPriorytet();
                    String opisWybranegoZadania = zadanko.getOpis();
                    String komentarzWybranegoZadania = zadanko.getKomentarz();
                    
                    klikZadanie = 1;
                    System.out.println("KLIK ZADANIA: "+klikZadanie);
                    
                    switch(zadanko.getStatus()) {
                        case "1":
                            textfieldStatus.setText("Do wykonania");
                            break;
                            
                        case "2":
                            textfieldStatus.setText("Wykonywane");
                            break;
                            
                        case "3":
                            textfieldStatus.setText("Testowane");
                            break;
                            
                        case "4":
                            textfieldStatus.setText("Gotowe");
                            break;
                    }
                    
                    textfieldPriorytet.setText(zadanko.getPriorytet());
                    
                    textfieldUtworzono.setText(zadanko.getDataUtworzenia());
                    
                    textfieldZaaktualizowano.setText(zadanko.getDataAktualizacji());
                    
                    textareaOpis.setText(zadanko.getOpis());
                    textareaOpis.setWrapText(true);
                    
                    textareaKomentarz.setText(zadanko.getKomentarz());
                    textareaKomentarz.setWrapText(true);
                }});
            
            /**
             * 
             * Po kliknięciu na wyznaczone pole etapu / statusu zadania
             * następuje sprawdzenie, czy wcześniej zostało wybrane (kliknięte)
             * jakieś zadanie. Jeśli tak to owe zadanie zostaje przeniesione do
             * etapu na którym nastąpiło kliknięcie
             * 
             */
            
            paneDuring.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    
                    wybranyStatus = 2;
                    
                    if(klikPane<2 && klikZadanie==1)
                    {
                        klikPane++;
                    }
                    else {
                        klikPane = 0;
                    }
                    
                    System.out.println("KLIK PANEL: "+klikPane);
                    
                    if(klikZadanie==1 && klikPane!=1){
                        
                        try {

                        Connection connection = connect();
                        Statement stat = connection.createStatement();

                        String query2 = "UPDATE ZADANIA SET STATUS = " + wybranyStatus + " WHERE ID_ZADANIA =" + wybraneZadanie +"";

                        stat.executeUpdate(query2);

                        System.out.println("JESTEM W PRESSED I WYKONAŁEM");

                        stat.close();
                        connection.commit();
                        connection.close();

                        } catch (SQLException e) {
                             System.err.println(" nie można wykonac tego zapytania: UPDATE ZADANIA STATUS" + e.getMessage());
                            }
                        
                        try {
                            WczytajZadania();
                            System.out.println("JESTEM W WCZYTAJ I WYKONAŁEM");
                        } catch (SQLException ex) {
                            Logger.getLogger(Panel_Lidera_GrupyController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        
                        System.out.println("KLIK PANEL Przed kasacja : "+klikPane);
                        klikZadanie=0;
                        klikPaneToDo = 0;
                        klikPaneDuring = 0;
                        klikPaneTesting = 0;
                        klikPaneReady = 0;
                        klikPane = 0;
                        WczytajBarSprint();
                        System.out.println("KLIK PANEL PO kasacji : "+klikPane);
                    }}});
            
            
            /**
             *
             * Po najechaniu na pole etapu, następuje zmiana tła
             * 
             */
            
            paneDuring.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
               
                    paneDuring.setStyle("-fx-background-color: #bfdbe0;");    
                         
                }});
            
            
            paneDuring.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                
                    paneDuring.setStyle("-fx-background-color: white;");    
                       
                }});
            
            
            paneTesting.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    
                    wybranyStatus = 3;
                    
                    if(klikPane<2 && klikZadanie==1)
                    {
                        klikPane++;
                    }
                    else {
                        klikPane = 0;
                    }
                    
                    System.out.println("KLIK PANEL: "+klikPane);
                    
                    if(klikZadanie==1 && klikPane!=1){
                        
                        try {

                        Connection connection = connect();
                        Statement stat = connection.createStatement();

                        String query2 = "UPDATE ZADANIA SET STATUS = " + wybranyStatus + " WHERE ID_ZADANIA =" + wybraneZadanie +"";

                        stat.executeUpdate(query2);

                        System.out.println("JESTEM W PRESSED I WYKONAŁEM");

                        stat.close();
                        connection.commit();
                        connection.close();

                        } catch (SQLException e) {
                             System.err.println(" nie można wykonac tego zapytania: UPDATE ZADANIA STATUS" + e.getMessage());
                            }
                        
                        try {
                            WczytajZadania();
                            System.out.println("JESTEM W WCZYTAJ I WYKONAŁEM");
                        } catch (SQLException ex) {
                            Logger.getLogger(Panel_Lidera_GrupyController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        
                        System.out.println("KLIK PANEL Przed kasacja : "+klikPane);
                        klikZadanie=0;
                        klikPaneToDo = 0;
                        klikPaneDuring = 0;
                        klikPaneTesting = 0;
                        klikPaneReady = 0;
                        klikPane = 0;
                        WczytajBarSprint();
                        System.out.println("KLIK PANEL PO kasacji : "+klikPane);
                    }}});
            
            
                paneTesting.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                
                    paneTesting.setStyle("-fx-background-color: #bfdbe0;");    
                         
                }});
                
                
                paneTesting.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                
                    paneTesting.setStyle("-fx-background-color: white;");    
                         
                }});


            paneReady.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    
                    wybranyStatus = 4;
                    
                    if(klikPane<2 && klikZadanie==1)
                    {
                        klikPane++;
                    }
                    else {
                        klikPane = 0;
                    }
                    
                    System.out.println("KLIK PANEL: "+klikPane);
                    
                    if(klikZadanie==1 && klikPane!=1){
                        
                        try {

                        Connection connection = connect();
                        Statement stat = connection.createStatement();

                        String query2 = "UPDATE ZADANIA SET STATUS = " + wybranyStatus + " WHERE ID_ZADANIA =" + wybraneZadanie +"";

                        stat.executeUpdate(query2);

                        System.out.println("JESTEM W PRESSED I WYKONAŁEM");

                        stat.close();
                        connection.commit();
                        connection.close();

                        } catch (SQLException e) {
                             System.err.println(" nie można wykonac tego zapytania: UPDATE ZADANIA STATUS" + e.getMessage());
                            }
                        
                        try {
                            WczytajZadania();
                            System.out.println("JESTEM W WCZYTAJ I WYKONAŁEM");
                        } catch (SQLException ex) {
                            Logger.getLogger(Panel_Lidera_GrupyController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        
                        System.out.println("KLIK PANEL Przed kasacja : "+klikPane);
                        klikZadanie=0;
                        klikPaneToDo = 0;
                        klikPaneDuring = 0;
                        klikPaneTesting = 0;
                        klikPaneReady = 0;
                        klikPane = 0;
                        WczytajBarSprint();
                        System.out.println("KLIK PANEL PO kasacji : "+klikPane);
                    }}});
            
            
                paneReady.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                
                    paneReady.setStyle("-fx-background-color: #bfdbe0;");    
                     
                }});     
                
                
                paneReady.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                
                    paneReady.setStyle("-fx-background-color: white;");    
                      
                }});            
            
        
            paneToDo.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    
                    wybranyStatus = 1;
                    
                    if(klikPane<2 && klikZadanie==1)
                    {
                        klikPane++;
                    }
                    else {
                        klikPane = 0;
                    }
                    
                    System.out.println("KLIK PANEL: "+klikPane);
                    
                    if(klikZadanie==1 && klikPane!=1){
                        
                        try {

                        Connection connection = connect();
                        Statement stat = connection.createStatement();

                        String query2 = "UPDATE ZADANIA SET STATUS = " + wybranyStatus + " WHERE ID_ZADANIA =" + wybraneZadanie +"";

                        stat.executeUpdate(query2);

                        System.out.println("JESTEM W PRESSED I WYKONAŁEM");

                        stat.close();
                        connection.commit();
                        connection.close();

                        } catch (SQLException e) {
                             System.err.println(" nie można wykonac tego zapytania: UPDATE ZADANIA STATUS" + e.getMessage());
                            }
                        
                        try {
                            WczytajZadania();
                            System.out.println("JESTEM W WCZYTAJ I WYKONAŁEM");
                        } catch (SQLException ex) {
                            Logger.getLogger(Panel_Lidera_GrupyController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        
                        System.out.println("KLIK PANEL Przed kasacja : "+klikPane);
                        klikZadanie=0;
                        klikPaneToDo = 0;
                        klikPaneDuring = 0;
                        klikPaneTesting = 0;
                        klikPaneReady = 0;
                        klikPane = 0;
                        WczytajBarSprint();
                        System.out.println("KLIK PANEL PO kasacji : "+klikPane);
                    }}});


                paneToDo.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                
                    paneToDo.setStyle("-fx-background-color: #bfdbe0;");    
                    
                }});
                

                paneToDo.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {

                    paneToDo.setStyle("-fx-background-color: white;");
                    
                }});                 
            
            
            zadanie.setOnMouseEntered(new EventHandler<MouseEvent>() {
                
                @Override
                public void handle(MouseEvent event) {
                    zadanie.setStyle("-fx-background-color: white; -fx-border-color: blue; -fx-border-width: 1;");
                }});

            
            zadanie.setOnMouseExited(new EventHandler<MouseEvent>() {
                
                @Override
                public void handle(MouseEvent event) {
                    zadanie.setStyle("-fx-background-color: #5bbdcf; -fx-border-color: blue; -fx-border-width: 1;");
                }});
            
            
            /**
             * 
             * w zależności od liczby zadań znajdujących się w każdym
             * z pól etapów, każde zadanie jest umieszczane jedno pod drugim
             * z tego względu należy odpowiednie kontenery
             * równomiernie ułożyć w polu etapu
             * 
             */
            
            switch(status) {
            
                case 1:

                    zadanie.setLayoutY(layoutYToDo+12);
                    zadanie.setLayoutX(7);

                    layoutYToDo = layoutYToDo + 70;
                    
                    break;

                case 2:

                    zadanie.setLayoutY(layoutYDuring+12);
                    zadanie.setLayoutX(7);

                    layoutYDuring = layoutYDuring + 70;                   

                    break;

                case 3:

                    zadanie.setLayoutY(layoutYTesting+12);
                    zadanie.setLayoutX(7);

                    layoutYTesting = layoutYTesting + 70;

                    break;

                case 4:

                    zadanie.setLayoutY(layoutYReady+12);
                    zadanie.setLayoutX(7);

                    layoutYReady = layoutYReady + 70;

                    break;
            }
           
            zadanie.getChildren().add(new Label("| "+nazwaZadania+" |"));     /* MUSI BYĆ */
            zadanie.getChildren().get(0).setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-font-color: black;");
            zadanie.getChildren().get(0).prefHeight(20);
            zadanie.getChildren().get(0).prefWidth(50);
            zadanie.getChildren().get(0).setLayoutX(6);
            zadanie.getChildren().get(0).setLayoutY(6);
            zadanie.getChildren().get(0).setId("1");
            
            zadanie.getChildren().add(new Label("Priorytet: "+priorytet));     /* MUSI BYĆ */
            zadanie.getChildren().get(1).setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-font-color: black;");
            zadanie.getChildren().get(1).prefHeight(20);
            zadanie.getChildren().get(1).prefWidth(20);
            zadanie.getChildren().get(1).setLayoutX(6);
            zadanie.getChildren().get(1).setLayoutY(45);
            
            zadanie.getChildren().add(new Label("Utw: "+dataUtworzenia));     /* MUSI BYĆ */
            zadanie.getChildren().get(2).setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-font-color: black;");
            zadanie.getChildren().get(2).prefHeight(20);
            zadanie.getChildren().get(2).prefWidth(20);
            zadanie.getChildren().get(2).setLayoutX(115);
            zadanie.getChildren().get(2).setLayoutY(45);
            
            switch(status) {
            
            case 1:
                
                paneToDo.getChildren().add(zadanie);
                
                break;
                
            case 2:
                
                paneDuring.getChildren().add(zadanie);
                
                break;
            
            case 3:
                
                paneTesting.getChildren().add(zadanie);
                
                break;
                
            case 4:
                
                paneReady.getChildren().add(zadanie);
                
                break;
            } 
        }
        
        scrollToDo.setContent(paneToDo);
        scrollDuring.setContent(paneDuring);
        scrollTesting.setContent(paneTesting);
        scrollReady.setContent(paneReady);

        stat.close();
        connection.commit();
        connection.close();        
        
    }

    
    /**
     *
     * Metoda odpowiada za pobranie z pola tekstowego wiadomości, wysłanie jej do bazy a następnie
     * wyświetlenie w oknie chatu
     * 
     * @param event 
     */
    
    @FXML
    private void ActionChatWyslij(ActionEvent event) {
        
        try {

                Connection connection = connect();
                Statement stat = connection.createStatement();
                
                tresc = textfieldChatGrupa.getText();
                dataWyslania = String.valueOf(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMM hh:mm")));

                query = "INSERT INTO CHAT(LOGIN, TRESC, DATA_WYSLANIA, ID_PROJEKTU, ID_GRUPY) VALUES"
                        + " ('" + loginChat + "','" + tresc + "','" + dataWyslania + "','" + idProjektu + "','" + idGrupyChat + "')";
                
                stat.executeUpdate(query);
                
                textfieldChatGrupa.clear();
                
                stat.close();
                connection.commit();
                connection.close();
                
                switch(czyscGrupy) {
                    case 0:
                        WczytajChat(czyscGrupy);
                        break;
                    case 1:
                        WczytajChat(czyscGrupy);
                        break;
                }

            } catch (SQLException e) {
                
                PokazAlert("Informacja","Błąd","Niestety wystąpił błąd z bazą danych. Nie udało się wysłać wiadomości na chacie");
                
                System.err.println(" nie można wykonac tego zapytania: CHAT WYŚLIJ" + e.getMessage());
            }    
    }

    
    /**
     *
     * Metoda odpowiada za wyczyszczenie okna chatu
     * 
     * @param event 
     */
    
    @FXML
    private void ActionChatWyczysc(ActionEvent event) {
        
        textareaChatGrupa.clear();
        
        czyscGrupy = 1;
        WczytajChat(czyscGrupy);
        
    }

    
    String dataDzisiejsza;
    String opisWybranegoZadania;
    String komentarzWybranegoZadania;
    
    
    /**
     * 
     * Metoda odpowiada za zapisanie zmian wprowadzonych przez użytkownika poprzez
     * wykorzystanie prawej części interfejsu
     * 
     * @param event 
     */
    
    @FXML
    private void ActionButtonZapiszZmianyZadanie(ActionEvent event) {
        
        try {
            
                Connection connection = connect();
                Statement stat = connection.createStatement();
                
                dataDzisiejsza = String.valueOf(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE));
                
                opisWybranegoZadania = textareaOpis.getText();
                komentarzWybranegoZadania = textareaKomentarz.getText();
                
                query = "UPDATE ZADANIA SET DATA_AKTUALIZACJI = '" + dataDzisiejsza + "', OPIS = '" + opisWybranegoZadania + "', KOMENTARZ = '" + komentarzWybranegoZadania + "' WHERE ID_ZADANIA =" + getWybraneZadanie() + "";
            
                stat.executeUpdate(query);
                
                stat.close();
                connection.commit();
                connection.close();

                PokazAlert("Informacja","Potwierdzenie","Zapisany zmiany w zadaniu!");
            
            WczytajZadania();
            
        } catch (SQLException e) {
            
                PokazAlert("Informacja","Błąd","Niestety wystąpił błąd z bazą danych. Nie udało się zapisać zmian");
            
                System.err.println(" nie można wykonac tego zapytania: ZAPISZ ZMIANY ZADANIE " + e.getMessage());
            }
    }
    
    /**
     * 
     * Metoda zwraca ID wybranego / klikniętego zadania
     * 
     * @return 
     */
    
    public static int getWybraneZadanie() {
        return wybraneZadanie;
    }

    /**
     *
     * Metoda odpowiada za utworzenie okna dialogowego
     * i przekazanie odpowiednich informacji
     * 
     * Pod dane zmienne podajemy odpowiednie informacje w zależności od sytuacji
     * @param tytul
     * @param headText
     * @param content 
     */
    
    public void PokazAlert(String tytul, String headText, String content) {
    
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(tytul);
        alert.setHeaderText(headText);
        alert.setContentText(content);

        alert.showAndWait();
    }    
    
    
}
