/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication2;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author adria
 */
public class FXMLDocument2Controller implements Initializable {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private Pane fstProj;



    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void projOnClicked(MouseEvent event) throws IOException {
          AnchorPane pane = FXMLLoader.load(getClass().getResource("FXMLDocument3.fxml"));
//        FXMLLoader floader = FXMLLoader.load(getClass().getResource("FXMLDocument2.fxml"));
//        floader.setRoot(this);
//        floader.setController(this);
        
        rootPane.getChildren().setAll(pane);

    }
    
    

}
