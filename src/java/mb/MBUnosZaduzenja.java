/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mb;

import domen.Emisija;
import domen.Oprema;
import domen.Planer;
import domen.Stavkazaduzenja;
import domen.StavkazaduzenjaPK;
import domen.Zaduzenje;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import session.zaduzenje.SessionZaduzenjeLocal;

/**
 *
 * @author Bojan
 */
@ManagedBean
@ViewScoped
public class MBUnosZaduzenja {

    private Zaduzenje zad;
    private Stavkazaduzenja izabranaStavka;

    
    @EJB
    private SessionZaduzenjeLocal sbZaduzenje;
    
    @ManagedProperty(value = "#{mBSesija}")
    MBSesija mbSesija;
    
    public MBUnosZaduzenja() {
    }
    
    @PostConstruct
    public void inicijalizujPodatke(){
        Planer p = new Planer();
        p.setPlanerID(1);
        
        zad = new Zaduzenje();
        zad.setZaduzenjeID(sbZaduzenje.vratiZaduzenjeID()+1);
        zad.setDatumZaduzenja(new Date());
        zad.setDatumRazduzenja(null);
        zad.setVraceno(false);
        zad.setZaposlenID(mbSesija.getIzabraniZaposlen());
        zad.setZaduzio(mbSesija.getUlogovaniPlaner());
        zad.setRazduzio(p);
        zad.setStavkazaduzenjaList(new ArrayList<Stavkazaduzenja>());
        
    }
    

    public Zaduzenje getZad() {
        return zad;
    }

    public void setZad(Zaduzenje zad) {
        this.zad = zad;
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

    public Stavkazaduzenja getIzabranaStavka() {
        return izabranaStavka;
    }

    public void setIzabranaStavka(Stavkazaduzenja izabranaStavka) {
        this.izabranaStavka = izabranaStavka;
    }

    
    public List<Oprema> vratiOpremu(){
        return sbZaduzenje.vratiOpremu();
    }
    
    public List<Emisija> vratiEmisije(){
        return sbZaduzenje.vratiEmisije();
    }
    
    public void pokreniDodavanjeStavke(){
        izabranaStavka = new Stavkazaduzenja();
        izabranaStavka.setZaduzenje(zad);
        izabranaStavka.setStavkazaduzenjaPK(new StavkazaduzenjaPK(zad.getZaduzenjeID(), zad.getStavkazaduzenjaList().size()+1));
    }
    
    public void dodajStavku(){
        izabranaStavka.setZaduzenje(zad);
        izabranaStavka.setStavkazaduzenjaPK(new StavkazaduzenjaPK(zad.getZaduzenjeID(), zad.getStavkazaduzenjaList().size()+1));
        zad.getStavkazaduzenjaList().add(izabranaStavka);

        izabranaStavka = new Stavkazaduzenja();
        izabranaStavka.setZaduzenje(zad);
        izabranaStavka.setStavkazaduzenjaPK(new StavkazaduzenjaPK(zad.getZaduzenjeID(), zad.getStavkazaduzenjaList().size()+1));
    }
    
    public void obrisiStavku(){
        int rb = izabranaStavka.getStavkazaduzenjaPK().getRb()-1;
        System.out.println(rb);
        izabranaStavka = zad.getStavkazaduzenjaList().get(rb);
        System.out.println(izabranaStavka);
        System.out.println(izabranaStavka.getOpermaID());
        zad.getStavkazaduzenjaList().remove(rb);
    }
    
    public String sacuvajZaduzenje(){
        try {            
            sbZaduzenje.sacuvajZaduzenje(zad);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Uspešno čuvanje zaduženja", null));
            return "/radSaZaduzenjima/prikazZaduzenja.xhtml";
        } catch (Exception ex) {
           ex.printStackTrace();
           FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Neuspešno čuvanje zaduženja", null));
           return "";
        }
    }
}
