/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProjektZespolowy;

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
import static ProjektZespolowy.Polaczenie.connect;
import javafx.scene.Node;


/**
 * FXML Controller class
 *
 * @author MaRkOs
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
private Pane paneZadanie;

@FXML
private Pane paneTitle;

@FXML
private ComboBox comboboxProjekt;

@FXML
private ComboBox comboboxSprint;


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

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
    
    
//    @FXML
//    private void setOnDragDropped(MouseEvent event) {
//        
//        paneToDo.getChildren().remove(paneZadanie);        
//        paneDuring.getChildren().add(paneZadanie);
//        
//    }
    String nameDragging;
    
    private void eventDraggingPaneToDo(MouseEvent event)
    {
        nameDragging = "PaneToDo";
    }
    
    @FXML
    private void eventDrop(MouseEvent event) {
        
       paneToDo.getChildren().remove(paneZadanie);
       paneDuring.getChildren().add(paneZadanie);
    }
    
    private void eventDodaj(ActionEvent event) {
         
//         TextField paneText = new TextField();
//         paneText.setVisible(true);
//         paneText.setText("Siemano");
//         paneDuring.getChildren().add(paneText);
             
         VBox paneTest = new VBox();
         
         Label paneLabel = new Label();
         paneLabel.setText("Cos tam");
         paneTest.getChildren().add(paneLabel);
         paneTest.setVisible(true);
         paneTest.setTranslateY(30);
         paneTest.setId("Test");
         System.out.println(paneTest.getId());
        
         for(int i=0; i<4; i++)
         {
            paneToDo.getChildren().add(new VBox(i+1));
         }
         
        paneTest.setStyle("-fx-background-color: black;");
         paneToDo.getChildren().add(paneTest);
         System.out.println("Udalo sie");

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

    @FXML
    private void ActionComboBoxWybierzProjekt(ActionEvent event) {
    }

    @FXML
    private void ActionComboBoxWybierzSprint(ActionEvent event) {
    }

    @FXML
    private void ActionChatWyslij(ActionEvent event) {
    }

    @FXML
    private void ActionChatWyczysc(ActionEvent event) {
    }

    @FXML
    private void ActionZapiszZmiany(ActionEvent event) {
    }

    @FXML
    private void ActionWyczyscWszystko(ActionEvent event) {
    }

}
