package es.mira.progesin.persistence.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

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
@Table(name = "REG_ACTIVIDAD")
public class RegistroActividad implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_REGISTRO")
	@SequenceGenerator(name = "SEQ_REGISTRO", sequenceName = "SEQ_REGISTRO", allocationSize = 1)
	@Column(name = "REG_ACTIVIDAD", length = 5)
	private Long idRegActividad;

	@Column(name = "NOMBRE_SECCION", length = 50)
	private String nombreSeccion;

	@Lob
	@Column(name = "DESCRIPCION")
	private String descripcion;

	@CreatedDate
	@Column(name = "FECHA_ALTA")
	private Date fechaAlta;

	@CreatedBy
	@Column(name = "USUARIO_REGISTRO")
	private String usernameRegActividad;

	@Column(name = "TIPO_REG_ACTIVIDAD")
	private String tipoRegActividad;

}
