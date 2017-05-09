package es.mira.progesin.persistence.entities.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Definición de contentType de ficheros
 * 
 * @author EZENTIS
 *
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("javadoc")
public enum ContentTypeEnum {
    DOCX("application/vnd.openxmlformats-officedocument.wordprocessingml.document"), PDF("application/pdf");
    
    String contentType;
}
