/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mb;

import domen.Zaduzenje;
import domen.Zaposlen;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import session.zaduzenje.SessionZaduzenjeLocal;

/**
 *
 * @author Bojan
 */
@ManagedBean
@RequestScoped
public class MBPrikazZaduzenja {

    private Zaposlen izabraniZaposlen;
    private Zaduzenje izabranoZaduzenje;
    private List<Zaduzenje> listaZaduzenja;
    
    @EJB
    private SessionZaduzenjeLocal sbZaduzenje;
    
    @ManagedProperty(value = "#{mBSesija}")
    MBSesija mbSesija;
    
    public MBPrikazZaduzenja() {
    }
    
    @PostConstruct
    public void inicijalizujPodatke(){
        izabraniZaposlen = mbSesija.getIzabraniZaposlen();
        listaZaduzenja = sbZaduzenje.vratiZaduzenja(izabraniZaposlen);
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

    public List<Zaduzenje> getListaZaduzenja() {
        return listaZaduzenja;
    }

    public void setListaZaduzenja(List<Zaduzenje> listaZaduzenja) {
        this.listaZaduzenja = listaZaduzenja;
    }

    public SessionZaduzenjeLocal getSbZaduzenje() {
        return sbZaduzenje;
    }

    public void setSbZaduzenje(SessionZaduzenjeLocal sbZaduzenje) {
        this.sbZaduzenje = sbZaduzenje;
    }

    public MBSesija getMbSesija() {
        return mbSesija;
    }

    public void setMbSesija(MBSesija mbSesija) {
        this.mbSesija = mbSesija;
    }
    
    public void postaviIzabranoZaduzenje(){
        mbSesija.setIzabranoZaduzenje(izabranoZaduzenje);
        System.out.println(izabranoZaduzenje.getStavkazaduzenjaList());
    }
    
    public void listaVracenihZaduzenja(){
        listaZaduzenja = sbZaduzenje.vratiVracenaZaduzenja(izabraniZaposlen);
    }
    
    public void listaNevracenihZaduzenja(){
        listaZaduzenja = sbZaduzenje.vratiNevracenaZaduzenja(izabraniZaposlen);
    }
    
    public String prikaziUnosZaduzenja(){
        if(izabraniZaposlen != null)
            return "/radSaZaduzenjima/unosZaduzenja.xhtml";
        else
            return "";
    }
    
    public void razduziZaduzenje(){
        try {
            izabranoZaduzenje = mbSesija.getIzabranoZaduzenje();
            
            if(izabranoZaduzenje.getVraceno() == false){
                izabranoZaduzenje.setDatumRazduzenja(new Date());
                izabranoZaduzenje.setRazduzio(mbSesija.getUlogovaniPlaner());
                izabranoZaduzenje.setVraceno(true);
                sbZaduzenje.sacuvajZaduzenje(izabranoZaduzenje);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Uspešno razduživanje zaduženja", null));
            }else{
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Zaduženje je već razduženo", null));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Neuspešno razduživanje zaduženja", null));
        }
    }
    
    
}
