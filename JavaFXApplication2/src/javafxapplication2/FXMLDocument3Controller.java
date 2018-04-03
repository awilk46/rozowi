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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author adria
 */
public class FXMLDocument3Controller implements Initializable {

    @FXML
    private AnchorPane anothRootPane;
    @FXML
    private ImageView undoImg;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void undoOnClicked(MouseEvent event) throws IOException {
         AnchorPane pane = FXMLLoader.load(getClass().getResource("FXMLDocument2.fxml"));
//        FXMLLoader floader = FXMLLoader.load(getClass().getResource("FXMLDocument2.fxml"));
//        floader.setRoot(this);
//        floader.setController(this);
        
        anothRootPane.getChildren().setAll(pane);

    }
    
}
