package es.mira.progesin.persistence.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
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
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode()
@Builder
@ToString
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Entity
@Table(name = "EQUIPO")

public class Equipo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "seq_equipo", sequenceName = "seq_equipo", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_equipo")
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "nombreEquipo", length = 100, nullable = false)
	private String nombreEquipo;

	@ManyToOne
	@JoinColumn(name = "idTipoEquipo")
	private TipoEquipo tipoEquipo;

	@Column(name = "jefeEquipo", length = 100, nullable = false)
	private String jefeEquipo;

	@CreatedDate
	@Column(name = "fecha_alta", nullable = false)
	private Date fechaAlta;

	@Column(name = "fecha_baja")
	private Date fechaBaja;

	@CreatedBy
	@Column(name = "username_alta", length = 12, nullable = false)
	private String usernameAlta;

	@Column(name = "username_baja", length = 12)
	private String usernameBaja;

	@Column(name = "nombreJefe", length = 150)
	private String nombreJefe;

	@OneToMany(mappedBy = "equipo", fetch = FetchType.LAZY)
	private List<Miembros> miembros;

}
