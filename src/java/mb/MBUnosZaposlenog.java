/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mb;

import domen.Mesto;
import domen.Zaposlen;
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
public class MBUnosZaposlenog {
    
    private Zaposlen zap;
    public int modRada;
    public static final int UNOS = 1;
    public static final int IZMENA = 2;
    
    @EJB
    private SessionZaposlenLocal sbZaposlen;

    @ManagedProperty(value = "#{mBSesija}")
    MBSesija mbSesija;

    public MBUnosZaposlenog() {
    }
    
    @PostConstruct
    public void inicijalizujPodatke(){
        zap = mbSesija.getIzabraniZaposlen();
        
        if(zap == null){
            zap = new Zaposlen();
            modRada = UNOS;
        }else{
            modRada = IZMENA;
        }
    }

    public Zaposlen getZap() {
        return zap;
    }

    public void setZap(Zaposlen zap) {
        this.zap = zap;
    }

    public SessionZaposlenLocal getSbZaposlen() {
        return sbZaposlen;
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

    
    public void sacuvajZaposlenog(){
        try {
            if(modRada == UNOS){
                zap.setZaposlenID(vratiZaposlenID());
            }
            sbZaposlen.sacuvajZaposlenog(zap);
            zap = new Zaposlen();
            if(modRada == UNOS){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Uspešno čuvanje zaposlenog", null));
            }else{
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Uspešna izmena zaposlenog", null));
            }
        } catch (Exception ex) {
           ex.printStackTrace();
           FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Neuspešno čuvanje zaposlenog", null));
        }
    }
    
    public List<Mesto> vratiMesta(){
        return sbZaposlen.vratiMesta();
    }
    
    public int vratiZaposlenID(){
        return sbZaposlen.vratiZaposlenID()+1;
    }
    
}
