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

public class Lider {

    public Lider(int idLideraR, String imieLideraR, String nazwiskoLideraR, String nazwaGrupyLidera) {
        this.idLideraR = idLideraR;
        this.imieLideraR = imieLideraR;
        this.nazwiskoLideraR = nazwiskoLideraR;
        this.nazwaGrupyLidera = nazwaGrupyLidera;
    }

    public int getIdLideraR() {
        return idLideraR;
    }

    public String getImieLideraR() {
        return imieLideraR;
    }

    public String getNazwiskoLideraR() {
        return nazwiskoLideraR;
    }

    public String getNazwaGrupyLidera() {
        return nazwaGrupyLidera;
    }

    public void setIdLideraR(int idLideraR) {
        this.idLideraR = idLideraR;
    }

    public void setImieLideraR(String imieLideraR) {
        this.imieLideraR = imieLideraR;
    }

    public void setNazwiskoLideraR(String nazwiskoLideraR) {
        this.nazwiskoLideraR = nazwiskoLideraR;
    }

    public void setNazwaGrupyLidera(String nazwaGrupyLidera) {
        this.nazwaGrupyLidera = nazwaGrupyLidera;
    }
    
    int idLideraR;
    String imieLideraR;
    String nazwiskoLideraR;
    String nazwaGrupyLidera;
    
    
}
