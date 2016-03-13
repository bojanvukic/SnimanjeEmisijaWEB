/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mb;

import domen.Planer;
import domen.Zaduzenje;
import domen.Zaposlen;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Bojan
 */
@ManagedBean
@SessionScoped
public class MBSesija implements Serializable{

    private Planer ulogovaniPlaner;
    private Zaposlen izabraniZaposlen;
    private Zaduzenje izabranoZaduzenje;
    
    public MBSesija() {
        
    }
    
    
    public Planer getUlogovaniPlaner() {
        return ulogovaniPlaner;
    }

    public void setUlogovaniPlaner(Planer ulogovaniPlaner) {
        this.ulogovaniPlaner = ulogovaniPlaner;
    }

    public Zaposlen getIzabraniZaposlen() {
        return izabraniZaposlen;
    }

    public void setIzabraniZaposlen(Zaposlen izabraniZaposlen) {
        this.izabraniZaposlen = izabraniZaposlen;
    }

    public Zaduzenje getIzabranoZaduzenje() {
        return izabranoZaduzenje;
    }

    public void setIzabranoZaduzenje(Zaduzenje izabranoZaduzenje) {
        this.izabranoZaduzenje = izabranoZaduzenje;
    }
    
    
}
