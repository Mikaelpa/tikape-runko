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
public class Viesti {

    private Integer id;
    private String teksti;
    private Integer keskustelunnumero;
    private Integer kirjoittaja;
    private String kirjoittajannimi; // helpottaa tiedon siirtoa HTML:ään yhtenä oliona
    private String paivays;

    public Viesti(Integer id, String teksti, Integer keskustelunnumero, Integer kirjoittaja, String kirjoittajannimi, String paivays) {
        this.id = id;
        this.teksti = teksti;
        this.keskustelunnumero = keskustelunnumero;
        this.kirjoittaja = kirjoittaja;
        this.kirjoittajannimi = kirjoittajannimi;
        this.paivays = paivays;
    }

    public Viesti(Integer id, String teksti, Integer keskustelunnumero, Integer kirjoittaja, String paivays) {
        this.id = id;
        this.teksti = teksti;
        this.keskustelunnumero = keskustelunnumero;
        this.kirjoittaja = kirjoittaja;
        this.paivays = paivays;
    }

    public Viesti(String teksti, Integer keskustelunnumero) {
        this.teksti = teksti;
        this.keskustelunnumero = keskustelunnumero;
    }

    public Viesti(String teksti, Integer keskustelunnumero, Integer kirjoittaja) {
        this.teksti = teksti;
        this.keskustelunnumero = keskustelunnumero;
        this.kirjoittaja = kirjoittaja;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTeksti() {
        return teksti;
    }

    public void setTeksti(String teksti) {
        this.teksti = teksti;
    }

    public Integer getKeskustelunnumero() {
        return keskustelunnumero;
    }

    public void setKeskustelunnumero(Integer keskustelunnumero) {
        this.keskustelunnumero = keskustelunnumero;
    }

    public Integer getKirjoittaja() {
        return kirjoittaja;
    }

    public void setKirjoittaja(Integer kirjoittaja) {
        this.kirjoittaja = kirjoittaja;
    }

    public String getKirjoittajannimi() {
        return kirjoittajannimi;
    }

    public void setKirjoittajannimi(String kirjoittajannimi) {
        this.kirjoittajannimi = kirjoittajannimi;
    }

    public String getPaivays() {
        return paivays;
    }

    public void setPaivays(String paivays) {
        this.paivays = paivays;
    }
    
}
