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
            System.err.println("Błąd w połączeniu z bazą: \n" + e.getMessage());
            return null;
        }
        return connection;
    }
    
}

