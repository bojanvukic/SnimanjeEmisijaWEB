/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package konverter;

import domen.Oprema;
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
@FacesConverter(value = "konverterOpreme")
public class KonverterOpreme implements Converter{

    @EJB
    SessionZaduzenjeLocal sbZad;
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Oprema oprema = null;
        if (value != null && !value.isEmpty()){
            try {
                List<Oprema> lo = sbZad.vratiOpremu();
                for(Oprema o : lo){
                    if(o.getNazivOpreme().equals(value))
                        oprema = o;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return oprema;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if(value instanceof Oprema){
            Oprema o = (Oprema) value;
            return o.getNazivOpreme();
        }
        return null;
    }
    
}
