/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mb;

import domen.Emisija;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import session.emisija.SessionEmisijaLocal;

/**
 *
 * @author Bojan
 */
@ManagedBean
@RequestScoped
public class MBEmisija {

    private Emisija e;
    private List<Emisija> listaEmisija;
    
    @EJB
    private SessionEmisijaLocal sbEmisija;

    public MBEmisija() {
        e = new Emisija();
        listaEmisija = new ArrayList<>();
    }
    
    @PostConstruct
    public void inicijalizujPodatke(){
        listaEmisija = sbEmisija.vratiEmisije();
    }
    
    
    public Emisija getE() {
        return e;
    }

    public void setE(Emisija e) {
        this.e = e;
    }

    public SessionEmisijaLocal getSbEmisija() {
        return sbEmisija;
    }

    public void setSbEmisija(SessionEmisijaLocal sbEmisija) {
        this.sbEmisija = sbEmisija;
    }

    public List<Emisija> getListaEmisija() {
        return listaEmisija;
    }

    public void setListaEmisija(List<Emisija> listaEmisija) {
        this.listaEmisija = listaEmisija;
    }

    public void sacuvajEmisiju(){
        try {
            e.setEmisijaID(vratiEmisijaID());
            sbEmisija.sacuvajEmisiju(e);
            e = new Emisija();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Uuspešno čuvanje emisije", null));
        } catch (Exception ex) {
           ex.printStackTrace();
           FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Neuspešno čuvanje emisije", null));
        }
    }
    
    public int vratiEmisijaID(){
        return sbEmisija.vratiEmisijaID()+1;
    }
    
}
