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
import tikape.runko.domain.Keskustelija;
import tikape.runko.domain.Keskustelu;

/**
 *
 * @author Hate Halon
 */
public class KeskustelijaDao implements Dao<Keskustelija, Integer> {

    private Database database;

    public KeskustelijaDao(Database database) {
        this.database = database;
    }

    public Keskustelija create(Keskustelija t) throws SQLException {
        database.update("INSERT INTO Kirjoittaja (nimi) VALUES (?)", t.getNimi());
        return t;
    }
    
    public Integer loytyyko(String kuka) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Kirjoittaja WHERE nimi = '" + kuka + "'");

        ResultSet rs = stmt.executeQuery();
        List<Keskustelija> keskustelijat = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String nimi = rs.getString("nimi");

            keskustelijat.add(new Keskustelija(id, nimi));
        }

        rs.close();
        stmt.close();
        connection.close();

        if (keskustelijat.isEmpty()) {
            return 0;
        } else {
        return keskustelijat.get(0).getId();
        }
        
    }
    
    @Override
    public Keskustelija findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Kirjoittaja WHERE id = " + key);

        ResultSet rs = stmt.executeQuery();
        List<Keskustelija> keskustelijat = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String nimi = rs.getString("nimi");

            keskustelijat.add(new Keskustelija(id, nimi));
        }

        rs.close();
        stmt.close();
        connection.close();

        return keskustelijat.get(0);
    }

    @Override
    public List<Keskustelija> findAll() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
