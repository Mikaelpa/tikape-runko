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
import tikape.runko.domain.Keskustelu;

/**
 *
 * @author Hate Halon
 */
public class KeskusteluDao implements Dao<Keskustelu, Integer> {

    private Database database;

    public KeskusteluDao(Database database) {
        this.database = database;
    }

    public Keskustelu create(Keskustelu t) throws SQLException {
        database.update("INSERT INTO Keskustelu (nimi, alue) VALUES (?, ?)", t.getNimi(), t.getAlueennumero());
        return t;
    }

    // XXXXXXXXXXXXXXXXXXXXXXXXXXX huom!!!!
    public void update(String updateQuery, Object... params) throws SQLException { // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX täysi muutos
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement(updateQuery);

        for (int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]);
        }

        int changes = stmt.executeUpdate();

        stmt.close();
        connection.close();

        //return changes;
    }

    @Override
    public Keskustelu findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Keskustelu WHERE id = " + key);

        ResultSet rs = stmt.executeQuery();
        List<Keskustelu> keskut = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String nimi = rs.getString("nimi");

            keskut.add(new Keskustelu(id, nimi, 0));
        }

        rs.close();
        stmt.close();
        connection.close();

        return keskut.get(0);
    }

    @Override
    public List<Keskustelu> findAll() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Keskustelu");

        ResultSet rs = stmt.executeQuery();
        List<Keskustelu> keskut = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String nimi = rs.getString("nimi");

            keskut.add(new Keskustelu(id, nimi, 0));
        }

        rs.close();
        stmt.close();
        connection.close();

        return keskut;
    }

    public List<Keskustelu> findAllFrom(int alue) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Keskustelu WHERE alue = " + alue);

        ResultSet rs = stmt.executeQuery();
        List<Keskustelu> keskut = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String nimi = rs.getString("nimi");

            keskut.add(new Keskustelu(id, nimi, 0));
        }

        rs.close();
        stmt.close();
        connection.close();

        /// seuraavassa haetaan viestien määrät: ////////////////7

        connection = database.getConnection();
        stmt = connection.prepareStatement("SELECT Keskustelu.id, COUNT(Viesti.id) AS mr FROM Alue, Keskustelu, Viesti "
            + "WHERE Alue.id = Keskustelu.alue AND Viesti.keskustelu = Keskustelu.id AND Alue.id = " + alue + " GROUP BY Keskustelu.id");
        rs = stmt.executeQuery();
        
        while (rs.next()) {
            Integer id = rs.getInt("id");

            for (Keskustelu aa: keskut) {
                if (aa.getId() == id) {
                    aa.setViestimaara(rs.getInt("mr"));
                }
            }
        }
        rs.close();
        stmt.close();
        connection.close();

        return keskut;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
