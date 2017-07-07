package es.mira.progesin.persistence.entities.cuestionarios;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import es.mira.progesin.persistence.entities.DatosTablaGenerica;
import es.mira.progesin.persistence.entities.gd.Documento;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Entidad para la respuesta asociada a un cuestionario.
 * 
 * @author EZENTIS
 *
 */
@EqualsAndHashCode(of = "respuestaId")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
@Entity
@Table(name = "respuestascuestionario")
@NamedEntityGraph(name = "RespuestaCuestionario.documentos", attributeNodes = @NamedAttributeNode("documentos"))
public class RespuestaCuestionario implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
     * ID de la respuesta.
     */
    @EmbeddedId
    private RespuestaCuestionarioId respuestaId;
    
    /**
     * Lista con las respuestas de tipo tabla o matriz.
     */
    @OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    @JoinColumns(value = { @JoinColumn(name = "RESPUESTA_ID_CUEST_ENVIADO"),
            @JoinColumn(name = "RESPUESTA_ID_PREGUNTA") })
    @OrderBy("id")
    private List<DatosTablaGenerica> respuestaTablaMatriz;
    
    /**
     * Lista con las respuestas de tipo texto.
     */
    @Column(name = "respuesta_texto", length = 2000)
    private String respuestaTexto;
    
    /**
     * Lista con los documentos asociados a la respuesta.
     */
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "respuestas_cuest_docs", joinColumns = { @JoinColumn(name = "id_cuestionario_enviado"),
            @JoinColumn(name = "id_pregunta") }, inverseJoinColumns = @JoinColumn(name = "id_documento"))
    private List<Documento> documentos;
    
    /**
     * Login de usuario que valida la respuesta.
     */
    @Column
    private String usernameValidacion;
    
    /**
     * Fecha de validación.
     */
    @Column
    private Date fechaValidacion;
    
}
