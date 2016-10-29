/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.domain;

/**
 *
 * @author Hate Halon
 */
public class Keskustelu {

    private Integer id;
    private String nimi;
    private Integer alueennumero;
    private Integer viestimaara; // html:lle helposti
    private String uusimmanpaivays;

    public Keskustelu(Integer id, String nimi, Integer viestim) {
        this.id = id;
        this.nimi = nimi;
        this.alueennumero = null;
        this.viestimaara = viestim;
    }

    public Keskustelu(String nimi, Integer aluee) {
        this.id = null;
        this.nimi = nimi;
        this.alueennumero = aluee;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public Integer getAlueennumero() {
        return alueennumero;
    }

    public void setAlueennumero(Integer alueennumero) {
        this.alueennumero = alueennumero;
    }

    public Integer getViestimaara() {
        return viestimaara;
    }

    public void setViestimaara(Integer viestimaara) {
        this.viestimaara = viestimaara;
    }

    public String getUusimmanpaivays() {
        return uusimmanpaivays;
    }

    public void setUusimmanpaivays(String uusimmanpaivays) {
        this.uusimmanpaivays = uusimmanpaivays;
    }
    
}
