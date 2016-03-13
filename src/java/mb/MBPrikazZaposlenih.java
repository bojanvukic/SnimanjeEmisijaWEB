/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mb;

import domen.Zaposlen;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import session.zaposlen.SessionZaposlenLocal;

/**
 *
 * @author Bojan
 */
@ManagedBean
@RequestScoped
public class MBPrikazZaposlenih {

    private List<Zaposlen> listaZaposlenih;
    private Zaposlen izabraniZaposlen;
    
    @EJB
    private SessionZaposlenLocal sbZaposlen;
    
    @ManagedProperty(value = "#{mBSesija}")
    MBSesija mbSesija;
    
    public MBPrikazZaposlenih() {
        listaZaposlenih = new ArrayList<>();
    }
    
    @PostConstruct
    public void inicijalizujPodatke(){
        listaZaposlenih = sbZaposlen.vratiZaposlene();
    }

    
    public List<Zaposlen> getListaZaposlenih() {
        return listaZaposlenih;
    }

    public void setListaZaposlenih(List<Zaposlen> listaZaposlenih) {
        this.listaZaposlenih = listaZaposlenih;
    }

    public SessionZaposlenLocal getSbZaposlen() {
        return sbZaposlen;
    }
    
    public Zaposlen getIzabraniZaposlen() {
        return izabraniZaposlen;
    }

    public void setIzabraniZaposlen(Zaposlen izabraniZaposlen) {
        this.izabraniZaposlen = izabraniZaposlen;
    }

    public void setSbZaposlen(SessionZaposlenLocal sbZaposlen) {
        this.sbZaposlen = sbZaposlen;
    }

    public MBSesija getMbSesija() {
        return mbSesija;
    }

    public void setMbSesija(MBSesija mbSesija) {
        this.mbSesija = mbSesija;
    }
    
    public void postaviIzabranogZaposlenog(){
        if(izabraniZaposlen != null){
            mbSesija.setIzabraniZaposlen(izabraniZaposlen);
        }else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Niste izabrali zaposlenog", null));
        }
    }
    
    public void ukloniIzabranogZaposlenog(){
        mbSesija.setIzabraniZaposlen(null);
    }
    
    public void obrisiZaposlenog(){
        try {
            sbZaposlen.obrisiZaposlenog(izabraniZaposlen.getZaposlenID());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Uspešno brisanje zaposlenog", null));
        } catch (Exception ex) {
           ex.printStackTrace();
           FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Niste izabrali zaposlenog", null));
        }
    }
    
    public String prikaziZaduzenja(){
        if(izabraniZaposlen != null){
            mbSesija.setIzabraniZaposlen(izabraniZaposlen);
            return "/radSaZaduzenjima/prikazZaduzenja.xhtml";
        }else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Niste izabrali zaposlenog", null));
            return "";
        }
    }
}
