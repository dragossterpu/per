package es.mira.progesin.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import es.mira.progesin.persistence.entities.enums.RoleEnum;

/**
 * Adaptador para el enum RoleEnum.
 * 
 * @see es.mira.progesin.persistence.entities.enums.RoleElum
 * 
 * @author EZENTIS
 *
 */
@Converter(autoApply = true)
public class RoleEnumAdapter implements AttributeConverter<RoleEnum, String> {
    
    /**
     * Método que recibe un valor RoleEnum y devuelve su nombre.
     */
    @Override
    public String convertToDatabaseColumn(RoleEnum role) {
        if (role != null) {
            return role.name();
        }
        return null;
    }
    
    /**
     * Método que decibe un nombre y devuelve su correspondiente RoleEnum.
     */
    @Override
    public RoleEnum convertToEntityAttribute(String dbData) {
        return RoleEnum.valueOf(dbData);
    }
    
}
