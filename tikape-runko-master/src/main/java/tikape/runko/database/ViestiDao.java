/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Keskustelu;
import tikape.runko.domain.Viesti;

/**
 *
 * @author Hate Halon
 */
public class ViestiDao implements Dao<Viesti, Integer> {

    private Database database;

    public ViestiDao(Database database) {
        this.database = database;
    }
    
    public Viesti create(Viesti t) throws SQLException {
        database.update("INSERT INTO Viesti (teksti, keskustelu, kirjoittaja, paivays) VALUES (?, ?, ?, datetime('now'))", t.getTeksti(), t.getKeskustelunnumero(), t.getKirjoittaja());
        return t;
    }

    @Override
    public Viesti findOne(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Viesti> findAll() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Viesti> findNewestTen() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viesti ORDER BY Viesti.paivays DESC LIMIT 10");
//        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viesti WHERE keskustelu = 1");

        ResultSet rs = stmt.executeQuery();
        List<Viesti> viestit = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String teks = rs.getString("teksti");
            Integer kesku = rs.getInt("keskustelu");
            Integer kirjo = rs.getInt("kirjoittaja");
            // date
            String dat = rs.getString("paivays");
            //

            viestit.add(new Viesti(id, teks, kesku, kirjo, dat));
        }

        rs.close();
        stmt.close();
        connection.close();

        return viestit;
    }

    public String findNewestFromAlueString(Integer alue) throws SQLException {      ///ota toimiva turvaan ensin ja koita max min komennoilla, myös alemmasta ///
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT paivays FROM Viesti, Alue, Keskustelu "
                + "WHERE Keskustelu.id = Viesti.keskustelu AND Alue.id = Keskustelu.alue AND Alue.id = " + alue + " ORDER BY Viesti.paivays DESC");

        ResultSet rs = stmt.executeQuery();
        List<String> paivat = new ArrayList<>();
        while (rs.next()) {
            String dat = rs.getString("paivays");

            paivat.add(dat);
        }

        rs.close();
        stmt.close();
        connection.close();

        if (paivat.isEmpty()) {
            return "-";
        }
        return paivat.get(0);
    }

    public String findNewestFromKeskusteluString(Integer keskustelu) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT paivays FROM Viesti, Keskustelu "
                + "WHERE Keskustelu.id = Viesti.keskustelu AND Keskustelu.id = " + keskustelu + " ORDER BY Viesti.paivays DESC");

        ResultSet rs = stmt.executeQuery();
        List<String> paivat = new ArrayList<>();
        while (rs.next()) {
            String dat = rs.getString("paivays");

            paivat.add(dat);
        }

        rs.close();
        stmt.close();
        connection.close();

        if (paivat.isEmpty()) {
            return "-";
        }
        return paivat.get(0);
    }

    public List<Viesti> findAllFrom(Integer keskustelu) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viesti WHERE keskustelu = " + keskustelu);
//        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viesti WHERE keskustelu = 1");

        ResultSet rs = stmt.executeQuery();
        List<Viesti> viestit = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String teks = rs.getString("teksti");
            Integer kesku = rs.getInt("keskustelu");
            Integer kirjo = rs.getInt("kirjoittaja");
            // date
            String dat = rs.getString("paivays");
            //

            viestit.add(new Viesti(id, teks, kesku, kirjo, dat));
        }

        rs.close();
        stmt.close();
        connection.close();

        return viestit;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    
}
