package es.mira.progesin.persistence.entities.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Enumerado para la definición de contentType de ficheros.
 * 
 * @author EZENTIS
 *
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("javadoc")
public enum ContentTypeEnum {
    /**
     * ContentType para la generación de un archivo DOCX.
     */
    DOCX("application/vnd.openxmlformats-officedocument.wordprocessingml.document"),
    /**
     * ContentType para la generación de un archivo PDF.
     */
    PDF("application/pdf");
    /**
     * Descripción del contentType.
     */
    String contentType;
}
