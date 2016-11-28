package es.mira.progesin.persistence.entities.cuestionarios;

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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode()
@Builder
@ToString
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "CUESTIONARIO_PERSONALIZADO")
public class CuestionarioPersonalizado implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "seq_cuestionariopersonalizado", sequenceName = "seq_cuestionariopersonalizado", allocationSize = 1, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cuestionariopersonalizado")
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "nombre_cuestionario", nullable = false, length = 100)
	private String nombreCuestionario;

	@CreatedDate
	@Column(name = "fecha_creacion", nullable = false)
	private Date fechaCreacion;

	@CreatedBy
	@Column(name = "username_creacion", length = 15, nullable = false)
	private String usernameCreacion;

	// @Column(name = "id_modelo_cuestionario", nullable = false)
	// private Integer idModeloCuestionario;

	@ManyToOne
	@JoinColumn(name = "id_modelo_cuestionario", nullable = false)
	private ModeloCuestionario modeloCuestionario;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "cuest_per_preguntas", joinColumns = @JoinColumn(name = "id_cuest_pers"), inverseJoinColumns = @JoinColumn(name = "id_preg_elegida"))
	private List<PreguntasCuestionario> preguntasElegidas;
}
