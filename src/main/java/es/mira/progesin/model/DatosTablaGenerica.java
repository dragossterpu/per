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
    
    private String campo1;
    
    private String campo2;
    
    private String campo3;
    
    private String campo4;
    
    private String campo5;
    
    private String campo6;
    
    private String campo7;
    
    private String campo8;
    
    private String campo9;
    
    private String campo10;
    
    // @ManyToOne
    // @JoinColumns(value = { @JoinColumn(name = "RESPUESTA_ID_CUEST_ENVIADO"),
    // @JoinColumn(name = "RESPUESTA_ID_PREGUNTA") })
    // private RespuestaCuestionario respuesta;
    
    @Override
    public String toString() {
        return "DatosTablaGenerica [ID = " + id + ", nombreFila=" + nombreFila + ", campo1=" + campo1 + ", campo2="
                + campo2 + ", campo3=" + campo3 + ", campo4=" + campo4 + ", campo5=" + campo5 + ", campo6=" + campo6
                + ", campo7=" + campo7 + ", campo8=" + campo8 + ", campo9=" + campo9 + ", campo10=" + campo10 + "]";
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
