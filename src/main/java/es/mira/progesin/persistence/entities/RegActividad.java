package es.mira.progesin.persistence.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@EqualsAndHashCode()
@Builder
@ToString
@Getter
@Setter
@Entity
@Table(name = "REG_ACTIVIDAD", schema = "public")
public class RegActividad implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "REG_ACTIVIDAD", length = 5)
	private Integer idRegActividad;

	@Column(name = "NOMBRE_SECCION", length = 50)
	private String nombreSeccion;

	@Column(name = "DESCRIPCION", length = 4000)
	private String descripcion;

	@Column(name = "FECHA_REGISTRO")
	private Date fechaAlta;

	@Column(name = "USUARIO_BAJA", length = 50)
	private String usernameBaja;

	@Column(name = "TIPO_REG_ACTIVIDAD", length = 50)
	private String tipoRegActividad;
	
	@Column(name = "FECHA_BAJA")
	private Date fechaBaja;

	@Column(name = "USUARIO_REGISTRO", length = 50)
	private String usernameRegActividad;
}
