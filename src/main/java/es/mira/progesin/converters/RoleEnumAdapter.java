package es.mira.progesin.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import es.mira.progesin.persistence.entities.enums.RoleEnum;

@Converter(autoApply = true)
public class RoleEnumAdapter implements AttributeConverter<RoleEnum, String> {

        @Override
        public String convertToDatabaseColumn(RoleEnum role) {
            if (role != null) {
                return role.name();
            }
            return null;
        }

        @Override
        public RoleEnum convertToEntityAttribute(String dbData) {
            return RoleEnum.valueOf(dbData);
        }

}
