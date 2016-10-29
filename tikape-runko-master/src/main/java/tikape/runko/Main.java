package tikape.runko;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.AlueDao;
import tikape.runko.database.Database;
import tikape.runko.database.KeskustelijaDao;
import tikape.runko.database.KeskusteluDao;
import tikape.runko.database.MuunnaString;
import tikape.runko.database.ViestiDao;
import tikape.runko.domain.Alue;
import tikape.runko.domain.Keskustelija;
import tikape.runko.domain.Keskustelu;
import tikape.runko.domain.Viesti;

public class Main {

    public static void main(String[] args) throws Exception {
        Database database = new Database("jdbc:sqlite:opiskelijat.db");
        database.init();

        AlueDao alue = new AlueDao(database);
        KeskusteluDao kesk = new KeskusteluDao(database);
        ViestiDao vies = new ViestiDao(database);
        KeskustelijaDao user = new KeskustelijaDao(database);
        MuunnaString stringMuuntaja = new MuunnaString();
        
        get("/", (req, res) -> {                // etusivu
            HashMap map = new HashMap<>();
            map.put("viesti", "tervehdys");
            List<Viesti> viestit = vies.findNewestTen();                                        // haetaan uusimmat viestit
            for (Viesti vie: viestit) {
                vie.setKirjoittajannimi(user.findOne(vie.getKirjoittaja()).getNimi());          // lisätään viestiolioihin kirjoittaja
            }
            map.put("viestit", viestit);                                                        // viedään html-mappiin viestioliot

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        get("/alueet", (req, res) -> {          // alueiden näyttäminen
            HashMap map = new HashMap<>();

            List<Alue> curralueet = alue.findAll();                                             // haetaan kaikki alueet
            for (Alue aa: curralueet) {
                aa.setUusimmanpaivays(vies.findNewestFromAlueString(aa.getId()));               // lisää uusimman viestin päiväyksen alueolioihin, "--" jos ei ole
            }
            map.put("alueet", curralueet);                                                      // viedään html-mappiin alueet

            return new ModelAndView(map, "alueet");
        }, new ThymeleafTemplateEngine());

        get("/alueet/:id", (req, res) -> {      // keskusteluiden näyttäminen
            HashMap map = new HashMap<>();
            map.put("alue", alue.findOne(Integer.parseInt(req.params(":id"))));                 // viedään html-mappiin current alueolio (otsikkoa varten)
            
            List<Keskustelu> currkeskustelut = kesk.findAllFrom(Integer.parseInt(req.params(":id")));   // haetaan alueen keskustelut
            for (Keskustelu kes: currkeskustelut) {
                kes.setUusimmanpaivays(vies.findNewestFromKeskusteluString(kes.getId()));               // lisätään keskusteluille uusin päiväys tai "--"
            }
            map.put("keskustelut", currkeskustelut);                                            // viedään html-mappiin alueen keskustelut

            return new ModelAndView(map, "keskustelut");
        }, new ThymeleafTemplateEngine());

        post("/alueet", (req, res) -> {         // alueen lisääminen
            Alue a = new Alue(stringMuuntaja.suodataInjektiot(req.queryParams("name")));
            a = alue.create(a);
            
            res.redirect("/alueet");
            return "";
        });
        
        post("/alueet/:id", (req, res) -> {       // keskustelun lisääminen
            int currid = Integer.parseInt(req.params(":id"));
            Keskustelu a = new Keskustelu(stringMuuntaja.suodataInjektiot(req.queryParams("name")), currid);
            a = kesk.create(a);
            
            res.redirect("/alueet/" + currid);
            return "";
        });
        
        get("/alueet/:id/keskustelut/:keskid", (req, res) -> {      // viestien näyttäminen
            HashMap map = new HashMap<>();
            map.put("alue", alue.findOne(Integer.parseInt(req.params(":id"))));                 // viedään html-mappiin current alueolio (otsikkoa varten)
            map.put("keskustelu", kesk.findOne(Integer.parseInt(req.params(":keskid"))));       // viedään html-mappiin current keskusteluolio (otsikkoa varten)
            List<Viesti> viestit = vies.findAllFrom(Integer.parseInt(req.params(":keskid")));   // haetaan viestit
            for (Viesti vie: viestit) {                                                         
                vie.setKirjoittajannimi(user.findOne(vie.getKirjoittaja()).getNimi());          // lisätään viestiolioihin kirjoittaja
            }
            map.put("viestit", viestit);                                                        // viedään html-mappiin viestioliot

            return new ModelAndView(map, "viestit");
        }, new ThymeleafTemplateEngine());

        post("/alueet/:id/keskustelut/:keskid", (req, res) -> {      // viestin lisäys
            int currid = Integer.parseInt(req.params(":id"));
            int keskid = Integer.parseInt(req.params(":keskid"));
            String kirjo = stringMuuntaja.suodataInjektiot(req.queryParams("keskusteli"));
            
            int userid = user.loytyyko(kirjo);                                                  // palauttaa nollan jos kirjoittajaa ei löydy
            if (userid == 0) {
                Keskustelija a = new Keskustelija(kirjo);                                       // luodaan uusi kirjoittaja
                a = user.create(a);
                userid = user.loytyyko(kirjo);
            }
            
            Viesti a = new Viesti(stringMuuntaja.suodataInjektiot(req.queryParams("teksti")), keskid, userid);
            a = vies.create(a);
            
            res.redirect("/alueet/" + currid + "/keskustelut/" + keskid);
            return "";
        });
        
    }
}
