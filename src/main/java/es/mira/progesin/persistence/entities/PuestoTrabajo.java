package es.mira.progesin.persistence.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

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
@Table(name = "PUESTOSTRABAJO")
public class PuestoTrabajo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PUESTO_TRABAJO")
	@SequenceGenerator(name = "SEQ_PUESTO_TRABAJO", sequenceName = "SEQ_PUESTO_TRABAJO", allocationSize = 1, initialValue = 1)
	@Column(name = "ID", length = 2)
	private Long id;

	@Column(name = "DESCRIPCION", length = 100)
	private String descripcion;

	@Column(name = "nivel")
	private int nivel;

	@Column(name = "clase", length = 20)
	private String clase;
}
