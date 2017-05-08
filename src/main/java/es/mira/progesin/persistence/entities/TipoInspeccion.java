package es.mira.progesin.persistence.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, of = "codigo")
@Builder
@ToString
@Getter
@Setter

@Entity
@Table(name = "TIPOS_INSPECCION")
@NamedQuery(name = "TipoInspeccion.findAll", query = "SELECT t FROM TipoInspeccion t")
public class TipoInspeccion extends AbstractEntity implements Serializable, Comparable<TipoInspeccion> {
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(length = 10)
    private String codigo;
    
    @Column(length = 100)
    private String descripcion;
    
    @Override
    public int compareTo(TipoInspeccion o) {
        return this.descripcion.compareTo(o.descripcion);
    }
}
