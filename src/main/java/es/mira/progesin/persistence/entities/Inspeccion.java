package es.mira.progesin.persistence.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioEnviado;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode()
@Builder
@ToString
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "INSPECCIONES", schema = "public")
public class Inspeccion implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "ID", length = 5)
	private Long id;

	@Column(name = "numero", length = 100, nullable = false)
	private String numero;

	@Column(name = "tipoInspeccion")
	private String tipoInspeccion;

	// Lo comento de momento para poder crear inspecciones
	// @ManyToOne
	// @JoinColumn(name = "id_equipo")
	// private Equipo equipo;

	@OneToOne(mappedBy = "inspeccion", fetch = FetchType.LAZY)
	private CuestionarioEnviado cuestionarioEnviado;

	@CreatedDate
	@Column(name = "fechaCreacion", nullable = false)
	private Date fechaCreacion;

	@CreatedBy
	@Column(name = "usernameCreacion", nullable = false)
	private String usernameCreacion;

}
