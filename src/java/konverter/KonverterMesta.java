/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package konverter;

import domen.Mesto;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import session.zaposlen.SessionZaposlenLocal;

/**
 *
 * @author Bojan
 */
@FacesConverter(value = "konverterMesta")
public class KonverterMesta implements Converter{

    @EJB
    SessionZaposlenLocal sbZap;
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Mesto mesto = null;
        if (value != null && !value.isEmpty()){
            try {
                List<Mesto> lm = sbZap.vratiMesta();
                for(Mesto m : lm){
                    if(m.getNaziv().equals(value))
                        mesto = m;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return mesto;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if(value instanceof Mesto){
            Mesto m = (Mesto) value;
            return m.getNaziv();
        }
        return null;
    }
    
}
