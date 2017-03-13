package es.mira.progesin.persistence.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * Entidad para el almacenamiento de pasos de guía
 * 
 * @author Ezentis
 *
 */

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "guiaPasos")
public class GuiaPasos implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "seq_pasosGuia", sequenceName = "seq_pasosGuia", allocationSize = 1, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_pasosGuia")
	@Column(name = "id", nullable = false)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "idGuia")
	private Guia idGuia;

	@Column(name = "paso", nullable = false, length = 2000)
	private String paso;

	@Column(name = "orden", nullable = false)
	private Integer orden;

	@Column(name = "fecha_baja")
	protected Date fechaBaja;

	@Column(name = "username_baja")
	protected String usernameBaja;

}
