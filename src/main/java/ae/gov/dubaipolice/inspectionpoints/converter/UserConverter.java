/*package ae.gov.dubaipolice.inspectionpoints.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.inject.Named;

import ae.gov.dubaipolice.base.entities.DpEmpMvGrp;
import ae.gov.dubaipolice.inspectionpoints.repositories.DpGrpUserRepository;
@FacesConverter("userConverter")
@Named
public class UserConverter implements Converter{
	@Inject
	DpGrpUserRepository reposit;
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		try{
			Long id = Long.parseLong(value);
			DpEmpMvGrp entity = reposit.findOne(id);
			return entity;
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {
		if(value == null){
			return null;
		}
		if(value instanceof DpEmpMvGrp){
			return ((DpEmpMvGrp)value).getPersonId()+"";
		} else {
            throw new IllegalArgumentException("object " + value + " is of type " + value.getClass().getName() + "; expected type: " + DpEmpMvGrp.class.getName());
        }
	}

}
*/