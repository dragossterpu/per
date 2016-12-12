package es.mira.progesin.persistence.entities.gd;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import es.mira.progesin.converters.ListaExtensionesAdapter;
import es.mira.progesin.persistence.entities.enums.AmbitoInspeccionEnum;
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
@Table(name = "tipodocumentacionprevia")

public class TipoDocumentacion implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "seq_tipodocumentacionprevia", sequenceName = "seq_tipodocumentacionprevia", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tipodocumentacionprevia")
	@Column(name = "ID", nullable = false)
	private Long id;

	@Column(name = "DESCRIPCION", length = 255)
	private String descripcion;

	@Column(name = "NOMBRE", length = 255)
	private String nombre;

	@Column(name = "EXTENSIONES")
	@Convert(converter = ListaExtensionesAdapter.class)
	private List<String> extensiones;

	@Column(name = "ambito", length = 10)
	@Enumerated(EnumType.STRING)
	private AmbitoInspeccionEnum ambito;
}
