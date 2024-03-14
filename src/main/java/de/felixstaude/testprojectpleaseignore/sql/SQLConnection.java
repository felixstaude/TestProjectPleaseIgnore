package de.felixstaude.testprojectpleaseignore.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLConnection {
    private Connection connection;

    private final String url = "jdbc:mysql://mysql.gameserver.gamed.de:3306/s1484238?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private final String user = "s1484238";
    private final String password = "chuteastig";

    public void connect() {
        try {
            if (connection != null && !connection.isClosed()) {
                System.out.println("Already connected.");
                return;
            }
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the database.");
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Disconnected from the database.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                System.out.println("?????");
                connect(); // Versuche erneut zu verbinden, wenn die Verbindung geschlossen ist oder nicht existiert
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
