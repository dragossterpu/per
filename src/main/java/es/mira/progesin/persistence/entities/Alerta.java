package es.mira.progesin.persistence.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "ALERTAS", schema = "public")
public class Alerta implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "ID_ALERTA", length = 5)
	private Integer idAlerta;

	@Column(name = "NOMBRE_SECCION", length = 50)
	private String nombreSeccion;

	@Column(name = "DESCRIPCION", length = 4000)
	private String descripcion;

	@Column(name = "FECHA_REGISTRO")
	private Date fechaAlta;

	@Column(name = "USUARIO_REGISTRO", length = 50)
	private String usernameAlerta;

	@Column(name = "TIPO_ALERTA", length = 20)
	private String tipoAlerta;

}
