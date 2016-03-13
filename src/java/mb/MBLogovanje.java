/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mb;

import domen.Planer;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import session.planer.SessionPlanerLocal;

/**
 *
 * @author Bojan
 */
@ManagedBean
@RequestScoped
public class MBLogovanje {

    private Planer trenutniPlaner;
    
    @ManagedProperty(value = "#{mBSesija}")
    MBSesija mbSesija;
    
    @EJB
    private SessionPlanerLocal sbPlaner;
    
    public MBLogovanje() {
        trenutniPlaner = new Planer();
    }

    public Planer getTrenutniPlaner() {
        return trenutniPlaner;
    }

    public void setTrenutniPlaner(Planer trenutniPlaner) {
        this.trenutniPlaner = trenutniPlaner;
    }

    public MBSesija getMbSesija() {
        return mbSesija;
    }

    public void setMbSesija(MBSesija mbSesija) {
        this.mbSesija = mbSesija;
    }
    
    public String prijaviPlanera(){
        try {
            trenutniPlaner = sbPlaner.login(trenutniPlaner.getKorisnickoIme(), trenutniPlaner.getKorisnickaSifra());
            mbSesija.setUlogovaniPlaner(trenutniPlaner);
            return "pocetna/pocetna.xhtml";
        } catch (Exception ex) {
           ex.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Neuspešno prijavljivanje planera", null));
        }
        return null;
    }
    
    public String odjaviPlanera(){
        mbSesija.setUlogovaniPlaner(null);
        return null;
    }
    
    public boolean daLiJePlanerUlogovan(){
        if(mbSesija.getUlogovaniPlaner() == null)
            return false;
        return true;
    }
    
    
    
}
