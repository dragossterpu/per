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

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
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
@Table(name = "SUGERENCIA")
public class Sugerencia implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "ID_SUGERENCIA", length = 5)
	private Integer idSugerencia;

	@Column(name = "MODULO", length = 50)
	private String modulo;

	@Column(name = "DESCRIPCION", length = 4000)
	private String descripcion;

	@Column(name = "FECHA_REGISTRO")
	@CreatedDate
	private Date fechaRegistro;

	@Column(name = "USUARIO_REGISTRO", length = 50)
	@CreatedBy
	private String usuarioRegistro;

	@Column(name = "FECHA_CONTESTACION")
	private Date fechaContestacion;

	@Column(name = "USUARIO_CONTESTACION", length = 50)
	private String usuarioContestacion;

}
