/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.database;

/**
 *
 * @author Hate Halon
 */
public class MuunnaString {
    
    public String suodataInjektiot(String sana) {
        String tulos = "";
        for (int a = 0; a < sana.length(); a++) {
            if (sana.charAt(a) == '<') {
                tulos = tulos + '(';
            } else if (sana.charAt(a) == '>') {
                tulos = tulos + ')';
            } else if (sana.charAt(a) == ';') {
                tulos = tulos + ':';
            } else if (sana.charAt(a) == '{') {
                tulos = tulos + '(';
            } else if (sana.charAt(a) == '}') {
                tulos = tulos + ')';
            } else if (sana.charAt(a) == '\'') {    // java käsittelee merkkijonon \' pelkäksi heittomerkiksi
                tulos = tulos + '"';
            } else {
                tulos += sana.charAt(a);
            }
        }
        return tulos;
    }
    
}
