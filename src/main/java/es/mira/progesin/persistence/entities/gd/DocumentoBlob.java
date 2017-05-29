package es.mira.progesin.persistence.entities.gd;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * Entidad para el almacenamiento en base de datos de los documentos en formato blob.
 * 
 * @author EZENTIS
 * @see es.mira.progesin.persistence.entities.gd.Documento
 */

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode()
@Builder
@ToString
@Getter
@Setter
@Entity
@Table(name = "documentosBlob")
public class DocumentoBlob implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
     * Identificador de la entidad, generada por medio de una secuencia.
     */
    @Id
    @SequenceGenerator(name = "seq_documentosblob", sequenceName = "seq_documentosblob", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_documentosblob")
    @Column(name = "id", nullable = false)
    private Long id;
    
    /**
     * Nombre del fichero.
     */
    @Column(name = "nombreFichero")
    private String nombreFichero;
    
    /**
     * Array de bytes con el contenido del fichero.
     */
    @Column(name = "fichero")
    @Lob
    private byte[] fichero;
    
}
