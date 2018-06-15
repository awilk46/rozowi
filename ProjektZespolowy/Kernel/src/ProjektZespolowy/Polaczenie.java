/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProjektZespolowy;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 *
 * @author MaRkOs
 */

public class Polaczenie {
    
    public static Connection connect() {
        Connection connection = null;
        try {
            // Wskazanie jaki rodzaj bazy danych bęzie wykorzystany, tu sqlite
            Class.forName("org.sqlite.JDBC");
            // Połączenie, wskazujemy rodzaj bazy i jej nazwę
            connection = DriverManager.getConnection("jdbc:sqlite:ProjektBaza.db");
            connection.setAutoCommit(false);
            connection.setReadOnly(false);
            System.out.println("Połączyłem się z bazą ");
        } catch (Exception e) {
            
            PokazAlert("Informacja","Błąd","Niestety nie udało się połączyć z bazą danych");
            
            System.err.println("Błąd w połączeniu z bazą: \n" + e.getMessage());
            return null;
        }
        return connection;
    }
    
    public static void PokazAlert(String tytul, String headText, String content) {
    
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(tytul);
        alert.setHeaderText(headText);
        alert.setContentText(content);

        alert.showAndWait();
    }
    
}

