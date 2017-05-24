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

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Entidad para la subarea de un informe
 * 
 * @author EZENTIS
 *
 */
@Builder
@ToString
@Getter
@Setter
@Entity
@IdClass(RespuestaInformeId.class)
@Table(name = "RESPUESTAS_INFORME")
public class RespuestaInforme implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @ManyToOne
    @JoinColumn(name = "informe_id", foreignKey = @ForeignKey(name = "fk_informe"))
    private Informe informe;
    
    @Id
    @ManyToOne
    @JoinColumn(name = "subarea_id", foreignKey = @ForeignKey(name = "fk_subarea_inf"))
    private SubareaInforme subarea;
    
    @Lob
    @Column(name = "respuesta")
    private byte[] respuesta;
}
