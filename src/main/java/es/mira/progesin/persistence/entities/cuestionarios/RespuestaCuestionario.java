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

import es.mira.progesin.model.DatosTablaGenerica;
import es.mira.progesin.persistence.entities.gd.Documento;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author EZENTIS
 * 
 * Entidad para la respuesta asociada a un cuestionario
 *
 */
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
    
    @EmbeddedId
    RespuestaCuestionarioId respuestaId;
    
    @OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE,
            CascadeType.PERSIST }/* , orphanRemoval = true */)
    @JoinColumns(value = { @JoinColumn(name = "RESPUESTA_ID_CUEST_ENVIADO"),
            @JoinColumn(name = "RESPUESTA_ID_PREGUNTA") })
    @OrderBy("id")
    private List<DatosTablaGenerica> respuestaTablaMatriz;
    
    @Column(name = "respuesta_texto", length = 2000)
    private String respuestaTexto;
    
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "respuestas_cuest_docs", joinColumns = { @JoinColumn(name = "id_cuestionario_enviado"),
            @JoinColumn(name = "id_pregunta") }, inverseJoinColumns = @JoinColumn(name = "id_documento"))
    private List<Documento> documentos;
    
    @Column
    private String usernameCumplimentacion;
    
    @Column
    private Date fechaCumplimentacion;
    
    @Column
    private String usernameValidacion;
    
    @Column
    private Date fechaValidacion;
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((respuestaId == null) ? 0 : respuestaId.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        RespuestaCuestionario other = (RespuestaCuestionario) obj;
        if (respuestaId == null) {
            if (other.respuestaId != null) {
                return false;
            }
        } else if (!respuestaId.getCuestionarioEnviado().getId()
                .equals(other.respuestaId.getCuestionarioEnviado().getId())
                && !respuestaId.getPregunta().getId().equals(other.respuestaId.getPregunta().getId())) {
            return false;
        }
        return true;
    }
    
}
