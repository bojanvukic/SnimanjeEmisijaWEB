/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package konverter;

import domen.Emisija;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import session.zaduzenje.SessionZaduzenjeLocal;

/**
 *
 * @author Bojan
 */
@FacesConverter(value = "konverterEmisija")
public class KonverterEmisija implements Converter{

    @EJB
    SessionZaduzenjeLocal sbZad;
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Emisija emisija = null;
        if (value != null && !value.isEmpty()){
            try {
                List<Emisija> le = sbZad.vratiEmisije();
                for(Emisija e : le){
                    if(e.getNazivEmisije().equals(value))
                        emisija = e;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return emisija;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if(value instanceof Emisija){
            Emisija e = (Emisija) value;
            return e.getNazivEmisije();
        }
        return null;
    }
    
}
