package es.mira.progesin.persistence.entities;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

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
