package es.mira.progesin.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "respuesta_datos_tabla")
public class DatosTablaGenerica implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @SequenceGenerator(name = "seq_respuestatabla", sequenceName = "seq_respuestatabla", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_respuestatabla")
    @Column(name = "id", nullable = false)
    private Long id;
    
    // Para cuando haya que dibujar matrices
    private String nombreFila;
    
    private String campo01;
    
    private String campo02;
    
    private String campo03;
    
    private String campo04;
    
    private String campo05;
    
    private String campo06;
    
    private String campo07;
    
    private String campo08;
    
    private String campo09;
    
    private String campo10;
    
    private String campo11;
    
    private String campo12;
    
    private String campo13;
    
    private String campo14;
    
    private String campo15;
    
    private String campo16;
    
    private String campo17;
    
    private String campo18;
    
    private String campo19;
    
    private String campo20;
    
    // @ManyToOne
    // @JoinColumns(value = { @JoinColumn(name = "RESPUESTA_ID_CUEST_ENVIADO"),
    // @JoinColumn(name = "RESPUESTA_ID_PREGUNTA") })
    // private RespuestaCuestionario respuesta;
    
    @Override
    public String toString() {
        return "DatosTablaGenerica [id=" + id + ", nombreFila=" + nombreFila + ", campo01=" + campo01 + ", campo02="
                + campo02 + ", campo03=" + campo03 + ", campo04=" + campo04 + ", campo05=" + campo05 + ", campo06="
                + campo06 + ", campo07=" + campo07 + ", campo08=" + campo08 + ", campo09=" + campo09 + ", campo10="
                + campo10 + ", campo11=" + campo11 + ", campo12=" + campo12 + ", campo13=" + campo13 + ", campo14="
                + campo14 + ", campo15=" + campo15 + ", campo16=" + campo16 + ", campo17=" + campo17 + ", campo18="
                + campo18 + ", campo19=" + campo19 + ", campo20=" + campo20 + "]";
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
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
        DatosTablaGenerica other = (DatosTablaGenerica) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }
    
}
