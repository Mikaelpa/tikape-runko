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
import tikape.runko.domain.Alue;

/**
 *
 * @author Hate Halon
 */
public class AlueDao implements Dao<Alue, Integer> {

    private Database database;

    public AlueDao(Database database) {
        this.database = database;
    }

    public Alue create(Alue t) throws SQLException {
        database.update("INSERT INTO Alue (nimi) VALUES (?)", t.getNimi());
        return t;
    }

    @Override
    public Alue findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Alue WHERE id = " + key);

        ResultSet rs = stmt.executeQuery();
        List<Alue> alueet = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String nimi = rs.getString("nimi");

            alueet.add(new Alue(id, nimi));
        }

        rs.close();
        stmt.close();
        connection.close();

        return alueet.get(0);
    }

    @Override
    public List<Alue> findAll() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Alue");

        ResultSet rs = stmt.executeQuery();
        List<Alue> alueet = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String nimi = rs.getString("nimi");

            alueet.add(new Alue(id, nimi, 0));
        }
        rs.close();
        stmt.close();
        connection.close();

        /// seuraavassa haetaan viestien määrät: ////////////////7
        /// Varmuuskopio toimivana ja testaile///
        
        connection = database.getConnection();
        stmt = connection.prepareStatement("SELECT Alue.id, COUNT(Viesti.id) AS mr FROM Alue, Keskustelu, Viesti WHERE Alue.id = Keskustelu.alue AND Viesti.keskustelu = Keskustelu.id GROUP BY Alue.id");
        rs = stmt.executeQuery();
        
        while (rs.next()) {
            Integer id = rs.getInt("id");

            for (Alue aa: alueet) {
                if (aa.getId() == id) {
                    aa.setViestimaara(rs.getInt("mr"));
                }
            }
        }
        rs.close();
        stmt.close();
        connection.close();

        return alueet;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
