package es.mira.progesin.persistence.entities.informes;

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
    @ManyToOne
    @Id
    @JoinColumn(name = "informe_id", foreignKey = @ForeignKey(name = "fk_informe"), insertable = false, updatable = false, nullable = false)
    private Informe informe;
    
    /**
     * Subarea respondida.
     */
    @ManyToOne
    @Id
    @JoinColumn(name = "subarea_id", foreignKey = @ForeignKey(name = "fk_subarea_inf"), insertable = false, updatable = false, nullable = false)
    private SubareaInforme subarea;
    
    /**
     * Texto de la respuesta del subárea del informe en código HTML con estilos de PrimeFaces.
     */
    @Lob
    @Column(name = "texto", nullable = false)
    private byte[] texto;
    
    /**
     * Conclusiones de la respuesta del subárea del informe en código HTML con estilos de PrimeFaces.
     */
    @Lob
    @Column(name = "conclusiones")
    private byte[] conclusiones;
}
