package es.mira.progesin.persistence.entities;

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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "guiaPersonalizada")
public class GuiaPersonalizada {

	@Id
	@SequenceGenerator(name = "seq_guiaPersonalizada", sequenceName = "seq_guiaPersonalizada", allocationSize = 1, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_guiaPersonalizada")
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "nombre_guia", nullable = false, length = 100)
	private String nombreGuia;

	@CreatedDate
	@Column(name = "fecha_creacion", nullable = false)
	private Date fechaCreacion;

	@CreatedBy
	@Column(name = "username_creacion", nullable = false)
	private String usernameCreacion;

	@Column(name = "fecha_baja")
	private Date fechaBaja;

	@Column(name = "username_baja")
	private String usernameBaja;

	@ManyToOne
	@JoinColumn(name = "id_modelo_guia", nullable = false)
	private Guia guia;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "guia_personalizada_pasos", joinColumns = @JoinColumn(name = "id_guia_pers"), inverseJoinColumns = @JoinColumn(name = "id_paso_elegido"))
	private List<GuiaPasos> pasosElegidos;
}
