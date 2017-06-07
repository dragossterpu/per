package es.mira.progesin.persistence.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Entidad para la subárea de un informe.
 * 
 * @author EZENTIS
 *
 */
@EqualsAndHashCode(of = { "informe", "subarea" })
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
@Entity
@IdClass(RespuestaInformeId.class)
@Table(name = "RESPUESTAS_INFORME")
public class RespuestaInforme implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Informe al que pertenece a respuesta.
     */
    @Id
    @ManyToOne
    @JoinColumn(name = "informe_id", foreignKey = @ForeignKey(name = "fk_informe"))
    private Informe informe;
    
    /**
     * Subarea respondida.
     */
    @Id
    @ManyToOne
    @JoinColumn(name = "subarea_id", foreignKey = @ForeignKey(name = "fk_subarea_inf"))
    private SubareaInforme subarea;
    
    /**
     * Texto de la respuesta en código HTML con estilos de PrimeFaces.
     */
    @Lob
    @Column(name = "respuesta")
    private byte[] respuesta;
}
