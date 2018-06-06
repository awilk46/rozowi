/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProjektZespolowy;

/**
 *
 * @author MaRkOs
 */

public class Zadania {

    public int getIdZadania() {
        return idZadania;
    }

    public int getIdZglaszajacego() {
        return idZglaszajacego;
    }

    public int getIdPrzypisanego() {
        return idPrzypisanego;
    }

    public int getIdProjektu() {
        return idProjektu;
    }

    public int getIdSprintu() {
        return idSprintu;
    }

    public int getIdGrupy() {
        return idGrupy;
    }

    public String getNazwaZadania() {
        return nazwaZadania;
    }

    public String getStatus() {
        return status;
    }

    public String getPriorytet() {
        return priorytet;
    }

    public String getDataUtworzenia() {
        return dataUtworzenia;
    }

    public String getDataAktualizacji() {
        return dataAktualizacji;
    }

    public String getOpis() {
        return opis;
    }

    public String getKomentarz() {
        return komentarz;
    }

    public void setIdZadania(int idZadania) {
        this.idZadania = idZadania;
    }

    public void setIdZglaszajacego(int idZglaszajacego) {
        this.idZglaszajacego = idZglaszajacego;
    }

    public void setIdPrzypisanego(int idPrzypisanego) {
        this.idPrzypisanego = idPrzypisanego;
    }

    public void setIdProjektu(int idProjektu) {
        this.idProjektu = idProjektu;
    }

    public void setIdSprintu(int idSprintu) {
        this.idSprintu = idSprintu;
    }

    public void setIdGrupy(int idGrupy) {
        this.idGrupy = idGrupy;
    }

    public void setNazwaZadania(String nazwaZadania) {
        this.nazwaZadania = nazwaZadania;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPriorytet(String priorytet) {
        this.priorytet = priorytet;
    }

    public void setDataUtworzenia(String dataUtworzenia) {
        this.dataUtworzenia = dataUtworzenia;
    }

    public void setDataAktualizacji(String dataAktualizacji) {
        this.dataAktualizacji = dataAktualizacji;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public void setKomentarz(String komentarz) {
        this.komentarz = komentarz;
    }

    public Zadania(int idZglaszajacego, int idPrzypisanego, int idProjektu, int idSprintu, int idGrupy, String nazwaZadania, String status, String priorytet, String dataUtworzenia, String dataAktualizacji, String opis, String komentarz) {
        this.idZglaszajacego = idZglaszajacego;
        this.idPrzypisanego = idPrzypisanego;
        this.idProjektu = idProjektu;
        this.idSprintu = idSprintu;
        this.idGrupy = idGrupy;
        this.nazwaZadania = nazwaZadania;
        this.status = status;
        this.priorytet = priorytet;
        this.dataUtworzenia = dataUtworzenia;
        this.dataAktualizacji = dataAktualizacji;
        this.opis = opis;
        this.komentarz = komentarz;
    }
    
    public Zadania() {
        this.idZglaszajacego = 0;
        this.idPrzypisanego = 0;
        this.idProjektu = 0;
        this.idSprintu = 0;
        this.idGrupy = 0;
        this.nazwaZadania = "";
        this.status = "";
        this.priorytet = "";
        this.dataUtworzenia = "";
        this.dataAktualizacji = "";
        this.opis = "";
        this.komentarz = "";       
    }
    
    public Zadania(String nazwaZadania, String status, String priorytet, String dataUtworzenia, String dataAktualizacji, String opis, String komentarz) {
        this.nazwaZadania = nazwaZadania;
        this.status = status;
        this.priorytet = priorytet;
        this.dataUtworzenia = dataUtworzenia;
        this.dataAktualizacji = dataAktualizacji;
        this.opis = opis;
        this.komentarz = komentarz;
    }
    
    public Zadania(int idZadania, String nazwaZadania, String status, String priorytet, int idZglaszajacego, int idPrzypisanego, String dataUtworzenia, String dataAktualizacji, String opis, String komentarz, int idProjektu, int idSprintu, int idGrupy) {
        this.idZadania = idZadania;
        this.nazwaZadania = nazwaZadania;
        this.status = status;
        this.priorytet = priorytet;
        this.idZglaszajacego = idZglaszajacego;
        this.idPrzypisanego = idPrzypisanego;
        this.dataUtworzenia = dataUtworzenia;
        this.dataAktualizacji = dataAktualizacji;
        this.opis = opis;
        this.komentarz = komentarz;
        this.idProjektu = idProjektu;
        this.idSprintu = idSprintu;
        this.idGrupy = idGrupy;
    }    
    
    int idZadania;
    int idZglaszajacego;
    int idPrzypisanego;
    int idProjektu;
    int idSprintu;
    int idGrupy;
    String nazwaZadania;
    String status;
    String priorytet;
    String dataUtworzenia;
    String dataAktualizacji;
    String opis;
    String komentarz;
    
}
