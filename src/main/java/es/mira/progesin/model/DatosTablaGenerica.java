package es.mira.progesin.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import es.mira.progesin.persistence.entities.cuestionarios.RespuestaCuestionario;
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

	@ManyToOne
	private RespuestaCuestionario respuesta;

	@Override
	public String toString() {
		return "DatosTablaGenerica [nombreFila=" + nombreFila + ", campo1=" + campo1 + ", campo2=" + campo2
				+ ", campo3=" + campo3 + ", campo4=" + campo4 + ", campo5=" + campo5 + ", campo6=" + campo6
				+ ", campo7=" + campo7 + ", campo8=" + campo8 + ", campo9=" + campo9 + ", campo10=" + campo10 + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((campo1 == null) ? 0 : campo1.hashCode());
		result = prime * result + ((campo10 == null) ? 0 : campo10.hashCode());
		result = prime * result + ((campo2 == null) ? 0 : campo2.hashCode());
		result = prime * result + ((campo3 == null) ? 0 : campo3.hashCode());
		result = prime * result + ((campo4 == null) ? 0 : campo4.hashCode());
		result = prime * result + ((campo5 == null) ? 0 : campo5.hashCode());
		result = prime * result + ((campo6 == null) ? 0 : campo6.hashCode());
		result = prime * result + ((campo7 == null) ? 0 : campo7.hashCode());
		result = prime * result + ((campo8 == null) ? 0 : campo8.hashCode());
		result = prime * result + ((campo9 == null) ? 0 : campo9.hashCode());
		result = prime * result + ((nombreFila == null) ? 0 : nombreFila.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DatosTablaGenerica other = (DatosTablaGenerica) obj;
		if (campo1 == null) {
			if (other.campo1 != null)
				return false;
		}
		else if (!campo1.equals(other.campo1))
			return false;
		if (campo10 == null) {
			if (other.campo10 != null)
				return false;
		}
		else if (!campo10.equals(other.campo10))
			return false;
		if (campo2 == null) {
			if (other.campo2 != null)
				return false;
		}
		else if (!campo2.equals(other.campo2))
			return false;
		if (campo3 == null) {
			if (other.campo3 != null)
				return false;
		}
		else if (!campo3.equals(other.campo3))
			return false;
		if (campo4 == null) {
			if (other.campo4 != null)
				return false;
		}
		else if (!campo4.equals(other.campo4))
			return false;
		if (campo5 == null) {
			if (other.campo5 != null)
				return false;
		}
		else if (!campo5.equals(other.campo5))
			return false;
		if (campo6 == null) {
			if (other.campo6 != null)
				return false;
		}
		else if (!campo6.equals(other.campo6))
			return false;
		if (campo7 == null) {
			if (other.campo7 != null)
				return false;
		}
		else if (!campo7.equals(other.campo7))
			return false;
		if (campo8 == null) {
			if (other.campo8 != null)
				return false;
		}
		else if (!campo8.equals(other.campo8))
			return false;
		if (campo9 == null) {
			if (other.campo9 != null)
				return false;
		}
		else if (!campo9.equals(other.campo9))
			return false;
		if (nombreFila == null) {
			if (other.nombreFila != null)
				return false;
		}
		else if (!nombreFila.equals(other.nombreFila))
			return false;
		return true;
	}

}
