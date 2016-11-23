package es.mira.progesin.persistence.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import es.mira.progesin.persistence.entities.enums.AmbitoInspeccionEnum;
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
@EntityListeners(AuditingEntityListener.class)
@Table(name = "INSPECCIONES")
public class Inspeccion implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "seq_inspeccion", sequenceName = "seq_inspeccion", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_inspeccion")
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

	// @OneToOne(mappedBy = "inspeccion", fetch = FetchType.LAZY)
	// private CuestionarioEnvio cuestionarioEnviado;

	@Column(name = "nombre_unidad", length = 100)
	private String nombreUnidad;

	@Column(name = "ambito", length = 10)
	@Enumerated(EnumType.STRING)
	private AmbitoInspeccionEnum ambito;

	@CreatedDate
	@Column(name = "fechaCreacion", nullable = false)
	private Date fechaCreacion;

	@CreatedBy
	@Column(name = "usernameCreacion", nullable = false)
	private String usernameCreacion;

	@Column(name = "fecha_finalizacion")
	private Date fechaFinalizacion;

	@Column(name = "username_finalizacion")
	private String usernameFinalizacion;

}
