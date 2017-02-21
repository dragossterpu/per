package es.mira.progesin.persistence.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@Entity
@Table(name = "guias")
@NamedEntityGraph(name = "Guia.preguntas", attributeNodes = @NamedAttributeNode("preguntas"))
public class Guia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "seq_guias", sequenceName = "seq_guias", allocationSize = 1, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_guias")
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "nombre_guia", nullable = false)
	private String nombre;

	@Column(name = "orden", nullable = false)
	private Integer orden;

	@ManyToOne
	@JoinColumn(name = "tipo_inspeccion")
	private TipoInspeccion tipoInspeccion;

	@CreatedDate
	@Column(name = "fecha_creacion", nullable = false)
	private Date fechaCreacion;

	@CreatedBy
	@Column(name = "usuario_creacion", nullable = false)
	private String usuarioCreacion;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "idGuia")
	private List<GuiaPreguntas> preguntas;

	@Column(name = "fecha_modificacion")
	private Date fechaModificacion;

	@Column(name = "usuario_modificacion")
	private String usuarioModificacion;

}
