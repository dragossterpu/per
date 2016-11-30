package es.mira.progesin.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import es.mira.progesin.persistence.entities.cuestionarios.RespuestaCuestionario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
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
	@JoinColumn(name = "id_respuesta")
	private RespuestaCuestionario respuesta;

}
