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
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * POJO para los Cuerpos del estado
 * @author sperezp
 *
 */

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode()
@Builder
@ToString
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "CUERPOSESTADO", schema = "public")
public class CuerpoEstado implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "ID", length = 2)
	private Integer id;

	@Column(name = "DESCRIPCION", length = 100, nullable = false)
	private String descripcion;

	@CreatedDate
	@Column(name = "fecha_alta", nullable = false)
	private Date fechaAlta;

	@CreatedBy
	@Column(name = "username_alta", length = 12, nullable = false)
	private String usernameAlta;

	@LastModifiedDate
	@Column(name = "fecha_modif")
	private Date fechaModif;

	@LastModifiedBy
	@Column(name = "username_modif", length = 12)
	private String usernameModif;

	@Column(name = "fecha_baja")
	private Date fechaBaja;

	@Column(name = "username_baja", length = 12)
	private String usernameBaja;
}
