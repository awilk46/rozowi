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
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import static ProjektZespolowy.Polaczenie.connect;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author MaRkOs
 */

public class Panel_Lidera_GrupyController implements Initializable {

    
@FXML
private ComboBox comboboxGrupa;

@FXML
private Button buttonStworzGrupe;

@FXML
private Button buttonStworzZadanie;

@FXML
private Button buttonEdytujZadanie;

@FXML
private Button buttonWyjdz;

@FXML
private AnchorPane paneChatGrupa;

@FXML
private AnchorPane paneChatProjekt;

@FXML
private Button buttonWyslijGrupa;

@FXML
private Button buttonWyslijProjekt;

@FXML
private Button buttonCzyscChatGrupa;

@FXML
private Button buttonCzyscChatProjekt;

@FXML
private TextField textfieldChatGrupa;

@FXML
private TextField textfieldChatProjekt;

@FXML
private TextArea textareaChatGrupa;

@FXML
private TextArea textareaChatProjekt;


@FXML
private Label labelChatTytulProjekt;

@FXML
private Label labelChatNazwaProjekt;

@FXML
private ProgressBar barSprint;

@FXML
private ProgressBar barDataSprint;

@FXML
private Label labelChatNazwaGrupy;
    

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
    private Button buttonEdytujGrupe;
    @FXML
    private Label labelChatTytulGrupa;
    @FXML
    private Label labelToDo1;
    @FXML
    private Button buttonZapiszZmianyZadanie;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
    private void ActionComboBoxProjekt(ActionEvent event) {
    }

    @FXML
    private void ActionComboBoxSprint(ActionEvent event) {
    }

    @FXML
    private void ActionComboBoxGrupa(ActionEvent event) {
    }

    @FXML
    private void ActionButtonStworzGrupe(ActionEvent event) {
        
        ((Node)(event.getSource())).getScene().getWindow().hide();
        
    }

    @FXML
    private void ActionButtonEdytujGrupe(ActionEvent event) {
        
        ((Node)(event.getSource())).getScene().getWindow().hide();
        
    }

    @FXML
    private void ActionButtonStworzZadanie(ActionEvent event) {
        
        ((Node)(event.getSource())).getScene().getWindow().hide();
        
    }

    @FXML
    private void ActionButtonEdytujZadanie(ActionEvent event) {
    }

    @FXML
    private void ActionButtonWyslijGrupa(ActionEvent event) {
    }

    @FXML
    private void ActionButtonCzyscChatGrupa(ActionEvent event) {
    }

    @FXML
    private void ActionButtonWyslijProjekt(ActionEvent event) {
    }

    @FXML
    private void ActionButtonCzyscChatProjekt(ActionEvent event) {
    }

    @FXML
    private void ActionButtonZapiszZmianyZadanie(ActionEvent event) {
    }

    @FXML
    private void ActionButtonWyczyscZadanie(ActionEvent event) {
    }
    
}
