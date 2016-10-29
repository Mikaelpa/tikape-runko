package tikape.runko.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private String databaseAddress;

    public Database(String databaseAddress) throws ClassNotFoundException {
        this.databaseAddress = databaseAddress;
    }

    public Connection getConnection() throws SQLException {
//        return DriverManager.getConnection(databaseAddress);
        return DriverManager.getConnection("jdbc:sqlite:testi16.db");
    }

    public int update(String updateQuery, Object... params) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(updateQuery);

        for (int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]);
        }

        int changes = stmt.executeUpdate();

        stmt.close();
        conn.close();

        return changes;
    }

    public void init() {
        List<String> lauseet = sqliteLauseet();

        // "try with resources" sulkee resurssin automaattisesti lopuksi
        try (Connection conn = getConnection()) {
            Statement st = conn.createStatement();

            // suoritetaan komennot
            for (String lause : lauseet) {
                System.out.println("Running command >> " + lause);
                st.executeUpdate(lause);
            }

        } catch (Throwable t) {
            // jos tietokantataulu on jo olemassa, ei komentoja suoriteta
            System.out.println("Error >> " + t.getMessage());
        }
    }

    private List<String> sqliteLauseet() {
        ArrayList<String> lista = new ArrayList<>();

        // tietokantataulujen luomiseen tarvittavat komennot suoritusjärjestyksessä
        lista.add("CREATE TABLE Opiskelija (id integer PRIMARY KEY, nimi varchar(255));");
        lista.add("INSERT INTO Opiskelija (nimi) VALUES ('Platon');");
        lista.add("INSERT INTO Opiskelija (nimi) VALUES ('Aristoteles');");
        lista.add("INSERT INTO Opiskelija (nimi) VALUES ('Homeros');");

        lista.add("CREATE TABLE Alue (id integer PRIMARY KEY, nimi varchar(255));");
        lista.add("INSERT INTO Alue (nimi) VALUES ('Default alue 1');");
        lista.add("INSERT INTO Alue (nimi) VALUES ('Default alue 2');");
        lista.add("INSERT INTO Alue (nimi) VALUES ('Default alue 3');");

        lista.add("CREATE TABLE Keskustelu (id integer PRIMARY KEY, nimi varchar(255), alue integer, FOREIGN KEY (alue) REFERENCES Alue(id));");
        lista.add("INSERT INTO Keskustelu (nimi, alue) VALUES ('Default keskustelu 1', 1);");
        lista.add("INSERT INTO Keskustelu (nimi, alue) VALUES ('Default keskustelu 2', 1);");

        lista.add("CREATE TABLE Kirjoittaja (id integer PRIMARY KEY, nimi varchar(255));");
        lista.add("INSERT INTO Kirjoittaja (nimi) VALUES ('Default kirjoittaja 1');");
        lista.add("INSERT INTO Kirjoittaja (nimi) VALUES ('Default kirjoittaja 2');");
        
        lista.add("CREATE TABLE Viesti (id integer PRIMARY KEY, teksti varchar(1000), keskustelu integer, kirjoittaja integer, paivays datetime, FOREIGN KEY (keskustelu) REFERENCES Keskustelu(id), FOREIGN KEY (kirjoittaja) REFERENCES Kirjoittaja(id));");
        lista.add("INSERT INTO Viesti (teksti, keskustelu, kirjoittaja, paivays) VALUES ('Default Viesti 1', 1, 1, datetime('2015-12-12 08:30:47'));");   // testitietokantaa
        lista.add("INSERT INTO Viesti (teksti, keskustelu, kirjoittaja, paivays) VALUES ('Default Viesti 2', 1, 2, datetime('2015-12-12 13:30:47'));");
        lista.add("INSERT INTO Viesti (teksti, keskustelu, kirjoittaja, paivays) VALUES ('Default Viesti 3', 1, 1, datetime('2016-01-01 13:30:47'));");
        lista.add("INSERT INTO Viesti (teksti, keskustelu, kirjoittaja, paivays) VALUES ('Default Viesti 4', 2, 1, datetime('2016-03-07 13:30:47'));");
        lista.add("INSERT INTO Viesti (teksti, keskustelu, kirjoittaja, paivays) VALUES ('Default Viesti 5', 1, 1, datetime('2016-05-18 08:30:47'));");
        lista.add("INSERT INTO Viesti (teksti, keskustelu, kirjoittaja, paivays) VALUES ('Default Viesti 6', 1, 2, datetime('2016-06-07 15:30:47'));");
        lista.add("INSERT INTO Viesti (teksti, keskustelu, kirjoittaja, paivays) VALUES ('Default Viesti 7', 1, 2, datetime('2016-08-02 18:30:47'));");
        lista.add("INSERT INTO Viesti (teksti, keskustelu, kirjoittaja, paivays) VALUES ('Default Viesti 8', 2, 2, datetime('2016-09-28 08:30:47'));");
        lista.add("INSERT INTO Viesti (teksti, keskustelu, kirjoittaja, paivays) VALUES ('Default Viesti 9', 2, 2, datetime('2016-10-08 17:30:47'));");
        lista.add("INSERT INTO Viesti (teksti, keskustelu, kirjoittaja, paivays) VALUES ('Default Viesti 10', 1, 2, datetime('2016-10-09 08:30:47'));");
        lista.add("INSERT INTO Viesti (teksti, keskustelu, kirjoittaja, paivays) VALUES ('Default Viesti 11', 1, 1, datetime('2016-10-19 11:30:47'));");
        lista.add("INSERT INTO Viesti (teksti, keskustelu, kirjoittaja, paivays) VALUES ('Default Viesti 12', 1, 2, datetime('2016-10-19 13:30:47'));");
        lista.add("INSERT INTO Viesti (teksti, keskustelu, kirjoittaja, paivays) VALUES ('Default Viesti 13', 2, 2, datetime('2016-10-19 14:30:47'));");
        lista.add("INSERT INTO Viesti (teksti, keskustelu, kirjoittaja, paivays) VALUES ('Default Viesti 14', 1, 2, datetime('2016-10-19 15:30:47'));");
        lista.add("INSERT INTO Viesti (teksti, keskustelu, kirjoittaja, paivays) VALUES ('Default Viesti 15', 1, 1, datetime('2016-10-19 18:30:47'));");
        lista.add("INSERT INTO Viesti (teksti, keskustelu, kirjoittaja, paivays) VALUES ('Default Viesti 16', 1, 1, datetime('2016-10-20 01:40:47'));");
        lista.add("INSERT INTO Viesti (teksti, keskustelu, kirjoittaja, paivays) VALUES ('Default Viesti 17', 1, 2, datetime('2016-10-20 05:10:47'));");
        lista.add("INSERT INTO Viesti (teksti, keskustelu, kirjoittaja, paivays) VALUES ('Default Viesti 18', 1, 2, datetime('2016-10-20 06:20:47'));");
        lista.add("INSERT INTO Viesti (teksti, keskustelu, kirjoittaja, paivays) VALUES ('Default Viesti 19', 2, 2, datetime('2016-10-20 08:30:47'));");
        return lista;
    }
}
